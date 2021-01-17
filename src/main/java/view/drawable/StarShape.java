package view.drawable;

import controller.GameController;
import view.Display;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class StarShape implements Drawable {
    private final GeneralPath path;

    public StarShape() {
        double[] xCoords = GameController.getStarXShapeCoords();
        double[] yCoords = GameController.getStarYShapeCoords();
        path = new GeneralPath(BasicStroke.JOIN_ROUND, xCoords.length);
        path.moveTo(xCoords[0], yCoords[0]);
        for (int i=0; i<xCoords.length; ++i) {
            path.lineTo(xCoords[i], yCoords[i]);
        }
        path.closePath();
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!GameController.twinklingStar()) return;
        double x = Display.WIDTH / (double)GameController.getGameWidth() * GameController.getStarPositionX();
        double y = Display.HEIGHT / (double)GameController.getGameHeight() * GameController.getStarPositionY();

        drawEdgeProof(g2, path, 0, x, y);
    }
}
