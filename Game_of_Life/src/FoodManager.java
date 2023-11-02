import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FoodManager {
    private int foodUnits;  // Total food units available
    private Lock foodLock;  // For synchronization

    public FoodManager(int initialFoodUnits) {
        this.foodUnits = initialFoodUnits;
        this.foodLock = new ReentrantLock();
    }

    public boolean consumeFood(Cell cell, int amount) {
        foodLock.lock();
        try {
            if (foodUnits >= amount) {
                foodUnits -= amount;
                cell.eat(); // Notify the cell that it has eaten
                return true; // Food consumed successfully
            }
            return false; // Not enough food available
        } finally {
            foodLock.unlock();
        }
    }

    public void replenishFood(int amount) {
        foodLock.lock();
        try {
            foodUnits += amount;
        } finally {
            foodLock.unlock();
        }
    }

    public int getFoodUnits() {
        return foodUnits;
    }
}
