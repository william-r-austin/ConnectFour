package edu.gmu.cs.ai.cs580.project.connectfour.app;

import javax.swing.JOptionPane;

import edu.gmu.cs.ai.cs580.project.connectfour.agent.ConnectFourAgent;
import edu.gmu.cs.ai.cs580.project.connectfour.agent.HumanPlayer;
import edu.gmu.cs.ai.cs580.project.connectfour.agent.TestAgent;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Player;
import edu.gmu.cs.ai.cs580.project.connectfour.ui.ConnectFourUI;

public class App {
    
    public static void main(String[] args) {
        
        ConnectFourAgent player1 = null;
        ConnectFourAgent player2 = null;

        //GameState masterGameState = new GameState();
        
        ConnectFourUI userInterface = new ConnectFourUI();
        //userInterface.displayUI();
        
        //Yes/No/Cancel (in different words); showOptionDialog
        //Custom button text
        Object[] options = {"Yes, let me play first", "Nah, I'll go second"};
        
        int moveOrderResult = JOptionPane.showOptionDialog(userInterface.getGameWindow(),
             "Would you like to play first?", "Select Player Move Order", JOptionPane.YES_NO_OPTION,
             JOptionPane.QUESTION_MESSAGE, null, options, null);
        
        if(JOptionPane.YES_OPTION == moveOrderResult) {
            player1 = new HumanPlayer(Player.PLAYER_A, userInterface);
            player2 = new TestAgent(Player.PLAYER_B);            
        }
        else {
            player1 = new TestAgent(Player.PLAYER_A);
            player2 = new HumanPlayer(Player.PLAYER_B, userInterface);
        }
        
        AppUtilities.playGameWithUI(userInterface, player1, player2);
        
       // player1 = new HumanPlayer(Player.PLAYER_A, userInterface);
        //ConnectFourAgent player1 = new TestAgent(Player.PLAYER_A);
        
        // --------------------------------------------------------------
        
        //ConnectFourAgent player2 = new HumanPlayer(Player.PLAYER_B, userInterface);
       // player2 = new TestAgent(Player.PLAYER_B);
        /*
        ConnectFourAgent[] playerArray = new ConnectFourAgent[2];
        playerArray[0] = player1;
        playerArray[1] = player2;
        
        boolean complete = false;
        int moveNumber = 0;
        
        while(!complete) {
            
            ConnectFourAgent currentPlayer = playerArray[moveNumber % 2];
            
            Integer k = currentPlayer.getNextMove(masterGameState);
            try {
                masterGameState.makeMove(k, currentPlayer.getPlayer());
                userInterface.refresh();
                
                WinCheckResult winCheckResult = masterGameState.checkForWinner();
                complete = winCheckResult.getFoundWinner();
                
                if(complete) {
                    System.out.println("Winner found: " + winCheckResult.getWinningPlayer());
                }
            }
            catch(Exception e) {
                complete = true;
                e.printStackTrace();
            }
                        
            moveNumber++;
        }*/
    }
}
