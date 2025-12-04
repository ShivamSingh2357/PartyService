# 🚀 Party Service - Quick Setup for New Joiners

This guide will help you set up the Party Service on your machine in **less than 10 minutes**!

---

## ⚡ Super Quick Setup (1 Command!)

### For Mac/Linux Users:

1. Open **Terminal**
2. Copy and paste this command:

```bash
cd ~ && curl -O https://raw.githubusercontent.com/dfh-swt-banking/sales-and-onboarding/main/BackendServices/PartyService/setup.sh && chmod +x setup.sh && ./setup.sh
```

3. Follow the on-screen prompts
4. Done! ✅

---

### For Windows Users:

1. Open **Command Prompt** or **PowerShell**
2. Download and run the setup script:

```batch
curl -O https://raw.githubusercontent.com/dfh-swt-banking/sales-and-onboarding/main/BackendServices/PartyService/setup.bat
setup.bat
```

3. Follow the on-screen prompts
4. Done! ✅

---

## 📋 What You'll Need

The script will check for these automatically:

- ✅ **Java 17 or higher** - [Download](https://www.oracle.com/java/technologies/downloads/)
- ✅ **Maven 3.6+** - Usually comes with Java
- ✅ **Git** - [Download](https://git-scm.com/downloads)
- ⚠️ **PostgreSQL** - Optional for local setup

---

## 🎯 What Happens During Setup

The script will ask you a few questions:

### 1️⃣ **Workspace Location**
```
Where do you want to save the project?
Default: /home/yourname/workspace (or C:\Users\yourname\workspace on Windows)
```
**Tip:** Just press Enter to use the default location

### 2️⃣ **Database Setup**
```
Choose database setup:
1. Local PostgreSQL (script will create database for you)
2. Remote/Cloud Database (like AWS RDS)
3. Use existing connection details
```
**Tip:** Choose option 1 if you have PostgreSQL installed locally

### 3️⃣ **Database Details**
```
Database name: [party_service]
Database user: [partyuser]
Database password: [partypass]
```
**Tip:** Just press Enter to use the default values

### 4️⃣ **Server Port**
```
Server port: [8082]
```
**Tip:** Press Enter to use default port 8082

### 5️⃣ **Build & Start**
```
The script will build the project (takes 2-5 minutes)
Do you want to start the application now? (y/n)
```
**Tip:** Type 'y' and press Enter to start immediately

---

## ✅ Verification

Once setup is complete, verify everything is working:

### Check 1: Application is Running
Open browser and visit: **http://localhost:8082/actuator/health**

You should see:
```json
{
  "status": "UP"
}
```

### Check 2: API Documentation
Open: **http://localhost:8082/swagger-ui/index.html**

You should see the interactive API documentation.

### Check 3: Test an API
Try the health check:
```bash
curl http://localhost:8082/actuator/health
```

---

## 🎉 You're All Set!

Your development environment is ready! Here's what you can do now:

### 📖 View API Documentation
- Swagger UI: http://localhost:8082/swagger-ui/index.html

### 🧪 Test the APIs
Try creating a party:
```bash
curl -X POST http://localhost:8082/v1/party \
  -H "Content-Type: application/json" \
  -d '{
    "payload": {
      "custId": 1,
      "custFirstName": "Test",
      "custLastName": "User",
      "emailId": "test@example.com",
      "phoneNo": "1234567890"
    }
  }'
```

### 📂 Navigate to Project
Your project is located at:
- **Mac/Linux:** `~/workspace/sales-and-onboarding/BackendServices/PartyService`
- **Windows:** `C:\Users\YourName\workspace\sales-and-onboarding\BackendServices\PartyService`

### 💻 Open in IDE
Open the project in your favorite IDE:
- IntelliJ IDEA (recommended)
- Eclipse
- VS Code

---

## 🆘 Troubleshooting

### Script says "Java not found"
1. Install Java 17: https://www.oracle.com/java/technologies/downloads/
2. Verify: `java -version`
3. Run the script again

### Script says "Maven not found"
1. Maven usually comes with Java
2. If needed, download from: https://maven.apache.org/download.cgi
3. Add to PATH and restart terminal

### Database connection failed
1. Make sure PostgreSQL is running
2. Check if database credentials are correct
3. Try using remote database option instead

### Port 8082 already in use
1. Stop any application using port 8082, OR
2. Use a different port when the script asks

### Build failed
1. Make sure you have internet connection (to download dependencies)
2. Try again: `mvn clean install -DskipTests`
3. Check if you have Java 17+

---

## 📞 Need Help?

1. Check the main [README.md](./README.md) for detailed documentation
2. Ask your team lead or mentor
3. Check the troubleshooting section in README.md

---

## 🎓 Next Steps

Now that your environment is set up:

1. **Explore the Code**
   - Start with `PartyController.java`
   - Check out `PartyService.java` for business logic
   - Look at `PartyEntity.java` for database model

2. **Try Making Changes**
   - Add a new field to PartyRequest
   - Create a new API endpoint
   - Run the application and test your changes

3. **Read the Documentation**
   - Check `README.md` for complete documentation
   - Understand the project structure
   - Learn about the APIs

4. **Start Coding!** 🚀

---

**Happy Coding! Welcome to the team! 🎉**

