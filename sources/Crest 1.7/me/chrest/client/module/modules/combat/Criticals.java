// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.combat;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import me.chrest.utils.ClientUtils;
import me.chrest.client.option.Option;
import me.chrest.client.module.Module;

@Mod(displayName = "Crit")
public class Criticals extends Module
{
    @Option.Op(name = "Packet")
    private boolean Packet;
    
    public Criticals() {
        if (this.Packet) {
            double[] array;
            for (int length = (array = new double[] { 0.05, 0.0, 0.03, 0.0 }).length, i = 0; i < length; ++i) {
                final double offset = array[i];
                ClientUtils.player().sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX, ClientUtils.player().posY + offset, ClientUtils.player().posZ, false));
            }
        }
    }
}
