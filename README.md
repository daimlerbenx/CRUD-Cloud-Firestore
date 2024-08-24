# CRUD-Cloud-Firestore

**CRUD-Cloud-Firestore** is an Android application for managing employee data using Google Cloud Firestore as the database. Developed using Java and XML in Android Studio Arctic Fox (2020.3.1), this app provides basic Create, Read, Update, and Delete (CRUD) functionalities. 

## Features

- **Create**: Add new employee records with name and age.
- **Read**: View a list of all employee records.
- **Update**: Edit existing employee records.
- **Delete**: Remove employee records from the database.

## Setup / How to Start

1. **Create a New Project**: Open Android Studio and create a new project with an Empty Activity.
2. **Connect to Cloud Firestore**:
   - Navigate to **Tools > Firebase**.
   - Select **Cloud Firestore** and click **Connect to Firebase**.
   - Add or choose an existing Firebase project and follow the prompts to connect.
3. **Add Firestore to Your App**: In Android Studio, accept the changes to add Cloud Firestore to your app.
4. **Create Firestore Database**:
   - Go to [Firebase Console](https://console.firebase.google.com/).
   - Navigate to **Cloud Firestore** and click **Create database**.
   - Start in Test Mode, then click **Next** and **Enable**.

## Functionality

- **Insert Data**: Enter a name and age (up to 3 digits) in the input fields and click the "CREATE" button to add a new record to Firestore.
- **Show Data**: Click the "SHOW DATA" button to display all records in a RecyclerView.
- **Delete Data**: Swipe right on an item to delete it from the database.
- **Edit Data**: Swipe left on an item to edit its details.

## Files

- **MainActivity.java**: Manages the creation and updating of employee records.
- **Model.java**: Defines the data structure for employee records.
- **ReadActivity.java**: Displays a list of employee records and supports swipe actions for editing and deleting.
- **TouchHelper.java**: Handles swipe gestures for editing and deleting records in the RecyclerView.

## Screenshots

*Include screenshots of the application in action here.*

## Contributing

Feel free to submit issues or pull requests if you have suggestions for improvements or new features.
