//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import javax.swing.JFrame;

public class SimulationManager {
    private final CellManager cellManager;
    private final FoodManager foodManager;
    Semaphore foodSem = new Semaphore(1);
    Semaphore cellSem = new Semaphore(1);
    Semaphore reprSem = new Semaphore(2);
    private boolean isRunning;
    private int simulationTime;
    private JFrame frame;
    private JFrame frame1;
    private CellGUI cellGUI;
    private SimulationGUI simulationGUI;

    public int getSimulationTime() {
        return this.simulationTime;
    }

    public SimulationManager(int initialFoodUnits, int initialCellCount) {
        this.foodManager = new FoodManager(initialFoodUnits);
        this.cellManager = new CellManager();
        this.isRunning = true;
        this.simulationTime = 0;
        Random random = new Random();

        for(int i = 0; i < initialCellCount; ++i) {
            double randomValue = random.nextDouble();
            CellType cellType = randomValue < 0.5 ? CellType.ASEXUATE : CellType.SEXUATE;
            Cell cell = new Cell(cellType, this.foodManager, foodSem, cellSem, reprSem, this.cellManager);
            cellManager.addCell(cell);
        }

        // GUI setup
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int xCoordinate = screenWidth / 2 - 39;
        int yCoordinate = screenHeight / 2 - 400;
        this.frame = new JFrame("Game Of Life - Cell Simulation");
        this.frame.setDefaultCloseOperation(3);
        CellManager var10000 = this.cellManager;
        List<Cell> cellsForGUI = cellManager.getAllCells();
        this.cellGUI = new CellGUI(cellsForGUI);
        this.frame.add(this.cellGUI);
        this.frame.setSize(600, 627);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.simulationGUI = new SimulationGUI(cellsForGUI);
        this.frame1 = new JFrame("Game Of Life - Cell Simulation Process");
        this.frame1.setDefaultCloseOperation(3);
        this.frame1.add(this.simulationGUI);
        this.frame1.setSize(600, 627);
        this.frame1.setVisible(true);
        this.frame1.setResizable(false);
        this.frame1.setLocation(xCoordinate, yCoordinate);
    }


    public void runSimulation() {
        this.cellManager.startCells();
        System.out.println("runSimulation() initiated.");

        while(this.isRunning) {
            ++this.simulationTime;

            //this.printStatistics();

            this.cellGUI.updateCells(cellManager.getAllCells());
            this.simulationGUI.updateCells(cellManager.getAllCells());

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

    private void printStatistics() {
        System.out.println("\n\n\n\n----Statistics----\n");
        int asexuate_count = 0;
        int sexuate_count = 0;
        int foodUnitCount = 0;
        List<Cell> cells = cellManager.getAllCells();

        for(Iterator var5 = cells.iterator(); var5.hasNext(); foodUnitCount = Cell.getFoodUnitCountFromFoodManager()) {
            Cell cell = (Cell)var5.next();
            char cellSymbol = (char) (cell.getType() == CellType.ASEXUATE ? 65 : 83);
            if (cellSymbol == 65) {
                ++asexuate_count;
            } else {
                ++sexuate_count;
            }

            System.out.println("" + cellSymbol + " Cell ID: " + cell.getCellId() + " hunger - " + cell.getSaturation() + " reprC - " + cell.getReproductionCycle());
        }

        System.out.println("food units: " + foodUnitCount);
        System.out.println("asexuate cells: " + asexuate_count);
        System.out.println("sexuate cells: " + sexuate_count);
    }
}
