package biai.fingerprints.learn;

import biai.fingerprints.data.Data;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.StringBuilderWriter;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LearingModule {

    MultiLayerPerceptron neuralNetwork;
    private Integer inputSize;
    private Integer outputSize;
    private Integer middleSize;
    private DataSet trainingSet;

    /**
     * rzeczy dla parametrów wejściowych
     */
    private static String folderPath;
    private static Integer scanWidth;
    private static Integer scanHeight;



    public LearingModule(Integer inputSize, Integer outputSize, Integer middleSize) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.middleSize = middleSize;
        trainingSet = new DataSet(inputSize, outputSize);
        neuralNetwork = new MultiLayerPerceptron(TransferFunctionType.TANH, inputSize, middleSize, outputSize);
    }

    /**
     * określa skąd brane są pliki do uczenia
     *  0 - z folderu o nazwie test*
     *  1 - harcodowane w metodzie createTrainingSet()
     */
    private static final Integer LEARNING_SET = 0;

    public void createTrainingSet() throws IOException {
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
                trainingSet.addRow(new DataSetRow(knownFinger0.getData(linesNumber), new double[]{1}));

                trainingSet.addRow(new DataSetRow(unknownFinger0.getData(linesNumber), new double[]{0}));

                trainingSet.addRow(new DataSetRow(knownFinger1.getData(linesNumber), new double[]{1}));

                trainingSet.addRow(new DataSetRow(unknownFinger1.getData(linesNumber), new double[]{0}));

                trainingSet.addRow(new DataSetRow(knownFinger2.getData(linesNumber), new double[]{1}));
                trainingSet.addRow(new DataSetRow(knownFinger3.getData(linesNumber), new double[]{1}));

                trainingSet.addRow(new DataSetRow(unknownFinger2.getData(linesNumber), new double[]{0}));
                trainingSet.addRow(new DataSetRow(unknownFinger3.getData(linesNumber), new double[]{0}));
                trainingSet.addRow(new DataSetRow(unknownFinger4.getData(linesNumber), new double[]{0}));
                break;
        }

    }

    private void createTrainingSetFromFolder(String path) throws IOException {

        File dir = new File(path);
        List<File> fingerTxtFilesList = new ArrayList<>();
        if(dir.isDirectory()) {
            for(File file : dir.listFiles()) {
                if(file.isFile() && FilenameUtils.getExtension(file.getName()).equals("txt")) {
                    fingerTxtFilesList.add(file);
                }
            }
        }

       int isKnownFinger;
       int linesNumber = inputSize / 4;
       for (int i = 0; i >= fingerTxtFilesList.size(); i++) {

           String currentFilename = fingerTxtFilesList.get(i).getPath();
           if (currentFilename.contains(".1")) {
               //finger is known
               Data knownFinger = new Data();
               knownFinger.readFromFile(currentFilename, scanWidth, scanHeight);
               knownFinger.normalizeData();
               trainingSet.addRow(new DataSetRow(knownFinger.getData(linesNumber), new double[]{1}));
           } else if (currentFilename.contains(".0")){
               //finger is unknown
               Data unknownFinger = new Data();
               unknownFinger.readFromFile(currentFilename, scanWidth, scanHeight);
               unknownFinger.normalizeData();
               trainingSet.addRow(new DataSetRow(unknownFinger.getData(linesNumber), new double[]{0}));
           } else {
               // it is not said if finger is known/unknown
                ;
           }
       }

    }
    public void learnNetwork() {
        neuralNetwork.learn(trainingSet);
    }
    
    public void saveNetwork() {
        neuralNetwork.save("fingerptints_perceptron.nnet");
    }

    public static void main(String[] args) throws IOException {
        folderPath = args[0];
        scanWidth = Integer.parseInt(args[1]);
        scanHeight = Integer.parseInt(args[2]);

        //LearingModule learingModule = new LearingModule(80, 1, 61);
        Integer inputSize = 80;
        Integer outputSize = 1;
        Integer middleSize = 57;
        LearingModule learingModule = new LearingModule(inputSize, outputSize, middleSize);

        System.out.println("Utworzono sieć");
        System.out.println("ilość neuronow w warstwie wejściowej: " + inputSize);
        System.out.println("ilość neuronow w warstwie wyjsciowej: " + outputSize);
        System.out.println("ilość neuronow w warstwie ukrytej: " + middleSize);

        learingModule.createTrainingSet();

        System.out.println("Utworzono treningowy zestaw danych");
        System.out.println("Rozpoczyna sie proces uczenia");

        learingModule.learnNetwork();

        System.out.println("Prioces uczenia zakończył się");

        learingModule.saveNetwork();

        System.out.println("Zapisano sieć do pliku fingerptints_perceptron.nnet");
    }

}
