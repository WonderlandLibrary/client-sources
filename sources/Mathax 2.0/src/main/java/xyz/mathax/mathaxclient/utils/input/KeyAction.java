package xyz.mathax.mathaxclient.utils.input;

import org.lwjgl.glfw.GLFW;

public enum KeyAction {
    Press("Press"),
    Repeat("Repeat"),
    Release("Release");

    private final String name;

    KeyAction(String name) {
        this.name = name;
    }

    public static KeyAction get(int action) {
        return switch (action) {
            case GLFW.GLFW_PRESS -> Press;
            case GLFW.GLFW_RELEASE -> Release;
            default -> Repeat;
        };
    }

    @Override
    public String toString() {
        return name;
    }
}
