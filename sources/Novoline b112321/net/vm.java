package net;

import viaversion.viarewind.protocol.protocol1_8to1_9.storage.Windows;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.minecraft.item.Item;

public class vm {
   public static String c(Windows var0, short var1) {
      return var0.get(var1);
   }

   public static void a(UserConnection var0, Item var1, short var2) {
      Windows.updateBrewingStand(var0, var1, var2);
   }

   public static Item[] b(Windows var0, short var1) {
      return var0.getBrewingItems(var1);
   }

   public static void a(Windows var0, short var1) {
      var0.remove(var1);
   }

   public static void a(Windows var0, short var1, String var2) {
      var0.put(var1, var2);
   }
}
