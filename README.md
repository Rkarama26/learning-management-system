# Learning-Management-System

An online learning platform where students and teachers can interact in a one-on-one environment through features like video conferencing, scheduling, and content sharing. The platform is designed to provide seamless communication and collaboration between teachers and students.

---


## Features

#### 1. User Authentication and Authorization
- Users can register as either a student or a teacher.
- Secure login mechanism with Oauth2.O and JWT.
- Role-based access control ensures appropriate functionality for each user role.

#### 2. User Profile Management
- Users can update personal information, including contact details and passwords.
- Teachers can set and modify their availability schedules.

#### 3. Scheduling and Calendar Integration
- Students can view teacher availability via an integrated calendar.
- Students can book, reschedule, or cancel appointments with teachers.
- Automatic reminders are sent to students and teachers before scheduled sessions.

#### 4. Payment Integration (Planned)
- Secure payment gateway for session payments.
- Support for multiple payment methods (credit cards, digital wallets).
- Transaction handling and receipt generation.

#### 5. Interactive Online Classroom (In Progress)
- Video conferencing tool integration.
- Interactive tools like digital whiteboards and file sharing during sessions.
- Session recording for later playback by students.

#### 6. Feedback and Ratings (Planned)
- Students can rate sessions and provide feedback.
- Teachers receive summarized feedback for self-assessment.

#### 7. Course Content Management (Planned)
- Teachers can upload and organize course materials accessible to students.
- Content can be categorized by topic or chapter.

#### 8. Reporting and Analytics (Planned)
- Teachers can access analytics on student engagement and session ratings.
- Students can view their progress reports and feedback history.

---


- **Performance**: The system is designed to handle up to 10,000 concurrent users without performance degradation.

---

## Installation

### Prerequisites
- **Backend**: Java, Spring Boot
- **Frontend**: React
- **Database**: MySQL
- **Other Tools**: Docker, Zoom API

### Steps to Install
1. Clone the repository:
   ```bash
   git clone https://github.com/Rkarama26/teacher-portal.git
   ```
2. Navigate to the project directory:
   ```bash
   cd teacher-portal
   ```
3. Set up the backend:
   - Navigate to the backend folder:
     ```bash
     cd backend
     ```
   - Install dependencies:
     ```bash
     mvn clean install
     ```
   - Configure environment variables for database, Zoom API, and JWT in `application.properties`.
   - Run the application:
     ```bash
     mvn spring-boot:run
     ```

---

## Usage

1. Register as a student or teacher.
2. Teachers can set availability, and students can book sessions.
3. Join sessions using the integrated video conferencing tool.
4. Receive automatic reminders for scheduled sessions.

---

## Contributing

Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add a descriptive message"
   ```
4. Push to the branch:
   ```bash
   git push origin feature-name
   ```
5. Open a pull request.

---

## Contact

For any questions or feedback, please reach out to:
- **Email**: [rohit.karma026@gmail.com](mailto:rohit.karma026@gmail.com)
- **GitHub**: [Rkarama26](https://github.com/Rkarama26)

