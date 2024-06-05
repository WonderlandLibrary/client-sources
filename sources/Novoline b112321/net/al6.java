package net;

import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class al6 {
   public static WorldSavedData a(MapStorage var0, Class var1, String var2) {
      return var0.loadData(var1, var2);
   }

   public static void a(MapStorage var0, String var1, WorldSavedData var2) {
      var0.setData(var1, var2);
   }

   public static void a(MapStorage var0) {
      var0.saveAllData();
   }
}
