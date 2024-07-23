package io.github.liticane.monoxide.module.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ModuleCategory {
    COMBAT("Combat", 0),
    MOVEMENT("Movement", 1),
    PLAYER("Player", 2),
    MISCELLANEOUS("Others", 3),
    RENDER("Render", 4),
    HUD("Design", 5);

    private final String name;
    private final int index;
}
