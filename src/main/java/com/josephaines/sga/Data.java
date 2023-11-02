package com.josephaines.sga;

public class Data {
    public float[] averageUtility;
    public float[] maxUtility;
    public float[] minUtility;
    public float[][] allUtility;

    public Data(int cycles, int populationSize) {
        averageUtility = new float[cycles];
        maxUtility = new float[cycles];
        minUtility = new float[cycles];
        allUtility = new float[cycles][populationSize];
    }
}
