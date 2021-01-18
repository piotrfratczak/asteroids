import model.UFO;
import model.Vector;
import model.guns.Bullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UFOTest {

    private UFO tested;

    @BeforeEach
    void initialize() {
        tested = new UFO();
    }

    @Test
    void shoot() {
        Bullet bullet = tested.shoot();
        assertNotNull(bullet);
    }

    @Test
    void collides() {
        Vector v = new Vector(tested.getX(), tested.getY());
        assertTrue(tested.collides(v, 1));
    }

    @Test
    void updatePosition() {
        double x1 = tested.getX();
        double y1 = tested.getY();
        tested.updatePosition();
        double x2 = tested.getX();
        double y2 = tested.getY();
        assertNotEquals(x1, x2);
        assertNotEquals(y1, y2);
    }

    @Test
    void pointsScored() {
        assertTrue(tested.pointsScored() > 0);
    }
}