// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Movement;

import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.badlion.Events.EventUpdate;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Mod.Mod;

public class Fly extends Mod
{
    public Fly() {
        super("Flight", Category.MOVEMENT);
    }
    
    @Override
    public void onEnable() {
        EventManager.register(this);
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        this.mc.thePlayer.isAirBorne = false;
        this.mc.thePlayer.capabilities.isFlying = false;
        this.mc.thePlayer.motionX = 0.0;
        this.mc.thePlayer.motionY = 0.0;
        this.mc.thePlayer.motionZ = 0.0;
        this.mc.thePlayer.jumpMovementFactor = 1.0f;
        if (Minecraft.gameSettings.keyBindJump.pressed) {
            final EntityPlayerSP thePlayer = this.mc.thePlayer;
            ++thePlayer.motionY;
        }
        if (Minecraft.gameSettings.keyBindSneak.pressed) {
            final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
            --thePlayer2.motionY;
        }
    }
    
    @Override
    public void onDisable() {
        this.mc.thePlayer.capabilities.isFlying = false;
        EventManager.unregister(this);
    }
}
