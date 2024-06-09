package com.client.glowclient.modules.movement;

import com.client.glowclient.modules.*;
import com.client.glowclient.sponge.mixinutils.*;
import com.client.glowclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class SafeWalk extends ModuleContainer
{
    public SafeWalk() {
        super(Category.MOVEMENT, "SafeWalk", false, -1, "Shift without shifting");
    }
    
    @Override
    public void E() {
        HookTranslator.v19 = false;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        HookTranslator.v19 = true;
    }
}
