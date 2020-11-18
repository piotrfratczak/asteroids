package Controller;

import Model.Asteroid;
import Model.Spaceship;
import View.Display;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public static final String TITLE = "Asteroids";

    private static Display display;
    private static Level level;

    public Game() {
        display = new Display();
        level   = new Level();
    }

    public static void main(String[] args) {

        Game game = new Game();

    }

    public static int getDisplayWidth() {
        return display.getWidth();
    }

    public static int getDisplayHeight() {
        return display.getHeight();
    }

    private static class Level {

        static int which;
        private static Spaceship spaceship;
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
