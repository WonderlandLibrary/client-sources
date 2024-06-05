package me.vaziak.sensation.client.api.event.events;

import me.vaziak.sensation.client.api.event.Cancellable;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;

/**
 * @author antja03
 */
public class SendPacketEvent extends Cancellable {
    private Packet packet;

    public SendPacketEvent(Packet packet) {
        this.packet = packet;
        if (getPacket() instanceof C00PacketKeepAlive) {
      //  	setCancelled(true);
        }
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
