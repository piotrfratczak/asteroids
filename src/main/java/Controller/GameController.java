package Controller;

import Model.Asteroid;
import Model.Spaceship;
import View.Display;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    public static final String TITLE = "Asteroids";
    public static int SPACE_WIDTH = 1000;
    public static int SPACE_HEIGHT = 1000;

    public static int RIGHT_BOUND  = 500;
    public static int TOP_BOUND    = 500;
    public static int LEFT_BOUND   = -500;
    public static int BOTTOM_BOUND = -500;

    private static Display display;
    private static Level level;

    private static Spaceship spaceship;

    public GameController() {
        System.out.println(this.getClass().getCanonicalName());

        display = new Display();
//        level   = new Level();

        spaceship = new Spaceship();
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
        spaceship.update();
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
