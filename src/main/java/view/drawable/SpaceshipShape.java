package view.drawable;

import controller.Game;
import controller.GameController;
import view.Display;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;

public class SpaceshipShape implements Drawable{

    private final GeneralPath path;
    private double rotation;

    public SpaceshipShape() {
        double[] xCoords = GameController.getShipXShapeCoords();
        double[] yCoords = GameController.getShipYShapeCoords();
        path = new GeneralPath(BasicStroke.JOIN_ROUND, xCoords.length);
        path.moveTo(xCoords[0], yCoords[0]);
        for (int i=0; i<xCoords.length; ++i) {
            path.lineTo(xCoords[i], yCoords[i]);
        }
        path.closePath();
    }

    @Override
    public void draw(Graphics2D g2) {
        GameController.updateSpaceship();
        double x = Display.WIDTH / (double)GameController.getGameWidth() * GameController.getSpaceshipX();
        double y = Display.HEIGHT / (double)GameController.getGameHeight() * GameController.getSpaceshipY();

        rotation = GameController.getSpaceshipRotation();
        // Draw the actual ship
        paintOffset(g2, x, y);
        // Smooth edge transitions
        paintOffset(g2, x + Display.WIDTH, y);   // Draw the ship on the right side when it's over the left edge
        paintOffset(g2, x - Display.WIDTH, y);   // And so on...
        paintOffset(g2, x, y + Display.HEIGHT);
        paintOffset(g2, x, y - Display.HEIGHT);

    }

    private void paintOffset(Graphics2D g2, double x, double y) {
        AffineTransform transform = new AffineTransform();
        transform.translate(Display.WIDTH/2.0 + x,Display.HEIGHT/2.0 + y);
        transform.rotate(rotation + Math.PI/2);

        g2.transform(transform);
        g2.draw(path);

        try{
            g2.transform(transform.createInverse());
        }catch(NoninvertibleTransformException e){
            e.printStackTrace();
        }
    }
}
