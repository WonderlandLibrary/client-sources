package xyz.mathax.mathaxclient.utils.entity;

public enum SortPriority {
    Lowest_Distance("Lowest distance"),
    Highest_Distance("Highest distance"),
    Lowest_Health("Lowest health"),
    Highest_Health("Highest health"),
    Closest_Angle("Closest angle");

    private final String name;

    SortPriority(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}