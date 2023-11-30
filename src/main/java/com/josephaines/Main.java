package com.josephaines;

import com.josephaines.sga.Data;
import com.josephaines.sga.SimpleGeneticAlgorithm;
import com.josephaines.sga.individual.Algorithm;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int populationSize = 50;
        int geneLength = 20;
        int cycles = 100;
        float mutationProbability = 0.3f;
        float mutationStep = 0.4f;
        float min = -10f;
        float max = 10f;

        SimpleGeneticAlgorithm simpleGeneticAlgorithm = new SimpleGeneticAlgorithm(populationSize, geneLength,
                cycles, mutationProbability, mutationStep, min, max, Algorithm.TWO);


        Data data = simpleGeneticAlgorithm.runAlgorithm();
        System.out.println(Arrays.toString(data.averageUtility));
        System.out.println(Arrays.toString(data.minUtility));

        double[] gens = new double[cycles];
        for (int i = 0; i < cycles; i++) {
            gens[i] = i;
        }

        XYChart chart = QuickChart.getChart("Fitness over generations.", "Fitness", "Generations",
                "Algorithm One", gens, data.averageUtility);
        new SwingWrapper(chart).displayChart();
    }
}