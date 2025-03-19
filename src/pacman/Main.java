package pacman;
import utils.Commons;
public class Main {
    public static void main(String[] args){
        PacmanGeneticAlgorithm gen = new PacmanGeneticAlgorithm();
        PacmanNN b = gen.search();
        new Pacman(b,true,Commons.SEED);
    }
}