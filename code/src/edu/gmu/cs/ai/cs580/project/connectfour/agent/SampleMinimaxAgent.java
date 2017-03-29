package edu.gmu.cs.ai.cs580.project.connectfour.agent;

import java.util.List;

import edu.gmu.cs.ai.cs580.project.connectfour.common.GameState;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Player;
import edu.gmu.cs.ai.cs580.project.connectfour.common.SequenceResultType;
import edu.gmu.cs.ai.cs580.project.connectfour.common.SequenceState;

public class SampleMinimaxAgent extends MinimaxAgent {

    private static final Integer[] DEFAULT_WEIGHTS = {2, 100, 5000, 130000, -1, -85, -4000, -125000}; 
    //{2, 60, 2200, 80000, -1, -40, -2200, -10000};
    private final Integer[] weights;
    
    public SampleMinimaxAgent(Player player, Integer depthCutoff, Integer[] weights) {
        super(player, depthCutoff);
        this.weights = weights;
    }
    
    public SampleMinimaxAgent(Player player, Integer depthCutoff) {
        super(player, depthCutoff);
        this.weights = DEFAULT_WEIGHTS;
    }
    
    public SampleMinimaxAgent(Player player, Integer[] weights) {
        super(player);
        this.weights = DEFAULT_WEIGHTS;
    }
    
    public SampleMinimaxAgent(Player player) {
        super(player);
        this.weights = DEFAULT_WEIGHTS;
    }

    @Override
    public Integer evaluateBoard(GameState gameState) {
        Integer total = 0;
        List<SequenceState> usefulSequences = AgentUtilities.getUsefulSequenceStates(gameState);
        SequenceResultType mySequenceResultType = Player.PLAYER_A.getSequenceResultType();
        
        for(SequenceState ss : usefulSequences) {
            int size = ss.getSize();
            if(mySequenceResultType.equals(ss.getSequenceResultType())) {
                total += weights[size - 1];
            }
            else {
                total += weights[4 + (size - 1)];
            }
        }
        
        return total;
    }

}
