package me.finz0.osiris.module.modules.chat;

import me.finz0.osiris.event.events.GuiScreenDisplayedEvent;
import me.finz0.osiris.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiGameOver;

/**
 * Memeszz
 */
public class Excuses extends Module {
    public Excuses() {
        super("Excuses", Category.CHAT);
    }


    @EventHandler
    private Listener<GuiScreenDisplayedEvent> listener = new Listener<>(event -> {
        if(event.getScreen() instanceof GuiGameOver) {
             {
                mc.player.respawnPlayer();
                mc.displayGuiScreen(null);
                mc.player.sendChatMessage("Yea photoshop Aurora users dont die");
            }
        }
    });

}