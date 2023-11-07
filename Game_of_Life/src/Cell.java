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

    public Cell(CellType type) {
        this.id = nextId++;
        this.type = type;
        this.hunger = 0;
        this.reproductionCycle = 0;
        this.cellLock = new ReentrantLock();
        // A reentrant lock is a mutual exclusion mechanism that allows threads to reenter into a lock
        // on a resource (multiple times) without a deadlock situation
    }

    // this represents the life cycle of the cell
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
            // CHORE: Simulate eating behavior
            hunger--;
            // CHORE: Implement logic to decrement food units or handle food resource management
        }
    }

    public void starve() {
        if (hunger <= 0) {
            // CHORE: Implement the tasks below
            // Simulate starvation and cell death
            // Drop a random number of food units (1 to 5) when the cell dies
            int foodUnitsDropped = (int) (Math.random() * 5) + 1;
            // Logic to drop food units and handle cell death
        }
    }

    public void reproduce() {
        if (reproductionCycle >= 10) {
            // CHORE: Check if the cell is ready to reproduce
            if (type == CellType.ASEXUATE) {
                // CHORE: Reproduction for asexuate cells: division into two new cells
                Cell newCell1 = new Cell(CellType.ASEXUATE);
                Cell newCell2 = new Cell(CellType.ASEXUATE);
                // CHORE: Implement logic to add new cells to the simulation

                //  reset the reproduction cycle
                reproductionCycle = 0;
            } else {
                // CHORE: Reproduction for sexuate cells: interaction with other cells
                // CHORE: Implement logic to find a suitable/macthing/fit partner cell for reproduction
                // CHORE: Create  new cell resulting from that interaction
                Cell newCell = new Cell(CellType.SEXUATE);
                // CHORE: Logic to add the new cell to the simulation

                // CHORE: reset the reproduction cycle
                reproductionCycle = 0;
            }
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

    // Locking and unlocking the threads (concurrency issue)
    public void acquireLock() {
        cellLock.lock();
    }

    public void releaseLock() {
        cellLock.unlock();
    }
}
