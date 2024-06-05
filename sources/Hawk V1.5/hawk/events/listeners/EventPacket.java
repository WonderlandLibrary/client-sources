package hawk.events.listeners;

import hawk.events.Event;
import net.minecraft.network.Packet;

public class EventPacket<T> extends Event<EventPacket> {
   public boolean cancelled;
   public static Packet packet;

   public static Packet getPacket() {
      return packet;
   }

   public void setCancelled(boolean var1) {
      this.cancelled = var1;
   }

   public boolean isCancelled() {
      return this.cancelled;
   }

   public EventPacket(Packet var1) {
      packet = var1;
   }

   public void setPacket(Packet var1) {
      packet = var1;
   }
}
