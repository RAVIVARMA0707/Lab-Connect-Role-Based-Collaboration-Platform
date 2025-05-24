# Lab Connect – Role-Based Collaboration Platform (Backend) 🔗👥

A robust backend system designed for managing lab-based student collaborations, attendance, events, and forums in an academic setting. This backend supports role-based access control for **Admins**, **Faculty**, **Students**, and **Industry Partners**.

---

## 🚀 Features

- **Role-based authentication** (Google OAuth support)
- **Student lab registration** with preference handling and approval flow
- **Event scheduling** & automatic attendance generation
- **Daily attendance marking** (forenoon/afternoon)
- **Dedicated Discussion Forum** with posts and comments per lab category
- **Lab change request** workflow with approval tracking
- RESTful APIs following clean architecture

---

## 🧱 Tech Stack

- **Java 21**
- **Spring Boot 3.3.7**
- **Spring Data JPA**
- **Spring Security Oauth2**
- **Spring Mail Sender**
- **MySQL**
- **Hibernate**
- **Google Authentication**

---

## 🗃️ Database Schema

Here is the visual representation of the database schema used in this project:
![diagram-export-5-24-2025-10_08_20-PM](https://github.com/user-attachments/assets/39496bfa-15a5-4be4-88f9-2b35d155d6a3)


---
# 📡 REST API Endpoints
## 🔐 Admin APIs

| Method | Endpoint                    | Description                    |
| ------ | --------------------------- | ------------------------------ |
| POST   | `/pic/labs/add`             | Add new lab                    |
| GET    | `/pic/labs/get`             | Get all labs                   |
| GET    | `/pic/labs/get/{id}`        | Get lab by ID                  |
| GET    | `/pic/labs/{id}/students`   | Get students in a lab          |
| PUT    | `/pic/labs/update/{id}`     | Update lab                     |
| DELETE | `/pic/labs/delete/{id}`     | Delete lab                     |
| GET    | `/pic/requests/{facultyId}` | View lab registration requests |

## 🎓 Student APIs

| Method | Endpoint                                         | Description                      |
| ------ | ------------------------------------------------ | -------------------------------- |
| POST   | `/pic/students/add`                              | Add new student                  |
| GET    | `/pic/students/get`                              | Get all students                 |
| GET    | `/pic/students/{studentId}`                      | Get student by ID                |
| POST   | `/pic/students/registerLabs/{studentId}`         | Register labs (with preferences) |
| POST   | `/lab-change/request/{studentId}/{desiredLabId}` | Request lab change               |

## 👨‍🏫 Faculty APIs
| Method | Endpoint                                      | Description                        |
| ------ | --------------------------------------------- | ---------------------------------- |
| POST   | `/pic/faculty`                                | Add faculty                        |
| GET    | `/pic/faculty/pic/faculty`                    | Get all faculty                    |
| GET    | `/pic/faculty/{id}`                           | Get faculty by ID                  |
| PUT    | `/pic/faculty/{id}`                           | Update faculty                     |
| DELETE | `/pic/faculty/{id}`                           | Delete faculty                     |
| POST   | `/pic/faculty/approve/{studentId}`            | Approve student's lab registration |
| POST   | `/pic/faculty/reject/{studentId}`             | Reject student's lab registration  |
| GET    | `/pic/faculty/{facultyId}/requests`           | View lab registration requests     |
| GET    | `/lab-change/faculty/{facultyId}/requests`    | View lab change requests           |
| POST   | `/lab-change/approve/{requestId}/{facultyId}` | Approve lab change request         |
| POST   | `/lab-change/reject/{requestId}`              | Reject lab change request          |

## 🧪 Industrial Partner APIs
| Method | Endpoint                 | Description               |
| ------ | ------------------------ | ------------------------- |
| GET    | `/pic/indusPartner`      | Get all industry partners |
| POST   | `/pic/indusPartner`      | Add new industry partner  |
| PUT    | `/pic/indusPartner/{id}` | Update partner info       |
| DELETE | `/pic/indusPartner/{id}` | Delete industry partner   |

# 📝 Discussion Forum APIs
## 📁 Categories
| Method | Endpoint                                              | Description                  |
| ------ | ----------------------------------------------------- | ---------------------------- |
| GET    | `/pic/blogs/categories/{labId}`                       | Get all categories for a lab |
| POST   | `/pic/blogs/category/create`                          | Create category (Faculty)    |
| PUT    | `/pic/blogs/category/update/{facultyId}/{categoryId}` | Update category (Faculty)    |

## 📄 Posts
| Role        | Create                                       | Update                                | Delete                                                        |
| ----------- | -------------------------------------------- | ------------------------------------- | ------------------------------------------------------------- |
| Faculty     | POST `/pic/blogs/post/create/faculty`        | PUT `/pic/blogs/post/update/{postId}` | DELETE `/pic/blogs/post/delete/{userId}/{postId}/faculty`     |
| Student     | POST `/pic/blogs/post/create/student`        | PUT `/pic/blogs/post/update/{postId}` | DELETE `/pic/blogs/post/delete/{userId}/{postId}/student`     |
| IndsPartner | POST `/pic/blogs/post/create/inds`           | PUT `/pic/blogs/post/update/{postId}` | DELETE `/pic/blogs/post/delete/{userId}/{postId}/indsPartner` |
| All Roles   | GET `/pic/blogs/posts/category/{categoryId}` | -                                     | -                                                             |

## 💬 Comments
| Role        | Create                                  | Update                                      | Delete                                                              |
| ----------- | --------------------------------------- | ------------------------------------------- | ------------------------------------------------------------------- |
| Faculty     | POST `/pic/blogs/comment/faculty`       | PUT `/pic/blogs/comment/update/{commentId}` | DELETE `/pic/blogs/comment/delete/{userId}/{commentId}/faculty`     |
| Student     | POST `/pic/blogs/comment/student`       | PUT `/pic/blogs/comment/update/{commentId}` | DELETE `/pic/blogs/comment/delete/{userId}/{commentId}/student`     |
| IndsPartner | POST `/pic/blogs/comment/inds`          | PUT `/pic/blogs/comment/update/{commentId}` | DELETE `/pic/blogs/comment/delete/{userId}/{commentId}/indsPartner` |
| All Roles   | GET `/pic/blogs/comments/post/{postId}` | -                                           | -                                                                   |

---

## 📁 Project Structure

```bash
semester/
├── src/
│ ├── main/
│ │ ├── java/com/ravi/semester/
│ │ │ ├── SemesterApplication.java
│ │ │ ├── Config/
│ │ │ │ ├── RequestInterceptor.java
│ │ │ │ ├── SecurityConfig.java
│ │ │ │ └── WebConfig.java
│ │ │ ├── controller/
│ │ │ │ ├── AttendanceController.java
│ │ │ │ ├── BlogController.java
│ │ │ │ ├── EventController.java
│ │ │ │ ├── FacultyController.java
│ │ │ │ ├── IndsPartnerController.java
│ │ │ │ ├── LabChangeRequestController.java
│ │ │ │ ├── LabController.java
│ │ │ │ ├── LoginController.java
│ │ │ │ ├── StudentController.java
│ │ │ │ └── UserController.java
│ │ │ ├── dto/
│ │ │ │ ├── CategoryRequestDto.java
│ │ │ │ ├── CommentDTO.java
│ │ │ │ ├── FacultyDTO.java
│ │ │ │ ├── IndsPartnerDto.java
│ │ │ │ ├── LabChangeRequestDTO.java
│ │ │ │ ├── LabDTO.java
│ │ │ │ ├── PostDTO.java
│ │ │ │ ├── StudentDTO.java
│ │ │ │ └── UserDto.java
│ │ │ ├── model/
│ │ │ │ ├── Attendance.java
│ │ │ │ ├── Category.java
│ │ │ │ ├── Comment.java
│ │ │ │ ├── Event.java
│ │ │ │ ├── Faculty.java
│ │ │ │ ├── IndsPartner.java
│ │ │ │ ├── Lab.java
│ │ │ │ ├── LabChangeRequest.java
│ │ │ │ ├── Post.java
│ │ │ │ └── Student.java
│ │ │ ├── repository/
│ │ │ │ ├── AttendanceRepository.java
│ │ │ │ ├── CategoryRepo.java
│ │ │ │ ├── CommentRepo.java
│ │ │ │ ├── EventRepository.java
│ │ │ │ ├── FacultyRepo.java
│ │ │ │ ├── IndsPartnerRepo.java
│ │ │ │ ├── LabChangeRequestRepo.java
│ │ │ │ ├── LabRepo.java
│ │ │ │ ├── PostRepo.java
│ │ │ │ └── StudentRepo.java
│ │ │ └── service/
│ │ │ ├── AttendanceService.java
│ │ │ ├── BlogService.java
│ │ │ ├── EmailSenderService.java
│ │ │ ├── EventService.java
│ │ │ ├── FacultyService.java
│ │ │ ├── IndsPartnerService.java
│ │ │ ├── LabChangeRequestService.java
│ │ │ ├── LabService.java
│ │ │ └── StudentService.java
│ │ └── resources/
│ │ ├── application.properties
│ │ ├── static/
│ │ └── templates/
│ │ ├── lab_change_approved.html
│ │ ├── lab_change_rejected.html
│ │ ├── lab_change_sucess.html
│ │ ├── lab_regs_approved.html
│ │ ├── lab_regs_rejected.html
│ │ └── lab_regs_sucess.html
│ └── test/
│ └── java/com/ravi/semester/
│ └── SemesterApplicationTests.java
```
---

## ⚙️ Application Configuration (`application.properties`)

### 🏷️ Application Details
- `spring.application.name=semester`  
  Sets the name of the Spring Boot application.

### 🗄️ Database Configuration
- `spring.jpa.hibernate.ddl-auto=update`  
  Automatically updates the database schema on application start.
- `spring.datasource.url=jdbc:mysql://localhost:3306/pic`  
  MySQL database URL with database name `pic`.
- `spring.datasource.username=root`  
  Database username.
- `spring.datasource.password=ravi`  
  Database password.
- `spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver`  
  MySQL JDBC driver.

### 📧 Email (SMTP) Configuration for Gmail
- `spring.mail.host=smtp.gmail.com`  
  Gmail SMTP server.
- `spring.mail.port=587`  
  SMTP port for TLS.
- `spring.mail.username=`  
  Your Gmail email address (fill this).
- `spring.mail.password=`  
  Your Gmail app password or actual password (fill this).
- `spring.mail.properties.mail.smtp.auth=true`  
  Enable SMTP authentication.
- `spring.mail.properties.mail.smtp.starttls.enable=true`  
  Enable STARTTLS encryption.

### 🔐 OAuth2 Google Authentication Configuration
- `spring.security.oauth2.client.registration.google.client-id=`  
  Your Google OAuth2 client ID (fill this).
- `spring.security.oauth2.client.registration.google.client-secret=`  
  Your Google OAuth2 client secret (fill this).
- `spring.security.oauth2.client.registration.google.redirect-uri=/login/oauth2/code/google`  
  Redirect URI after Google login.
- `spring.security.oauth2.client.registration.google.scope=openid,profile,email`  
  OAuth scopes requested.

### 🔒 Session Cookie Settings
- `server.servlet.session.cookie.same-site=None`  
  Allow cross-site cookies.
- `server.servlet.session.cookie.secure=true`  
  Ensure cookies are sent only over HTTPS.

---

### ⚠️ **Important:**

- **Fill in your Gmail username and password** for email sending to work.
- **Fill in your Google OAuth client ID and secret** for Google login integration.
- Ensure your Gmail account allows SMTP access (use App Passwords if 2FA enabled).


---

## 📧 Email Templates

- Stored in `src/main/resources/templates/`
- Used for sending email notifications about requests such as:
  - ✅ Lab change approvals/rejections
  - ✅ Lab registration approvals/rejections
  - 🎉 Success notifications for lab requests

Each template corresponds to an email type and is rendered with dynamic content when sending emails.

---

## 🚀 How Email Sending Works

- The `EmailSenderService` service loads the appropriate HTML template from the templates folder.
- 📝 It replaces placeholders with actual data (like student name, request details, etc.).
- 📤 Then, it sends the email to the student's registered email address.
- 🔄 This happens automatically whenever a request status is updated (approved/rejected/success).

---
# 👨‍💻 Author
- Developed by Ravivarma E D
- Email: ravivarmaed@gmail.com


