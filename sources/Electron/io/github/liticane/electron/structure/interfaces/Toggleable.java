package io.github.liticane.electron.structure.interfaces;

public interface Toggleable {
    void setEnabled(boolean bool);
    boolean isEnabled();

    void toggle();

    default void onEnable() { /* */ };
    default void onDisable() { /* */ };
}