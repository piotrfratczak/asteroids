package model;

import model.guns.Bullet;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Spaceship extends Ship {

    private static final int LIVES = 3;
    private static final double INIT_DIRECTION = -Math.PI/2;
    private static final double ROTATION = Math.PI/20;

    private int lives;
    private List<Bullet> bullets;

    public Spaceship() {
        super(0,0);
        lives = LIVES;
        direction = INIT_DIRECTION;
        velocity = new Vector(0,0);

        bullets = new LinkedList<>();
    }

    public void shoot() {
        Vector position = new Vector(direction);
        //TODO: get rid of this random multiplication
        position.multiplyBy(30);
        position.add(this.position);
        bullets.add(new Bullet(position, direction));
    }

    public void rotateRight() {
        this.direction += ROTATION;
    }

    public void rotateLeft() {
        this.direction -= ROTATION;
    }

    public void thrust() {
        Vector force = new Vector(direction);
        velocity.add(force);
    }

    public void updateVelocity() {
        velocity.multiplyBy(0.998);
    }

    public void updateBullets() {
        bullets.forEach(Bullet::update);
        bullets.removeIf(Bullet::isBurnedOut);
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
