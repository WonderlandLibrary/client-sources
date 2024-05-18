// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Damage extends Cmd
{
    public Damage() {
        super("damage", "Damages you.", "damage");
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
        for (int i = 0; i < 70; ++i) {
            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.06, this.mc.thePlayer.posZ, false));
            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
        }
        this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.1, this.mc.thePlayer.posZ, false));
    }
}
