package edu.gmu.cs.ai.cs580.project.connectfour.app;

import javax.swing.JOptionPane;

import edu.gmu.cs.ai.cs580.project.connectfour.agent.ConnectFourAgent;
import edu.gmu.cs.ai.cs580.project.connectfour.agent.HumanPlayer;
import edu.gmu.cs.ai.cs580.project.connectfour.agent.SampleMinimaxAgent;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Player;
import edu.gmu.cs.ai.cs580.project.connectfour.ui.ConnectFourUI;

public class App {
    
    public static void main(String[] args) {
        
        ConnectFourAgent player1 = null;
        ConnectFourAgent player2 = null;
        
        ConnectFourUI userInterface = new ConnectFourUI();

        Object[] options = {"Yes, let me play first", "Nah, I'll go second"};
        
        int moveOrderResult = JOptionPane.showOptionDialog(userInterface.getGameWindow(),
             "Would you like to play first?", "Select Player Move Order", JOptionPane.YES_NO_OPTION,
             JOptionPane.QUESTION_MESSAGE, null, options, null);
        
        if(JOptionPane.YES_OPTION == moveOrderResult) {
            player1 = new HumanPlayer(Player.PLAYER_A, userInterface);
            player2 = new SampleMinimaxAgent(Player.PLAYER_B);            
        }
        else {
            player1 = new SampleMinimaxAgent(Player.PLAYER_A);
            player2 = new HumanPlayer(Player.PLAYER_B, userInterface);
        }
        
        AppUtilities.playGameWithUI(userInterface, player1, player2);
    }
}
