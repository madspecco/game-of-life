public class GameOfLifeSimulation {
    public static void main(String[] args) {
<<<<<<< Updated upstream
        int initialFoodUnits = 10;  // Initial food units
        int initialCellCount = 3;   // Initial number of cells
=======
        int initialFoodUnits = 0;  // Initial food units
        int initialCellCount = 5;   // Initial number of cells
>>>>>>> Stashed changes

        SimulationManager sim = new SimulationManager(initialFoodUnits, initialCellCount);
        sim.runSimulation();
        // Cell cell1 = new Cell(CellType.SEXUATE);
        // Cell cell2 = new Cell(CellType.SEXUATE);
        // System.out.println(cell2.getCellId());
    }

}
