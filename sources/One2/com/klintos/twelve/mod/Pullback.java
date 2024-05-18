// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import com.klintos.twelve.mod.events.EventPacketSend;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiGameOver;
import com.klintos.twelve.mod.events.EventPreUpdate;

public class Pullback extends Mod
{
    private int delay;
    private int health;
    public boolean hasDamaged;
    
    public Pullback() {
        super("Pullback", 0, ModCategory.EXPLOITS);
    }
    
    @Override
    public void onEnable() {
        this.health = (int)Pullback.mc.thePlayer.getHealth();
        this.delay = 0;
        if (Pullback.mc.thePlayer.moveStrafing > 0.0f || Pullback.mc.thePlayer.moveForward > 0.0f) {
            this.damage();
        }
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        this.hasDamaged = ((int)Pullback.mc.thePlayer.getHealth() < this.health);
        if (Pullback.mc.thePlayer.isDead || Pullback.mc.currentScreen instanceof GuiGameOver) {
            this.hasDamaged = false;
        }
        if (!this.hasDamaged) {
            if (Pullback.mc.gameSettings.keyBindForward.pressed) {
                Pullback.mc.thePlayer.setSprinting(true);
            }
            if (Pullback.mc.thePlayer.moveStrafing > 0.0f || Pullback.mc.thePlayer.moveForward > 0.0f) {
                this.damage();
            }
        }
        else {
            final EntityPlayerSP thePlayer = Pullback.mc.thePlayer;
            thePlayer.motionY -= 1.3;
            ++this.delay;
        }
    }
    
    @EventTarget
    public void onPacketSend(final EventPacketSend event) {
        if (event.getPacket() instanceof C03PacketPlayer && this.delay >= 15) {
            final C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)event.getPacket();
            c03PacketPlayer.y += 0.09;
        }
    }
    
    @Override
    public void onDisable() {
        Pullback.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Pullback.mc.thePlayer.posX, Pullback.mc.thePlayer.posY + 2.147483647E9, Pullback.mc.thePlayer.posZ, false));
    }
    
    public void damage() {
        for (int i = 0; i < 70; ++i) {
            Pullback.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Pullback.mc.thePlayer.posX, Pullback.mc.thePlayer.posY + 0.06, Pullback.mc.thePlayer.posZ, false));
            Pullback.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Pullback.mc.thePlayer.posX, Pullback.mc.thePlayer.posY, Pullback.mc.thePlayer.posZ, false));
        }
        Pullback.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Pullback.mc.thePlayer.posX, Pullback.mc.thePlayer.posY + 0.1, Pullback.mc.thePlayer.posZ, false));
    }
}
