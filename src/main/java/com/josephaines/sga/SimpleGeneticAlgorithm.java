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

    public void runAlgorithm(){
        Data data = new Data(cycles, populationSize);
        Random random = new Random();

        Population population = new Population(geneLength);
        population.generatePopulation(algorithm, populationSize, min, max);

        for (int cycle = 0; cycle < cycles; cycle++) {
            Population offspring = new Population(geneLength, populationSize);

            tournamentSelection(population, offspring);
        }
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
                offspring.setIndividual(i, new Individual(algorithm, offspringOne.getGenes().clone()));
            }
        }
    }
}
