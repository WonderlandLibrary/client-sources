package com.client.glowclient.modules.player;

import com.client.glowclient.events.*;
import net.minecraft.client.gui.*;
import com.client.glowclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class AutoRespawn extends ModuleContainer
{
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (Wrapper.mc.currentScreen instanceof GuiGameOver) {
            Wrapper.mc.currentScreen = null;
        }
        if (Wrapper.mc.player.getHealth() <= 0.0f) {
            Wrapper.mc.player.respawnPlayer();
        }
    }
    
    public AutoRespawn() {
        super(Category.PLAYER, "AutoRespawn", false, -1, "Auto respawn on death");
    }
}
