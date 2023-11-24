import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


// Cell class inherits the Thread object
public class Cell extends Thread {
    private static int nextId = 0;
    private final int id;           // unique id for the cell
    private final CellType type;    // type of the cell
    private int hunger;             // hunger state (satiation)
    private int reproductionCycle;  // at 10 cycles, the cell reproduces(by case)
    private Lock cellLock;          // Lock object for managing concurrency issues

    static {
        // Code to be executed once when the class is loaded
        foodManager = new FoodManager(0);
    }
    public Cell(CellType type) {
        this.id = nextId++;
        this.type = type;
<<<<<<< Updated upstream
        this.hunger = 0;
=======
        this.hunger = (int) (Math.random() * 8) + 2;
>>>>>>> Stashed changes
        this.reproductionCycle = 0;
        this.cellLock = new ReentrantLock();
        // A reentrant lock is a mutual exclusion mechanism that allows threads to reenter into a lock
        // on a resource (multiple times) without a deadlock situation
    }

    // this represents the life cycle of the cell
<<<<<<< Updated upstream
    @Override
    public void run() {
        while (true) {
            try {
                // Simulate cell actions and interactions here
                eat(); // Cell eats in each simulation step
                starve(); // Check for starvation
                reproduce(); // Check for reproduction

                // Sleep for some time to represent the passage of time
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Handle interruptions if necessary
                break;
            }
        }
    }

    public void eat() {
        if (hunger > 0) {
            // CHORE: Simulate eating behavior (Bolos)
            hunger--;
            // CHORE: Implement logic to decrement food units or handle food resource management (Bolos)
=======
    //@Override
//    public void run() {
//        // Simulate cell actions and interactions here
//        System.out.println("Cell ID: " + this.getCellId() + " has eaten ");
//        System.out.println("Cell ID: " + this.getCellId() + " Hunger Level(before eating): " + this.getHunger());
//        eat(); // Cell eats in each simulation step
//        System.out.println("Cell ID: " + this.getCellId() + " Hunger Level(after eating): " + this.getHunger());
//
//        System.out.println("Cell ID: " + this.getCellId() + " has starved " + "hunger level: " + this.getHunger());
//        starve(); // Check for starvation
//
//        System.out.println("\nCell ID: " + this.getCellId() + " has reproduced.");
//        reproduce(); // Check for reproduction
//
//        // Sleep for some time to represent the passage of time
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public boolean eat() {
        if (hunger > 0) {
            // check if food was really eaten, then decrement hunger
            boolean foodEaten = foodManager.consumeFood(this, 1);
            if (foodEaten) {
                hunger--;
            }
            reproductionCycle++;
            return foodEaten;
>>>>>>> Stashed changes
        }
        return false;
    }

<<<<<<< Updated upstream
    public void starve() {
        if (hunger <= 0) {
            // CHORE: Implement the tasks below (Bolos)
            // Simulate starvation and cell death
            // Drop a random number of food units (1 to 5) when the cell dies
            int foodUnitsDropped = (int) (Math.random() * 5) + 1;
            // Logic to drop food units and handle cell death
=======
    public boolean starve() {
        cellLock.lock();
        try {
            if (hunger >= 10) {
                int foodUnitsDropped = (int) (Math.random() * 5) + 1;

                // get the cell's food
                foodManager.replenishFood(foodUnitsDropped);

                // remove the cell from the simulation
                return true; // Cell is starved , should be deleted
            }
            return false; // Cell is not starving
        } finally {
            // unlock the cell
            cellLock.unlock();
>>>>>>> Stashed changes
        }
    }


    public boolean reproduce() {
        if (reproductionCycle >= 10) {
            // CHORE: Check if the cell is ready to reproduce (Horia)
            if (type == CellType.ASEXUATE) {
                // CHORE: Reproduction for asexuate cells: division into two new cells (Horia)
                Cell newCell1 = new Cell(CellType.ASEXUATE);
                Cell newCell2 = new Cell(CellType.ASEXUATE);
<<<<<<< Updated upstream
                // CHORE: Implement logic to add new cells to the simulation (Horia)

                //  reset the reproduction cycle
=======

                CellManager.addCell(newCell1);
                CellManager.addCell(newCell2);

                // Reset the reproduction cycle
>>>>>>> Stashed changes
                reproductionCycle = 0;

                return true; // Reproduction occurred
            } else {
<<<<<<< Updated upstream
                // CHORE: Reproduction for sexuate cells: interaction with other cells (Horia)
                // CHORE: Implement logic to find a suitable/matching/fit partner cell for reproduction (Timi)
                // CHORE: Create  new cell resulting from that interaction (Timi)
                Cell newCell = new Cell(CellType.SEXUATE);
                // CHORE: Logic to add the new cell to the simulation (Timi)

                // CHORE: reset the reproduction cycle (Timi)
                reproductionCycle = 0;
=======
                // Search for the sexuate cell to mate with
                List<Cell> cellList = CellManager.getAllCells();
                boolean match = false;

                for (int i = 0; i < cellList.size(); i++) {
                    Cell currentCell = cellList.get(i);
                    if (currentCell.getType() == CellType.SEXUATE && currentCell.getReproductionCycle() >= 10) {
                        // Search for another Sexuate cell
                        for (int j = 0; j < cellList.size(); j++) {
                            Cell otherCell = cellList.get(j);
                            // Check if match is found
                            if (otherCell.getType() == CellType.SEXUATE && otherCell.getReproductionCycle() >= 10 && i != j) {
                                match = true;
                                // Set reproduction cycle of the OTHER cell to 0
                                otherCell.setReproductionCycle(0);
                                break;
                            }
                        }
                    }
                }
                // Add new cell to simulation if needed
                if (match) {
                    // Create a new cell resulting from that interaction
                    Cell newCell = new Cell(CellType.SEXUATE);
                    // Logic to add the new cell to the simulation
                    CellManager.addCell(newCell);

                    // Reset the reproduction cycle
                    reproductionCycle = 0;

                    return true; // Reproduction occurred
                }
>>>>>>> Stashed changes
            }
        }
        return false; // Reproduction did not occur
    }



    public int getCellId() {
        return id;
    }


    public CellType getType() {
        return type;
    }

    public int getHunger() {
        return hunger;
    }


    public int getReproductionCycle() {
        return reproductionCycle;
    }
    public int getFoodUnitCountFromFoodManager() {
        return foodManager.getFoodUnits();
    }
    public static FoodManager getFoodManager() {
        return foodManager;
    }

    // Locking and unlocking the threads (concurrency issue)
    public void acquireLock() {
        cellLock.lock();
    }

    public void releaseLock() {
        cellLock.unlock();
    }
<<<<<<< Updated upstream
=======

    public void updateTime() {
        // for each cycle, hunger ++
        this.hunger = hunger + 2;
        this.reproductionCycle++;
    }

    public void updateFoodManagerFoodUnits(int number) {
        // update global food manager with this number of food units
        foodManager.setFoodUnits(number);
    }

    public int getFoodManagerFoodUnits() {
        // update global food manager with this number of food units
        return foodManager.getFoodUnits();
    }
>>>>>>> Stashed changes
}
