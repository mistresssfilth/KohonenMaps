import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Kohonen {
    private final int steps;
    public Network network;
    ArrayList<ArrayList<Double>> input;

    JPanel screen;
    public Kohonen(String filename, int width, int height, JPanel screen) {
        this.network = new Network(width, height);
        this.input = getListFromFile(filename);
        this.steps = 2000;
        this.screen = screen;
    }
    public void addNeuron(ArrayList<Double> weights, int i, int j) {
        network.setNeuron(i, j, new Neuron(weights, i, j));
    }

    public void som() {
        ArrayList<Double> oldVector = new ArrayList<>();
        ArrayList<Double> vector;

        for (int t = 0; t < steps; t++) {

            do {
                int randomInput = (int) (Math.random() * input.size());
                vector = input.get(randomInput);
            } while (oldVector.equals(vector));

            oldVector = new ArrayList<>(vector);

            Neuron bmu = network.getBMU(vector);

            if (steps >= bmu.getSizeWeights()) network.updateNeighbors(bmu, vector, t, steps);

            this.screen.repaint();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                System.out.println("Unexpected exception" + e);
            }
        }
    }
    public static String read(String filename) {
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (line != null) {
                text.append(line);
                text.append("\n");
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    public static ArrayList<ArrayList<Double>> getListFromFile(String filename) {
        String inputStr = read(filename);
        ArrayList<ArrayList<Double>> input = new ArrayList<>();

        String[] inputSplit = inputStr.split("\n");
        String[] vectors;
        String line;
        int index = 0;

        for (String str : inputSplit) {
            line = str;

            input.add(new ArrayList<>());
            vectors = line.split("\t");

            for (int i = 1; i < vectors.length; i++) {
                input.get(index).add(Double.parseDouble(vectors[i]));
            }
            index++;
        }

        return input;
    }
}
