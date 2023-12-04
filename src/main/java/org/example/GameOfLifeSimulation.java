package org.example;

public class GameOfLifeSimulation {
    public static void main(String[] args) {
        int initialFoodUnits = 1000;     // Initial food units
        int initialCellCount = 10;       // Initial number of cells

        SimulationManager sim = new SimulationManager(initialFoodUnits, initialCellCount);
        sim.runSimulation();
    }

}
