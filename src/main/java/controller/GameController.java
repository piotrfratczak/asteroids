package controller;

import model.Asteroid;
import model.AsteroidSize;
import model.GameModel;
import model.Spaceship;
import view.Display;

import java.util.*;

public class GameController {

    private static Display display;
    private static Level level;
    private static Spaceship spaceship;

    public GameController() {
        System.out.println(this.getClass().getCanonicalName());
        display   = new Display();
        level     = new Level();
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

    public static void collide() {//TODO: rename
        level.collide(spaceship);
    }

    public static List<double[]> getBulletsCoords() {
        return spaceship.getBulletsCoords();
    }

    public static double[] getAsteroidCoords(int id) {
        Asteroid asteroid = level.asteroids.get(id);
        double[] coords = new double[2];
        coords[0] = asteroid.getX();
        coords[1] = asteroid.getY();

        return coords;
    }

    public static List<double[]> getAsteroidShape(int id) {// TODO: make it smarter get asteroid by id or sth
        return level.asteroids.get(id).getVertices();
    }

    public static Set<Integer> getAsteroidIds() {
        return level.asteroids.keySet();
    }

    private static class Level {

        int which;
        private int asteroidCount;
        private Map<Integer, Asteroid> asteroids;

        private Level() {
            ++which;
            asteroids = new HashMap<>();
            asteroidCount = 5;
            generateAsteroids();
        }

        private void generateAsteroids() {
            for (int i=0; i<asteroidCount; ++i) {
                Asteroid asteroid = new Asteroid(AsteroidSize.LARGE);
                Integer id = asteroid.getId();
                asteroids.put(id, asteroid);
            }
        }

        public void collide(Spaceship spaceship) {
            spaceship.collide(asteroids);
            if (asteroids.size() == 0) {
                ++which;
                ++asteroidCount;
                generateAsteroids();
            }
        }
    }

}
