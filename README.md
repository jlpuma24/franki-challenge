# Franki Challenge Project

## Overview

### Key Features:

- Fetches real-time weather data for a specified location.
- Includes error handling to manage API failures or invalid user input.
- Combines the traditional XML layouts and Jetpack Compose to showcase UI development flexibility, both approaches was taken in count.
- Modular code structure to improve scalability and maintainability.

---

## Project Structure

### 1. **data**
Handles data-related operations:
- **entity**: Contains data classes representing database entities.
- **local**: Handles local storage and Room database integration.
- **model**: Contains data transfer objects (DTOs) for mapping data.
- **remote**: Manages API requests using Retrofit.
- **repository**: Implements repository logic to bridge data and domain layers.

### 2. **di (Dependency Injection)**
Defines dependency injection modules using Koin:
- **DatabaseModule.kt**: Manages Room database dependencies.
- **Modules.kt**: Configures general dependencies.
- **NetworkModule.kt**: Sets up network-related dependencies (e.g., Retrofit).

### 3. **domain**
Defines core business logic:
- **repository**: Interfaces for repository classes.
- **usecase**: Implements use case logic for various features.

### 4. **presentation**
Handles UI and user interaction:
- **extensions**: Provides utility extension functions.
- **screens**: Defines UI components using Jetpack Compose.
- **viewmodel**: Implements `ViewModel` classes for state management.

### 5. **FrankiChallengeApplication**
The main `Application` class initializes dependencies and configurations.

---

## Testing

### Unit Tests
The `test` directory contains unit test cases to validate application logic. Tests are written for the following components:
- **data.repository**
    - `ConcreteWeatherRepositoryTest`: Tests repository logic for weather-related data.
- **domain.usecase**
    - `GetWeatherUseCaseTest`: Validates the business logic for retrieving weather data.
- **presentation.viewmodel**
    - `WeatherViewModelTest`: Ensures correct behavior and state management in the weather-related `ViewModel`.

---

## Dependencies
The project utilizes the following dependencies:

### Core
- `androidx.core:core-ktx`
- `androidx.appcompat:appcompat`
- `com.google.android.material:material`
- `androidx.constraintlayout:constraintlayout`

### Network
- `com.squareup.retrofit2:retrofit`
- `com.squareup.retrofit2:adapter-rxjava2`
- `com.squareup.retrofit2:converter-gson`
- `com.squareup.okhttp3:logging-interceptor`
- `com.squareup.retrofit2:converter-moshi`
- `com.google.code.gson:gson`

### Dependency Injection
- `org.koin:koin-android`
- `org.koin:koin-android-compat`
- `org.koin:koin-androidx-workmanager`
- `org.koin:koin-androidx-navigation`

### Room Database
- `androidx.room:room-runtime`
- `androidx.room:room-ktx`
- Room compiler with `ksp`: `androidx.room:room-compiler`

### Image Loading
- `com.github.bumptech.glide:glide`

### Jetpack Compose
- `androidx.activity:activity-compose`
- `androidx.ui:ui`
- `androidx.material:material`
- `androidx.ui:tooling-preview`
- `androidx.lifecycle:lifecycle-viewmodel-compose`
- `androidx.navigation:navigation-compose`
- `io.coil-kt:coil-compose`

### Testing
- Unit Testing:
    - `org.jetbrains.kotlinx:kotlinx-coroutines-test`
    - `androidx.core:core-testing`
    - `io.mockk:mockk`
- Instrumentation Testing:
    - `junit:junit`
    - `androidx.test.ext:junit`
    - `androidx.test.espresso:espresso-core`
    - `androidx.test.espresso:espresso-contrib`

---

## Java Version
The project uses **Java 21** for development and build processes.

---

## How to Run
1. Clone the repository.
2. Open the project in Android Studio.
3. Sync Gradle to download dependencies.
4. Ensure a device or emulator is configured.
5. Build and run the application.

