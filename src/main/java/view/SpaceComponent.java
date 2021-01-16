package view;

import controller.GameController;
import view.drawable.AsteroidShape;
import view.drawable.BulletShape;
import view.drawable.SpaceshipShape;
import view.drawable.UFOShape;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SpaceComponent extends JComponent {

    private final float STROKE_WIDTH = 1.5f;

    public static final String RES_PATH = "src/main/resources/";
    public static final String DIGITAL_FONT = "digital-mono-7.ttf";
    public static Font digitalFont;

    private final SpaceshipShape spaceshipShape;
    private final BulletShape bulletShape;
    private final Map<Integer, AsteroidShape> asteroidShapes;
    private final UFOShape ufoShape;

    SpaceComponent() {
        spaceshipShape = new SpaceshipShape();
        bulletShape = new BulletShape();
        asteroidShapes = new HashMap<>();
        ufoShape = new UFOShape();
        addFont();
    }

    private void addFont() {
        try {
            digitalFont = Font.createFont(Font.TRUETYPE_FONT, new File(RES_PATH + DIGITAL_FONT));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        digitalFont = digitalFont.deriveFont(Font.BOLD,28);
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
            g2.drawString(text, Display.WIDTH / 2 - offset, Display.HEIGHT/2);
            return;
        }

        drawText(g2);

        spaceshipShape.draw(g2);
        ufoShape.draw(g2);
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
}