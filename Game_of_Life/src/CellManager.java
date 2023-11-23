import java.util.ArrayList;
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
}
