import java.util.ArrayList;
import java.util.List;

public class SimulationManager {
    private List<Cell> cells;           // list of cells that are in the simulation
    private FoodManager foodManager;    // instance of FoodManager
    private boolean isRunning;          // flag for running state

    public SimulationManager(int initialFoodUnits, int initialCellCount) {
        foodManager = new FoodManager(initialFoodUnits);
        cells = new ArrayList<>();
        isRunning = true;

        // Create and add initial cells to the simulation
        for (int i = 0; i < initialCellCount; i++) {
            // CHORE: Think of another logic for spawning the cells' type
            CellType cellType = (i % 2 == 0) ? CellType.ASEXUATE : CellType.SEXUATE;
            cells.add(new Cell(cellType));
        }

        // Start the simulation threads
        // start() method is from the Thread Class, since Cell extends Thread
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
        while (isRunning) {
            // CHORE: Simulate the progression of time
            // CHORE: Implement any other global simulation logic here

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
        char[][] grid = new char[10][10]; // Adjust the grid size as needed

        // CHORE: Determine cell position according to its id (maybe change the logic)
        for (Cell cell : cells) {
            long x = cell.getId() % 10;
            long y = cell.getId() / 10;

            char cellSymbol = (cell.getType() == CellType.ASEXUATE) ? 'A' : 'S';
            grid[(int)y][(int)x] = cellSymbol;
        }

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
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
}
