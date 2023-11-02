public class GameOfLifeSimulation {
    public static void main(String[] args) {
        int initialFoodUnits = 100;  // Initial food units
        int initialCellCount = 5;   // Initial number of cells

        // SimManager controls the simulation
        SimulationManager simulation = new SimulationManager(initialFoodUnits, initialCellCount);

        simulation.runSimulation();
    }

}
