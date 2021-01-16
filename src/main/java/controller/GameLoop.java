package controller;

enum GameStatus {
    RUNNING, STOPPED
}

public class GameLoop {

    private Thread gameThread;
    private volatile GameStatus status;

    public GameLoop() {
        status = GameStatus.STOPPED;
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
        //TODO: process input
        //TODO: log exceptions
        try {
            Thread.sleep(4);
        } catch (InterruptedException e) {

        }
        // TODO: better timer
    }

    private void render() {
        GameController.render();
    }

    private void update() {
        if (GameController.isGameOver()) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {

            }
            GameController.startNewGame();
        }

        GameController.update();
    }

}
