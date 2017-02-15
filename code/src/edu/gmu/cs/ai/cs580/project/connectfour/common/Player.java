package edu.gmu.cs.ai.cs580.project.connectfour.common;

public enum Player {
    PLAYER_A(FillType.PLAYER_A, SequenceResultType.PLAYER_A),
    PLAYER_B(FillType.PLAYER_B, SequenceResultType.PLAYER_B);
    
    private FillType fillType;
    private SequenceResultType sequenceResultType;
    
    private Player(FillType fillType, SequenceResultType sequenceResultType) {
        this.fillType = fillType;
        this.sequenceResultType = sequenceResultType;
    }
    
    public FillType getFillType() {
        return fillType;
    }
    
    public SequenceResultType getSequenceResultType() {
        return sequenceResultType;
    }
    
    public static Player getPlayerFromFillType(FillType fillType) {
        for(Player player : Player.values()) {
            if(player.getFillType().equals(fillType)) {
                return player;
            }
        }
        
        return null;
    }
    

}
