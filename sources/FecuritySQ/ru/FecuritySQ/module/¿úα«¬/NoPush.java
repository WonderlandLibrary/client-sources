package ru.FecuritySQ.module.игрок;

import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolean;

public class NoPush extends Module {
    public NoPush() {
        super(Category.Игрок, GLFW.GLFW_KEY_0);
    }

}
