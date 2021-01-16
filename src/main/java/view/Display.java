package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Display extends JFrame {

    public static final int WIDTH  = 1000;
    public static final int HEIGHT = 1000;
    public static final int MARGIN = 50;

    private final SpaceComponent spaceComponent;

    public Display() {
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
        Action thrustAction = new ThrustAction();
        Action stopThrustAction = new StopThrustAction();
        Action leftAction = new LeftAction();
        Action stopLeftAction = new StopLeftAction();
        Action rightAction = new RightAction();
        Action stopRightAction = new StopRightAction();
        Action shootAction = new ShootAction();
        Action stopShootAction = new StopShootAction();
        Action teleportAction = new TeleportAction();


        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "thrustAction");
        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "thrustAction");
        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "stopThrustAction");
        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "stopThrustAction");

        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "leftAction");
        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "leftAction");
        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "stopLeftAction");
        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "stopLeftAction");

        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "rightAction");
        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "rightAction");
        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "stopRightAction");
        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "stopRightAction");

        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "shootAction");
        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "stopShootAction");

        spaceComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, InputEvent.SHIFT_DOWN_MASK), "teleportAction");


        spaceComponent.getActionMap().put("thrustAction", thrustAction);
        spaceComponent.getActionMap().put("stopThrustAction", stopThrustAction);

        spaceComponent.getActionMap().put("leftAction", leftAction);
        spaceComponent.getActionMap().put("stopLeftAction", stopLeftAction);

        spaceComponent.getActionMap().put("rightAction", rightAction);
        spaceComponent.getActionMap().put("stopRightAction", stopRightAction);

        spaceComponent.getActionMap().put("shootAction", shootAction);
        spaceComponent.getActionMap().put("stopShootAction", stopShootAction);

        spaceComponent.getActionMap().put("teleportAction", teleportAction);
    }

    public static class ThrustAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.boostSpaceship();
        }
    }

    public static class StopThrustAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.stopBoostingSpaceship();
        }
    }

    public static class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.startRotatingSpaceshipLeft();
        }
    }

    public static class StopLeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.stopRotatingSpaceshipLeft();
        }
    }

    public static class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.startRotatingSpaceshipRight();
        }
    }

    public static class StopRightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.stopRotatingSpaceshipRight();
        }
    }

    public static class ShootAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.startShooting();
        }
    }

    public static class StopShootAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.stopShooting();
        }
    }

    public static class TeleportAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameController.teleportSpaceship();
        }
    }

}
