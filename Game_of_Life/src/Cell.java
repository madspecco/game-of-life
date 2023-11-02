import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cell extends Thread {
    private static int nextId = 1;
    private final int id;
    private final CellType type;
    private int hunger;
    private int reproductionCycle;
    private Lock cellLock;

    public Cell(CellType type) {
        this.id = nextId++;
        this.type = type;
        this.hunger = 0;
        this.reproductionCycle = 0;
        this.cellLock = new ReentrantLock();
    }

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
            // Simulate eating behavior
            hunger--;
            // Logic to decrement food units or handle food resource management
        }
    }

    public void starve() {
        if (hunger <= 0) {
            // Simulate starvation and cell death
            // Drop a random number of food units (1 to 5) when the cell dies
            int foodUnitsDropped = (int) (Math.random() * 5) + 1;
            // Logic to drop food units and handle cell death
        }
    }

    public void reproduce() {  // TO BE IMPLEMENTED !!!!
        if (reproductionCycle >= 10) {
            // Check if the cell is ready to reproduce
            if (type == CellType.ASEXUATE) {
                // Reproduction for asexuate cells: division into two new cells
                Cell newCell1 = new Cell(CellType.ASEXUATE);
                Cell newCell2 = new Cell(CellType.ASEXUATE);
                // Implement logic to add new cells to the simulation

                //  reset the reproduction cycle
                reproductionCycle = 0;
            } else { // TO BE IMPLEMENTED !!!!
                // Reproduction for sexuate cells: interaction with other cells
                // Implement logic to find a suitable/macthing/fit partner cell for reproduction
                // Create  new cell resulting from that interaction
                Cell newCell = new Cell(CellType.SEXUATE);
                // TO BE IMPLEMENTED !!!!
                // Logic to add the new cell to the simulation

                // reset the reproduction cycle
                reproductionCycle = 0;
            }
        }
    }

//    public int getId() {
//        return id;
//    }
    //Careful because of the getId() from Thread class !
    // Grija daca va incurca mai departe :)

    public CellType getType() {
        return type;
    }

    public int getHunger() {
        return hunger;
    }

    public int getReproductionCycle() {
        return reproductionCycle;
    }

    public void acquireLock() {
        cellLock.lock();
    }

    public void releaseLock() {
        cellLock.unlock();
    }
}
