# Automation Script to Bulk Upload Laporan to Database using CURL
# Run this from terminal: powershell -ExecutionPolicy Bypass -File upload_laporan.ps1

$baseUrl = "http://localhost:9090/api"
$localFolder = "C:\Users\akip\Desktop\monitoring-anak-backend\laporan"
$periode = "2024-01-20"
$idGuru = 1 # Budi Santoso (Default Guru)

Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "STARTING BULK UPLOAD TO DATABASE (CREATE + UPLOAD)" -ForegroundColor Cyan
Write-Host "==================================================" -ForegroundColor Cyan

# 1. Login to get JWT Token
$token = $null
try {
    Write-Host "Authenticating as Guru..." -NoNewline
    $loginBody = @{
        username = "guru_budi"
        password = "password"
    } | ConvertTo-Json
    
    $loginResponse = Invoke-RestMethod -Uri "$baseUrl/auth/login" -Method Post -Body $loginBody -ContentType "application/json" -ErrorAction Stop
    if ($loginResponse.success) {
        $token = $loginResponse.data.token
        Write-Host " [OK] Authenticated." -ForegroundColor Green
    }
}
catch {
    Write-Host " [FAILED]" -ForegroundColor Red
    Write-Host "[ERROR] Login failed: $($_.Exception.Message)" -ForegroundColor Red
    pause
    return
}

$headers = @{
    Authorization = "Bearer $token"
}

# 2. Fetch all children from API for name matching
try {
    Write-Host "Fetching children and existing reports..."
    $anakResponse = Invoke-RestMethod -Uri "$baseUrl/anak" -Method Get -Headers $headers -ErrorAction Stop
    $anakList = $anakResponse.data
    
    $laporanResponse = Invoke-RestMethod -Uri "$baseUrl/laporan" -Method Get -Headers $headers -ErrorAction Stop
    $allLaporan = $laporanResponse.data
}
catch {
    Write-Host "[ERROR] Could not connect to backend or unauthorized. Make sure run_backend.bat is active!" -ForegroundColor Red
    Write-Host "Detail: $($_.Exception.Message)"
    pause
    return
}

# 2. Iterate through local files in /laporan
$files = Get-ChildItem -Path $localFolder -File
foreach ($file in $files) {
    $baseName = [System.IO.Path]::GetFileNameWithoutExtension($file.Name)
    $child = $anakList | Where-Object { $_.namaAnak -ieq $baseName }
    
    if ($child) {
        $idAnak = $child.idAnak
        Write-Host "Child Found: '$baseName' (ID: $idAnak)" -ForegroundColor Magenta
        
        # 3. Check if record already exists for this child and period
        $idLaporan = $null
        $matchLaporan = $allLaporan | Where-Object { $_.anak.idAnak -eq $idAnak -and $_.periode -eq $periode }
        
        if ($matchLaporan) {
            $idLaporan = $matchLaporan.idLaporan
            Write-Host "Existing record found (ID: $idLaporan). Proceeding to upload..." -ForegroundColor Gray
        }
        else {
            try {
                $laporanPayload = @{
                    anak       = @{ idAnak = $idAnak }
                    periode    = $periode
                    dibuatOleh = @{ idUser = $idGuru }
                } | ConvertTo-Json
                
                $createResponse = Invoke-RestMethod -Uri "$baseUrl/laporan" -Method Post -Body $laporanPayload -ContentType "application/json" -Headers $headers
                if ($createResponse.success) {
                    $idLaporan = $createResponse.data.idLaporan
                    Write-Host "DB Record Created (ID: $idLaporan)." -ForegroundColor Gray
                }
            }
            catch {
                Write-Host " [FAILED to create record: $($_.Exception.Message)]" -ForegroundColor Red
                continue
            }
        }

        # 4. Upload binary content using CURL.EXE (using .exe to bypass PowerShell alias)
        if ($idLaporan) {
            Write-Host "Uploading file..." -NoNewline
            $uploadUri = "$baseUrl/laporan/$idLaporan/upload"
            $filePath = $file.FullName
            
            # Use curl.exe specifically to avoid the Invoke-WebRequest alias
            $cmd = "curl.exe -s -X POST `"$uploadUri`" -H `"Authorization: Bearer $token`" -F `"file=@$filePath`""
            $uploadResultRaw = Invoke-Expression $cmd
            
            try {
                $uploadResult = $uploadResultRaw | ConvertFrom-Json
                if ($uploadResult.success) {
                    Write-Host " [OK] SUCCESS" -ForegroundColor Green
                }
                else {
                    Write-Host " [UPLOAD FAILED: $($uploadResult.message)]" -ForegroundColor Red
                }
            }
            catch {
                Write-Host " [ERROR parsing response: $uploadResultRaw]" -ForegroundColor Red
            }
        }
    }
    else {
        Write-Host "[SKIP] Filename '$($file.Name)' does not match any child in DB." -ForegroundColor Yellow
    }
}

Write-Host "`nBulk Upload Finished!" -ForegroundColor Cyan
pause
