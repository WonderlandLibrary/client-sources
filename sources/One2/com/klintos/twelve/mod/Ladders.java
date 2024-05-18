// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.BlockLadder;
import com.klintos.twelve.utils.PlayerUtils;
import net.minecraft.util.MathHelper;
import com.klintos.twelve.mod.events.EventPreUpdate;

public class Ladders extends Mod
{
    public Ladders() {
        super("Ladders", 38, ModCategory.WORLD);
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        boolean movementInput = Ladders.mc.gameSettings.keyBindForward.pressed || Ladders.mc.gameSettings.keyBindBack.pressed || Ladders.mc.gameSettings.keyBindLeft.pressed || Ladders.mc.gameSettings.keyBindLeft.pressed;

    	if (mc.thePlayer.isOnLadder() && movementInput) {
    		mc.thePlayer.motionY = 0.2;
    	}
    	
    }
}
