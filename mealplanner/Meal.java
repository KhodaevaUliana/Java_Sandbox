package mealplanner;

import java.util.ArrayList;

public class Meal {
    private MealType type;
    private String name;
    private ArrayList<String> ingredients;
    private int mealId;

    public Meal (String type, String name, ArrayList<String> ingredients, int mealId) {
        for (MealType el : MealType.values()) {
            if (el.getValue().equals(type)) {
                this.type = el;
                break;
            }
        }
        if (this.type == null) {
            System.out.println("Wrong input");
        }
        this.name = name;
        this.ingredients = ingredients;
        this.mealId = mealId;
    }

    public String getMealType() {
       return this.type.getValue();
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getIngredients() {
        return this.ingredients;
    }

    public int getMealId() {
        return this.mealId;
    }

    public String toString() {
        String caption = "Category: " + this.type.getValue() + "\n"
                + "Name: " + this.name + "\n"
                + "Ingredients:\n";
        StringBuilder res = new StringBuilder(caption);
        for (String ingredient : this.ingredients) {
            res.append(ingredient);
            res.append("\n");
        }
        return res.toString();
    }

    public String toStringWithoutCategory() {
        String caption = "Name: " + this.name + "\n"
                + "Ingredients:\n";
        StringBuilder res = new StringBuilder(caption);
        for (String ingredient : this.ingredients) {
            res.append(ingredient);
            res.append("\n");
        }
        return res.toString();
    }
}
