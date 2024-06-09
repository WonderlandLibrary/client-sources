package com.client.glowclient.modules.render;

import com.client.glowclient.sponge.mixinutils.*;
import com.client.glowclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.*;

public class CameraClip extends ModuleContainer
{
    public static final NumberValue zoom;
    
    @Override
    public void D() {
        HookTranslator.v3 = true;
    }
    
    @Override
    public String M() {
        return "";
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        HookTranslator.v3 = true;
    }
    
    @Override
    public void E() {
        HookTranslator.v3 = false;
    }
    
    static {
        zoom = ValueFactory.M("CameraClip", "Zoom", "Zoom Distance", 4.0, 0.5, 3.0, 20.0);
    }
    
    public CameraClip() {
        super(Category.RENDER, "CameraClip", false, -1, "Blocks no longer block camera");
    }
}
