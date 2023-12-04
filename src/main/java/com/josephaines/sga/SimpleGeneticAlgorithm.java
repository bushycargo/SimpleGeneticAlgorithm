package com.josephaines.sga;

import com.josephaines.sga.individual.Algorithm;
import com.josephaines.sga.individual.Individual;

import java.util.Random;

public class SimpleGeneticAlgorithm {
    int populationSize, geneLength, cycles;
    float mutationProbability, mutationStep, min, max;
    Algorithm algorithm;

//    For set parameters
    public SimpleGeneticAlgorithm(int populationSize, int geneLength, int cycles, float mutationProbability,
                                  float mutationStep, float min, float max, Algorithm algorithm) {
        this.geneLength = geneLength;
        this.min = min;
        this.max = max;
        this.algorithm = algorithm;
        this.cycles = cycles;

        this.populationSize = populationSize;
        this.mutationProbability = mutationProbability;
        this.mutationStep = mutationStep;
    }

//    For finding parameters
    public SimpleGeneticAlgorithm(int geneLength, float min, float max, Algorithm algorithm, int cycles){
        this.geneLength = geneLength;
        this.min = min;
        this.max = max;
        this.algorithm = algorithm;
        this.cycles = cycles;
    }

    public Data findBestParameters(float stepper, float mutationStepMin, float mutationStepMax,
                                      int populationMax, int populationMin, int avOver){
        mutationStep = mutationStepMin;
        mutationProbability = 0;
        populationSize = (populationMax + populationMin) / 2;

        Population population = new Population(geneLength);
        population.generatePopulation(algorithm, populationSize, min, max);

        System.out.println("Finding best Mutation Step and Mutation Chance combination");

        Data[][] dataPoints = new Data[(int) (mutationStepMax/ stepper)][(int) (1 / 0.01f) + 1];

        int currentRun = 1;
//        Run for the number of steps required.
        for (int step = 0; step < (mutationStepMax / stepper); step++) {
            mutationStep += stepper;
            mutationProbability = 0;

//            Test variations of probability on the step value increasing in 1%
            for (int probStep = 0; probStep < (1/0.01f); probStep++) {
                mutationProbability += 0.01f;

//                Average out over 10 runs
                Data[] cycleData = new Data[avOver];
                for (int run = 0; run < avOver; run++) {
                    cycleData[run] = run(population);
                    System.out.println("Running: " + (currentRun / ((1/0.01f) * (mutationStepMax / stepper) * avOver)) * 100 + "%");
                    currentRun++;
                }
                dataPoints[step][probStep] = Data.getAverageData(cycleData, cycles, avOver);
            }

        }

        Data bestData = null;
        for (int i = 0; i < (mutationStepMax/ stepper); i++) {
            for (int j = 0; j < 100; j++) {
                if (bestData == null){
                    bestData = dataPoints[i][j];
                    continue;
                }
                if (dataPoints[i][j].averageUtility[dataPoints[i][j].averageUtility.length-1] <
                        bestData.averageUtility[bestData.averageUtility.length-1]){
                    if (dataPoints[i][j].averageUtility[dataPoints[i][j].averageUtility.length-1] < 0){
                        continue;
                    }
                    bestData = dataPoints[i][j];
                }
//                System.out.println("Current best data: \nMutation Prob: " + bestData.mutProb + "\nMutation Step:" + bestData.mutStep
//                + "\nAverage: " +bestData.averageUtility[bestData.averageUtility.length-1]);
            }
        }

        assert bestData != null;
        System.out.println("\nBest data: \nMutation Prob: " + bestData.mutProb + "\nMutation Step:" + bestData.mutStep
                + "\nBest Average Fitness: " +bestData.averageUtility[bestData.averageUtility.length-1]);

//        System.out.println("Finding best population size");
//        mutationStep = bestData.mutStep;
//        mutationProbability = bestData.mutProb;
//        populationSize = populationMin;

//        EVERYTHING ABOVE THIS LINE WORKS. POPULATION TESTING IS DEAD STILL
//        Data[] popDataPoints = new Data[populationMax / 5];
//        for (int step = 0; step < populationMax / 5; step++) {
//            populationSize += 5;
//            population = new Population(geneLength);
//            population.generatePopulation(algorithm, populationSize, min, max);
//
//            popDataPoints[step] = run(population);
//        }
//
//        bestData = null;
//        for (Data data:
//             popDataPoints) {
//            if (bestData == null){
//                bestData = data;
//                continue;
//            }
//            if (bestData.averageUtility[bestData.averageUtility.length-1] < data.averageUtility[data.averageUtility.length-1]){
//                bestData = data;
//            }
//            System.out.println("Current best pop size: " + bestData.populationSize);
//        }
//        assert bestData != null;
//        System.out.println("Best pop size: " + bestData.populationSize + "Average: " + bestData.averageUtility[bestData.averageUtility.length-1]);


        return bestData;
    }
    public Data runAlgorithm(){
        Population population = new Population(geneLength);
        population.generatePopulation(algorithm, populationSize, min, max);

        return run(population);
    }

