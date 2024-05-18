package me.finz0.osiris.module.modules.misc;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.event.events.GuiScreenDisplayedEvent;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiGameOver;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("AutoRespawn", Category.MISC, "Respawn when you die");
    }

    Setting coords;

    public void setup(){
        AuroraMod.getInstance().settingsManager.rSetting(coords = new Setting("DeathCoords", this, true, "AutoRespawnDeathCoords"));
    }

    @EventHandler
    private Listener<GuiScreenDisplayedEvent> listener = new Listener<>(event -> {
        if(event.getScreen() instanceof GuiGameOver) {
            if(coords.getValBoolean())
                Command.sendClientMessage(String.format("You died at x%d y%d z%d", (int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ));
            if(mc.player != null)
                mc.player.respawnPlayer();
            mc.displayGuiScreen(null);
        }
    });

    public void onEnable(){
        AuroraMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        AuroraMod.EVENT_BUS.unsubscribe(this);
    }
}
