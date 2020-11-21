package Controller;

enum GameStatus {
    RUNNING, STOPPED
}

public class GameLoop {

    private GameController controller;
    private Thread gameThread;
    private volatile GameStatus status;

    public GameLoop() {

        controller = new GameController();
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
            Thread.sleep(10);
        } catch (InterruptedException e) {

        }
    }

    private void render() {
    }

    private void update() {
    }

}
