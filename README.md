# Personal Finance Manager (PFM) Backend

A robust, secure, and feature-rich **Personal Finance Manager REST API** built using **Java 21** and **Spring Boot**. This backend serves as the foundation for tracking personal transactions, managing custom categories, establishing savings goals, and generating comprehensive monthly/yearly financial reports.

---

## 🚀 Key Features

*   **Secure Authentication**: Fully session-based authentication using **Spring Security** with secure session tracking (`HttpSession`) and **BCrypt password hashing** for robust credential security.
*   **Smart Category Management**: 
    *   Pre-seeded default system categories (e.g., *Salary*, *Food*, *Rent*, *Transportation*, *Entertainment*, *Healthcare*, *Utilities*) generated automatically on startup.
    *   Support for custom user-defined categories.
    *   Built-in security rules preventing users from modifying default categories or accessing categories owned by other users.
*   **Transaction Management**: 
    *   Record and update income/expense transactions with descriptions, amounts, and dates.
    *   Flexible filtering capabilities: view transactions by date range, specific categories, or both.
*   **Interactive Savings Goals**: 
    *   Establish custom savings goals with target dates and target amounts.
    *   Automatic progress tracking (current progress, remaining amount, and percentage completed) computed dynamically from the user's income vs. expense transactions since the goal's start date.
*   **Financial Reports & Analytics**:
    *   **Monthly Report**: Aggregated breakdown of income and expenses categorized by name, plus net savings calculation for any specific month.
    *   **Yearly Report**: Annual financial overview summarizing all category-wise income, expenses, and net savings.
*   **Robust Input Validation**: Strict validation rules for all requests (e.g., checking for valid email formats, password complexity, positive amounts, and past/future date rules).

---

## 🛠️ Technology Stack

*   **Core Language**: Java 21
*   **Framework**: Spring Boot 3.5.x (Web, Security, Validation, Actuator)
*   **Data Access**: Spring Data JPA & Hibernate
*   **Database**: H2 Database (In-Memory for rapid setup/testing)
*   **Utilities & Quality**: Lombok (for clean, boilerplate-free code), Jakarta Validation API

---

## 📂 Project Architecture & Components

The application follows standard Spring Boot clean-layered architecture:

```
src/main/java/com/dhananjay/pfm_backend
├── config                      # Application Configurations (Security, Data Seeder)
├── controller                  # REST APIs mapping HTTP requests to Service calls
├── dto                         # Data Transfer Objects
│   ├── request                 # Incoming JSON Request Payloads & Validation Rules
│   └── response                # Outgoing JSON Response Structure
├── entity                      # JPA Database Entities
├── enums                       # Enums defining Transaction Types (INCOME/EXPENSE)
├── exception                   # Global Exception Handling & Custom Exceptions
├── repository                  # Spring Data JPA Database Repository interfaces
└── service                     # Service layer implementing core business logic
```

---

## 🔌 API Documentation

### 🔒 Authentication (`/api/auth`)

| Method | Endpoint | Description | Request Body |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/auth/register` | Register a new user account | `RegisterRequest` (JSON) |
| **POST** | `/api/auth/login` | Login and start a secure session | `LoginRequest` (JSON) |
| **POST** | `/api/auth/logout` | End current session and logout | *None* |
| **GET** | `/api/auth/me` | Fetch currently logged-in user profile | *Session Header Required* |

### 🏷️ Categories (`/api/categories`)

| Method | Endpoint | Description | Request Body |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/categories` | Create a new custom category | `CategoryRequest` (JSON) |
| **GET** | `/api/categories` | Get all system & user-owned categories | *None* |
| **DELETE** | `/api/categories/{id}` | Delete a custom category | *None* |

### 💸 Transactions (`/api/transactions`)

| Method | Endpoint | Description | Query Parameters / Request Body |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/transactions` | Create a new transaction | `TransactionRequest` (JSON) |
| **GET** | `/api/transactions` | Fetch all user transactions (with filters) | `startDate`, `endDate`, `categoryId` (Optional) |
| **PUT** | `/api/transactions/{id}`| Update an existing transaction | `UpdateTransactionRequest` (JSON) |
| **DELETE** | `/api/transactions/{id}`| Delete a transaction | *None* |

### 🎯 Savings Goals (`/api/goals`)

| Method | Endpoint | Description | Request Body |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/goals` | Create a new savings goal | `GoalRequest` (JSON) |
| **GET** | `/api/goals` | List all savings goals with calculated progress | *None* |
| **GET** | `/api/goals/{id}` | Get details & live status of a specific goal | *None* |
| **PUT** | `/api/goals/{id}` | Update savings goal parameters | `UpdateGoalRequest` (JSON) |
| **DELETE** | `/api/goals/{id}` | Delete a savings goal | *None* |

### 📊 Reports (`/api/reports`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **GET** | `/api/reports/monthly/{year}/{month}` | Get categorized income/expenses & net savings for the month |
| **GET** | `/api/reports/yearly/{year}` | Get yearly aggregated financial summary |

---

## ⚙️ How to Get Started

### 📋 Prerequisites
*   **Java Development Kit (JDK)**: Version 21
*   **Maven**: 3.8+ (or use the built-in Maven Wrapper `./mvnw`)

### 🏃 Running the Application
1. Clone or copy the project codebase into your directory.
2. Open your terminal at the project root directory.
3. Run the following command to start the Spring Boot server:

**For Windows (PowerShell/CMD):**
```powershell
.\mvnw.cmd spring-boot:run
```

**For Linux/macOS:**
```bash
chmod +x mvnw
./mvnw spring-boot:run
```

4. The server will start up on **`http://localhost:8080`**.

### 🗃️ Database Console Access
The project uses an in-memory H2 database that automatically seeds default configurations on startup. To inspect data visually:
*   **URL**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
*   **JDBC URL**: `jdbc:h2:mem:pfmdb`
*   **Username**: `sa`
*   **Password**: *(leave empty)*

---

## 🛡️ Input Validation Specifications

To ensure high data integrity, the REST APIs enforce validation rules:
*   **User Registration**:
    *   `username`/`email`: Must be a valid email format.
    *   `password`: Must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one number.
    *   `phoneNumber`: Must be exactly 10 digits.
*   **Transactions**:
    *   `amount`: Must be positive.
    *   `date`: Cannot be in the future (`@PastOrPresent`).
*   **Goals**:
    *   `targetAmount`: Must be positive.
    *   `targetDate`: Must be in the future (`@Future`).
