package simulation;

import utilities.CellGUI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CellManager {
    private static final List<Cell> cells = new ArrayList<>();
    private static final Lock cellListLock = new ReentrantLock();

    public CellManager() {
    }

    public void addCell(Cell cell) {
        cellListLock.lock();

        try {
            boolean placeFound = false;
            boolean gridFull = false;
            int xCurr = 0;
            int yCurr = 0;

            label82:
            while(true) {
                while(true) {
                    if (placeFound) {
                        break label82;
                    }

                    xCurr = (int)(Math.random() * (double)(CellGUI.gridSize - 2)) + 1;
                    yCurr = (int)(Math.random() * (double)(CellGUI.gridSize - 2)) + 1;
                    placeFound = true;
                    List<Cell> cellList = this.getAllCells();
                    if (cellList.size() == (CellGUI.gridSize - 2) * (CellGUI.gridSize - 2)) {
                        gridFull = true;
                        break label82;
                    }

                    for (Cell cellAux : cellList) {
                        int xAux = cellAux.getxPos();
                        int yAux = cellAux.getyPos();
                        if (xCurr == xAux && yCurr == yAux) {
                            placeFound = false;
                            break;
                        }
                    }
                }
            }

            if (!gridFull) {
                cell.setxPos(xCurr);
                cell.setyPos(yCurr);
                cells.add(cell);
            }
        } finally {
            cellListLock.unlock();
        }

    }

    public void removeCell(Cell cell) {
        cellListLock.lock();

        try {
            cells.remove(cell);
        } finally {
            cellListLock.unlock();
        }

    }

    public List<Cell> getAllCells() {
        cellListLock.lock();

        ArrayList<Cell> var0;
        try {
            var0 = new ArrayList<>(cells);
        } finally {
            cellListLock.unlock();
        }

        return var0;
    }

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

    public int getCellPopulation(){
        return cells.size();
    }
}
