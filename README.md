# ⏱️ Freelance Hours Tracker

A backend API for tracking work hours across clients and projects.

This application allows users to manage clients, projects, and time entries to keep track of freelance work.

---

## 🚀 Tech Stack
- Java
- Spring Boot
- Spring Data JPA
- H2 Database (testing)
- JUnit & Mockito

---

## 📌 Features

- Manage clients
- Manage projects linked to clients
- Track time entries (hours worked)
- Filter time entries by date range
- RESTful API structure

---

## 🔗 Example Endpoints

### Get all time entries
GET /api/time-entries

### Create a time entry
POST /api/time-entries

### Get entries between dates
GET /api/time-entries?start=YYYY-MM-DD&end=YYYY-MM-DD

---

## ⚙️ How to Run

1. Clone the repository
2. Open in IntelliJ IDEA
3. Run the Spring Boot application
4. Use Postman to test endpoints

---

## 🧪 Testing

- Unit tests with JUnit & Mockito
- Controller tests using MockMvc
- Focus on validation and edge cases

---

## 🧠 What I Learned

- Designing a structured backend with service and controller layers
- Working with relationships between entities (Client, Project, TimeEntry)
- Writing testable code and unit tests
- Handling validation and error responses
- Building practical APIs for real-world use cases

---

## 📌 Author

- GitHub: https://github.com/rooomaisa