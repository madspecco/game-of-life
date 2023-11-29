import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Cell extends Thread {
    private static int nextId = 0;
    private final int id;           // unique id for the cell
    private final CellType type;    // type of the cell
    private int hunger;             // hunger state (satiation)

    private final FoodManager foodManager; //no food manager in cell

    public void setReproductionCycle(int reproductionCycle) {
        this.reproductionCycle = reproductionCycle;
    }

    private int reproductionCycle;  // at 10 cycles, the cell reproduces(by case)
    private final Lock cellLock;    // Lock object for managing concurrency issues


    public Cell(CellType type, FoodManager fm) {
        this.id = nextId++;
        this.type = type;
        this.hunger = 5;
        this.reproductionCycle = 0;
        this.cellLock = new ReentrantLock();
        this.foodManager = fm;
        // A reentrant lock is a mutual exclusion mechanism that allows threads to reenter into a lock
        // on a resource (multiple times) without a deadlock situation
    }
    public boolean eat() {
        if (hunger > 0) {
            // check if food was really eaten, then decrement hunger
            boolean foodEaten = foodManager.consumeFood(this, 1);
            if (foodEaten) {
                hunger -= 2;
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
                int foodUnitsDropped = 3;
                foodManager.replenishFood(foodUnitsDropped);

                // remove the cell from the simulation
                return true;
            }
            return false; // Cell is not starving
        } finally {
            // unlock the cell
            cellLock.unlock();
        }
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

    public void updateTime() {
        // for each cycle, hunger ++
        this.hunger = hunger + 2;
        this.reproductionCycle++;
    }
}
