package model;

import model.Guns.AbstractGun;

abstract class Ship extends FlyingObject {

    AbstractGun gun;

    public Ship(int posX, int posY) {
        super(posX, posY);
    }

    abstract public void shoot();

}