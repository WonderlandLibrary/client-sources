package mathax.client.utils.misc;

import org.lwjgl.glfw.GLFW;

public enum CursorStyle {
    Default("Default"),
    Click("Click"),
    Type("Type");

    private final String title;
    private boolean created;
    private long cursor;

    CursorStyle(String title) {
        this.title = title;
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
        return title;
    }
}
