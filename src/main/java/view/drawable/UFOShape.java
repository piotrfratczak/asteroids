package view.drawable;

import controller.GameController;
import view.Display;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class UFOShape implements Drawable {

    private final GeneralPath pathLarge;
    private final GeneralPath pathSmall;

    public UFOShape() {
        double[] xCoordsLarge = GameController.getUFOLargeXShapeCoords();
        double[] yCoordsLarge = GameController.getUFOLargeYShapeCoords();
        pathLarge = new GeneralPath(BasicStroke.JOIN_ROUND, xCoordsLarge.length);
        pathLarge.moveTo(xCoordsLarge[0], yCoordsLarge[0]);

        double[] xCoordsSmall = GameController.getUFOSmallXShapeCoords();
        double[] yCoordsSmall = GameController.getUFOSmallYShapeCoords();
        pathSmall = new GeneralPath(BasicStroke.JOIN_ROUND, xCoordsSmall.length);
        pathSmall.moveTo(xCoordsSmall[0], yCoordsSmall[0]);

        for (int i=0; i<xCoordsLarge.length; ++i) {
            pathLarge.lineTo(xCoordsLarge[i], yCoordsLarge[i]);
            pathSmall.lineTo(xCoordsSmall[i], yCoordsSmall[i]);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!GameController.flyingUFO()) return;
        double x = Display.WIDTH / (double)GameController.getGameWidth() * GameController.getUFOPositionX();
        double y = Display.HEIGHT / (double)GameController.getGameHeight() * GameController.getUFOPositionY();

        GeneralPath path;
        if (GameController.getUFOSize() == 'L') path = pathLarge;
        else path = pathSmall;

        drawEdgeProof(g2, path, 0, x, y);
    }
}
