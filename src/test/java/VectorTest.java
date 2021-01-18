import model.Vector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    private Vector tested;

    @BeforeEach
    void initialize() {
        tested = new Vector(1, 2);
    }

    @Test
    @Order(1)
    void getX() {
        assertEquals(1, tested.getX());
    }

    @Test
    @Order(2)
    void getY() {
        assertEquals(2, tested.getY());
    }

    @Test
    void setX() {
        tested.setX(5);
        assertEquals(5, tested.getX());
    }

    @Test
    void setY() {
        tested.setY(10);
        assertEquals(10, tested.getY());
    }

    @Test
    void add() {
        Vector another = new Vector(5, 6);
        tested.add(another);
        assertEquals(1+5, tested.getX());
        assertEquals(2+6, tested.getY());
    }

    @Test
    void multiplyBy() {
        tested.multiplyBy(2);
        assertEquals(2*1, tested.getX());
        assertEquals(2*2, tested.getY());
    }
}