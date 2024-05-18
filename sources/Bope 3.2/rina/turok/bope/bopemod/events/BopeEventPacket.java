package rina.turok.bope.bopemod.events;

import net.minecraft.network.Packet;
import rina.turok.bope.external.BopeEventCancellable;

public class BopeEventPacket extends BopeEventCancellable {
   private final Packet packet;

   public BopeEventPacket(Packet packet) {
      this.packet = packet;
   }

   public Packet get_packet() {
      return this.packet;
   }

   public static class SendPacket extends BopeEventPacket {
      public SendPacket(Packet packet) {
         super(packet);
      }
   }

   public static class ReceivePacket extends BopeEventPacket {
      public ReceivePacket(Packet packet) {
         super(packet);
      }
   }
}
