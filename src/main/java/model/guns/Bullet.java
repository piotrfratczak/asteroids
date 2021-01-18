package model.guns;

import model.FlyingObject;
import model.GameModel;
import model.Vector;

public class Bullet extends FlyingObject {

    private boolean isAlive;

    public Bullet(Vector position, double direction, double speed) {
        super(position);
        this.velocity = new Vector(direction);
        this.velocity.multiplyBy(speed);
        this.direction = direction;

        this.isAlive = true;
    }

    @Override
    public double getX() {
        if (position.getX() > GameModel.RIGHT_BOUND || position.getX() < GameModel.LEFT_BOUND) {
            destroy();
        }

        return position.getX();
    }

    @Override
    public double getY() {
        if (position.getY() > GameModel.TOP_BOUND || position.getY() < GameModel.BOTTOM_BOUND) {
            destroy();
        }

        return position.getY();
    }

    public void destroy() {
        isAlive = false;
    }

    public boolean isBurnedOut() {
        return !isAlive;
    }

}
