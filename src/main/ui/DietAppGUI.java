package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

import model.Event;
import model.EventLog;
import model.Food;
import model.Meal;
import model.MealPlan;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/*The GUI design is helped by
    SimpleDrawingEditor in Edx: https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete.git
    TrafficLight on Edx: https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter
    ListDemo : https://docs.oracle.com/javase/tutorial/uiswing/examples/zipfiles/components-ListDemoProject.zip */

//represent the entire graphic user interface of diet management application
public class DietAppGUI extends JFrame implements ActionListener, ListSelectionListener {

    private MealPlan records;   //the meal records of the past
    private MealPlan plan;      //the meal plan of the future

    private static final String JSON_STORE_RECORD = "./data/record.json";
    private static final String JSON_STORE_PLAN = "./data/plan.json";

    private JsonWriter jsonWriterRecord;
    private JsonReader jsonReaderRecord;
    private JsonWriter jsonWriterPlan;
    private JsonReader jsonReaderPlan;

    private final JButton button1 = new JButton("Add meals");
    private final JButton button2 = new JButton("Finish meals");
    private final JButton button3 = new JButton("Save");
    private final JButton button4 = new JButton("Load");

    private JPanel welcomePanel;

    private JPanel menu;
    private JTextArea textArea;

    private JPanel addingPanel;
    private JPanel addingButtons;
    JTextArea nameText = new JTextArea();
    JTextArea calorieText = new JTextArea();
    JTextArea weightText = new JTextArea();
    private Meal addedMeal;

    private JPanel addToWherePanel;

    private JPanel finishPanel;
    private JList list;
    private DefaultListModel listModel;
    private JButton finishButton;

