package xyz.mathax.mathaxclient.utils.render;

public enum CategoryIcons {
    Custom("Custom"),
    Minecraft("Minecraft"),
    None("None");

    private final String name;

    CategoryIcons(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}