import model.Asteroid;
import model.AsteroidSize;
import model.Vector;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class AsteroidTest {

    @Test
    @Order(2)
    void getId() {
        Asteroid asteroid = new Asteroid(AsteroidSize.LARGE);
        assertEquals(2, asteroid.getId());
    }

    @Test
    void isHit() {
        Asteroid asteroid = new Asteroid(AsteroidSize.LARGE);
        double x = asteroid.getX();
        double y = asteroid.getY();
        boolean result = asteroid.isHit(new Vector(x,y));
        assertTrue(result);
    }

    @Test
    void getVertices() {
        Asteroid asteroid = new Asteroid(AsteroidSize.LARGE);
        assertNotEquals(null, asteroid.getVertices());
    }

    @Test
    void pointsScored() {
        Asteroid asteroid = new Asteroid(AsteroidSize.LARGE);
        assertEquals(50, asteroid.pointsScored());
    }


}