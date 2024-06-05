package me.superskidder.lune.modules.player;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventTick;
import me.superskidder.lune.manager.event.EventTarget;
import net.minecraft.client.gui.GuiGameOver;

/**
 * @description: 自动重生
 * @author: QianXia
 * @create: 2020/10/11 18:36
 **/
public class AutoRespawn extends Mod {
    public AutoRespawn() {
        super("AutoRespawn", ModCategory.Player, "Auto respawn When you died");
    }

    @EventTarget
    public void onUpdate(EventTick event) {
        if (mc.thePlayer.isDead && mc.currentScreen != null && mc.currentScreen.getClass() == GuiGameOver.class) {
            mc.thePlayer.respawnPlayer();
            mc.displayGuiScreen(null);
        }
    }
}
