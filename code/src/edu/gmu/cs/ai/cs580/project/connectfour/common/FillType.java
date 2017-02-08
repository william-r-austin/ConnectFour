package edu.gmu.cs.ai.cs580.project.connectfour.common;

import java.awt.Color;

public enum FillType {
    PLAYER_A('A', Color.BLUE),
    PLAYER_B('B', Color.RED),
    EMPTY('-', Color.LIGHT_GRAY);
    
    private Character characterValue;
    private Color color;
    
    private FillType(Character charValue, Color color) {
        this.characterValue = charValue;
        this.color = color;
    }
    
    public Character getCharacterValue() {
        return characterValue;
    }
    
    public Color getColor() {
        return color;
    }
    
    @Override
    public String toString() {
        return name();
    }
}
