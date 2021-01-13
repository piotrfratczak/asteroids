package model;

public class UFO extends Ship {

    private static final double[] X_SHAPE_COORDS = {-30, -15, 15, 30, -30, -15, 15, 10, -10, -15, 15, 30};
    private static final double[] Y_SHAPE_COORDS = {0, 10, 10, 0, 0, -10, -10, -20, -20, -10, -10, 0};

    private final UFOSize size;

    public UFO() {
        super( GameModel.LEFT_BOUND,
                (int)(Math.random() * (GameModel.SPACE_HEIGHT - 200) - (GameModel.TOP_BOUND - 100)) );

        direction = Math.random() < 0.5 ? 0 : Math.PI;
        size = UFOSize.randomSize();
        setVelocity();
    }

    public static double[] getXShapeCoords() {
        return X_SHAPE_COORDS;
    }

    public static double[] getYShapeCoords() {
        return Y_SHAPE_COORDS;
    }

    private void setVelocity() {
        velocity = new Vector(direction);
        switch (size) {
            case SMALL -> velocity.multiplyBy(2);
            case LARGE -> velocity.multiplyBy(1);
        }
    }

    @Override
    public void shoot() {
        System.out.println("ufopew");
    }

}
