// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Movement;

import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.client.network.badlion.Utils.ClientUtils;
import net.minecraft.Badlion;
import net.minecraft.client.network.badlion.Events.EventState;
import net.minecraft.client.network.badlion.Events.EventUpdate;
import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.client.network.badlion.Events.EventTick;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Mod.Mod;

public class Step extends Mod
{
    public boolean canstep;
    
    public static boolean steping = false;
    
    public Step() {
        super("Step", Category.MOVEMENT);
    }
    
    @EventTarget
    public void onTick(final EventTick event) {
        this.setRenderName(String.format("%s", this.getModName()));
    }
    
    @EventTarget
    public void onStep(final EventUpdate event) {
        if (event.state == EventState.PRE) {
            this.mc.thePlayer.stepHeight = 0.5f;
            if (!Badlion.getWinter().theMods.getMod(SkipClip.class).isEnabled() && this.mc.thePlayer.movementInput != null && !this.mc.thePlayer.movementInput.jump && this.mc.thePlayer.isCollidedHorizontally && this.mc.thePlayer.onGround) {
                this.mc.thePlayer.stepHeight = 1.0f;
                steping = true;
            }
        }
        else if (event.state == EventState.POST && !Badlion.getWinter().theMods.getMod(SkipClip.class).isEnabled() && this.mc.thePlayer.movementInput != null && !this.mc.thePlayer.movementInput.jump && this.mc.thePlayer.isCollidedHorizontally && this.mc.thePlayer.onGround) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 0.42, ClientUtils.z(), ClientUtils.player().onGround));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 0.75, ClientUtils.z(), ClientUtils.player().onGround));
            steping = false;
        }
    }
    
    @Override
    public void onEnable() {
        EventManager.register(this);
    }
    
    @Override
    public void onDisable() {
        this.mc.thePlayer.stepHeight = 0.5f;
        EventManager.unregister(this);
    }
}
