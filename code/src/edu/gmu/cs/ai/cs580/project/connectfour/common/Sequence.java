package edu.gmu.cs.ai.cs580.project.connectfour.common;

import java.util.Arrays;
import java.util.Iterator;

public class Sequence implements Iterable<Square> {
    private Square[] locations = new Square[Constants.WINNING_DISCS_COUNT];
    private OrientationType sequenceType;
    
    public void setPair(Integer pairNumber, Square pairValue) {
        if(pairNumber != null && pairNumber >= 0 && pairNumber < Constants.WINNING_DISCS_COUNT) {
            locations[pairNumber] = pairValue;
        }
        else {
            throw new IllegalArgumentException("Invalid index for pair number. Value was: " + pairNumber);
        }
    }
    
    public Square getPair(Integer pairNumber) {
        Square pair = null;
        
        if(pairNumber != null && pairNumber >= 0 && pairNumber < Constants.WINNING_DISCS_COUNT) {
            pair = locations[pairNumber];
        }
        else {
            throw new IllegalArgumentException("Invalid index for pair number. Value was: " + pairNumber);
        }

        return pair;
    }

    public OrientationType getSequenceType() {
        return sequenceType;
    }

    public void setSequenceType(OrientationType sequenceType) {
        this.sequenceType = sequenceType;
    }

    @Override
    public String toString() {
        return "Sequence: sequenceType = " + sequenceType + ", locations = " + Arrays.toString(locations);
    }

    @Override
    public Iterator<Square> iterator() {
        Iterator<Square> myIterator = new SequenceIterator(this);
        return myIterator;
    }

}
