# Library Management System

A **Library Management System** built with **Java** and **JavaFX** for an interactive GUI, and **MySQL** database for backend storage. This system efficiently manages library records like books, users, and transactions.

---

## Features

- **Book Management**: Add, edit, remove, and search for books.
- **User Management**: Manage library members efficiently.
- **Lending and Returning**: Handle book transactions with due-date tracking.
- **Reports**: Generate reports on borrowing history and popular books.
- **Real-time Search**: Quickly find books or users.

---

## Technologies Used

- **Frontend**: JavaFX
- **Backend**: Java
- **Database**: MySQL
- **Build Tool**: Maven 


---

## Prerequisites

Ensure you have the following installed:

1. **Java JDK**: Version 11 or later
2. **MySQL Server**: Version 8.0 or later
3. **JavaFX SDK**: Properly configured in your IDE
4. **Maven**: For dependency management (optional if using Gradle)

---

## Database Setup

1. **Create the Database**:
   ```sql
   CREATE DATABASE library_management;
Import Tables:

Import the library_management.sql file into the database.
Configure Database Connection: Update the db_config.properties file with your database credentials:

    db.url=jdbc:mysql://localhost:3306/library_management
    db.username=root
    db.password=your_password
    
**How to Run the Application**
Clone the repository:
git clone https://github.com/your-username/library-management-system.git
cd library-management-system
Build the project using your preferred tool:
