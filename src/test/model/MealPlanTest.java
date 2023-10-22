package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MealPlanTest {
    private MealPlan mealPlan1;
    private Meal meal1;
    private Meal meal2;

    @BeforeEach
    void runBefore() {
        mealPlan1 = new MealPlan();
        meal1 = new Meal();
        meal2 = new Meal();
        Food food1 = new Food("Chicken", 150, 100);
        Food food2 = new Food("Egg", 100, 60);

        meal1.addFood(food1);
        meal2.addFood(food1);
        meal2.addFood(food2);
    }

    @Test
    void testConstructor() {
        assertTrue(mealPlan1.getMeals().isEmpty());
        assertEquals(0, mealPlan1.getDailyCalories());
    }

    @Test
    void testAddMealOneTime() {
        mealPlan1.addMeal(meal1);

        assertEquals(1, mealPlan1.getMeals().size());
        assertEquals(meal1, mealPlan1.getMeals().get(0));
        assertEquals(150, mealPlan1.getDailyCalories());
    }

    @Test
    void testAddMealManyTimes() {
        mealPlan1.addMeal(meal1);
        mealPlan1.addMeal(meal2);

        assertEquals(2, mealPlan1.getMeals().size());
        assertEquals(meal1, mealPlan1.getMeals().get(0));
        assertEquals(meal2, mealPlan1.getMeals().get(1));
        assertEquals(360, mealPlan1.getDailyCalories());
    }

}
