package mealplanner;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String url = "jdbc:postgresql://localhost:5432/meals_db"; // Change the database name if necessary
    //String url = "jdbc:postgresql:meals_db";
    //String user = "ulianakhodaeva"; // Your username
    String user = "postgres";
    //String password = "valent40"; // Your password
    String password = "1111";
    MealsDB mealsDB = new MealsDB(url, user, password);
    Planner planner = new Planner(mealsDB);
    boolean userEnteredExit = false;
    while (!userEnteredExit) {
      System.out.println("What would you like to do (add, show, plan, list plan, save, exit)?");
      String command = scanner.nextLine();
      switch (command) {
        case "add":
          MealAdder mealAdder = new MealAdder(scanner, mealsDB);
          Meal currMeal = mealAdder.enterMeal();
          break;
        case "show":
          showMeals(mealsDB, scanner);
          break;
        case "plan":
          planner.plan(scanner);
          break;
        case "list plan":
          planner.listPlan();
          break;
        case "save":
          planner.save(scanner);
          break;
        case "exit":
          userEnteredExit = true;
          System.out.println("Bye!");
          mealsDB.closeDB();
          break;
      }
    }


  }

  /*public static void showMeals (MealsDB mealsDB) {
    ArrayList<Meal> meals = mealsDB.retrieveMeals();
    if (meals.isEmpty()) {
      System.out.println("No meals saved. Add a meal first.");
      return;
    }
    System.out.println("");
    for (Meal meal : meals) {
      System.out.println(meal.toString());

    }
  }*/

  public static void showMeals (MealsDB mealsDB, Scanner scanner) {
    System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
    String mealType = scanner.nextLine();
    while (!MealType.checkMealType(mealType)) {
      System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
      mealType = scanner.nextLine();
    }
    ArrayList<Meal> meals = mealsDB.retrieveMealsOfSpecificCategory(mealType);
    if (meals.isEmpty()) {
      System.out.println("No meals found.");
      return;
    }
    System.out.println("Category: " + mealType + "\n");
    for (Meal meal : meals) {
      System.out.println(meal.toStringWithoutCategory());

    }

  }




}