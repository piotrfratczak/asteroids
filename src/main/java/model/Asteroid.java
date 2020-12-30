package model;

import java.util.LinkedList;
import java.util.List;

public class Asteroid extends FlyingObject {

    private final AsteroidSize size;
    private List<double[]> vertices;

    public Asteroid(AsteroidSize size) {
        this.size = size;

        double x = Math.random() * GameModel.SPACE_WIDTH;
        double y = Math.random() * GameModel.SPACE_HEIGHT;
        this.position = new Vector(x, y);

        this.direction = Math.random() * 2*Math.PI;
        setVelocity();
        generateShape();
    }

    private Asteroid(Asteroid that) {
        if (that.size == AsteroidSize.LARGE) {
            this.size = AsteroidSize.MEDIUM;
        } else {
            this.size = AsteroidSize.SMALL;
        }

        this.position = new Vector(that.position);
        this.direction = Math.random() * 2*Math.PI;
        setVelocity();
        generateShape();
    }

    private void setVelocity() {
        double factor;
        switch (size) {
            case LARGE -> factor = 0.5;
            case MEDIUM -> factor = 0.7;
            case SMALL -> factor = 1;
            default -> factor = 0; //TODO: maybe throw an exception
        }
        Vector velocity = new Vector(direction);
        velocity.multiplyBy(factor);
        this.velocity = velocity;
    }

    private void generateShape() {
        vertices = new LinkedList<>();

        int edges = 6 + (int)(5*Math.random());
        double R = size.getValue();
        double[] pair = new double[2];
        pair[0] = R;
        pair[1] = 0;
        vertices.add(pair);
        for (int i=1; i<edges; ++i) {
            pair = new double[2];
            double d = Math.random() * R/2;
            pair[0] = (R-d) * Math.cos(i * 2*Math.PI/edges);
            pair[1] = (R-d) * Math.sin(i * 2*Math.PI/edges);
            vertices.add(pair);
        }
    }

    public boolean isHit(Vector position) {
        return this.position.distance(position) <= size.getValue();
    }

    public List<Asteroid> hit() {
        List<Asteroid> childAsteroids = new LinkedList<>();
        if (size != AsteroidSize.SMALL) {
            for (int i=0; i<2; ++i) {
                childAsteroids.add(new Asteroid(this));
            }
        }

        return childAsteroids;
    }

    public List<double[]> getVertices() {
        return vertices;
    }
}
