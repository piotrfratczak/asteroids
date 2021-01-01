package view;

import controller.GameController;
import view.drawable.AsteroidShape;
import view.drawable.BulletShape;
import view.drawable.SpaceshipShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Display extends JFrame implements ActionListener {

    public static final int WIDTH  = 1000;
    public static final int HEIGHT = 1000;

    private final SpaceComponent spaceComponent;

    private final Timer timer = new Timer(3, this);

    public Display() {
        timer.start();

        this.setTitle(GameController.getTitle());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.getContentPane().setBackground(Color.BLACK);
        this.setResizable(false);

        spaceComponent = new SpaceComponent();
        bindShipActions();
        this.add(spaceComponent);

        this.pack();
        this.setVisible(true);
    }

    private void bindShipActions() {
        Action upAction = new UpAction();
        Action leftAction = new LeftAction();
        Action rightAction = new RightAction();
        Action shootAction = new ShootAction();
        Action teleportAction = new TeleportAction();

        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "upAction");
        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "leftAction");
        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "rightAction");
        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "shootAction");
        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, InputEvent.SHIFT_DOWN_MASK), "teleportAction");

        spaceComponent.getActionMap().put("upAction", upAction);
        spaceComponent.getActionMap().put("leftAction", leftAction);
        spaceComponent.getActionMap().put("rightAction", rightAction);
        spaceComponent.getActionMap().put("shootAction", shootAction);
        spaceComponent.getActionMap().put("teleportAction", teleportAction);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            this.repaint();
        }
    }

    public static class UpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.boostSpaceship();
        }
    }

    public static class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.rotateSpaceshipLeft();
        }
    }

    public static class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.rotateSpaceshipRight();
        }
    }

    public static class ShootAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.shootSpaceship();
        }
    }

    public static class TeleportAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.teleportSpaceship();
        }
    }

    private static class SpaceComponent extends JComponent {

        private final float STROKE_WIDTH = 1.5f;

        private final SpaceshipShape spaceshipShape;
        private final BulletShape bulletShape;
        private final AsteroidShape asteroidShape;


        SpaceComponent() {
            spaceshipShape = new SpaceshipShape();
            bulletShape = new BulletShape();
            asteroidShape = new AsteroidShape();
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            g2.setStroke(new BasicStroke(STROKE_WIDTH));
            g2.setPaint(Color.white);

            spaceshipShape.draw(g2);
            bulletShape.draw(g2);
            asteroidShape.draw(g2);
        }
    }

}
