package edu.gmu.cs.ai.cs580.project.connectfour.agent;

import edu.gmu.cs.ai.cs580.project.connectfour.common.GameState;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Player;
import edu.gmu.cs.ai.cs580.project.connectfour.ui.ConnectFourUI;

public class HumanPlayer implements ConnectFourAgent {

    public ConnectFourUI userInterface;
    private Player player;
    
    public HumanPlayer(Player player) {
        this.player = player;
    }
    
    @Override
    public Integer getNextMove(GameState gameState) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public AgentType getAgentType() {
        return AgentType.HUMAN;
    }
    
    @Override
    public Player getPlayer() {
        return player;
    }

}
