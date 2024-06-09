package com.client.glowclient.modules.player;

import com.client.glowclient.events.*;
import com.client.glowclient.sponge.mixinutils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;

public class Reach extends ModuleContainer
{
    public static final NumberValue distance;
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        HookTranslator.v6 = true;
    }
    
    public Reach() {
        super(Category.PLAYER, "Reach", false, -1, "Changes reach distance of player");
    }
    
    @Override
    public void E() {
        HookTranslator.v6 = false;
    }
    
    @Override
    public void D() {
        HookTranslator.v6 = true;
    }
    
    static {
        final String s = "Reach";
        final String s2 = "Distance";
        final String s3 = "How far you can interact with things";
        final double n = 0.5;
        final double n2 = 4.5;
        distance = ValueFactory.M(s, s2, s3, n2, n, n2, 10.0);
    }
    
    @Override
    public String M() {
        return String.format("%.1f", Reach.distance.k());
    }
}
