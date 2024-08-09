package ru.FecuritySQ.module.общее;

import net.minecraft.client.gui.screen.DeathScreen;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolean;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super(Category.Общее, GLFW.GLFW_KEY_0);
    }

    @Override
    public void event(Event e) {
        if(e instanceof EventUpdate && isEnabled()){
            if (mc.currentScreen instanceof DeathScreen) {
                mc.player.respawnPlayer();
                mc.displayGuiScreen(null);
            }
        }
    }
}
