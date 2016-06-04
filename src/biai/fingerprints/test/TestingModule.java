package biai.fingerprints.test;

import biai.fingerprints.data.Data;
import org.neuroph.core.NeuralNetwork;

public class TestingModule {

    private static final int TESTING_MODE = 2;
    private static final int NUMBER_OF_ARGS = 3;

    private static String path;
    private static Integer scanWidth;
    private static Integer scanHeight;

    private static int validateArgs(String[] args) {


        int argsLength = args.length;
        if (argsLength < NUMBER_OF_ARGS) {
            System.out.println("za mało argumentów na wejściu");
            return 0;
        } else {
            path = args[0];
            scanWidth = Integer.parseInt(args[1]);
            scanHeight = Integer.parseInt(args[2]);
            System.out.println("plik: " + path + ", wymiary obrazka: " + scanWidth + " x " + scanHeight + ", liczba args: " + argsLength);

            return 1;
        }

    }

    private static final void runTest() {
        NeuralNetwork trainedNeuralNetwork = NeuralNetwork.load("fingerptints_perceptron.nnet");

        switch (TESTING_MODE) {
            case 0:
                //na pewno znany palec
                Data knownFinger0 = new Data();
                knownFinger0.readFromFile("106_2.txt", 448, 478);
                knownFinger0.normalizeData();
                trainedNeuralNetwork.setInput(knownFinger0.getData(20));
                break;
            case 1:
                //na pewno nieznany palec
                Data unknownFinger0 = new Data();
                unknownFinger0.readFromFile("109_5.txt", 448, 478);
                unknownFinger0.normalizeData();
                trainedNeuralNetwork.setInput(unknownFinger0.getData(20));
                break;
            case 2:
                //jakiś palec
                Data someFinger0 = new Data();
                someFinger0.readFromFile(path, scanWidth, scanHeight);
                someFinger0.normalizeData();
                trainedNeuralNetwork.setInput(someFinger0.getData(20));
        }
        System.out.println("Rozpoczęto obliczanie");
        trainedNeuralNetwork.calculate();
        System.out.println("Zakończono obliczanie");
        System.out.println("");
        double[] output = trainedNeuralNetwork.getOutput();
        System.out.println("Wyniki");
        for (int i = 0; i < output.length; i++) {
            System.out.println(output[i]);
            System.out.println(commentOutput(output[i]));
        }
    }

    private static final double LIMIT = 0.5;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static String commentOutput(double output) {

        if (output > LIMIT) {
            return ANSI_GREEN + "Odcisk palca należy do zdefiniowanych w sieci" + ANSI_RESET;
        } else {
            return ANSI_RED + "Odcisku palca nie ma w zdefiniowanych w sieci" + ANSI_RESET;
        }
    }

    public static void main(String[] args) {

        final int IS_VALIDATION_OK = validateArgs(args);

        if (IS_VALIDATION_OK == 1) {
            runTest();
        }
    }
}
