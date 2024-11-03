package vestige.event.impl;

import net.minecraft.network.Packet;
import vestige.event.type.CancellableEvent;

public class PacketEvent extends CancellableEvent {
   private Packet packet;

   public Packet packet() {
      return this.packet;
   }

   public PacketEvent(Packet packet) {
      this.packet = packet;
   }

   public void setPacket(Packet packet) {
      this.packet = packet;
   }
}
