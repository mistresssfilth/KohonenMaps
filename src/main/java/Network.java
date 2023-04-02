import java.util.ArrayList;

public class Network {
    private final Neuron[][] network;
    private final int width;
    private final int height;
    public Neuron getNeuron(int i, int j) {
        return network[i][j];
    }

    public void setNeuron(int i, int j, Neuron value) {
        network[i][j] = value;
    }

    public Network(int width, int height) {
        this.width = width;
        this.height = height;
        this.network = new Neuron[width][height];
    }

    public double distance(ArrayList<Double> v1, ArrayList<Double> v2) {
        double distance = 0.0;
        for (int i = 0; i < v1.size(); i++)
            distance += Math.pow(v1.get(i) - v2.get(i), 2);

        return Math.sqrt(distance);
    }

    public Neuron getBMU(ArrayList<Double> input) {
        Neuron BMU = null;
        double distanceMin = Double.MAX_VALUE;

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {

                double distance = distance(input, getNeuron(i, j).getWeights());

                if (distance < distanceMin) {
                    distanceMin = distance;
                    BMU = getNeuron(i, j);
                }
            }
        }
        return BMU;
    }

    public void updateNeighbors(Neuron BMU, ArrayList<Double> input, int currentIteration, int steps) {
        double radiusMap = width / 2;
        double lambda = steps / radiusMap;

        double radius = radiusMap * Math.exp(-currentIteration / lambda);

        int firstMin = (BMU.getY() - radius) < 0 ? 0 : (int) (BMU.getY() - radius);
        int firstMax = (BMU.getY() + radius) > width ? width : (int) (BMU.getY() + radius);
        int secondMin = (BMU.getX() - radius) < 0 ? 0 : (int) (BMU.getX() - radius);
        int secondMax = (BMU.getX() + radius) > width ? width : (int) (BMU.getX() + radius);

        for (int i = firstMin; i < firstMax; i++) {
            for (int j = secondMin; j < secondMax; j++) {
                Neuron neuron = getNeuron(i, j);

                double lr = 1;

                double sigm = lr * Math.exp(-currentIteration / lambda);

                double sumDistance = distance(BMU.getWeights(), neuron.getWeights());

                double theta = Math.exp(-1 * (sumDistance / (2 * Math.pow(radius, 2))));

                for (int k = 0; k < BMU.getSizeWeights(); k++) {

                    double value = neuron.getWeight(k) + theta * sigm * (input.get(k) - neuron.getWeight(k));
                    neuron.setWeight(k, value);
                }
            }
        }
    }
}
