# 🍔 FoodieExpress - Online Food Delivery Web Application

FoodieExpress is a dynamic food ordering web application developed using Java, JSP, Servlets, JDBC, and MySQL following the MVC architecture. The application allows users to browse restaurants, explore menus, add food items to their cart, and place orders through an interactive and responsive interface.

---

## 📌 Project Overview

FoodieExpress simulates an online food ordering platform similar to Swiggy and Zomato. The application provides restaurant listings, menu browsing, user authentication, shopping cart functionality, checkout, and order management.

---

## 🚀 Features

### 🍽 Restaurant Module
- Display all restaurants
- Restaurant image
- Restaurant description
- Cuisine type
- Delivery time
- Restaurant rating
- Price range
- Opening & Closing timings
- Browse menu for each restaurant

### 🍕 Menu Module
- Display menu based on selected restaurant
- Food image
- Food name
- Description
- Price
- Category
- Availability status
- Add to Cart functionality

### 👤 User Module
- User Registration
- User Login
- Session Management
- Logout

### 🛒 Cart Module
- Add food items
- Remove food items
- Update quantity
- View subtotal
- Grand total calculation
- Clear cart

### 💳 Checkout Module
- User information
- Delivery address
- Order summary
- Total bill
- Place order

### 📦 Order Module
- Save orders
- Save ordered items
- Order confirmation
- Order history

---

# 🛠 Technology Stack

## Frontend
- HTML5
- CSS3
- JavaScript
- JSP

## Backend
- Java
- Servlet
- JDBC

## Database
- MySQL

## IDE
- Eclipse IDE

## Server
- Apache Tomcat 10

---

# 📂 Project Structure

```
FoodieExpress
│
├── src
│   ├── com.model
│   ├── com.DAO
│   ├── com.DAOIMPL
│   ├── com.servlets
│   └── com.util
│
├── WebContent
│   ├── css
│   ├── images
│   │   ├── restaurants
│   │   └── menu
│   ├── view
│   ├── index.jsp
│   └── WEB-INF
│
└── database
```

---

# 🗄 Database Tables

## User

- User ID
- Username
- Email
- Password
- Address
- Role

---

## Restaurant

- Restaurant ID
- Name
- Cuisine Type
- Delivery Time
- Address
- Rating
- Active Status
- Image Path
- Description
- Price Range
- Opening Time
- Closing Time

---

## Menu

- Menu ID
- Restaurant ID
- Item Name
- Description
- Price
- Category
- Availability
- Image Path

---

## Order Table

- Order ID
- User ID
- Restaurant ID
- Total Amount
- Order Date
- Status

---

## Order Item

- Order Item ID
- Order ID
- Menu ID
- Quantity
- Price

---

# ⚙ Architecture

The project follows the **MVC (Model View Controller)** architecture.

```
Browser
   │
   ▼
JSP Pages
   │
   ▼
Servlets
   │
   ▼
DAO Layer
   │
   ▼
DAO Implementation
   │
   ▼
MySQL Database
```

---

# 🔄 Application Flow

```
Restaurant Page

        ↓

Browse Menu

        ↓

Menu Page

        ↓

Login (If Required)

        ↓

Add To Cart

        ↓

Cart

        ↓

Checkout

        ↓

Place Order

        ↓

Order Success

        ↓

Order History
```

---

# 📸 Modules

### Home / Restaurant Page
Displays all available restaurants with complete details.

### Menu Page
Displays all food items for the selected restaurant.

### Login
Allows registered users to authenticate.

### Cart
Stores selected food items and calculates totals.

### Checkout
Displays bill summary and confirms order.

### Order History
Displays all previously placed orders.

---

# 💡 Key Concepts Used

- Object-Oriented Programming
- MVC Architecture
- JDBC Connectivity
- Session Management
- CRUD Operations
- Collections Framework
- Exception Handling
- SQL Joins
- Foreign Key Relationships
- Dynamic JSP Pages
- Servlet Request Handling

---

# 🎯 Learning Outcomes

- Java Web Development
- JSP & Servlet Programming
- JDBC Integration
- MySQL Database Design
- MVC Design Pattern
- Session Management
- CRUD Operations
- Responsive Web Design

---

# 🔮 Future Enhancements

- Online Payment Gateway
- Google Maps Integration
- Live Order Tracking
- Restaurant Search & Filters
- Wishlist / Favorites
- Customer Reviews & Ratings
- Coupon System
- Admin Dashboard
- Restaurant Owner Dashboard
- Email Notifications
- Mobile Responsive UI
- Dark Mode
- AI-based Food Recommendation

---

# 📷 Screens

- Restaurant Page
- Menu Page
- Login Page
- Cart Page
- Checkout Page
- Order Success Page
- Order History Page

---

# ▶️ How to Run

1. Clone the repository.

```
git clone <repository-url>
```

2. Import the project into Eclipse.

3. Configure Apache Tomcat 10.

4. Create the MySQL database.

5. Execute the SQL scripts.

6. Update database credentials in `DBConnection.java`.

7. Run the project on Tomcat.

8. Open your browser:

```
http://localhost:8080/FoodieExpress/
```

---

# 👨‍💻 Developed By

**Rakesh Balati**

Computer Science Engineering

Java Full Stack Developer

---

# 📄 License

This project is developed for educational and learning purposes.
