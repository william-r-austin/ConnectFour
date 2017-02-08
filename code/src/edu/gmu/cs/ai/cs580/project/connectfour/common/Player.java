package edu.gmu.cs.ai.cs580.project.connectfour.common;

public enum Player {
    PLAYER_A(FillType.PLAYER_A),
    PLAYER_B(FillType.PLAYER_B);
    
    private FillType fillType;
    
    private Player(FillType fillType) {
        this.fillType = fillType;
    }
    
    public FillType getFillType() {
        return fillType;
    }
}
