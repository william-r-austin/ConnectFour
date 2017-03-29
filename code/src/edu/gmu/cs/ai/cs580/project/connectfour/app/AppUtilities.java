package edu.gmu.cs.ai.cs580.project.connectfour.app;

import javax.swing.JOptionPane;

import edu.gmu.cs.ai.cs580.project.connectfour.agent.ConnectFourAgent;
import edu.gmu.cs.ai.cs580.project.connectfour.common.GameState;
import edu.gmu.cs.ai.cs580.project.connectfour.common.WinCheckResult;
import edu.gmu.cs.ai.cs580.project.connectfour.ui.ConnectFourUI;

public class AppUtilities {

    public static WinCheckResult playGameWithUI(ConnectFourUI userInterface, ConnectFourAgent player1, ConnectFourAgent player2) {
        userInterface.displayUI();
        return playGame(userInterface.getGameState(), userInterface, player1, player2);
    }
    
    public static WinCheckResult playSimulatedGame(ConnectFourAgent player1, ConnectFourAgent player2) {
        GameState masterGameState = new GameState();
        return playGame(masterGameState, null, player1, player2);
    }

    private static WinCheckResult playGame(GameState masterGameState, ConnectFourUI userInterface, ConnectFourAgent player1, ConnectFourAgent player2) {
        
        ConnectFourAgent[] playerArray = new ConnectFourAgent[2];
        playerArray[0] = player1;
        playerArray[1] = player2;
        
        boolean complete = false;
        int moveNumber = 0;
        WinCheckResult winCheckResult = null;
        
        while(!complete) {
            
            ConnectFourAgent currentPlayer = playerArray[moveNumber % 2];
            
            Integer k = currentPlayer.getNextMove(masterGameState);
            try {
                masterGameState.makeMove(k, currentPlayer.getPlayer());
                
                if(userInterface != null) {
                    userInterface.refresh();
                }
                
                moveNumber++;
                
                winCheckResult = masterGameState.checkForWinner();
                complete = winCheckResult.getIsGameComplete();
                if(complete) {
                    String message = "";
                    if(winCheckResult.getFoundWinner()) {
                        message = "Game completed, WINNER found: " + winCheckResult.getWinningPlayer() + ", total moves = " + moveNumber;
                    }
                    else {
                        message = "Game completed, result was a TIE, total moves = " + moveNumber;
                    }
                    
                    System.out.println(message);
                    
                    if(userInterface != null) {
                        JOptionPane.showMessageDialog(userInterface.getGameWindow(), message, "Game Finished", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
            catch(Exception e) {
                complete = true;
                e.printStackTrace();
            }
        }
        
        return winCheckResult;
    }
}
