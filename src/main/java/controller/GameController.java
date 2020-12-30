package controller;

import model.Asteroid;
import model.AsteroidSize;
import model.GameModel;
import model.Spaceship;
import view.Display;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    public static void update() {//TODO: rename
        Level.update(spaceship);
    }

    public static List<double[]> getBulletsCoords() {
        return spaceship.getBulletsCoords();
    }

    public static List<double[]> getAsteroidsCoords() {
        List<double[]> coords = new LinkedList<>();
        for (Asteroid asteroid : Level.asteroids) {
            double[] pair = new double[2];
            pair[0] = asteroid.getX();
            pair[1] = asteroid.getY();
            coords.add(pair);
        }
        return coords;
    }

    public static List<List<double[]>> getAsteroidShapes() {// TODO: make it smarter get asteroid by id or sth
        List<List<double[]>> shapes = new LinkedList<>();
        for (Asteroid asteroid : Level.asteroids) {
            List<double[]> vertices = asteroid.getVertices();
            shapes.add(vertices);
        }
        return shapes;
    }

    private static class Level {

        static int which;
        private static int asteroidCount;
        private static List<Asteroid> asteroids;

        private Level() {
            ++which;
            asteroids = new ArrayList<>();
            asteroidCount = 5;
            generateAsteroids();
        }

        private static void generateAsteroids() {
            for (int i=0; i<asteroidCount; ++i) {
                asteroids.add(new Asteroid(AsteroidSize.LARGE));
            }
        }

        public static void update(Spaceship spaceship) {
            spaceship.update(asteroids);
            if (asteroids.size() == 0) {
                ++which;
                ++asteroidCount;
                generateAsteroids();
            }
        }

    }

}
