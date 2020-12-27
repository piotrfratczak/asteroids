package controller;

import model.Asteroid;
import model.GameModel;
import model.Spaceship;
import view.Display;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private static Display display;
    private static Level level;

    private static Spaceship spaceship;

    public GameController() {
        System.out.println(this.getClass().getCanonicalName());

        display = new Display();
//        level   = new Level();

        spaceship = new Spaceship();
    }

    public static String getTitle() {
        return GameModel.TITLE;
    }

    public static int getGameWidth() {
        return GameModel.SPACE_WIDTH;
    }

    public static int getGameHeight() {
        return GameModel.SPACE_HEIGHT;
    }

    public static void rotateSpaceshipRight() {
        spaceship.rotateRight();
    }

    public static void rotateSpaceshipLeft() {
        spaceship.rotateLeft();
    }

    public static double getSpaceshipRotation() {
        return spaceship.getDirection();
    }

    public static double getSpaceshipX() {
        return spaceship.getX();
    }

    public static double getSpaceshipY() {
        return spaceship.getY();
    }

    public static void boostSpaceship() {
        spaceship.thrust();
    }

    public static void updateSpaceship() {
        spaceship.updateVelocity();
    }

    public static void shootSpaceship() {
        spaceship.shoot();
    }

    public static void teleportSpaceship() {
        spaceship.teleport();
    }

    private static class Level {

        static int which;
        private List<Asteroid> asteroids;

        private Level() {
            ++which;
            asteroids = new ArrayList<>();
            generateAsteroids(5);
        }

        private void generateAsteroids(int numberOfAsteroids) {
            for (int i=0; i<numberOfAsteroids; ++i) {
                asteroids.add(new Asteroid());
            }
        }

    }

}
