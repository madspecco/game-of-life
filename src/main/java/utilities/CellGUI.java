package utilities;

import simulation.Cell;
import types.CellType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CellGUI extends JPanel {
    private List<Cell> cells;
    public static int gridSize = 10;

    public CellGUI(List<Cell> cells) {
        this.cells = cells;
    }

    public void updateCells(List<Cell> newCells) {
        this.cells = newCells;
        this.repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int borderWidth = 2;
        int cellSize = this.getWidth() / gridSize;
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        int i;
        int linePosition;
        int x;
        int y;
        for(i = 0; i < gridSize; ++i) {
            for(linePosition = 0; linePosition < gridSize; ++linePosition) {
                if (i == 0 || linePosition == 0 || i == gridSize - 1 || linePosition == gridSize - 1) {
                    x = i * cellSize;
                    y = linePosition * cellSize;
                    g.setColor(new Color(200, 200, 200));
                    g.fillRect(x, y, cellSize, cellSize);
                    g.setColor(Color.gray);
                    g.fillRect(x + borderWidth, y + borderWidth, cellSize - 2 * borderWidth, cellSize - 2 * borderWidth);
                }
            }
        }

        for (Cell cell : this.cells) {
            x = cell.getxPos() * cellSize + borderWidth;
            y = cell.getyPos() * cellSize + borderWidth;
            g.setColor(cell.getType() == CellType.ASEXUATE ? Color.CYAN : Color.YELLOW);
            g.fillRect(x, y, cellSize - 2 * borderWidth, cellSize - 2 * borderWidth);
            g.setColor(Color.BLACK);
            FontMetrics fontMetrics = g.getFontMetrics(new Font("Arial", Font.BOLD, 10));
            String text1 = "ID: " + cell.getCellId();
            String text2 = "S: " + cell.getSaturation();
            String text3 = "RC: " + cell.getReproductionCycle();
            int textX = x + ((cellSize - fontMetrics.stringWidth(text1)) / 2 - 4);
            int textY = y + (cellSize - fontMetrics.getHeight() * 3) / 2 + fontMetrics.getAscent();
            g.drawString(text1, textX, textY);
            g.drawString(text2, textX, textY + fontMetrics.getHeight());
            g.drawString(text3, textX, textY + 2 * fontMetrics.getHeight());
        }

        g.setColor(Color.BLACK);

        for(i = 0; i <= gridSize; ++i) {
            linePosition = i * cellSize;
            g.drawLine(linePosition, 0, linePosition, this.getHeight());
            g.drawLine(0, linePosition, this.getWidth(), linePosition);
        }

        g.setColor(Color.WHITE);
        g.drawString("FOOD ", 73, 35);
        g.setColor(Color.WHITE);
        g.drawString("UNITS: ", 130, 35);
        g.setColor(Color.WHITE);
        g.drawString(" " + Cell.getFoodUnitCountFromFoodManager(), 198, 35);
    }
}

