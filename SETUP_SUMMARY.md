# Party Service - Setup Summary

## 📋 Setup Files Created

### 1. **setup.sh** (Mac/Linux)
Automated setup script for Unix-based systems.

**How to use:**
```bash
chmod +x setup.sh
./setup.sh
```

### 2. **setup.bat** (Windows)
Automated setup script for Windows systems.

**How to use:**
```batch
setup.bat
```

### 3. **README.md**
Complete documentation with both automated and manual setup instructions.

### 4. **QUICK_SETUP.md**
Quick reference guide for new joiners - includes 1-command setup.

---

## 🎯 What the Scripts Do

### Automatic Tasks (No Manual Work Required)

1. ✅ **Prerequisites Check**
   - Verifies Java 17+ is installed
   - Verifies Maven is installed
   - Checks for Git
   - Checks for PostgreSQL (optional)

2. ✅ **Repository Cloning**
   - Clones from: https://github.com/dfh-swt-banking/sales-and-onboarding
   - Navigates to: BackendServices/PartyService
   - Handles existing repositories (pulls latest)

3. ✅ **Database Setup**
   - Option 1: Creates local PostgreSQL database
   - Option 2: Configures remote database connection
   - Option 3: Uses existing database details
   - Creates database user and grants permissions

4. ✅ **Application Configuration**
   - Backs up original application.properties
   - Updates database URL
   - Updates database credentials
   - Configures server port

5. ✅ **Project Build**
   - Runs `mvn clean install`
   - Downloads all dependencies
   - Compiles the code
   - Shows build progress

6. ✅ **Application Startup** (Optional)
   - Starts the Spring Boot application
   - Shows access URLs (Swagger, Health Check)
   - Displays useful commands

---

## 🚀 Usage Examples

### Quick Start for New Joiner

**Scenario:** Fresh laptop, nothing installed

1. Install Java 17: https://www.oracle.com/java/technologies/downloads/
2. Install Git: https://git-scm.com/downloads
3. Run setup script: `./setup.sh`
4. Answer prompts (or use defaults)
5. Application is ready!

**Total Time:** 10-15 minutes (including downloads)

---

### Developer with Local Database

**Scenario:** Developer has PostgreSQL installed

```bash
./setup.sh

# When prompted:
# - Workspace: [Press Enter for default]
# - Database setup: Choose option 1 (Local PostgreSQL)
# - Database name: party_service [Press Enter]
# - Database user: partyuser [Press Enter]
# - Database password: partypass [Press Enter]
# - Server port: 8082 [Press Enter]
# - Start now? y [Press Enter]
```

**Result:** Application running on http://localhost:8082

---

### Developer with Cloud Database

**Scenario:** Developer wants to use AWS RDS

```bash
./setup.sh

# When prompted:
# - Workspace: [Press Enter for default]
# - Database setup: Choose option 2 (Remote Database)
# - Database host: your-db.rds.amazonaws.com
# - Database port: 5432
# - Database name: party_service
# - Database username: admin
# - Database password: yourpassword
# - Server port: 8082 [Press Enter]
# - Start now? y [Press Enter]
```

**Result:** Application connected to cloud database

---

## 📁 What Gets Created

After running the setup script:

```
~/workspace/                              (or custom location)
└── sales-and-onboarding/
    └── BackendServices/
        └── PartyService/
            ├── src/
            │   └── main/
            │       ├── java/
            │       └── resources/
            │           ├── application.properties          (configured)
            │           └── application.properties.backup   (original backup)
            ├── target/                                     (compiled code)
            ├── pom.xml
            ├── setup.sh
            ├── setup.bat
            ├── README.md
            ├── QUICK_SETUP.md
            └── SETUP_SUMMARY.md
```

---

## 🛡️ Script Safety Features

### Backup & Recovery
- ✅ Backs up `application.properties` before modifying
- ✅ Can restore from `.backup` file if needed

