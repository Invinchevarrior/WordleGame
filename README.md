# Java Wordle Game

A Java implementation of the popular word-guessing game Wordle with an intelligent hint system and bot player.

## Features

### Core Game
- Classic Wordle gameplay with 6 attempts to guess a 5-letter word
- Color-coded feedback system (green for correct position, yellow for wrong position, gray for incorrect letters)
- Dictionary validation for all guesses
- Real-time visual feedback with a clean GUI built using Java Swing

### Smart Hint System
- Intelligent word suggestion algorithm using statistical analysis
- Letter frequency and position-based scoring
- Adaptive word filtering based on previous guesses and their results
- One-click hint button for assistance during gameplay

### Automated Solver
- Built-in WordleBot that can solve puzzles efficiently
- Advanced word ranking system using WordComparator
- Pattern matching and letter position tracking
- Optimized guess selection based on remaining possible words

## Technical Details

- **Language:** Java
- **GUI Framework:** Java Swing
- **Project Structure:**
  - `Wordle.java`: Main GUI and game controller
  - `WordleGame.java`: Core game logic and state management
  - `Solution.java`: Hint system implementation
  - `GuessException.java`: Constructor for a GuessException
  - `Status.java`: Check status of the word

## Getting Started

### Prerequisites
- Java JDK 8 or higher
- A text file named `words.txt` containing valid 5-letter words

### Running the Game
1. Clone this repository
2. Ensure `words.txt` is in the project root directory
3. Compile and run `Wordle.java`

