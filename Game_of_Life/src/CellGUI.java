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

        int cellSize = getWidth() / gridSize; // Calculate cell size based on the panel width

        // Fill the entire background with white
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw the border
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j< gridSize; j++){
                if(i == 0 || j == 0 || i == gridSize - 1 || j == gridSize - 1){
                    int x = (int) i * cellSize;
                    int y = (int) j * cellSize;

                    // Set color based on cell type
                    g.setColor(Color.BLACK);

                    // Paint a filled rectangle representing the cell
                    g.fillRect(x, y, cellSize, cellSize);
                }
            }
        }

        for (Cell cell : cells) {
            int x = cell.getxPos() * cellSize;
            int y = cell.getyPos() * cellSize;

            // Set color based on cell type
            g.setColor((cell.getType() == CellType.ASEXUATE) ? Color.CYAN : Color.YELLOW);

            // Paint a filled rectangle representing the cell
            g.fillRect(x, y, cellSize, cellSize);

            // Draw the first line of text in the center of the rectangle
            String text1 = "ID: " + cell.getCellId(); // You can customize this text as needed
            FontMetrics fontMetrics = g.getFontMetrics(new Font("Arial", Font.PLAIN, 10)); // Adjust the font size as needed
            int textX1 = x + (cellSize - fontMetrics.stringWidth(text1)) / 2;
            int textY1 = y + (cellSize - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent() - 10;
            g.setColor(Color.BLACK);
            g.drawString(text1, textX1, textY1);

            // Draw the second line of text below the first one
            // String text2 = (cell.getType() == CellType.ASEXUATE) ? "A" : "S";

            String text2 = "S: " + cell.getSaturation();
            int textX2 = x + (cellSize - fontMetrics.stringWidth(text2)) / 2;
            int textY2 = textY1 + fontMetrics.getHeight() - 2; // Place it below the first line
            g.drawString(text2, textX2, textY2);

            // Draw the third line of text below the second one
            // String text2 = (cell.getType() == CellType.ASEXUATE) ? "A" : "S";
            String text3 = "RC: " + cell.getReproductionCycle();
            int textX3 = (x + (cellSize - fontMetrics.stringWidth(text2)) / 2) - 4;
            int textY3 = textY2 + fontMetrics.getHeight() - 1; // Place it below the second line
            g.drawString(text3, textX3, textY3);
        }

        // Draw grid lines
        g.setColor(Color.BLACK);
        for (int i = 0; i <= gridSize; i++) {
            int linePosition = i * cellSize;
            g.drawLine(linePosition, 0, linePosition, getHeight());
            g.drawLine(0, linePosition, getWidth(), linePosition);
        }

        g.setColor(Color.WHITE);
        g.drawString("FOOD UNITS: " + Cell.getFoodUnitCountFromFoodManager(), 30, 30);

    }
}