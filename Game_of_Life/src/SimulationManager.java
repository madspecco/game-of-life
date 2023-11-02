import java.util.ArrayList;
import java.util.List;

public class SimulationManager {
    private List<Cell> cells;
    private FoodManager foodManager;
    private boolean isRunning;

    public SimulationManager(int initialFoodUnits, int initialCellCount) {
        foodManager = new FoodManager(initialFoodUnits);
        cells = new ArrayList<>();
        isRunning = true;

        // Create and add initial cells to the simulation
        for (int i = 0; i < initialCellCount; i++) {
            CellType cellType = (i % 2 == 0) ? CellType.ASEXUATE : CellType.SEXUATE;
            cells.add(new Cell(cellType));
        }

        // Start the simulation threads
        for (Cell cell : cells) {
            cell.start();
        }
    }

    public void stopSimulation() {
        isRunning = false;
        for (Cell cell : cells) {
            cell.interrupt(); // Interrupt all cell threads to stop the simulation
        }
    }

    public void runSimulation() {
        while (isRunning) { // TO BE IMPLEMENTED !!!!
            // Simulate the progression of time
            // Implement any other global simulation logic here

            // Print the current state of the simulation
           // printSimulationState();
            printSimulationStateGraphic();  //Graphic Version prints a "map" version in the console

            // Sleep for a specified interval to control the simulation speed
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printSimulationStateGraphic() {
        // Clear the console to redraw the simulation state (you may need platform-specific code) !!!
        System.out.print("\033[H\033[2J");

        System.out.println("Simulation State:");
        char[][] grid = new char[20][20]; // Adjust the grid size as needed

        for (Cell cell : cells) {
            long x = cell.getId() % 20;
            long y = cell.getId() / 20;

            char cellSymbol = (cell.getType() == CellType.ASEXUATE) ? 'A' : 'S';
            grid[(int)y][(int)x] = cellSymbol;
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


    private void printSimulationState() {
        System.out.println("Simulation State:");
        System.out.println("Food Units: " + foodManager.getFoodUnits());
        for (Cell cell : cells) {
            System.out.println("Cell " + cell.getId() + " - Type: " + cell.getType() +
                    " - Hunger: " + cell.getHunger() +
                    " - Reproduction Cycle: " + cell.getReproductionCycle());
        }
        System.out.println("------------------------------------");
    }

    public static void main(String[] args) {
        // Initialize and start the simulation
        SimulationManager simulation = new SimulationManager(100, 5); // Initial food units and cell count
        simulation.runSimulation();
        simulation.stopSimulation();
    }
}
