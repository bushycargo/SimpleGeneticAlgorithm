package com.josephaines;

import com.josephaines.sga.Data;
import com.josephaines.sga.SimpleGeneticAlgorithm;
import com.josephaines.sga.individual.Algorithm;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int populationSize = 10;
        int geneLength = 10;
        int cycles = 10;
        float mutationProbability = (float) 1 /populationSize;
        float mutationStep = 0.05f;
        float min = -5;
        float max = 5;

        SimpleGeneticAlgorithm simpleGeneticAlgorithm = new SimpleGeneticAlgorithm(populationSize, geneLength,
                cycles, mutationProbability, mutationStep, min, max, Algorithm.ONE);

        Data data = simpleGeneticAlgorithm.runAlgorithm();
        System.out.println(Arrays.toString(data.averageUtility));
    }
}