package vestige.handler.packet;

import net.minecraft.network.Packet;
import vestige.util.misc.TimerUtil;

public class DelayedPacket {
   private final Packet packet;
   private final TimerUtil timer;

   public DelayedPacket(Packet packet) {
      this.packet = packet;
      this.timer = new TimerUtil();
   }

   public Packet getPacket() {
      return this.packet;
   }

   public TimerUtil getTimer() {
      return this.timer;
   }
}
