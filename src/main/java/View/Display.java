package View;

import Controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

public class Display extends JFrame {

    private static final int WIDTH  = 1000;
    private static final int HEIGHT = 1000;

    private SpaceshipShape spaceshipShape;

    public Display() {
        this.setTitle(GameController.TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.getContentPane().setBackground(Color.BLACK);
        this.setResizable(false);
        this.setLayout(null);
        this.pack();

//        addKeyListener(new GameController.ControlsListener());

//        getContentPane().add(new Board());



        this.setVisible(true);

//        ImageIcon icon = new ImageIcon("../../resources/logo.png");
//        this.setIconImage(icon.getImage());

    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getCenterX() {
        return WIDTH/2;
    }

    public int getCenterY() {
        return HEIGHT / 2;
    }

    public void repaint() {
        this.getContentPane().repaint();
    }

    public void rotateSpaceship() {
        spaceshipShape.move();
    }

    private static class Board extends JPanel {

        Board() {
            this.setBackground(Color.BLACK);
            this.setBounds(0, 0, Display.WIDTH, Display.HEIGHT);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.clearRect(0, 0, WIDTH, HEIGHT);
            Color prevColor = g.getColor();

            g.setColor(Color.BLACK); // background color
            g.fillRect(0, 0, Display.WIDTH, Display.HEIGHT); // fill a rectangle with background color
            g.setColor(prevColor);

            SpaceshipShape spaceshipShape = new SpaceshipShape(g);
            spaceshipShape.move();
        }

    }

    private static class SpaceshipShape {
        private final int[] X_COORDS = {0, 25, 0, -25};
        private final int[] Y_COORDS = {-30, 30, 10, 30};

        private final float STROKE_WIDTH = 1.5f;

        private GeneralPath shape;

        private Graphics2D graphics;

        SpaceshipShape(Graphics g) {
            graphics = (Graphics2D) g;
            shape = new GeneralPath(BasicStroke.JOIN_ROUND, X_COORDS.length);
            shape.moveTo(X_COORDS[0] + WIDTH/2, Y_COORDS[0] + HEIGHT/2);
            for (int i=0; i<4; ++i) {
                shape.lineTo(X_COORDS[i] + WIDTH/2, Y_COORDS[i] + HEIGHT/2);
            }
            shape.closePath();

            repaint();
        }

        void move() {
            shape.moveTo(100,100);
            repaint();
        }

        void rotate() {

        }

        void repaint() {
            graphics.setStroke(new BasicStroke(STROKE_WIDTH));
            graphics.setColor(Color.white);
            graphics.draw(shape);
        }
    }

}
