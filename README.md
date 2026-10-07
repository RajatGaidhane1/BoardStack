# Projemanag - Project Management App

A collaborative project management application built with **Kotlin, Firebase Firestore, and Material Design**. Projemanag demonstrates advanced Android development patterns including real-time database synchronization, complex data relationships, and modern UI/UX practices.

**The most complex portfolio project** – featuring boards, lists, cards, team collaboration, and real-time Firebase integration.

---

## 🎯 Features

### Core Features
✅ **User Authentication** – Firebase Email/Password authentication with secure SignUp/SignIn  
✅ **Board Management** – Create, organize, and manage multiple project boards  
✅ **Hierarchical Tasks** – Boards → Lists → Cards structure for flexible task organization  
✅ **Team Collaboration** – Add team members to boards and assign tasks  
✅ **Card Labels** – 20+ color options for visual task categorization  
✅ **Due Dates** – Set and track task deadlines with date picker  
✅ **User Profiles** – View and update user information and avatar  
✅ **Real-time Sync** – Firebase Firestore for instant data synchronization  
✅ **Gesture Controls** – Swipe-to-delete boards for intuitive UX  
✅ **Responsive UI** – Material Design with proper error handling and loading states  

---

## 📸 Screenshots

**Authentication & Onboarding**  
[Splash Screen] [Sign In] [Sign Up] [Intro/Welcome]

**Main Dashboard**  
[Boards List] [Create Board] [Empty State]

**Board Management**  
[Board Detail View] [Lists & Cards] [Swipe to Delete]

**Task Details**  
[Card Details] [Label Color Picker] [Members Assignment] [Due Date Picker]

**User Management**  
[User Profile] [Edit Profile] [Members List]

---

## 🛠 Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Kotlin 1.8.10 |
| **Android** | SDK 34 (Target), SDK 21 (Min) |
| **Database** | Firebase Firestore |
| **Auth** | Firebase Authentication |
| **Build** | Gradle 8.2, AGP 8.2.0 |
| **UI** | Material Design, View Binding |
| **Networking** | Firebase Realtime Listeners |
| **Images** | Glide 4.15.1 |
| **Notifications** | Firebase Cloud Messaging |

---

## 🏗 Architecture

### Project Structure
```
com.projemanag/
├── activities/          # UI Activities (Auth, Main, Board, etc.)
├── adapters/           # RecyclerView Adapters (6 custom adapters)
├── dialogs/            # Custom Dialogs (Color picker, Member selection)
├── firebase/           # FirestoreClass (All CRUD operations)
├── model/              # Data Models (Board, Card, Task, User)
├── utils/              # Constants, Utilities
└── fcm/                # Firebase Cloud Messaging
```

### Key Data Flow
```
Firebase Authentication
    ↓
User Login → Firestore Queries → Board Data
    ↓
RecyclerView Adapters → UI Display
    ↓
User Actions → Firestore Write → Real-time Listener Update
```

---

## 📊 Complex Data Model

