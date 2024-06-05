package net;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.Map;
import viaversion.viabackwards.api.data.VBMappingDataLoader;

public class mv {
   public static JsonObject b(String var0) {
      return VBMappingDataLoader.loadFromDataDir(var0);
   }

   public static void a(short[] var0, JsonObject var1, JsonObject var2, JsonObject var3, boolean var4) {
      VBMappingDataLoader.mapIdentifiers(var0, var1, var2, var3, var4);
   }

   public static JsonObject a(String var0) {
      return VBMappingDataLoader.loadData(var0);
   }

   public static Int2ObjectMap a(JsonObject var0, JsonObject var1, JsonObject var2) {
      return VBMappingDataLoader.loadItemMappings(var0, var1, var2);
   }

   public static Map a(JsonObject var0) {
      return VBMappingDataLoader.objectToMap(var0);
   }
}
