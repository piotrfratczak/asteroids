package model.guns;

import model.Vector;

public class SerialGun extends AbstractGun {

    private final static int shotRate = 50;
    private final static double bulletSpeed = 3.0;

    public SerialGun() {
        super(shotRate, bulletSpeed);
    }

    @Override
    public Bullet shoot(Vector position, double direction) {
        if (fired++ % shotRate > 10) {
            return null;
        }
        Vector initialPosition = new Vector(direction);
        initialPosition.multiplyBy(FRAME_OFFSET);
        initialPosition.add(position);
        return new Bullet(initialPosition, direction, bulletSpeed);
    }

    @Override
    public Bullet shoot(Vector position) {
        return null;
    }
}