    private Data run(Population population) {
        Data data = new Data(cycles);
        for (int cycle = 0; cycle < cycles; cycle++) {
            Population offspring = new Population(geneLength, populationSize);

            tournamentSelection(population, offspring);
            crossover(offspring);
            floatMutation(offspring);

            population.setIndividuals(offspring.getIndividuals().clone());
            data.averageUtility[cycle] = population.getAverageUtility();
            data.maxUtility[cycle] = population.getMaxUtility();
            data.minUtility[cycle] = population.getMinUtility();
        }
        data.mutProb = mutationProbability;
        data.mutStep = mutationStep;
        data.populationSize = populationSize;
        return data;
    }

    private void tournamentSelection(Population population, Population offspring){
        Random random = new Random();
        for (int i = 0; i < populationSize; i++) {
            Individual offspringOne = population.getIndividuals()[random.nextInt(populationSize)];
            Individual offspringTwo = population.getIndividuals()[random.nextInt(populationSize)];

//            Less than (<) as minimising
            if (offspringOne.test() < offspringTwo.test()){
                offspring.setIndividual(i, new Individual(algorithm, offspringOne.getGenes().clone()));
            }else {
                offspring.setIndividual(i, new Individual(algorithm, offspringTwo.getGenes().clone()));
            }
        }
    }

    private void crossover(Population offspring){
        Random random = new Random();

        for (int i = 0; i < offspring.getPopulationSize(); i = i + 2) {
            float[] individualOne = offspring.getIndividuals()[i].getGenes().clone();
            float[] individualTwo = offspring.getIndividuals()[i + 1].getGenes().clone();
            float[] individualThree = offspring.getIndividuals()[i].getGenes().clone();

            int crossover = random.nextInt(geneLength);
            for (int j = crossover; j < geneLength; j++) {
                individualOne[j] = individualTwo[j];
                individualTwo[j] = individualThree[j];
            }
            offspring.setIndividual(i, new Individual(algorithm, individualOne));
            offspring.setIndividual(i + 1, new Individual(algorithm, individualTwo));
        }
    }

    private void floatMutation(Population offspring) {
        Random random = new Random();
        for (Individual individual :
                offspring.getIndividuals()) {
            Individual newIndividual = new Individual(algorithm, geneLength);
            for (int i = 0; i < geneLength; i++) {
                float gene = individual.getGenes().clone()[i];
                float probability = random.nextFloat();
                if (probability < mutationProbability) {
                    float alter = random.nextFloat(-mutationStep, mutationStep);
                    gene = gene + alter;
                    if (gene > max) {
                        gene = max;
                    }
                    if (gene < min){
                        gene = min;
                    }
                    newIndividual.setGene(i, gene);
                } else {
                    newIndividual.setGene(i, gene);
                }
            }
            individual.setGenes(newIndividual.getGenes().clone());
        }
    }
}
