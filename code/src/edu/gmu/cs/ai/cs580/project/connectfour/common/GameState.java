package edu.gmu.cs.ai.cs580.project.connectfour.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameState {
    
    private FillType[][] boardState;
    private String moveSequence;
    private String stateString;
    private Player playerToMove;
    private Integer[] nextEmptyRow;
    
    public GameState() {
        init();
    }
    
    public GameState(GameState other) {
        init();
        copy(other);
    }
    
    private void init() {
        boardState = new FillType[Constants.COLUMNS][Constants.ROWS];
        initializeBoardToEmpty();
        nextEmptyRow = new Integer[Constants.COLUMNS];
        Arrays.fill(nextEmptyRow, 0);
        moveSequence = "";
        playerToMove = Player.PLAYER_A;
        refreshStateString();
    }
    
    private void copy(GameState other) {
        
        for(int col = 0; col < Constants.COLUMNS; col++) {
            for(int row = 0; row < Constants.ROWS; row++) {
                boardState[col][row] = other.getFillType(col, row);
            }
        }
        
        stateString = other.getStateString();
        moveSequence = other.getMoveSequence();
        playerToMove = other.getPlayerToMove();
        
        for(int i = 0; i < Constants.COLUMNS; i++) {
            nextEmptyRow[i] = other.getNextEmptyRow(i);
        }
    }
    
    private void refreshStateString() {
        StringBuilder sb = new StringBuilder();
        for(int col = 0; col < Constants.COLUMNS; col++) {
            for(int row = 0; row < Constants.ROWS; row++) {
                sb.append(boardState[col][row].getCharacterValue());
            }
        }
        
        stateString = sb.toString();
    }
    
    public String getStateString() {
        return stateString;
    }
    
    public String getMoveSequence() {
        return moveSequence;
    }
    
    public Player getPlayerToMove() {
        return playerToMove;
    }
    
    public Integer getNextEmptyRow(Integer column) {
        if(column == null) {
            throw new IllegalArgumentException("Invalid method argument: Values for column cannot be null.");
        }
        
        if(column < 0 || column >= Constants.COLUMNS) {
            throw new IllegalArgumentException("Invalid method argument. Values was: " + column);
        }
        
        return nextEmptyRow[column];
    }
    
    private void initializeBoardToEmpty() {
        for(FillType[] column: boardState) {
            Arrays.fill(column, FillType.EMPTY);
        }
    }
    
    public FillType getFillType(Integer column, Integer row) {
        if(column == null || row == null) {
            throw new IllegalArgumentException("Invalid method arguments: Values for row and column cannot be null.");
        }
        
        if(column < 0 || row < 0 || column >= Constants.COLUMNS || row >= Constants.ROWS) {
            throw new IllegalArgumentException("Invalid method arguments. Values were: (" + column + ", " + row + ")");
        }
        
        return boardState[column][row];
    }
    
    public void makeMove(Integer columnSelected, Player movingPlayer) {
        if(playerToMove.equals(movingPlayer)) {
            if(columnSelected < 0 || columnSelected >= Constants.COLUMNS) {
                throw new IllegalArgumentException("Invalid column value. The provided value was: " + columnSelected + 
                    ", but it must be between 0 and " + (Constants.COLUMNS - 1) + " (inclusive).");
            }
            
            Integer rowValue = getNextEmptyRow(columnSelected);
            
            if(rowValue < 0 || rowValue >= Constants.ROWS) {
                throw new IllegalArgumentException("Invalid row value. The value of the next empty row is: " + rowValue);
            }
            
            FillType fillType = boardState[columnSelected][rowValue];
            
            if(!FillType.EMPTY.equals(fillType)) {
                throw new IllegalArgumentException("Invalid fill type. The requested position (" + columnSelected + 
                    ", " + rowValue + ") is already filled. The value is: " + fillType.toString());
            }
            
            if(Player.PLAYER_A.equals(playerToMove)) {
                boardState[columnSelected][rowValue] = FillType.PLAYER_A;
                playerToMove = Player.PLAYER_B;
            }
            else if(Player.PLAYER_B.equals(playerToMove)) {
                boardState[columnSelected][rowValue] = FillType.PLAYER_B;
                playerToMove = Player.PLAYER_A;
            }
            else {
                throw new IllegalArgumentException("Invalid player to move value.");
            }
            
            moveSequence += String.valueOf(columnSelected);
            
            int newNextEmptyRow = rowValue + 1;
            if(newNextEmptyRow >= Constants.ROWS) {
                newNextEmptyRow = -1;
            }
            
            nextEmptyRow[columnSelected] = newNextEmptyRow;
            refreshStateString();
        }
        else {
            throw new IllegalArgumentException("Out of order move. Expected move from " + playerToMove.toString() + 
               ", but received a move from " + movingPlayer.toString());
        }
    }
    
    public WinCheckResult checkForWinner() {
        for(Sequence sequence : SequenceConstants.ALL_SEQUENCES) {
            WinCheckResult wcr = populateWinCheckResultForSequence(sequence);
            if(wcr != null && wcr.getFoundWinner()) {
                return wcr;
            }
        }
                
        return new WinCheckResult();
    }
    
    public WinCheckResult populateWinCheckResultForSequence(Sequence sequence) {
        Set<FillType> fillTypes = new HashSet<FillType>();
        
        for(Square p : sequence) {
            FillType fillType = boardState[p.col()][p.row()];
            
            if(FillType.EMPTY.equals(fillType)) {
                return null;
            }
            
            fillTypes.add(fillType);
            if(fillTypes.size() > 1) {
                return null;
            }
        }
        
        Player winningPlayer = Player.getPlayerFromFillType(fillTypes.iterator().next());
        
        WinCheckResult winCheckResult = new WinCheckResult();
        winCheckResult.setFoundWinner(true);
        winCheckResult.setWinningPlayer(winningPlayer);
        
        return winCheckResult;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((stateString == null) ? 0 : stateString.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GameState other = (GameState) obj;
        if (stateString == null) {
            if (other.stateString != null)
                return false;
        } else if (!stateString.equals(other.stateString))
            return false;
        return true;
    }
}
