package dev.excellent.client.component.impl.rotationcomponent;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MovementFix {
    OFF("Off"),
    SILENT("Silent"),
    STRICT("Strict");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}