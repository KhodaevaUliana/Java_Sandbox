package mealplanner;

public enum MealType {
        BREAKFAST("breakfast"), LUNCH("lunch"), DINNER("dinner");
        private String value;

        MealType (String value) {
                this.value = value;
        }

        public String getValue() {
                return this.value;
        }

        public static boolean checkMealType(String type) {
                boolean res = false;
                for (MealType el : MealType.values()) {
                        if (el.getValue().equals(type)) {
                                res = true;
                                break;
                        }
                }
                return res;
        }
}
