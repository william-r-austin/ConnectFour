package edu.gmu.cs.ai.cs580.project.connectfour.agent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.gmu.cs.ai.cs580.project.connectfour.common.Constants;
import edu.gmu.cs.ai.cs580.project.connectfour.common.FillType;
import edu.gmu.cs.ai.cs580.project.connectfour.common.GameState;
import edu.gmu.cs.ai.cs580.project.connectfour.common.MinimaxResult;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Player;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Sequence;
import edu.gmu.cs.ai.cs580.project.connectfour.common.SequenceConstants;
import edu.gmu.cs.ai.cs580.project.connectfour.common.SequenceResultType;
import edu.gmu.cs.ai.cs580.project.connectfour.common.SequenceState;
import edu.gmu.cs.ai.cs580.project.connectfour.common.SquareState;
import edu.gmu.cs.ai.cs580.project.connectfour.common.WinCheckResult;

public class TestAgent implements ConnectFourAgent {
    
    private Player player;
    private final Integer DEPTH_CUTOFF = 6;
    
    private static final Integer[] DEFAULT_WEIGHTS = //{2, 60, 2200, 80000, -1, -40, -2200, -10000};
        {2, 34, 2907, 104618, -4, -197, -968, -6588};
    
    private Integer[] positiveWeights = new Integer[4];
    private Integer[] negativeWeights = new Integer[4];
    
    public TestAgent(Player player, Integer[] weights) {
        this.player = player;
        for(int i = 0; i < 4; i++) {
            positiveWeights[i] = weights[i];
        }
        
        for(int j = 4; j < 8; j++) {
            negativeWeights[j - 4] = weights[j];
        }
    }
    
    public TestAgent(Player player) {
        this(player, DEFAULT_WEIGHTS);
    }
    
    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Integer getNextMove(GameState gameState) {
        
        MinimaxResult result = getMinimaxValue(gameState);
        return result.getColumnNumber();

        /*
        Map<Integer, Integer> depthOneScores = getDepthOneScores(gameState);
        Integer bestColumn = -1;
        Integer bestScore = Integer.MIN_VALUE;
        
        if(!depthOneScores.isEmpty()) {
            for(Map.Entry<Integer, Integer> entry : depthOneScores.entrySet()) {
                
                System.out.println("Move #" + gameState.getMoveSequence().length() 
                    + ", Column #" + entry.getKey() + ", Value = " + entry.getValue());
                if(entry.getValue() > bestScore) {
                    bestColumn = entry.getKey();
                    bestScore = entry.getValue();
                }
            }
        }

        System.out.println("Move #" + gameState.getMoveSequence().length() 
                + ", Best Column = " + bestColumn);
        return bestColumn;*/
    }

    @Override
    public AgentType getAgentType() {
        return AgentType.MACHINE;
    }
    
    private MinimaxResult getMaxValue(GameState gameState, Integer depth, Integer alpha, Integer beta) {
        return minimaxHelper(gameState, depth, true, alpha, beta);
    }
    
    private MinimaxResult getMinValue(GameState gameState, Integer depth, Integer alpha, Integer beta) {
        return minimaxHelper(gameState, depth, false, alpha, beta);
    }
    
    private MinimaxResult minimaxHelper(GameState gameState, Integer depth, Boolean isMax, Integer alpha, Integer beta) {
        Boolean isTerminalState = false;
        Integer stateValue = null;
        if(depth >= DEPTH_CUTOFF) {
            stateValue = evaluateBoardForPlayerA(gameState); 
            isTerminalState = true;
        }
        else {
            WinCheckResult wcr = gameState.checkForWinner();
            if(wcr.getIsGameComplete()) {
                isTerminalState = true;
                stateValue = 0;
                
                if(wcr.getFoundWinner()) {
                    if(Player.PLAYER_A.equals(wcr.getWinningPlayer())) {
                        stateValue = positiveWeights[3];
                    }
                    else {
                        stateValue = negativeWeights[3];
                    }
                    
                }
            }
        }

        if(isTerminalState) {
            return new MinimaxResult(stateValue);
        }
        
        MinimaxResult best = new MinimaxResult();
        
        for(Integer testColumn = 0; testColumn < Constants.COLUMNS; testColumn++) {
            Integer nextRow = gameState.getNextEmptyRow(testColumn);
            
            if(nextRow >= 0 && nextRow < Constants.ROWS) {
                GameState nextGameState = new GameState(gameState);
                nextGameState.makeMove(testColumn, nextGameState.getPlayerToMove());
                
                MinimaxResult current = null;
                if(isMax) {
                    current = getMinValue(nextGameState, depth + 1, alpha, beta);
                }
                else {
                    current = getMaxValue(nextGameState, depth + 1, alpha, beta);
                }
                
                Integer bestMoveValue = best.getMoveValue();
                Integer currentMoveValue = current.getMoveValue();
                
                if(bestMoveValue == null)  {
                    best.setMoveValue(currentMoveValue);
                    best.setColumnNumber(testColumn);
                }
                else if(isMax) {
                    if(current.getMoveValue() > bestMoveValue) {
                        best.setMoveValue(currentMoveValue);
                        best.setColumnNumber(testColumn);
                    }
                    
                    if(best.getMoveValue() >= beta) {
                        return best;
                    }
                    
                    alpha = Math.max(alpha, best.getMoveValue());
                        
                }
                else {
                    if(current.getMoveValue() < bestMoveValue) {
                        best.setMoveValue(currentMoveValue);
                        best.setColumnNumber(testColumn);
                    }
                    
                    if(best.getMoveValue() <= alpha) {
                        return best;
                    }
                    
                    beta = Math.min(beta, best.getMoveValue());
                }
            }
        }
        
        return best;        
    }
    
    private MinimaxResult getMinimaxValue(GameState gameState) {
        MinimaxResult result = null;
        if(Player.PLAYER_A.equals(gameState.getPlayerToMove())) {
            result = getMaxValue(gameState, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        else {
            result = getMinValue(gameState, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        return result;
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
    public Integer evaluateBoardForPlayerA(GameState gameState) {

        
        Integer total = 0;
        List<SequenceState> usefulSequences = getUsefulSequenceStates(gameState);
        SequenceResultType mySequenceResultType = Player.PLAYER_A.getSequenceResultType();
        
        for(SequenceState ss : usefulSequences) {
            int size = ss.getSize();
            if(mySequenceResultType.equals(ss.getSequenceResultType())) {
                total += positiveWeights[size - 1];
            }
            else {
                total += negativeWeights[size - 1];
            }
        }
        
        return total;
    }
    
    
    // This is w.r.t AFTER the player has moved.
    public Integer evaluateBoard(GameState gameState) {
        
        Integer total = 0;
        List<SequenceState> usefulSequences = getUsefulSequenceStates(gameState);
        SequenceResultType mySequenceResultType = getPlayer().getSequenceResultType();
        
        for(SequenceState ss : usefulSequences) {
            int size = ss.getSize();
            if(mySequenceResultType.equals(ss.getSequenceResultType())) {
                total += positiveWeights[size - 1];
            }
            else {
                total += negativeWeights[size - 1];
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
