package hawk.events.listeners;

import hawk.events.Event;
import net.minecraft.network.Packet;

public class EventReceivePacket extends Event<EventReceivePacket> {
   Packet packet;

   public Packet getPacket() {
      return this.packet;
   }

   public EventReceivePacket(Packet var1) {
      this.packet = var1;
   }

   public void setPacket(Packet var1) {
      this.packet = var1;
   }
}
