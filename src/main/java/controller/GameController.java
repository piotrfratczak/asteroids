package controller;

import model.*;
import view.Display;

import java.util.*;

public class GameController {

    private static GameModel game;
    private static Display display;

    private static boolean quiting = false;
    private static boolean startingGame = false;
    private static boolean nextModeEnhanced = false;
    private static boolean inGame = false;

    public GameController() {
        game = new GameModel();
        display = new Display();
    }

    public static void startNewGame(boolean enhancedMode) {
        startingGame = true;
        nextModeEnhanced = enhancedMode;
    }

    public static boolean isStartingGame() {
        return startingGame;
    }

    public static void executeNewGame() {
        startingGame = false;
        inGame = true;
        game.startNewGame(nextModeEnhanced);
        display.game();
    }

    public static void render() {
        display.repaint();
    }

    public static void goToMenu() {
        inGame = false;
        display.menu();
    }

    public static boolean isGameOver() {
        return game.isOver();
    }

    public static boolean isInGame() {
        return inGame;
    }

    public static void closeWindow() {
        quiting = true;
        display.dispose();
    }

    public static boolean isQuitting() {
        return quiting;
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
        return Spaceship.getXShapeCoords();
    }

    public static double[] getShipYShapeCoords() {
        return Spaceship.getYShapeCoords();
    }

    public static double[] getUFOLargeXShapeCoords() {
        return UFO.getLargeXShapeCoords();
    }

    public static double[] getUFOLargeYShapeCoords() {
        return UFO.getLargeYShapeCoords();
    }

    public static double[] getUFOSmallXShapeCoords() {
        return UFO.getSmallXShapeCoords();
    }

    public static double[] getUFOSmallYShapeCoords() {
        return UFO.getSmallYShapeCoords();
    }

    public static double[] getStarXShapeCoords() {
        return Star.getXShapeCoords();
    }

    public static double[] getStarYShapeCoords() {
        return Star.getYShapeCoords();
    }

    public static void startRotatingSpaceshipRight() {
        game.startRotatingSpaceshipRight();
    }

    public static void stopRotatingSpaceshipRight() {
        game.stopRotatingSpaceshipRight();
    }

    public static void startRotatingSpaceshipLeft() {
        game.startRotatingSpaceshipLeft();
    }

    public static void stopRotatingSpaceshipLeft() {
        game.stopRotatingSpaceshipLeft();
    }

    public static double getSpaceshipRotation() {
        return game.getSpaceshipRotation();
    }

    public static double getSpaceshipX() {
        return game.getSpaceshipX();
    }

    public static double getSpaceshipY() {
        return game.getSpaceshipY();
    }

    public static void boostSpaceship() {
        game.boostSpaceship();
    }

    public static void stopBoostingSpaceship() {
        game.stopBoostingSpaceship();
    }

    public static void startShooting() {
        game.startShooting();
    }

    public static void stopShooting() {
        game.stopShooting();
    }

    public static void teleportSpaceship() {
        game.teleportSpaceship();
    }

    public static void update() {
        game.update();
    }

    public static int getScore() {
        return game.getPoints();
    }

    public static int getLives() {
        return game.getLives();
    }

    public static int getLevel() {
        return game.getLevel();
    }

    public static List<double[]> getBulletsCoords() {
        return game.getBulletsCoords();
    }

    public static double[] getAsteroidCoords(int id) {
        return game.getAsteroidCoords(id);
    }

    public static List<double[]> getAsteroidShape(int id) {
        return game.getAsteroidShape(id);
    }

    public static Set<Integer> getAsteroidIds() {
        return game.getAsteroidIds();
    }

    public static double getUFOPositionX() {
        return game.getUFOPositionX();
    }

    public static double getUFOPositionY() {
        return game.getUFOPositionY();
    }

    public static double getStarPositionX() {
        return game.getStarPositionX();
    }

    public static double getStarPositionY() {
        return game.getStarPositionY();
    }

    public static char getUFOSize() {
        return game.getUFOSize();
    }

    public static boolean flyingUFO() {
        return game.flyingUFO();
    }

    public static boolean twinklingStar() {
        return game.twinklingStar();
    }

    public static boolean spaceshipHasShield() {
        return game.spaceshipHasShield();
    }
}
