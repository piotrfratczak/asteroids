package model;

import controller.GameController;

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

        if (position.getX() > GameController.RIGHT_BOUND) {
            position.setX(GameController.LEFT_BOUND);
        } else if (position.getX() < GameController.LEFT_BOUND) {
            position.setX(GameController.RIGHT_BOUND);
        }

        return position.getX();
    }

    public double getY() {
        position.add(velocity);

        if (position.getY() > GameController.TOP_BOUND) {
            position.setY(GameController.BOTTOM_BOUND);
        } else if (position.getY() < GameController.BOTTOM_BOUND) {
            position.setY(GameController.TOP_BOUND);
        }

        return position.getY();
    }

}

