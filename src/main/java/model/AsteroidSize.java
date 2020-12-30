package model;

public enum AsteroidSize {
    LARGE(50), MEDIUM(35), SMALL(20);

    private final int value;
    private AsteroidSize(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
