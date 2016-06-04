package biai.fingerprints.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Data {

    private List<Line> rawData;
    private List<Line> normalizedData;
    private Integer scanWidth;
    private Integer scanHeight;

    public Data() {
        rawData = new ArrayList<Line>();
        normalizedData = new ArrayList<Line>();
    }

    public boolean readFromFile(String path, Integer scanWidth, Integer scanHeight) {
        this.scanWidth = scanWidth;
        this.scanHeight = scanHeight;
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            readRawDataFromLines(lines);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void readRawDataFromLines(List<String> lines) {
        for (String line : lines) {
            String[] words = line.split(" ");
            Line imageLine = new Line();
            imageLine.setX1(Double.parseDouble(words[0]));
            imageLine.setY1(Double.parseDouble(words[1]));
            imageLine.setX2(Double.parseDouble(words[2]));
            imageLine.setY2(Double.parseDouble(words[3]));
            rawData.add(imageLine);
        }
    }

    private double normalize(double number, double scale) {
        return number / scale;
    }

    public void normalizeData() {
        for (Line line : rawData) {
            Line normalizedLine = new Line();
            normalizedLine.setX1(normalize(line.getX1(), scanWidth));
            normalizedLine.setY1(normalize(line.getY1(), scanHeight));
            normalizedLine.setX2(normalize(line.getX2(), scanWidth));
            normalizedLine.setY2(normalize(line.getY2(), scanHeight));
            normalizedData.add(normalizedLine);
        }
    }

    public void printNormalizedData() {
        for (Line line : normalizedData) {
            System.out.println(line.getX1() + " " + line.getY1() + " " + line.getX2() + " " + line.getY2());
        }
    }

    public double[] getData(int count) {
        double[] array = new double[count * 4];
        for (int i = 0; i < count; i += 4) {
            array[i] = normalizedData.get(i).getX1();
            array[i + 1] = normalizedData.get(i).getY1();
            array[i + 2] = normalizedData.get(i).getX2();
            array[i + 3] = normalizedData.get(i).getY2();
        }
        return array;
    }
}
