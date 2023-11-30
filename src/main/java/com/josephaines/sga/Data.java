package com.josephaines.sga;

public class Data {
    public double[] averageUtility;
    public double[] maxUtility;
    public double[] minUtility;

    public Data(int cycles) {
        averageUtility = new double[cycles];
        maxUtility = new double[cycles];
        minUtility = new double[cycles];
    }
}
