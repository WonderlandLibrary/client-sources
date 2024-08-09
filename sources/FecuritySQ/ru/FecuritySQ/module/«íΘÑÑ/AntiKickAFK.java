package ru.FecuritySQ.module.общее;

import net.minecraft.client.gui.screen.DeathScreen;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolean;

public class AntiKickAFK extends Module {
    public AntiKickAFK() {
        super(Category.Общее, GLFW.GLFW_KEY_0);
    }

    @Override
    public void event(Event e) {
        if(e instanceof EventUpdate && isEnabled()){
            if (mc.player.getHealth() > 0) {
                if (mc.player.ticksExisted % 400 == 0) mc.player.sendChatMessage("/kekkak");
            }
        }
    }
}
