package view.drawable;

import controller.GameController;
import view.Display;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.util.List;

public class AsteroidShape implements Drawable {
    //TODO: do not redraw path every time

    @Override
    public void draw(Graphics2D g2) {
        java.util.List<double[]> asteroidsCoords = GameController.getAsteroidsCoords();
        java.util.List<java.util.List<double[]>> asteroidShapes = GameController.getAsteroidShapes();
        int index = 0;
        for (double[] pair : asteroidsCoords) {
            double x = Display.WIDTH / (double)GameController.getGameWidth() * pair[0];
            double y = Display.HEIGHT / (double)GameController.getGameHeight() * pair[1];

            List<double[]> vertices = asteroidShapes.get(index);
            ++index;

            GeneralPath path = new GeneralPath(BasicStroke.JOIN_ROUND, vertices.size());
            path.moveTo(vertices.get(0)[0],vertices.get(0)[1]);
            for (double[] vertex : vertices) {
                path.lineTo(vertex[0], vertex[1]);
            }
            path.closePath();

            paintOffset(g2, path, x, y);
        }
    }

    private void paintOffset(Graphics2D g2, GeneralPath path, double x, double y) {
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
