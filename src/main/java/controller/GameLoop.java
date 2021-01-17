package controller;

enum GameStatus {
    RUNNING, STOPPED
}

public class GameLoop {

    private Thread gameThread;
    private volatile GameStatus status;

    private boolean gameStopped;

    public GameLoop() {
        status = GameStatus.STOPPED;
        gameStopped = false;
        new GameController();
    }

    public void start() {
        status = GameStatus.RUNNING;
        gameThread = new Thread(this::processGameLoop);
        gameThread.start();
    }

    public void stop() {
        status = GameStatus.STOPPED;
    }

    public boolean isRunning() {
        return status == GameStatus.RUNNING;
    }

    private void processGameLoop() {
        while (isRunning()) {
                processInput();
                update();
                render();
        }
    }

    private void processInput() {
        if (GameController.isQuitting()) stop();
        try {
            Thread.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // TODO: better timer

        if (GameController.isGameOver() && GameController.isInGame()) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GameController.goToMenu();
        }

        if (GameController.isStartingGame()) {
            GameController.executeNewGame();
        }
    }

    private void render() {
        GameController.render();
    }

    private void update() {
        GameController.update();
    }

}
