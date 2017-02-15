package edu.gmu.cs.ai.cs580.project.connectfour.agent;

import edu.gmu.cs.ai.cs580.project.connectfour.common.GameState;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Player;
import edu.gmu.cs.ai.cs580.project.connectfour.ui.ConnectFourUI;

public class HumanPlayer implements ConnectFourAgent {

    public ConnectFourUI userInterface;
    private Player player;
    
    public HumanPlayer(Player player, ConnectFourUI userInterface) {
        this.player = player;
        this.userInterface = userInterface;
    }
    
    @Override
    public Integer getNextMove(GameState gameState) {
        userInterface.beginMouseMonitoring();
        Integer clickedColumn = userInterface.waitAndGetClickedColumn();
        return clickedColumn;
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
