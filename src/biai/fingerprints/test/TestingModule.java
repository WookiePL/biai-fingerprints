package biai.fingerprints.test;

import biai.fingerprints.data.Data;
import org.neuroph.core.NeuralNetwork;

public class TestingModule {

    public static void main(String[] args) {

        NeuralNetwork trainedNeuralNetwork = NeuralNetwork.load("fingerptints_perceptron.nnet");

//        Data knownFinger0 = new Data();
//        knownFinger0.readFromFile("106_2.txt", 448, 478);
//        knownFinger0.normalizeData();
//        trainedNeuralNetwork.setInput(knownFinger0.getData(20));

        Data unknownFinger0 = new Data();
        unknownFinger0.readFromFile("106_5.txt", 448, 478);
        unknownFinger0.normalizeData();
        trainedNeuralNetwork.setInput(unknownFinger0.getData(20));

        System.out.println("Rozpoczęto obliczanie");
        trainedNeuralNetwork.calculate();
        System.out.println("Zakończono obliczanie");
        double[] output = trainedNeuralNetwork.getOutput();
        System.out.println("Wyniki");
        for (int i = 0; i < output.length; i++) {
            System.out.println(output[i]);
        }
    }
}
