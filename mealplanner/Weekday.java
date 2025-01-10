package mealplanner;

public enum Weekday {
    MONDAY("Monday"), TUESDAY("Tuesday"), WEDNESDAY("Wednesday"), THURSDAY("Thursday"),
    FRIDAY("Friday"), SATURDAY("Saturday"), SUNDAY("Sunday");
    private String value;

    Weekday(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

