# Script untuk download XAMPP (MySQL included)
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  XAMPP Installer Helper" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "XAMPP adalah paket lengkap yang berisi:" -ForegroundColor Yellow
Write-Host "  - Apache Web Server" -ForegroundColor White
Write-Host "  - MySQL Database" -ForegroundColor White
Write-Host "  - phpMyAdmin (GUI untuk manage database)" -ForegroundColor White
Write-Host ""

Write-Host "Membuka halaman download XAMPP..." -ForegroundColor Green
Start-Process "https://www.apachefriends.org/download.html"

Write-Host ""
Write-Host "Instruksi:" -ForegroundColor Cyan
Write-Host "1. Download XAMPP versi terbaru (PHP 8.x)" -ForegroundColor White
Write-Host "2. Install ke C:\xampp" -ForegroundColor White
Write-Host "3. Buka XAMPP Control Panel" -ForegroundColor White
Write-Host "4. Klik 'Start' pada MySQL" -ForegroundColor White
Write-Host "5. Buka browser: http://localhost/phpmyadmin" -ForegroundColor White
Write-Host "6. Buat database 'monitoring_anak'" -ForegroundColor White
Write-Host "7. Import file schema.sql" -ForegroundColor White
Write-Host ""
Write-Host "Setelah selesai, jalankan: .\run_backend.bat" -ForegroundColor Green
Write-Host ""

Read-Host "Press Enter to exit"
