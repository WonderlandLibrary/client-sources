package me.napoleon.napoline.modules.player;

import me.napoleon.napoline.events.EventTick;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;
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
