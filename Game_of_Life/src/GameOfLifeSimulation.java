//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class GameOfLifeSimulation {
    public GameOfLifeSimulation() {
    }

    public static void main(String[] args) {
        int initialFoodUnits = 1000;
        int initialCellCount = 10;
        SimulationManager sim = new SimulationManager(initialFoodUnits, initialCellCount);
        sim.runSimulation();
    }
}
