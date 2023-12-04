package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Cell extends Thread {
    private static int nextId = 0;
    private final int id;           // unique id for the cell
    private final CellType type;    // type of the cell

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    private int saturation;             // hunger state (satiation)

    public final int T_full = 5;
    public final int T_starve = -5;

    private static FoodManager foodManager; //no food manager in cell

    private int xPos;

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    private int yPos;

    public void setReproductionCycle(int reproductionCycle) {
        this.reproductionCycle = reproductionCycle;
    }

    private int reproductionCycle;  // at 10 cycles, the cell reproduces(by case)
    private final Lock cellLock;    // Lock object for managing concurrency issues


    public Cell(CellType type, FoodManager fm) {
        this.id = nextId++;
        this.type = type;
        this.saturation = 0;
        this.reproductionCycle = 0;
        this.cellLock = new ReentrantLock();
        this.foodManager = fm;
        // A reentrant lock is a mutual exclusion mechanism that allows threads to reenter into a lock
        // on a resource (multiple times) without a deadlock situation
    }
    public boolean eat() {
        if (saturation <= 0) {
            // check if food was really eaten, then decrement hunger
            boolean foodEaten = foodManager.consumeFood(this, 1);
            if (foodEaten) {
                saturation = T_full;
                reproductionCycle++;
            }
            return foodEaten;
        }
        return false;
    }

    public boolean starve() {
        cellLock.lock();
        try {
            if (saturation == T_starve) {
                int foodUnitsDropped = (int) (Math.random() * 4) + 1;
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

    public int getSaturation() {
        return saturation;
    }

    public int getReproductionCycle() {
        return reproductionCycle;
    }

    public static int getFoodUnitCountFromFoodManager() {
        return foodManager.getFoodUnits();
    }

    public void updateTime() {
        // for each cycle, hunger --
        this.saturation = saturation - 1;
    }
}