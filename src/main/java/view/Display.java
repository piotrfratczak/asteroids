package view;

import controller.GameController;
import view.drawable.AsteroidShape;
import view.drawable.BulletShape;
import view.drawable.SpaceshipShape;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

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
        private final Map<Integer, AsteroidShape> asteroidShapes;

        SpaceComponent() {
            spaceshipShape = new SpaceshipShape();
            bulletShape = new BulletShape();
            asteroidShapes = new HashMap<>();
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            g2.setStroke(new BasicStroke(STROKE_WIDTH));
            g2.setPaint(Color.white);

            GameController.collide();
            spaceshipShape.draw(g2);
            bulletShape.draw(g2);
            drawAsteroids(g2);
        }

        private void drawAsteroids(Graphics2D g2) {
            Set<Integer> asteroidIds = GameController.getAsteroidIds();

            List<Integer> obsoleteIds = new LinkedList<>(asteroidShapes.keySet());
            obsoleteIds.removeAll(asteroidIds);
            for (int id : obsoleteIds) {
                asteroidShapes.remove(id);
            }

            List<Integer> newIds = new LinkedList<>(asteroidIds);
            newIds.removeAll(asteroidShapes.keySet());
            for (int id : newIds) {
                asteroidShapes.put(id, new AsteroidShape(id));
            }

            for (AsteroidShape asteroidShape : asteroidShapes.values()) {
                asteroidShape.draw(g2);
            }
        }

//        private <E> void drawElement(Map<Integer, E> map, Graphics2D g2) {
//            Set<Integer> ids = GameController.getIds(map.);
//
//            List<Integer> obsoleteIds = new LinkedList<>(map.keySet());
//            obsoleteIds.removeAll(ids);
//            for (int id : obsoleteIds) {
//                map.remove(id);
//            }
//
//            List<Integer> newIds = new LinkedList<>(ids);
//            newIds.removeAll(map.keySet());
//            for (int id : newIds) {
//                map.put(id, new E(id));
//            }
//
//            for (E element : map.values()) {
//                draw(element, g2);
//            }
//        }
//
//        private void draw(AsteroidShape asteroidShape, Graphics2D g2) {
//            asteroidShape.draw(g2);
//        }
//
//        private void draw(BulletShape bulletShape, Graphics2D g2) {
//            bulletShape.draw(g2);
//        }
    }

}
