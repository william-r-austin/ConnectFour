package edu.gmu.cs.ai.cs580.project.connectfour.app;

import edu.gmu.cs.ai.cs580.project.connectfour.agent.ConnectFourAgent;
import edu.gmu.cs.ai.cs580.project.connectfour.agent.TestAgent;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Player;

public class MultigameApp {
    
    //private static final Integer[] CONFIG1 = {2, 60, 2200, 800000, -1, -40, -2200, -100000};
    //private static final Integer[] CONFIG1 = {2, 34, 2907, 104618, -4, -197, -968, -6588};
    private static final Integer[] CONFIG1 = {2, 100, 5000, 130000, -1, -85, -4000, -125000};
    private static final Integer[] CONFIG2 = {2, 120, 1200, 0, -1, -100, -700, 0};
    //private static final Integer[] CONFIG2 = {2, 30, 600, 12000, -1, -20, -400, -8000};
    //private static final Integer[] CONFIG2 = {2, 60, 2200, 80000, -1, -40, -2200, -10000};
    
    
    
    public static void main(String[] args) {
        
        //for(int gameNumber = 1; gameNumber <= 2; gameNumber++) {
            //GameState masterGameState = new GameState();
            
           // ConnectFourUI userInterface = new ConnectFourUI(masterGameState);
            //userInterface.displayUI();
            
            ConnectFourAgent player1A = new TestAgent(Player.PLAYER_A, CONFIG1);
            //ConnectFourAgent player1 = new TestAgent(Player.PLAYER_A);
            
            
            //--------------------------------------------------------------
            
            //onnectFourAgent player2 = new HumanPlayer(Player.PLAYER_B, userInterface);
            ConnectFourAgent player1B = new TestAgent(Player.PLAYER_B, CONFIG2);
            //ConnectFourAgent player2 = new HumanPlayer(Player.PLAYER_B, userInterface);
            //ConnectFourAgent player2 = new RandomMachinePlayer(Player.PLAYER_B);
            
            AppUtilities.playSimulatedGame(player1A, player1B);
            
            
            ConnectFourAgent player2A = new TestAgent(Player.PLAYER_A, CONFIG2);
            ConnectFourAgent player2B = new TestAgent(Player.PLAYER_B, CONFIG1);
            
            AppUtilities.playSimulatedGame(player2A, player2B);
        //}
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
                    moveNumber++;
                   // userInterface.refresh();
                    
                    WinCheckResult winCheckResult = masterGameState.checkForWinner();
                    complete = winCheckResult.getFoundWinner();
                    
                    if(complete) {
                        System.out.println("Game Number " + gameNumber + ": Winner found: " + winCheckResult.getWinningPlayer() + ", total moves = " + moveNumber);
                    }
                }
                catch(Exception e) {
                    complete = true;
                    e.printStackTrace();
                }
                            
                
            }
        }
        */

    }

}
