package cc.novoline.events.events;

import cc.novoline.events.EventManager;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.callables.CancellableEvent;
import net.apZ;
import net.minecraft.network.Packet;

public class PacketEvent extends CancellableEvent {
   private final PacketDirection direction;
   private Packet packet;
   private static String c;

   public PacketEvent(Packet var1, PacketDirection var2) {
      MotionUpdateEvent.c();
      super();
      apZ var4 = new apZ(var1, var2);
      EventManager.call(var4);
      if(var4.isCancelled()) {
         this.setCancelled(true);
      }

      this.direction = var4.getDirection();
      this.packet = var4.getPacket().getPacket();
   }

   public void a(Packet var1) {
      this.packet = var1;
   }

   public PacketDirection getDirection() {
      return this.direction;
   }

   public Packet getPacket() {
      return this.packet;
   }

   public static void b(String var0) {
      c = var0;
   }

   public static String c() {
      return c;
   }

   static {
      if(c() == null) {
         b("RD2zX");
      }

   }
}
