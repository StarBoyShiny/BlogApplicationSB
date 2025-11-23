# Blog Application (Spring Boot)

A full-featured blog application built with Spring Boot, providing a robust platform for content creation, management, and sharing.

## 🚀 Features

- **User Authentication & Authorization**
  - User registration and login
  - JWT-based authentication
  - Role-based access control (Admin, User)

- **Blog Post Management**
  - Create, read, update, and delete blog posts

- **Category & Tag System**
  - Organize posts by categories
  - Tag-based content discovery
  - Category and tag management

- **Search Functionality**
  - Search posts by title, content, or tags

- **User Profile Management**
  - Profile information update

## 🛠️ Tech Stack

- **Backend Framework:** Spring Boot 
- **Database:** MySQL
- **Security:** Spring Security with JWT
- **ORM:** Hibernate / JPA
- **Build Tool:** Maven

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ or PostgreSQL 12+
- Git

## ⚙️ Installation & Setup

### 1. Clone the repository

```bash
git clone https://github.com/StarBoyShiny/BlogApplicationSB.git
cd BlogApplicationSB
```

### 2. Configure the Database

Create a MySQL database:

```sql
CREATE DATABASE blog_application;
```

Update the `application.properties` or `application.yml` file located in `src/main/resources/`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/blog_application
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# JWT Secret Key
app.jwt.secret=your_secret_key_here
app.jwt.expiration=86400000
```

### 3. Build the application

```bash
mvn clean install
```

### 4. Run the application

```bash
mvn spring-boot:run
```

Or run the JAR file:

```bash
java -jar target/blog-application-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

⚠️ **Important:** Change these credentials immediately after first login!

## 📡 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login
- `POST /api/auth/logout` - User logout

### Posts
- `GET /api/posts` - Get all posts (with pagination)
- `GET /api/posts/{id}` - Get post by ID
- `POST /api/posts` - Create new post (Auth required)
- `PUT /api/posts/{id}` - Update post (Auth required)
- `DELETE /api/posts/{id}` - Delete post (Auth required)

### Categories
- `GET /api/categories` - Get all categories
- `POST /api/categories` - Create category (Admin only)
- `PUT /api/categories/{id}` - Update category (Admin only)
- `DELETE /api/categories/{id}` - Delete category (Admin only)

### Comments
- `GET /api/posts/{postId}/comments` - Get comments for a post
- `POST /api/posts/{postId}/comments` - Add comment (Auth required)
- `DELETE /api/comments/{id}` - Delete comment (Auth required)

### Users
- `GET /api/users/{id}` - Get user profile
- `PUT /api/users/{id}` - Update user profile (Auth required)

## 🗂️ Project Structure

```
BlogApplicationSB/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── blog/
│   │   │           ├── config/          # Configuration classes
│   │   │           ├── controller/      # REST Controllers
│   │   │           ├── dto/             # Data Transfer Objects
│   │   │           ├── entity/          # JPA Entities
│   │   │           ├── exception/       # Custom Exceptions
│   │   │           ├── repository/      # Data Access Layer
│   │   │           ├── security/        # Security Configuration
│   │   │           ├── service/         # Business Logic
│   │   │           └── BlogApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/                            # Unit & Integration Tests
├── pom.xml
└── README.md
```

## 👤 Author

**StarBoyShiny**

- GitHub: [@StarBoyShiny](https://github.com/StarBoyShiny)

## 🙏 Acknowledgments

- Spring Boot Documentation
- Spring Security
- Hibernate ORM
- MySQL Community


