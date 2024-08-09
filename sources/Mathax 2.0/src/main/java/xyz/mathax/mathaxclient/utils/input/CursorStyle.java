package xyz.mathax.mathaxclient.utils.input;

import org.lwjgl.glfw.GLFW;

public enum CursorStyle {
    Default("Default"),
    Click("Click"),
    Type("Type");

    private final String name;

    private boolean created;

    private long cursor;

    CursorStyle(String name) {
        this.name = name;
    }

    public long getGlfwCursor() {
        if (!created) {
            switch (this) {
                case Click -> cursor = GLFW.glfwCreateStandardCursor(GLFW.GLFW_HAND_CURSOR);
                case Type -> cursor = GLFW.glfwCreateStandardCursor(GLFW.GLFW_IBEAM_CURSOR);
            }

            created = true;
        }

        return cursor;
    }

    @Override
    public String toString() {
        return name;
    }
}