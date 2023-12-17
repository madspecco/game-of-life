package main;

import simulation.SimulationManager;

public class Main {
    public static void main(String[] args) {
        int initialFoodUnits = 1000;
        int initialCellCount = 10;
        SimulationManager sim = new SimulationManager(initialFoodUnits, initialCellCount);
        sim.runSimulation();
    }
}