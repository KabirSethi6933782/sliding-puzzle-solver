import java.util.ArrayList;

public class State implements Comparable<State> {
    public Puzzle puzzle;
    public int gCost; // The cost of reaching this state (g(n))
    public int hCost; // The heuristic value (h(n))
    public int fCost; // The total cost (f(n) = g(n) + h(n))
    public State previousState; // Reference to the previous state for path reconstruction
    public String move; // The move that led to this state (e.g., "Right", "Left", "Up", "Down")

    public State(Puzzle puzzle, int gCost, int hCost, State previousState, String move) {
        this.puzzle = puzzle.clonePuzzle(); // Clone the puzzle to avoid modifying the original
        this.gCost = gCost;
        this.hCost = hCost;
        this.fCost = gCost + hCost;
        this.previousState = previousState;
        this.move = move; // Store the move that led to this state
    }

    // Expand neighbors
    public ArrayList<State> expandNeighbors() {
        ArrayList<State> neighbors = new ArrayList<>();

        // Try to move the blank tile up
        if (puzzle.moveUp()) {
            neighbors.add(new State(puzzle, gCost + 1, puzzle.MisplacedTiles(), this, "Up"));
            puzzle.moveDown(); // Undo the move to restore original state
        }

        // Try to move the blank tile down
        if (puzzle.moveDown()) {
            neighbors.add(new State(puzzle, gCost + 1, puzzle.MisplacedTiles(), this, "Down"));
            puzzle.moveUp(); // Undo the move to restore original state
        }

        // Try to move the blank tile left
        if (puzzle.moveLeft()) {
            neighbors.add(new State(puzzle, gCost + 1, puzzle.MisplacedTiles(), this, "Left"));
            puzzle.moveRight(); // Undo the move to restore original state
        }

        // Try to move the blank tile right
        if (puzzle.moveRight()) {
            neighbors.add(new State(puzzle, gCost + 1, puzzle.MisplacedTiles(), this, "Right"));
            puzzle.moveLeft(); // Undo the move to restore original state
        }

        return neighbors;
    }

    // Compare states based on f(n)
    @Override
    public int compareTo(State other) {
        return Integer.compare(this.fCost, other.fCost); // Sort by fCost
    }
}