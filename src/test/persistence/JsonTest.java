package persistence;

import model.Food;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkFood(String name, Double calorie, int weight, Food food) {
        assertEquals(name, food.getFoodname());
        assertEquals(calorie, food.getCalorie());
        assertEquals(weight, food.getWeight());

    }
}
