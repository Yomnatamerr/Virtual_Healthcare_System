# Virtual Healthcare System

A Java-based desktop application developed to manage virtual healthcare services through a graphical user interface.

## Features

- Patient management: Add, edit, delete, and view patient records.
- Provider management: Manage healthcare provider information.
- Appointment management: Schedule, update, and manage appointments.
- Billing: Manage patient bills and billing information.
- Feedback: Submit and manage patient feedback.
- Data persistence: Store and retrieve application records using Java object serialization.

## Technologies

- Java
- Java Swing
- Object-Oriented Programming (OOP)
- Java Collections
- File I/O and Object Serialization
- IntelliJ IDEA

## Project Structure

The application is organized into Java classes representing users, patients, providers, appointments, electronic health records, prescriptions, billing, payments, and feedback.

`DataManager` handles data persistence and searching, while `VirtualHealthcareGUI` provides the graphical interface.

## How to Run

1. Clone or download the repository.
2. Open the project in IntelliJ IDEA.
3. Configure a Java JDK.
4. Navigate to `src/VirtualHealthcareGUI.java`.
5. Run the `main()` method.

The application uses `.dat` files for local data persistence.
