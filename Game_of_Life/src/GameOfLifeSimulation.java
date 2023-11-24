public class GameOfLifeSimulation {
    public static void main(String[] args) {
        int initialFoodUnits = 10;  // Initial food units
        int initialCellCount = 7;   // Initial number of cells

        SimulationManager sim = new SimulationManager(initialFoodUnits, initialCellCount);
        sim.runSimulation();
    }

}
