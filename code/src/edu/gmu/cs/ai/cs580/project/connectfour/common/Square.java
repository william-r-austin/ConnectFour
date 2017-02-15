package edu.gmu.cs.ai.cs580.project.connectfour.common;

public class Square {
    
    private Integer a;
    private Integer b;
    
    public Square(Integer a, Integer b) {
        this.a = a;
        this.b = b;
    }

    public Integer col() {
        return a;
    }

    public void col(Integer a) {
        this.a = a;
    }

    public Integer row() {
        return b;
    }

    public void row(Integer b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "(" + a + ", " + b + ")";
    }
    
    

}
