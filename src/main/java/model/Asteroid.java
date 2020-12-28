package model;

public class Asteroid extends FlyingObject {

    private final AsteroidSize size;

    public Asteroid(AsteroidSize size) {
        this.size = size;

        double x = Math.random() * GameModel.SPACE_WIDTH;
        double y = Math.random() * GameModel.SPACE_HEIGHT;
        this.position = new Vector(x, y);

        this.direction = Math.random() * 2*Math.PI;
        setVelocity();
    }

    private void setVelocity() {
        double factor;
        switch (this.size) {
            case LARGE -> factor = 0.5;
            case MEDIUM -> factor = 20;
            case SMALL -> factor = 25;
            default -> factor = 0;
        }
        Vector velocity = new Vector(direction);
        velocity.multiplyBy(factor);
        this.velocity = velocity;
    }

}
