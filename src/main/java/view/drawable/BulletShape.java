package view.drawable;

import controller.GameController;
import view.Display;

import java.awt.*;
import java.util.List;

public class BulletShape implements Drawable {

    @Override
    public void draw(Graphics2D g2) {
        List<double[]> bulletsCoords = GameController.getBulletsCoords();
        int r = 4;

        for (double[] pair : bulletsCoords) {
            double x = Display.WIDTH / (double)GameController.getGameWidth() * pair[0];
            double y = Display.HEIGHT / (double)GameController.getGameHeight() * pair[1];
            g2.fillOval((int)x + Display.WIDTH/2 - r/2, (int)y + Display.HEIGHT/2 - r/2, r, r);
        }
    }
}
