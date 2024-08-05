package fr.dog.module;

import fr.dog.structure.interfaces.Nameable;

public enum ModuleCategory implements Nameable {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    PLAYER("Player"),
    EXPLOIT("Exploit"),
    RENDER("Render");

    private final String name;

    ModuleCategory(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}