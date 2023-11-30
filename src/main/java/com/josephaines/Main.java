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
        int cycles1 = 100;
        int cycles2 = 100;

        // populationSize, geneLength, cycles, mutationProbability, mutationStep, min, max, Algorithm.ONE/TWO
        SimpleGeneticAlgorithm algorithm1 = new SimpleGeneticAlgorithm(50, 20,
                cycles1, 0.3f, 0.4f, -10f, 10f, Algorithm.ONE);

        SimpleGeneticAlgorithm algorithm2 = new SimpleGeneticAlgorithm(50, 20,
                cycles2, 0.3f, 0.4f, -5f, 10f, Algorithm.TWO);

        Data data1 = algorithm1.runAlgorithm();
        Data data2 = algorithm2.runAlgorithm();

        System.out.println(Arrays.toString(data1.averageUtility));
        System.out.println(Arrays.toString(data1.minUtility));

        double[] gens1 = new double[cycles1];
        for (int i = 0; i < cycles1; i++) {
            gens1[i] = i;
        }

        XYChart chart = QuickChart.getChart("Fitness over generations.", "Fitness", "Generations",
                "Algorithm One", gens1, data1.averageUtility);
        XYChart chart2 = QuickChart.getChart("Fitness over generations.", "Fitness", "Generations",
                "Algorithm Two", gens1, data2.averageUtility);
        new SwingWrapper(chart).displayChart();
        new SwingWrapper(chart2).displayChart();
    }
}