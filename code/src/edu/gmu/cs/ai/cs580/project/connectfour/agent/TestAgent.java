package edu.gmu.cs.ai.cs580.project.connectfour.agent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.gmu.cs.ai.cs580.project.connectfour.common.Constants;
import edu.gmu.cs.ai.cs580.project.connectfour.common.FillType;
import edu.gmu.cs.ai.cs580.project.connectfour.common.GameState;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Player;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Sequence;
import edu.gmu.cs.ai.cs580.project.connectfour.common.SequenceConstants;
import edu.gmu.cs.ai.cs580.project.connectfour.common.SequenceResultType;
import edu.gmu.cs.ai.cs580.project.connectfour.common.SequenceState;
import edu.gmu.cs.ai.cs580.project.connectfour.common.SquareState;

public class TestAgent implements ConnectFourAgent {
    
    private Player player;
    
    public TestAgent(Player player) {
        this.player = player;
    }
    
    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Integer getNextMove(GameState gameState) {
        Map<Integer, Integer> depthOneScores = getDepthOneScores(gameState);
        Integer bestColumn = -1;
        Integer bestScore = Integer.MIN_VALUE;
        
        if(!depthOneScores.isEmpty()) {
            for(Map.Entry<Integer, Integer> entry : depthOneScores.entrySet()) {
                if(entry.getValue() > bestScore) {
                    bestColumn = entry.getKey();
                    bestScore = entry.getValue();
                }
            }
        }
        
        return bestColumn;
    }

    @Override
    public AgentType getAgentType() {
        return AgentType.MACHINE;
    }
    
    public Map<Integer, Integer> getDepthOneScores(GameState gameState) {
        
        Map<Integer, Integer> resultMap = new LinkedHashMap<Integer, Integer>();
        
        for(Integer testColumn = 0; testColumn < Constants.COLUMNS; testColumn++) {
            Integer nextRow = gameState.getNextEmptyRow(testColumn);
            
            if(nextRow >= 0 && nextRow < Constants.ROWS) {
                GameState nextGameState = new GameState(gameState);
                nextGameState.makeMove(testColumn, getPlayer());
                Integer score = evaluateBoard(nextGameState);
                resultMap.put(testColumn, score);
            }
        }
        
        return resultMap;
    }
    
    // This is w.r.t AFTER the player has moved.
    public Integer evaluateBoard(GameState gameState) {
        int[] posScores = {2, 6, 22, 5000};
        int[] negScores = {-1, -4, -22, -10000};
        
        Integer total = 0;
        List<SequenceState> usefulSequences = getUsefulSequenceStates(gameState);
        SequenceResultType mySequenceResultType = getPlayer().getSequenceResultType();
        
        for(SequenceState ss : usefulSequences) {
            int size = ss.getSize();
            if(mySequenceResultType.equals(ss.getSequenceResultType())) {
                total += posScores[size - 1];
            }
            else {
                total += negScores[size - 1];
            }
        }
        
        return total;
    }
    
    public List<SequenceState> getUsefulSequenceStates(GameState gameState) {
        List<SequenceState> all = getAllSequenceStates(gameState);
        List<SequenceState> useful = new ArrayList<SequenceState>();
        
        for(SequenceState ss : all) {
            if(ss != null && !SequenceResultType.EMPTY.equals(ss.getSequenceResultType()) && 
                    !SequenceResultType.MIXED.equals(ss.getSequenceResultType())) {
                useful.add(ss);
            }
        }
        
        return useful;
    }
    
    public List<SequenceState> getAllSequenceStates(GameState gameState) {
        List<SequenceState> allSequenceStates = new ArrayList<SequenceState>();
        
        for(Sequence sequence : SequenceConstants.ALL_SEQUENCES) {
            SequenceState sequenceState = new SequenceState(sequence);
            
            for(int k = 0; k < Constants.WINNING_DISCS_COUNT; k++) {
                Integer col = sequence.getPair(k).col();
                Integer row = sequence.getPair(k).row();
                FillType fillType = gameState.getFillType(col, row);
                
                if(FillType.EMPTY.equals(fillType)) {
                    // Set the empty squares
                    Integer nextAvailableRow = gameState.getNextEmptyRow(col);
                    SquareState emptySquareState = new SquareState();
                    emptySquareState.setSquare(sequence.getPair(k));
                    emptySquareState.setHeight(row - nextAvailableRow);
                    sequenceState.addEmptySquareState(emptySquareState);
                }
                else if(FillType.PLAYER_A.equals(fillType)) {
                    if(SequenceResultType.EMPTY.equals(sequenceState.getSequenceResultType())) {
                        sequenceState.setSequenceResultType(SequenceResultType.PLAYER_A);
                        sequenceState.setSize(1);
                    }
                    else if(SequenceResultType.PLAYER_A.equals(sequenceState.getSequenceResultType())) {                    
                        sequenceState.incrementSize();
                    }
                    else if(SequenceResultType.PLAYER_B.equals(sequenceState.getSequenceResultType())) {
                        sequenceState.setSequenceResultType(SequenceResultType.MIXED);
                        sequenceState.setSize(0);
                    }
                }
                else if(FillType.PLAYER_B.equals(fillType)) {
                    if(SequenceResultType.EMPTY.equals(sequenceState.getSequenceResultType())) {
                        sequenceState.setSequenceResultType(SequenceResultType.PLAYER_B);
                        sequenceState.setSize(1);
                    }
                    else if(SequenceResultType.PLAYER_A.equals(sequenceState.getSequenceResultType())) {                    
                        sequenceState.setSequenceResultType(SequenceResultType.MIXED);
                        sequenceState.setSize(0);
                    }
                    else if(SequenceResultType.PLAYER_B.equals(sequenceState.getSequenceResultType())) {
                        sequenceState.incrementSize();
                    }
                }
            }
            
            allSequenceStates.add(sequenceState);
        }
        
        return allSequenceStates;
    }
    
}
