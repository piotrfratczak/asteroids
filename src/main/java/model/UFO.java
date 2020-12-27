package model;

public class UFO extends Ship {

    public UFO(int posX, int posY) {
        super(posX, posY);
    }

    @Override
    public void shoot() {
        System.out.println("ufopew");
    }

}
