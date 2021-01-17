package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {

    public static final int WIDTH  = 1000;
    public static final int HEIGHT = 1000;
    public static final int MARGIN = 50;

    private final SpaceComponent space;
    private final MenuComponent menu;

    public Display() {
        this.setTitle(GameController.getTitle());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.getContentPane().setBackground(Color.BLACK);
        this.setResizable(false);

        menu = new MenuComponent();
        space = new SpaceComponent();
        this.add(menu);

        this.pack();
        this.setVisible(true);
    }

    public void menu() {
        this.remove(space);
        this.add(menu);
        menu.requestFocus();
        this.revalidate();
    }

    public void game() {
        this.remove(menu);
        this.add(space);
        space.requestFocus();
        this.revalidate();
    }

}
