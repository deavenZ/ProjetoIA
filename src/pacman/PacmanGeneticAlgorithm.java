package pacman;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import utils.Commons;

public class PacmanGeneticAlgorithm {
	private static final double MUTATION_RATE = 0.15;
	private static final double MUTATION_PERCENTAGE = 0.05;
	private static final double SELECTION_PERCENTAGE = 0.05;
	private static final double SELECTION_MUTATION_PERCENTAGE = 0.2;
	private static final int POPULATION_SIZE = 1000;
	private static final int NUM_GENERATIONS = 1000;
	private static List<PacmanNN> population = new ArrayList<>(POPULATION_SIZE);
	private static double bestFitness = 0;
	private static PacmanNN bestFitnessNN;
	
	public PacmanGeneticAlgorithm(){
        System.out.println("Seed: " + Commons.SEED);
		initialize();
	}
	
	private void initialize() {
		for(int i=0;i<POPULATION_SIZE;i++){
			population.add(i,new PacmanNN(Commons.PACMAN_STATE_SIZE, Commons.PACMAN_HIDDEN_DIM, Commons.PACMAN_NUM_ACTIONS));
			population.get(i).setFitness(Commons.SEED);
			checkFittest(population.get(i));
		}
		Collections.sort(population);
	}
	
	public PacmanNN search() {

        for (int i = 0; i < NUM_GENERATIONS; i++) {
			Collections.sort(population);	
			if(i%50==0) {
				System.out.println("Gen: " + i);
			}
			generateNextGeneration();
		}
		return bestFitnessNN;
	}
	
	private void generateNextGeneration() {
        int fittestParents = (int) (POPULATION_SIZE * SELECTION_PERCENTAGE);
        List<PacmanNN> parents = population.subList(0, fittestParents);
        List<PacmanNN> nextGeneration = new ArrayList<>(fittestParents);
		nextGeneration.addAll(parents);

        int numMutations=(int)(fittestParents*SELECTION_MUTATION_PERCENTAGE);
		List<PacmanNN> mutated = new ArrayList<>(numMutations);
		for(int i=0;i<numMutations;i++){
			PacmanNN chosenParent=randomParent(parents);
			PacmanNN mutateParent;
			if(!mutated.contains(chosenParent)){
				mutateParent = mutate(chosenParent);
			}else{
				chosenParent=randomParent(parents);
				mutateParent = mutate(chosenParent);
			
			}
			nextGeneration.add(mutateParent);
		}

        int numCrossovers = POPULATION_SIZE - fittestParents - numMutations;
        for(int i =0 ; i <numCrossovers ; i++) {
			PacmanNN parent1 = randomParent(parents);
			PacmanNN parent2 = randomParent(parents);
			PacmanNN[] descendants = crossover(parent1, parent2);
			nextGeneration.add(descendants[0]);
			nextGeneration.add(descendants[1]);
		}

        population = nextGeneration;
    }

    private PacmanNN randomParent(List<PacmanNN> parents){
		Random random = new Random();
		return parents.get(random.nextInt(parents.size()));
	}

    private PacmanNN mutate(PacmanNN parent) {
        double[] genes = parent.getNeuralNetwork();
        for (int i = 0; i < genes.length; i++) {
            if (Math.random() < MUTATION_RATE) {
                genes[i] += (Math.random() * 5 - 1) * MUTATION_PERCENTAGE;
            }
        }
        PacmanNN mutated = new PacmanNN(Commons.PACMAN_STATE_SIZE, Commons.PACMAN_HIDDEN_DIM, Commons.PACMAN_NUM_ACTIONS, genes);
    	 mutated.setFitness(Commons.SEED);
        checkFittest(mutated);
        return mutated;
    }
    
	private PacmanNN[] crossover(PacmanNN parent1, PacmanNN parent2) {
		Random random = new Random();
		double[] chromosome1 = parent1.getNeuralNetwork();
	 	double[] chromosome2 = parent2.getNeuralNetwork();
	 	double[] descendant1 = new double[chromosome1.length];
	 	double[] descendant2 = new double[chromosome2.length];

	 	int crossoverPoint = (random.nextInt() * chromosome1.length);

		for (int i = 0; i < chromosome1.length; i++) {
			descendant1[i] = (i < crossoverPoint) ? chromosome2[i] : chromosome1[i];
			descendant2[i] = (i < crossoverPoint) ? chromosome1[i] : chromosome2[i];
		}
		PacmanNN newIndividual1 = new PacmanNN(Commons.PACMAN_STATE_SIZE, Commons.PACMAN_HIDDEN_DIM,
				Commons.PACMAN_NUM_ACTIONS, chromosome1);
		PacmanNN newIndividual2 = new PacmanNN(Commons.PACMAN_STATE_SIZE, Commons.PACMAN_HIDDEN_DIM,
				Commons.PACMAN_NUM_ACTIONS, chromosome2);
		return new PacmanNN[] { newIndividual1, newIndividual2 };
	}

	private void checkFittest(PacmanNN p){
		if (p.getFitness()>bestFitness){
			bestFitness=p.getFitness();
			System.out.println("BestFitness: " + bestFitness);
			bestFitnessNN=p;
		}
	}
	
}