import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Render extends JPanel {
    private final int width = 800;
    private final int height = 800;
    private final int squareSize = 20;
    private final int measuredWidth = width / squareSize;
    private final int measuredHeight = height / squareSize;
    private final Kohonen kohonen;

    public Kohonen getKohonen() {
        return this.kohonen;
    }

    public Render() {
        this.kohonen = new Kohonen("src/main/resources/color.txt", measuredWidth, measuredHeight, this);
        this.init();
        this.setVisible(true);
    }

    public void init() {
        for (int i = 0; i < measuredHeight; i++) {
            for (int j = 0; j < measuredWidth; j++) {

                double red = Math.random();
                double green = Math.random();
                double blue = Math.random();

                kohonen.addNeuron(new ArrayList<>(Arrays.asList(red, green, blue)), j, i);
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < measuredHeight; i++) {
            for (int j = 0; j < measuredWidth; j++) {
                Neuron neuron = kohonen.network.getNeuron(i, j);
                ArrayList<Double> colors = neuron.getWeights();

                g.setColor(new Color((int) (colors.get(0) * 255), (int) (colors.get(1) * 255), (int) (colors.get(2) * 255)));
                g.fillRect(j * squareSize, i * squareSize, squareSize, squareSize);
            }
        }
    }
}
