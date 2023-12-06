//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;

public class SimulationGUI extends JPanel {
    private List<Cell> cells;
    public static int gridSize = 10;
    private SimulationManager simulationManager;

    public SimulationGUI(List<Cell> cells) {
        this.cells = cells;
    }

    public void updateCells(List<Cell> newCells) {
        this.cells = newCells;
        this.repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.getFontMetrics(new Font("Arial", 1, 30));
        String text1 = "Simulation Process";
        g.drawString(text1, 30, 30);
        this.displayStatistics(g);
    }

    private void displayStatistics(Graphics g) {
        FontMetrics fontMetrics = g.getFontMetrics(new Font("Arial", 0, 12));
        int lineHeight = fontMetrics.getHeight() + 5;
        int startY = 80;
        int asexuate_count = 0;
        int sexuate_count = 0;
        int foodUnitCount = 0;

        for(Iterator var8 = this.cells.iterator(); var8.hasNext(); startY += lineHeight) {
            Cell cell = (Cell)var8.next();
            char cellSymbol = (char) (cell.getType() == CellType.ASEXUATE ? 65 : 83);
            if (cellSymbol == 65) {
                ++asexuate_count;
            } else {
                ++sexuate_count;
            }

            foodUnitCount = Cell.getFoodUnitCountFromFoodManager();
            String cellDetails = "" + cellSymbol + ", Cell ID: " + cell.getCellId() + ", saturation - " + cell.getSaturation() + ", reprC - " + cell.getReproductionCycle();
            g.drawString(cellDetails, 30, startY + lineHeight);
        }

        g.drawString("Every Cell Each Round", 30, 70);
        g.drawString("Statistics\n", 330, 70);
        g.drawString("Food Units: " + foodUnitCount, 330, 80 + lineHeight);
        g.drawString("Asexuate Cells: " + asexuate_count, 330, 80 + 2 * lineHeight);
        g.drawString("Sexuate Cells: " + sexuate_count, 330, 80 + 3 * lineHeight);
    }
}
