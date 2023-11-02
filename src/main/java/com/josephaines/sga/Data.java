package com.josephaines.sga;

public class Data {
    public float[] averageUtility;
    public float[] maxUtility;
    public float[] minUtility;

    public Data(int cycles) {
        averageUtility = new float[cycles];
        maxUtility = new float[cycles];
        minUtility = new float[cycles];
    }
}
