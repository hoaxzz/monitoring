@echo off
echo ============================================
echo   Monitoring Anak Backend Starter
echo ============================================
echo.

REM Set Maven
set MAVEN_HOME=C:\maven\apache-maven-3.9.12
set PATH=%MAVEN_HOME%\bin;%PATH%

echo [1/4] Checking Java installation...
java -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Java not found! Please install Java 17 or higher.
    pause
    exit /b 1
)
echo [OK] Java is installed
echo.

echo [2/4] Checking Maven installation...
call mvn -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Maven not found!
    echo Please run: install_maven.ps1
    pause
    exit /b 1
)
echo [OK] Maven is ready
echo.

echo [3/4] Checking MySQL connection...
echo Trying to connect to MySQL at localhost:3306...
timeout /t 2 /nobreak >nul

REM Note: We'll let Spring Boot handle the connection check
echo [WARNING] Make sure MySQL is running!
echo          - If using XAMPP: Start MySQL in XAMPP Control Panel
echo          - If using MySQL Server: Check if service is running
echo          - Database 'monitoring_anak' must exist
echo.
echo If MySQL is not installed, read: SETUP_DATABASE.md
echo Or run: .\install_xampp.ps1
echo.

set /p continue="Continue anyway? (Y/N): "
if /i not "%continue%"=="Y" (
    echo Cancelled by user.
    pause
    exit /b 0
)

echo.
echo [4/4] Starting Spring Boot backend...
echo Server will run on: http://localhost:9090/api
echo.

call mvn spring-boot:run

echo.
echo ============================================
if errorlevel 1 (
    echo [ERROR] Backend failed to start!
    echo.
    echo Common issues:
    echo 1. MySQL is not running
    echo 2. Database 'monitoring_anak' doesn't exist
    echo 3. Wrong MySQL username/password in application.properties
    echo.
    echo Please check SETUP_DATABASE.md for help
) else (
    echo [SUCCESS] Backend stopped gracefully
)
echo ============================================
pause
