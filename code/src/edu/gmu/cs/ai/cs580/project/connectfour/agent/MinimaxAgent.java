package edu.gmu.cs.ai.cs580.project.connectfour.agent;

import edu.gmu.cs.ai.cs580.project.connectfour.common.Constants;
import edu.gmu.cs.ai.cs580.project.connectfour.common.GameState;
import edu.gmu.cs.ai.cs580.project.connectfour.common.MinimaxResult;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Player;
import edu.gmu.cs.ai.cs580.project.connectfour.common.WinCheckResult;

public abstract class MinimaxAgent implements ConnectFourAgent {
    private Player player;
    private Integer depthCutoff;
    
    public MinimaxAgent(Player player, Integer depthCutoff) {
        this.player = player;
        this.depthCutoff = depthCutoff;
    }
    
    public MinimaxAgent(Player player) {
        this(player, 5);
    }
    
    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Integer getNextMove(GameState gameState) {
        
        MinimaxResult result = getMinimaxValue(gameState);
        return result.getColumnNumber();
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
        if(depth >= depthCutoff) {
            stateValue = evaluateBoard(gameState); 
            isTerminalState = true;
        }
        else {
            WinCheckResult wcr = gameState.checkForWinner();
            if(wcr.getIsGameComplete()) {
                isTerminalState = true;
                stateValue = 0;
                
                if(wcr.getFoundWinner()) {
                    if(Player.PLAYER_A.equals(wcr.getWinningPlayer())) {
                        stateValue = Integer.MAX_VALUE;
                    }
                    else {
                        stateValue = Integer.MIN_VALUE;
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
  
    public abstract Integer evaluateBoard(GameState gameState);
}
