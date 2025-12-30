Bir Kelime Bir İşlem (Word & Number Puzzle)
A modern, desktop-based brain teaser game inspired by the classic Turkish TV show "Bir Kelime Bir İşlem." Built with Java Swing, this application challenges your linguistic and mathematical skills through two distinct game modes: Word Game and Number Game.

🎮 Game Modes
1. Word Game
Your goal is to construct the longest possible word using a given set of random letters.

Gameplay Mechanics:

Selection: Right-click on a letter to automatically move it to the first available slot in your word construction area.

Correction: If you change your mind, left-click a letter in the word area to return it to its original position (either the letter pool or the Joker panel).

Joker Panel: If the provided letters are insufficient, you can utilize additional letters from the Joker Panel on the left to complete your word.

Solution Reveal: If the timer runs out and you haven't found a word, the game reveals a valid solution in the Game Over notification.

2. Number Game
Test your mathematical prowess by reaching a target number using five randomly generated numbers and basic arithmetic operations.

Gameplay Mechanics:

Drag & Drop: Select two numbers and drag them into the operation slots at the bottom of the screen.

Operation Selection: Click the operator button between the two numbers to cycle through addition (+), subtraction (-), multiplication (*), and division (/).

Result Accumulation: Press the Equal (=) button to calculate the result. Every result you create is saved in a list on the right side of the screen, allowing you to use these new values in subsequent calculations.

Winning: The game concludes immediately once you reach the exact target number, and your final score is recorded.

Hints: If you fail to reach the target before time expires, the system displays a possible mathematical solution.

✨ Key Features
Modern UI/UX: A sleek "Dark Theme" interface with custom-designed buttons, smooth animations, and a responsive layout.

Dynamic Difficulty: Choose between Easy, Normal, and Hard modes to adjust the game timer and challenge level.

Persistent Leaderboard: A global high-score table that tracks top performers across different game sessions using a local database.

User Management: Register your username at the start to track your personal records.

🛠️ Technical Stack
Language: Java

GUI Framework: Swing (AWT for custom rendering and event handling).

Data Management: File-based/Local Database for storing high scores.

Pattern: Modular architecture with a clear separation between UI (Frame), Game Logic (Logic), and State Management (GameManager).

🚀 Getting Started
Prerequisites: Ensure you have Java JDK 8 or higher installed.

Installation:

Bash

git clone https://github.com/yusuflevent12/bir_kelime_bir_islem.git
Running the Game:

Navigate to the src folder.

Compile and run the GameManager.java file:

Bash

javac GameManager.java
java GameManager
