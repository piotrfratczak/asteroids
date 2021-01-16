package model;

import model.guns.Bullet;

import java.util.*;

public class GameModel {

    public static final String TITLE = "Asteroids";
    public static final int SPACE_WIDTH = 1000;
    public static final int SPACE_HEIGHT = 1000;

    public static final int RIGHT_BOUND  = 500;
    public static final int TOP_BOUND    = 500;
    public static final int LEFT_BOUND   = -500;
    public static final int BOTTOM_BOUND = -500;

    private final int INITIAL_ASTEROID_COUNT = 4;
    private final double INITAL_UFO_PROBABILITY = 0.0001;
    private final double UFO_PROBABILITY_CHANGE_RATE = 1.01;

    private int level;
    private int points;
    private int asteroidCount;
    private Map<Integer, Asteroid> asteroids;
    private List<Bullet> bullets;
    private List<Bullet> ufoBullets;
    private double ufoProbability;
    private UFO ufo;
    private Spaceship spaceship;


    public GameModel() {
        startNewGame();
    }

    private void startNewGame() {
        level = 1;
        points = 0;
        ufoProbability = INITAL_UFO_PROBABILITY;
        asteroidCount = INITIAL_ASTEROID_COUNT;
        spaceship = new Spaceship();
        generateAsteroids();
    }

