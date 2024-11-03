package dev.stephen.nexus.module;

public enum ModuleCategory {

    COMBAT("Combat"), GHOST("Ghost"), MOVEMENT("Movement"), PLAYER("Player"), RENDER("Render"), OTHER("Other"), CLIENT("Client");
    public final String name;

    ModuleCategory(String name) {
        this.name = name;
    }
}
