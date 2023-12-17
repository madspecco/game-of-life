package simulation;

import types.CellType;
import utilities.SimLogger;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.List;

import static types.EventType.*;

public class Cell extends Thread {
    private static int nextId = 0;
    private final int id;

    private final CellType type;

    private int saturation;
    public final int T_full = 5;
    public final int T_starve = -5;
    private static FoodManager foodManager;
    private int xPos;
    private int yPos;
    private int reproductionCycle;
    private boolean alive = true;
    Semaphore foodSem;
    Semaphore cellSem;
    Semaphore reprSem;
    private final CellManager parentManager;

    public boolean readyToReproduce = false;

    public int getxPos() {
        return this.xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return this.yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public void setReproductionCycle(int reproductionCycle) {
        this.reproductionCycle = reproductionCycle;
    }

    public Cell(CellType type, FoodManager fm, Semaphore fs, Semaphore cs,Semaphore rs, CellManager parentManager) {
        this.id = nextId++;
        this.type = type;
        this.saturation = 0;
        this.reproductionCycle = 0;
        foodManager = fm;
        foodSem = fs;
        cellSem = cs;
        reprSem = rs;
        this.parentManager = parentManager;
    }

    // food related functions
    private void eat() throws InterruptedException {
        // First, get a permit.
        System.out.println("Cell " + id + " is waiting for a foodManager permit.");

        // acquiring the lock
        if( foodSem.tryAcquire(100, TimeUnit.MILLISECONDS) ) {

            System.out.println("Cell " + id + " gets a foodManager permit.");

            // Now, accessing the shared resource.
            boolean hasEaten = foodManager.consumeFood();

            // Release the permit.
            foodSem.release();

            // Check if the cell found food and act accordingly
            if (hasEaten) {
                this.saturation = T_full;
                this.reproductionCycle = this.reproductionCycle + 1;
                System.out.println("Cell " + id + " has eaten");

                // mq
                SimLogger.sendMessage(FOOD_CONSUMED, "Cell " + id + " has eaten.");

            } else {
                System.out.println("Cell " + id + " could not eat. No food left.");
            }
        }
        else{
            System.out.println("Cell " + id + " didn't find a foodManager permit");
        }
    }

    public void starve() throws InterruptedException {
        // First, get a permit.
        System.out.println("Cell " + id + " is waiting for a foodManager permit.");

        // acquiring the lock
        foodSem.acquire();

        System.out.println("Cell " + id + " gets a foodManager permit.");

        int foodUnitsDropped = (int)(Math.random() * 4.0) + 1;
        // mq
        foodManager.replenishFood(foodUnitsDropped);

        foodSem.release();


    }

    public void reproduceCell() {


        int cellCap = 64;
        if (this.type == CellType.ASEXUATE) {

            Cell newCell1 = new Cell(CellType.ASEXUATE, foodManager, this.foodSem, this.cellSem, this.reprSem, this.parentManager);

            try {
                if(this.parentManager.getCellPopulation() < cellCap) {
                    System.out.println("Cell " + id + " is waiting for a cellManager permit.");
                    cellSem.acquire();
                    System.out.println("Cell " + id + " gets a cellManager permit.");

                    parentManager.addCell(newCell1);
                    newCell1.start();

                    System.out.println("Cell " + newCell1.id + " born. (A)");

                    this.reproductionCycle = 0;
                    this.saturation = 0;

                    // mq
                    SimLogger.sendMessage(REPRODUCTION_A, "Cell " + id + " has reproduced." +
                            "\nCell " + newCell1.id + " has spawned.");
                    cellSem.release();
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        else{
            try {
                System.out.println("Cell " + id + " is waiting for a reproduction permit.");
                reprSem.acquire();

                if(reprSem.availablePermits() == 1){
                    //first cell
                    this.readyToReproduce = true;
                    Thread.sleep(5);
                    this.readyToReproduce = false;
                }
                else if(reprSem.availablePermits() == 0){
                    //second cell
                    List<Cell> cells = parentManager.getAllCells();
                    for (Cell cell : cells){
                        if(cell.readyToReproduce){
                            Cell newCell1 = new Cell(CellType.SEXUATE, foodManager, this.foodSem, this.cellSem, this.reprSem, this.parentManager);
                            try {
                                if(this.parentManager.getCellPopulation() < cellCap){
                                    System.out.println("Cell " + id + " is waiting for a cellManager permit.");
                                    cellSem.acquire();
                                    System.out.println("Cell " + id + " gets a cellManager permit.");

                                    parentManager.addCell(newCell1);
                                    newCell1.start();

                                    System.out.println("Cell " + newCell1.id + " born. (S)");

                                    this.reproductionCycle = 0;
                                    this.saturation = 0;

                                    cell.setSaturation(0);
                                    cell.setReproductionCycle(0);

                                    // mq
                                    SimLogger.sendMessage(REPRODUCTION_S, "Cell " + cell.id +
                                            " has reproduced with Cell " + this.id + "." +
                                            "\nCell " + newCell1.id + " has spawned.");
                                    cellSem.release();
                                }

                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    Thread.sleep(5);
                }

                reprSem.release();


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void run(){
        while(this.alive){
            try {
                Thread.sleep(100);
                this.saturation = this.saturation - 1;

                if(saturation <= 0){
                    this.eat();
                }

                if(saturation <= T_starve){
                    this.alive = false;
                }

                if(reproductionCycle >= 10){
                    this.reproduceCell();
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Cell " + id + " saturation is " + this.saturation);
        }

        try {
            this.starve();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Cell " + id + " is waiting for a cellManager permit.");
            cellSem.acquire();
            System.out.println("Cell " + id + " gets a cellManager permit.");

            parentManager.removeCell(this);

            System.out.println("Cell " + id + " died.");
            // mq
            SimLogger.sendMessage(DEATH, "Cell " + id + " has died.");

            cellSem.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCellId() {
        return this.id;
    }

    public CellType getType() {
        return this.type;
    }

    public int getSaturation() {
        return this.saturation;
    }

    public int getReproductionCycle() {
        return this.reproductionCycle;
    }

    public static int getFoodUnitCountFromFoodManager() {
        return foodManager.getFoodUnits();
    }

}