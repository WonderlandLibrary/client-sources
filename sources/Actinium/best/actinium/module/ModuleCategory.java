package best.actinium.module;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ModuleCategory {

    COMBAT ("Combat"),
    GHOST ("Ghost"),
    MOVEMENT ("Movement"),
    PLAYER ("Player"),
    WORLD ("World"),
    VISUAL ("Visual"),
    MANAGER ("Manager");

    private final String name;

}
