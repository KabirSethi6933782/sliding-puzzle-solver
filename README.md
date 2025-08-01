# Sliding Puzzle Solver

This project implements a solver for the classic **sliding numbers puzzle** (8-puzzle and 15-puzzle) using AI search strategies.

## 🧠 Description

The sliding puzzle consists of a 3x3 or 4x4 grid with numbered tiles and one blank space. The goal is to rearrange the tiles into numerical order using legal moves.

This solver supports:
- **Iterative Deepening Depth-First Search (IDDFS)**
- **A\* Search** with the following heuristics:
    - Misplaced Tiles
    - Manhattan Distance
    - Custom heuristic

## 🚀 Features

- Solves both 3x3 and 4x4 puzzles
- Outputs the initial puzzle, solved puzzle, and move steps
- Performance comparison between algorithms and heuristics
- Includes a full analysis write-up


## 📥 Sample Input

### puzzle3x3.txt
3

1 2 3

X 8 6

4 5 7

### puzzle4x4.txt
4

5 8 6 3

2 1 11 X

13 10 14 4

7 9 15 12


## 🛠️ How to Run

1. Compile all files in the `src/` folder.
2. Run the `Puzzle` class.
3. Provide input through one of the provided text files (`puzzle3x3.txt` or `puzzle4x4.txt`).

You can edit the code to hardcode file paths or accept command line arguments as needed.

## 📊 Report

See `WriteUp.pdf` for:
- Algorithm explanation
- Heuristic comparison
- Performance graphs and results

## 🧰 Technologies Used

- Java 8+
- Command Line Interface

## 👤 Author

Kabir Sethi  
Bachelor of Computer Science and Business
Passionate about clean code, problem-solving, and building cool tools.

## 📄 License

This project was developed for academic purposes and is shared for educational use.

