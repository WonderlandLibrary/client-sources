package ru.FecuritySQ.module.передвижение;

import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;

public class NoSlowDown extends Module {
    public NoSlowDown() {
        super(Category.Передвижение, GLFW.GLFW_KEY_0);
    }

    @Override
    public void event(Event e) {}
}
