public class GameOfLifeSimulation {
    public static void main(String[] args) {
        int initialFoodUnits = 10;  // Initial food units
        int initialCellCount = 3;   // Initial number of cells

        SimulationManager sim = new SimulationManager(initialFoodUnits, initialCellCount);
        sim.runSimulation();
        // Cell cell1 = new Cell(CellType.SEXUATE);
        // Cell cell2 = new Cell(CellType.SEXUATE);
        // System.out.println(cell2.getCellId());
    }

}
