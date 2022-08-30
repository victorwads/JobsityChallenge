# Run Project

To run the project use android studio or use terminal with follow commands.

To run on any device connected:
```bash
# unix
./gradlew installDebug
# windows
./gradlew.bat installDebug
```

To generate apks (debug and release):
```bash
# unix
./gradlew assemble
# windows
./gradlew.bat assemble
```

## File organization

- commons - Common classes and resources
- features - features classes and resources separated by folder
  - some_feature
    - model - Feature data models
    - repository - Feature repositories: persistence, requests, etc.
    - view
      - res - Android Resource Files, configured on app build.gradle for each feature
    - viewModel - feature activities viewModel(s)
