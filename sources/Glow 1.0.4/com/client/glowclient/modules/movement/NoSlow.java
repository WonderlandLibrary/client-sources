package com.client.glowclient.modules.movement;

import com.client.glowclient.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.sponge.mixinutils.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class NoSlow extends ModuleContainer
{
    public static nB mode;
    
    public NoSlow() {
        super(Category.MOVEMENT, "NoSlow", false, -1, "Stops slowing from items");
    }
    
    @Override
    public void E() {
        HookTranslator.v18 = false;
    }
    
    static {
        NoSlow.mode = ValueFactory.M("NoSlow", "Mode", "Mode of NoSlow", "NCP", "NCP", "AAC");
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        HookTranslator.v18 = true;
    }
    
    @Override
    public void D() {
        HookTranslator.v18 = true;
    }
}
