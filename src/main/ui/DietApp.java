package ui;

import model.Food;
import model.Meal;
import model.MealPlan;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

//The console based interface part is significantly helped by the Teller application provided in edx
//                                               (https://github.students.cs.ubc.ca/CPSC210/TellerApp)


//Represent the entire diet management application
public class DietApp {
    private MealPlan records;   //the meal records of the past
    private MealPlan plan;      //the meal plan of the future
    private Scanner input;      //used to collect input from user

    private static final String JSON_STORE_RECORD = "./data/record.json";
    private static final String JSON_STORE_PLAN = "./data/plan.json";

    private JsonWriter jsonWriterRecord;
    private JsonReader jsonReaderRecord;
    private JsonWriter jsonWriterPlan;
    private JsonReader jsonReaderPlan;

    // EFFECTS: runs the diet manager application
    public DietApp() throws FileNotFoundException {
        jsonWriterRecord = new JsonWriter(JSON_STORE_RECORD);
        jsonReaderRecord = new JsonReader(JSON_STORE_RECORD);
        jsonWriterPlan = new JsonWriter(JSON_STORE_PLAN);
        jsonReaderPlan = new JsonReader(JSON_STORE_PLAN);
        runDietManager();
    }

    // MODIFIES: this
    // EFFECTS: processes user inputs
    private void runDietManager() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nKeep on keeping up!");
    }

    // MODIFIES: this
    // EFFECTS: initializes meal plans
    private void init() {
        records = new MealPlan();
        plan = new MealPlan();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add meal to plans");
        System.out.println("\tv -> view meal plans");
        System.out.println("\tf -> finish meals");
        System.out.println("\ts -> save meal plans to file");
        System.out.println("\tl -> load meal plans from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            MealPlan chosenPlan = selectPlan();
            doAddMeal(chosenPlan);
        } else if (command.equals("v")) {
            MealPlan chosenPlan = selectPlan();
            doViewPlans(chosenPlan);
        } else if (command.equals("s")) {
            saveMealPlan();
        } else if (command.equals("l")) {
            loadMealPlan();
        } else if (command.equals("f")) {
            doFinishMeal(plan);
        } else {
            System.out.println("Selection not valid...");
        }
    }


    /*
     * MODIFIES: this
     * EFFECTS: add the meal for the meal plan that user selected
     */
    private void doAddMeal(MealPlan plan) {
        boolean keepAdding = true;
        Meal addedMeal = new Meal();

        while (keepAdding) {
            System.out.print("What's the name of the food material? ");
            String name = input.next();

            System.out.print("What's the material's calorie per 100 grams");
            int calories = input.nextInt();

            System.out.print("What's the weight of material?");
            int weight = input.nextInt();

            if ((calories <= 0) || (weight <= 0)) {
                System.out.println("Can not add entropy decreased food");
            } else {
                System.out.println("Do you have more food to add? Yes or Not");
                String command = input.next();
                command = command.toLowerCase();
                if (command.equals("not")) {
                    keepAdding = false;
                }
                addedMeal.addFood(new Food(name, calories, weight));
            }

        }

        plan.addMeal(addedMeal);
        printMeals(plan.getMeals());

    }

    /*
     * MODIFIES: this
     * EFFECTS: finish one of the meals from the future meal plan, move the finished meal to records
     */
    private void doFinishMeal(MealPlan plan) {
        List<Meal> meals = plan.getMeals();
        if (meals.isEmpty()) {
            System.out.println("There is no meal in the plan");
        } else {
            printMeals(meals);
            System.out.println("Which Meal is finished? Type in the number of the meal");
            int num = input.nextInt();

            Meal finishedMeal = meals.get(num - 1);
            meals.remove(finishedMeal);
            records.addMeal(finishedMeal);
            System.out.println("Meal has been finished");
        }
    }

    /*
     * EFFECTS: print the details of the meal plan that selected by user
     */
    private void doViewPlans(MealPlan plan) {
        List<Meal> meals = plan.getMeals();
        printMeals(meals);
    }

    /*
     * EFFECTS: print all the meals one by one to the screen
     */
    private void printMeals(List<Meal> meals) {
        int totalCalories = 0;
        if (meals.isEmpty()) {
            System.out.println("There is no meal in this plan");
        } else {
            for (Meal meal : meals) {
                int mealNum = meals.indexOf(meal);
                System.out.println("Meal " + (mealNum + 1) + "----Food: "
                        + printFoodNames(meal.getNameList()) + "total calories is " + meal.getTotalCalories());

                totalCalories += meal.getTotalCalories();
            }
            System.out.println("Total calories of this day is " + totalCalories);
        }
    }

    /*
     * EFFECTS: print all the names of food in one meal
     */
    private String printFoodNames(List<String> namelist) {
        String returned = "";
        for (String name : namelist) {
            returned = returned + name + ", ";
        }

        return returned;
    }

    // EFFECTS: let users select the meal plan that they wish to adjust or view
    private MealPlan selectPlan() {
        String selection = "";


        while (!(selection.equals("r") || selection.equals("p"))) {
            System.out.println("Which plan do you want to look at?");
            System.out.println("r for past meal records");
            System.out.println("p for future meal plans");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("r")) {
            return records;
        } else {
            return plan;
        }
    }


    // EFFECTS: saves the record and plan in different files
    private void saveMealPlan() {
        try {
            jsonWriterRecord.open();
            jsonWriterRecord.write(records);
            jsonWriterRecord.close();

            jsonWriterPlan.open();
            jsonWriterPlan.write(plan);
            jsonWriterPlan.close();
            System.out.println("Saved records and plans to " + JSON_STORE_RECORD);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_RECORD);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads record and plan from file
    private void loadMealPlan() {
        try {
            records = jsonReaderRecord.read();
            plan = jsonReaderPlan.read();
            System.out.println("Loaded records and plans from " + JSON_STORE_RECORD + " and " + JSON_STORE_PLAN);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_RECORD + " and " + JSON_STORE_PLAN);
        }
    }


}
