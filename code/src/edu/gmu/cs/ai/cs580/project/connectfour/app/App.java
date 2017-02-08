package edu.gmu.cs.ai.cs580.project.connectfour.app;

import edu.gmu.cs.ai.cs580.project.connectfour.agent.ConnectFourAgent;
import edu.gmu.cs.ai.cs580.project.connectfour.agent.RandomMachinePlayer;
import edu.gmu.cs.ai.cs580.project.connectfour.common.GameState;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Player;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Sequence;
import edu.gmu.cs.ai.cs580.project.connectfour.common.SequenceConstants;
import edu.gmu.cs.ai.cs580.project.connectfour.ui.ConnectFourUI;

public class App {
    
    public static void main(String[] args) {
        
        GameState masterGameState = new GameState();
        
        ConnectFourUI userInterface = new ConnectFourUI(masterGameState);
        userInterface.displayUI();
        
        ConnectFourAgent player1 = new RandomMachinePlayer(Player.PLAYER_A);
        ConnectFourAgent player2 = new RandomMachinePlayer(Player.PLAYER_B);
        
        boolean complete = false;
        
        while(!complete) {
            Integer k = player1.getNextMove(masterGameState);
            try {
                masterGameState.makeMove(k, player1.getPlayer());
                userInterface.refresh();
                complete = masterGameState.containsFourInARow();
            }
            catch(Exception e) {
                complete = true;
                e.printStackTrace();
            }
            

            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                complete = true;
            }
            
            if(!complete) {
                Integer m = player2.getNextMove(masterGameState);
                try {
                    masterGameState.makeMove(m, player2.getPlayer());
                    userInterface.refresh();
                    complete = masterGameState.containsFourInARow();
                }
                catch(Exception e) {
                    complete = true;
                    e.printStackTrace();
                }
                
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    complete = true;
                }
            }
            
            
        }
        
        
        // Setup the game. Force them to click on "Start a new Game".
        

        
        int i = 1;
        for(Sequence seq : SequenceConstants.ALL_SEQUENCES) {
            System.out.println("Sequence #" + i + " - " + seq.toString());
            i++;
        }
    }
}
