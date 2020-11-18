package Model;

import Model.Guns.AbstractGun;

abstract class Ship extends FlyingObject {

    AbstractGun gun;

    abstract public void shoot();

}