### Error Handling
- ✅ Checks prerequisites before proceeding
- ✅ Validates inputs
- ✅ Stops on build errors
- ✅ Shows clear error messages

### Non-Destructive
- ✅ Doesn't delete existing data
- ✅ Asks for confirmation before overwriting
- ✅ Pulls latest code if repo exists (doesn't re-clone)

---

## 🎓 For Team Leads / Mentors

### Onboarding New Developers

**Share this with new joiners:**

1. **Send them QUICK_SETUP.md**
   - Contains 1-command setup
   - Clear step-by-step instructions
   - Troubleshooting guide

2. **Prerequisites checklist:**
   ```
   ☐ Java 17+ installed
   ☐ Git installed
   ☐ Internet connection
   ☐ Access to repository
   ☐ Database details (if using remote)
   ```

3. **Expected timeline:**
   - Java/Git installation: 10-15 minutes
   - Script execution: 5-10 minutes
   - **Total: ~20-25 minutes**

### Customization

To customize the script for your team:

1. **Change default database name:**
   ```bash
   # In setup.sh, modify:
   DB_NAME="your_db_name"
   ```

2. **Change default port:**
   ```bash
   # In setup.sh, modify:
   SERVER_PORT=${SERVER_PORT:-8083}  # Changed from 8082
   ```

3. **Pre-configure database:**
   - Edit `application.properties` in the repository
   - Scripts will use those as defaults

---

## 🔧 Advanced Usage

### Run Setup Without Building

```bash
./setup.sh
# When it asks to start application, say 'n'
# Then manually build: mvn clean install -DskipTests
```

### Setup Multiple Instances

```bash
# Instance 1 on port 8082
./setup.sh
# Choose workspace: ~/workspace/party-1
# Choose port: 8082

# Instance 2 on port 8083
./setup.sh
# Choose workspace: ~/workspace/party-2
# Choose port: 8083
```

### Silent Mode (Future Enhancement)

Could add environment variables for silent mode:
```bash
export DB_HOST="localhost"
export DB_NAME="party_service"
export DB_USER="postgres"
export DB_PASSWORD="postgres"
export SERVER_PORT="8082"
./setup.sh --silent
```

---

## 📊 Success Metrics

After setup completes, verify:

| Check | URL | Expected Result |
|-------|-----|-----------------|
| Health Check | http://localhost:8082/actuator/health | `{"status":"UP"}` |
| Swagger UI | http://localhost:8082/swagger-ui/index.html | API docs page loads |
| Info Endpoint | http://localhost:8082/actuator/info | App info displayed |
| Create Party | POST /v1/party | Returns 200 with party data |

---

## 🐛 Common Issues & Solutions

### Issue: "Java not found"
**Solution:**
```bash
# Install Java 17
# Mac: brew install openjdk@17
# Windows: Download from Oracle
# Verify: java -version
```

### Issue: "Port already in use"
**Solution:**
```bash
# Find process using port 8082
lsof -i :8082  # Mac/Linux
netstat -ano | findstr :8082  # Windows

# Kill process or use different port
```

### Issue: "Cannot connect to database"
**Solution:**
- Check PostgreSQL is running
- Verify credentials in application.properties
- Test connection: `psql -h localhost -U postgres`

### Issue: "Build failed"
**Solution:**
```bash
# Clear Maven cache and rebuild
rm -rf ~/.m2/repository
mvn clean install -U
```

---

## 📞 Support & Feedback

- For script issues: Check README.md troubleshooting section
- For application issues: Check application logs
- For database issues: Check PostgreSQL logs

---

## 🎉 Success!

If you see this at the end:

```
========================================
Setup Complete! 🎉
========================================

Project Location: /path/to/PartyService
Database: jdbc:postgresql://localhost:5432/party_service
Server Port: 8082

Swagger UI:    http://localhost:8082/swagger-ui/index.html
Health Check:  http://localhost:8082/actuator/health

You're all set! Happy Coding! 🚀
```

**You're ready to start developing!** 🎊

---

**Last Updated:** December 2025

