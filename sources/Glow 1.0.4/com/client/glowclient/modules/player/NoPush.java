package com.client.glowclient.modules.player;

import com.client.glowclient.sponge.mixinutils.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class NoPush extends ModuleContainer
{
    public static BooleanValue water;
    public static BooleanValue blocks;
    public static BooleanValue entities;
    
    @Override
    public void D() {
        if (NoPush.water.M()) {
            HookTranslator.v16 = true;
        }
        else {
            HookTranslator.v16 = false;
        }
        if (NoPush.entities.M()) {
            HookTranslator.v15 = true;
        }
        else {
            HookTranslator.v15 = false;
        }
        if (NoPush.blocks.M()) {
            HookTranslator.v14 = true;
            return;
        }
        HookTranslator.v14 = false;
    }
    
    @Override
    public void E() {
        HookTranslator.v16 = false;
        HookTranslator.v15 = false;
        HookTranslator.v14 = false;
    }
    
    static {
        NoPush.entities = ValueFactory.M("NoPush", "Entities", "Stops pushing of entities", true);
        NoPush.water = ValueFactory.M("NoPush", "Water", "Stops pushing from water", true);
        NoPush.blocks = ValueFactory.M("NoPush", "Blocks", "Stops pushing from blocks", true);
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (NoPush.water.M()) {
            HookTranslator.v16 = true;
        }
        else {
            HookTranslator.v16 = false;
        }
        if (NoPush.entities.M()) {
            HookTranslator.v15 = true;
        }
        else {
            HookTranslator.v15 = false;
        }
        if (NoPush.blocks.M()) {
            HookTranslator.v14 = true;
            return;
        }
        HookTranslator.v14 = false;
    }
    
    public NoPush() {
        super(Category.PLAYER, "NoPush", false, -1, "Stops player pushing");
    }
}
