# Flappy Bird Game

## Description

This is a simple implementation of the classic **Flappy Bird** game using Java and Swing. The game involves navigating a bird through pipes, avoiding collisions with the pipes or falling to the ground. Features include a score system, game-over screen, and options to restart or exit the game.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Setup](#setup)
- [Usage](#usage)
- [Code Overview](#code-overview)
- [Screenshots](#screenshots)
- [License](#license)

## Features

- **Gameplay**: Control the bird to pass through pipes and score points.
- **Score Tracking**: Current score and highest score are displayed.
- **Game Over Screen**: Displays game-over message, current score, highest score, and options to restart or exit.
- **Responsive Design**: UI elements are dynamically positioned and scaled based on the window size.

## Requirements

- **Java Development Kit (JDK) 8 or higher**
- **An IDE or text editor** (e.g., IntelliJ IDEA, Eclipse, VSCode)

## Setup

1. **Clone the Repository**:

    ```bash
    git clone https://github.com/yourusername/flappybird-java.git
    ```

2. **Navigate to the Project Directory**:

    ```bash
    cd flappybird-java
    ```

3. **Compile the Code**:

    ```bash
    javac App.java FlappyBird.java
    ```

4. **Run the Game**:

    ```bash
    java App
    ```

## Usage

- **Spacebar**: Makes the bird jump.
- **Restart Button**: Appears on the game-over screen to restart the game.
- **Exit Button**: Closes the game when pressed.

### How to Play

1. **Start the Game**: The game starts automatically upon running.
2. **Control the Bird**: Press the spacebar to make the bird jump.
3. **Avoid Obstacles**: Navigate through the pipes without hitting them or falling.
4. **Score**: Earn points by successfully passing through the pipes.
5. **Game Over**: When the bird hits a pipe or falls, the game-over screen will appear with options to restart or exit.

## Code Overview

- **App.java**: The entry point of the game. It sets up the main application window and initializes the `FlappyBird` panel.
  - **Initialization**: Creates the `JFrame` and adds the `FlappyBird` `JPanel` to it.
  - **Window Setup**: Configures the window size, title, and close operation.
  - **Visibility**: Makes the window visible to the user.

- **FlappyBird.java**: Contains the main game logic, rendering, and user interface.
  - **Bird**: Represents the player-controlled bird.
  - **Pipe**: Represents the obstacles the bird must navigate.
  - **Game Mechanics**: Includes score tracking, collision detection, and game-over conditions.
  - **UI Components**: Displays game status, scores, and control buttons.

## Screenshots

### Game Start Page

<img src="Screenshot/StartGame.png" alt="StartGame" width="360" height="640" >

### Game Screen

<img src="Screenshot/GameScreen.png" alt="GameScreen" width="360" height="640" >

### Game Over Page

<img src="Screenshot/GameOver.png" alt="GameOver" width="360" height="640" >

## License

This project is open source and available under the [MIT License](https://choosealicense.com/licenses/mit/)

## Acknowledgements

- Inspired by the original Flappy Bird game.
- Thanks to the Java and Swing communities for providing the tools and support needed to create this game.
