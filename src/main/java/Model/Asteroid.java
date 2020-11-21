package Model;

import Controller.GameController;

import java.awt.*;

public class Asteroid extends FlyingObject {

    public Asteroid() {
        super(0,0);
        int x = (int) (Math.random() * GameController.SPACE_WIDTH);
        int y = (int) (Math.random() * GameController.SPACE_HEIGHT);
        this.position = new int[] {x, y};

        this.direction = Math.random() * 2*Math.PI;
    }

}
