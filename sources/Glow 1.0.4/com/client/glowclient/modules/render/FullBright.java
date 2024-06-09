package com.client.glowclient.modules.render;

import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class FullBright extends ModuleContainer
{
    public FullBright() {
        super(Category.RENDER, "FullBright", false, -1, "Sets gamma to max");
    }
    
    @Override
    public void D() {
        Wrapper.mc.gameSettings.gammaSetting = 16.0f;
    }
    
    @SubscribeEvent
    public void M(final TickEvent$ClientTickEvent tickEvent$ClientTickEvent) {
        Wrapper.mc.gameSettings.gammaSetting = 16.0f;
    }
    
    @Override
    public void E() {
        Wrapper.mc.gameSettings.gammaSetting = 1.0f;
    }
    
    @Override
    public boolean A() {
        return false;
    }
}
