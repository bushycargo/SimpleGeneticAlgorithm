package com.josephaines.sga;

import com.josephaines.sga.individual.Algorithm;
import com.josephaines.sga.individual.Individual;

import java.util.Random;

public class SimpleGeneticAlgorithm {
    int populationSize, geneLength, cycles;
    float mutationProbability, mutationStep, min, max;
    Algorithm algorithm;

    public SimpleGeneticAlgorithm(int populationSize, int geneLength, int cycles, float mutationProbability,
                                  float mutationStep, float min, float max, Algorithm algorithm) {
        this.populationSize = populationSize;
        this.geneLength = geneLength;
        this.cycles = cycles;
        this.mutationProbability = mutationProbability;
        this.mutationStep = mutationStep;
        this.min = min;
        this.max = max;
        this.algorithm = algorithm;
    }

    public Data runAlgorithm(){
        Data data = new Data(cycles);
        Random random = new Random();

        Population population = new Population(geneLength);
        population.generatePopulation(algorithm, populationSize, min, max);

        for (int cycle = 0; cycle < cycles; cycle++) {
            Population offspring = new Population(geneLength, populationSize);

            tournamentSelection(population, offspring);
            crossover(offspring);
            floatMutation(offspring);

            population.setIndividuals(offspring.getIndividuals().clone());
            data.averageUtility[cycle] = population.getAverageUtility();
            data.maxUtility[cycle] = population.getMaxUtility();
            data.minUtility[cycle] = population.getMinUtility();
        }

        return data;
    }

    private void tournamentSelection(Population population, Population offspring){
        Random random = new Random();
        for (int i = 0; i < populationSize; i++) {
            Individual offspringOne = population.getIndividuals()[random.nextInt(populationSize)];
            Individual offspringTwo = population.getIndividuals()[random.nextInt(populationSize)];

//            Less than (<) as minimising
            if (offspringOne.test() < offspringTwo.test()){
                offspring.setIndividual(i, new Individual(algorithm, offspringOne.getGenes().clone()));
            }else {
                offspring.setIndividual(i, new Individual(algorithm, offspringTwo.getGenes().clone()));
            }
        }
    }

    private void crossover(Population offspring){
        Random random = new Random();

        for (int i = 0; i < offspring.getPopulationSize(); i = i + 2) {
            float[] individualOne = offspring.getIndividuals()[i].getGenes().clone();
            float[] individualTwo = offspring.getIndividuals()[i + 1].getGenes().clone();
            float[] individualThree = offspring.getIndividuals()[i].getGenes().clone();

            int crossover = random.nextInt(geneLength);
            for (int j = crossover; j < geneLength; j++) {
                individualOne[j] = individualTwo[j];
                individualTwo[j] = individualThree[j];
            }
            offspring.setIndividual(i, new Individual(algorithm, individualOne));
            offspring.setIndividual(i + 1, new Individual(algorithm, individualTwo));
        }
    }
}
