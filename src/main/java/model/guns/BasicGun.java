package model.guns;

import model.Vector;

public class BasicGun extends AbstractGun {

    private final static int shotRate = 50;
    private final static double bulletSpeed = 3.0;

    public BasicGun() {
        super(shotRate, bulletSpeed);
    }

    @Override
    public Bullet shoot(Vector position) {
        return null;
    }
}
