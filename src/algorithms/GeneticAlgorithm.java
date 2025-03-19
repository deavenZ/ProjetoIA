package algorithms;

import java.util.ArrayList;
import java.util.List;

import breakout.BreakoutBoard;
import utils.Commons;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm {

    private static final int POPULATION_SIZE = 100;
    private static final double MUTATION_RATE = 0.1;
    private static final int NUM_GENERATIONS = 100;
    private static double fitness;
    public  List<FeedforwardNeuralNetwork> evolvePopulation(FeedforwardNeuralNetwork initialNetwork, int seed)  {
        List<FeedforwardNeuralNetwork> population = generateInitialPopulation();

        for (int generation = 0; generation < NUM_GENERATIONS; generation++) {
            // Evaluate fitness of each individual
            for (FeedforwardNeuralNetwork individual : population) {
                double fitness = evaluateFitness(individual);
                individual.setFitness(fitness);
            }

            // Create offspring through crossover        }

        // After the loop, select the best individual from the final population
        FeedforwardNeuralNetwork bestIndividual = selectBestIndividual(population);
        System.out.println("Best individual fitness: " + bestIndividual.getFitness());
    }
		return population;
    }   

    private static List<FeedforwardNeuralNetwork> generateInitialPopulation() {
        List<FeedforwardNeuralNetwork> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            FeedforwardNeuralNetwork individual = new FeedforwardNeuralNetwork(Commons.BREAKOUT_STATE_SIZE, 50, 1);
            population.add(individual);
        }
        return population; // Ensure that the population list is returned
    }


    public static double evaluateFitness(FeedforwardNeuralNetwork individual) {
    	BreakoutBoard b = new BreakoutBoard(individual, false, 123);
    	b.setSeed(12);
    	b.runSimulation();
    	fitness = b.getFitness();  
    	return fitness;
    }

    private static List<FeedforwardNeuralNetwork> selection(List<FeedforwardNeuralNetwork> population) {
        // Implement selection method (e.g., tournament selection, roulette wheel selection)
        return null; // Placeholder
    }

    private static List<FeedforwardNeuralNetwork> crossover(List<FeedforwardNeuralNetwork> parents) {
        // Implement crossover method
        return null; // Placeholder
    }

    private static void mutate(List<FeedforwardNeuralNetwork> offspring) {
        // Implement mutation method
    }

    public static FeedforwardNeuralNetwork selectBestIndividual(List<FeedforwardNeuralNetwork> population) {
        // Select the individual with the highest fitness score
        FeedforwardNeuralNetwork bestIndividual = population.get(0);
        for (FeedforwardNeuralNetwork individual : population) {
            if (individual.getFitness() > bestIndividual.getFitness()) {
                bestIndividual = individual;
            }
        }
        return bestIndividual;
    }
}
