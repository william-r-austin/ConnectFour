package edu.gmu.cs.ai.cs580.project.connectfour.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.gmu.cs.ai.cs580.project.connectfour.common.Player;
import edu.gmu.cs.ai.cs580.project.connectfour.common.WinCheckResult;

public class ParameterTuningApp {
    
    public static final Integer POOL_SIZE = 7;
    public static final Random random = new Random();
    
    public static void main(String[] args) {
        AgentChild[] pool = new AgentChild[POOL_SIZE];
        
        for(int i = 0; i < POOL_SIZE; i++) {
            pool[i] = new AgentChild(defaultWeights(), i);
        }
        
        int generation = 1;
        
        while(generation <= 10000) {
            
            compete(pool);
            
            Integer[] cdfArray = getCdfArray(pool);
            System.out.print("Generation = " + generation + ", CDF Array = " + Arrays.toString(cdfArray));
                                
            AgentChild[] nextGenPool = new AgentChild[POOL_SIZE];
            
            Integer maxWins = -1;
            List<Integer> indices = new ArrayList<Integer>();
            System.out.println("Calculating top performers for generation " + generation + ". Total points awarded = " + cdfArray[POOL_SIZE -1]);
            for(int q = 0; q < POOL_SIZE; q++) {
                Integer wins = pool[q].getWins();
                System.out.println("Agent " + q + " = " + Arrays.toString(pool[q].getWeights()) + ", score = " + wins);
                if(wins > maxWins) {
                    indices.clear();
                    maxWins = wins;
                    indices.add(q);
                }
                else if(wins == maxWins) {
                    boolean found = false;
                    if(!indices.isEmpty()) {
                        for(Integer existingIndex : indices) {
                            Integer[] existingWeights = pool[existingIndex].getWeights();
                            
                            if(Arrays.equals(existingWeights, pool[q].getWeights())) {
                                found = true;
                            }
                        }
                    }
                    
                    if(!found) {
                        indices.add(q);
                    }
                }
            }
            
            System.out.println("Agents to be saved: " + indices.toString());
            
            int x = 0;
            for(Integer j : indices) {
                AgentChild repeatAgent = new AgentChild(pool[j].getWeights(), x);
                nextGenPool[x] = repeatAgent;
                x++;
            }
            
            for(int z = x; z < POOL_SIZE; z++) {
                Integer randIndex1 = getRandomParentIndex(cdfArray); 
                
                Integer randIndex2 = randIndex1;
                
                while(randIndex2 == randIndex1) {
                    randIndex2 = getRandomParentIndex(cdfArray); 
                }
                
                AgentChild offspring = createOffspring(pool[randIndex1], pool[randIndex1], z);
                nextGenPool[z] = offspring;
            }
            
            pool = nextGenPool;
            generation++;
        }
    }
    
    private static Integer getRandomParentIndex(Integer[] cdfArray) {
        Integer total = cdfArray[POOL_SIZE - 1];
        
        Integer selection = random.nextInt(total);
        
        
        for(int i = 0; i < POOL_SIZE; i++) {
            if(selection < cdfArray[i]) {
                return i;
            }
        }
        
        throw new RuntimeException("Not valid cdf");
    }
    
    private static AgentChild createOffspring(AgentChild parent1, AgentChild parent2, Integer index) {
        Integer[] parent1Weights = parent1.getWeights();
        Integer[] parent2Weights = parent2.getWeights();
        
        Integer[] newWeights = Arrays.copyOf(parent1Weights, parent1Weights.length);
        
        for(int k = 0; k < 8; k++) {
            double d = random.nextDouble();
            
            if(d < 0.5) {
                newWeights[k] = parent2Weights[k];
            }
            
            double d2 = random.nextDouble();
            
            if(d2 < 0.25) {
                double d3 = random.nextDouble();
                double factor = Math.abs(random.nextGaussian()) + 1.0;
                
                if(d3 < 0.5) {
                    Double newWeight = factor * newWeights[k];
                    newWeights[k] = newWeight.intValue();
                }
                else {
                    Double newWeight = newWeights[k] / factor;
                    newWeights[k] = newWeight.intValue();
                }
            }
            
            newWeights[k] += (random.nextInt(5) - 2);
            
            if(k < 4) {
                newWeights[k] = Math.max(1, newWeights[k]);
            }
            else {
                newWeights[k] = Math.min(-1, newWeights[k]);
            }
    
        }
        
        AgentChild newAgentChild = new AgentChild(newWeights, index);
        return newAgentChild;
    }
    
    private static void compete(AgentChild[] pool) {

        for(int i = 0; i < POOL_SIZE - 1; i++) {
            for(int j = i + 1; j < POOL_SIZE; j++) {
                AgentChild p1 = pool[i];
                AgentChild p2 = pool[j];
                
                System.out.println("Weights for Agent " + i + " = " + Arrays.toString(p1.getWeights()));
                System.out.println("Weights for Agent " + j + " = " + Arrays.toString(p2.getWeights()));

                System.out.println("MATCHUP: Agent " + i + " vs. Agent " + j);
                WinCheckResult wcr1 = AppUtilities.playSimulatedGame(p1.getAgentA(), p2.getAgentB());
                applyResult(p1, p2, wcr1);
                
                System.out.println("MATCHUP: Agent " + j + " vs. Agent " + i);
                WinCheckResult wcr2 = AppUtilities.playSimulatedGame(p2.getAgentA(), p1.getAgentB());
                applyResult(p2, p1, wcr2);
            }
        }
    }
    
    private static Integer[] getCdfArray(AgentChild[] pool) {
        Integer[] cdfArray = new Integer[POOL_SIZE];
        Integer runningTotal = 0;
                
        for(int i = 0; i < POOL_SIZE; i++) {
            runningTotal += pool[i].getWins();
            cdfArray[i] = runningTotal;
        }
        
        return cdfArray;
    }
    
    private static void applyResult(AgentChild playerA, AgentChild playerB, WinCheckResult wcr) {
        if(wcr.getIsGameComplete()) {
            if(wcr.getFoundWinner()) {
                if(Player.PLAYER_A.equals(wcr.getWinningPlayer())) {
                    playerA.addWins(2);
                    
                }
                else if(Player.PLAYER_B.equals(wcr.getWinningPlayer())) {
                    playerB.addWins(2);
                }
                else {
                    throw new RuntimeException("Invalid result for winner!!");
                }
            }
            else {
                playerA.addWins(1);
                playerB.addWins(1);
            }
        }
        else {
            throw new RuntimeException("Invalid result - not complete!!");
        }
    }
    
    private static Integer[] defaultWeights() {
        return new Integer[] {2, 60, 2200, 80000, -1, -40, -2200, -10000}; 
    }
    
    private static Integer[] randomWeights() {
        Integer[] weights = new Integer[8];
        for(int k = 0; k < 8; k++) {
            weights[k] = random.nextInt(1000000) - 500000; 
        }
        return weights;
    }

}
