# Diet meal manager 

## Meal plan, records and calories calculation  

<p>This application is able to track the food that user ate and calculate the calories and nutrition they had everyday.
User can also use it to plan the meal in the future. The records and plans can be adjusted by changes as
it is hard to follow strict diet meal.

Anyone wishes to lose weight or gain muscle can use this application to keep a track of their calories income and meal 
plan in order to keep the wished outcome or changes diet plan.

Personally, I am quite interested in workout so that I usually have to keep diet for that. And I know it will be easier 
to keep diet plan if meals can be tracked and planned in advance. </p>

**User story**:
- As a user, I want to be able to add a kind of food or a meal for the food record or plan for a day
- As a user, I want to be able to view the list of meal and foods I had or planned for a day
- As a user, I want to be able to view the total calories I had for a day
- As a user, I want to be able to mark a meal as complete on my plan
- As a user, I want to be able to save my meal records or plan to file
- As a user, I want to be able to be able to load my meal record or plan from file

**Instructions for Grader**:
- You can find the welcome image once you open the application, press the welcome button to access to the main menu
- You can add food by pressing "Add" button and input the data you wnat in the following panel's text area. Then press 
"Add more" to add another food, or press "Add confirmed" to add all the foods so far to one meal. Then choose "Record"
or "Plan" button to decide to add the meal to which meal plan
- You can finish the meals in the plan by pressing "Finish" button and choose the meal you wish to finish in the scroll 
and press "Finish" to transfer the selected meal to records
- You can save current record and plan by pressing "Save"
- You can load current record and plan from file by pressing "Load"

**Phase 4: Task 2**:
- When a meal is added to a meal plan, names of food added and the calories of the added meal will be printed at close.
  - Example:
    - Wed Nov 30 20:52:31 PST 2022
    - Meal added to meal plan
    - Food: Chicken, Water,
    - Calories: 170
- If meal plans are saved, the "Meal plan saved" will be printed at close

**Phase 4: Task 3**:
- The first change will be about the GUI. The current version only used one class for all the panel designs and action 
listeners, which is too long and complex. So I will add an abstract tool class and separate the different functions of 
the GUI into different tool classes with relevant action listeners, which will be easier to handle each tool’s 
functions.
- The second change I will do is to change the design of the panels in the GUI. I have used too many fields of GUI for 
buttons and panels, which I actually may not need that much. In order to make the GUI easier to read and use, I should 
try to reduce some uses of those elements or use more classes to avoid repetition. 
- The third change I will do is trying to merge the Food, Meal, and MealPlan classes. These three classes have 
significant similarities with each other. I could use an interface or abstract class to reduce the repetition to 2 
classes I assume.
