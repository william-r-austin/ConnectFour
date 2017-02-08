package edu.gmu.cs.ai.cs580.project.connectfour.common;

public class Pair {
    
    private Integer a;
    private Integer b;
    
    public Pair(Integer a, Integer b) {
        this.a = a;
        this.b = b;
    }

    public Integer a() {
        return a;
    }

    public void a(Integer a) {
        this.a = a;
    }

    public Integer b() {
        return b;
    }

    public void b(Integer b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "(" + a + ", " + b + ")";
    }
    
    

}
