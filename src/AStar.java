import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

public class AStar {
    private Puzzle puzzle;
    private String heuristicType;
    private int maxDepthReached; // To track the maximum depth reached
    private int expandedStates; // To track how many states were expanded

    public AStar(Puzzle puzzle, String heuristicType) {
        this.puzzle = puzzle;
        this.heuristicType = heuristicType;
        this.maxDepthReached = 0; // Initialize max depth to 0
        this.expandedStates = 0; // Initialize expanded states to 0
    }

    // A* Search method
    public boolean search() {
        PriorityQueue<State> openList = new PriorityQueue<>();
        HashSet<String> closedList = new HashSet<>(); // HashSet to track visited states

        // Initial state
        State initialState = new State(puzzle, 0, calculateHeuristic(puzzle), null, null);
        openList.add(initialState);

        while (!openList.isEmpty()) {
            State currentState = openList.poll();
            expandedStates++; // Increment the count of expanded states

            // Update max depth reached
            maxDepthReached = Math.max(maxDepthReached, currentState.gCost);

            // If it's the goal, return true
            if (currentState.puzzle.isGoal()) {
                List<String> solutionPath = reconstructPath(currentState); // Reconstruct the path

                // Print results
                System.out.println("Puzzle solved with A* search using " + heuristicType + "!");
                currentState.puzzle.printPuzzle();
                System.out.println("Moves: " + solutionPath);
                System.out.println("Maximum Depth Reached: " + maxDepthReached);
                System.out.println("States Expanded: " + expandedStates);
                return true;
            }

            // Add the current state to the closed list
            closedList.add(currentState.puzzle.puzzleToString());

            // Expand neighbors
            ArrayList<State> neighbors = currentState.expandNeighbors();

            for (State neighbor : neighbors) {
                if (!closedList.contains(neighbor.puzzle.puzzleToString())) {
                    neighbor.gCost = currentState.gCost + 1; // Increment move count
                    neighbor.hCost = calculateHeuristic(neighbor.puzzle); // Apply chosen heuristic
                    neighbor.fCost = neighbor.gCost + neighbor.hCost; // Total cost f(n)
                    openList.add(neighbor);
                }
            }
        }

        System.out.println("No solution found.");
        return false;
    }

    // Calculate heuristic based on the chosen type
    private int calculateHeuristic(Puzzle puzzle) {
        int heuristicValue = 0;
        switch (heuristicType) {
            case "Manhattan":
                heuristicValue = puzzle.ManhattanDistance();
                break;
            case "MisplacedTiles":
                heuristicValue = puzzle.MisplacedTiles();
                break;
            case "LinearConflict":
                heuristicValue = puzzle.LinearConflict();
                break;
        }
        return heuristicValue;
    }

    // Reconstruct the solution path from the goal state back to the initial state
    private List<String> reconstructPath(State goalState) {
        List<String> path = new ArrayList<>();
        State currentState = goalState;
        while (currentState.previousState != null) {
            path.add(0, currentState.move); // Add the move to the solution path
            currentState = currentState.previousState; // Move to the previous state
        }
        return path;
    }

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle(4); // Create a 3x3 puzzle
        puzzle.readPuzzleFromFile("puzzle4x4.txt"); // Load the puzzle from file

        System.out.println("Initial Puzzle:");
        puzzle.printPuzzle();

        // Run A* search using Misplaced Tiles heuristic
        System.out.println("\nSolving with A* using Misplaced Tiles heuristic...");
        AStar aStarMisplaced = new AStar(puzzle, "MisplacedTiles");
        aStarMisplaced.search();

        // Reset the puzzle
        puzzle.readPuzzleFromFile("puzzle4x4.txt");

        // Run A* search using Manhattan Distance heuristic
        System.out.println("\nSolving with A* using Manhattan Distance heuristic...");
        AStar aStarManhattan = new AStar(puzzle, "Manhattan");
        aStarManhattan.search();

        // Reset the puzzle
        puzzle.readPuzzleFromFile("puzzle4x4.txt");

        // Run A* search using Linear Conflict heuristic
        System.out.println("\nSolving with A* using Linear Conflict heuristic...");
        AStar aStarLinearConflict = new AStar(puzzle, "LinearConflict");
        aStarLinearConflict.search();
    }
}