package vestige.event.impl;

import net.minecraft.network.Packet;
import vestige.event.type.CancellableEvent;

public class PacketReceiveEvent extends CancellableEvent {
   private Packet packet;

   public Packet getPacket() {
      return this.packet;
   }

   public PacketReceiveEvent(Packet packet) {
      this.packet = packet;
   }
}
