/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.player;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

@Module.Mod(displayName="Damage")
public class Damage
extends Module {
    @Option.Op(increment=0.5, min=0.5)
    private double damage = 0.5;

    @Override
    public void enable() {
        if (ClientUtils.player() != null) {
            int i = 0;
            while ((double)i < 80.0 + 40.0 * (this.damage - 0.5)) {
                ClientUtils.player().sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX, ClientUtils.player().posY + 0.049, ClientUtils.player().posZ, false));
                ClientUtils.player().sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX, ClientUtils.player().posY, ClientUtils.player().posZ, false));
                ++i;
            }
            ClientUtils.player().sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX, ClientUtils.player().posY, ClientUtils.player().posZ, true));
        }
    }
}

