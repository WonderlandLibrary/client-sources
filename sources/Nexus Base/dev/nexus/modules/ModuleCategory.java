package dev.nexus.modules;

public enum ModuleCategory {

    COMBAT("Combat"), MOVEMENT("Movement"), PLAYER("Player"), RENDER("Render"), OTHER("Other");

    public final String name;

    ModuleCategory(String name) {
        this.name = name;
    }
}
