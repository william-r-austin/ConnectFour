package edu.gmu.cs.ai.cs580.project.connectfour.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;

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
    private GameState gameState;
    private BoardPanel connectFourPanel;
    
    public ConnectFourUI(GameState gameState) {
        this.gameState = gameState;
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {

        // Create and set up the window.
        GameWindow connectFourFrame = new GameWindow();


        
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
        
        // Game Info Panel
        Border standardLineBorder = BorderFactory.createLineBorder(Color.BLACK, 1, false);  
        JPanel gameInformationPanel = new JPanel(new GridBagLayout());
        gameInformationPanel.setBackground(BACKGROUND_COLOR);
        Border gameInformationPanelBorder = BorderFactory.createTitledBorder(standardLineBorder, "Game Information", TitledBorder.LEADING, TitledBorder.TOP); 
        gameInformationPanel.setBorder(gameInformationPanelBorder);
        
        
        JLabel gameInformationLabel = new JLabel("This is some game information.");
        
        gameInformationPanel.add(gameInformationLabel, c);
        
        
        // BoardPanel
        Border standardLineBorder2 = BorderFactory.createLineBorder(Color.BLACK, 1, false);  
        JPanel boardPanel = new JPanel(new GridBagLayout());
        boardPanel.setBackground(BACKGROUND_COLOR);
        Border boardPanelBorder = BorderFactory.createTitledBorder(standardLineBorder2, "Connect Four Board", TitledBorder.LEADING, TitledBorder.TOP); 
        boardPanel.setBorder(boardPanelBorder);
        
        connectFourPanel = new BoardPanel(gameState);
        boardPanel.add(connectFourPanel, c);
        
        // Game Log Panel
        Border standardLineBorder3 = BorderFactory.createLineBorder(Color.BLACK, 1, false);  
        JPanel gameLogPanel = new JPanel(new GridBagLayout());
        gameLogPanel.setBackground(BACKGROUND_COLOR);
        Border gameLogPanelBorder = BorderFactory.createTitledBorder(standardLineBorder3, "Game Log", TitledBorder.LEADING, TitledBorder.TOP); 
        gameLogPanel.setBorder(gameLogPanelBorder);
        
        JTextArea gameLog = new JTextArea();
        gameLog.setEditable(false);
        // TODO - Include the date every time that we add a message.
        gameLog.append("This is some random text." + Constants.NEWLINE);
        gameLog.append("This is some random text again." + Constants.NEWLINE);
        gameLog.append("This is some random text 3." + Constants.NEWLINE);
        gameLog.append("This is some random text." + Constants.NEWLINE);
        gameLog.append("This is some random text again." + Constants.NEWLINE);
        gameLog.append("This is some random text 3." + Constants.NEWLINE);
        
        
        JScrollPane gameLogScrollPane = new JScrollPane(gameLog);
        gameLogScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        gameLogPanel.add(gameLogScrollPane, c);
                
        c.insets = new Insets(0, 0, 0, 0);
        
        layoutPanel.add(gameInformationPanel, c);
        c.gridy++;
        layoutPanel.add(boardPanel, c);
        
        c.gridy++;
        layoutPanel.add(gameLogPanel, c);
        
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
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    public void refresh() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                connectFourPanel.repaint();
            }
        });
    }
}
