package Model;

public class Spaceship extends Ship {

    private static final int LIVES = 3;
    private static final double INIT_DIRECTION = 0.0;

    private static final double ROTATION = Math.PI/16;

    private int lives;
    private double rotation;

    public Spaceship(int posX, int posY) {
        super(posX, posY);
        lives = LIVES;
        direction = INIT_DIRECTION;
    }

    @Override
    public void shoot() {
        System.out.println("pew");
    }

    public void rotate() {
        this.direction += rotation;
        System.out.println(this.direction);
    }

    public void setRotation(double angle) {
        rotation = angle;
    }

    public int getLives() {
        return this.lives;
    }

    public void looseLife() {
        --this.lives;
    }

}
