package com.josephaines;

import com.josephaines.sga.Data;
import com.josephaines.sga.SimpleGeneticAlgorithm;
import com.josephaines.sga.individual.Algorithm;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.internal.ChartBuilder;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.Arrays;

import static com.josephaines.sga.Data.getAverageData;


public class Main {
    public static void main(String[] args) {
        int averageOver = 10;

        // ALGORITHM 1 PARAMETERS
        int popSize1 = 50;
        int cycles1 = 100;
        int geneLength1 = 20;
        float mutationProbability1 = 0.4f;
        float mutationStep1 = 0.4f;
        float min1 = -10f;
        float max1 = 10f;


        // ALGORITHM 2 PARAMETERS
        int popSize2 = 50;
        int cycles2 = 100;
        int geneLength2 = 20;
        float mutationProbability2 = 0.4f;
        float mutationStep2 = 0.4f;
        float min2 = -5f;
        float max2 = 10f;

        // populationSize, geneLength, cycles, mutationProbability, mutationStep, min, max, Algorithm.ONE/TWO
        SimpleGeneticAlgorithm algorithm1 = new SimpleGeneticAlgorithm(popSize1, geneLength1,
                cycles1, mutationProbability1, mutationStep1, min1, max1, Algorithm.ONE);

        SimpleGeneticAlgorithm algorithm2 = new SimpleGeneticAlgorithm(popSize2, geneLength2,
                cycles2, mutationProbability2, mutationStep2, min2, max2, Algorithm.TWO);

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
        double[] gens2 = new double[cycles2];
        for (int i = 0; i < cycles2; i++) {
            gens2[i] = i;
        }

        XYChart chart1 = createGraph(avData1, gens1);
        XYChart chart2 = createGraph(avData2, gens2);

        new SwingWrapper(chart1).displayChart();
        new SwingWrapper(chart2).displayChart();
        System.out.println("Finished");
    }

    private static XYChart createGraph(Data avData, double[] gens) {
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .xAxisTitle("Generation")
                .yAxisTitle("Fitness").build();

        chart.addSeries("Minimum", gens, avData.minUtility).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Average", gens, avData.averageUtility).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Maximum", gens, avData.maxUtility).setMarker(SeriesMarkers.NONE);

        return chart;
    }


}