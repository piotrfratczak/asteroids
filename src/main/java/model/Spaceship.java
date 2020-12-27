package model;

import java.util.Random;

public class Spaceship extends Ship {

    private static final int LIVES = 3;
    private static final double INIT_DIRECTION = -Math.PI/2;
    private static final double ROTATION = Math.PI/20;

    private int lives;

    public Spaceship() {
        super(0,0);
        lives = LIVES;
        direction = INIT_DIRECTION;
        velocity = new Vector(0,0);
    }

    @Override
    public void shoot() {
        System.out.println("pew");
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
