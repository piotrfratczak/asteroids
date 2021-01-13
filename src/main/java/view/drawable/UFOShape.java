package view.drawable;

import controller.GameController;
import view.Display;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;

public class UFOShape implements Drawable {

    private final GeneralPath path;

    public UFOShape() {
        double[] xCoords = GameController.getUFOXShapeCoords();
        double[] yCoords = GameController.getUFOYShapeCoords();
        path = new GeneralPath(BasicStroke.JOIN_ROUND, xCoords.length);
        path.moveTo(xCoords[0], yCoords[0]);
        for (int i=0; i<12; ++i) {
            path.lineTo(xCoords[i], yCoords[i]);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!GameController.updateUFOPosition()) return;
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
//        transform.rotate(rotation + Math.PI/2);

        g2.transform(transform);
        g2.draw(path);

        try{
            g2.transform(transform.createInverse());
        }catch(NoninvertibleTransformException e){
            e.printStackTrace();
        }
    }

}
