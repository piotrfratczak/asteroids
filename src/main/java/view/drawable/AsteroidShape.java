package view.drawable;

import controller.GameController;
import view.Display;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.List;

public class AsteroidShape implements Drawable {

    private List<double[]> vertices;
    private GeneralPath path;
    private int id;
    private double rotation;
    private double rotationDirection;

    public AsteroidShape(int id) {
        this.id = id;
        vertices = GameController.getAsteroidShape(this.id);

        rotation = 0;
        rotationDirection = Math.random() < 0.5 ? -1 : 1;

        path = new GeneralPath(BasicStroke.JOIN_ROUND, vertices.size());
        path.moveTo(vertices.get(0)[0],vertices.get(0)[1]);
        for (double[] vertex : vertices) {
            path.lineTo(vertex[0], vertex[1]);
        }
        path.closePath();
    }

    @Override
    public void draw(Graphics2D g2) {
        rotation += rotationDirection * Math.PI/3000;
        double coords[] = GameController.getAsteroidCoords(id);
        double x = Display.WIDTH / (double)GameController.getGameWidth() * coords[0];
        double y = Display.HEIGHT / (double)GameController.getGameHeight() * coords[1];

        drawEdgeProof(g2, path, rotation, x, y);
    }


}
