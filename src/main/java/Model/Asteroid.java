package Model;

import Controller.GameController;

import java.awt.*;

public class Asteroid extends FlyingObject {

    public Asteroid() {
        double x = Math.random() * GameController.SPACE_WIDTH;
        double y = Math.random() * GameController.SPACE_HEIGHT;
        this.position = new Vector(x, y);

        this.direction = Math.random() * 2*Math.PI;
    }

}
