# Mastermind Android Game

A word-guessing game for Android built with Jetpack Compose. Guess the 4-letter sequence within 60 seconds!

## Features

### Game Mechanics
- **Random Secret Generation**: At game start, a random 4-character string (A-Z) is generated
- **Input Validation**: Four input boxes allow users to enter one letter each (A-Z only)
- **Color Feedback System**:
  - 🟢 **GREEN**: Character is in the correct position
  - 🟠 **ORANGE**: Character exists in the secret but is in the wrong position
  - 🔴 **RED**: Character is not in the secret string
- **60-Second Timer**: Visual countdown timer displayed on screen
- **Navigation**:
  - Success screen when the correct sequence is entered
  - Game Over screen when time expires
  - Retry button on both result screens to start a new game

### Technical Features
- Built with **Jetpack Compose** and **Material 3**
- Testable game logic separated from UI
- Unit tests for core game mechanics
- CI/CD pipeline with GitHub Actions

## Requirements

- **Android SDK**: 26 (Android 7.0) or higher
- **Target SDK**: 34
- **Java**: JDK 17
- **Gradle**: 8.13+
- **Android Studio**: Hedgehog (2023.1.1) or later

## Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/adimitare/Mastermind-Game.git
   cd mastermindgame
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the `mastermindgame` directory

3. **Sync Gradle**
   - Android Studio will automatically sync Gradle dependencies
   - Wait for sync to complete

4. **Run the app**
   - Connect an Android device or start an emulator (API 26+)
   - Click Run (▶️) or press `Shift+F10`
   - Select your device/emulator

## How to Play

1. **Start**: Launch the app - a random 4-letter secret (A-Z) is generated and logged to Logcat (tag: "Mastermind") for convenience
2. **Input**: Enter one letter (A-Z) in each of the four input boxes
3. **Check**: Press the "Check" button to evaluate your guess
4. **Feedback**: Each box changes color based on the evaluation:
   - Green = correct letter in correct position
   - Orange = letter exists but wrong position
   - Red = letter not in secret
5. **Win**: Guess correctly before the timer expires → Success screen
6. **Lose**: Timer reaches 0:00 → Game Over screen
7. **Retry**: Press "Retry" on either result screen to start a new game

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/angdim/mastermindgame/
│   │   │   ├── MainActivity.kt          # Entry point
│   │   │   ├── core/
│   │   │   │   └── StateManager.kt         # Pure game logic (testable)
│   │   │   └── ui/
│   │   │       ├── App.kt               # Navigation setup
│   │   │       ├── GameScreen.kt        # Main game UI
│   │   │       └── MatchSummaryScreen.kt      # Success/Game Over screens
│   │   ├── res/                          # Resources (strings, themes)
│   │   └── AndroidManifest.xml
│   └── test/
│       └── java/com/angdim/mastermindgame/
│           └── GameScreenTest.kt         # Unit tests
└── build.gradle                          # App dependencies

.github/
└── workflows/
    ├── android-ci.yml                   # CI workflow
```

## Testing

### Running Unit Tests

The game logic is fully testable and comes with unit tests:

```bash
# Run tests via command line
./gradlew test

# Or run tests in Android Studio
# Right-click on `GameScreenTest.kt` → Run 'GameScreenTest'
```

**Test Coverage**:
- All green (perfect guess)
- All red (complete miss)
- All orange (reordered letters)
- Duplicate handling
- Mixed scenarios

### Test Structure

Tests are located in `app/src/test/java/com/angdim/mastermindgame/GameScreenTest.kt`:
- `allGreen()` - Tests perfect guess
- `allRed()` - Tests complete miss
- `allOrangeReordered()` - Tests all letters present but wrong order
- `duplicatesHandled()` - Tests duplicate letter handling
- `mixedCase()` - Tests mixed green/orange/red scenarios

## CI/CD

### Continuous Integration

The project includes GitHub Actions workflows:

1. **Android CI** (`.github/workflows/android-ci.yml`)
   - Triggers on push/PR to `main` or `master`
   - Builds the project
   - Runs unit tests
   - Uploads test reports and debug APK as artifacts

### Manual Release

To create a release:

```bash
git tag v1.0.0
git push origin v1.0.0
```

The release workflow will automatically:
- Build the release APK
- Create a GitHub Release
- Attach the APK for download

## Development

### Game Logic Design

The game logic (`GameScreen.kt`) is intentionally separated from UI concerns:

- **Pure Functions**: No side effects, fully testable
- **Immutable**: Returns new data structures
- **Injectable Random**: Supports test seeding

Key functions:
- `provideSecret(length: Int, random: Random)`: Generates random secret
- `evaluateGuess(secret: String, guess: String)`: Returns color evaluation
- `isWin(evaluation: List<CharacterStatus>)`: Checks if guess is correct

### UI Architecture

- **Compose Navigation**: Screen routing with NavController
- **State Management**: Compose state (`remember`, `mutableStateOf`)
- **Timer**: Kotlin Coroutines with `LaunchedEffect`

## Troubleshooting

**Build fails:**
- Ensure JDK 17 is installed and configured in Android Studio
- Sync Gradle: `File → Sync Project with Gradle Files`

**Tests fail:**
- Clean project: `Build → Clean Project`
- Rebuild: `Build → Rebuild Project`

**Secret not visible:**
- Check Logcat in Android Studio
- Filter by tag: "Mastermind"
- Secret is logged at game start

## License

This project is provided as-is for educational purposes.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add/update tests if needed
5. Submit a pull request

---

**Enjoy cracking the code!** 🎯

