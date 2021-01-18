package model.guns;

import model.Vector;

public class HighFreqGun extends AbstractGun {

    private final static int shotRate = 20;
    private final static double bulletSpeed = 3.0;

    public HighFreqGun() {
        super(shotRate, bulletSpeed);
    }

    @Override
    public Bullet shoot(Vector position) {
        return null;
    }
}
