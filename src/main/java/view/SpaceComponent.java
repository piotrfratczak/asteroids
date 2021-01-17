package view;

import controller.GameController;
import view.drawable.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SpaceComponent extends AbstractComponent {

    private final SpaceshipShape spaceshipShape;
    private final BulletShape bulletShape;
    private final Map<Integer, AsteroidShape> asteroidShapes;
    private final UFOShape ufoShape;
    private final StarShape starShape;

    SpaceComponent() {
        super();
        spaceshipShape = new SpaceshipShape();
        bulletShape = new BulletShape();
        asteroidShapes = new HashMap<>();
        ufoShape = new UFOShape();
        starShape = new StarShape();
        bindActions();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(STROKE_WIDTH));
        g2.setPaint(Color.white);

        if (GameController.isGameOver()) {
            g2.setFont(digitalFont.deriveFont(Font.BOLD,60));
            String text = "GAME OVER";
            int offset = g2.getFontMetrics().stringWidth(text)/2;
            g2.drawString(text, Display.WIDTH / 2 - offset, Display.HEIGHT / 2);
            g2.setFont(digitalFont);
            String score = "FINAL SCORE: " + GameController.getScore();
            int scoreOffset = g2.getFontMetrics().stringWidth(score)/2;
            g2.drawString(score, Display.WIDTH / 2 - scoreOffset, Display.HEIGHT / 2 + 2 * Display.MARGIN);
            return;
        }

        drawText(g2);

        spaceshipShape.draw(g2);
        ufoShape.draw(g2);
        starShape.draw(g2);
        bulletShape.draw(g2);
        drawAsteroids(g2);
    }

    private void drawAsteroids(Graphics2D g2) {
        Set<Integer> asteroidIds = GameController.getAsteroidIds();

        java.util.List<Integer> obsoleteIds = new LinkedList<>(asteroidShapes.keySet());
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

    private void drawText(Graphics2D g2) {
        int lives = GameController.getLives();
        int level = GameController.getLevel();
        int score = GameController.getScore();
        g2.setFont(digitalFont);
        g2.drawString("SCORE: " + score, Display.MARGIN, Display.MARGIN);
        g2.drawString("LIVES: " + lives, Display.MARGIN, (float) (1.5 * Display.MARGIN));
        String levelStr = "LEVEL: " + level;
        int offset = g2.getFontMetrics().stringWidth(levelStr)/2;
        g2.drawString(levelStr, Display.WIDTH / 2 - offset, Display.MARGIN);
    }

    private void bindActions() {
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "thrustAction");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "thrustAction");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "stopThrustAction");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "stopThrustAction");

        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "leftAction");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "leftAction");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "stopLeftAction");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "stopLeftAction");

        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "rightAction");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "rightAction");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "stopRightAction");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "stopRightAction");

        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "shootAction");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "stopShootAction");

        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, InputEvent.SHIFT_DOWN_MASK), "teleportAction");


        this.getActionMap().put("thrustAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.boostSpaceship();
            }
        });
        this.getActionMap().put("stopThrustAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.stopBoostingSpaceship();
            }
        });

        this.getActionMap().put("leftAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.startRotatingSpaceshipLeft();
            }
        });
        this.getActionMap().put("stopLeftAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.stopRotatingSpaceshipLeft();
            }
        });

        this.getActionMap().put("rightAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.startRotatingSpaceshipRight();
            }
        });
        this.getActionMap().put("stopRightAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.stopRotatingSpaceshipRight();
            }
        });

        this.getActionMap().put("shootAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.startShooting();
            }
        });
        this.getActionMap().put("stopShootAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.stopShooting();
            }
        });

        this.getActionMap().put("teleportAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.teleportSpaceship();
            }
        });
    }
}