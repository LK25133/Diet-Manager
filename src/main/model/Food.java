package model;

import org.json.JSONObject;
import persistence.Writable;

//Represent a food material with a name, calorie per gram, and total weight that consumed in a Meal
public class Food implements Writable {

    private String foodname; //The name of this food material
    private double calorie;  //The calorie per gram
    private int weight;      //Total weight of this food material

    /*
     * REQUIRES: calorie > 0, weight > 0
     * EFFECTS: Create a food material, the food's name is set by name; the input calorie is the calorie per 100 gram
     *          so has to be divided by 100 and stored as double; if both calorie and weight > 0 then they are set as
     *          usual, otherwise they are set as 0
     */
    public Food(String name, double calorie, int weight) {
        this.foodname = name;
        if ((calorie > 0) & (weight > 0)) {
            this.calorie = (double) calorie / 100;
            this.weight = weight;
        } else {
            this.calorie = 0;
            this.weight = 0;
        }

    }

    public String getFoodname() {
        return this.foodname;
    }

    public double getCalorie() {
        return this.calorie;
    }

    public int getWeight() {
        return this.weight;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", foodname);
        json.put("calorie", calorie * 100);
        json.put("weight", weight);
        return json;
    }
}

