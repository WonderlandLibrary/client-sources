package com.minus.module;

public enum Category {
    COMBAT("Combat"), MOVEMENT("Movement"),MISC("Misc") ,RENDER("Render"), PLAYER("Player");
    public final String name;
    Category(String name) {
        this.name = name;
    }
}