```kotlin
Board
├── name: String
├── image: String (Default URL)
├── createdBy: String (User UID)
├── assignedTo: ArrayList<String> (User UIDs)
└── taskList: ArrayList<Task>
    └── Task
        ├── title: String
        ├── createdBy: String
        └── cards: ArrayList<Card>
            └── Card
                ├── name: String
                ├── assignedTo: ArrayList<String>
                ├── labelColor: String (#hex)
                └── dueDate: Long (milliseconds)
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog+
- Minimum SDK: 21
- Target SDK: 34  
- Java 17

### Setup Steps

1. **Clone Repository**
   ```bash
   git clone https://github.com/RajatGaidhane1/projemanag.git
   ```

2. **Setup Firebase Project**
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create new project
   - Register Android app (package: `com.projemanag`)
   - Download `google-services.json` → place in `app/` folder

3. **Enable Firebase Services**
   - **Authentication**: Email/Password
   - **Firestore Database**: Create in test mode
   - **Cloud Messaging**: Enable FCM

4. **Firestore Security Rules**
   ```firestore
   rules_version = '2';
   service cloud.firestore {
     match /databases/{database}/documents {
       match /{document=**} {
         allow read, write: if request.auth != null;
       }
     }
   }
   ```

5. **Build & Run**
   ```bash
   ./gradlew build
   # Run on emulator or device via Android Studio
   ```

---

## 💡 Key Implementation Highlights

### Firebase Integration
- **Queries**: `whereArrayContains()` for board filtering, `whereEqualTo()` for lookups
- **Real-time Listeners**: Automatic UI updates on database changes
- **Error Handling**: Proper exception catching with user feedback
- **Batch Operations**: Atomic updates for board/card modifications

### UI/UX Patterns
- **View Binding**: Type-safe view references (no findViewById)
- **Custom Dialogs**: Color picker, member selection, date picker
- **Swipe Gestures**: ItemTouchHelper for swipe-to-delete with animation
- **Progress Dialogs**: Loading indicators for async operations
- **Error SnackBars**: User-friendly error messages

### RecyclerView Optimization
- 6 custom adapters (Board, Card, Task List, Members, etc.)
- ViewHolder pattern with efficient binding
- Item click listeners for navigation
- Notifу methods for real-time updates

---

## 🔑 Key Classes

| Class | Responsibility |
|-------|-----------------|
| `FirestoreClass.kt` | All Firestore operations (create, read, update, delete) |
| `BoardItemsAdapter.kt` | Board list display + swipe-to-delete |
| `CardListItemsAdapter.kt` | Card display within task lists |
| `TaskListItemsAdapter.kt` | Task/List display within boards |
| `MemberListItemsAdapter.kt` | Team member selection |
| `LabelColorListDialog.kt` | Color picker dialog |

---

## 📋 Features Deep Dive

### Boards
- Create new boards with default image
- Swipe left to delete (with confirmation)
- View all assigned boards on dashboard
- Real-time updates when boards are modified

### Lists & Cards
- Create multiple lists within a board
- Add cards to any list
- Edit card name, due date, and color label
- Assign cards to team members
- Delete cards with undo option

### Members
- Search team members by email
- Add members to specific boards
- View member list with avatars
- Assign multiple members per card

### Color Labels
- 20 predefined colors for categorization
- Visual feedback with color preview
- Persistent label assignment

---

## ⚙️ Advanced Features

### Implemented
- ✅ Real-time Firestore synchronization
- ✅ Firebase Authentication with error handling
- ✅ Member assignment & collaboration
- ✅ Swipe-to-delete gestures
- ✅ Custom dialogs & pickers
- ✅ Image loading with Glide

### Planned
- 🔄 Push notifications via FCM
- 🔄 Drag & drop card reordering
- 🔄 Activity history/audit log
- 🔄 Offline mode with sync
- 🔄 Board templates
- 🔄 Dark mode support

---

## 🧪 Testing & Validation

**Tested Scenarios:**
- Authentication (SignUp, SignIn, error cases)
- Board CRUD operations
- Member addition & assignment
- Card color & due date changes
- Real-time sync across multiple devices
- Swipe gesture recognition

---

## 📚 What This Project Demonstrates

✅ **Firebase Mastery** – Real-time database, auth, listeners  
✅ **Android Architecture** – Proper separation of concerns  
✅ **RecyclerView Excellence** – Multiple adapters, efficient rendering  
✅ **Data Relationships** – Complex nested data structures  
✅ **Material Design** – Professional UI/UX implementation  
✅ **Error Handling** – User-friendly error messages & loading states  
✅ **Modern Kotlin** – Data classes, extensions, lambda expressions  
✅ **Production Quality** – Organized codebase, scalable patterns  

---

## 🤝 Contributing

Found a bug or have a suggestion? Feel free to open an issue or PR!

---

## 📄 License

MIT License – Free for personal and commercial use

---

## 👨‍💻 Author

**Rajat Gaidhane**  
GitHub: [@RajatGaidhane1](https://github.com/RajatGaidhane1)  
Portfolio: Complex Firebase + Android Project (2024)

---
