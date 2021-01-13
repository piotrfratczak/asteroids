package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum UFOSize {
    LARGE, SMALL;

    private static final List<UFOSize> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static UFOSize randomSize()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
