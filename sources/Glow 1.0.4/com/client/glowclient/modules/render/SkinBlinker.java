package com.client.glowclient.modules.render;

import com.client.glowclient.events.*;
import com.client.glowclient.utils.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class SkinBlinker extends ModuleContainer
{
    public Timer b;
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (!this.b.hasBeenSet()) {
            this.b.reset();
        }
        if (this.b.delay(0.0)) {
            this.b.reset();
            Wrapper.mc.gameSettings.setModelPartEnabled(EnumPlayerModelParts.HAT, new Random().nextBoolean());
            Wrapper.mc.gameSettings.setModelPartEnabled(EnumPlayerModelParts.JACKET, new Random().nextBoolean());
            Wrapper.mc.gameSettings.setModelPartEnabled(EnumPlayerModelParts.LEFT_PANTS_LEG, new Random().nextBoolean());
            Wrapper.mc.gameSettings.setModelPartEnabled(EnumPlayerModelParts.LEFT_SLEEVE, new Random().nextBoolean());
            Wrapper.mc.gameSettings.setModelPartEnabled(EnumPlayerModelParts.RIGHT_PANTS_LEG, new Random().nextBoolean());
            Wrapper.mc.gameSettings.setModelPartEnabled(EnumPlayerModelParts.RIGHT_SLEEVE, new Random().nextBoolean());
            Wrapper.mc.gameSettings.setModelPartEnabled(EnumPlayerModelParts.CAPE, new Random().nextBoolean());
        }
    }
    
    public SkinBlinker() {
        super(Category.RENDER, "SkinBlinker", false, -1, "Flash skin layers on and off");
        this.b = new Timer();
    }
    
    @Override
    public void E() {
        Wrapper.mc.gameSettings.setModelPartEnabled(EnumPlayerModelParts.HAT, true);
        Wrapper.mc.gameSettings.setModelPartEnabled(EnumPlayerModelParts.JACKET, true);
        Wrapper.mc.gameSettings.setModelPartEnabled(EnumPlayerModelParts.LEFT_PANTS_LEG, true);
        Wrapper.mc.gameSettings.setModelPartEnabled(EnumPlayerModelParts.LEFT_SLEEVE, true);
        Wrapper.mc.gameSettings.setModelPartEnabled(EnumPlayerModelParts.RIGHT_PANTS_LEG, true);
        Wrapper.mc.gameSettings.setModelPartEnabled(EnumPlayerModelParts.RIGHT_SLEEVE, true);
        Wrapper.mc.gameSettings.setModelPartEnabled(EnumPlayerModelParts.CAPE, true);
    }
}
