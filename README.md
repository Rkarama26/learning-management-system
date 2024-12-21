# Teacher Portal Application

An online learning platform where students and teachers can interact in a one-on-one environment through features like video conferencing, scheduling, and content sharing. The platform is designed to provide seamless communication and collaboration between teachers and students.

---

## Features

### Functional Requirements (Implemented and In Progress)

#### 1. User Authentication and Authorization
- **FR1.1**: Users can register as either a student or a teacher.
- **FR1.2**: Secure login mechanism with optional two-factor authentication.
- **FR1.3**: Role-based access control ensures appropriate functionality for each user role.

#### 2. User Profile Management
- **FR2.1**: Users can update personal information, including contact details and passwords.
- **FR2.2**: Teachers can set and modify their availability schedules.

#### 3. Scheduling and Calendar Integration
- **FR3.1**: Students can view teacher availability via an integrated calendar.
- **FR3.2**: Students can book, reschedule, or cancel appointments with teachers.
- **FR3.3**: Automatic reminders are sent to students and teachers before scheduled sessions.

#### 4. Payment Integration (Planned)
- **FR4.1**: Secure payment gateway for session payments.
- **FR4.2**: Support for multiple payment methods (credit cards, digital wallets).
- **FR4.3**: Transaction handling and receipt generation.

#### 5. Interactive Online Classroom (In Progress)
- **FR5.1**: Video conferencing tool integration.
- **FR5.2**: Interactive tools like digital whiteboards and file sharing during sessions.
- **FR5.3**: Session recording for later playback by students.

#### 6. Feedback and Ratings (Planned)
- **FR6.1**: Students can rate sessions and provide feedback.
- **FR6.2**: Teachers receive summarized feedback for self-assessment.

#### 7. Course Content Management (Planned)
- **FR7.1**: Teachers can upload and organize course materials accessible to students.
- **FR7.2**: Content can be categorized by topic or chapter.

#### 8. Reporting and Analytics (Planned)
- **FR8.1**: Teachers can access analytics on student engagement and session ratings.
- **FR8.2**: Students can view their progress reports and feedback history.

---

## Non-functional Requirements

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
   git clone https://github.com/your-username/teacher-portal.git
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
4. Set up the frontend:
   - Navigate to the frontend folder:
     ```bash
     cd frontend
     ```
   - Install dependencies:
     ```bash
     npm install
     ```
   - Start the development server:
     ```bash
     npm start
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

## License

This project is licensed under the [MIT License](LICENSE).

---

## Contact

For any questions or feedback, please reach out to:
- **Email**: [your-email@example.com](mailto:rohit.karma026@gmail.com)
- **GitHub**: [your-username](https://github.com/Rkarama26)

