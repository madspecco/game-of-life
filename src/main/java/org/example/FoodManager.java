package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FoodManager {
    private int foodUnits;  // Total food units available

    private int deadCellFoodUnits = 0;
    private final Lock foodLock;  // For synchronization, same usage as the cellLock, the name differs

    public FoodManager(int initialFoodUnits) {
        this.foodUnits = initialFoodUnits;
        this.foodLock = new ReentrantLock();
    }

    public boolean consumeFood(Cell cell, int amount) {
        foodLock.lock();
        try {
            if (foodUnits >= amount) {
                foodUnits -= amount;
                // cell.eat(); // Notify the cell that it has eaten
                return true; // Food consumed successfully
            }
            return false; // Not enough food available
        } finally {
            foodLock.unlock();
        }
    }

    // increase food units
    public void replenishFood(int amount) {
        foodLock.lock();
        try {
            deadCellFoodUnits += amount;
        } finally {
            foodLock.unlock();
        }
    }

    public void addDeadCellFoodUnits() {
        this.foodUnits += deadCellFoodUnits;
        deadCellFoodUnits = 0;
    }

    public int getFoodUnits() {
        return foodUnits;
    }

    public void setFoodUnits(int foodUnits) {
        this.foodUnits = foodUnits;
    }
}