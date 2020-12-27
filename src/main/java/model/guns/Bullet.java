package model.guns;

import model.FlyingObject;
import model.Vector;

public class Bullet extends FlyingObject {

    private final int TTL = 300;
    private int timeToLive;

    public Bullet(Vector position, double direction) {
        super(position);
        this.velocity = new Vector(direction);
        this.velocity.multiplyBy(2);
        this.direction = direction;

        this.timeToLive = TTL;
    }

    public void update() {
        --timeToLive;
    }

    public boolean isActive() {
        return timeToLive != 0;
    }

}
