## TFL Road Status Sample App

Basic app that shows the state of a given road

### Tech stack
Architecture: MVVM + Clean Architecture\
Featured Libs: Hilt, Jetpack Compose, Retrofit, Compose Navigation\
Tests: JUnit unit tests

#### Demo



### Building instructions

You need an "appKey" to be able to build the project. That can be obtained from https://api-portal.tfl.gov.uk/ .
After you have obtained the key, it can either be added to your `local.properties` file as `appKey=[yourAppKey]`
, added to your `gradle.properties` file, or passed as an argument when running any gradle command `-PappKey=[yourAppKey]`

#### Building the app

```bash
./gradlew assembleDebug
```

or with app key

```bash
./gradlew assembleDebug -PappKey=[yourAppKey]
```


### Running the tests

Simply run:

```bash
./gradlew test
```