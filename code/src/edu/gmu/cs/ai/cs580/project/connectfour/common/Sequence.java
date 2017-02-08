package edu.gmu.cs.ai.cs580.project.connectfour.common;

import java.util.Arrays;
import java.util.Iterator;

public class Sequence implements Iterable<Pair> {
    private Pair[] locations = new Pair[Constants.WINNING_DISCS_COUNT];
    private SequenceType sequenceType;
    
    public void setPair(Integer pairNumber, Pair pairValue) {
        if(pairNumber != null && pairNumber >= 0 && pairNumber < Constants.WINNING_DISCS_COUNT) {
            locations[pairNumber] = pairValue;
        }
        else {
            throw new IllegalArgumentException("Invalid index for pair number. Value was: " + pairNumber);
        }
    }
    
    public Pair getPair(Integer pairNumber) {
        Pair pair = null;
        
        if(pairNumber != null && pairNumber >= 0 && pairNumber < Constants.WINNING_DISCS_COUNT) {
            pair = locations[pairNumber];
        }
        else {
            throw new IllegalArgumentException("Invalid index for pair number. Value was: " + pairNumber);
        }

        return pair;
    }

    public SequenceType getSequenceType() {
        return sequenceType;
    }

    public void setSequenceType(SequenceType sequenceType) {
        this.sequenceType = sequenceType;
    }

    @Override
    public String toString() {
        return "Sequence: sequenceType = " + sequenceType + ", locations = " + Arrays.toString(locations);
    }

    @Override
    public Iterator<Pair> iterator() {
        Iterator<Pair> myIterator = new SequenceIterator(this);
        return myIterator;
    }

}
