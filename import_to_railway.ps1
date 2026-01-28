# import_to_railway.ps1
# Script to import schema.sql to the Railway MySQL database

$mysqlPath = "mysql" # Assumes mysql is in PATH
# If it's not in PATH, try XAMPP path:
$mysqlPaths = @(
    "C:\xampp\mysql\bin\mysql.exe",
    "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe",
    "C:\Program Files\MySQL\MySQL Workbench 8.0\mysql.exe"
)
foreach ($path in $mysqlPaths) {
    if (Test-Path $path) {
        $mysqlPath = $path
        break
    }
}

if (-not (Test-Path $mysqlPath) -and $mysqlPath -ne "mysql") {
    Write-Host "Error: mysql client not found. Please install MySQL client or make sure it's in your PATH." -ForegroundColor Red
    exit
}

$hostName = "yamabiko.proxy.rlwy.net"
$port = "56743"
$user = "root"
$password = "tUlzDPljpsSrqLnZBwOzgpnorrHPtguF"
$database = "railway"
$schemaFile = "schema.sql"

Write-Host "Importing $schemaFile to Railway MySQL..." -ForegroundColor Cyan

# Use cat to feed the schema file into mysql command
Get-Content $schemaFile | & $mysqlPath -h $hostName -P $port -u $user "-p$password" $database

if ($LASTEXITCODE -eq 0) {
    Write-Host "Import Successful!" -ForegroundColor Green
}
else {
    Write-Host "Import Failed with exit code $LASTEXITCODE" -ForegroundColor Red
}
