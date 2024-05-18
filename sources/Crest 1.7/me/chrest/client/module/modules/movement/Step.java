// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.movement;

import me.chrest.event.EventTarget;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import me.chrest.event.events.StepEvent;
import net.minecraft.client.Minecraft;
import me.chrest.client.module.Module;

@Mod(displayName = "Step")
public class Step extends Module
{
    private boolean didSend;
    private int sendTicks;
    Minecraft mc;
    
    public Step() {
        this.mc = Minecraft.getMinecraft();
    }
    
    @EventTarget
    private void onStep(final StepEvent event) {
        ++this.sendTicks;
        if (this.mc.thePlayer.isCollidedHorizontally && !this.didSend && this.mc.thePlayer.onGround) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.42, this.mc.thePlayer.posZ, true));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.75, this.mc.thePlayer.posZ, true));
            this.mc.thePlayer.setPositionAndUpdate(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0, this.mc.thePlayer.posZ);
            this.sendTicks = 0;
            this.didSend = true;
        }
        if (this.sendTicks >= 6) {
            this.didSend = false;
        }
    }
}
