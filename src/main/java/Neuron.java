import java.util.ArrayList;

public class Neuron {
    private final ArrayList<Double> weights;

    private final int x;
    private final int y;

    public Neuron(ArrayList<Double> w, int x, int y) {
        this.weights = w;
        this.x = x;
        this.y = y;
    }

    public ArrayList<Double> getWeights() {
        return weights;
    }

    public Double getWeight(int i) {
        return this.weights.get(i);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setWeight(int i, double value) {
        this.weights.set(i, value);
    }

    public int getSizeWeights() {
        return this.weights.size();
    }
}
