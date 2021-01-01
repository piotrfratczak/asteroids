package view.drawable;

import controller.GameController;
import view.Display;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.util.List;

public class AsteroidShape implements Drawable {

    private List<double[]> vertices;
    private GeneralPath path;
    private int id;

    public AsteroidShape(int id) {
        this.id = id;
        vertices = GameController.getAsteroidShape(this.id);

        path = new GeneralPath(BasicStroke.JOIN_ROUND, vertices.size());
        path.moveTo(vertices.get(0)[0],vertices.get(0)[1]);
        for (double[] vertex : vertices) {
            path.lineTo(vertex[0], vertex[1]);
        }
        path.closePath();
    }

    @Override
    public void draw(Graphics2D g2) {
        double coords[] = GameController.getAsteroidCoords(id);
        double x = Display.WIDTH / (double)GameController.getGameWidth() * coords[0];
        double y = Display.HEIGHT / (double)GameController.getGameHeight() * coords[1];

        paintOffset(g2, x, y);
        // Smooth edge transitions
        paintOffset(g2, x + Display.WIDTH, y);
        paintOffset(g2, x - Display.WIDTH, y);
        paintOffset(g2, x, y + Display.HEIGHT);
        paintOffset(g2, x, y - Display.HEIGHT);
    }

    private void paintOffset(Graphics2D g2, double x, double y) {
        AffineTransform transform = new AffineTransform();
        transform.translate(Display.WIDTH/2.0 + x,Display.HEIGHT/2.0 + y);
        transform.rotate(Math.PI/2);

        g2.transform(transform);
        g2.draw(path);

        try{
            g2.transform(transform.createInverse());
        }catch(NoninvertibleTransformException e){
            e.printStackTrace();
        }
    }
}
