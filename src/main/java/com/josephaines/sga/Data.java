package com.josephaines.sga;

public class Data {
    public double[] averageUtility;
    public double[] maxUtility;
    public double[] minUtility;
    public float mutStep;
    public float mutProb;
    public float populationSize;

    public Data(int cycles) {
        averageUtility = new double[cycles];
        maxUtility = new double[cycles];
        minUtility = new double[cycles];
    }
    public static Data getAverageData(Data[] data, int cycles, int averageOver) {
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
        avData.mutStep = data[0].mutStep;
        avData.mutProb = data[0].mutProb;
        avData.populationSize = data[0].populationSize;
        return avData;
    }
}
