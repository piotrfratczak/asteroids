package model;

import model.guns.Bullet;

public class UFO extends Ship {

    private final int FRAME_OFFSET = 25;
    private static final double[] X_SHAPE_COORDS = {-30, -15, 15, 30, -30, -15, 15, 10, -10, -15, 15, 30};
    private static final double[] Y_SHAPE_COORDS = {0, 10, 10, 0, 0, -10, -10, -20, -20, -10, -10, 0};

    private final int FRAME_OFFSET_S = 15;
    private static final double[] X_SHAPE_COORDS_S = {-20, -10, 10, 20, -20, -10, 10, 8, -8, -10, 10, 20};
    private static final double[] Y_SHAPE_COORDS_S = {0, 8, 8, 0, 0, -8, -8, -15, -15, -8, -8, 0};

    private final double TURNING_PROBABILITY = 0.002;
    private final int SHOT_RATE = 120;

    private final int frameOffset;
    private final UFOSize size;
    private int shotCounter;



    public UFO() {
        super( GameModel.LEFT_BOUND,
                (int)(Math.random() * (GameModel.SPACE_HEIGHT - 200) - (GameModel.TOP_BOUND - 100)) );
        this.direction = Math.random() * 2*Math.PI;
        this.size = UFOSize.randomSize();
        this.shotCounter = 0;
        this.frameOffset = size == UFOSize.SMALL ? FRAME_OFFSET_S : FRAME_OFFSET;
        setVelocity();
    }

    private void setVelocity() {
        velocity = new Vector(direction);
        switch (size) {
            case SMALL -> velocity.multiplyBy(1.5);
            case LARGE -> velocity.multiplyBy(0.8);
        }
    }

    public char getSize() {
        switch (size) {
            case SMALL -> {
                return 'S';
            }
            case LARGE -> {
                return 'L';
            }
            default -> {
                return '-';
            }
        }
    }

    public static double[] getLargeXShapeCoords() {
        return X_SHAPE_COORDS;
    }

    public static double[] getLargeYShapeCoords() {
        return Y_SHAPE_COORDS;
    }

    public static double[] getSmallXShapeCoords() {
        return X_SHAPE_COORDS_S;
    }

    public static double[] getSmallYShapeCoords() {
        return Y_SHAPE_COORDS_S;
    }

    public Bullet shoot() {
        ++shotCounter;
        if (shotCounter % SHOT_RATE != 0) {
            return null;
        }
        double direction = Math.random() * 2*Math.PI;
        Vector position = new Vector(direction);
        position.multiplyBy(frameOffset);
        position.add(this.position);
        return new Bullet(position, direction);
    }

    public boolean collides(Vector flyingObjectPosition, double flyingObjectRadius) {
        return this.position.distance(flyingObjectPosition) <= flyingObjectRadius + frameOffset;
    }

    @Override
    public void updatePosition() {
        if (Math.random() < TURNING_PROBABILITY) {
           this.direction += Math.random() * 2*Math.PI/80 - Math.random() * 2*Math.PI/80;
           setVelocity();
        }
        super.updatePosition();
    }

    public int pointsScored() {
        switch (size) {
            case LARGE -> {
                return 250;
            }
            case SMALL -> {
                return 400;
            }
            default -> {
                return 0;
            }
        }
    }
}
