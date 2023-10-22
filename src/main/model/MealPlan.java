package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//Represent a day's meal plan that either in the past or future,
//          which contains a list of meals and total calorie of this day
public class MealPlan implements Writable {
    private List<Meal> meals;   //list of meals of this day
    private int dailyCalories;  //total calories of this day

    /*
     * EFFECTS: construct a daily meal plan with empty list of meals and 0 total calories
     */
    public MealPlan() {
        this.meals = new ArrayList<>();
        this.dailyCalories = 0;
    }

    /*
     * MODIFIES: this
     * EFFECTS: add a meal for the meal plan of a day and calculate total calories
     */
    public void addMeal(Meal meal) {
        meals.add(meal);
        dailyCalories += meal.getTotalCalories();
        EventLog.getInstance().logEvent(new Event("Meal added to meal plan"
                + "\nFood: " + printNames(meal)
                + "\nCalories: " + meal.getTotalCalories()));
    }

    /*
     * EFFECTS: print names
     */
    private String printNames(Meal meal) {
        String names = "";
        for (String name : meal.getNameList()) {
            names += name + ", ";
        }
        return names;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public int getDailyCalories() {
        return dailyCalories;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("daily calories", dailyCalories);
        json.put("Meals", mealsToJson());
        EventLog.getInstance().logEvent(new Event("Meal plan saved"));
        return json;
    }

    // EFFECTS: returns meals in this meal plan as a JSON array
    private JSONArray mealsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Meal m : meals) {
            jsonArray.put(m.toJson());
        }

        return jsonArray;
    }
}
