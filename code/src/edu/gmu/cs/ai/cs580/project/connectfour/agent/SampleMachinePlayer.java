package edu.gmu.cs.ai.cs580.project.connectfour.agent;

import edu.gmu.cs.ai.cs580.project.connectfour.common.Constants;
import edu.gmu.cs.ai.cs580.project.connectfour.common.GameState;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Player;

public class SampleMachinePlayer implements ConnectFourAgent {
    
    private Player player;
    
    public SampleMachinePlayer(Player player) {
        this.player = player;
    }
    
    @Override
    public Integer getNextMove(GameState gameState) {
        for(int i = 0; i < Constants.COLUMNS; i++) {
            Integer next = gameState.getNextEmptyRow(i);
            if(next >= 0 && next < Constants.ROWS) {
                return i;
            }
        }
        
        return -1;
    }

    @Override
    public AgentType getAgentType() {
        return AgentType.MACHINE;
    }
    
    @Override
    public Player getPlayer() {
        return player;
    }
}
