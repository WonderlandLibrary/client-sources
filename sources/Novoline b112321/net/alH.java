package net;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.io.InputStream;
import java.util.Map;
import viaversion.viaversion.api.data.MappingDataLoader;
import viaversion.viaversion.util.Int2IntBiMap;

public class alH {
   public static void a(short[] var0, JsonObject var1, JsonObject var2, JsonObject var3) {
      MappingDataLoader.mapIdentifiers(var0, var1, var2, var3);
   }

   public static void a(short[] var0, JsonObject var1, JsonObject var2) {
      MappingDataLoader.mapIdentifiers(var0, var1, var2);
   }

   public static void a(short[] var0, JsonArray var1, JsonArray var2, JsonObject var3, boolean var4) {
      MappingDataLoader.mapIdentifiers(var0, var1, var2, var3, var4);
   }

   public static boolean c() {
      return MappingDataLoader.isCacheJsonMappings();
   }

   public static Map a() {
      return MappingDataLoader.getMappingsCache();
   }

   public static JsonObject a(String var0, boolean var1) {
      return MappingDataLoader.loadData(var0, var1);
   }

   public static JsonObject c(String var0) {
      return MappingDataLoader.loadData(var0);
   }

   public static JsonObject a(String var0) {
      return MappingDataLoader.loadFromDataDir(var0);
   }

   public static InputStream b(String var0) {
      return MappingDataLoader.getResource(var0);
   }

   public static Object2IntMap a(JsonObject var0) {
      return MappingDataLoader.indexedObjectToMap(var0);
   }

   public static void a(Int2IntBiMap var0, JsonObject var1, JsonObject var2, JsonObject var3) {
      MappingDataLoader.mapIdentifiers(var0, var1, var2, var3);
   }

   public static Object2IntMap a(JsonArray var0) {
      return MappingDataLoader.arrayToMap(var0);
   }

   public static void b() {
      MappingDataLoader.enableMappingsCache();
   }
}
