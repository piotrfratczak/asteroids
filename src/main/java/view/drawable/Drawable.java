package view.drawable;

import view.Display;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

public interface Drawable {

    void draw(Graphics2D g2);

    default void drawEdgeProof(Graphics2D g2, GeneralPath path, double rotation, double x, double y) {
        // Draw the actual object
        drawOffset(g2, path, rotation, x, y);
        // Smooth edge transitions
        drawOffset(g2, path, rotation, x + Display.WIDTH, y);   // Draw the object on the right side when it's over the left edge
        drawOffset(g2, path, rotation, x - Display.WIDTH, y);   // And so on...
        drawOffset(g2, path, rotation, x, y + Display.HEIGHT);
        drawOffset(g2, path, rotation, x, y - Display.HEIGHT);
    }

    default void drawOffset(Graphics2D g2, GeneralPath path, double rotation, double x, double y) {
        AffineTransform transform = new AffineTransform();
        transform.translate(Display.WIDTH/2.0 + x,Display.HEIGHT/2.0 + y);
        transform.rotate(rotation);

        g2.transform(transform);
        g2.draw(path);

        try{
            g2.transform(transform.createInverse());
        }catch(NoninvertibleTransformException e){
            e.printStackTrace();
        }
    }

}
