import java.util.ArrayList;
import java.util.List;

public class SimulationManager {
    private CellManager cellManager;    // Instance of CellManager
    private FoodManager foodManager;    // Instance of FoodManager
    private boolean isRunning;          // Flag for running state
    private int simulationTime;

    public SimulationManager(int initialFoodUnits, int initialCellCount) {
        cellManager = new CellManager(); // Create an instance of CellManager
        foodManager = new FoodManager(initialFoodUnits);
        isRunning = true;
        simulationTime = 0;

        // Create and add initial cells to the simulation using the CellManager
        for (int i = 0; i < initialCellCount; i++) {
            CellType cellType = (i % 2 == 0) ? CellType.ASEXUATE : CellType.SEXUATE;
            Cell cell = new Cell(cellType);
            cell.setFoodManager(foodManager); // Set the food manager for each cell
            cellManager.addCell(cell); // Add the cell to the CellManager
        }

        // Start the simulation threads
        cellManager.startCells();
    }

    public void stopSimulation() {
        isRunning = false;
        cellManager.stopCells(); // Stop the cell threads using CellManager
    }

    public void runSimulation() {
        System.out.println("1");
        while (isRunning) {
            System.out.println("2");
            simulationTime++;
            System.out.println("3 simTime++");
            // 1. Update the state of each cell
            // The CellManager handles the cell logic and threads
            cellManager.updateCellState();
            System.out.println("4 updated Cellstate()");
            // 2. Implement global simulation logic here
            // For example, replenish food units (adjust amount as needed):
            foodManager.replenishFood(10);
            System.out.println("5  replenished food");

            // Print the current state of the simulation
            printSimulationStateGraphic();
            System.out.println("6 PRINT");

            if (simulationTime % 60 == 0) {
                stopSimulation();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printSimulationStateGraphic() {
        // Clear the console to redraw the simulation state (you may need platform-specific code) !!!
        // System.out.print("\033[H\033[2J");
        System.out.println("\n\n\n\n\n");

        System.out.println("Simulation State:");
        char[][] grid = new char[20][20]; // Adjust the grid size as needed

        // Retrieve the list of cells from the CellManager
        List<Cell> cells = cellManager.getAllCells();

        // Determine cell position according to its id
        for (Cell cell : cells) {
            long x = cell.getId() % 20;
            long y = cell.getId() / 20;

            char cellSymbol = (cell.getType() == CellType.ASEXUATE) ? 'A' : 'S';
            grid[(int) y][(int) x] = cellSymbol;
        }

        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                if (grid[y][x] == '\0') {
                    grid[y][x] = '.'; // Empty space
                }
                System.out.print(grid[y][x] + " ");
            }
            System.out.println();
        }

        System.out.println("Food Units: " + foodManager.getFoodUnits());
        System.out.println("------------------------------------");
    }



//    private void printSimulationState() {
//        System.out.println("Simulation State:");
//        System.out.println("Food Units: " + foodManager.getFoodUnits());
//        for (Cell cell : cells) {
//            System.out.println("Cell " + cell.getId() + " - Type: " + cell.getType() +
//                    " - Hunger: " + cell.getHunger() +
//                    " - Reproduction Cycle: " + cell.getReproductionCycle());
//        }
//        System.out.println("------------------------------------");
//    }
}