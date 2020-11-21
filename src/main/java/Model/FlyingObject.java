package Model;

abstract class FlyingObject {

    int[] position;
    double direction;
    double speed;

    public FlyingObject(int posX, int posY) {
        position = new int[] {posX, posY};
    }

    public double getDirection() {
        return direction;
    }

}
