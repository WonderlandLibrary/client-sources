package dev.darkmoon.client.module;

import dev.darkmoon.client.utility.render.animation.Animation;
import dev.darkmoon.client.utility.render.animation.impl.DecelerateAnimation;
import lombok.Getter;

public enum Category {
    COMBAT("Combat", "A"), MOVEMENT("Movement", "B"),
    RENDER("Display", "C"),
    PLAYER("Player", "D"),
    UTIL("Util", "E"),
    CONFIGS("Configs", "F"),
    THEMES("Themes", "G");

    @Getter
    private final String name;
    @Getter
    private final String icon;
    @Getter
    private boolean bottom = false;
    @Getter
    private final Animation animation = new DecelerateAnimation(340, 1f);

    Category(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }
    Category(String name, String icon, boolean bottom) {
        this.name = name;
        this.icon = icon;
        this.bottom = bottom;
    }
}
