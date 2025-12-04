@echo off
REM ############################################################################
REM Party Service - Automated Setup Script (Windows)
REM This script automates the complete setup process for new developers
REM ############################################################################

setlocal enabledelayedexpansion

REM Set colors (requires Windows 10+)
set "RED=[91m"
set "GREEN=[92m"
set "YELLOW=[93m"
set "BLUE=[94m"
set "NC=[0m"

echo.
echo ========================================================
echo      Party Service - Automated Setup Script
echo      This will set up everything for you!
echo ========================================================
echo.

REM ############################################################################
REM 1. CHECK PREREQUISITES
REM ############################################################################

echo.
echo ================================
echo Checking Prerequisites
echo ================================
echo.

set "ALL_GOOD=true"

REM Check Java
where java >nul 2>&1
if %errorlevel% equ 0 (
    for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
        set JAVA_VERSION=%%g
        set JAVA_VERSION=!JAVA_VERSION:"=!
        echo [92m[OK] Java found: !JAVA_VERSION![0m
    )
) else (
    echo [91m[ERROR] Java is not installed[0m
    echo Please install Java 17 or higher from: https://www.oracle.com/java/technologies/downloads/
    set "ALL_GOOD=false"
)

REM Check Maven
where mvn >nul 2>&1
if %errorlevel% equ 0 (
    for /f "tokens=3" %%g in ('mvn -version ^| findstr /i "Apache Maven"') do (
        echo [92m[OK] Maven found: %%g[0m
    )
) else (
    echo [91m[ERROR] Maven is not installed[0m
    echo Please install Maven from: https://maven.apache.org/download.cgi
    set "ALL_GOOD=false"
)

REM Check Git
where git >nul 2>&1
if %errorlevel% equ 0 (
    for /f "tokens=3" %%g in ('git --version') do (
        echo [92m[OK] Git found: %%g[0m
    )
) else (
    echo [91m[ERROR] Git is not installed[0m
    echo Please install Git from: https://git-scm.com/downloads
    set "ALL_GOOD=false"
)

REM Check PostgreSQL
where psql >nul 2>&1
if %errorlevel% equ 0 (
    echo [92m[OK] PostgreSQL client found[0m
) else (
    echo [93m[WARNING] PostgreSQL client not found in PATH[0m
    echo You can still use a remote database or install PostgreSQL later
)

if "%ALL_GOOD%"=="false" (
    echo.
    echo [91mPlease install missing prerequisites and run this script again[0m
    pause
    exit /b 1
)

echo.
echo [92mAll prerequisites are met![0m

REM ############################################################################
REM 2. CLONE REPOSITORY
REM ############################################################################

echo.
echo ================================
echo Cloning Repository
echo ================================
echo.

set "REPO_URL=https://github.com/dfh-swt-banking/sales-and-onboarding"
set "WORK_DIR=%USERPROFILE%\workspace"
set "PROJECT_PATH=%WORK_DIR%\sales-and-onboarding\BackendServices\PartyService"

set /p "CUSTOM_DIR=Enter workspace directory (press Enter for default: %WORK_DIR%): "
if not "%CUSTOM_DIR%"=="" (
    set "WORK_DIR=%CUSTOM_DIR%"
    set "PROJECT_PATH=%WORK_DIR%\sales-and-onboarding\BackendServices\PartyService"
)

REM Create workspace directory
if not exist "%WORK_DIR%" mkdir "%WORK_DIR%"
cd /d "%WORK_DIR%"

REM Clone or update repository
if exist "sales-and-onboarding" (
    echo [93mRepository already exists. Updating...[0m
    cd sales-and-onboarding
    git pull origin main
) else (
    echo Cloning repository from %REPO_URL%
    git clone "%REPO_URL%"
)

cd /d "%PROJECT_PATH%"
echo [92mRepository ready at: %PROJECT_PATH%[0m

REM ############################################################################
REM 3. DATABASE SETUP
REM ############################################################################

echo.
echo ================================
echo Database Configuration
echo ================================
echo.

echo Choose database setup option:
echo 1. Local PostgreSQL (will create database)
echo 2. Remote/Cloud Database (AWS RDS, etc.)
echo 3. Use existing connection details
set /p "DB_CHOICE=Enter choice (1/2/3): "

if "%DB_CHOICE%"=="1" goto LOCAL_DB
if "%DB_CHOICE%"=="2" goto REMOTE_DB
if "%DB_CHOICE%"=="3" goto EXISTING_DB
echo [91mInvalid choice[0m
pause
exit /b 1

:LOCAL_DB
echo.
echo Setting up local PostgreSQL database

set "DB_NAME=party_service"
set "DB_USER=partyuser"
set "DB_PASSWORD=partypass"
set "DB_HOST=localhost"
set "DB_PORT=5432"

set /p "INPUT_DB_NAME=Database name (press Enter for '%DB_NAME%'): "
if not "%INPUT_DB_NAME%"=="" set "DB_NAME=%INPUT_DB_NAME%"

set /p "INPUT_DB_USER=Database user (press Enter for '%DB_USER%'): "
if not "%INPUT_DB_USER%"=="" set "DB_USER=%INPUT_DB_USER%"

set /p "INPUT_DB_PASSWORD=Database password (press Enter for '%DB_PASSWORD%'): "
if not "%INPUT_DB_PASSWORD%"=="" set "DB_PASSWORD=%INPUT_DB_PASSWORD%"

set "DB_URL=jdbc:postgresql://%DB_HOST%:%DB_PORT%/%DB_NAME%"

echo [93mNote: You may need to create the database manually using PostgreSQL tools[0m
echo   Database: %DB_NAME%
echo   User: %DB_USER%
echo   Password: %DB_PASSWORD%

goto CONFIG_APP

:REMOTE_DB
echo.
echo Configuring remote database connection

set /p "DB_HOST=Database host: "
set /p "DB_PORT=Database port (default 5432): "
if "%DB_PORT%"=="" set "DB_PORT=5432"
set /p "DB_NAME=Database name: "
set /p "DB_USER=Database username: "
set /p "DB_PASSWORD=Database password: "

set "DB_URL=jdbc:postgresql://%DB_HOST%:%DB_PORT%/%DB_NAME%"

goto CONFIG_APP

:EXISTING_DB
echo.
echo Using existing database configuration

set /p "DB_URL=Database JDBC URL: "
set /p "DB_USER=Database username: "
set /p "DB_PASSWORD=Database password: "

goto CONFIG_APP

REM ############################################################################
REM 4. CONFIGURE APPLICATION
REM ############################################################################

:CONFIG_APP
echo.
echo ================================
echo Configuring Application
echo ================================
echo.

set "APP_PROPERTIES=%PROJECT_PATH%\src\main\resources\application.properties"

if not exist "%APP_PROPERTIES%" (
    echo [91mapplication.properties not found![0m
    pause
    exit /b 1
)

REM Backup original file
copy "%APP_PROPERTIES%" "%APP_PROPERTIES%.backup" >nul
echo Backup created: application.properties.backup

REM Update database configuration using PowerShell
powershell -Command "(Get-Content '%APP_PROPERTIES%') -replace 'spring.datasource.url=.*', 'spring.datasource.url=%DB_URL%' | Set-Content '%APP_PROPERTIES%'"
powershell -Command "(Get-Content '%APP_PROPERTIES%') -replace 'spring.datasource.username=.*', 'spring.datasource.username=%DB_USER%' | Set-Content '%APP_PROPERTIES%'"
powershell -Command "(Get-Content '%APP_PROPERTIES%') -replace 'spring.datasource.password=.*', 'spring.datasource.password=%DB_PASSWORD%' | Set-Content '%APP_PROPERTIES%'"

REM Ask for server port
set /p "SERVER_PORT=Server port (press Enter for default 8082): "
if "%SERVER_PORT%"=="" set "SERVER_PORT=8082"
powershell -Command "(Get-Content '%APP_PROPERTIES%') -replace 'server.port=.*', 'server.port=%SERVER_PORT%' | Set-Content '%APP_PROPERTIES%'"

echo [92mApplication configured successfully[0m
echo Database URL: %DB_URL%
echo Server will run on port: %SERVER_PORT%

REM ############################################################################
REM 5. BUILD PROJECT
REM ############################################################################

echo.
echo ================================
echo Building Project
echo ================================
echo.

cd /d "%PROJECT_PATH%"

echo Running Maven clean install...
echo This may take 2-5 minutes for first build (downloading dependencies)...
echo.

call mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo [91mBuild failed! Check the errors above.[0m
    pause
    exit /b 1
)

echo [92mProject built successfully![0m

REM ############################################################################
REM 6. PRINT SUMMARY
REM ############################################################################

echo.
echo ================================
echo Setup Complete! 🎉
echo ================================
echo.

echo Project Location: %PROJECT_PATH%
echo Database: %DB_URL%
echo Server Port: %SERVER_PORT%
echo.
echo Useful Commands:
echo   Start application:  mvn spring-boot:run
echo   Build project:      mvn clean install
echo   Run tests:          mvn test
echo.
echo Useful URLs (when app is running):
echo   Swagger UI:    http://localhost:%SERVER_PORT%/swagger-ui/index.html
echo   Health Check:  http://localhost:%SERVER_PORT%/actuator/health
echo   API Base:      http://localhost:%SERVER_PORT%/v1/party
echo.
echo [92mYou're all set! Happy Coding! 🚀[0m
echo.

REM ############################################################################
REM 7. RUN APPLICATION
REM ############################################################################

set /p "START_APP=Do you want to start the application now? (y/n): "
if /i "%START_APP%"=="y" (
    echo.
    echo Starting Party Service...
    echo Application will be available at: http://localhost:%SERVER_PORT%
    echo Swagger UI: http://localhost:%SERVER_PORT%/swagger-ui/index.html
    echo Health Check: http://localhost:%SERVER_PORT%/actuator/health
    echo.
    echo [93mPress Ctrl+C to stop the application[0m
    echo.
    
    call mvn spring-boot:run
) else (
    echo.
    echo Skipping application startup
    echo.
    echo To start the application later, run:
    echo   cd %PROJECT_PATH%
    echo   mvn spring-boot:run
    echo.
)

pause

