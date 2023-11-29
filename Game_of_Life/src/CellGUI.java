import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CellGUI extends JPanel {

    private List<Cell> cells;

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

        int gridSize = 20; // Adjust the grid size as needed
        int cellSize = getWidth() / gridSize; // Calculate cell size based on the panel width

        // Fill the entire background with white
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (Cell cell : cells) {
            int x = (int) (cell.getId() % gridSize) * cellSize;
            int y = (int) (cell.getId() / gridSize) * cellSize;

            // Set color based on cell type
            g.setColor((cell.getType() == CellType.ASEXUATE) ? Color.BLUE : Color.RED);

            // Paint a filled rectangle representing the cell
            g.fillRect(x, y, cellSize, cellSize);

            // Set color to black for the outline
            g.setColor(Color.BLACK);

            // Draw a black outline around the cell
            g.drawRect(x, y, cellSize, cellSize);
        }
    }

}
