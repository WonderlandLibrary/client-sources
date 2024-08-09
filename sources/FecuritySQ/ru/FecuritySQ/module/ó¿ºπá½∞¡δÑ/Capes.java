package ru.FecuritySQ.module.визуальные;

import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolean;

public class Capes extends Module {

    public Capes() {
        super(Category.Визуальные, GLFW.GLFW_KEY_0);
        setEnabled(true);
    }

}
