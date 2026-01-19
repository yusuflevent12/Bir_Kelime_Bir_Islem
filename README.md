# 🧩 Bir Kelime Bir İşlem | Word & Number Puzzle

**Bir Kelime Bir İşlem** is a modern **Java Swing desktop puzzle game** inspired by the classic Turkish TV show. It combines **linguistic creativity** and **mathematical reasoning** into a fast-paced, visually polished brain-training experience.

Designed with a clean **Dark Theme UI**, the game challenges players to think quickly, plan strategically, and improve both vocabulary and numerical skills.

---

## 🎮 Game Modes

### 🔤 Word Game Mode
**Objective:** Create the **longest valid word** using the given **8 letters**.

**How to Play:**
1. **Letter Selection:** Right-click a letter from the pool to move it into the word area.
2. **Undo:** Left-click a letter in the word area to send it back.
3. **Joker Panel:** Use extra letters if you get stuck.
4. **Submission:** When the timer ends or you submit manually, the word is automatically validated.

📌 If no valid word is found, the game reveals a correct solution.

---

### 🔢 Number Game Mode
**Objective:** Reach the **target number** using **5 small numbers and 1 large number**.

**How to Play:**
1. Drag & drop numbers into the operation slots.
2. Toggle the operator using the center button: **+ − × ÷**.
3. Press **=** to calculate.
4. Newly generated numbers are added to the list on the right and can be reused.

🎯 Smart combinations and step-by-step calculations bring you closer to the target.

---

## 🧠 Game Over & Scoring Panels

### 🧩 Solution Path (Game Over)
When the player fails to reach the target number, the game displays **all valid calculation paths** leading to the correct result. This helps players understand *how* the solution can be achieved, turning failure into a learning opportunity.

<img width="383" height="445" alt="Solution Path 1" src="https://github.com/user-attachments/assets/5b83db35-07ab-4279-a9a4-38ae4c562946" />
<img width="384" height="507" alt="Solution Path 2" src="https://github.com/user-attachments/assets/352c20a3-bdd4-4708-ab93-b0955547c641" />

---

### 🏆 Win & Score Panel
When the player successfully completes the game, a dedicated panel displays the **earned score**, giving immediate feedback and reinforcing competitive motivation.

<img width="510" height="427" alt="Score Panel" src="https://github.com/user-attachments/assets/f247e185-62f3-46c9-ad11-4823fd8dd8e8" />

---

## 📸 Game Preview

### Main Menu
<img width="100%" alt="Main Menu" src="https://github.com/user-attachments/assets/654e5b88-b0f8-4125-bbeb-8e510d39f2ae" />

### Word Game Mode
<img width="100%" alt="Word Game" src="https://github.com/user-attachments/assets/51bbc2f8-1523-414e-8d46-fb22b71eccc1" />

### Number Game Mode
<img width="100%" alt="Number Game" src="https://github.com/user-attachments/assets/ce5b7925-7c48-4aa6-8a17-f5e3965955cd" />

---

## 🧠 Technical Architecture
The project follows a **modular design**, clearly separating UI components from business logic.

| Class | Description |
|------|------------|
| **GameManager.java** | Core controller managing menus and game flow |
| **Logic.java** | Word validation and mathematical target-generation algorithms |
| **DataBase.java** | Local persistence for scores and leaderboard |
| **Frame.java** | Main application window with custom Swing rendering |
| **Word.java / Number.java** | Game-mode–specific UI panels |
| **Custom Components** | Custom buttons and labels for Dark Theme styling |

---

## ✨ Features
- 🎨 **Modern Dark Theme UI** with custom Swing components
- ⏱️ **Difficulty Levels:** Easy / Normal / Hard
- 🥇 **Persistent Leaderboard** stored locally
- 👤 **User Profiles** to track individual progress
- 🧩 **Solution Visualization** after game over
- ⚡ **Lightweight & Fast** desktop performance

---

## 🚀 Installation & Usage

### Prerequisites
- **JDK 8 or higher**

### Clone the Repository
```bash
git clone https://github.com/yusuflevent12/bir_kelime_bir_islem.git
```

### Run the Game
```bash
cd bir_kelime_bir_islem/src
javac GameManager.java
java GameManager
```

---

## 👨‍💻 Developer

Developed with ❤️ by **yusuflevent12**

> Feedback, contributions, and ⭐ stars are always welcome!