@echo off
setlocal
echo ==================================================
echo BULK UPLOAD LAPORAN TO DATABASE (BLOB)
echo ==================================================
echo.

:: Menjalankan script PowerShell untuk memproses upload
:: -ExecutionPolicy Bypass dijalankan agar tidak terhalang security policy
:: -File upload_laporan.ps1 akan dipanggil dan outputnya ditampilkan di terminal ini

powershell -ExecutionPolicy Bypass -File "%~dp0upload_laporan.ps1"

if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] Terjadi kesalahan saat menjalankan proses upload.
) else (
    echo.
    echo [SELESAI] Semua proses telah dijalankan.
)

echo.
pause
