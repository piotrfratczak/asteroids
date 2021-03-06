package view.drawable;

import controller.GameController;
import view.Display;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class SpaceshipShape implements Drawable{

    private final GeneralPath path;
    private final int r = 90;

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
        double x = Display.WIDTH / (double)GameController.getGameWidth() * GameController.getSpaceshipX();
        double y = Display.HEIGHT / (double)GameController.getGameHeight() * GameController.getSpaceshipY();
        double rotation = GameController.getSpaceshipRotation() + Math.PI/2;

        if (GameController.spaceshipHasShield()) g2.drawOval((int)x + Display.WIDTH/2 - r/2, (int)y + Display.HEIGHT/2 - r/2 , r, r);

        drawEdgeProof(g2, path, rotation, x, y);
    }
}
