<p align="center">
  <img src="https://img.shields.io/badge/TODO%20Manager-Full%20Stack-30A7FC?style=for-the-badge&logo=todoist&logoColor=white" alt="Project Banner">
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Frontend-React%20%2B%20Vite-61DAFB?style=for-the-badge&logo=react&logoColor=white" alt="React Badge"/>
  <img src="https://img.shields.io/badge/Backend-Java%20Spark-007396?style=for-the-badge&logo=java&logoColor=white" alt="Java Spark Badge"/>
  <img src="https://img.shields.io/badge/Database-MySQL%20%2B%20JDBC-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL Badge"/>
  <img src="https://img.shields.io/badge/Styling-Custom%20CSS-1572B6?style=for-the-badge&logo=css3&logoColor=white" alt="Custom CSS Badge"/>
</p>

# ✅ TODO Manager — React + Vite + Java Spark + JDBC + MySQL

A modern, full-stack **Task Management Application** built to demonstrate a powerful combination of **React** for a dynamic user interface and **Java Spark** for a robust, lightweight backend, all connected via **JDBC** to a **MySQL** database.

## 🌟 Features

### ✅ Core Functionality
* **Create** tasks with title and description.
* **Edit** existing tasks with real-time updates.
* **Delete** tasks instantly from the database.
* **Persistent Storage** in a **MySQL** database.
* **Real-time UI updates** (no page refresh required).

### 🎨 UI/UX Enhancements
* **Dark/Light Mode** toggle for comfortable viewing.
* **Clickable Status Badges** to cycle through:
    * 🔴 **Pending**
    * 🟡 **In-progress**
    * 🟢 **Completed**
* **Toast Notifications** for success and error feedback.
* Clean, stylish card layout with smooth animations.
* **Responsive UI** for seamless mobile use.

### 🔄 Advanced Features
* **Drag & Drop Task Sorting** powered by `react-beautiful-dnd` to visually reorder tasks.
* **API Endpoints** for all CRUD (Create, Read, Update, Delete) operations.

---

## 🛠️ Tech Stack

| Layer | Technology | Description |
| :--- | :--- | :--- |
| **Frontend** | **React** + **Vite** | Modern, fast UI development framework and build tool. |
| **UI Utility** | `react-beautiful-dnd` | Library for creating beautiful, accessible drag and drop lists. |
| **Backend** | **Java 17+** | Robust, scalable backend language. |
| **Web Framework** | **Spark Java** | Lightweight, simple web framework for REST APIs. |
| **Database** | **MySQL 8.x** | Open-source relational database for persistent storage. |
| **Connector** | **JDBC** | Java Database Connectivity for connecting Java to MySQL. |

---

## 📦 Project Structure

```bash
todo-app/
── screenshots/
│ ├── home.png
│ ├── dark_mode.png
│ ├── edit_task.png
│ ├── mobile_view.png
│ ├── delete_task.png
│── frontend/
│ ├── src/
│ │ ├── components/
│ │ │ ├── TaskList.jsx      # Handles task list and D&D logic
│ │ │ ├── TaskForm.jsx      # Component for creating new tasks
│ │ │ ├── EditModal.jsx     # Modal for editing task details
│ │ │ ├── Toast.jsx         # Notification component
│ │ ├── App.jsx             # Main component, handles state/API calls
│ ├── public/
│ ├── package.json
│
│── backend/
│ ├── src/main/java/com/example/todo/
│ │ ├── Main.java           # Server entry point, defines Spark routes
│ │ ├── model/Task.java     # Java POJO for Task object
│ │ ├── dao/TaskDAO.java    # Data Access Object, handles JDBC/SQL logic
│ │ ├── util/DBUtil.java    # Database connection utility
│ ├── pom.xml               # Maven configuration
│
│── README.md
```
## 💾 Database Setup

Ensure you have a running **MySQL instance**. Then, connect to your MySQL shell and execute the following SQL to set up the database and the `tasks` table:

```sql
CREATE DATABASE todo_jdbc;
USE todo_jdbc;

CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(32) DEFAULT 'pending' CHECK (status IN ('pending', 'in-progress', 'completed')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
## Local Development Setup
### 1️⃣ Clone the Repository
```bash
git clone https://github.com/KundlikTech/TODO-Manager.git
cd todo-app
```
### 2️⃣ Backend Setup (Java + Spark + JDBC)
1.Navigate to the backend folder:
```bash
cd backend
```
2.Install dependencies, compile, and package the application using Maven:
```bash
mvn clean package
```
3.Run the Java server (make sure you have Java 17+ installed):
```bash
java -jar target/backend-1.0-SNAPSHOT.jar
```
### 3️⃣ Frontend Setup (React + Vite)
1.Navigate back to the main directory and then into the frontend folder:
```bash
cd ../frontend
```
2.Install the Node.js dependencies:
```bash
npm install
```
3.Start the development server:
```bash
npm run dev
```
## 🌐 API Endpoints
The Spark Java backend exposes the following RESTful API endpoints:
| Method | Endpoint | Description | Example Request Body |
| :--- | :--- | :--- | :--- |
| **GET** | `/tasks` | Retrieves all tasks. | N/A |
| **GET** | `/tasks/:id` | Retrieves a single task by ID. | N/A |
| **POST** | `/tasks` | Creates a new task. | `{ "title": "Study", "description": "Prepare for exam", "status": "pending" }` |
| **PUT** | `/tasks/:id` | Updates an entire task. | `{ "title": "New Title", "description": "New Desc", "status": "in-progress" }` |
| **PUT** | `/tasks/:id/status` | Updates only the status of a task. | `{ "status": "completed" }` |
| **DELETE** | `/tasks/:id` | Deletes a task by ID. | N/A |

## 🎯 Future Improvements

* **Authentication (JWT):** Implement user login/signup for multi-user support.
* **Drag-and-Drop Persistence:** Store the task order in the database.
* **Subtasks:** Allow tasks to have smaller, nested tasks.
* **Priority Levels:** Add a field and UI to set task priority (Low, Medium, High).
* **Calendar Deadlines:** Integrate date/time pickers for due dates.

## ❤️ Contributing
Pull requests are welcome! For major changes or new features, please open an issue first to discuss what you would like to change.
## 📄 License
This project is licensed under the MIT License.
