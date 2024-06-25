package cc.slack.events.impl.network;

import cc.slack.events.Event;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketDirection;

public class PacketEvent extends Event {
   private Packet packet;
   private final PacketDirection direction;

   public void setPacket(Packet<?> packet) {
      this.packet = packet;
   }

   public <T extends Packet> T getPacket() {
      return this.packet;
   }

   public PacketDirection getDirection() {
      return this.direction;
   }

   public PacketEvent(Packet packet, PacketDirection direction) {
      this.packet = packet;
      this.direction = direction;
   }
}
