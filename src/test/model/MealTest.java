package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MealTest {
    private Meal meal1;
    private Food food1;
    private Food food2;

    @BeforeEach
    void runBefore() {
        meal1 = new Meal();
        food1 = new Food("Chicken", 150, 100);
        food2 = new Food("Egg", 100, 60);
    }

    @Test
    void testConstructor() {
        assertTrue(meal1.getFoodList().isEmpty());
        assertEquals(0, meal1.getTotalCalories());
        assertTrue(meal1.getNameList().isEmpty());
    }

    @Test
    void testAddFoodOneTime() {
        meal1.addFood(food1);

        assertEquals(1, meal1.getFoodList().size());
        assertEquals(food1, meal1.getFoodList().get(0));
        assertEquals(1, meal1.getNameList().size());
        assertEquals("Chicken", meal1.getNameList().get(0));
        assertEquals(150, meal1.getTotalCalories());
    }

    @Test
    void testAddFoodManyTimes() {
        meal1.addFood(food1);
        meal1.addFood(food2);

        assertEquals(2, meal1.getFoodList().size());
        assertEquals(food1, meal1.getFoodList().get(0));
        assertEquals(food2, meal1.getFoodList().get(1));
        assertEquals(2, meal1.getNameList().size());
        assertEquals("Chicken", meal1.getNameList().get(0));
        assertEquals("Egg", meal1.getNameList().get(1));
        assertEquals(210, meal1.getTotalCalories());
    }



}