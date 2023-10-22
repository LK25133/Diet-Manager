package persistence;


import model.Food;
import model.Meal;
import model.MealPlan;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MealPlan mp = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyMealPlan() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMealPlan.json");
        try {
            MealPlan mp = reader.read();
            assertEquals(0, mp.getDailyCalories());
            assertEquals(0, mp.getMeals().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralMealPlan() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMealPlan.json");
        try {
            MealPlan mp = reader.read();
            assertEquals(462, mp.getDailyCalories());
            List<Meal> meals = mp.getMeals();
            assertEquals(2, meals.size());
            List<Food> foods = meals.get(0).getFoodList();
            checkFood("chicken", 1.5,100,foods.get(0));
            checkFood("egg", 1.0,100,foods.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
