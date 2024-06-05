package club.mega.module;

public enum Category {

    COMBAT("Combat", "a"), MOVEMENT("Movement", "b"), PLAYER("Player", "c"), VISUAL("Visual", "g"), HUD("Hud", "f");

    private final String name, icon;

    Category(final String name, final String icon) {
        this.name = name;
        this.icon = icon;
    }

    public final String getName() {
        return name;
    }

    public final String getIcon() {
        return icon;
    }

}
