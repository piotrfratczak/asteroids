import model.Asteroid;
import model.AsteroidSize;
import model.Star;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StarTest {

    @Test
    void isTwinkling() {
        Star tested = new Star(new Asteroid(AsteroidSize.LARGE));
        for (int i=0; i<1000; ++i) {
            assertTrue(tested.isTwinkling());
            tested.updatePosition();
        }
        assertFalse(tested.isTwinkling());
    }
}