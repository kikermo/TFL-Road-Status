## TFL Road Status Sample App

Basic app that shows the state of a given road

## Tech stack
Architecture: MVVM + Clean Architecture
Featured Libs: Hilt, Jetpack Compose, Retrofit, Compose Navigation
Tests: JUnit unit tests

### Building instructions

You need an "appKey" to be able to build the project. That can be obtained from https://api-portal.tfl.gov.uk/ 
After you have the key it can either be added to your `local.properties` as `appKey=[yourAppKey]`
file, specified as an argument when running a gradle command `-PappKey=[yourAppKey]`, or it can also be set on the `gradle.properties` file.

#### To build the app

```bash
./gradlew assembleDebug
```

or with app key

```bash
./gradlew assembleDebug -PappKey=[yourAppKey]
```