package net;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;
import viaversion.viaversion.api.data.MappingData;
import viaversion.viaversion.api.data.Mappings;

public class ai4 extends MappingData {
   private static int b;

   public ai4() {
      super("1.14", "1.15", true);
   }

   protected Mappings loadFromArray(JsonObject var1, JsonObject var2, @Nullable JsonObject var3, String var4) {
      int var5 = c();
      return !var4.equals("sounds")?super.loadFromArray(var1, var2, var3, var4):new Mappings(var1.getAsJsonArray(var4), var2.getAsJsonArray(var4), false);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 15;
   }

   static {
      b(3);
   }
}
