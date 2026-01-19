🧩 Bir Kelime Bir İşlem | Word & Number PuzzleA modern, desktop-based brain teaser inspired by the classic Turkish TV show "Bir Kelime 
Bir İşlem." Challenge your brain with complex mathematical targets and linguistic puzzles, all 
wrapped in a sleek, high-performance Java Swing application.
📸 Game PreviewNote to User
Main Menu
<img width="1385" height="842" alt="Ekran görüntüsü 2026-01-19 191422" src="https://github.com/user-attachments/assets/654e5b88-b0f8-4125-bbeb-8e510d39f2ae" />

Word Game Mode
<img width="1385" height="842" alt="Ekran görüntüsü 2026-01-19 191444" src="https://github.com/user-attachments/assets/51bbc2f8-1523-414e-8d46-fb22b71eccc1" />

Number Game Mode
<img width="1390" height="840" alt="Ekran görüntüsü 2026-01-19 191540" src="https://github.com/user-attachments/assets/ce5b7925-7c48-4aa6-8a17-f5e3965955cd" />

Leaderboard

🕹️


How to Play🔤

Word Game Mode
The objective is to find the longest possible word using the given 8 letters.
1.Selection: Right-clickon a letter in the pool to move it to the word area.
2.Modification: If you make a mistake, Left-click the letter in your word area to send it back.
3.The Joker: Use the Joker Panel on the left to add extra letters if you are stuck.
4.Submission: The game automatically checks your word when the timer hits zero or you submit.
Reveal:

<img width="383" height="445" alt="Ekran görüntüsü 2026-01-19 191722" src="https://github.com/user-attachments/assets/5ed29243-15f1-4e5b-bed4-609cd4a744e1" />

<img width="384" height="507" alt="Ekran görüntüsü 2026-01-19 191641" src="https://github.com/user-attachments/assets/c7bf8ede-bbf5-42a3-8486-03aca88b0e86" />

— If you can't find a word, the game will reveal a valid solution.

🔢 Number Game Mode
1.Combine 5 random numbers and 1 "big" number to reach the target result.
2.Drag & Drop: Select a number and drag it into one of the operation slots at the bottom.
3.Arithmetic: Click the center operator button to toggle between +, -, *, /.
4.Stacking: Press "=" to calculate. Your result is added to the list on the right.
5.Recycling: You can use these new results in your next calculations to get closer to the target!

🛠️ Technical Class Breakdown 
The project follows a modular structure to separate UI from business logic:

GameManager.java: The brain of the application. It handles the state transitions between menus and the game loop.
Logic.java: Contains the algorithms for word validation and mathematical target generation.
DataBase.java: Manages local I/O operations to save high scores and retrieve the leaderboard.
Frame.java: The main window container using custom Swing rendering.
Word.java & Number.java: Specialized panels that handle the specific UI elements for each game mode.
Custom Components: Classes like ButtonWord and LabelNumber provide the unique "Dark Theme" aesthetics.

✨ Features
🎨 Modern UI/UX: Sleek "Dark Theme" with custom-designed buttons and smooth animations.
📈 Dynamic Difficulty: Choose between Easy, Normal, and Hard to challenge your speed.
🥇 Persistent Leaderboard: Track your progress and compete for the top spot.
👤 User Management: Create a profile to keep your high scores organized.

🚀 Installation & UsagePrerequisites: Ensure you have JDK 8 or higher installed.

Clone:Bashgit clone https://github.com/yusuflevent12/bir_kelime_bir_islem.git
Run:Navigate to the src folder,
then compile and run:
Bash javac GameManager.java
java GameManager
Developed with ❤️ by yusuflevent12
