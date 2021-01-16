import model.Asteroid;
import model.AsteroidSize;
import model.Vector;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AsteroidTest {

    @Test
    void getId() {
        Asteroid asteroid = new Asteroid(AsteroidSize.LARGE);
        Assert.assertEquals(1, asteroid.getId());
    }

    @Test
    void isHit() {
        Asteroid asteroid = new Asteroid(AsteroidSize.LARGE);
        double x = asteroid.getX();
        double y = asteroid.getY();
        boolean result = asteroid.isHit(new Vector(x,y));
        Assert.assertTrue(result);
    }

    @Test
    void getVertices() {
        Asteroid asteroid = new Asteroid(AsteroidSize.LARGE);
        Assert.assertNotEquals(null, asteroid.getVertices());
    }


}

// TODO: add tests