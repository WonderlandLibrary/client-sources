package net;

import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets;

public class nr {
   private static int[] b;

   public static String a(String var0) {
      return InventoryPackets.getNewPluginChannelId(var0);
   }

   public static void b(Item var0) {
      InventoryPackets.toClient(var0);
   }

   public static void a(Item var0) {
      InventoryPackets.a(var0);
   }

   public static void a(Protocol1_13To1_12_2 var0) {
      InventoryPackets.register(var0);
   }

   public static String b(String var0) {
      return InventoryPackets.getOldPluginChannelId(var0);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[5]);
      }

   }
}
