package view.drawable;

import controller.GameController;
import view.Display;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;

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

        // Draw the actual ship
        paintOffset(g2, x, y);
        // Smooth edge transitions
        paintOffset(g2, x + Display.WIDTH, y);   // Draw the ship on the right side when it's over the left edge
        paintOffset(g2, x - Display.WIDTH, y);   // And so on...
    }

    private void paintOffset(Graphics2D g2, double x, double y) {
        AffineTransform transform = new AffineTransform();
        transform.translate(Display.WIDTH/2.0 + x,Display.HEIGHT/2.0 + y);
        g2.transform(transform);

        if (GameController.getUFOSize() == 'L') g2.draw(pathLarge);
        else if (GameController.getUFOSize() == 'S') g2.draw(pathSmall);
        else return;

        try{
            g2.transform(transform.createInverse());
        }catch(NoninvertibleTransformException e){
            e.printStackTrace();
        }
    }

}
