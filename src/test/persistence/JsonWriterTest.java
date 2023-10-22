package persistence;

import model.Food;
import model.Meal;
import model.MealPlan;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {

            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyMealPlan() {
        try {
            MealPlan mp = new MealPlan();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMealPlan.json");
            writer.open();
            writer.write(mp);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMealPlan.json");
            mp = reader.read();
            assertEquals(0, mp.getDailyCalories());
            assertEquals(0, mp.getMeals().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMealPlan() {
        try {
            MealPlan mp = new MealPlan();
            Food food1 = new Food("chicken",150,100);
            Food food2 = new Food("egg",100,100);
            Food food3 = new Food("beef",200,100);
            Food food4 = new Food("water",25,50);
            Meal meal1 = new Meal();
            Meal meal2 = new Meal();
            meal1.addFood(food1);
            meal1.addFood(food2);
            meal2.addFood(food3);
            meal2.addFood(food4);
            mp.addMeal(meal1);
            mp.addMeal(meal2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMealPlan.json");
            writer.open();
            writer.write(mp);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMealPlan.json");
            mp = reader.read();
            assertEquals(462, mp.getDailyCalories());
            List<Meal> meals = mp.getMeals();
            assertEquals(2, meals.size());
            List<Food> foods = meals.get(0).getFoodList();
            checkFood("chicken", 1.5,100,foods.get(0));
            checkFood("egg", 1.0,100,foods.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
