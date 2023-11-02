package com.josephaines;

import com.josephaines.sga.Data;
import com.josephaines.sga.SimpleGeneticAlgorithm;
import com.josephaines.sga.individual.Algorithm;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int populationSize = 50;
        int geneLength = 20;
        int cycles = 50;
        float mutationProbability = (float) 1 /populationSize;
        float mutationStep = 0.05f;
        float min = -5f;
        float max = 10f;

        SimpleGeneticAlgorithm simpleGeneticAlgorithm = new SimpleGeneticAlgorithm(populationSize, geneLength,
                cycles, mutationProbability, mutationStep, min, max, Algorithm.TWO);

        Data data = simpleGeneticAlgorithm.runAlgorithm();
        System.out.println(Arrays.toString(data.maxUtility));
        System.out.println(Arrays.toString(data.averageUtility));
        System.out.println(Arrays.toString(data.minUtility));
    }
}