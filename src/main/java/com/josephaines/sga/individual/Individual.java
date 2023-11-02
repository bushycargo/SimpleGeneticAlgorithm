package com.josephaines.sga.individual;

public class Individual {
    protected float[] genes;
    protected Algorithm algorithm;

    public Individual(Algorithm algorithm, float[] genes) {
        this.algorithm = algorithm;
        this.genes = genes;
    }

    public Individual(Algorithm algorithm, int geneLength){
        this.algorithm = algorithm;
        this.genes = new float[geneLength];
    }

    public float[] getGenes() {
        return genes;
    }

    public void setGenes(float[] genes) {
        this.genes = genes;
    }

    public float test(){
        float utility = 0;
        switch (algorithm){
            case ONE -> {
                utility = (float) Math.pow((genes[0] - 1), 2);
                for (int i = 1; i < genes.length; i++) {
                    utility+= (float) (i*Math.pow(Math.pow(2*genes[i],2) - genes[i-1], 2));
                }
            }
            case TWO -> {
                utility = 0;
                for (float gene :
                        genes) {
                    utility+= (float) Math.pow(gene, 2);
                }

                float tempFloat = 0;
                for (int i = 0; i < genes.length; i++) {
                    tempFloat+= (0.5f * i * genes[i]);
                }
                utility+= (float) Math.pow(tempFloat, 2);

                tempFloat = 0;
                for (int i = 0; i < genes.length; i++) {
                    tempFloat+= (0.5f * i * genes[i]);
                }
                utility+= (float) Math.pow(tempFloat, 4);
            }
        }
        return utility;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }
}
