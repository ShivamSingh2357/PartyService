#!/bin/bash

################################################################################
# Party Service - Automated Setup Script (Mac/Linux)
# This script automates the complete setup process for new developers
################################################################################

set -e  # Exit on any error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Print functions
print_header() {
    echo -e "\n${BLUE}================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}================================${NC}\n"
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠ $1${NC}"
}

print_info() {
    echo -e "${BLUE}ℹ $1${NC}"
}

################################################################################
# 1. CHECK PREREQUISITES
################################################################################

check_prerequisites() {
    print_header "Checking Prerequisites"
    
    local all_good=true
    
    # Check Java
    if command -v java &> /dev/null; then
        JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
        if [ "$JAVA_VERSION" -ge 17 ]; then
            print_success "Java $JAVA_VERSION found"
        else
            print_error "Java 17 or higher is required. Found version: $JAVA_VERSION"
            all_good=false
        fi
    else
        print_error "Java is not installed. Please install Java 17 or higher"
        echo "Download from: https://www.oracle.com/java/technologies/downloads/"
        all_good=false
    fi
    
    # Check Maven
    if command -v mvn &> /dev/null; then
        MVN_VERSION=$(mvn -version | head -n 1 | awk '{print $3}')
        print_success "Maven $MVN_VERSION found"
    else
        print_error "Maven is not installed. Please install Maven 3.6 or higher"
        echo "Download from: https://maven.apache.org/download.cgi"
        all_good=false
    fi
    
    # Check PostgreSQL
    if command -v psql &> /dev/null; then
        PSQL_VERSION=$(psql --version | awk '{print $3}')
        print_success "PostgreSQL $PSQL_VERSION found"
    else
        print_warning "PostgreSQL client not found in PATH"
        print_info "You can still use a remote database or install PostgreSQL later"
        read -p "Do you want to continue without local PostgreSQL? (y/n): " continue_without_pg
        if [[ ! $continue_without_pg =~ ^[Yy]$ ]]; then
            exit 1
        fi
    fi
    
    # Check Git
    if command -v git &> /dev/null; then
        GIT_VERSION=$(git --version | awk '{print $3}')
        print_success "Git $GIT_VERSION found"
    else
        print_error "Git is not installed. Please install Git"
        echo "Download from: https://git-scm.com/downloads"
        all_good=false
    fi
    
    if [ "$all_good" = false ]; then
        print_error "Please install missing prerequisites and run this script again"
        exit 1
    fi
    
    print_success "All prerequisites are met!"
}

################################################################################
# 2. CLONE REPOSITORY
################################################################################

clone_repository() {
    print_header "Cloning Repository"
    
    REPO_URL="https://github.com/dfh-swt-banking/sales-and-onboarding"
    WORK_DIR="$HOME/workspace"
    PROJECT_PATH="$WORK_DIR/sales-and-onboarding/BackendServices/PartyService"
    
    # Ask for custom directory
    read -p "Enter workspace directory (press Enter for default: $WORK_DIR): " custom_dir
    if [ ! -z "$custom_dir" ]; then
        WORK_DIR="$custom_dir"
        PROJECT_PATH="$WORK_DIR/sales-and-onboarding/BackendServices/PartyService"
    fi
    
    # Create workspace directory
    mkdir -p "$WORK_DIR"
    cd "$WORK_DIR"
    
    # Clone or update repository
    if [ -d "sales-and-onboarding" ]; then
        print_warning "Repository already exists. Updating..."
        cd sales-and-onboarding
        git pull origin main
    else
        print_info "Cloning repository from $REPO_URL"
        git clone "$REPO_URL"
    fi
    
    cd "$PROJECT_PATH"
    print_success "Repository ready at: $PROJECT_PATH"
}

################################################################################
# 3. DATABASE SETUP
################################################################################

setup_database() {
    print_header "Database Configuration"
    
    echo "Choose database setup option:"
    echo "1. Local PostgreSQL (will create database)"
    echo "2. Remote/Cloud Database (AWS RDS, etc.)"
    echo "3. Use existing connection details"
    read -p "Enter choice (1/2/3): " db_choice
    
    case $db_choice in
        1)
            setup_local_database
            ;;
        2)
            setup_remote_database
            ;;
        3)
            setup_existing_database
            ;;
        *)
            print_error "Invalid choice"
            exit 1
            ;;
    esac
}

setup_local_database() {
    print_info "Setting up local PostgreSQL database"
    
    DB_NAME="party_service"
    DB_USER="partyuser"
    DB_PASSWORD="partypass"
    DB_HOST="localhost"
    DB_PORT="5432"
    
    read -p "Database name (press Enter for '$DB_NAME'): " input_db_name
    DB_NAME=${input_db_name:-$DB_NAME}
    
    read -p "Database user (press Enter for '$DB_USER'): " input_db_user
    DB_USER=${input_db_user:-$DB_USER}
    
    read -sp "Database password (press Enter for '$DB_PASSWORD'): " input_db_password
    echo
    DB_PASSWORD=${input_db_password:-$DB_PASSWORD}
    
    # Try to create database using psql
    if command -v psql &> /dev/null; then
        print_info "Creating database..."
        
        # Create database and user
        PGPASSWORD=postgres psql -U postgres -h localhost -c "CREATE DATABASE $DB_NAME;" 2>/dev/null || print_warning "Database may already exist"
        PGPASSWORD=postgres psql -U postgres -h localhost -c "CREATE USER $DB_USER WITH PASSWORD '$DB_PASSWORD';" 2>/dev/null || print_warning "User may already exist"
        PGPASSWORD=postgres psql -U postgres -h localhost -c "GRANT ALL PRIVILEGES ON DATABASE $DB_NAME TO $DB_USER;" 2>/dev/null
        
        print_success "Database setup completed"
    else
        print_warning "Please create the database manually:"
        echo "  Database: $DB_NAME"
        echo "  User: $DB_USER"
        echo "  Password: $DB_PASSWORD"
    fi
    
    DB_URL="jdbc:postgresql://$DB_HOST:$DB_PORT/$DB_NAME"
}

