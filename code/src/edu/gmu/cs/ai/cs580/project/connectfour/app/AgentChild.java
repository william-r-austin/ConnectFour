package edu.gmu.cs.ai.cs580.project.connectfour.app;

import edu.gmu.cs.ai.cs580.project.connectfour.agent.ConnectFourAgent;
import edu.gmu.cs.ai.cs580.project.connectfour.agent.TestAgent;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Player;

public class AgentChild {
    private ConnectFourAgent agentA;
    private ConnectFourAgent agentB;
    private Integer totalWins;
    private Integer[] weights;
    private Integer index;
    
    public AgentChild(Integer[] weights, Integer index) {
        this.agentA = new TestAgent(Player.PLAYER_A, weights);
        this.agentB = new TestAgent(Player.PLAYER_B, weights);
        this.totalWins = 0;
        this.weights = weights;
        this.index = index;
    }
    
    public ConnectFourAgent getAgentA() {
        return agentA;
    }
    
    public ConnectFourAgent getAgentB() {
        return agentB;
    }
    
    public Integer[] getWeights() {
        return weights;
    }
    
    public void addWins(Integer numWins) {
        totalWins += numWins; 
    }
    
    public Integer getWins() {
        return totalWins;
    }
    
    public Integer getIndex() {
        return index;
    }

}
