package model.guns;

import model.Vector;

public class SuperHighFreqGun extends AbstractGun {

    private final static int shotRate = 3;
    private final static double bulletSpeed = 3.0;

    public SuperHighFreqGun() {
        super(shotRate, bulletSpeed);
    }

    @Override
    public Bullet shoot(Vector position) {
        return null;
    }
}
