package run.tests;

import gui.GBoardDisplay;

import javax.swing.*;

public class GraphicsChessBoardTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GBoardDisplay display = new GBoardDisplay();

        frame.setTitle("Chess Board");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(display);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
