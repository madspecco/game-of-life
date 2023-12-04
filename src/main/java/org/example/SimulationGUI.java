package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SimulationGUI extends JPanel {

    private List<Cell> cells;

    public static int gridSize = 10;

    public SimulationGUI(List<Cell> cells) {
        this.cells = cells;
    }

    public void updateCells(List<Cell> newCells) {
        this.cells = newCells;
        repaint(); // Redraw the panel with the updated cells
    }

    private SimulationManager simulationManager;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        FontMetrics fontMetrics = g.getFontMetrics(new Font("Arial", Font.BOLD, 30));
        String text1 = "Simulation Process";
        g.drawString(text1, 30, 30);

        // Draw the simulation or cells here

        displayStatistics(g);
    }

    private void displayStatistics(Graphics g) {


        FontMetrics fontMetrics = g.getFontMetrics(new Font("Arial", Font.PLAIN, 12));
        int lineHeight = fontMetrics.getHeight() + 5;
        int startY = 80; // Starting Y position for statistics

        int asexuate_count = 0;
        int sexuate_count = 0;
        int foodUnitCount = 0;

        // Fetch cell statistics
        for (Cell cell : cells) {
            char cellSymbol = (cell.getType() == CellType.ASEXUATE) ? 'A' : 'S';
            if (cellSymbol == 'A') {
                asexuate_count++;
            } else {
                sexuate_count++;
            }

            foodUnitCount = cell.getFoodUnitCountFromFoodManager();
            String cellDetails = cellSymbol+", " + "Cell ID: " + cell.getCellId() +", " + "saturation - " + cell.getSaturation() +", " + "reprC - " + cell.getReproductionCycle();
            g.drawString(cellDetails, 30, startY + lineHeight);
            startY += lineHeight; // Move to the next line

            // Show Simulation Time
            //String SimulationTime = "Simulation Time - " + simulationManager.getSimulationTime();
            //g.drawString(SimulationTime, 30, 800);
        }

        // Display statistics
        g.drawString("Every Cell Each Round", 30, 70);
        g.drawString("Statistics\n", 330, 70);
        g.drawString("Food Units: " + foodUnitCount, 330, 80 + lineHeight);
        g.drawString("Asexuate Cells: " + asexuate_count, 330, 80 + 2*lineHeight);
        g.drawString("Sexuate Cells: " + sexuate_count, 330, 80 + 3 * lineHeight);

    }



}

