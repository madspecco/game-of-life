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
}
