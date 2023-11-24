public class GameOfLifeSimulation {
    public static void main(String[] args) {
        int initialFoodUnits = 0;  // Initial food units
        int initialCellCount = 5;   // Initial number of cells

        SimulationManager sim = new SimulationManager(initialFoodUnits, initialCellCount);
        sim.runSimulation();
    }

}
