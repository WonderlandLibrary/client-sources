package dev.darkmoon.client.module.impl.player;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.client.gui.GuiGameOver;

@ModuleAnnotation(name = "AutoRespawn", category = Category.PLAYER)
public class AutoRespawn extends Module {
    @EventTarget
    public void onUpdateEvent(EventUpdate eventUpdate) {
        if (mc.currentScreen instanceof GuiGameOver) {
            mc.player.respawnPlayer();
            mc.displayGuiScreen(null);
        }
    }
}
