# Party Service API

A RESTful API service for managing party (customer) information built with Spring Boot. This service provides APIs to create, update, and retrieve customer details.

## 📋 Table of Contents

- [About the Project](#about-the-project)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Available APIs](#available-apis)
- [Project Structure](#project-structure)
- [Configuration](#configuration)

---

## 🎯 About the Project

Party Service is a microservice that manages customer/party information including:
- Customer ID
- First and Last Names
- Email Address
- Phone Number

The service provides REST APIs to create, update, and retrieve party details with proper validation and error handling.

---

## 🛠 Tech Stack

- **Java 17** - Programming language
- **Spring Boot 4.0.0** - Application framework
- **Spring Data JPA** - Database operations
- **PostgreSQL** - Database
- **Hibernate** - ORM framework
- **Lombok** - Reduces boilerplate code
- **SpringDoc OpenAPI (Swagger)** - API documentation
- **Maven** - Build tool

---

## ✅ Prerequisites

Before you begin, make sure you have the following installed on your computer:

1. **Java 17 or higher**
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Verify installation: Open terminal and run `java -version`

2. **Maven 3.6+** (Usually comes with Java)
   - Verify installation: Run `mvn -version`

3. **PostgreSQL Database**
   - Download from: https://www.postgresql.org/download/
   - Or use any cloud PostgreSQL service (AWS RDS, etc.)

4. **Git** (to clone the project)
   - Download from: https://git-scm.com/downloads

5. **Your favorite IDE** (Optional but recommended)
   - IntelliJ IDEA (recommended)
   - Eclipse
   - VS Code with Java extensions

---

## 🚀 Getting Started

### ⚡ Quick Setup (Automated - Recommended for New Joiners)

We've created automated setup scripts that do everything for you! Just run one command:

**For Mac/Linux:**
```bash
bash <(curl -s https://raw.githubusercontent.com/dfh-swt-banking/sales-and-onboarding/main/BackendServices/PartyService/setup.sh)
```

**Or download and run locally:**
```bash
# Download the script
curl -O https://raw.githubusercontent.com/dfh-swt-banking/sales-and-onboarding/main/BackendServices/PartyService/setup.sh
chmod +x setup.sh

# Run it
./setup.sh
```

**For Windows:**
```batch
REM Download setup.bat from the repository and run:
setup.bat
```

The automated script will:
- ✅ Check all prerequisites (Java, Maven, PostgreSQL, Git)
- ✅ Clone the repository
- ✅ Set up the database (local or remote)
- ✅ Configure application properties
- ✅ Build the project
- ✅ Start the application

**That's it! The script handles everything automatically! 🎉**

---

### 📖 Manual Setup (Alternative Method)

If you prefer to set up manually or want to understand each step, follow these instructions:

### Step 1: Clone the Project

```bash
# Open terminal and navigate to where you want to save the project
cd /path/to/your/workspace

# Clone the repository (if using Git)
git clone https://github.com/dfh-swt-banking/sales-and-onboarding#
cd sales-and-onboarding/BackendServices/PartyService
```

### Step 2: Set Up the Database

#### Option A: Using Local PostgreSQL

1. **Start PostgreSQL** on your machine

2. **Create a Database**
   ```sql
   -- Open PostgreSQL command line or pgAdmin
   CREATE DATABASE party_service;
   ```

3. **Create a User** (optional, or use existing postgres user)
   ```sql
   CREATE USER partyuser WITH PASSWORD 'partypass';
   GRANT ALL PRIVILEGES ON DATABASE party_service TO partyuser;
   ```

#### Option B: Using Cloud Database (AWS RDS, etc.)

1. Get your database connection details (host, port, database name, username, password)
2. Make sure the database is accessible from your local machine

### Step 3: Configure Database Connection

Open the file: `src/main/resources/application.properties`

Update these lines with your database details:

```properties
# Change these to match your database setup
spring.datasource.url=jdbc:postgresql://localhost:5432/party_service
spring.datasource.username=postgres
spring.datasource.password=postgres
```

**Example configurations:**

**For Local PostgreSQL:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/party_service
spring.datasource.username=postgres
spring.datasource.password=yourpassword
```

**For Cloud Database:**
```properties
spring.datasource.url=jdbc:postgresql://your-db-host.com:5432/party_service
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Step 4: Build the Project

```bash
# Navigate to project root directory (where pom.xml is located)
cd sales-and-onboarding/BackendServices/PartyService

# Build the project using Maven
mvn clean install

# This will:
# - Download all required dependencies
# - Compile the code
# - Run tests
# - Create a JAR file
```

**Note:** First build might take 2-5 minutes to download dependencies.

---

## ▶️ Running the Application

There are three ways to run the application:

### Method 1: Using Maven (Easiest)

```bash
mvn spring-boot:run
```

### Method 2: Using Java Command

```bash
# First build the project
mvn clean package

# Then run the JAR file
java -jar target/PartyService-0.0.1-SNAPSHOT.jar
```

### Method 3: Using IDE (IntelliJ/Eclipse)

1. Open the project in your IDE
2. Navigate to `src/main/java/com/candescent/PartyService/PartyServiceApplication.java`
3. Right-click on the file
4. Select "Run PartyServiceApplication"

### ✅ Verify Application is Running

Once started, you should see logs like:

```
Started PartyServiceApplication in X.XXX seconds
Tomcat started on port(s): 8082 (http)
```

**Quick Health Check:**
Open browser and go to: `http://localhost:8082/actuator/health`

You should see:
```json
{
  "status": "UP"
}
```

---

## 📚 API Documentation

Once the application is running, you can access the **interactive API documentation** (Swagger UI):

🔗 **Swagger UI:** http://localhost:8082/swagger-ui/index.html

This provides:
- Interactive API testing
- Request/Response examples
- Complete API documentation
- Try out APIs directly from the browser

---

## 🔌 Available APIs

### 1. Create Party (Customer)

**POST** `/v1/party`

Creates a new party/customer record.

**Request Body:**
```json
{
  "payload": {
    "custId": 2,
    "custFirstName": "John",
    "custLastName": "Doe",
    "emailId": "john.doe@example.com",
    "phoneNo": "9876543210"
  }
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "message": "Party created successfully",
  "payload": {
    "id": "1",
    "custId": 2,
    "custFirstName": "John",
    "custLastName": "Doe",
    "emailId": "john.doe@example.com",
    "phoneNo": "9876543210"
  }
}
```

### 2. Update Party

**PUT** `/v1/party/{id}`

Updates an existing party record by ID.

**Path Parameter:** `id` - Party database ID

**Request Body:**
```json
{
  "payload": {
    "custId": 2,
    "custFirstName": "John",
    "custLastName": "Smith",
    "emailId": "john.smith@example.com",
    "phoneNo": "9876543210"
  }
}
```

### 3. Get Party by Customer ID

**GET** `/v1/party/customer/{custId}`

Retrieves a party record by customer ID.

**Path Parameter:** `custId` - Customer ID (mandatory)

**Response:**
```json
{
  "status": "SUCCESS",
  "message": "Party retrieved successfully",
  "payload": {
    "id": "1",
    "custId": 2,
    "custFirstName": "John",
    "custLastName": "Doe",
    "emailId": "john.doe@example.com",
    "phoneNo": "9876543210"
  }
}
```

### Testing APIs

You can test the APIs using:
1. **Swagger UI** (easiest) - http://localhost:8082/swagger-ui/index.html
2. **Postman** - Import the API collection
3. **cURL** commands:

```bash
# Create Party
curl -X POST http://localhost:8082/v1/party \
  -H "Content-Type: application/json" \
  -d '{
    "payload": {
      "custId": 2,
      "custFirstName": "John",
      "custLastName": "Doe",
      "emailId": "john@example.com",
      "phoneNo": "9876543210"
    }
  }'

# Get Party by Customer ID
curl http://localhost:8082/v1/party/customer/2
```

---

## 📁 Project Structure

```
PartyService/
├── src/
│   ├── main/
│   │   ├── java/com/candescent/PartyService/
│   │   │   ├── api/              # API interfaces
│   │   │   │   └── PartyApi.java
│   │   │   ├── common/           # Common utilities
│   │   │   │   ├── constants/
│   │   │   │   ├── exception/    # Custom exceptions & handlers
│   │   │   │   └── util/
│   │   │   ├── config/           # Configuration classes
│   │   │   │   ├── DatabaseConfig.java
│   │   │   │   ├── JacksonConfig.java
│   │   │   │   └── OpenApiConfig.java
│   │   │   ├── controller/       # REST controllers
│   │   │   │   └── PartyController.java
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   │   ├── common/
│   │   │   │   ├── request/
│   │   │   │   └── response/
│   │   │   ├── entities/         # JPA entities
│   │   │   │   ├── BaseEntity.java
│   │   │   │   └── PartyEntity.java
│   │   │   ├── mapper/           # Entity-DTO mappers
│   │   │   │   └── PartyMapper.java
│   │   │   ├── repository/       # Database repositories
│   │   │   │   └── PartyRepository.java
│   │   │   ├── service/          # Business logic
│   │   │   │   └── PartyService.java
│   │   │   └── PartyServiceApplication.java  # Main class
│   │   └── resources/
│   │       └── application.properties  # Configuration
│   └── test/                     # Test classes
├── pom.xml                       # Maven dependencies
└── README.md                     # This file
```

---

## ⚙️ Configuration

### Important Application Properties

Located in: `src/main/resources/application.properties`

#### Server Configuration
```properties
server.port=8082  # Change this to use a different port
```

#### Database Configuration
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/party_service
spring.datasource.username=postgres
spring.datasource.password=postgres
```

#### JPA/Hibernate Configuration
```properties
spring.jpa.hibernate.ddl-auto=update  # Auto-create/update database tables
spring.jpa.show-sql=false             # Set to 'true' to see SQL queries in logs
```

#### Connection Pool Settings
```properties
spring.datasource.hikari.maximum-pool-size=20  # Max database connections
spring.datasource.hikari.minimum-idle=5        # Min idle connections
```

---

## 🐛 Troubleshooting

### Common Issues and Solutions

#### 1. Port Already in Use
**Error:** `Port 8082 is already in use`

**Solution:**
- Change port in `application.properties`: `server.port=8083`
- Or stop the application using port 8082

#### 2. Database Connection Failed
**Error:** `Connection refused` or `Could not connect to database`

**Solutions:**
- Check if PostgreSQL is running
- Verify database credentials in `application.properties`
- Ensure database `party_service` exists
- Check if firewall is blocking the connection

#### 3. Java Version Mismatch
**Error:** `Unsupported class file version`

**Solution:**
- Verify Java 17+ is installed: `java -version`
- Set JAVA_HOME to Java 17 installation

#### 4. Build Fails
**Error:** Maven build errors

**Solution:**
```bash
# Clean and rebuild
mvn clean install -U

# Skip tests if needed (during initial setup)
mvn clean install -DskipTests
```

#### 5. Lombok Errors in IDE
**Error:** Cannot find symbol for getters/setters

**Solution:**
- Install Lombok plugin in your IDE
- Enable annotation processing in IDE settings

---

## 📊 Health Checks

The application provides health check endpoints:

- **Overall Health:** http://localhost:8082/actuator/health
- **Application Info:** http://localhost:8082/actuator/info
- **Metrics:** http://localhost:8082/actuator/metrics

---

## 🔒 Database Schema

The application automatically creates the following table:

### `party` Table

| Column          | Type         | Constraints          |
|-----------------|--------------|---------------------|
| id              | BIGINT       | PRIMARY KEY, AUTO   |
| cust_id         | BIGINT       | NOT NULL            |
| cust_first_name | VARCHAR(100) | NOT NULL            |
| cust_last_name  | VARCHAR(100) | NOT NULL            |
| email_id        | VARCHAR(255) | NOT NULL            |
| phone_no        | VARCHAR(20)  | NOT NULL            |
| created_ts      | TIMESTAMP    | NOT NULL            |
| modified_ts     | TIMESTAMP    |                     |

---

## 📝 Validation Rules

- **custId:** Required, must be a valid number
- **custFirstName:** Required, cannot be empty
- **custLastName:** Required, cannot be empty
- **emailId:** Required, must be valid email format
- **phoneNo:** Required, 7-20 characters, can contain digits, spaces, hyphens, parentheses

---

## 🎬 Quick Start Guide for New Joiners

### Using the Automated Setup Script

1. **Open Terminal** (Mac/Linux) or **Command Prompt** (Windows)

2. **Run the setup script:**
   ```bash
   # Mac/Linux
   ./setup.sh
   
   # Windows
   setup.bat
   ```

3. **Follow the prompts:**
   - The script will check if you have Java, Maven, and Git installed
   - It will ask where you want to clone the project
   - Choose database setup option (local or remote)
   - Enter database connection details
   - The script will build and optionally start the application

4. **Access the application:**
   - Swagger UI: http://localhost:8082/swagger-ui/index.html
   - Health Check: http://localhost:8082/actuator/health

**Total time: ~5-10 minutes (including downloads)**

### What the Script Does

```
┌─────────────────────────────────────────┐
│  1. Check Prerequisites                 │
│     ✓ Java 17+                          │
│     ✓ Maven 3.6+                        │
│     ✓ PostgreSQL (optional)             │
│     ✓ Git                               │
├─────────────────────────────────────────┤
│  2. Clone Repository                    │
│     → Downloads code from GitHub        │
├─────────────────────────────────────────┤
│  3. Database Setup                      │
│     → Creates local DB OR               │
│     → Configures remote DB              │
├─────────────────────────────────────────┤
│  4. Configure Application               │
│     → Updates application.properties    │
│     → Sets database credentials         │
│     → Configures server port            │
├─────────────────────────────────────────┤
│  5. Build Project                       │
│     → Downloads dependencies            │
│     → Compiles code                     │
│     → Runs tests                        │
├─────────────────────────────────────────┤
│  6. Start Application (Optional)        │
│     → Launches the service              │
│     → Opens on port 8082                │
└─────────────────────────────────────────┘
```

---

## 🤝 Support

If you face any issues:

1. Check the [Troubleshooting](#troubleshooting) section
2. Review application logs in the console
3. Check database connectivity
4. Verify all configurations in `application.properties`

---

## 📄 License

Apache 2.0

---

## 👨‍💻 Developer Notes

### Stopping the Application

- If running via Maven: Press `Ctrl + C` in terminal
- If running via IDE: Click the Stop button
- If running as JAR: Press `Ctrl + C` or kill the process

### Logs Location

Console logs show real-time application activity. For debugging:
- Set logging level to DEBUG in `application.properties`
- Check logs for error messages

### Making Changes

After code changes:
1. Stop the application
2. Rebuild: `mvn clean install`
3. Restart the application

---

**Happy Coding! 🚀**


