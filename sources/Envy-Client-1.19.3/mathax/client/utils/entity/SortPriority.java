package mathax.client.utils.entity;

public enum SortPriority {
    Lowest_Distance("Lowest Distance"),
    Highest_Distance("Highest Distance"),
    Lowest_Health("Lowest Health"),
    Highest_Health("Highest Health"),
    Closest_Angle("Closest Angle");

    private final String title;

    SortPriority(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
