package us.dev.direkt.module;

/**
 * @author Foundry
 */
public enum ModCategory {
    PLAYER("Player", 0xD8B400),
    MOVEMENT("Movement", 0xA6364C),
    COMBAT("Combat", 0xC12A2E),
    WORLD("World", 0xC7823C),
    RENDER("Render", 0x198C8C),
    MISC("Misc", 0xADADA6),
    CORE("Core", 0x000000);

    private String name;
    private int defaultColor;

    ModCategory(String name, int defaultColor) {
        this.name = name;
        this.defaultColor = defaultColor;
    }
    public int getDefaultColor() {
        return this.defaultColor;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
