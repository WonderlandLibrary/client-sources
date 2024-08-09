package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import net.minecraft.client.gui.screen.DeathScreen;

@ModuleInfo(name = "Auto Respawn", description = "Автоматически спавнит вас при смерти", category = Category.MISC)
public class AutoRespawn extends Module {
    private final Listener<UpdateEvent> onUpdate = event -> {
        if (mc.currentScreen instanceof DeathScreen && mc.player.deathTime > 2) {
            mc.player.respawnPlayer();
            mc.displayScreen(null);
        }
    };
}
