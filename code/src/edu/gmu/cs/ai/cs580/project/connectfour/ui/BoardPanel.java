package edu.gmu.cs.ai.cs580.project.connectfour.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import edu.gmu.cs.ai.cs580.project.connectfour.common.Constants;
import edu.gmu.cs.ai.cs580.project.connectfour.common.FillType;
import edu.gmu.cs.ai.cs580.project.connectfour.common.GameState;

public class BoardPanel extends JPanel {
    
    public static final int DIVIDER_THICKNESS = 4;
    
    public static final int HALF_PADDING_WIDTH = 8;
    public static final int HALF_PADDING_HEIGHT = 8;
    
    public static final int DISC_DIAMETER = 60;
    
    public static final int PANEL_WIDTH = calculatePanelWidth();
    public static final int PANEL_HEIGHT = calculatePanelHeight();
    
    public static final Dimension PANEL_DIMENSION = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);

    private static final long serialVersionUID = -6814083306010580626L;
    
    private GameState gameState;
    
    public BoardPanel(GameState gameState) {
        super();
        this.gameState = gameState;
    }
    
    @Override
    public Dimension getPreferredSize() {
        return PANEL_DIMENSION;
    }
    
    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }
    
    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
    
    private static int calculatePanelWidth() {
        return Constants.COLUMNS * DISC_DIAMETER +
                2 * Constants.COLUMNS * HALF_PADDING_WIDTH;
    }
    
    private static int calculatePanelHeight() {
        return Constants.ROWS * DISC_DIAMETER +
                2 * Constants.ROWS * HALF_PADDING_HEIGHT;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        
        super.paintComponent(graphics);
        
        paintBackground(graphics);
        
        for(int i = 0; i < Constants.COLUMNS; i++) {
            
            int xUpperLeft = i * (2 * HALF_PADDING_WIDTH + DISC_DIAMETER) + HALF_PADDING_WIDTH;
            for(int j = 0; j < Constants.ROWS; j++) {
                int yUpperLeft = j * (2 * HALF_PADDING_HEIGHT + DISC_DIAMETER) + HALF_PADDING_HEIGHT;
                
                FillType fillType = gameState.getFillType(i, Constants.ROWS - j - 1);
                graphics.setColor(fillType.getColor());
                
                graphics.fillOval(xUpperLeft, yUpperLeft, DISC_DIAMETER, DISC_DIAMETER);
            }
        }
    }
    
    public void paintBackground(Graphics graphics) {
        graphics.setColor(Color.YELLOW);
        graphics.fillRect(0,  0, PANEL_WIDTH, PANEL_HEIGHT);
    }
    
}
