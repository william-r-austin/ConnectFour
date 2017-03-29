package edu.gmu.cs.ai.cs580.project.connectfour.agent;

import java.util.ArrayList;
import java.util.List;

import edu.gmu.cs.ai.cs580.project.connectfour.common.Constants;
import edu.gmu.cs.ai.cs580.project.connectfour.common.FillType;
import edu.gmu.cs.ai.cs580.project.connectfour.common.GameState;
import edu.gmu.cs.ai.cs580.project.connectfour.common.Sequence;
import edu.gmu.cs.ai.cs580.project.connectfour.common.SequenceConstants;
import edu.gmu.cs.ai.cs580.project.connectfour.common.SequenceResultType;
import edu.gmu.cs.ai.cs580.project.connectfour.common.SequenceState;
import edu.gmu.cs.ai.cs580.project.connectfour.common.SquareState;

public class AgentUtilities {
    
    public static List<SequenceState> getUsefulSequenceStates(GameState gameState) {
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
    
    public static List<SequenceState> getAllSequenceStates(GameState gameState) {
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
    
    public static void printBoard(GameState gameState) {
        for(int row = Constants.ROWS - 1; row >= 0; row--) {
        
            StringBuilder sb = new StringBuilder();
            for(int col = 0; col < Constants.COLUMNS; col++) {    
                FillType fillType = gameState.getFillType(col, row);
                sb.append(fillType.getCharacterValue());
            }
            System.out.println(sb.toString());
        }
        //System.out.println("-------");
    }

}
