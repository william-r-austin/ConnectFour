package edu.gmu.cs.ai.cs580.project.connectfour.common;

public class WinCheckResult {
    private Boolean foundWinner = false;
    private Player winningPlayer = null;
    private Sequence winningSequence = null;
    
    public Boolean getFoundWinner() {
        return foundWinner;
    }
    
    public void setFoundWinner(Boolean foundWinner) {
        this.foundWinner = foundWinner;
    }
    
    public Player getWinningPlayer() {
        return winningPlayer;
    }
    
    public void setWinningPlayer(Player winningPlayer) {
        this.winningPlayer = winningPlayer;
    }
    
    public Sequence getWinningSequence() {
        return winningSequence;
    }
    
    public void setWinningSequence(Sequence winningSequence) {
        this.winningSequence = winningSequence;
    }

}
