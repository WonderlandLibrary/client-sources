package com.alan.clients.font;

import lombok.Getter;

@Getter
public enum Weight {
    NONE(0, ""),
    LIGHT(1, "Light", "light", "LIGHT"),
    MEDIUM(2, "Medium", "medium", "MEDIUM"),
    REGULAR(3, "Regular", "regular", "REGULAR"),
    BOLD(4, "Bold", "bold", "BOLD");

    final private int num;
    final private String[] aliases;

    Weight(int num, String... aliases) {
        this.num = num;
        this.aliases = aliases;
    }
}
