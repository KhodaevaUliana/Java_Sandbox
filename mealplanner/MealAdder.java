package mealplanner;

import java.util.Scanner;
import java.util.ArrayList;

public class MealAdder {
    private Scanner scanner;
    private MealsDB mealsDB;

    public MealAdder(Scanner scanner, MealsDB mealsDB) {
        this.scanner = scanner;
        this.mealsDB = mealsDB;
    }

    public Meal enterMeal() {
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
        String type = this.scanner.nextLine();
        while (!MealType.checkMealType(type)) {
            System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            type = this.scanner.nextLine();
        }
        System.out.println("Input the meal's name:");
        String name = this.scanner.nextLine();
        while (!checkNameCorrectness(name)) {
            System.out.println("Wrong format. Use letters only!");
            name = this.scanner.nextLine();
        }
        System.out.println("Input the ingredients:");
        ArrayList<String> ingredients = parseIngredientsInput(this.scanner.nextLine());
        while (!checkIngredientsCorrectness(ingredients)) {
            System.out.println("Wrong format. Use letters only!");
            ingredients = parseIngredientsInput(this.scanner.nextLine());
        }
        Meal entry = new Meal(type, name, ingredients, 0);
        System.out.println("The meal has been added!");
        this.mealsDB.addMeal(entry);
        return entry;
    }



    private static ArrayList<String> parseIngredientsInput (String input) {
        String[] ingredients = input.split(",");
        ArrayList<String> res = new ArrayList<>();
        for (int i = 0; i < ingredients.length; i++) {
            res.add(ingredients[i].trim());
        }
        return res;
    }

    private static boolean checkNameCorrectness (String name) {
        return (name.matches("[A-Za-z\s]*") && !(name.equals("")));
    }

    private static boolean checkIngredientsCorrectness (ArrayList<String> ingredients) {
        for (String ingredient :  ingredients) {
            if (!checkNameCorrectness(ingredient)) {
                return false;
            }
        }
        return true;
    }
}
