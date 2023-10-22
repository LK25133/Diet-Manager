package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//Represents a entire meal with all the foods in a list, their names in another list,
//          and the total caloties of this meal
public class Meal implements Writable {
    private List<Food> foodList;    //the list of all the foods consumed in a meal
    private List<String> nameList;  //the list of names of all the foods
    private int totalCalories;      //total calories consumed in a meal

    /*
     * EFFECTS: Create a Meal with empty food list and name list; the total calorie is set to 0
     */
    public Meal() {
        this.foodList = new ArrayList<>();
        this.totalCalories = 0;
        this.nameList = new ArrayList<>();
    }

    /*
     * MODIFIES: this
     * EFFECTS: add a kind of food into the food list and its name to name list; and calculate total calories
     */
    public void addFood(Food food) {
        foodList.add(food);
        totalCalories += (food.getCalorie() * food.getWeight());
        nameList.add(food.getFoodname());
    }

    public List<Food> getFoodList() {
        return this.foodList;
    }

    public int getTotalCalories() {
        return this.totalCalories;
    }

    public List<String> getNameList() {
        return nameList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("meal calories", totalCalories);
        json.put("foods", foodsToJson());
        return json;
    }

    // EFFECTS: returns foods in this meal as a JSON array
    private JSONArray foodsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Food food : foodList) {
            jsonArray.put(food.toJson());
        }

        return jsonArray;
    }
}
