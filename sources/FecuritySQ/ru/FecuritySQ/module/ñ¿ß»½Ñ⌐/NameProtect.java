package ru.FecuritySQ.module.дисплей;

import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.module.Module;

public class NameProtect extends Module {
    public NameProtect() {
        super(Category.Дисплей, GLFW.GLFW_KEY_0);
    }

}