    private void generateAsteroids() {
        asteroids = new HashMap<>();
        bullets = new LinkedList<>();
        ufoBullets = new LinkedList<>();
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

    public void collide() {
        generateUFO();
        if (asteroids.size() == 0) {
            ++level;
            ++asteroidCount;
            ufoProbability *= UFO_PROBABILITY_CHANGE_RATE;
            generateAsteroids();
        }
        collideAsteroidAndUFO();
        collideAsteroidsAndSpaceship();
        collideBulletsAndAsteroids();
        collideUFOBulletsAndAsteroids();
        collideBulletsAndUFO();
        collideSpaceshipAndUFO();
        collideUfoBulletsAndSpaceship();
    }

    private void collideAsteroidsAndSpaceship() {
        for (Map.Entry<Integer,Asteroid> entry : asteroids.entrySet()) {
            Asteroid asteroid = entry.getValue();
            if (this.spaceship.collides(asteroid.position, asteroid.getRadius())) {
                hitAsteroid(asteroid);
                break;
            }
            asteroid.updatePosition();
        }
    }

    private void collideBulletsAndAsteroids() {
        for (Bullet bullet : bullets) {
            for (Map.Entry<Integer,Asteroid> entry : asteroids.entrySet()) {
                Asteroid asteroid = entry.getValue();
                if (asteroid.isHit(bullet.position)) {
                    bullet.destroy();
                    points += hitAsteroid(asteroid);
                    break;
                }
            }
            bullet.updatePosition();
        }
        bullets.removeIf(Bullet::isBurnedOut);
    }

    private void collideAsteroidAndUFO() {
        if (ufo == null) return;
        for (Map.Entry<Integer,Asteroid> entry : asteroids.entrySet()) {
            Asteroid asteroid = entry.getValue();
            if (this.ufo.collides(asteroid.position, asteroid.getRadius())) {
                hitAsteroid(asteroid);
                ufo = null;
                break;
            }
        }
    }

    private void collideSpaceshipAndUFO() {
        if (ufo == null) return;
        if (this.spaceship.collides(this.ufo.position, 25)) {
            ufo = null;
        }
    }

    private void collideBulletsAndUFO() {
        if (ufo == null) return;
        for (Bullet bullet : bullets) {
            if (ufo.collides(bullet.position, 1)) {
                bullet.destroy();
                points += ufo.pointsScored();
                ufo = null;
                break;
            }
        }
        bullets.removeIf(Bullet::isBurnedOut);
    }

    private void collideUfoBulletsAndSpaceship() {
        for (Bullet bullet : ufoBullets) {
            if (spaceship.collides(bullet.position, 1)) {
                bullet.destroy();
                break;
            }
        }
        bullets.removeIf(Bullet::isBurnedOut);
    }

    private void collideUFOBulletsAndAsteroids() {
        for (Bullet bullet : ufoBullets) {
            for (Map.Entry<Integer,Asteroid> entry : asteroids.entrySet()) {
                Asteroid asteroid = entry.getValue();
                if (asteroid.isHit(bullet.position)) {
                    bullet.destroy();
                    hitAsteroid(asteroid);
                    break;
                }
            }
            bullet.updatePosition();
        }
        ufoBullets.removeIf(Bullet::isBurnedOut);
    }

    private int hitAsteroid(Asteroid asteroid) {
        List<Asteroid> children = asteroid.hit();
        for (Asteroid child : children) asteroids.put(child.getId(), child);
        asteroids.remove(asteroid.getId());
        return asteroid.pointsScored();
    }

    public List<double[]> getBulletsCoords() {
        List<double[]> coords = new LinkedList<>();
        for (Bullet bullet : bullets) {
            double[] pair = new double[2];
            pair[0] = bullet.getX();
            pair[1] = bullet.getY();
            coords.add(pair);
        }
        for (Bullet bullet : ufoBullets) {
            double[] pair = new double[2];
            pair[0] = bullet.getX();
            pair[1] = bullet.getY();
            coords.add(pair);
        }
        return coords;
    }

    public double[] getAsteroidCoords(int id) {
        Asteroid asteroid = asteroids.get(id);
        double[] coords = new double[2];
        coords[0] = asteroid.getX();
        coords[1] = asteroid.getY();

        return coords;
    }

    public int getPoints() {
        return points;
    }

    public int getLevel() {
        return level;
    }

    public double[] getShipXShapeCoords() {
        return spaceship.getXShapeCoords();
    }

    public double[] getShipYShapeCoords() {
        return spaceship.getYShapeCoords();
    }

    public boolean updateUFOPosition() {
        if (ufo != null) {
            ufo.updatePosition();
            Bullet nextBullet = ufo.shoot();
            if (nextBullet != null) {
                ufoBullets.add(nextBullet);
            }
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

    public void startRotatingSpaceshipRight() {
        spaceship.startRotatingRight();
    }

    public void stopRotatingSpaceshipRight() {
        spaceship.stopRotatingRight();
    }

    public void startRotatingSpaceshipLeft() {
        spaceship.startRotatingLeft();
    }

    public void stopRotatingSpaceshipLeft() {
        spaceship.stopRotatingLeft();
    }

    public double getSpaceshipRotation() {
        return spaceship.getDirection();
    }

    public double getSpaceshipX() {
        return spaceship.getX();
    }

    public double getSpaceshipY() {
        return spaceship.getY();
    }

    public void boostSpaceship() {
        spaceship.startThrusting();
    }

    public void stopBoostingSpaceship() {
        spaceship.stopThrusting();
    }

    public void updateSpaceship() {
        spaceship.updatePosition();
        Bullet nextBullet = spaceship.shoot();
        if (nextBullet != null) {
            bullets.add(nextBullet);
        }
    }

    public void startShooting() {
        spaceship.startShooting();
    }

    public void stopShooting() {
        spaceship.stopShooting();
    }

    public void teleportSpaceship() {
        spaceship.teleport();
    }

    public int getLives() {
        int lives = spaceship.getLives();
        if (lives < 0) {
            startNewGame();
            lives = spaceship.getLives();
        }
        return lives;
    }

    public List<double[]> getAsteroidShape(int id) {
        return asteroids.get(id).getVertices();
    }

    public Set<Integer> getAsteroidIds() {
        return asteroids.keySet();
    }

    public char getUFOSize() {
        if (ufo == null) return '-';
        return ufo.getSize();
    }
}
