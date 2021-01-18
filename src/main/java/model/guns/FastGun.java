package model.guns;

import model.Vector;

public class FastGun extends AbstractGun {

    private final static int shotRate = 50;
    private final static double bulletSpeed = 6.0;

    public FastGun() {
        super(shotRate, bulletSpeed);
    }

    @Override
    public Bullet shoot(Vector position) {
        return null;
    }
}
