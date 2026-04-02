# 🎯 Reward Points Calculation API

## 📌 Overview
This project is a **Spring Boot REST API** that calculates reward points for customers based on transaction amounts.

### 🧮 Reward Rules
- 🟢 2 points for every ₹1 spent above ₹100  
- 🟡 1 point for every ₹1 spent between ₹50 and ₹100  
- 🔴 No points for amount ≤ ₹50  

---

## 🚀 Tech Stack
- Java 17
- Spring Boot
- Spring Web
- Maven

---

## 📂 Project Structure


com.charter.rewardPoints
│── controller → Handles API requests
│── service → Business logic for reward calculation
│── dto → Response objects (RewardResponse)


---

## ▶️ How to Run the Project

```bash
mvn clean install
mvn spring-boot:run

Application runs on:

http://localhost:8080
🔗 API Endpoint
✅ Calculate Reward Points
GET /api/v1/rewards/calculate
📥 Request Parameters (Optional)
Parameter	Type	Format
startDate	LocalDate	yyyy-MM-dd
endDate	LocalDate	yyyy-MM-dd
⚠️ Important Configuration

Ensure date parsing works correctly:

@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
📬 Sample API Requests (Postman)
✅ 1. Success – With Date Range
GET http://localhost:8080/api/v1/rewards/calculate?startDate=2024-01-01&endDate=2024-03-01
✅ 2. Success – Default (Last 3 Months)
GET http://localhost:8080/api/v1/rewards/calculate
❌ 3. Failure – Invalid Date Range
GET http://localhost:8080/api/v1/rewards/calculate?startDate=2024-03-01&endDate=2024-01-01
❌ 4. Failure – More Than 3 Months Range
GET http://localhost:8080/api/v1/rewards/calculate?startDate=2024-01-01&endDate=2024-05-01
❌ 5. Failure – No Data Found
GET http://localhost:8080/api/v1/rewards/calculate?startDate=2020-01-01&endDate=2020-01-31
📤 Sample Response
[
  {
    "customerId": 1,
    "monthlyPoints": {
      "2024-01": 120
    },
    "totalPoints": 120
  }
]
⚠️ Error Response Example
{
  "message": "Start date cannot be after end date"
}
📸 Screenshots

Screenshots are available in the /document folder:

postman-success.png
postman-default.png
postman-invalid-date.png
postman-no-data.png
🧪 Test Scenarios Covered

✔ Valid date range
✔ Default 3 months calculation
✔ Invalid date range validation
✔ Date range > 3 months validation
✔ No transactions scenario

💡 Key Features
Clean Controller → Service → DTO architecture
Optional date filtering
Monthly and total reward calculation
Proper validation and error handling
Lightweight and easy to run
👨‍💻 Author

Mohd Asgar Khan
Java Spring Boot Developer

📌 Notes
Date format must be: yyyy-MM-dd
API works with or without date parameters
Default behavior calculates rewards for last 3 months
