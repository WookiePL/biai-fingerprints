package biai.fingerprints.learn;

import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

public class LearingModule {

    MultiLayerPerceptron neuralNetwork;

    private Integer inputSize;
    private Integer outputSize;
    private Integer middleSize;

    private DataSet traininigSet;
    public LearingModule(Integer inputSize, Integer outputSize, Integer middleSize) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.middleSize = middleSize;
        traininigSet = new DataSet(inputSize, outputSize);

        neuralNetwork = new MultiLayerPerceptron(TransferFunctionType.TANH, inputSize, middleSize, outputSize);

        System.out.println("Utworzono sieć");
        System.out.println("ilość neuronow w warstwie wejściowej: " + inputSize);
        System.out.println("ilość neuronow w warstwie ukrytej: " + middleSize);
        System.out.println("ilość neuronow w warstwie wyjsciowej: " + outputSize);

    }

    private void createTrainingSet() {

    }
    
    
    

}
