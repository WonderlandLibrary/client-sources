package net;

import net.aRX;
import net.acE;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets.InventoryPackets;

public class a2P {
   private static acE[] b;

   public static void a(Item var0) {
      InventoryPackets.toClient(var0);
   }

   public static void a(aRX var0) {
      InventoryPackets.a(var0);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[1]);
      }

   }
}
