# Full-Stack E-Commerce Web Application

This repository contains the source code for a complete e-commerce web application built with a Spring Boot backend, React frontend, and MySQL database.

## Technology Stack

- **Backend**: Spring Boot, Spring Security (JWT), Spring Data JPA, Maven
- **Frontend**: React (Coming in Phase 2)
- **Database**: MySQL
- **API**: RESTful APIs

## 1. Backend Setup

Follow these steps to get the backend server running locally.

### Prerequisites

- Java 17 or later
- Apache Maven
- MySQL 8.0 or later
- An API client like Postman

### Step 1: Database Configuration

1. Open your MySQL client (e.g., MySQL Workbench, DBeaver).

2. Create the database using the following command. This command uses the recommended utf8mb4 character set to support a wide range of characters.

```sql
CREATE SCHEMA `ecommerce_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
```

The application will automatically create the necessary tables (users, products, etc.) when it starts for the first time.

### Step 2: Configure Application Properties

1. Navigate to `src/main/resources/application.properties`.

2. Update the database connection details with your local MySQL credentials.

```properties
# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password # <-- IMPORTANT: Change this
```

3. Ensure the JWT secret is set to a strong, private value.

```properties
# JWT Secret Configuration
jwt.secret=replace-this-with-a-strong-secret-key
```

### Step 3: Load Initial Data

The project is configured to load sample data from `src/main/resources/data.sql` upon startup. This will populate your database with:

- **1 Admin User:**
  - email: `admin@example.com`
  - password: `admin123`

- **2 Categories:** "Electronics" and "Books"

- **3 Products:** "Laptop Pro", "Smartphone X", and "The Great Novel"

**Troubleshooting:** If the data does not load, you can force it by adding the following line to `application.properties` and restarting the server:

```properties
spring.sql.init.mode=always
```

### Step 4: Run the Backend

1. Open a terminal in the project's root directory.

2. Run the application using Maven.

```bash
mvn spring-boot:run
```

The backend server will start on `http://localhost:8080`.

## 2. API Testing Guide (Postman)

This guide walks through testing all API endpoints in a logical order.

### Postman Initial Setup

1. **Create an Environment:** In Postman, go to Environments > Create Environment. Name it "E-commerce Backend".

2. **Add a Variable:** Add a new variable:
   - **VARIABLE:** `baseURL`
   - **INITIAL VALUE:** `http://localhost:8080`

3. **Select Environment:** In the top-right corner of Postman, select your "E-commerce Backend" environment.

### Testing Flow

#### Step 1: Authentication (`/api/auth`)

We will register a new customer, log in as that customer, and then log in as the pre-loaded admin.

**A. Register a New Customer**

- **Method:** POST
- **URL:** `{{baseURL}}/api/auth/register`
- **Body (raw, JSON):**

```json
{
    "email": "customer@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe"
}
```

- **Expected Response (200 OK):** `{ "message": "User registered successfully!" }`

**B. Login as Customer**

- **Method:** POST
- **URL:** `{{baseURL}}/api/auth/login`
- **Body (raw, JSON):**

```json
{
    "email": "customer@example.com",
    "password": "password123"
}
```

- **Action:** From the response, copy the token value. This is your **Customer Token**.

**C. Login as Admin**

- **Method:** POST
- **URL:** `{{baseURL}}/api/auth/login`
- **Body (raw, JSON):**

```json
{
    "email": "admin@example.com",
    "password": "admin123"
}
```

- **Action:** From the response, copy the token value. This is your **Admin Token**.

#### Step 2: Public Endpoints (No Auth Required)

**A. Get All Products**

- **Method:** GET
- **URL:** `{{baseURL}}/api/products`

**B. Get All Categories**

- **Method:** GET
- **URL:** `{{baseURL}}/api/categories`

#### Step 3: Shopping Cart (Customer Token Required)

For all requests in this step, set Authorization to Bearer Token and use the **Customer Token**.

**A. Add Product to Cart**

- **Method:** POST
- **URL:** `{{baseURL}}/api/cart/add`
- **Body (raw, JSON):**

```json
{
    "productId": 1,
    "quantity": 2
}
```

**B. Get User's Cart**

- **Method:** GET
- **URL:** `{{baseURL}}/api/cart`

**C. Remove Product from Cart**

- **Method:** DELETE
- **URL:** `{{baseURL}}/api/cart/remove/1`

#### Step 4: Placing an Order (Customer Token Required)

First, add an item to your cart again.

**A. Create an Order**

- **Method:** POST
- **URL:** `{{baseURL}}/api/orders`
- **Authorization:** Bearer Token (Customer)
- **Body (raw, JSON):**

```json
{
    "shippingAddress": "123 Main St, Anytown, USA"
}
```

**B. Get User's Order History**

- **Method:** GET
- **URL:** `{{baseURL}}/api/orders`
- **Authorization:** Bearer Token (Customer)

#### Step 5: Admin Management (Admin Token Required)

For all requests in this step, set Authorization to Bearer Token and use the **Admin Token**.

**A. Add a New Product**

- **Method:** POST
- **URL:** `{{baseURL}}/api/products`
- **Body (raw, JSON):**

```json
{
    "name": "Wireless Mouse",
    "description": "A reliable wireless mouse.",
    "price": 25.00,
    "stock": 300,
    "category": {
        "id": 1
    },
    "imageUrl": "/images/mouse.jpg"
}
```

**B. Update Order Status**

- **Method:** PUT
- **URL:** `{{baseURL}}/api/orders/1/status` (Use an ID from the customer's order history)
- **Body (raw, JSON):**

```json
{
    "status": "SHIPPED"
}
```

## 3. Frontend Setup (Coming Soon)

This section will be updated once the React frontend is developed.

### Prerequisites

- Node.js and npm

### Running the Frontend

```bash
# Install dependencies
npm install

# Start the development server
npm start
```

## API Endpoints Summary

| Endpoint | Method | Auth Required | Description |
|----------|---------|---------------|-------------|
| `/api/auth/register` | POST | No | Register new user |
| `/api/auth/login` | POST | No | User login |
| `/api/products` | GET | No | Get all products |
| `/api/products` | POST | Admin | Add new product |
| `/api/categories` | GET | No | Get all categories |
| `/api/cart` | GET | Customer | Get user's cart |
| `/api/cart/add` | POST | Customer | Add item to cart |
| `/api/cart/remove/{id}` | DELETE | Customer | Remove item from cart |
| `/api/orders` | GET | Customer | Get order history |
| `/api/orders` | POST | Customer | Create new order |
| `/api/orders/{id}/status` | PUT | Admin | Update order status |

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.