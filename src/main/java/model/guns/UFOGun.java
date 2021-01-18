package model.guns;

import model.Vector;

public class UFOGun extends AbstractGun {

    private static final int shotRate = 120;
    private static final double bulletSpeed = 3.0;

    public UFOGun() {
        super(shotRate, bulletSpeed);
    }

    @Override
    public Bullet shoot(Vector position) {
        double direction = Math.random() * 2 * Math.PI;
        return shoot(position, direction);
    }
}
