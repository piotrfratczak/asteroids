package model;

public class Star extends FlyingObject {

    private static final int TIME_TO_LIVE = 1000;

    private static final double[] X_SHAPE_COORDS = {0, 15, 42, 23, 30, 0, -30, -23, -42, -15};
    private static final double[] Y_SHAPE_COORDS = {-40, -10, -10, 10, 38, 25, 38, 10, -10, -10};

    private int timeToLive;

    public Star(Asteroid asteroid) {
        super(asteroid.position);
        this.timeToLive = TIME_TO_LIVE;
    }

    @Override
    public void updatePosition() {
        --timeToLive;
    }

    public boolean isTwinkling() {
        return timeToLive > 0;
    }

    public static double[] getXShapeCoords() {
        return X_SHAPE_COORDS;
    }

    public static double[] getYShapeCoords() {
        return Y_SHAPE_COORDS;
    }
}
