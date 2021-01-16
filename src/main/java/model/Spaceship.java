package model;

import model.guns.Bullet;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Spaceship extends Ship {

    private final int LIVES = 3;
    private final double INIT_DIRECTION = -Math.PI/2;
    private final double ROTATION = Math.PI/120;
    private final double MAX_VELOCITY = 3;
    private final double BOOST = 0.4;
    private final double FRICTION = 0.99;
    private final int SPACESHIP_FRAME_OFFSET = 30;

    private final double[] X_SHAPE_COORDS = {0, 25, 0, -25};
    private final double[] Y_SHAPE_COORDS = {-30, 30, 10, 30};

    private boolean isThrusting;
    private boolean isTurningRight;
    private boolean isTurningLeft;
    private boolean isShooting;

    private int lives;

    public Spaceship() {
        super(0,0);
        lives = LIVES;
        direction = INIT_DIRECTION;
        isThrusting = false;
        velocity = new Vector(0,0);
    }

    public double[] getXShapeCoords() {
        return X_SHAPE_COORDS;
    }

    public double[] getYShapeCoords() {
        return Y_SHAPE_COORDS;
    }

    public void startShooting() {
        this.isShooting = true;
    }

    public void stopShooting() {
        this.isShooting = false;
    }

    public Bullet shoot() {
        if (isShooting) {
            Vector position = new Vector(direction);
            position.multiplyBy(SPACESHIP_FRAME_OFFSET);
            position.add(this.position);
            return new Bullet(position, direction);
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
        shoot();
        super.updatePosition();
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

    public int getLives() {
        return this.lives;
    }

    public boolean collides(Vector flyingObjectPosition, double flyingObjectRadius) {
        if (this.position.distance(flyingObjectPosition) <= flyingObjectRadius + 25) {
            this.position = new Vector(0, 0);
            this.velocity = new Vector(0, 0);
            this.direction = INIT_DIRECTION;
            --this.lives;
            return true;
        }
        return false;
    }

}
