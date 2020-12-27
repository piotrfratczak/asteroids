package model;

abstract class FlyingObject {

    Vector position;
    double direction;
    Vector velocity;

    public FlyingObject() {
    }

    public FlyingObject(int posX, int posY) {
        position = new Vector(posX, posY);
    }

    public double getDirection() {
        return direction;
    }

    public double getX() {
        position.add(velocity);

        if (position.getX() > GameModel.RIGHT_BOUND) {
            position.setX(GameModel.LEFT_BOUND);
        } else if (position.getX() < GameModel.LEFT_BOUND) {
            position.setX(GameModel.RIGHT_BOUND);
        }

        return position.getX();
    }

    public double getY() {
        position.add(velocity);

        if (position.getY() > GameModel.TOP_BOUND) {
            position.setY(GameModel.BOTTOM_BOUND);
        } else if (position.getY() < GameModel.BOTTOM_BOUND) {
            position.setY(GameModel.TOP_BOUND);
        }

        return position.getY();
    }

}

