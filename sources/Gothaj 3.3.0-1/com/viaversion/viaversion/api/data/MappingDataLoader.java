package com.viaversion.viaversion.api.data;

import com.google.common.annotations.Beta;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.io.NBTIO;
import com.viaversion.viaversion.libs.opennbt.tag.io.TagReader;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class MappingDataLoader {
   private static final Map<String, CompoundTag> MAPPINGS_CACHE = new HashMap<>();
   private static final TagReader<CompoundTag> MAPPINGS_READER = NBTIO.reader(CompoundTag.class).named();
   private static final byte DIRECT_ID = 0;
   private static final byte SHIFTS_ID = 1;
   private static final byte CHANGES_ID = 2;
   private static final byte IDENTITY_ID = 3;
   private static boolean cacheValid = true;

   @Deprecated
   public static void enableMappingsCache() {
   }

   public static void clearCache() {
      MAPPINGS_CACHE.clear();
      cacheValid = false;
   }

   @Nullable
   public static JsonObject loadFromDataDir(String name) {
      File file = new File(Via.getPlatform().getDataFolder(), name);
      if (!file.exists()) {
         return loadData(name);
      } else {
         try (FileReader reader = new FileReader(file)) {
            return GsonUtil.getGson().fromJson(reader, JsonObject.class);
         } catch (JsonSyntaxException var17) {
            Via.getPlatform().getLogger().warning(name + " is badly formatted!");
            throw new RuntimeException(var17);
         } catch (JsonIOException | IOException var18) {
            throw new RuntimeException(var18);
         }
      }
   }

   @Nullable
   public static JsonObject loadData(String name) {
      InputStream stream = getResource(name);
      if (stream == null) {
         return null;
      } else {
         try (InputStreamReader reader = new InputStreamReader(stream)) {
            return GsonUtil.getGson().fromJson(reader, JsonObject.class);
         } catch (IOException var16) {
            throw new RuntimeException(var16);
         }
      }
   }

   @Nullable
   public static CompoundTag loadNBT(String name, boolean cache) {
      if (!cacheValid) {
         return loadNBTFromFile(name);
      } else {
         CompoundTag data = MAPPINGS_CACHE.get(name);
         if (data != null) {
            return data;
         } else {
            data = loadNBTFromFile(name);
            if (cache && data != null) {
               MAPPINGS_CACHE.put(name, data);
            }

            return data;
         }
      }
   }

   @Nullable
   public static CompoundTag loadNBT(String name) {
      return loadNBT(name, false);
   }

   @Nullable
   public static CompoundTag loadNBTFromFile(String name) {
      InputStream resource = getResource(name);
      if (resource == null) {
         return null;
      } else {
         try {
            InputStream stream = resource;
            Throwable var3 = null;

            CompoundTag var4;
            try {
               var4 = MAPPINGS_READER.read(stream);
            } catch (Throwable var14) {
               var3 = var14;
               throw var14;
            } finally {
               if (resource != null) {
                  if (var3 != null) {
                     try {
                        stream.close();
                     } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                     }
                  } else {
                     resource.close();
                  }
               }
            }

            return var4;
         } catch (IOException var16) {
            throw new RuntimeException(var16);
         }
      }
   }

   @Nullable
   public static Mappings loadMappings(CompoundTag mappingsTag, String key) {
      return loadMappings(mappingsTag, key, size -> {
         int[] array = new int[size];
         Arrays.fill(array, -1);
         return array;
      }, (array, id, mappedId) -> array[id] = mappedId, IntArrayMappings::of);
   }

   @Beta
   @Nullable
   public static <M extends Mappings, V> Mappings loadMappings(
      CompoundTag mappingsTag,
      String key,
      MappingDataLoader.MappingHolderSupplier<V> holderSupplier,
      MappingDataLoader.AddConsumer<V> addConsumer,
      MappingDataLoader.MappingsSupplier<M, V> mappingsSupplier
   ) {
      CompoundTag tag = mappingsTag.get(key);
      if (tag == null) {
         return null;
      } else {
         ByteTag serializationStragetyTag = tag.get("id");
         IntTag mappedSizeTag = tag.get("mappedSize");
         byte strategy = serializationStragetyTag.asByte();
         if (strategy == 0) {
            IntArrayTag valuesTag = tag.get("val");
            return IntArrayMappings.of(valuesTag.getValue(), mappedSizeTag.asInt());
         } else {
            V mappings;
            if (strategy == 1) {
               IntArrayTag shiftsAtTag = tag.get("at");
               IntArrayTag shiftsTag = tag.get("to");
               IntTag sizeTag = tag.get("size");
               int[] shiftsAt = shiftsAtTag.getValue();
               int[] shiftsTo = shiftsTag.getValue();
               int size = sizeTag.asInt();
               mappings = holderSupplier.get(size);
               if (shiftsAt[0] != 0) {
                  int to = shiftsAt[0];

                  for (int id = 0; id < to; id++) {
                     addConsumer.addTo(mappings, id, id);
                  }
               }

               for (int i = 0; i < shiftsAt.length; i++) {
                  int from = shiftsAt[i];
                  int to = i == shiftsAt.length - 1 ? size : shiftsAt[i + 1];
                  int mappedId = shiftsTo[i];

                  for (int id = from; id < to; id++) {
                     addConsumer.addTo(mappings, id, mappedId++);
                  }
               }
            } else {
               if (strategy != 2) {
                  if (strategy == 3) {
                     IntTag sizeTag = tag.get("size");
                     return new IdentityMappings(sizeTag.asInt(), mappedSizeTag.asInt());
                  }

                  throw new IllegalArgumentException("Unknown serialization strategy: " + strategy);
               }

               IntArrayTag changesAtTag = tag.get("at");
               IntArrayTag valuesTag = tag.get("val");
               IntTag sizeTag = tag.get("size");
               boolean fillBetween = tag.get("nofill") == null;
               int[] changesAt = changesAtTag.getValue();
               int[] values = valuesTag.getValue();
               mappings = holderSupplier.get(sizeTag.asInt());

               for (int i = 0; i < changesAt.length; i++) {
                  int id = changesAt[i];
                  if (fillBetween) {
                     int previousId = i != 0 ? changesAt[i - 1] + 1 : 0;

                     for (int identity = previousId; identity < id; identity++) {
                        addConsumer.addTo(mappings, identity, identity);
                     }
                  }

                  addConsumer.addTo(mappings, id, values[i]);
               }
            }

            return mappingsSupplier.create(mappings, mappedSizeTag.asInt());
         }
      }
   }

   public static FullMappings loadFullMappings(CompoundTag mappingsTag, CompoundTag unmappedIdentifiers, CompoundTag mappedIdentifiers, String key) {
      ListTag unmappedElements = unmappedIdentifiers.get(key);
      ListTag mappedElements = mappedIdentifiers.get(key);
      if (unmappedElements != null && mappedElements != null) {
         Mappings mappings = loadMappings(mappingsTag, key);
         if (mappings == null) {
            mappings = new IdentityMappings(unmappedElements.size(), mappedElements.size());
         }

         return new FullMappingsBase(
            unmappedElements.getValue().stream().map(t -> (String)t.getValue()).collect(Collectors.toList()),
            mappedElements.getValue().stream().map(t -> (String)t.getValue()).collect(Collectors.toList()),
            mappings
         );
      } else {
         return null;
      }
   }

   @Deprecated
   public static void mapIdentifiers(
      int[] output, JsonObject unmappedIdentifiers, JsonObject mappedIdentifiers, @Nullable JsonObject diffIdentifiers, boolean warnOnMissing
   ) {
      Object2IntMap<String> newIdentifierMap = indexedObjectToMap(mappedIdentifiers);

      for (Entry<String, JsonElement> entry : unmappedIdentifiers.entrySet()) {
         int id = Integer.parseInt(entry.getKey());
         int mappedId = mapIdentifierEntry(id, entry.getValue().getAsString(), newIdentifierMap, diffIdentifiers, warnOnMissing);
         if (mappedId != -1) {
            output[id] = mappedId;
         }
      }
   }

   private static int mapIdentifierEntry(
      int id, String val, Object2IntMap<String> mappedIdentifiers, @Nullable JsonObject diffIdentifiers, boolean warnOnMissing
   ) {
      int mappedId = mappedIdentifiers.getInt(val);
      if (mappedId == -1) {
         if (diffIdentifiers != null) {
            JsonElement diffElement = diffIdentifiers.get(val);
            if (diffElement != null || (diffElement = diffIdentifiers.get(Integer.toString(id))) != null) {
               String mappedName = diffElement.getAsString();
               if (mappedName.isEmpty()) {
                  return -1;
               }

               mappedId = mappedIdentifiers.getInt(mappedName);
            }
         }

         if (mappedId == -1) {
            if (warnOnMissing && !Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
               Via.getPlatform().getLogger().warning("No key for " + val + " :( ");
            }

            return -1;
         }
      }

      return mappedId;
   }

   @Deprecated
   public static void mapIdentifiers(
      int[] output, JsonArray unmappedIdentifiers, JsonArray mappedIdentifiers, @Nullable JsonObject diffIdentifiers, boolean warnOnMissing
   ) {
      Object2IntMap<String> newIdentifierMap = arrayToMap(mappedIdentifiers);

      for (int id = 0; id < unmappedIdentifiers.size(); id++) {
         JsonElement unmappedIdentifier = unmappedIdentifiers.get(id);
         int mappedId = mapIdentifierEntry(id, unmappedIdentifier.getAsString(), newIdentifierMap, diffIdentifiers, warnOnMissing);
         if (mappedId != -1) {
            output[id] = mappedId;
         }
      }
   }

   public static Object2IntMap<String> indexedObjectToMap(JsonObject object) {
      Object2IntMap<String> map = new Object2IntOpenHashMap<>(object.size(), 0.99F);
      map.defaultReturnValue(-1);

      for (Entry<String, JsonElement> entry : object.entrySet()) {
         map.put(entry.getValue().getAsString(), Integer.parseInt(entry.getKey()));
      }

      return map;
   }

   public static Object2IntMap<String> arrayToMap(JsonArray array) {
      Object2IntMap<String> map = new Object2IntOpenHashMap<>(array.size(), 0.99F);
      map.defaultReturnValue(-1);

      for (int i = 0; i < array.size(); i++) {
         map.put(array.get(i).getAsString(), i);
      }

      return map;
   }

   @Nullable
   public static InputStream getResource(String name) {
      return MappingDataLoader.class.getClassLoader().getResourceAsStream("assets/viaversion/data/" + name);
   }

   @FunctionalInterface
   public interface AddConsumer<T> {
      void addTo(T var1, int var2, int var3);
   }

   @FunctionalInterface
   public interface MappingHolderSupplier<T> {
      T get(int var1);
   }

   @FunctionalInterface
   public interface MappingsSupplier<T extends Mappings, V> {
      T create(V var1, int var2);
   }
}
