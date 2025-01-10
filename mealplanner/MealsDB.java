package mealplanner;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MealsDB {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    public MealsDB(String url, String user, String password) {
        try {
            // Load and register PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish connection
            this.conn = DriverManager.getConnection(url, user, password);
            //System.out.println("Connected to the PostgreSQL server successfully.");

            // Create the first table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS meals ("
                    + "meal_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
                    + "category VARCHAR, "
                    + "meal VARCHAR)";

            this.stmt = this.conn.createStatement();
            //DELETE AFTER TESTING
            /*this.stmt.executeUpdate("drop table if exists meals");
            this.stmt.executeUpdate("drop table if exists ingredients");*/

            this.stmt.executeUpdate(createTableSQL);
            //System.out.println("Table 1 created");

            // Create the second table
            createTableSQL = "CREATE TABLE IF NOT EXISTS ingredients ("
                    + "ingredient_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
                    + "ingredient VARCHAR, "
                    + "meal_id INTEGER)";

            this.stmt = this.conn.createStatement();
            this.stmt.executeUpdate(createTableSQL);
            //System.out.println("Table 2 created");

        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public void closeDB() {
        try {
            if (this.rs != null) this.rs.close();
            if (this.stmt != null) this.stmt.close();
            if (this.conn != null) this.conn.close();


        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public void addMeal(Meal meal) {
        String mealData = "'" + meal.getMealType() + "', '" + meal.getName() + "'";
        try {
            // Insert some data into the table
            String insertDataSQL = "INSERT INTO meals (category, meal) "
                    + "VALUES (" + mealData + ")";
            //System.out.println(insertDataSQL);

            this.stmt.executeUpdate(insertDataSQL);

            //retrieve meal_id
            String query = "SELECT meal_id FROM meals WHERE meal = ?";
            PreparedStatement preparedStatement = this.conn.prepareStatement(query);
            preparedStatement.setString(1, meal.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            int mealId = 0;
            if (resultSet.next()) {
                mealId = resultSet.getInt("meal_id");
            }
            //System.out.println(mealId);
            //insert ingredients
            for (String ingredient : meal.getIngredients()) {
                insertDataSQL = "INSERT INTO ingredients (ingredient, meal_id) VALUES (?, ?)";
                preparedStatement = conn.prepareStatement(insertDataSQL);
                preparedStatement.setString(1, ingredient);
                preparedStatement.setInt(2, mealId);
                preparedStatement.executeUpdate();
            }


        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public ArrayList<Meal> retrieveMeals() {
        ArrayList<Meal> res = new ArrayList<>();
        String selectSQL = "SELECT meal_id, category, meal FROM meals";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int mealId = resultSet.getInt("meal_id");
                String category = resultSet.getString("category");
                String meal = resultSet.getString("meal");
                //find ingredients
                selectSQL = "SELECT ingredient FROM ingredients WHERE meal_id = ?";
                preparedStatement = conn.prepareStatement(selectSQL);
                preparedStatement.setInt(1, mealId);
                ResultSet resultSetIngredient = preparedStatement.executeQuery();
                ArrayList<String> ingredients = new ArrayList<>();
                while (resultSetIngredient.next()) {
                    ingredients.add(resultSetIngredient.getString("ingredient"));
                }
                res.add(new Meal(category, meal, ingredients, mealId));

            }
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
        return res;
    }

    public ArrayList<Meal> retrieveMealsOfSpecificCategory(String mealType) {
        ArrayList<Meal> res = new ArrayList<>();
        String selectSQL = "SELECT meal_id, category, meal FROM meals WHERE category = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setString(1, mealType);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int mealId = resultSet.getInt("meal_id");
                String category = resultSet.getString("category");
                String meal = resultSet.getString("meal");
                //find ingredients
                selectSQL = "SELECT ingredient FROM ingredients WHERE meal_id = ?";
                preparedStatement = conn.prepareStatement(selectSQL);
                preparedStatement.setInt(1, mealId);
                ResultSet resultSetIngredient = preparedStatement.executeQuery();
                ArrayList<String> ingredients = new ArrayList<>();
                while (resultSetIngredient.next()) {
                    ingredients.add(resultSetIngredient.getString("ingredient"));
                }
                Meal mealToAdd = new Meal(category, meal, ingredients, mealId);
                res.add(mealToAdd);

            }
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
        return res;
    }

    public ArrayList<Meal> retrieveMealsOfSpecificCategoryOrdered(String mealType) {
        ArrayList<Meal> res = new ArrayList<>();
        String selectSQL = "SELECT meal_id, category, meal FROM meals WHERE category = ? ORDER BY meal";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setString(1, mealType);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int mealId = resultSet.getInt("meal_id");
                String category = resultSet.getString("category");
                String meal = resultSet.getString("meal");
                //find ingredients
                selectSQL = "SELECT ingredient FROM ingredients WHERE meal_id = ?";
                preparedStatement = conn.prepareStatement(selectSQL);
                preparedStatement.setInt(1, mealId);
                ResultSet resultSetIngredient = preparedStatement.executeQuery();
                ArrayList<String> ingredients = new ArrayList<>();
                while (resultSetIngredient.next()) {
                    ingredients.add(resultSetIngredient.getString("ingredient"));
                }
                Meal mealToAdd = new Meal(category, meal, ingredients, mealId);
                res.add(mealToAdd);

            }
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
        return res;
    }

    public void startPlanning() {
        String dropQuery = "DROP TABLE IF EXISTS plan";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(dropQuery);
            preparedStatement.executeUpdate();
            String createQuery =  "CREATE TABLE plan ("
                    + "day VARCHAR, "
                    + "meal_category VARCHAR, "
                    + "meal_option VARCHAR, "
                    + "meal_id INTEGER)";
            preparedStatement = conn.prepareStatement(createQuery);
            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public void insertPlanner(Meal meal, Weekday day) {
        try {
            String insertDataSQL = "INSERT INTO plan (day,  meal_category, meal_option, meal_id) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertDataSQL);
            preparedStatement.setString(1, day.getValue());
            preparedStatement.setString(2, meal.getMealType());
            preparedStatement.setString(3, meal.getName());
            preparedStatement.setInt(4, meal.getMealId());
            preparedStatement.executeUpdate();

        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public boolean checkPlans() {
        String checkTableSQL =
                "SELECT COUNT(*) FROM information_schema.tables " +
                        "WHERE table_schema = 'public' AND table_name = 'plan'";

        try (PreparedStatement checkStatement = conn.prepareStatement(checkTableSQL)) {
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int tableExists = resultSet.getInt(1);
            if (tableExists == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
        return false;
    }

    public String retrievePlannedMeal(Weekday day, MealType mealType) {
        String selectSQL = "SELECT meal_option FROM plan WHERE meal_category = ? AND day = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setString(1, mealType.getValue());
            preparedStatement.setString(2, day.getValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("meal_option");
            } else {
                return "";
            }
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
        return "";
    }

    public HashMap<String, Integer> ingredientsList() {
        HashMap<String, Integer> res = new HashMap<>();
        try {
            String selectSQL = "SELECT meal_option, meal_id, COUNT(*) AS meal_num  FROM plan GROUP BY meal_option, meal_id";
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            ResultSet resultSetMeals = preparedStatement.executeQuery();
            while (resultSetMeals.next()) {
                int mealId = resultSetMeals.getInt("meal_id");
                int times = resultSetMeals.getInt("meal_num");
                selectSQL = "SELECT ingredient FROM ingredients WHERE meal_id = ?";
                preparedStatement = conn.prepareStatement(selectSQL);
                preparedStatement.setInt(1, mealId);
                ResultSet resultSetIngredient = preparedStatement.executeQuery();
                ArrayList<String> ingredients = new ArrayList<>();
                while (resultSetIngredient.next()) {
                    String ingredient = resultSetIngredient.getString("ingredient");
                    int currVal = res.getOrDefault(ingredient, 0);
                    res.put(ingredient, currVal + times);
                }
                //System.out.println(resultSetIngredient.getString("meal_option") + resultSetIngredient.getInt("meal_num"));
            }
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
        return res;
    }


}
