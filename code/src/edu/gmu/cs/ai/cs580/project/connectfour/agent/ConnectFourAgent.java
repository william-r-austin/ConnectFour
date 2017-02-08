package edu.gmu.cs.ai.cs580.project.connectfour.agent;

import edu.gmu.cs.ai.cs580.project.connectfour.common.GameState;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Player;

public interface ConnectFourAgent {
    public Player getPlayer();
    public Integer getNextMove(GameState gameState);
    public AgentType getAgentType();
}
