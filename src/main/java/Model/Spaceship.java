package Model;

public class Spaceship extends Ship {

    private int lives;

    public Spaceship() {
        this.lives = 3;
    }

    @Override
    public void shoot() {
        System.out.println("pew");
    }

    public int getLives() {
        return this.lives;
    }

    public void looseLife() {
        --this.lives;
    }

}
