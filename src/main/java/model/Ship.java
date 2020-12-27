package model;

import model.guns.AbstractGun;

abstract class Ship extends FlyingObject {

    AbstractGun gun;

    public Ship(int posX, int posY) {
        super(posX, posY);
    }

    abstract public void shoot();

}
