package net;

import net.aRw;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.InventoryPackets;

public class pi {
   private static int[] b;

   public static void a(Item var0) {
      InventoryPackets.toServer(var0);
   }

   public static void a(aRw var0) {
      InventoryPackets.a(var0);
   }

   public static void b(Item var0) {
      InventoryPackets.toClient(var0);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[1]);
      }

   }
}
