import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


// Cell class inherits the Thread object
public class Cell extends Thread {
    private static int nextId = 0;
    private final int id;           // unique id for the cell
    private final CellType type;    // type of the cell
    private int hunger;             // hunger state (satiation)

    private static FoodManager foodManager;

    public void setReproductionCycle(int reproductionCycle) {
        this.reproductionCycle = reproductionCycle;
    }

    private int reproductionCycle;  // at 10 cycles, the cell reproduces(by case)
    private Lock cellLock;          // Lock object for managing concurrency issues

    static {
        // Code to be executed once when the class is loaded
        foodManager = new FoodManager(0);
    }
    public Cell(CellType type) {
        this.id = nextId++;
        this.type = type;
        this.hunger = (int) (Math.random() * 8) + 2;
        this.reproductionCycle = 0;
        this.cellLock = new ReentrantLock();
        // A reentrant lock is a mutual exclusion mechanism that allows threads to reenter into a lock
        // on a resource (multiple times) without a deadlock situation
    }
    public boolean eat() {
        if (hunger > 0) {
            // check if food was really eaten, then decrement hunger
            boolean foodEaten = foodManager.consumeFood(this, 1);
            if (foodEaten) {
                hunger--;
            }
            reproductionCycle++;
            return foodEaten;
        }
        return false;
    }

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
        }
    }


    public boolean reproduce() {
        if (reproductionCycle >= 10) {
            if (type == CellType.ASEXUATE) {
                Cell newCell1 = new Cell(CellType.ASEXUATE);
                Cell newCell2 = new Cell(CellType.ASEXUATE);

                CellManager.addCell(newCell1);
                CellManager.addCell(newCell2);

                // Reset the reproduction cycle
                reproductionCycle = 0;

                return true; // Reproduction occurred
            } else {
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

    public void setFoodManager(FoodManager foodManager) {
        this.foodManager = foodManager;
    }

    // Locking and unlocking the threads (concurrency issue)
    public void acquireLock() {
        cellLock.lock();
    }

    public void releaseLock() {
        cellLock.unlock();
    }

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
}
