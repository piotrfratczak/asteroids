package controller;

import model.*;
import view.Display;

import java.util.*;

public class GameController {

    private static Display display;
    private static Level level;
    private static Spaceship spaceship;

    public GameController() {
        spaceship = new Spaceship();
        level     = new Level();
        display   = new Display();
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

    public static double[] getShipXShapeCoords() {
        return spaceship.getXShapeCoords();
    }

    public static double[] getShipYShapeCoords() {
        return spaceship.getYShapeCoords();
    }

    public static double[] getUFOXShapeCoords() {
        return level.getUFOXShapeCoords();
    }

    public static double[] getUFOYShapeCoords() {
        return level.getUFOYShapeCoords();
    }

    public static void startRotatingSpaceshipRight() {
        spaceship.startRotatingRight();
    }

    public static void stopRotatingSpaceshipRight() {
        spaceship.stopRotatingRight();
    }

    public static void startRotatingSpaceshipLeft() {
        spaceship.startRotatingLeft();
    }

    public static void stopRotatingSpaceshipLeft() {
        spaceship.stopRotatingLeft();
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
        spaceship.startThrusting();
    }

    public static void stopBoostingSpaceship() {
        spaceship.stopThrusting();
    }

    public static void updateSpaceship() {
        spaceship.updatePosition();
    }

    public static void startShooting() {
        spaceship.startShooting();
    }

    public static void stopShooting() {
        spaceship.stopShooting();
    }

    public static void teleportSpaceship() {
        spaceship.teleport();
    }

    public static void collide() {//TODO: rename
        level.collide(spaceship);
    }

    public static int getScore() {
        return level.getPoints();
    }

    public static int getLives() {
        //TODO: write game over
        int lives = spaceship.getLives();
        if (lives < 0) {
            spaceship = new Spaceship();
            level = new Level();
            return spaceship.getLives();
        }
        return lives;
    }

    public static int getLevel() {
        return level.getLevel();
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

    public static List<double[]> getAsteroidShape(int id) {
        return level.asteroids.get(id).getVertices();
    }

    public static Set<Integer> getAsteroidIds() {
        return level.asteroids.keySet();
    }

    public static boolean updateUFOPosition() {
        return level.updateUFOPosition();
    }

    public static double getUFOPositionX() {
        return level.getUFOPositionX();
    }

    public static double getUFOPositionY() {
        return level.getUFOPositionY();
    }

    private static class Level {

        private int which;
        private int points;
        private int asteroidCount;
        private final Map<Integer, Asteroid> asteroids;
        private UFO ufo;
        private double ufoProbability;

        private Level() {
            which = 1;
            points = 0;
            ufoProbability = 0.0001;
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

        private void generateUFO() {
            if (ufo == null && Math.random() < ufoProbability) {
                ufo = new UFO();
            }
        }

        public void collide(Spaceship spaceship) {
            generateUFO();
            points += spaceship.collide(asteroids);
            if (asteroids.size() == 0) {
                ++which;
                ++asteroidCount;
                ufoProbability *= 1.01;
                generateAsteroids();
            }
        }

        public int getPoints() {
            return points;
        }

        public int getLevel() {
            return which;
        }

        public boolean updateUFOPosition() {
            if (ufo != null) {
                ufo.updatePosition();
                return true;
            }
            return false;
        }

        public double getUFOPositionX() {
            if (ufo != null) {
                return ufo.getX();
            } else {
                return -1;
            }
        }

        public double getUFOPositionY() {
            if (ufo != null) {
                return ufo.getY();
            } else {
                return -1;
            }
        }

        public double[] getUFOXShapeCoords() {
            return UFO.getXShapeCoords();
        }

        public double[] getUFOYShapeCoords() {
            return UFO.getYShapeCoords();
        }
    }

}
