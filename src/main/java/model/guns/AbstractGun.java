package model.guns;

import model.Vector;

abstract public class AbstractGun {

    protected final static int FRAME_OFFSET = 30;

    protected int fired;
    protected int shotRate;
    protected double bulletSpeed;

    AbstractGun(int shotRate, double bulletSpeed) {
        this.fired = 0;
        this.shotRate = shotRate;
        this.bulletSpeed = bulletSpeed;
    }

    public Bullet shoot(Vector position, double direction) {
        if (fired++ % shotRate != 0) {
            return null;
        }
        Vector initialPosition = new Vector(direction);
        initialPosition.multiplyBy(FRAME_OFFSET);
        initialPosition.add(position);
        return new Bullet(initialPosition, direction, bulletSpeed);
    }

    abstract public Bullet shoot(Vector position);

    public void restart() {
        fired = 0;
    }
}
