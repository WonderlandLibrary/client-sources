package maxstats.weave.event;

import me.sleepyfish.smok.rats.event.Event;
import net.minecraft.network.Packet;

// Class from SMok Client by SleepyFish
public class EventSendPacket extends Event {

   private Packet<?> packet;

   public EventSendPacket(Packet<?> packet) {
      this.packet = packet;
   }

   public Packet<?> getPacket() {
      return this.packet;
   }

   public void setPacket(Packet<?> packet) {
      this.packet = packet;
   }

}