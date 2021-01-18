import model.Spaceship;
import model.Vector;
import model.guns.Bullet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpaceshipTest {

    Spaceship tested;

    @BeforeEach
    void initialize() {
        tested = new Spaceship();
    }

    @Test
    void shoot() {
        tested.stopShooting();
        Bullet bullet1 = tested.shoot();
        assertNull(bullet1);
        tested.startShooting();
        Bullet bullet2 = tested.shoot();
        assertNotNull(bullet2);
    }

    @Test
    void updatePosition() {
        tested.stopRotatingLeft();
        tested.stopRotatingRight();
        double rotation1 = tested.getDirection();
        tested.startRotatingLeft();
        tested.updatePosition();
        double rotation2 = tested.getDirection();
        assertNotEquals(rotation2, rotation1);

        tested.stopRotatingLeft();
        tested.startRotatingRight();
        tested.updatePosition();
        double rotation3 = tested.getDirection();
        assertEquals(rotation1, rotation3);
    }

    @Test
    void teleport() {
        double x1 = tested.getX();
        double y1 = tested.getY();
        tested.teleport();
        double x2 = tested.getX();
        double y2 = tested.getY();
        assertNotEquals(x1, x2);
        assertNotEquals(y1, y2);
    }

    @Test
    void collides() {
        Vector v = new Vector(tested.getX(), tested.getY());
        assertTrue(tested.collides(v, 1));
    }

    @Test
    void restart() {
        tested.teleport();
        tested.restart();
        double x = tested.getX();
        double y = tested.getY();
        assertEquals(0, x);
        assertEquals(0, y);
    }

    @Test
    void upgradeGun() {
        tested.startShooting();
        tested.shoot();
        Bullet bullet1 = tested.shoot();
        assertNull(bullet1);
        tested.stopShooting();

        tested.upgradeGun();
        tested.upgradeGun();
        tested.startShooting();
        tested.shoot();
        Bullet bullet2 = tested.shoot();
        assertNotNull(bullet2);
    }
}