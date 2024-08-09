package dev.excellent.impl.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Release {
    PUBLIC("Public"),
    BETA("Beta"),
    DEV("Dev");
    private final String name;
}