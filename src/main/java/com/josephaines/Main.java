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
        int averageOver = 10;

        int cycles1 = 100;
        int cycles2 = 100;

        // populationSize, geneLength, cycles, mutationProbability, mutationStep, min, max, Algorithm.ONE/TWO
        SimpleGeneticAlgorithm algorithm1 = new SimpleGeneticAlgorithm(50, 20,
                cycles1, 0.3f, 0.4f, -10f, 10f, Algorithm.ONE);

        SimpleGeneticAlgorithm algorithm2 = new SimpleGeneticAlgorithm(50, 20,
                cycles2, 0.3f, 0.4f, -5f, 10f, Algorithm.TWO);

        Data[] data1 = new Data[averageOver];
        Data[] data2 = new Data[averageOver];
        for (int i = 0; i < averageOver; i++) {
            data1[i] = algorithm1.runAlgorithm();
            data2[i] = algorithm2.runAlgorithm();
        }

        Data avData1 = getAverageData(data1, cycles1, averageOver);
        Data avData2 = getAverageData(data2, cycles2, averageOver);


        double[] gens1 = new double[cycles1];
        for (int i = 0; i < cycles1; i++) {
            gens1[i] = i;
        }

        XYChart chart = QuickChart.getChart("Fitness over generations.", "Fitness", "Generations",
                "Algorithm One", gens1,avData1.averageUtility);
        XYChart chart2 = QuickChart.getChart("Fitness over generations.", "Fitness", "Generations",
                "Algorithm Two", gens1,avData2.averageUtility);
        new SwingWrapper(chart).displayChart();
        new SwingWrapper(chart2).displayChart();
    }

    private static Data getAverageData(Data[] data, int cycles, int averageOver) {
        Data avData = new Data(cycles);
        for (int run = 0; run < averageOver; run++) {
            for (int generation = 0; generation < cycles; generation++) {
                avData.averageUtility[generation] += data[run].averageUtility[generation];
                avData.minUtility[generation] += data[run].minUtility[generation];
                avData.maxUtility[generation] += data[run].maxUtility[generation];
            }
        }
        for (int generation = 0; generation < cycles; generation++) {
            avData.averageUtility[generation] = avData.averageUtility[generation] / averageOver;
            avData.minUtility[generation] = avData.minUtility[generation] / averageOver;
            avData.maxUtility[generation] = avData.maxUtility[generation] / averageOver;
        }
        return avData;
    }
}