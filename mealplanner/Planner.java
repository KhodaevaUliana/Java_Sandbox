package mealplanner;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public class Planner {
    private MealsDB mealsDB;

    private HashMap<MealType, ArrayList<Meal>> mealTypeOptions;


    public Planner (MealsDB mealsDB) {
        this.mealsDB = mealsDB;
        this.mealTypeOptions = new HashMap<>();
    }

    public void plan(Scanner scanner) {
        //upload current options
        for (MealType mealType : MealType.values()) {
            this.mealTypeOptions.put(mealType, mealsDB.retrieveMealsOfSpecificCategoryOrdered(mealType.getValue()));
        }
        this.mealsDB.startPlanning();

        for (Weekday day : Weekday.values()) {
            System.out.println(day.getValue());
            for (MealType mealType : MealType.values()) {
                this.planDayMealType(day, mealType, scanner);
            }
            System.out.println(String.format("Yeah! We planned the meals for %s.", day.getValue()));
            System.out.println();
        }
        this.listPlan();

    }

    public void planDayMealType(Weekday day, MealType mealType, Scanner scanner) {
        for (Meal meal : this.mealTypeOptions.get(mealType)) {
            System.out.println(meal.getName());
        }
        System.out.println(String.format("Choose the %s for %s from the list above:", mealType.getValue(), day.getValue()));
        String mealInput = scanner.nextLine();
        boolean mealFound = false;
        while (!mealFound) {
            for (Meal currMeal : this.mealTypeOptions.get(mealType)) {
                if (currMeal.getName().equals(mealInput)) {
                    mealFound = true;
                    this.mealsDB.insertPlanner(currMeal, day);
                    break;
                }
            }
            if (!mealFound) {
                System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
                mealInput = scanner.nextLine();
            }
        }

    }

    public void listPlan() {
        if (!this.mealsDB.checkPlans()) {
            System.out.println("Database does not contain any meal plans");
            return;
        }
        for (Weekday day : Weekday.values()) {
            System.out.println(day.getValue());
            for (MealType mealType : MealType.values()) {
                System.out.println(mealType.getValue() + ": " + mealsDB.retrievePlannedMeal(day, mealType));
            }
            System.out.println();
        }

    }

    public void save(Scanner scanner) {
        if (!this.mealsDB.checkPlans()) {
            System.out.println("Unable to save. Plan your meals first.");
            return;
        }
        System.out.println("Input a filename:");
        String fileName = scanner.nextLine();
        //write to the file
        HashMap<String, Integer> dataForList = mealsDB.ingredientsList();
        //System.out.println(dataForList);
        Planner.writeDataToFile(fileName, dataForList);
        System.out.println("Saved!");
    }

    public static void writeDataToFile(String filename, HashMap<String, Integer> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String ingredient : data.keySet()) {
                String nextLine = ingredient;
                if (data.get(ingredient) > 1) {
                    nextLine += " x" + data.get(ingredient);
                }
                writer.write(nextLine);
                writer.newLine(); // Adds a newline after each line
            }
            //System.out.println("Data written to file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
