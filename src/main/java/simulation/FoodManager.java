package simulation;

import utilities.SimLogger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static types.EventType.FOOD_REPLENISHED;

public class FoodManager {
    private int foodUnits;
    private final Lock foodLock;

    public FoodManager(int initialFoodUnits) {
        this.foodUnits = initialFoodUnits;
        this.foodLock = new ReentrantLock();
    }

    public boolean consumeFood(){
        this.foodLock.lock();

        try {
            if(this.foodUnits > 0) {
                this.foodUnits = this.foodUnits - 1;
                return true;
            }
            else{
                return false;
            }
        } finally {
            this.foodLock.unlock();
        }
    }

    public void replenishFood(int amount) {
        this.foodLock.lock();

        try {
            this.foodUnits += amount;
            // mq
            SimLogger.sendMessage(FOOD_REPLENISHED, "Replenished " + amount + " food units.");
        } finally {
            this.foodLock.unlock();
        }

    }

    public int getFoodUnits() {
        return this.foodUnits;
    }
}
