import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Puzzle {
    private int[][] puzzle;
    private int size;
    private int blankRow, blankCol;

    // Constructor to initialize the puzzle size
    public Puzzle(int size) {
        this.size = size;
        this.puzzle = new int[size][size];
    }

    // Method to read the puzzle from a file
    public void readPuzzleFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    String input = scanner.next();
                    if (input.equalsIgnoreCase("X")) {
                        puzzle[i][j] = 0; // Represent the blank space with 0
                        blankRow = i;
                        blankCol = j;
                    } else {
                        puzzle[i][j] = Integer.parseInt(input);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            System.exit(1);
        }
    }

    // Misplaced Tiles Heuristic
    public int MisplacedTiles() {
        int misplaced = 0;
        int correctTile = 1;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Ignore the blank tile (represented by 0)
                if (puzzle[i][j] != 0 && puzzle[i][j] != correctTile) {
                    misplaced++;
                }
                correctTile++;
            }
        }
        return misplaced;
    }

    // Manhattan Distance Heuristic
    public int ManhattanDistance() {
        int manhattanDistance = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = puzzle[i][j];
                if (value != 0) {
                    int goalRow = (value - 1) / size;
                    int goalCol = (value - 1) % size;
                    manhattanDistance += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }
        return manhattanDistance;
    }

    // Linear Conflict Heuristic
    public int LinearConflict() {
        int linearConflict = ManhattanDistance(); // Start with Manhattan Distance

        // Check for conflicts in rows
        for (int row = 0; row < size; row++) {
            linearConflict += RowConflicts(row);
        }

        // Check for conflicts in columns
        for (int col = 0; col < size; col++) {
            linearConflict += ColumnConflicts(col);
        }

        return linearConflict;
    }

    // Check for linear conflicts in a row
    private int RowConflicts(int row) {
        int conflictCount = 0;

        for (int i = 0; i < size; i++) {
            int tileValue1 = puzzle[row][i];

            if (tileValue1 == 0) continue; // Skip blank tile

            int goalCol1 = (tileValue1 - 1) % size;
            if (goalCol1 != i) continue; // Only consider tiles in their goal row

            for (int j = i + 1; j < size; j++) {
                int tileValue2 = puzzle[row][j];
                if (tileValue2 == 0) continue; // Skip blank tile

                int goalCol2 = (tileValue2 - 1) % size;
                if (goalCol2 != j) continue; // Only consider tiles in their goal row

                // Linear conflict if tile 1's goal is further right than tile 2's goal
                if (goalCol1 > goalCol2) {
                    conflictCount += 2; // Add 2 moves to resolve this conflict
                }
            }
        }

        return conflictCount;
    }

    // Check for linear conflicts in a column
    private int ColumnConflicts(int col) {
        int conflictCount = 0;

        for (int i = 0; i < size; i++) {
            int tileValue1 = puzzle[i][col];

            if (tileValue1 == 0) continue; // Skip blank tile

            int goalRow1 = (tileValue1 - 1) / size;
            if (goalRow1 != i) continue; // Only consider tiles in their goal column

            for (int j = i + 1; j < size; j++) {
                int tileValue2 = puzzle[j][col];
                if (tileValue2 == 0) continue; // Skip blank tile

                int goalRow2 = (tileValue2 - 1) / size;
                if (goalRow2 != j) continue; // Only consider tiles in their goal column

                // Linear conflict if tile 1's goal is below tile 2's goal
                if (goalRow1 > goalRow2) {
                    conflictCount += 2; // Add 2 moves to resolve this conflict
                }
            }
        }

        return conflictCount;
    }

    // Method to print the current state of the puzzle
    public void printPuzzle() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (puzzle[i][j] == 0) {
                    System.out.print("X ");
                } else {
                    System.out.print(puzzle[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println(); // Add an empty line for better readability
    }

    // Movement methods
    public boolean moveUp() {
        if (blankRow == 0) return false;
        puzzle[blankRow][blankCol] = puzzle[blankRow - 1][blankCol];
        puzzle[blankRow - 1][blankCol] = 0;
        blankRow--;
        return true;
    }

    public boolean moveDown() {
        if (blankRow == size - 1) return false;
        puzzle[blankRow][blankCol] = puzzle[blankRow + 1][blankCol];
        puzzle[blankRow + 1][blankCol] = 0;
        blankRow++;
        return true;
    }

    public boolean moveLeft() {
        if (blankCol == 0) return false;
        puzzle[blankRow][blankCol] = puzzle[blankRow][blankCol - 1];
        puzzle[blankRow][blankCol - 1] = 0;
        blankCol--;
        return true;
    }

    public boolean moveRight() {
        if (blankCol == size - 1) return false;
        puzzle[blankRow][blankCol] = puzzle[blankRow][blankCol + 1];
        puzzle[blankRow][blankCol + 1] = 0;
        blankCol++;
        return true;
    }

    // Method to convert the puzzle state to a string for tracking
    public String puzzleToString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : puzzle) {
            for (int val : row) {
                sb.append(val).append(",");
            }
        }
        return sb.toString();
    }

    // Method to check if the puzzle is in the goal state
    public boolean isGoal() {
        int expected = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // The last position should be the blank space
                if (i == size - 1 && j == size - 1) {
                    if (puzzle[i][j] != 0) return false;
                } else {
                    if (puzzle[i][j] != expected) return false;
                    expected++;
                }
            }
        }
        return true;
    }

    // Clone the puzzle to create a new state
    public Puzzle clonePuzzle() {
        Puzzle cloned = new Puzzle(this.size);
        for (int i = 0; i < size; i++) {
            System.arraycopy(this.puzzle[i], 0, cloned.puzzle[i], 0, size);
        }
        cloned.blankRow = this.blankRow;
        cloned.blankCol = this.blankCol;
        return cloned;
    }
}