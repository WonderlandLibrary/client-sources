package net;

import viaversion.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
import viaversion.viaversion.api.minecraft.item.Item;

public class nn {
   private static String[] b;

   public static Item b(Item var0) {
      return ItemRewriter.toClient(var0);
   }

   public static Item a(Item var0) {
      return ItemRewriter.toServer(var0);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[3]);
      }

   }
}
