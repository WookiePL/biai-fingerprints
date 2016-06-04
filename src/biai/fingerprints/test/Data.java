package biai.fingerprints.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
            normalizedLine.setY2(normalize(line.getY1(), scanHeight));
            normalizedData.add(normalizedLine);
        }
    }
}
