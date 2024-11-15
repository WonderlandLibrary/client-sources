package dev.lvstrng.argon.modules;

public enum Category {
    COMBAT("Combat"),
    MISC("Misc"),
    RENDER("Render"),
    CLIENT("Client");

    public final String name;

    Category(final String name) {
        this.name = name;
    }
}