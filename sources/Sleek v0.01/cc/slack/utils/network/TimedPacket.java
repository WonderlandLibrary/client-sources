package cc.slack.utils.network;

import net.minecraft.network.Packet;

/** @deprecated */
@Deprecated
public class TimedPacket {
   private Packet<?> packet;
   private long ms;

   public TimedPacket(Packet<?> packet, long ms) {
      this.packet = packet;
      this.ms = ms;
   }

   public Packet<?> getPacket() {
      return this.packet;
   }

   public void setPacket(Packet<?> packet) {
      this.packet = packet;
   }

   public long getMs() {
      return this.ms;
   }

   public void setMs(long ms) {
      this.ms = ms;
   }

   public boolean elapsed(long ms) {
      return System.currentTimeMillis() - this.getMs() > ms;
   }
}
