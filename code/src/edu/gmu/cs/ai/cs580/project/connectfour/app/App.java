package edu.gmu.cs.ai.cs580.project.connectfour.app;

import edu.gmu.cs.ai.cs580.project.connectfour.agent.ConnectFourAgent;
import edu.gmu.cs.ai.cs580.project.connectfour.agent.HumanPlayer;
import edu.gmu.cs.ai.cs580.project.connectfour.agent.RandomMachinePlayer;
import edu.gmu.cs.ai.cs580.project.connectfour.agent.TestAgent;
import edu.gmu.cs.ai.cs580.project.connectfour.common.GameState;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Player;
import edu.gmu.cs.ai.cs580.project.connectfour.common.WinCheckResult;
import edu.gmu.cs.ai.cs580.project.connectfour.ui.ConnectFourUI;

public class App {
    
    public static void main(String[] args) {
        
        GameState masterGameState = new GameState();
        
        ConnectFourUI userInterface = new ConnectFourUI(masterGameState);
        userInterface.displayUI();
        
       // ConnectFourAgent player1 = new RandomMachinePlayer(Player.PLAYER_A);
        ConnectFourAgent player2 = new HumanPlayer(Player.PLAYER_B, userInterface);
        
        //--------------------------------------------------------------
        
        ConnectFourAgent player1 = new TestAgent(Player.PLAYER_A);
        //ConnectFourAgent player2 = new HumanPlayer(Player.PLAYER_B, userInterface);
        //ConnectFourAgent player2 = new RandomMachinePlayer(Player.PLAYER_B);
        
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
        }
    }
}
