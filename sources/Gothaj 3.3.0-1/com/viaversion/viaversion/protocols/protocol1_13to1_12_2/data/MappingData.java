package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.io.CharStreams;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.BiMappings;
import com.viaversion.viaversion.api.data.Int2IntMapBiMappings;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.util.GsonUtil;
import com.viaversion.viaversion.util.Int2IntBiHashMap;
import com.viaversion.viaversion.util.Key;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MappingData extends MappingDataBase {
   private final Map<String, int[]> blockTags = new HashMap<>();
   private final Map<String, int[]> itemTags = new HashMap<>();
   private final Map<String, int[]> fluidTags = new HashMap<>();
   private final BiMap<Short, String> oldEnchantmentsIds = HashBiMap.create();
   private final Map<String, String> translateMapping = new HashMap<>();
   private final Map<String, String> mojangTranslation = new HashMap<>();
   private final BiMap<String, String> channelMappings = HashBiMap.create();

   public MappingData() {
      super("1.12", "1.13");
   }

   @Override
   protected void loadExtras(CompoundTag data) {
      this.loadTags(this.blockTags, data.get("block_tags"));
      this.loadTags(this.itemTags, data.get("item_tags"));
      this.loadTags(this.fluidTags, data.get("fluid_tags"));
      CompoundTag legacyEnchantments = data.get("legacy_enchantments");
      this.loadEnchantments(this.oldEnchantmentsIds, legacyEnchantments);
      if (Via.getConfig().isSnowCollisionFix()) {
         this.blockMappings.setNewId(1248, 3416);
      }

      if (Via.getConfig().isInfestedBlocksFix()) {
         this.blockMappings.setNewId(1552, 1);
         this.blockMappings.setNewId(1553, 14);
         this.blockMappings.setNewId(1554, 3983);
         this.blockMappings.setNewId(1555, 3984);
         this.blockMappings.setNewId(1556, 3985);
         this.blockMappings.setNewId(1557, 3986);
      }

      JsonObject object = MappingDataLoader.loadFromDataDir("channelmappings-1.13.json");
      if (object != null) {
         for (Entry<String, JsonElement> entry : object.entrySet()) {
            String oldChannel = entry.getKey();
            String newChannel = entry.getValue().getAsString();
            if (!Key.isValid(newChannel)) {
               Via.getPlatform().getLogger().warning("Channel '" + newChannel + "' is not a valid 1.13 plugin channel, please check your configuration!");
            } else {
               this.channelMappings.put(oldChannel, newChannel);
            }
         }
      }

      Map<String, String> translationMappingData = GsonUtil.getGson()
         .fromJson(
            new InputStreamReader(MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/mapping-lang-1.12-1.13.json")),
            (new TypeToken<Map<String, String>>() {
            }).getType()
         );

      String[] unmappedTranslationLines;
      try (Reader reader = new InputStreamReader(
            MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/en_US.properties"), StandardCharsets.UTF_8
         )) {
         unmappedTranslationLines = CharStreams.toString(reader).split("\n");
      } catch (IOException var22) {
         throw new RuntimeException(var22);
      }

      for (String line : unmappedTranslationLines) {
         if (!line.isEmpty()) {
            String[] keyAndTranslation = line.split("=", 2);
            if (keyAndTranslation.length == 2) {
               String key = keyAndTranslation[0];
               String translation = keyAndTranslation[1].replaceAll("%(\\d\\$)?d", "%$1s").trim();
               this.mojangTranslation.put(key, translation);
               if (translationMappingData.containsKey(key)) {
                  String mappedKey = translationMappingData.get(key);
                  this.translateMapping.put(key, mappedKey != null ? mappedKey : key);
               }
            }
         }
      }
   }

   @Nullable
   @Override
   protected Mappings loadMappings(CompoundTag data, String key) {
      if (key.equals("blocks")) {
         return super.loadMappings(data, "blockstates");
      } else {
         return key.equals("blockstates") ? null : super.loadMappings(data, key);
      }
   }

   @Nullable
   @Override
   protected BiMappings loadBiMappings(CompoundTag data, String key) {
      return key.equals("items") ? (BiMappings)MappingDataLoader.loadMappings(data, "items", size -> {
         Int2IntBiHashMap map = new Int2IntBiHashMap(size);
         map.defaultReturnValue(-1);
         return map;
      }, Int2IntBiHashMap::put, (v, mappedSize) -> Int2IntMapBiMappings.of(v)) : super.loadBiMappings(data, key);
   }

   public static String validateNewChannel(String newId) {
      return !Key.isValid(newId) ? null : Key.namespaced(newId);
   }

   private void loadTags(Map<String, int[]> output, CompoundTag newTags) {
      for (Entry<String, Tag> entry : newTags.entrySet()) {
         IntArrayTag ids = (IntArrayTag)entry.getValue();
         output.put(Key.namespaced(entry.getKey()), ids.getValue());
      }
   }

   private void loadEnchantments(Map<Short, String> output, CompoundTag enchantments) {
      for (Entry<String, Tag> enty : enchantments.entrySet()) {
         output.put(Short.parseShort(enty.getKey()), ((StringTag)enty.getValue()).getValue());
      }
   }

   public Map<String, int[]> getBlockTags() {
      return this.blockTags;
   }

   public Map<String, int[]> getItemTags() {
      return this.itemTags;
   }

   public Map<String, int[]> getFluidTags() {
      return this.fluidTags;
   }

   public BiMap<Short, String> getOldEnchantmentsIds() {
      return this.oldEnchantmentsIds;
   }

   public Map<String, String> getTranslateMapping() {
      return this.translateMapping;
   }

   public Map<String, String> getMojangTranslation() {
      return this.mojangTranslation;
   }

   public BiMap<String, String> getChannelMappings() {
      return this.channelMappings;
   }
}
