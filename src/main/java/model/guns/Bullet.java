package model.guns;

import model.FlyingObject;
import model.Vector;

public class Bullet extends FlyingObject {

    private final int TTL = 100;
    private int timeToLive;

    public Bullet(Vector position, double direction) {
        super(position);
        this.velocity = new Vector(direction);
        this.velocity.multiplyBy(10);
        this.direction = direction;

        this.timeToLive = TTL;
    }

    public void update() {
        --timeToLive;
        updatePosition();
    }

    public void destroy() {
        timeToLive = 0;
    }

    public boolean isBurnedOut() {
        return timeToLive <= 0;
    }

}
