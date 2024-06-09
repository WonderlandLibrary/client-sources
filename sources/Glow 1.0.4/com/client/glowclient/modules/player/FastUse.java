package com.client.glowclient.modules.player;

import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import com.client.glowclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class FastUse extends ModuleContainer
{
    public FastUse() {
        super(Category.PLAYER, "FastUse", false, -1, "Fast use blocks and items");
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        try {
            Wrapper.mc.rightClickDelayTimer = 0;
        }
        catch (Exception ex) {}
    }
}
