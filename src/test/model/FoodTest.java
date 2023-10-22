package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FoodTest {
    private Food food1;
    private Food food2;

    @BeforeEach
    void runBefore() {
        food1 = new Food("Chicken", 150, 100);
        food2 = new Food("Water", -1, -1);
    }

    @Test
    void testConstructor() {
        assertEquals("Chicken", food1.getFoodname());
        assertEquals(1.5, food1.getCalorie());
        assertEquals(100, food1.getWeight());
    }

    @Test
    void testInvalidConstructor() {
        assertEquals("Water", food2.getFoodname());
        assertEquals(0, food2.getCalorie());
        assertEquals(0, food2.getWeight());
    }




}
