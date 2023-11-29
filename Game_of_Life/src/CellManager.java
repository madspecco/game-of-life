import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CellManager {
    private static final List<Cell> cells = new ArrayList<>();
    private static final Lock cellListLock = new ReentrantLock();

    private final FoodManager foodManager;

    public CellManager(FoodManager fm){
        this.foodManager = fm;
    }

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

    public boolean reproduceCell(Cell cell) {
        CellType type = cell.getType();
        int reproductionCycle = cell.getReproductionCycle();

        if (reproductionCycle >= 10) {
            if (type == CellType.ASEXUATE) {
                Cell newCell1 = new Cell(CellType.ASEXUATE, this.foodManager);

                addCell(newCell1);

                // Reset the reproduction cycle
                newCell1.setReproductionCycle(0);
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
                    Cell newCell = new Cell(CellType.SEXUATE, this.foodManager);
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
            cell.eat();

            if (cell.starve()) {

                removeCell(cell);
                iterator.remove(); // Remove the current cell using the iterator remove method

            } else {
                reproduceCell(cell);
            }
        }
    }
}