package ru.FecuritySQ.module.дисплей;

import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.module.Module;

public class ChatHider extends Module {
    public ChatHider() {
        super(Category.Дисплей, GLFW.GLFW_KEY_0);
    }

}
