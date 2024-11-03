package com.viaversion.viabackwards.api.data;

import com.google.common.base.Preconditions;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.BiMappings;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.util.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BackwardsMappings extends MappingDataBase {
   private final Class<? extends Protocol<?, ?, ?, ?>> vvProtocolClass;
   protected Int2ObjectMap<MappedItem> backwardsItemMappings;
   private Map<String, String> backwardsSoundMappings;
   private Map<String, String> entityNames;

   public BackwardsMappings(String unmappedVersion, String mappedVersion) {
      this(unmappedVersion, mappedVersion, null);
   }

   public BackwardsMappings(String unmappedVersion, String mappedVersion, @Nullable Class<? extends Protocol<?, ?, ?, ?>> vvProtocolClass) {
      super(unmappedVersion, mappedVersion);
      Preconditions.checkArgument(vvProtocolClass == null || !vvProtocolClass.isAssignableFrom(BackwardsProtocol.class));
      this.vvProtocolClass = vvProtocolClass;
   }

   @Override
   protected void loadExtras(CompoundTag data) {
      CompoundTag itemNames = data.get("itemnames");
      if (itemNames != null) {
         Preconditions.checkNotNull(this.itemMappings);
         this.backwardsItemMappings = new Int2ObjectOpenHashMap<>(itemNames.size());
         CompoundTag extraItemData = data.get("itemdata");

         for (Entry<String, Tag> entry : itemNames.entrySet()) {
            StringTag name = (StringTag)entry.getValue();
            int id = Integer.parseInt(entry.getKey());
            Integer customModelData = null;
            if (extraItemData != null && extraItemData.contains(entry.getKey())) {
               CompoundTag entryTag = extraItemData.get(entry.getKey());
               NumberTag customModelDataTag = entryTag.get("custom_model_data");
               customModelData = customModelDataTag != null ? customModelDataTag.asInt() : null;
            }

            this.backwardsItemMappings.put(id, new MappedItem(this.getNewItemId(id), name.getValue(), customModelData));
         }
      }

      CompoundTag entityNames = data.get("entitynames");
      if (entityNames != null) {
         this.entityNames = new HashMap<>(entityNames.size());

         for (Entry<String, Tag> entry : entityNames.entrySet()) {
            StringTag mappedTag = (StringTag)entry.getValue();
            this.entityNames.put(entry.getKey(), mappedTag.getValue());
         }
      }

      CompoundTag soundNames = data.get("soundnames");
      if (soundNames != null) {
         this.backwardsSoundMappings = new HashMap<>(soundNames.size());

         for (Entry<String, Tag> entry : soundNames.entrySet()) {
            StringTag mappedTag = (StringTag)entry.getValue();
            this.backwardsSoundMappings.put(entry.getKey(), mappedTag.getValue());
         }
      }
   }

   @Nullable
   @Override
   protected BiMappings loadBiMappings(CompoundTag data, String key) {
      if (key.equals("items") && this.vvProtocolClass != null) {
         Mappings mappings = super.loadMappings(data, key);
         MappingData mappingData = Via.getManager().getProtocolManager().getProtocol(this.vvProtocolClass).getMappingData();
         if (mappingData != null && mappingData.getItemMappings() != null) {
            return ItemMappings.of(mappings, mappingData.getItemMappings());
         }
      }

      return super.loadBiMappings(data, key);
   }

   @Override
   public int getNewItemId(int id) {
      return this.itemMappings.getNewId(id);
   }

   @Override
   public int getNewBlockId(int id) {
      return this.blockMappings.getNewId(id);
   }

   @Override
   public int getOldItemId(int id) {
      return this.checkValidity(id, this.itemMappings.inverse().getNewId(id), "item");
   }

   @Nullable
   public MappedItem getMappedItem(int id) {
      return this.backwardsItemMappings != null ? this.backwardsItemMappings.get(id) : null;
   }

   @Nullable
   public String getMappedNamedSound(String id) {
      return this.backwardsSoundMappings == null ? null : this.backwardsSoundMappings.get(Key.stripMinecraftNamespace(id));
   }

   @Nullable
   public String mappedEntityName(String entityName) {
      if (this.entityNames == null) {
         ViaBackwards.getPlatform().getLogger().severe("No entity mappings found when requesting them for " + entityName);
         new Exception().printStackTrace();
         return null;
      } else {
         return this.entityNames.get(entityName);
      }
   }

   @Nullable
   public Int2ObjectMap<MappedItem> getBackwardsItemMappings() {
      return this.backwardsItemMappings;
   }

   @Nullable
   public Map<String, String> getBackwardsSoundMappings() {
      return this.backwardsSoundMappings;
   }

   @Nullable
   public Class<? extends Protocol<?, ?, ?, ?>> getViaVersionProtocolClass() {
      return this.vvProtocolClass;
   }

   @Override
   protected Logger getLogger() {
      return ViaBackwards.getPlatform().getLogger();
   }

   @Nullable
   @Override
   protected CompoundTag readNBTFile(String name) {
      return VBMappingDataLoader.loadNBTFromDir(name);
   }
}
