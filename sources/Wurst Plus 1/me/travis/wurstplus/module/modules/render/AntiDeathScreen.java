package me.travis.wurstplus.module.modules.render;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.travis.wurstplus.event.events.GuiScreenEvent;
import me.travis.wurstplus.module.Module;
import net.minecraft.client.gui.GuiGameOver;

@Module.Info(name = "AntiDeathScreen", category = Module.Category.RENDER)
public class AntiDeathScreen extends Module {

    @EventHandler
    public Listener<GuiScreenEvent.Displayed> listener = new Listener<>(event -> {

        if (!(event.getScreen() instanceof GuiGameOver)) {
            return;
        }

        if (mc.player.getHealth() > 0) {
            mc.player.respawnPlayer();
            mc.displayGuiScreen(null);
        }

    });

}
