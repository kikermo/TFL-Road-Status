## TFL Road Status Sample App

Basic app that shows the state of a given road



### Building instructions
You need an "appKey" to be able to build the project. That can either be added to your `local.properties`
file, specified as an argument when running a gradle command, or set on the `gradle.properties` file.

#### To build the app

```bash
./gradlew assembleDebug
```

or with app key

```bash
./gradlew assembleDebug -PappKey=[yourAppKey]
```