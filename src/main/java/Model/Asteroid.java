package Model;

import Controller.Game;

public class Asteroid extends FlyingObject {

    private double rotation;

    public Asteroid() {
        int w = Game.getDisplayWidth();
        int h = Game.getDisplayHeight();
        int x = (int) (Math.random() * w);
        int y = (int) (Math.random() * h);
        this.position = new int[] {x, y};

        this.rotation = Math.random() * 2*Math.PI;
    }

    public double getRotation() {
        return this.rotation;
    }

}
