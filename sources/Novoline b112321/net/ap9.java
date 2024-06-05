package net;

import cc.novoline.events.events.PacketDirection;
import net.Eb;
import net.aG1;
import net.aaz;
import net.apC;
import net.apZ;
import net.minecraft.network.Packet;

public class ap9 extends apC {
   private final PacketDirection e;
   private Packet d;
   private static String c;

   public ap9(Packet var1, PacketDirection var2) {
      aG1.c();
      super();
      apZ var4 = new apZ(var1, var2);
      aaz.a((Eb)var4);
      if(var4.isCancelled()) {
         this.setCancelled(true);
      }

      this.e = var4.getDirection();
      this.d = var4.getPacket().getPacket();
   }

   public void a(Packet var1) {
      this.d = var1;
   }

   public PacketDirection a() {
      return this.e;
   }

   public Packet d() {
      return this.d;
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
