package org.example;

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
        repaint(); // Redraw the panel with the updated cells
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int borderWidth = 2;
        int cellSize = getWidth() / gridSize; // Calculate cell size based on the panel width

        // Fill the entire background
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, getWidth(), getHeight());


        // Draw the border
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j< gridSize; j++){
                if(i == 0 || j == 0 || i == gridSize - 1 || j == gridSize - 1){
                    int x = i * cellSize;
                    int y = j * cellSize;

                    // Set color for the border
                    g.setColor(new Color(200, 200, 200)); // Lighter gray
                    g.fillRect(x, y, cellSize, cellSize);

                    // Draw inner rectangle to represent the cell
                    g.setColor(Color.gray);
                    g.fillRect(x + borderWidth, y + borderWidth, cellSize - 2 * borderWidth, cellSize - 2 * borderWidth);
                }
            }
        }

        // Draw cells and text information inside the borders
        for (Cell cell : cells) {
            int x = cell.getxPos() * cellSize + borderWidth;
            int y = cell.getyPos() * cellSize + borderWidth;

            // Set color based on cell type
            g.setColor((cell.getType() == CellType.ASEXUATE) ? Color.CYAN : Color.YELLOW);

            // Paint a filled rectangle representing the cell inside the borders
            g.fillRect(x, y, cellSize - 2 * borderWidth, cellSize - 2 * borderWidth);

            // Draw text information
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

        // Draw grid lines
        g.setColor(Color.BLACK);
        for (int i = 0; i <= gridSize; i++) {
            int linePosition = i * cellSize;
            g.drawLine(linePosition, 0, linePosition, getHeight());
            g.drawLine(0, linePosition, getWidth(), linePosition);
        }

        g.setColor(Color.WHITE);
        g.drawString("FOOD ", 73, 35);

        g.setColor(Color.WHITE);
        g.drawString("UNITS: ", 130, 35);

        g.setColor(Color.WHITE);
        g.drawString(" " + Cell.getFoodUnitCountFromFoodManager(), 198, 35);
    }
}