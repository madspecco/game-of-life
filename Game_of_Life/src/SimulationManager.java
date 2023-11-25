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
            CellType cellType = (i % 2 == 0) ? CellType.SEXUATE : CellType.ASEXUATE;
            Cell cell = new Cell(cellType);
            cell.updateFoodManagerFoodUnits(initialFoodUnits); // Set the food manager for each cell
            CellManager.addCell(cell); // Add the cell to the CellManager
        }

        // Start the simulation threads
        cellManager.updateCellState();
    }

    public void stopSimulation() {
        isRunning = false;
        cellManager.stopCells(); // Stop the cell threads using CellManager
    }

    public void runSimulation() {
        System.out.println("runSimulation() initiated.");
        while (isRunning) {
            if(simulationTime == 0) {
                System.out.println("Printing INITIAL grid");
                printSimulationStateGraphic();
            }
            simulationTime++;

            if(simulationTime == 0) {
                System.out.println("Printing INITIAL grid");
                printSimulationStateGraphic();
            }

            cellManager.performCellActions();
            System.out.println("cell life cycle updated (eat,starve,reproduce cycle");

            // Print the current state of the simulation
            printSimulationStateGraphic();
            System.out.println("Printing grid");

            if (simulationTime % 20 == 0) {
                stopSimulation();
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printSimulationStateGraphic() {
        // Clear the console to redraw the simulation state (you may need platform-specific code) !!!
        System.out.println("\n\n\n\n\n");

        System.out.println("Simulation State:");
        char[][] grid = new char[20][20]; // Adjust the grid size as needed

        // Retrieve the list of cells from the CellManager
        List<Cell> cells = cellManager.getAllCells();
        int foodUnitCount = 0;
        // Determine cell position according to its id
        for (Cell cell : cells) {
            long x = cell.getId() % 20;
            long y = cell.getId() / 20;

            char cellSymbol = (cell.getType() == CellType.ASEXUATE) ? 'A' : 'S';
            grid[(int) y][(int) x] = cellSymbol;

            foodUnitCount = cell.getFoodUnitCountFromFoodManager();
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

        //System.out.println("Food Units: " + foodManager.getFoodUnits());
        System.out.println("Food Units: " + foodUnitCount);
        System.out.println("------------------------------------");
    }
}