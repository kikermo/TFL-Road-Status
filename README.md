## TFL Road Status Sample App

Basic app that shows the state of a given road

### Tech stack
Architecture: MVVM + Clean Architecture\
Featured Libs: Hilt, Jetpack Compose, Retrofit, Compose Navigation\
Tests: JUnit unit tests

#### Demo
[demo.webm](https://github.com/kikermo/TFL-Road-Status/assets/5659713/f59f8a86-6559-488d-9c11-49c32519ef5d)


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
