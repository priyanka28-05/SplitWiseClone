# 💸 SplitWise Clone - Expense Sharing Application

A **Spring Boot 3** backend project that replicates core Splitwise functionality — allowing users to manage shared expenses, create groups, track balances, and settle payments.

---

## 🧩 Features

- 👥 **User Management** — Register or log in with Google OAuth2
- 👪 **Groups** — Create groups, add/remove members
- 💰 **Expenses** — Add shared expenses among group members
- ⚖️ **Settlements** — Track and settle payments between members
- 🔒 **Secure Login** — OAuth2 (Google) Authentication
- 🧭 **Swagger / OpenAPI** — Auto-generated API documentation
- 💾 **H2 Database** — In-memory relational database for quick setup

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-------------|
| **Backend** | Spring Boot 3 (Java 17) |
| **Security** | Spring Security + OAuth2 (Google Login) |
| **Database** | H2 (In-memory) |
| **ORM** | Hibernate / Spring Data JPA |
| **API Docs** | Swagger / OpenAPI |
| **Frontend (Optional)** | Thymeleaf (for login success page) |

---

## 🚀 Getting Started

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/yourusername/splitwise-clone.git
cd splitwise-clone
```

### 2️⃣ Database Configuration (H2)
The project uses an **in-memory H2** database by default — no installation required.

To view the H2 console:
- URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: *(leave blank)*

You can also seed initial users/groups using the `data.sql` file:
```sql
INSERT INTO user_entity (id, user_name, email, oauth_id) VALUES (1, 'Rami Sharma', 'rami.sharma@example.com', 'rami.sharma@example.com');
INSERT INTO user_entity (id, user_name, email, oauth_id) VALUES (2, 'Piyu Mehta', 'piyu.mehta@example.com', 'piyu.mehta@example.com');

INSERT INTO group_entity (id, group_name) VALUES (1, 'Weekend Trip');
INSERT INTO group_members (group_id, user_id) VALUES (1, 1);
INSERT INTO group_members (group_id, user_id) VALUES (1, 2);

INSERT INTO expense_entity (id, group_id, created_by_id, description, total_amount)
VALUES (1, 1, 1, 'Veggie Lunch', 75.50);

INSERT INTO expense_share_entity (expense_id, user_id, share_amount)
VALUES (1, 1, 37.75), (1, 2, 37.75);
```

---

## 🔐 Google OAuth2 Login Setup

1. Go to [Google Cloud Console](https://console.cloud.google.com/).
2. Create a new project and enable **Google Identity API**.
3. Under **APIs & Services → Credentials → Create Credentials → OAuth Client ID**:
    - Application type: **Web Application**
    - Authorized redirect URI:
      ```
      http://localhost:8080/login/oauth2/code/google
      ```
4. Copy the generated **Client ID** and **Client Secret**.

Then add them in your `application.yml` (or `application.properties`):

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: YOUR_CLIENT_ID
            client-secret: YOUR_CLIENT_SECRET
            scope:
              - email
              - profile
```

---

## ⚙️ Application Configuration

**File:** `src/main/resources/application.yml`

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driverClassName: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html
```

---

## 🧩 API Endpoints

| Module | Method | Endpoint | Description |
|---------|---------|----------|--------------|
| **User** | `POST` | `/api/users/register` | Register a new user |
|  | `GET` | `/api/users` | Get all users |
|  | `GET` | `/api/users/{userName}` | Get user by name |
| **Group** | `POST` | `/api/v1/groups` | Create a new group |
|  | `GET` | `/api/v1/groups` | Get all groups |
|  | `PUT` | `/api/v1/groups/{groupName}/add-members` | Add members to group |
|  | `PUT` | `/api/v1/groups/{groupName}/remove-members` | Remove members |
| **Expense** | `POST` | `/api/v1/expenses` | Add a new expense |
|  | `GET` | `/api/v1/expenses/user/{userName}` | Get expenses by user |
|  | `GET` | `/api/v1/expenses/group/{groupName}` | Get expenses by group |
| **Settlement** | `POST` | `/api/settlements/settle` | Record a payment settlement |
|  | `GET` | `/api/settlements/user/{userName}` | Get settlements by user |
|  | `GET` | `/api/settlements/group/{groupName}` | Get settlements by group |
| **OAuth2** | `GET` | `/login` | Google OAuth2 login page |
|  | `GET` | `/loginSuccess` | Redirect page after successful login |

---

## 🌐 Swagger UI

You can test all APIs using Swagger:

📄 **Swagger URL:**  
👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🎨 Thymeleaf Page (Optional)

After successful Google login, the app redirects to `/loginSuccess`.

**File:** `src/main/resources/templates/loginSuccess.html`
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Login Successful</title>
</head>
<body>
    <h1>Welcome, <span th:text="${userName}"></span>!</h1>
    <p>Email: <span th:text="${email}"></span></p>
    <a href="/swagger-ui.html">Go to API Docs</a>
</body>
</html>
```

---

## 🧠 Example Flow

1. Register users: Rami Sharma and Piyu Mehta
2. Create a group: `Weekend Trip`
3. Add both users to the group
4. Add an expense: `Veggie Lunch (₹75.50)`
5. Each owes ₹37.75
6. Record settlement: Rami → Piyu ₹37.75

---

## 🧾 License
This project is open source under the [MIT License](LICENSE).

---

**Developed by:** Priyanka Mansuriya  
💬 *SplitWise Clone using Spring Boot, OAuth2, and H2 Database.*
