package biai.fingerprints;


import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.Perceptron;


public class Main {

    public static void main(String[] args) {
        // create new perceptron network
        NeuralNetwork neuralNetwork = new Perceptron(2, 1);
        // create training set
        DataSet trainingSet = new DataSet(2, 1);
        // add training data to training set (logical OR function)
        trainingSet.addRow (new DataSetRow(new double[]{0, 0}, new double[]{0}));
        trainingSet.addRow (new DataSetRow (new double[]{0, 1}, new double[]{1}));
        trainingSet.addRow (new DataSetRow (new double[]{1, 0}, new double[]{1}));
        trainingSet.addRow (new DataSetRow (new double[]{1, 1}, new double[]{1}));
        // learn the training set
        neuralNetwork.learn(trainingSet);
        // save the trained network into file
        neuralNetwork.save("or_perceptron.nnet");
        // load the saved network
        NeuralNetwork trainedNeuralNetwork = NeuralNetwork.load("or_perceptron.nnet");
        // set network input
        trainedNeuralNetwork.setInput(1, 1);
        // calculate network
        trainedNeuralNetwork.calculate();
        // get network output
        double[] networkOutput = trainedNeuralNetwork.getOutput();

        System.out.println("Wyniki");
        for (int i = 0; i < networkOutput.length; i++) {
            System.out.println(networkOutput[i]);
        }
    }
}
