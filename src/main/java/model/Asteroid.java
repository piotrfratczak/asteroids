package model;

public class Asteroid extends FlyingObject {

    public Asteroid() {
        double x = Math.random() * GameModel.SPACE_WIDTH;
        double y = Math.random() * GameModel.SPACE_HEIGHT;
        this.position = new Vector(x, y);

        this.direction = Math.random() * 2*Math.PI;
    }

}
