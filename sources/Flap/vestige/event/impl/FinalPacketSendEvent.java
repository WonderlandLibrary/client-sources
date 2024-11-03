package vestige.event.impl;

import net.minecraft.network.Packet;
import vestige.event.type.CancellableEvent;

public class FinalPacketSendEvent extends CancellableEvent {
   private Packet packet;

   public Packet getPacket() {
      return this.packet;
   }

   public FinalPacketSendEvent(Packet packet) {
      this.packet = packet;
   }

   public void setPacket(Packet packet) {
      this.packet = packet;
   }
}
