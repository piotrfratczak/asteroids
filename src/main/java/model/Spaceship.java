package model;

import model.guns.*;

import java.util.Random;

public class Spaceship extends Ship {

    private final double INIT_DIRECTION = -Math.PI/2;
    private final double ROTATION = Math.PI/120;
    private final double MAX_VELOCITY = 2;
    private final double BOOST = 0.3;
    private final double FRICTION = 0.995;
    private final int SHIELD_TTL = 4000;

    private static final double[] X_SHAPE_COORDS = {0, 25, 0, -25};
    private static final double[] Y_SHAPE_COORDS = {-30, 30, 10, 30};

    private boolean isThrusting;
    private boolean isTurningRight;
    private boolean isTurningLeft;
    private boolean isShooting;

    private int shieldTTL;

    public Spaceship() {
        super(0,0);
        this.direction = INIT_DIRECTION;
        this.isThrusting = false;
        this.velocity = new Vector(0,0);
        this.shieldTTL = 0;
        this.gun = new BasicGun();
    }

    public static double[] getXShapeCoords() {
        return X_SHAPE_COORDS;
    }

    public static double[] getYShapeCoords() {
        return Y_SHAPE_COORDS;
    }

    public void startShooting() {
        this.isShooting = true;
    }

    public void stopShooting() {
        this.isShooting = false;
        this.gun.restart();
    }

    public Bullet shoot() {
        if (isShooting) {
            return gun.shoot(position, direction);
        }
        return null;
    }

    private void rotate() {
        if (isTurningLeft && !isTurningRight) {
            this.direction -= ROTATION;
        } else if (isTurningRight && !isTurningLeft) {
            this.direction += ROTATION;
        }
    }

    public void startRotatingRight() {
        isTurningRight = true;
    }

    public void stopRotatingRight() {
        isTurningRight = false;
    }

    public void startRotatingLeft() {
        isTurningLeft = true;
    }

    public void stopRotatingLeft() {
        isTurningLeft = false;
    }

    @Override
    public void updatePosition() {
        rotate();
        thrust();
        super.updatePosition();
        if (shieldTTL > 0) --shieldTTL;
    }

    private void thrust() {
        if (!isThrusting) {
            velocity.multiplyBy(FRICTION);
        } else {
            Vector force = new Vector(direction);
            force.multiplyBy(BOOST);
            velocity.add(force);

            double speed = velocity.distance(new Vector(0,0));
            if (speed > MAX_VELOCITY) {
                velocity = new Vector(direction);
                velocity.multiplyBy(MAX_VELOCITY);
            }
        }
    }

    public void startThrusting() {
        isThrusting = true;
    }

    public void stopThrusting() {
        isThrusting = false;
    }

    public void teleport() {
        Random rand = new Random();
        int newX = rand.nextInt(GameModel.SPACE_WIDTH + 1) + GameModel.LEFT_BOUND;
        int newY = rand.nextInt(GameModel.SPACE_HEIGHT + 1) + GameModel.BOTTOM_BOUND;
        this.position = new Vector(newX, newY);
    }

    public boolean collides(Vector flyingObjectPosition, double flyingObjectRadius) {
        return this.position.distance(flyingObjectPosition) <= flyingObjectRadius + 25;
    }

    public void restart() {
        this.position = new Vector(0, 0);
        this.velocity = new Vector(0, 0);
        this.direction = INIT_DIRECTION;
    }

    public void activateShield() {
        shieldTTL = SHIELD_TTL;
    }

    public boolean hasShield() {
        return shieldTTL > 0;
    }

    public void upgradeGun() {
        if (this.gun instanceof BasicGun) this.gun = new FastGun();
        else if (this.gun instanceof FastGun) this.gun = new SerialGun();
        else if (this.gun instanceof SerialGun) this.gun = new HighFreqGun();
        else if (this.gun instanceof HighFreqGun) this.gun = new SuperHighFreqGun();
    }

}