setup_remote_database() {
    print_info "Configuring remote database connection"
    
    read -p "Database host: " DB_HOST
    read -p "Database port (default 5432): " DB_PORT
    DB_PORT=${DB_PORT:-5432}
    read -p "Database name: " DB_NAME
    read -p "Database username: " DB_USER
    read -sp "Database password: " DB_PASSWORD
    echo
    
    DB_URL="jdbc:postgresql://$DB_HOST:$DB_PORT/$DB_NAME"
}

setup_existing_database() {
    print_info "Using existing database configuration"
    print_warning "Make sure application.properties has correct database details"
    
    read -p "Database JDBC URL: " DB_URL
    read -p "Database username: " DB_USER
    read -sp "Database password: " DB_PASSWORD
    echo
}

################################################################################
# 4. CONFIGURE APPLICATION
################################################################################

configure_application() {
    print_header "Configuring Application"
    
    APP_PROPERTIES="$PROJECT_PATH/src/main/resources/application.properties"
    
    if [ ! -f "$APP_PROPERTIES" ]; then
        print_error "application.properties not found!"
        exit 1
    fi
    
    # Backup original file
    cp "$APP_PROPERTIES" "$APP_PROPERTIES.backup"
    print_info "Backup created: application.properties.backup"
    
    # Update database configuration
    sed -i.tmp "s|spring.datasource.url=.*|spring.datasource.url=$DB_URL|g" "$APP_PROPERTIES"
    sed -i.tmp "s|spring.datasource.username=.*|spring.datasource.username=$DB_USER|g" "$APP_PROPERTIES"
    sed -i.tmp "s|spring.datasource.password=.*|spring.datasource.password=$DB_PASSWORD|g" "$APP_PROPERTIES"
    rm -f "$APP_PROPERTIES.tmp"
    
    # Ask for server port
    read -p "Server port (press Enter for default 8082): " SERVER_PORT
    SERVER_PORT=${SERVER_PORT:-8082}
    sed -i.tmp "s|server.port=.*|server.port=$SERVER_PORT|g" "$APP_PROPERTIES"
    rm -f "$APP_PROPERTIES.tmp"
    
    print_success "Application configured successfully"
    print_info "Database URL: $DB_URL"
    print_info "Server will run on port: $SERVER_PORT"
}

################################################################################
# 5. BUILD PROJECT
################################################################################

build_project() {
    print_header "Building Project"
    
    cd "$PROJECT_PATH"
    
    print_info "Running Maven clean install..."
    print_info "This may take 2-5 minutes for first build (downloading dependencies)..."
    
    if mvn clean install -DskipTests; then
        print_success "Project built successfully!"
    else
        print_error "Build failed! Check the errors above."
        exit 1
    fi
}

################################################################################
# 6. RUN APPLICATION
################################################################################

run_application() {
    print_header "Starting Application"
    
    read -p "Do you want to start the application now? (y/n): " start_app
    
    if [[ $start_app =~ ^[Yy]$ ]]; then
        print_info "Starting Party Service..."
        print_info "Application will be available at: http://localhost:$SERVER_PORT"
        print_info "Swagger UI: http://localhost:$SERVER_PORT/swagger-ui/index.html"
        print_info "Health Check: http://localhost:$SERVER_PORT/actuator/health"
        print_info ""
        print_warning "Press Ctrl+C to stop the application"
        echo ""
        
        mvn spring-boot:run
    else
        print_info "Skipping application startup"
        print_info ""
        print_info "To start the application later, run:"
        echo "  cd $PROJECT_PATH"
        echo "  mvn spring-boot:run"
    fi
}

################################################################################
# 7. PRINT SUMMARY
################################################################################

print_summary() {
    print_header "Setup Complete! 🎉"
    
    echo "Project Location: $PROJECT_PATH"
    echo "Database: $DB_URL"
    echo "Server Port: $SERVER_PORT"
    echo ""
    echo "Useful Commands:"
    echo "  Start application:  cd $PROJECT_PATH && mvn spring-boot:run"
    echo "  Build project:      mvn clean install"
    echo "  Run tests:          mvn test"
    echo ""
    echo "Useful URLs (when app is running):"
    echo "  Swagger UI:    http://localhost:$SERVER_PORT/swagger-ui/index.html"
    echo "  Health Check:  http://localhost:$SERVER_PORT/actuator/health"
    echo "  API Base:      http://localhost:$SERVER_PORT/v1/party"
    echo ""
    print_success "You're all set! Happy Coding! 🚀"
}

################################################################################
# MAIN EXECUTION
################################################################################

main() {
    clear
    echo "╔════════════════════════════════════════════════════════╗"
    echo "║     Party Service - Automated Setup Script            ║"
    echo "║     This will set up everything for you!              ║"
    echo "╚════════════════════════════════════════════════════════╝"
    echo ""
    
    check_prerequisites
    clone_repository
    setup_database
    configure_application
    build_project
    
    print_summary
    
    run_application
}

# Run main function
main

