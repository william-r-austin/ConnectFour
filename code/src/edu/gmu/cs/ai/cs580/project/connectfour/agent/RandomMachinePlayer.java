package edu.gmu.cs.ai.cs580.project.connectfour.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.gmu.cs.ai.cs580.project.connectfour.common.Constants;
import edu.gmu.cs.ai.cs580.project.connectfour.common.GameState;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Player;

public class RandomMachinePlayer implements ConnectFourAgent {
    
    private Player player;
    
    public RandomMachinePlayer(Player player) {
        this.player = player;
    }
    
    @Override
    public Integer getNextMove(GameState gameState) {
        
        List<Integer> validLocations = new ArrayList<Integer>();
        
        for(int i = 0; i < Constants.COLUMNS; i++) {
            Integer next = gameState.getNextEmptyRow(i);
            if(next >= 0 && next < Constants.ROWS) {
                validLocations.add(Integer.valueOf(i));
            }
        }
        
        if(!validLocations.isEmpty()) {
            Collections.shuffle(validLocations);
            return validLocations.get(0);
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
