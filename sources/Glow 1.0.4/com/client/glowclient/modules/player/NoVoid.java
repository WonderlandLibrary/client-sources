package com.client.glowclient.modules.player;

import com.client.glowclient.events.*;
import com.client.glowclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class NoVoid extends ModuleContainer
{
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (Wrapper.mc.player.posY <= 0.1) {
            Wrapper.mc.player.motionY = 0.0;
        }
    }
    
    public NoVoid() {
        super(Category.PLAYER, "NoVoid", false, -1, "Stops player from falling into the void");
    }
    
    @Override
    public boolean A() {
        return false;
    }
}