    /*
     * MODIFIES: this
     * EFFECTS: initialize and add all the panels will be used to the frame and display the welcome panel
     */
    public DietAppGUI() {
        super("Diet Management App");

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        });


        setSize(700, 700);

        init();
        initializeMenu();
        initializeAddingPanel();
        initializeAddToWherePanel();
        initializeFinishPanel();
        initializeWelcome();

        add(welcomePanel);

        setVisible(true);

    }



    /*
     * MODIFIES: this
     * EFFECTS: initializes meal plans and Json files location
     */
    private void init() {
        records = new MealPlan();
        plan = new MealPlan();
        jsonWriterRecord = new JsonWriter(JSON_STORE_RECORD);
        jsonReaderRecord = new JsonReader(JSON_STORE_RECORD);
        jsonWriterPlan = new JsonWriter(JSON_STORE_PLAN);
        jsonReaderPlan = new JsonReader(JSON_STORE_PLAN);

        addedMeal = new Meal();
    }

    /*
     * MODIFIES: this
     * EFFECTS: initialize all the components on the main menu
     */
    private void initializeMenu() {
        menu = new JPanel(new BorderLayout());

        textArea = new JTextArea();
        button1.setActionCommand("a");
        button2.setActionCommand("f");
        button3.setActionCommand("s");
        button4.setActionCommand("l");

        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);
        menu.add(textArea, BorderLayout.CENTER);
        menu.add(buttonPanel, BorderLayout.SOUTH);


    }

    /*
     * MODIFIES: this
     * EFFECTS: initialize all the components on the adding food panel
     */
    private void initializeAddingPanel() {
        addingPanel = new JPanel();
        JPanel foodInfoPanel = new JPanel(new GridLayout(0, 2, 8, 50));
        JLabel nameLabel = new JLabel("What's the name of the food material?");
        JLabel calorieLabel = new JLabel("What's the material's calorie per 100 grams?");
        JLabel weightLabel = new JLabel("What's the weight of material?");

        foodInfoPanel.add(nameLabel);
        foodInfoPanel.add(nameText);
        foodInfoPanel.add(calorieLabel);
        foodInfoPanel.add(calorieText);
        foodInfoPanel.add(weightLabel);
        foodInfoPanel.add(weightText);

        initializeAddingPanelButton();

        addingPanel.add(foodInfoPanel, BorderLayout.CENTER);
        addingPanel.add(addingButtons, BorderLayout.SOUTH);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initialize the buttons on the adding food panel
     */
    private void initializeAddingPanelButton() {

        JButton confirmButton = new JButton("add confirm");
        JButton backButton = new JButton("back to main menu");
        JButton addMoreButton = new JButton("add more");
        confirmButton.setActionCommand("add confirmed");
        backButton.setActionCommand("back to main menu");
        addMoreButton.setActionCommand("add more");
        confirmButton.addActionListener(this);
        backButton.addActionListener(this);
        addMoreButton.addActionListener(this);

        addingButtons = new JPanel();
        addingButtons.add(confirmButton);
        addingButtons.add(backButton);
        addingButtons.add(addMoreButton);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initialize the finish meal panel
     */
    private void initializeFinishPanel() {
        finishPanel = new JPanel(new GridLayout(0, 1));

        listModel = new DefaultListModel();

        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);


        JPanel finishButtonPanel = new JPanel();
        finishButton = new JButton("Finish");
        JButton backButton = new JButton("Back to main menu");
        finishButton.setActionCommand("finish");
        backButton.setActionCommand("back to main menu");
        finishButton.addActionListener(this);
        backButton.addActionListener(this);

        finishButtonPanel.add(finishButton);
        finishButtonPanel.add(backButton);

        finishPanel.add(listScrollPane);
        finishPanel.add(finishButtonPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initialize the panel that decide add the input food to which meal plan
     */
    private void initializeAddToWherePanel() {
        addToWherePanel = new JPanel(new GridLayout(0, 1));
        JLabel addToWhere = new JLabel("Add to record or plan?");
        JButton addToRecordButton = new JButton("Record");
        JButton addToPlanButton = new JButton("Plan");
        addToRecordButton.setActionCommand("add to record");
        addToPlanButton.setActionCommand("add to plan");
        addToRecordButton.addActionListener(e -> {
            records.addMeal(addedMeal);
            clearText();
            backToMenu();
        });
        addToPlanButton.addActionListener(e -> {
            plan.addMeal(addedMeal);
            clearText();
            backToMenu();
        });

        addToWherePanel.add(addToWhere);
        addToWherePanel.add(addToRecordButton);
        addToWherePanel.add(addToPlanButton);
    }


    /*
     * MODIFIES: this
     * EFFECTS: initialize the welcome panel at the beginning
     */
    private void initializeWelcome() {
        welcomePanel = new JPanel(new BorderLayout());
        JLabel welcomeImage = new JLabel(new ImageIcon("./data/welcomeImage.jpg"));
        JButton welcomeButton = new JButton("Welcome");
        welcomeButton.setActionCommand("welcome");
        welcomeButton.addActionListener(this);

        welcomePanel.add(welcomeImage, BorderLayout.CENTER);
        welcomePanel.add(welcomeButton, BorderLayout.SOUTH);
    }

    /*
     * EFFECTS: calls the relevant method when a specified button is pressed
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("a")) {
            addMeal();
        } else if (e.getActionCommand().equals("f")) {
            finishMeal();
        } else if (e.getActionCommand().equals("s")) {
            saveMealPlan();
        } else if (e.getActionCommand().equals("l")) {
            loadMealPlan();
        } else if (e.getActionCommand().equals("add confirmed")) {
            finishAdding(false);
        } else if (e.getActionCommand().equals("add more")) {
            finishAdding(true);
        } else if (e.getActionCommand().equals("finish")) {
            transferMeal();
        } else if (e.getActionCommand().equals("back to main menu")) {
            clearText();
            backToMenu();
        } else if (e.getActionCommand().equals("welcome")) {
            welcomePanel.setVisible(false);
            add(menu);
            doViewPlans(records);
            doViewPlans(plan);
        }
    }

    /*
     * EFFECTS: add the adding panel to the screen
     */
    private void addMeal() {
        add(addingPanel);
        addingPanel.setVisible(true);
        menu.setVisible(false);
        finishPanel.setVisible(false);
    }

    /*
     * MODIFIES: this
     * EFFECTS: collect user's input and add the collected food to a meal
     */
    private void finishAdding(Boolean keepAdding) {
        String name = nameText.getText();
        int calories = Integer.parseInt(calorieText.getText());
        int weight = Integer.parseInt(weightText.getText());
        addedMeal.addFood(new Food(name, calories, weight));

        if (keepAdding) {
            clearText();
        } else {
            addAddToWherePanel();
        }
    }

    /*
     * EFFECTS: add to where panel to the screen
     */
    private void addAddToWherePanel() {
        add(addToWherePanel);
        addToWherePanel.setVisible(true);
        addingPanel.setVisible(false);
        menu.setVisible(false);
    }

    /*
     * MODIFIES: this
     * EFFECTS: add the finish meal panel to screen
     */
    private void finishMeal() {
        add(finishPanel);
        listModel.clear();
        addElements(plan);
        finishPanel.setVisible(true);
        menu.setVisible(false);
    }

    /*
     * MODIFIES: this
     * EFFECTS: transfer the meal that user wants to finish from plan to record
     */
    private void transferMeal() {
        int index = list.getSelectedIndex();
        listModel.remove(index);

        List<Meal> meals = plan.getMeals();
        Meal finishedMeal = meals.get(index);
        meals.remove(finishedMeal);
        records.addMeal(finishedMeal);

        int size = listModel.getSize();

        if (size == 0) {
            finishButton.setEnabled(false);

        } else {
            if (index == listModel.getSize()) {
                index--;
            }

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: add the existing meals in the plan to a list
     */
    private void addElements(MealPlan addingPlan) {
        List<Meal> meals = addingPlan.getMeals();

        for (Meal meal : meals) {
            int mealNum = meals.indexOf(meal);
            String e = ("Meal " + (mealNum + 1) + "----Food: "
                    + printFoodNames(meal.getNameList()) + "total calories is " + meal.getTotalCalories());
            listModel.addElement(e);
        }
    }



    /*
     * MODIFIES: this
     * EFFECTS: clear all of the text areas for adding a food, then user can input data and add another food again
     */
    private void clearText() {
        nameText.setText("");
        calorieText.setText("");
        weightText.setText("");
    }

    /*
     * EFFECTS: close all of the panels except the menu, return user to the main menu and show the current meal plans
     */
    private void backToMenu() {
        textArea.setText("");
        addedMeal = new Meal();
        menu.setVisible(true);
        addingPanel.setVisible(false);
        addToWherePanel.setVisible(false);
        finishPanel.setVisible(false);
        welcomePanel.setVisible(false);
        doViewPlans(records);
        doViewPlans(plan);
    }

    /*
     * EFFECTS: print the details of the meal plan that selected by user
     */
    private void doViewPlans(MealPlan plan) {
        if (plan == records) {
            textArea.append("Your record:");
        } else if (plan == this.plan) {
            textArea.append("\n\nYour plan:");
        }
        List<Meal> meals = plan.getMeals();
        printMeals(meals);
    }

    /*
     * EFFECTS: print all the meals one by one to the screen
     */
    private void printMeals(List<Meal> meals) {
        int totalCalories = 0;
        if (meals.isEmpty()) {
            textArea.append("\nThere is no meal in this plan");
        } else {
            for (Meal meal : meals) {
                int mealNum = meals.indexOf(meal);
                textArea.append("\nMeal " + (mealNum + 1) + "----Food: "
                        + printFoodNames(meal.getNameList()) + "total calories is " + meal.getTotalCalories());

                totalCalories += meal.getTotalCalories();
            }
            textArea.append("\nTotal calories of this day is " + totalCalories);
        }
    }

    /*
     * EFFECTS: print all the names of food in one meal
     */
    private String printFoodNames(List<String> nameList) {
        String returned = "";
        for (String name : nameList) {
            returned = returned + name + ", ";
        }

        return returned;
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
            textArea.append("\nSaved records and plans to " + JSON_STORE_RECORD);
        } catch (FileNotFoundException e) {
            textArea.append("\nUnable to write to file: " + JSON_STORE_RECORD);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads record and plan from file
    private void loadMealPlan() {
        try {
            records = jsonReaderRecord.read();
            plan = jsonReaderPlan.read();
            textArea.append("\n\nLoaded records and plans from " + JSON_STORE_RECORD + " and " + JSON_STORE_PLAN);
            textArea.append("\n\n");
            doViewPlans(records);
            doViewPlans(plan);
        } catch (IOException e) {
            textArea.append("\nUnable to read from file: " + JSON_STORE_RECORD + " and " + JSON_STORE_PLAN);
        }
    }

    /*
     * EFFECTS: print events in the event log to the console
     */
    private void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println("\n" + next.toString());
        }
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {}
}
