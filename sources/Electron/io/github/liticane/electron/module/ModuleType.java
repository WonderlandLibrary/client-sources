package io.github.liticane.electron.module;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ModuleType {
    COMBAT ("Combat"),
    MOVEMENT ("Movement"),
    PLAYER ("Player"),
    WORLD ("World"),
    VISUAL ("Visual");

    private final String name;
}