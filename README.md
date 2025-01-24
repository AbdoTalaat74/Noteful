# Noteful

## Overview
The **Noteful** is a modern Android application designed to help users efficiently create, edit, and manage notes with ease. Leveraging the latest technologies and best practices, this app ensures a seamless and responsive user experience.

---

## Features
- **Create, Edit, and Delete Notes**: Intuitive features to manage notes effortlessly.
- **Search Notes**: Quickly find notes with a powerful search functionality.
- **Categorize Notes**: Organize your notes into categories for better management.
- **Offline Support**: Access your notes anytime, even without an internet connection.

---

## Tech Stack

### 🛠 Architectural Pattern
- **Clean Architecture**: 
  - Modular structure with clear separation of concerns.
  - Divided into layers: `Presentation`, `Domain`, and `Data`.

- **MVVM (Model-View-ViewModel)**: 
  - Enables reactive UI updates.
  - Improves maintainability and testability.

### 💻 UI Framework
- **Jetpack Compose**: 
  - For building a responsive, declarative, and modern UI.

### ⚙️ Dependency Injection
- **Dagger Hilt**: 
  - Simplifies dependency management, ensuring modularity and testability.

### 🔄 Asynchronous Programming
- **Coroutines**: 
  - For efficient and seamless handling of background operations.

---

## Installation

### Prerequisites
- Android Studio `Arctic Fox` or newer.
- Minimum Android SDK level: `21`.

### Steps to Run the App
1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/note-app.git
   ```
2. Open the project in Android Studio.
3. Sync the project to download dependencies.
4. Run the app on an emulator or a physical device.

---

## Folder Structure
```
├── data
│   ├── repository   # Data sources implementation
├── domain
│   ├── model        # Business models
│   ├── repository   # Repository interfaces
│   ├── usecase      # Application business logic
├── presentation
│   ├── ui           # Composables and screens
│   ├── viewmodel    # State and logic management
├── di               # Dependency injection modules
└── utils            # Helper classes and functions
```

---

## Screenshots

![Image](https://github.com/user-attachments/assets/402a0cbf-8a9a-43d8-8991-ac2d5234fa43)
![Screenshot_20250124_233306](https://github.com/user-attachments/assets/2611d8d9-f23a-416c-91dc-b8b436a05570)

---

## Screen Record


https://github.com/user-attachments/assets/519d2c29-1b8c-4301-9dbb-e0ff94413015



## Acknowledgements
- **Google**: For Jetpack Compose and Android architecture guidelines.

---

## Contact
For any inquiries, please reach out to:
- **Abdelrahman Talaat **
- Email: abdelrahmant.dev@gmail.com
- GitHub: https://github.com/AbdoTalaat74
