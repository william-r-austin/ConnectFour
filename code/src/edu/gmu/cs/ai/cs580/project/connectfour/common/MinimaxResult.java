package edu.gmu.cs.ai.cs580.project.connectfour.common;

public class MinimaxResult {
    Integer moveValue;
    Integer columnNumber;
    
    public MinimaxResult(Integer moveValue, Integer columnNumber) {
        this.moveValue = moveValue;
        this.columnNumber = columnNumber;
    }
    
    public MinimaxResult(Integer moveValue) {
        this.moveValue = moveValue;
    }
    
    public MinimaxResult() {}
    
    public Integer getMoveValue() {
        return moveValue;
    }
    public void setMoveValue(Integer moveValue) {
        this.moveValue = moveValue;
    }
    public Integer getColumnNumber() {
        return columnNumber;
    }
    public void setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
    }
    
    

}
