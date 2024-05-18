// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import com.klintos.twelve.mod.events.EventPacketSend;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import com.klintos.twelve.mod.events.EventPreAttack;

public class Criticals extends Mod
{
    private boolean cancel;
    private int cancelled;
    
    public Criticals() {
        super("Criticals", 46, ModCategory.COMBAT);
    }
    
    @EventTarget
    public void onPreAttack(final EventPreAttack event) {
        if (Criticals.mc.thePlayer.isCollidedVertically && !Jesus.isOnLiquid()) {
            Criticals.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + 0.063, Criticals.mc.thePlayer.posZ, false));
            Criticals.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY, Criticals.mc.thePlayer.posZ, false));
            this.cancel = true;
        }
    }
    
    @EventTarget
    public void onPacketSend(final EventPacketSend event) {
        if (event.getPacket() instanceof C03PacketPlayer && this.cancel && this.cancelled < 2) {
            event.setCancelled(true);
            ++this.cancelled;
        }
        else if (this.cancelled == 2) {
            this.cancelled = 0;
            this.cancel = false;
        }
    }
}
