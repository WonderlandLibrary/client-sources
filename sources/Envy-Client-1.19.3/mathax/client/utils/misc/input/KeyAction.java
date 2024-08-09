package mathax.client.utils.misc.input;

import org.lwjgl.glfw.GLFW;

public enum KeyAction {
    Press("Press"),
    Repeat("Repeat"),
    Release("Release");

    private final String title;

    KeyAction(String title) {
        this.title = title;
    }

    public static KeyAction get(int action) {
        if (action == GLFW.GLFW_PRESS) return Press;
        else if (action == GLFW.GLFW_RELEASE) return Release;
        else return Repeat;
    }

    @Override
    public String toString() {
        return title;
    }
}
