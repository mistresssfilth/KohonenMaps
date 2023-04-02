import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Render render = new Render();
        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(render, "Center");
        frame.setSize(800, 800);
        frame.setVisible(true);

        render.getKohonen().som();
    }
}
