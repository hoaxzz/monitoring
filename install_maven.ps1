# Script untuk download dan install Maven
Write-Host "Downloading Apache Maven..." -ForegroundColor Green

# Buat folder Maven
$mavenDir = "C:\maven"
if (-not (Test-Path $mavenDir)) {
    New-Item -ItemType Directory -Path $mavenDir -Force | Out-Null
}

# Download Maven
$mavenVersion = "3.9.12"
$mavenUrl = "https://dlcdn.apache.org/maven/maven-3/$mavenVersion/binaries/apache-maven-$mavenVersion-bin.zip"
$zipFile = "$mavenDir\apache-maven-$mavenVersion-bin.zip"

Write-Host "Downloading from: $mavenUrl" -ForegroundColor Yellow
Invoke-WebRequest -Uri $mavenUrl -OutFile $zipFile

# Extract
Write-Host "Extracting Maven..." -ForegroundColor Green
Expand-Archive -Path $zipFile -DestinationPath $mavenDir -Force

# Cleanup
Remove-Item $zipFile

Write-Host "Maven installed successfully at: $mavenDir\apache-maven-$mavenVersion" -ForegroundColor Green
Write-Host ""
Write-Host "Sekarang jalankan: .\run_backend.bat" -ForegroundColor Cyan
