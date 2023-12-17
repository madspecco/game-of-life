package simulation;

import types.CellType;
import utilities.CellGUI;
import utilities.SimulationGUI;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class SimulationManager {
    private final CellManager cellManager;
    Semaphore foodSem = new Semaphore(1);
    Semaphore cellSem = new Semaphore(1);
    Semaphore reprSem = new Semaphore(2);
    private boolean isRunning;
    private int simulationTime;
    private final CellGUI cellGUI;
    private final SimulationGUI simulationGUI;

    public SimulationManager(int initialFoodUnits, int initialCellCount) {
        FoodManager foodManager = new FoodManager(initialFoodUnits);
        this.cellManager = new CellManager();
        this.isRunning = true;
        this.simulationTime = 0;
        Random random = new Random();

        for(int i = 0; i < initialCellCount; ++i) {
            double randomValue = random.nextDouble();
            CellType cellType = randomValue < 0.5 ? CellType.ASEXUATE : CellType.SEXUATE;
            Cell cell = new Cell(cellType, foodManager, this.foodSem, this.cellSem, this.reprSem, this.cellManager);
            this.cellManager.addCell(cell);
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int xCoordinate = screenWidth / 2 - 39;
        int yCoordinate = screenHeight / 2 - 400;
        JFrame frame = new JFrame("Game Of Life - Cell Simulation");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        java.util.List<Cell> cellsForGUI = this.cellManager.getAllCells();
        this.cellGUI = new CellGUI(cellsForGUI);
        frame.add(this.cellGUI);
        frame.setSize(600, 627);
        frame.setVisible(true);
        frame.setResizable(false);
        this.simulationGUI = new SimulationGUI(cellsForGUI);
        JFrame frame1 = new JFrame("Game Of Life - Cell Simulation Process");
        frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame1.add(this.simulationGUI);
        frame1.setSize(600, 627);
        frame1.setVisible(true);
        frame1.setResizable(true);
        frame1.setLocation(xCoordinate, yCoordinate);
    }

    public void runSimulation() {
        this.cellManager.startCells();
        System.out.println("runSimulation() initiated.");

        while(this.isRunning) {
            ++this.simulationTime;
            this.cellGUI.updateCells(this.cellManager.getAllCells());
            this.simulationGUI.updateCells(this.cellManager.getAllCells());
            if (this.simulationTime % 10000000 == 0) {
                this.isRunning = false;
            }

            try {
                Thread.sleep(30L);
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }
        }

        this.cellManager.stopCells();
    }
}
