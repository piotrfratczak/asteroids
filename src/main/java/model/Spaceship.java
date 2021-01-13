package model;

import model.guns.Bullet;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Spaceship extends Ship {

    private static final int LIVES = 3;
    private static final double INIT_DIRECTION = -Math.PI/2;
    private static final double ROTATION = Math.PI/80;
    private static final double MAX_VELOCITY = 4;

    private final double[] X_SHAPE_COORDS = {0, 25, 0, -25};
    private final double[] Y_SHAPE_COORDS = {-30, 30, 10, 30};

    private boolean isThrusting;
    private boolean isTurningRight;
    private boolean isTurningLeft;
    private boolean isShooting;

    private int lives;
    private final List<Bullet> bullets;

    public Spaceship() {
        super(0,0);
        lives = LIVES;
        direction = INIT_DIRECTION;
        isThrusting = false;
        velocity = new Vector(0,0);

        bullets = new LinkedList<>();
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

    public void shoot() {
        if (isShooting) {
            Vector position = new Vector(direction);
            //TODO: get rid of this random multiplication
            position.multiplyBy(30);
            position.add(this.position);
            bullets.add(new Bullet(position, direction));
        }
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
            velocity.multiplyBy(0.995);
        } else {
            Vector force = new Vector(direction);
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

    public int collide(Map<Integer,Asteroid> asteroids) {
        int scored = 0;
        for (Map.Entry<Integer,Asteroid> entry : asteroids.entrySet()) {
            Asteroid asteroid = entry.getValue();
            if (this.position.distance(asteroid.position) <= asteroid.getRadius() + 25) {
                this.position = new Vector(0,0);
                this.velocity = new Vector(0,0);
                this.direction = INIT_DIRECTION;
                looseLife();
                break;
            }
            asteroid.updatePosition();
        }
        for (Bullet bullet : bullets) {
            for (Map.Entry<Integer,Asteroid> entry : asteroids.entrySet()) {
                Asteroid asteroid = entry.getValue();
                if (asteroid.isHit(bullet.position)) {
                    bullet.destroy();
                    scored += asteroid.pointsScored();
                    List<Asteroid> children = asteroid.hit();
                    for (Asteroid child : children) asteroids.put(child.getId(), child);
                    asteroids.remove(asteroid.getId());
                    break;
                }
            }
            bullet.updatePosition();
        }
        bullets.removeIf(Bullet::isBurnedOut);

        return scored;
    }

    public List<double[]> getBulletsCoords() {
        List<double[]> coords = new LinkedList<>();
        for (Bullet bullet : bullets) {
            double[] pair = new double[2];
            pair[0] = bullet.getX();
            pair[1] = bullet.getY();
            coords.add(pair);
        }
        return coords;
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

    public void looseLife() {
        --this.lives;
    }

}
