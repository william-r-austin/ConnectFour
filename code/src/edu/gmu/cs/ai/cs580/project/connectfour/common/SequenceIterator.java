package edu.gmu.cs.ai.cs580.project.connectfour.common;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SequenceIterator implements Iterator<Square> {
    
    private Sequence sequence;
    private int index;
    
    public SequenceIterator(Sequence sequence) {
        this.sequence = sequence;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        // TODO Auto-generated method stub
        return (index < Constants.WINNING_DISCS_COUNT);
    }

    @Override
    public Square next() {
        if(hasNext()) {
            Square pair = sequence.getPair(index);
            index++;
            return pair;
        }
        
        throw new NoSuchElementException();
    }
    
}
