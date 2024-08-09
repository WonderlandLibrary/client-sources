package dev.excellent.client.module.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    COMBAT("Combat", "A"),
    MOVEMENT("Movement", "B"),
    RENDER("Render", "C"),
    PLAYER("Player", "D"),
    MISC("Misc", "E");
    private final String name;
    private final String icon;
}

