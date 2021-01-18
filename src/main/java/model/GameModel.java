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

    private final int LIVES = 3;
    private final int INITIAL_ASTEROID_COUNT = 3;
    private final double INITAL_UFO_PROBABILITY = 0.0001;
    private final double UFO_PROBABILITY_CHANGE_RATE = 1.01;
    private final double STAR_PROBABILITY = 0.01;

    private int level;
    private int points;
    private int lives;
    private boolean enhancedMode;
    private Star star;
    private int asteroidCount;
    private Map<Integer, Asteroid> asteroids;
    private List<Bullet> bullets;
    private List<Bullet> ufoBullets;
    private double ufoProbability;
    private UFO ufo;
    private Spaceship spaceship;


    public GameModel() {}

    public void startNewGame(boolean enhancedMode) {
        this.lives = LIVES;
        this.level = 1;
        this.points = 0;
        this.enhancedMode = enhancedMode;
        this.ufoProbability = INITAL_UFO_PROBABILITY;
        this.asteroidCount = INITIAL_ASTEROID_COUNT;
        this.spaceship = new Spaceship();
        generateAsteroids();
    }

    private void generateAsteroids() {
        this.asteroids = new HashMap<>();
        this.bullets = new LinkedList<>();
        this.ufoBullets = new LinkedList<>();
        for (int i=0; i<asteroidCount; ++i) {
            Asteroid asteroid = new Asteroid(AsteroidSize.LARGE);
            Integer id = asteroid.getId();
            asteroids.put(id, asteroid);
        }
    }

    public boolean isOver() {
        return lives <= 0;
    }

    public void update() {
        if (lives <= 0) return;
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
        updateUFO();
        updateSpaceship();
        updateStar();
    }

    private void updateStar() {
        if (star == null) return;
        star.updatePosition();
        if (spaceship.collides(star.position, 40)) {
            spaceship.activateShield();
            star = null;
        }
        else if (!star.isTwinkling()) star = null;
    }

    private void updateSpaceship() {
        spaceship.updatePosition();
        if (enhancedMode && level % 5 == 0 ) spaceship.upgradeGun();
        Bullet nextBullet = spaceship.shoot();
        if (nextBullet != null) {
            bullets.add(nextBullet);
        }
    }

    private void updateUFO() {
        if (ufo == null) {
            if (Math.random() < ufoProbability) {
                ufo = new UFO();
            }
            else return;
        }
        ufo.updatePosition();
        Bullet nextBullet = ufo.shoot();
        if (nextBullet != null) {
            ufoBullets.add(nextBullet);
        }
    }

    private void collideAsteroidsAndSpaceship() {
        for (Map.Entry<Integer,Asteroid> entry : asteroids.entrySet()) {
            Asteroid asteroid = entry.getValue();
            if (this.spaceship.collides(asteroid.position, asteroid.getRadius())) {
                if (!spaceship.hasShield()) {
                    --lives;
                    spaceship.restart();
                }
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
                    if (this.enhancedMode && star == null && Math.random() < STAR_PROBABILITY) {
                        star = new Star(asteroid);
                    }
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
            if (!spaceship.hasShield()) {
                --lives;
                spaceship.restart();
            }
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
                if (!spaceship.hasShield()) {
                    --lives;
                    spaceship.restart();
                }
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

    public double getUFOPositionX() {
        if (ufo != null) {
            return ufo.getX();
        } else {
            return SPACE_WIDTH;
        }
    }

    public double getUFOPositionY() {
        if (ufo != null) {
            return ufo.getY();
        } else {
            return SPACE_HEIGHT;
        }
    }

    public double getStarPositionX() {
        if (star != null) {
            return star.getX();
        } else {
            return SPACE_WIDTH;
        }
    }

    public double getStarPositionY() {
        if (star != null) {
            return star.getY();
        } else {
            return SPACE_HEIGHT;
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

    public boolean flyingUFO() {
        return this.ufo != null;
    }

    public boolean twinklingStar() {
        return this.star != null;
    }

    public boolean spaceshipHasShield() {
        return spaceship.hasShield();
    }
}
