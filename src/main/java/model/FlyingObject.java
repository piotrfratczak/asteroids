package model;

abstract public class FlyingObject {

    protected Vector position;
    protected double direction;
    protected Vector velocity;

    public FlyingObject() {}

    public FlyingObject(int posX, int posY) {
        position = new Vector(posX, posY);
    }

    public FlyingObject(Vector position) {
        this.position = position;
    }

    public double getDirection() {
        return direction;
    }

    public void updatePosition() {
        position.add(velocity);
    }

    public double getX() {
        if (position.getX() > GameModel.RIGHT_BOUND) {
            position.setX(GameModel.LEFT_BOUND);
        } else if (position.getX() < GameModel.LEFT_BOUND) {
            position.setX(GameModel.RIGHT_BOUND);
        }

        return position.getX();
    }

    public double getY() {
        if (position.getY() > GameModel.TOP_BOUND) {
            position.setY(GameModel.BOTTOM_BOUND);
        } else if (position.getY() < GameModel.BOTTOM_BOUND) {
            position.setY(GameModel.TOP_BOUND);
        }

        return position.getY();
    }

}
