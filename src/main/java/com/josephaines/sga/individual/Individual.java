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
                    utility = (float) (utility + (i * (((2 * (Math.pow(genes[i], 2))) - genes[i - 1]) * (2 * (Math.pow(genes[i], 2))) - genes[i - 1])));
                }
            }
            case TWO -> {
                double utility1 = 0;
                double utility2 = 0;
                for (int i = 0; i < genes.length; i++) {
                    utility1 = utility1 + Math.pow(genes[i], 2);
                    utility2 = utility2 + (0.5 * (i + 1) * genes[i]);
                }
                return (float) (utility1 + Math.pow(utility2, 2) + Math.pow(utility2, 4));
            }
            case TEST -> {
                for (float gene : genes) {
                    utility += gene;
                }
            }
        }
        return utility;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setGene(int i, float gene) {
        genes[i] = gene;
    }
}
