// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import com.darkmagician6.eventapi.EventTarget;
import java.util.Iterator;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import com.klintos.twelve.mod.events.EventPreUpdate;

public class Zoot extends Mod
{
    public Zoot() {
        super("Zoot", 0, ModCategory.MISC);
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        if (!Zoot.mc.thePlayer.onGround) {
            return;
        }
        for (final Object o : Zoot.mc.thePlayer.getActivePotionEffects()) {
            final PotionEffect effect = (PotionEffect)o;
            final Potion potion = Potion.potionTypes[effect.getPotionID()];
            if (potion == Potion.poison || potion == Potion.regeneration || potion == Potion.moveSlowdown || potion == Potion.hunger) {
                for (int index = 0; index < effect.getDuration() / 20; ++index) {
                    Zoot.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer(Zoot.mc.thePlayer.onGround));
                }
            }
        }
        if (Zoot.mc.thePlayer.isBurning()) {
            for (int index2 = 0; index2 < 20; ++index2) {
                Zoot.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer(Zoot.mc.thePlayer.onGround));
            }
        }
    }
}
