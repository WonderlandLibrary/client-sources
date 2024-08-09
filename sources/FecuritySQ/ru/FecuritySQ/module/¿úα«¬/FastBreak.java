package ru.FecuritySQ.module.игрок;

import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolean;

public class FastBreak extends Module {
    public FastBreak() {
        super(Category.Игрок, GLFW.GLFW_KEY_0);
    }

    @Override
    public void event(Event e) {
        if(isEnabled() && e instanceof EventUpdate){
            mc.playerController.blockHitDelay = 0;
            if (mc.playerController.curBlockDamageMP > 0.8f) {
                mc.playerController.curBlockDamageMP = 1F;
            }
        }
    }
}
