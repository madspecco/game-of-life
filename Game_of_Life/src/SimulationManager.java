import java.util.List;
import java.util.Random;

public class SimulationManager {
    private final CellManager cellManager;      // Instance of CellManager
    private final FoodManager foodManager;      // Instance of FoodManager
    private boolean isRunning;                  // Flag for running state
    private int simulationTime;

    public SimulationManager(int initialFoodUnits, int initialCellCount) {
        foodManager = new FoodManager(initialFoodUnits);
        cellManager = new CellManager(this.foodManager); // Create an instance of CellManager
        isRunning = true;
        simulationTime = 0;
        Random random = new Random();
        // Create and add initial cells to the simulation using the CellManager
        for (int i = 0; i < initialCellCount; i++) {
            // Generate a random number between 0 and 1
            double randomValue = random.nextDouble();

            // Decide the cell type based on the random number
            CellType cellType = (randomValue < 0.5) ? CellType.ASEXUATE : CellType.SEXUATE;

            // Create the cell based on the determined type
            Cell cell = new Cell(cellType, this.foodManager); // Set the food manager for each cell
            CellManager.addCell(cell); // Add the cell to the CellManager
        }

        // Start the simulation threads
        cellManager.startCells();
    }

    public void stopSimulation() {
        isRunning = false;
        cellManager.stopCells(); // Stop the cell threads using CellManager
    }

    public void runSimulation() {
        System.out.println("runSimulation() initiated.");
        while (isRunning) {
            simulationTime++;

            cellManager.performCellActions();
            foodManager.addDeadCellFoodUnits();

            printStatistics();

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

    private void printStatistics() {
        System.out.println("\n\n\n\n----Statistics----\n");

        int asexuate_count = 0;
        int sexuate_count = 0;
        int foodUnitCount = 0;

        List<Cell> cells = CellManager.getAllCells();
        for(Cell cell : cells) {
            char cellSymbol = (cell.getType() == CellType.ASEXUATE) ? 'A' : 'S';
            if (cellSymbol == 'A') {
                asexuate_count++;
            } else {
                sexuate_count++;
            }

            System.out.println(cellSymbol + " Cell ID: " + cell.getCellId() + " hunger - " + cell.getHunger() + " reprC - " + cell.getReproductionCycle());
            foodUnitCount = cell.getFoodUnitCountFromFoodManager();
        }

        System.out.println("food units: " + foodUnitCount);

        System.out.println("asexuate cells: " + asexuate_count);
        System.out.println("sexuate cells: " + sexuate_count);
    }
}
