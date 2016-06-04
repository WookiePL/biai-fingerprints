package biai.fingerprints;


import biai.fingerprints.data.Data;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.Perceptron;
import org.neuroph.util.TransferFunctionType;


public class Main {

    public static void main(String[] args) {
//        // create new perceptron network
//        int size= 50;
//        int size2 = 2 * size;
//
//        //NeuralNetwork neuralNetwork = new Perceptron(2, 1);
//        System.out.println("start");
//        MultiLayerPerceptron myMlPerceptron = new MultiLayerPerceptron(TransferFunctionType.TANH, size, size2, 1);
//        // create training set
//        System.out.println("stworzona siec");
//
//        DataSet trainingSet = new DataSet(size, 1);
//        double[] input0 = new double[size];
//        double[] input1 = new double[size];
//        double[] input2 = new double[size];
//
//        for (int i = 0; i < size; i++) {
//            input0[i] = 0;
//            input1[i] = 1;
//            input2[i] = 0.5;
//        }
//        System.out.println("wygenerowal dane");
//
//        // add training data to <></>raining set (logical OR function)
//        trainingSet.addRow (new DataSetRow(input0, new double[]{0}));
//        trainingSet.addRow (new DataSetRow (input1, new double[]{1}));
//        trainingSet.addRow (new DataSetRow (input2, new double[]{0.4}));
//        // learn the training set
//        myMlPerceptron.learn(trainingSet);
//        // save the trained network into file
//        System.out.println("pouczone");
//
//        //myMlPerceptron.save("or_perceptron.nnet");
//        // load the saved network
////        NeuralNetwork trainedNeuralNetwork = NeuralNetwork.load("or_perceptron.nnet");
//        // set network input
//        myMlPerceptron.setInput(input2);
//        // calculate network
//        myMlPerceptron.calculate();
//        // get network output
//        System.out.println("testuje");
//
//        double[] networkOutput = myMlPerceptron.getOutput();
//
//        System.out.println("Wyniki");
//        for (int i = 0; i < networkOutput.length; i++) {
//            System.out.println(networkOutput[i]);
//        }

        Data data = new Data();
        data.readFromFile("X:\\CO SE STUDIUJE\\sem6\\BIAI\\materialyProjekt\\odciski\\106_2.txt", 448, 478);
        data.normalizeData();
        data.printNormalizedData();
    }
}
