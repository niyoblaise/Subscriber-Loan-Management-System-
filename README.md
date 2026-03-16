# Subscriber Loan Management System 💸

Management system for tracking subscriber loans, repayments, and debt status. Built with Spring Boot and PostgreSQL.

## 🚀 Key Features
- **Subscriber Management**: Create and track subscribers and their status.
- **Loan Processing**: Issue new loans and track outstanding debt.
- **Payment Tracking**: Record repayments and view transaction history.
- **Recovery & Reports**: Monitor loan recovery status and total exposure.

## 🛠 Tech Stack
- **Backend**: Java 17, Spring Boot 4.0.1
- **Database**: PostgreSQL
- **Mapping**: MapStruct for clean DTO conversions

## 📂 Project Structure
- `controller/`: REST endpoints for Subscribers, Loans, and Payments.
- `model/`: Core entities (Loan, Payment, Subscriber, etc.).
- `service/`: Business logic and debt calculations.
- `dto/`: Data Transfer Objects for API requests.

## 🏁 Getting Started

### Prerequisites
- JDK 17
- PostgreSQL 15+
- Maven

### Quick Setup
1. **Database**: Create a database named `slms2`.
2. **Config**: Update `src/main/resources/application.properties` with your PostgreSQL credentials.
3. **Run**:
   ```bash
   mvn spring-boot:run
   ```

## 👨‍💻 Author
**NIYONSHUTI Blaise**

