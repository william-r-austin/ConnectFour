package edu.gmu.cs.ai.cs580.project.connectfour.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import edu.gmu.cs.ai.cs580.project.connectfour.common.Constants;
import edu.gmu.cs.ai.cs580.project.connectfour.common.GameState;

public class ConnectFourUI {
    
    public static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
    private GameWindow connectFourFrame;
    private GameState gameState;
    private BoardPanel connectFourPanel;
    
    public ConnectFourUI(GameState gameState) {
        this.gameState = gameState;
        this.connectFourFrame = new GameWindow();
    }
    
    public ConnectFourUI() {
        this.gameState = new GameState();
        this.connectFourFrame = new GameWindow();
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.ipadx = 0;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridwidth = 1;
        c.gridheight = 1;
        
        JPanel layoutPanel = new JPanel(new GridBagLayout());
        layoutPanel.setBackground(BACKGROUND_COLOR);
        
        // BoardPanel
        Border standardLineBorder2 = BorderFactory.createLineBorder(Color.BLACK, 1, false);  
        JPanel boardPanel = new JPanel(new GridBagLayout());
        boardPanel.setBackground(BACKGROUND_COLOR);
        Border boardPanelBorder = BorderFactory.createTitledBorder(standardLineBorder2, "Connect Four Board", TitledBorder.LEADING, TitledBorder.TOP); 
        boardPanel.setBorder(boardPanelBorder);
        
        connectFourPanel = new BoardPanel(gameState);
        boardPanel.add(connectFourPanel, c);
        /*
        // Game Log Panel
        Border standardLineBorder3 = BorderFactory.createLineBorder(Color.BLACK, 1, false);  
        JPanel gameLogPanel = new JPanel(new GridBagLayout());
        gameLogPanel.setBackground(BACKGROUND_COLOR);
        Border gameLogPanelBorder = BorderFactory.createTitledBorder(standardLineBorder3, "Game Log", TitledBorder.LEADING, TitledBorder.TOP); 
        gameLogPanel.setBorder(gameLogPanelBorder);
        
        JTextArea gameLog = new JTextArea();
        gameLog.setEditable(false);
        // TODO - Include the date every time that we add a message.
        // TODO - Put an upper bound on the vertical size of the game log.
        gameLog.append("This is some random text." + Constants.NEWLINE);
        gameLog.append("This is some random text again." + Constants.NEWLINE);
        gameLog.append("This is some random text 3." + Constants.NEWLINE);
        gameLog.append("This is some random text." + Constants.NEWLINE);
        gameLog.append("This is some random text again." + Constants.NEWLINE);
        gameLog.append("This is some random text 3." + Constants.NEWLINE);
        
        JScrollPane gameLogScrollPane = new JScrollPane(gameLog);
        gameLogScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        gameLogPanel.add(gameLogScrollPane, c);
        
        
        // Game Info Panel
        Border standardLineBorder = BorderFactory.createLineBorder(Color.BLACK, 1, false);  
        JPanel gameInformationPanel = new JPanel(new GridBagLayout());
        gameInformationPanel.setBackground(BACKGROUND_COLOR);
        Border gameInformationPanelBorder = BorderFactory.createTitledBorder(standardLineBorder, "Game Information", TitledBorder.LEADING, TitledBorder.TOP); 
        gameInformationPanel.setBorder(gameInformationPanelBorder);
        
        
        JLabel gameInformationLabel = new JLabel("This is some game information.");
        
        gameInformationPanel.add(gameInformationLabel, c);
                
        
        */
        c.insets = new Insets(0, 0, 0, 0);
        
        layoutPanel.add(boardPanel, c);
        /*
        c.gridy++;
        layoutPanel.add(gameLogPanel, c);
        
        c.gridx++;
        c.gridy = 0;
        c.gridheight = 2;
        layoutPanel.add(gameInformationPanel, c);*/
        
        connectFourFrame.getContentPane().add(layoutPanel, BorderLayout.CENTER);
        //connectFourFrame.setResizable(false);
        connectFourFrame.pack();
        connectFourFrame.setVisible(true);
        
        //Display the window.
        centerWindow(connectFourFrame);
    }
    
    private void centerWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
    
    public void displayUI() {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override            
                public void run() {
                    createAndShowGUI();
                }
            });
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void refresh() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override            
            public void run() {
                connectFourPanel.repaint();
            }
        });
    }
    
    public void beginMouseMonitoring() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                connectFourPanel.startMouseListeners();
            }
        });
    }
    
    public Integer waitAndGetClickedColumn() {
        connectFourPanel.waitForColumnClick();
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                connectFourPanel.stopMouseListeners();
            }
        });
        
        Integer result = connectFourPanel.getSelectedColumn();
        return result;
    }
    
    public GameWindow getGameWindow() {
        return connectFourFrame;
    }
    
    public GameState getGameState() {
        return gameState;
    }
}
