package biai.fingerprints.learn;

import biai.fingerprints.data.Data;
import org.apache.commons.io.FilenameUtils;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;
import org.apache.commons.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LearingModule {

    MultiLayerPerceptron neuralNetwork;
    private Integer inputSize;
    private Integer outputSize;
    private Integer middleSize;
    private DataSet traininigSet;

    private static String folderPath;

    public LearingModule(Integer inputSize, Integer outputSize, Integer middleSize) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.middleSize = middleSize;
        traininigSet = new DataSet(inputSize, outputSize);
        neuralNetwork = new MultiLayerPerceptron(TransferFunctionType.TANH, inputSize, middleSize, outputSize);
    }

    /**
     * określa skąd brane są pliki do uczenia
     *  0 - z folderu o nazwie test*
     *  1 - harcodowane w metodzie createTrainingSet()
     */
    private static final Integer LEARNING_SET = 1;

    public void createTrainingSet() {
        switch (LEARNING_SET) {
            case 0:
                createTrainingSetFromFolder(folderPath);
                break;
            case 1:

                Data knownFinger0 = new Data();
                knownFinger0.readFromFile("106_2.txt", 448, 478);
                knownFinger0.normalizeData();

                Data knownFinger1 = new Data();
                knownFinger1.readFromFile("106_3.txt", 448, 478);
                knownFinger1.normalizeData();

                Data knownFinger2 = new Data();
                knownFinger2.readFromFile("106_4.txt", 448, 478);
                knownFinger2.normalizeData();

                Data knownFinger3 = new Data();
                knownFinger3.readFromFile("106_5.txt", 448, 478);
                knownFinger3.normalizeData();


                Data unknownFinger0 = new Data();
                unknownFinger0.readFromFile("101_1.txt", 448, 478);
                unknownFinger0.normalizeData();

                Data unknownFinger1 = new Data();
                unknownFinger1.readFromFile("103_1.txt", 448, 478);
                unknownFinger1.normalizeData();

                Data unknownFinger2 = new Data();
                unknownFinger2.readFromFile("104_1.txt", 448, 478);
                unknownFinger2.normalizeData();

                Data unknownFinger3 = new Data();
                unknownFinger3.readFromFile("107_1.txt", 448, 478);
                unknownFinger3.normalizeData();

                Data unknownFinger4 = new Data();
                unknownFinger4.readFromFile("109_5.txt", 448, 478);
                unknownFinger4.normalizeData();

                int linesNumber = inputSize / 4;
                traininigSet.addRow(new DataSetRow(knownFinger0.getData(linesNumber), new double[]{1}));

                traininigSet.addRow(new DataSetRow(unknownFinger0.getData(linesNumber), new double[]{0}));

                traininigSet.addRow(new DataSetRow(knownFinger1.getData(linesNumber), new double[]{1}));

                traininigSet.addRow(new DataSetRow(unknownFinger1.getData(linesNumber), new double[]{0}));

                traininigSet.addRow(new DataSetRow(knownFinger2.getData(linesNumber), new double[]{1}));
                traininigSet.addRow(new DataSetRow(knownFinger3.getData(linesNumber), new double[]{1}));

                traininigSet.addRow(new DataSetRow(unknownFinger2.getData(linesNumber), new double[]{0}));
                traininigSet.addRow(new DataSetRow(unknownFinger3.getData(linesNumber), new double[]{0}));
                traininigSet.addRow(new DataSetRow(unknownFinger4.getData(linesNumber), new double[]{0}));
                break;
        }

    }

    private void createTrainingSetFromFolder(String path) {

        File dir = new File(path);
        List<File> fingerTxtFilesList = new ArrayList<>();
        if(dir.isDirectory()) {
            for(File file : dir.listFiles()) {
                if(file.isFile() && FilenameUtils.getExtension(file.getName()) == ".txt") {
                    String fingerTxtFile = file.getName();
                    fingerTxtFilesList.add(file);
                }
            }
        }

    }
    public void learnNetwork() {
        neuralNetwork.learn(traininigSet);
    }
    
    public void saveNetwork() {
        neuralNetwork.save("fingerptints_perceptron.nnet");
    }

    public static void main(String[] args) {
        //folderPath = args[0];
        //LearingModule learingModule = new LearingModule(80, 1, 61);
        LearingModule learingModule = new LearingModule(80, 1, 57);

        System.out.println("Utworzono sieć");
        System.out.println("ilość neuronow w warstwie wejściowej: " + 100);
        System.out.println("ilość neuronow w warstwie ukrytej: " + 100);
        System.out.println("ilość neuronow w warstwie wyjsciowej: " + 1);

        learingModule.createTrainingSet();

        System.out.println("Utworzono treningowy zestaw danych");
        System.out.println("Rozpoczyna sie proces uczenia");

        learingModule.learnNetwork();

        System.out.println("Prioces uczenia zakończył się");

        learingModule.saveNetwork();

        System.out.println("Zapisano sieć do pliku fingerptints_perceptron.nnet");
    }

}
