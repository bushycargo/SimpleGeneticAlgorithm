package com.josephaines.sga;

import com.josephaines.sga.individual.Algorithm;
import com.josephaines.sga.individual.Individual;

import java.util.Random;

public class Population {
    private Individual[] individuals;
    private final int geneLength;
    private final Random random = new Random();

    public Population(int geneLength) {
        this.geneLength = geneLength;
    }

    public Population(int geneLength, int populationSize) {
        this.individuals = new Individual[populationSize];
        this.geneLength = geneLength;
    }

    public Individual[] getIndividuals() {
        return individuals;
    }

    public void setIndividuals(Individual[] individuals) {
        this.individuals = individuals;
    }

    public int getPopulationSize(){
        return individuals.length;
    }

    public float testPopulation(){
        float utility = 0;
        for (Individual individual :
                individuals) {
            if (individual == null)
                continue;

            utility+= individual.test();
        }
        return utility;
    }

    public void generatePopulation(Algorithm algorithm, int populationSize, float geneMin, float geneMax){
        individuals = new Individual[populationSize];

        for (int i = 0; i < populationSize; i++) {
            float[] tempGenes = new float[geneLength];

            for (int j = 0; j < geneLength; j++) {
                tempGenes[j] = random.nextFloat(geneMin, geneMax);
                Individual individual = new Individual(algorithm, tempGenes);
                individuals[i] = individual;
            }
        }
    }

    public float getAverageUtility(){
        return testPopulation() / individuals.length;
    }
}
