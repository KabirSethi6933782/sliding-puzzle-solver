import java.util.ArrayList;

public class IterativeDFS {
    private Puzzle puzzle;
    private static final int MAX_DEPTH = 35;
    private ArrayList<String> movesList; // List to track moves

    public IterativeDFS(Puzzle puzzle) {
        this.puzzle = puzzle;
        this.movesList = new ArrayList<>();
    }

    public boolean iterativeDeepeningSearch() {
        for (int depthLimit = 0; depthLimit <= MAX_DEPTH; depthLimit++) {
            boolean found = recursiveDLS(depthLimit, 0, "");
            if (found) {
                return true; // Solution found
            }
        }
        return false; // No solution found
    }

    private boolean recursiveDLS(int depthLimit, int currentDepth, String lastMove) {
        if (puzzle.isGoal()) {
            return true; // Solution found
        }

        if (currentDepth == depthLimit) {
            return false;
        }

        boolean foundSolution = false;

        if (!lastMove.equals("down") && puzzle.moveUp()) {
            movesList.add("Up");
            foundSolution = recursiveDLS(depthLimit, currentDepth + 1, "up");
            if (foundSolution) return true;
            movesList.remove(movesList.size() - 1);
            puzzle.moveDown(); // Undo move
        }

        if (!lastMove.equals("up") && puzzle.moveDown()) {
            movesList.add("Down");
            foundSolution = recursiveDLS(depthLimit, currentDepth + 1, "down");
            if (foundSolution) return true;
            movesList.remove(movesList.size() - 1);
            puzzle.moveUp();
        }

        if (!lastMove.equals("right") && puzzle.moveLeft()) {
            movesList.add("Left");
            foundSolution = recursiveDLS(depthLimit, currentDepth + 1, "left");
            if (foundSolution) return true;
            movesList.remove(movesList.size() - 1);
            puzzle.moveRight();
        }

        if (!lastMove.equals("left") && puzzle.moveRight()) {
            movesList.add("Right");
            foundSolution = recursiveDLS(depthLimit, currentDepth + 1, "right");
            if (foundSolution) return true;
            movesList.remove(movesList.size() - 1);
            puzzle.moveLeft();
        }

        return foundSolution;
    }

    public ArrayList<String> getMoves() {
        return movesList;
    }

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle(3); // Create a 3x3 or 4x4 puzzle
        puzzle.readPuzzleFromFile("puzzle3x3.txt"); // Read puzzle from file

        System.out.println("Initial Puzzle:");
        puzzle.printPuzzle(); // Print the initial puzzle

        // Create an instance of IDDFS with the puzzle
        IterativeDFS iterativeDFS = new IterativeDFS(puzzle);

        System.out.println("Solving using IDDFS...");
        boolean solved = iterativeDFS.iterativeDeepeningSearch(); // Run IDDFS

        if (solved) {
            System.out.println("Puzzle solved!");
            System.out.println("Moves: " + iterativeDFS.getMoves()); // Print the moves
            puzzle.printPuzzle(); // Print the solved puzzle
        } else {
            System.out.println("No solution found.");
        }
    }
}
