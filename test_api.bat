@echo off
echo ==================================================
echo TESTING MONITORING ANAK BACKEND API (LENGKAP)
echo ==================================================
echo.

echo 1. TESTING AUTH LOGIN (Input: guru_budi / password123)
echo --------------------------------
curl -s -X POST http://localhost:9090/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"guru_budi\", \"password\":\"password\"}" ^
  | findstr /C:"token" > nul && (echo LOGIN SUCCESS) || (echo LOGIN FAILED)
curl -s -X POST http://localhost:9090/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"guru_budi\", \"password\":\"password123\"}"
echo.
echo.
timeout /t 1 > nul


echo 2. TESTING GET /anak (Semua Data Anak)
echo --------------------------------
curl -s http://localhost:9090/api/anak
echo.
echo.

echo 3. TESTING GET /anak/1 (Data Anak ID 1)
echo --------------------------------
curl -s http://localhost:9090/api/anak/1
echo.
echo.

echo 4. TESTING GET /perkembangan/fisik/1 (Fisik Anak ID 1)
echo --------------------------------
curl -s http://localhost:9090/api/perkembangan/fisik/1
echo.
echo.

echo 5. TESTING GET /perkembangan/aspek/1 (Aspek Anak ID 1)
echo --------------------------------
curl -s http://localhost:9090/api/perkembangan/aspek/1
echo.
echo.

echo 6. TESTING GET /perkembangan/aspek/1/average (Rata-rata Nilai)
echo --------------------------------
curl -s http://localhost:9090/api/perkembangan/aspek/1/average
echo.
echo.

echo 7. TESTING GET /laporan (Semua Data Laporan)
echo --------------------------------------
curl -s http://localhost:9090/api/laporan
echo.
echo.

echo 8. TESTING GET Download (Laporan ID 1)
echo ---------------------------------------------------------------------
echo Mencoba mendownload file...
curl -I http://localhost:9090/api/laporan/1/download
echo.

echo ==================================================
echo SELESAI.
echo ==================================================
pause
