package edu.gmu.cs.ai.cs580.project.connectfour.common;

public class SequenceConstants {
    public static final Sequence[] ALL_SEQUENCES = generateAllSequences();
    
    private static Sequence[] generateAllSequences() {
        
        Sequence[] horizontal = generateHorizontalSequences();
        Sequence[] vertical = generateVerticalSequences();
        Sequence[] diagonalUp = generateDiagonalUpSequences();
        Sequence[] diagonalDown = generateDiagonalDownSequences();
        
        Sequence[] all = new Sequence[horizontal.length + vertical.length + diagonalUp.length + diagonalDown.length];
        
        int z = 0;
        for(Sequence seq : horizontal) {
            all[z] = seq;
            z++;
        }
        
        for(Sequence seq : vertical) {
            all[z] = seq;
            z++;
        }
        
        for(Sequence seq : diagonalUp) {
            all[z] = seq;
            z++;
        }
        
        for(Sequence seq : diagonalDown) {
            all[z] = seq;
            z++;
        }
        
        return all;
    }
    
    private static Sequence[] generateHorizontalSequences() {
        int totalHorizontal = Constants.ROWS * (Constants.COLUMNS - Constants.WINNING_DISCS_COUNT + 1);
        
        Sequence[] horzSequences = new Sequence[totalHorizontal];
        int x = 0;
        
        for(int i = 0; i < Constants.COLUMNS - Constants.WINNING_DISCS_COUNT + 1; i++) {
            for(int j = 0; j < Constants.ROWS; j++) {
                Sequence newSequence = new Sequence();
                newSequence.setSequenceType(OrientationType.HORIZONTAL);
                for(int k = 0; k < Constants.WINNING_DISCS_COUNT; k++) {
                    Square pair = new Square(i + k, j);
                    newSequence.setPair(k, pair);
                }
                horzSequences[x] = newSequence;
                x++;
            }
        }
        
        return horzSequences;
    }
    
    private static Sequence[] generateVerticalSequences() {
        int totalVertical = Constants.COLUMNS * (Constants.ROWS - Constants.WINNING_DISCS_COUNT + 1);
        
        Sequence[] vertSequences = new Sequence[totalVertical];
        int x = 0;
        
        for(int i = 0; i < Constants.COLUMNS; i++) {
            for(int j = 0; j < Constants.ROWS - Constants.WINNING_DISCS_COUNT + 1; j++) {
                Sequence newSequence = new Sequence();
                newSequence.setSequenceType(OrientationType.VERTICAL);
                for(int k = 0; k < Constants.WINNING_DISCS_COUNT; k++) {
                    Square pair = new Square(i, j + k);
                    newSequence.setPair(k, pair);
                }
                vertSequences[x] = newSequence;
                x++;
            }
        }
        
        return vertSequences;
    }
    
    private static Sequence[] generateDiagonalUpSequences() {
        int totalDiagonal = (Constants.COLUMNS - Constants.WINNING_DISCS_COUNT + 1) * (Constants.ROWS - Constants.WINNING_DISCS_COUNT + 1);
        
        Sequence[] diagSequences = new Sequence[totalDiagonal];
        int x = 0;
        
        for(int i = 0; i < Constants.COLUMNS - Constants.WINNING_DISCS_COUNT + 1; i++) {
            for(int j = 0; j < Constants.ROWS - Constants.WINNING_DISCS_COUNT + 1; j++) {
                Sequence newSequence = new Sequence();
                newSequence.setSequenceType(OrientationType.DIAGONAL_UP);
                for(int k = 0; k < Constants.WINNING_DISCS_COUNT; k++) {
                    Square pair = new Square(i + k, j + k);
                    newSequence.setPair(k, pair);
                }
                diagSequences[x] = newSequence;
                x++;
            }
        }
        
        return diagSequences;
    }
    
    private static Sequence[] generateDiagonalDownSequences() {
        int totalDiagonal = (Constants.COLUMNS - Constants.WINNING_DISCS_COUNT + 1) * (Constants.ROWS - Constants.WINNING_DISCS_COUNT + 1);
        
        Sequence[] diagSequences = new Sequence[totalDiagonal];
        int x = 0;
        
        for(int i = 0; i < Constants.COLUMNS - Constants.WINNING_DISCS_COUNT + 1; i++) {
            for(int j = 0; j < Constants.ROWS - Constants.WINNING_DISCS_COUNT + 1; j++) {
                Sequence newSequence = new Sequence();
                newSequence.setSequenceType(OrientationType.DIAGONAL_DOWN);
                for(int k = 0; k < Constants.WINNING_DISCS_COUNT; k++) {
                    Square pair = new Square(i + k, j + Constants.WINNING_DISCS_COUNT - k - 1);
                    newSequence.setPair(k, pair);
                }
                diagSequences[x] = newSequence;
                x++;
            }
        }
        
        return diagSequences;
    }

}
