package ru.FecuritySQ.module.визуальные;

import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.module.Module;

public class FullBright extends Module {

    public FullBright() {
        super(Category.Визуальные, GLFW.GLFW_KEY_0);
    }

    @Override
    public void event(Event e) {
        if(isEnabled()) mc.gameSettings.gamma = 1000F;
    }

    @Override
    public void disable() {
        mc.gameSettings.gamma = 1F;
        super.disable();
    }
}
