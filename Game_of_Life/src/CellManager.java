import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CellManager {
    private static final List<Cell> cells = new ArrayList<>();
    private static final Lock cellListLock = new ReentrantLock();

    // add a cell to the simulation
    public static void addCell(Cell cell) {
        cellListLock.lock();
        try {
            cells.add(cell);
        } finally {
            cellListLock.unlock();
        }
    }

    // remove cell from the simulation
    public static void removeCell(Cell cell) {
        cellListLock.lock();
        try {
            cells.remove(cell);
        } finally {
            cellListLock.unlock();
        }
    }

    // get a copy of all cells in the simulation
    public static List<Cell> getAllCells() {
        cellListLock.lock();
        try {
            return new ArrayList<>(cells);
        } finally {
            cellListLock.unlock();
        }
    }

    // 'unlock' all the cells
    public void startCells() {
        cellListLock.lock();
        try {
            for (Cell cell : cells) {
                cell.start();
            }
        } finally {
            cellListLock.unlock();
        }
    }


    // 'lock' all the cells
    public void stopCells() {
        cellListLock.lock();
        try {
            for (Cell cell : cells) {
                cell.interrupt();
            }
        } finally {
            cellListLock.unlock();
        }
    }

    // life cycle of the cells (eat, starve, reproduce)
    public void updateCellState() {
        cellListLock.lock();
        try {
            for (Cell cell : cells) {
                // Call methods on each cell to update their state
                System.out.println(" updating cell " + cell.getCellId() + "  ");
                cell.start();
            }
        } finally {
            cellListLock.unlock();
        }
    }

    // same as updateCellState, instead of cell.start -> cell.updateTime()
    public void updateCellTime() {
        cellListLock.lock();
        try {
            for (Cell cell : cells) {
                // Call methods on each cell to update their state
                System.out.println(" updating cell time " + cell.getCellId() + "  ");
                cell.updateTime();
            }
        } finally {
            cellListLock.unlock();
        }
    }

    public boolean reproduceCell(Cell cell) {
        CellType type = cell.getType();
        int reproductionCycle = cell.getReproductionCycle();

        if (reproductionCycle >= 10) {
            if (type == CellType.ASEXUATE) {
                Cell newCell1 = new Cell(CellType.ASEXUATE);
                Cell newCell2 = new Cell(CellType.ASEXUATE);

                addCell(newCell1);
                addCell(newCell2);

                // Reset the reproduction cycle
                newCell1.setReproductionCycle(0);
                newCell2.setReproductionCycle(0);
                cell.setReproductionCycle(0);

                return true; // Reproduction occurred
            } else {
                List<Cell> cellList = getAllCells();
                boolean match = false;

                for (int i = 0; i < cellList.size(); i++) {
                    Cell currentCell = cellList.get(i);
                    if (currentCell.getType() == CellType.SEXUATE && currentCell.getReproductionCycle() >= 10) {
                        for (int j = 0; j < cellList.size(); j++) {
                            Cell otherCell = cellList.get(j);
                            if (otherCell.getType() == CellType.SEXUATE && otherCell.getReproductionCycle() >= 10 && i != j) {
                                match = true;
                                currentCell.setReproductionCycle(0);
                                otherCell.setReproductionCycle(0);
                                break;
                            }
                        }
                    }
                }

                if (match) {
                    Cell newCell = new Cell(CellType.SEXUATE);
                    addCell(newCell);
                    newCell.setReproductionCycle(0);

                    return true; // Reproduction occurred
                }
            }
        }
        return false; // Reproduction did not occur
    }


    void performCellActions() {
        List<Cell> cellList = getAllCells();

        // Using Iterator to safely remove elements during iteration
        Iterator<Cell> iterator = cellList.iterator();
        while (iterator.hasNext()) {
            Cell cell = iterator.next();

            cell.updateTime();
            System.out.println("CellManager cell[" + cell.getId() + "].eat()");
            cell.eat();

            //System.out.println("CellList size BEFORE starvation: " + cellList.size());
            //System.out.println("CellManager cell[" + cell.getId() + "] check if starving");

            if (cell.starve() == true) {
                System.out.println("CellManager cell[" + cell.getId() + "] HAS STARVED, REMOVE IT");
                iterator.remove(); // Remove the current cell using the iterator's remove method
                System.out.println("CellList size AFTER starvation: " + cellList.size());
            } else {
                System.out.println("CellManager cell[" + cell.getId() + "] is reproducing");
                reproduceCell(cell);
            }

            System.out.println("Cycle ended");
        }
    }
}