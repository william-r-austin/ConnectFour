package edu.gmu.cs.ai.cs580.project.connectfour.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.gmu.cs.ai.cs580.project.connectfour.common.Constants;
import edu.gmu.cs.ai.cs580.project.connectfour.common.FillType;
import edu.gmu.cs.ai.cs580.project.connectfour.common.GameState;

public class BoardPanel extends JPanel implements MouseMotionListener, MouseListener {
    
    public static final int DIVIDER_THICKNESS = 4;
    
    public static final int HALF_PADDING_WIDTH = 8;
    public static final int HALF_PADDING_HEIGHT = 8;
    
    public static final int DISC_DIAMETER = 60;
    
    public static final int HEADER_BAND_HEIGHT = 50;
    public static final int ARROW_BAND_HEIGHT = 15;
    public static final int HALF_ARROW_WIDTH = 10;
    public static final Color ARROW_COLOR = new Color(20, 100, 15);
    
    public static final int PANEL_WIDTH = calculatePanelWidth();
    public static final int PANEL_HEIGHT = calculatePanelHeight();
    
    public static final Dimension PANEL_DIMENSION = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);

    private static final long serialVersionUID = -6814083306010580626L;
    
    private static final Font COLUMN_LABEL_FONT = new Font("Consolas", Font.BOLD, 36);
    
    private static Integer ARROW_COLUMN = -1;
    private static Integer SELECTED_COLUMN = -1;
    private static Lock userInputLock = new ReentrantLock();
    private static Condition columnClickedCondition = null;
    
    
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
    
    public void startMouseListeners() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    public void stopMouseListeners() {
        removeMouseListener(this);
        removeMouseMotionListener(this);
        ARROW_COLUMN = -1;
    }
    
    public synchronized Integer getSelectedColumn() {
        return SELECTED_COLUMN;
    }
    
    private synchronized void setSelectedColumn(Integer selectedColumn) {
        SELECTED_COLUMN = selectedColumn;
    }
    
    public void waitForColumnClick() {
        
        System.out.println("Waiting for column click - acquiring lock.");
        userInputLock.lock();
        try {
            System.out.println("Creating condition and waiting for signal.");
            columnClickedCondition = null;
            columnClickedCondition = userInputLock.newCondition();
            columnClickedCondition.await();
            columnClickedCondition = null;            
            System.out.println("Condition signal received. Returning.");
        } 
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            userInputLock.unlock();
        }
    }
       
    private static int calculatePanelWidth() {
        return Constants.COLUMNS * DISC_DIAMETER +
                2 * Constants.COLUMNS * HALF_PADDING_WIDTH;
    }
    
    private static int calculatePanelHeight() {
        return HEADER_BAND_HEIGHT + ARROW_BAND_HEIGHT + Constants.ROWS * DISC_DIAMETER +
                2 * Constants.ROWS * HALF_PADDING_HEIGHT;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        paintBackground(graphics);
        paintColumnLabels(graphics);
        paintArrow(graphics);
        paintBoard(graphics);
    }
    
    private void paintBoard(Graphics graphics) {
        for(int i = 0; i < Constants.COLUMNS; i++) {
            
            int xUpperLeft = i * (2 * HALF_PADDING_WIDTH + DISC_DIAMETER) + HALF_PADDING_WIDTH;
            for(int j = 0; j < Constants.ROWS; j++) {
                int yUpperLeft = HEADER_BAND_HEIGHT + ARROW_BAND_HEIGHT + j * (2 * HALF_PADDING_HEIGHT + DISC_DIAMETER) + HALF_PADDING_HEIGHT;
                
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
    
    public void paintArrow(Graphics graphics) {
        if(ARROW_COLUMN != null && ARROW_COLUMN >= 0 && ARROW_COLUMN < Constants.COLUMNS) {
            Integer arrowCopy = new Integer(ARROW_COLUMN);

            int[] xPoints = new int[3];
            int[] yPoints = new int[3];
            
            int columnCenter = arrowCopy * (2 * HALF_PADDING_WIDTH + DISC_DIAMETER) + (HALF_PADDING_WIDTH + (DISC_DIAMETER / 2));
            xPoints[0] = columnCenter - HALF_ARROW_WIDTH;
            yPoints[0] = HEADER_BAND_HEIGHT;
            
            xPoints[1] = columnCenter + HALF_ARROW_WIDTH;
            yPoints[1] = HEADER_BAND_HEIGHT;
            
            xPoints[2] = columnCenter;
            yPoints[2] = HEADER_BAND_HEIGHT + ARROW_BAND_HEIGHT;
            
            graphics.setColor(ARROW_COLOR);
            graphics.fillPolygon(xPoints, yPoints, 3);
        }
    }
    
    public void paintColumnLabels(Graphics graphics) {
        
        graphics.setFont(COLUMN_LABEL_FONT);
        graphics.setColor(Color.BLACK);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int maxAscent = fontMetrics.getMaxAscent();
        int stringHeight = maxAscent + fontMetrics.getMaxDescent();

        for(int i = 0; i < Constants.COLUMNS; i++) {
            String columnLabel = String.valueOf(i + 1);
            int stringWidth = fontMetrics.stringWidth(columnLabel);
                        
            int xUpperLeft = i * (2 * HALF_PADDING_WIDTH + DISC_DIAMETER) + (HALF_PADDING_WIDTH + (DISC_DIAMETER / 2) - (stringWidth / 2));
            int yUpperLeft = (HEADER_BAND_HEIGHT / 2) - (stringHeight / 2) + maxAscent;
            graphics.drawString(columnLabel, xUpperLeft, yUpperLeft);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("Mouse clicked: " + e);
        //handleMouseClick(e);
    }
    
    private synchronized void handleMouseClick(MouseEvent e) {
        Integer columnNumber = getColumnSelectionFromMouseClick(e);
        System.out.println("Click registered in column: " + columnNumber);
        
        if(columnNumber != null && columnNumber >= 0 && columnNumber < Constants.COLUMNS) {
            setSelectedColumn(columnNumber);
            userInputLock.lock();
            
            if(columnClickedCondition != null) {
                columnClickedCondition.signal();
            }
                        
            userInputLock.unlock();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ARROW_COLUMN = -1;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                repaint();
            }
        });
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse released: " + e);
        handleMouseClick(e);
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        Integer columnNumber = getColumnSelectionFromMouseClick(arg0);
        boolean repaintNeeded = false;
        
        if(ARROW_COLUMN == null || ARROW_COLUMN != columnNumber) {
            ARROW_COLUMN = columnNumber;
            repaintNeeded = true;
        }
        
        if(repaintNeeded) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    repaint();
                }
            });
        }
    }
    
    private Integer getColumnSelectionFromMouseClick(MouseEvent me) {
        int xPixels = me.getX();
        int columnWidth = 2 * HALF_PADDING_WIDTH + DISC_DIAMETER;
        Integer columnNumber = (xPixels / columnWidth);
        return columnNumber;
    }
    
}
