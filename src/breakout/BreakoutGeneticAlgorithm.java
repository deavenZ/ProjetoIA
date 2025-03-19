package breakout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import utils.Commons;

public class BreakoutGeneticAlgorithm {
	
	private static final double MUTATION_RATE = 0.20;
	private static final double MUTATION_PERCENTAGE = 0.15;
	private static final double SELECTION_PERCENTAGE = 0.05;
	private static final double SELECTION_MUTATION_PERCENTAGE = 0.2;
	private static final int POPULATION_SIZE = 2000;
	private static final int NUM_GENERATIONS = 1000;
	private static List<BreakoutNN> population = new ArrayList<>(POPULATION_SIZE);
	private static double bestFitness = 0;
	private static BreakoutNN bestFitnessNN;
	
	public BreakoutGeneticAlgorithm(){
        System.out.println("Seed: " + Commons.SEED);
		initialize();
	}
	
	private void initialize() {
		for(int i=0;i<POPULATION_SIZE;i++){
			population.add(i,new BreakoutNN(Commons.BREAKOUT_STATE_SIZE, Commons.BREAKOUT_HIDDEN_DIM, Commons.BREAKOUT_NUM_ACTIONS));
			population.get(i).setFitness(Commons.SEED);
			checkFittest(population.get(i));
		}
		Collections.sort(population);
	}
	
	public BreakoutNN search() {

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
        List<BreakoutNN> parents = population.subList(0, fittestParents);
        List<BreakoutNN> nextGeneration = new ArrayList<>(POPULATION_SIZE );
        nextGeneration.addAll(parents);

        int numMutations=(int)(fittestParents*SELECTION_MUTATION_PERCENTAGE);
        List<BreakoutNN> mutated = new ArrayList<>(numMutations);

        for(int i=0;i<numMutations;i++){
            BreakoutNN chosenParent=randomParent(parents);
            mutated.add(i,chosenParent);
            BreakoutNN mutateParent=mutate(chosenParent);
            nextGeneration.add(mutateParent);
        }

        int numCrossovers = POPULATION_SIZE - fittestParents - numMutations;
        for(int i =0 ; i <numCrossovers ; i++) {
            List<BreakoutNN> descendants = crossover(randomParent(parents), randomParent(parents));
            nextGeneration.add(descendants.get(0));
            nextGeneration.add(descendants.get(1));
        }


        for (int i = fittestParents; i < Math.min(POPULATION_SIZE - 1, parents.size()); i++) {
            nextGeneration.add(parents.get(i));
        }
        population = nextGeneration;
    }

    private BreakoutNN randomParent(List<BreakoutNN> parents){
		Random random = new Random();
		return parents.get(random.nextInt(parents.size()));
	}

    private BreakoutNN mutate(BreakoutNN parent) {
        double[] genes = parent.getNeuralNetwork();
        for (int i = 0; i < genes.length; i++) {
            if (Math.random() < MUTATION_RATE) {
                genes[i] += (Math.random() * 2 - 1) * MUTATION_PERCENTAGE;
            }
        }
        BreakoutNN mutated = new BreakoutNN(Commons.BREAKOUT_STATE_SIZE, Commons.BREAKOUT_HIDDEN_DIM, Commons.BREAKOUT_NUM_ACTIONS, genes);
        mutated.setFitness(Commons.SEED);
        checkFittest(mutated);
        return mutated;
    }
    
	private List<BreakoutNN> crossover(BreakoutNN parent1, BreakoutNN parent2) {
		Random random = new Random();
		double[] chromosome1 = parent1.getNeuralNetwork();
		double[] chromosome2 = parent2.getNeuralNetwork();
		double[] descendant1 = new double[chromosome1.length];
		double[] descendant2 = new double[chromosome2.length];

		int crossoverPoint = (random.nextInt(chromosome1.length));

		for (int i = 0; i < chromosome1.length; i++) {
			descendant1[i] = (i < crossoverPoint) ? chromosome2[i] : chromosome1[i];
			descendant2[i] = (i < crossoverPoint) ? chromosome1[i] : chromosome2[i];
		}
		BreakoutNN newIndividual1 = new BreakoutNN(Commons.BREAKOUT_STATE_SIZE, Commons.BREAKOUT_HIDDEN_DIM,
				Commons.BREAKOUT_NUM_ACTIONS, chromosome1);
		BreakoutNN newIndividual2 = new BreakoutNN(Commons.BREAKOUT_STATE_SIZE, Commons.BREAKOUT_HIDDEN_DIM,
				Commons.BREAKOUT_NUM_ACTIONS, chromosome2);
		List<BreakoutNN> newGen = new ArrayList<>(2);
		newGen.add(newIndividual1);
		newGen.add(newIndividual2);
		return newGen;
	}

	private void checkFittest(BreakoutNN p){
		if (p.getFitness()>bestFitness){
			bestFitness=p.getFitness();
			System.out.println("Best Fitness: " + bestFitness);
			bestFitnessNN=p;
		}
	}
	
}