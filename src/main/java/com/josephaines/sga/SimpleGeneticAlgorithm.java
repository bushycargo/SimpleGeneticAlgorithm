package com.josephaines.sga;

import com.josephaines.sga.individual.Algorithm;

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

    }
}
