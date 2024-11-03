package dev.lvstrng.argon.utils;

public enum ActionType {
    ALL("All"),
    RIGHT_CLICK("Right Click"),
    LEFT_CLICK("Left Click");

    public final String name;

    ActionType(final String name) {
        this.name = name;
    }
}
