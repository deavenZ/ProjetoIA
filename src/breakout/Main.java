package breakout;

import utils.Commons;

public class Main {
    public static void main(String[] args){
        BreakoutGeneticAlgorithm gen = new BreakoutGeneticAlgorithm();
        BreakoutNN b = gen.search();	
      new Breakout(b,Commons.SEED);
    }
}