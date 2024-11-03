package vestige.event.impl;

import net.minecraft.network.Packet;
import vestige.event.type.CancellableEvent;

public class PacketSendEvent extends CancellableEvent {
   private Packet packet;

   public Packet getPacket() {
      return this.packet;
   }

   public PacketSendEvent(Packet packet) {
      this.packet = packet;
   }

   public void setPacket(Packet packet) {
      this.packet = packet;
   }
}
