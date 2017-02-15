package edu.gmu.cs.ai.cs580.project.connectfour.common;

import java.util.ArrayList;
import java.util.List;

public class SequenceState {
    
    private Sequence sequence;
    private SequenceResultType sequenceResultType;
    private List<SquareState> emptySquareStates;
    private Integer size;
     
    public SequenceState(Sequence sequence) {
        this.sequence = sequence;
        this.sequenceResultType = SequenceResultType.EMPTY;
        this.emptySquareStates = new ArrayList<SquareState>();
        this.size = 0;
    }
    
    public Sequence getSequence() {
        return sequence;
    }
    
    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }
    
    public SequenceResultType getSequenceResultType() {
        return sequenceResultType;
    }

    public void setSequenceResultType(SequenceResultType sequenceResultType) {
        this.sequenceResultType = sequenceResultType;
    }

    public List<SquareState> getEmptySquareStates() {
        return emptySquareStates;
    }

    public void addEmptySquareState(SquareState emptySquareState) {
        emptySquareStates.add(emptySquareState);
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
    
    public void incrementSize() {
        this.size++;
    }
}
