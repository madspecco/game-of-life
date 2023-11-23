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

    public Cell(CellType type) {
        this.id = nextId++;
        this.type = type;
        this.hunger = (int) (Math.random() * 10) + 5;
        this.reproductionCycle = 0;
        this.cellLock = new ReentrantLock();
        // A reentrant lock is a mutual exclusion mechanism that allows threads to reenter into a lock
        // on a resource (multiple times) without a deadlock situation
    }

    // this represents the life cycle of the cell
    @Override
    public void run() {
        // Simulate cell actions and interactions here
        System.out.println("Cell ID: " + this.getCellId() + " has eaten ");
        System.out.println("Cell ID: " + this.getCellId() + " Hunger Level(before eating): " + this.getHunger());
        eat(); // Cell eats in each simulation step
        System.out.println("Cell ID: " + this.getCellId() + " Hunger Level(after eating): " + this.getHunger());

        System.out.println("Cell ID: " + this.getCellId() + " has starved " + "hunger level: " + this.getHunger());
        starve(); // Check for starvation

        System.out.println("\nCell ID: " + this.getCellId() + " has reproduced.");
        reproduce(); // Check for reproduction

        // Sleep for some time to represent the passage of time
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void eat() {
        if (hunger > 0) {
            // check if food was really eaten, then decrement hunger
            if (foodManager.consumeFood(this, 1)) {
                hunger--;
            }
            foodManager.consumeFood(this, 1);
            reproductionCycle++;
        }
    }

    public void starve() {
        if (hunger <= 0) {
            int foodUnitsDropped = (int) (Math.random() * 5) + 1;

            // lock the cell to avoid concurrency issues during 'death'
            cellLock.lock();
            try {
                // get the cell's food
                foodManager.replenishFood(foodUnitsDropped);

                // remove the cell from the simulation
                CellManager.removeCell(this);
            } finally {
                // unlock the cell
                cellLock.unlock();
            }
        }
    }

    public void reproduce() {
        if (reproductionCycle >= 10) {
            if (type == CellType.ASEXUATE) {
                Cell newCell1 = new Cell(CellType.ASEXUATE);
                Cell newCell2 = new Cell(CellType.ASEXUATE);
                CellManager.addCell(newCell1);
                CellManager.addCell(newCell2);

                //  reset the reproduction cycle !!!!!!!!!

                // function kinda updateTime() for each rep cycle from the SimManager update all cells
                reproductionCycle = 0;
            } else {
                // search for the sexuate cell to mate with
                List<Cell> cellList = CellManager.getAllCells();
                boolean match = false;
                
                for(int i=0; i< cellList.size(); i++) {
                    Cell currentCell = cellList.get(i);
                    if(currentCell.getType() == CellType.SEXUATE && currentCell.getReproductionCycle() >= 10) {
                    	// search for another Sexuate cell
                    	for(int j=0; j<cellList.size(); j++) {
                    		Cell otherCell = cellList.get(j);
                    		// check if match is found
                    		if(otherCell.getType() == CellType.SEXUATE && otherCell.getReproductionCycle() >= 10 && i != j) {
                    			match = true;
                                // set reproduction cycle of the OTHER cell to 0
                                otherCell.setReproductionCycle(0);
                    			break;
                    		}
                    	}
                    }
                }
                
                // add new cell to simulation if needed
                if(match) {
	                // CHORE: Create  new cell resulting from that interaction (Timi)
	                Cell newCell = new Cell(CellType.SEXUATE);
	                // CHORE: Logic to add the new cell to the simulation (Timi)
	                CellManager.addCell(newCell);
                }
            }
            reproductionCycle = 0;
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

    }
}
