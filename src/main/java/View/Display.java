package View;

import Controller.Game;

import javax.swing.*;
import java.awt.*;

public class Display {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private JFrame window;

    public Display() {
        this.window = new JFrame(Game.TITLE);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel emptyLabel = new JLabel("la");
        this.window.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        this.window.pack();
        this.window.setVisible(true);
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}
