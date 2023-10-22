package persistence;

import model.Food;
import model.Meal;
import model.MealPlan;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// Represents a reader that reads meal plan from JSON data stored in file

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads meal plan from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MealPlan read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMealPlan(jsonObject);
    }




    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses meal plan from JSON object and returns it
    private MealPlan parseMealPlan(JSONObject jsonObject) {
        MealPlan mp = new MealPlan();
        addMeals(mp, jsonObject);
        return mp;
    }

    // MODIFIES: mp
    // EFFECTS: parses meals from JSON object and adds them to the meal plan
    private void addMeals(MealPlan mp, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Meals");
        for (Object json : jsonArray) {
            Meal meal = new Meal();
            JSONObject nextMeal = (JSONObject) json;
            addFoods(meal, nextMeal);
            mp.addMeal(meal);
        }
    }

    // MODIFIES: meal
    // EFFECTS: parses foods from JSON object and adds it to the meal
    private void addFoods(Meal meal, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("foods");
        for (Object json : jsonArray) {

            JSONObject nextFood = (JSONObject) json;
            addFood(meal, nextFood);

        }
    }

    // MODIFIES: meal
    // EFFECTS: parses a food from JSON object and adds it to meal
    private void addFood(Meal meal, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double calorie = jsonObject.getDouble("calorie");
        int weight = jsonObject.getInt("weight");

        Food food = new Food(name, calorie, weight);
        meal.addFood(food);
    }
}
