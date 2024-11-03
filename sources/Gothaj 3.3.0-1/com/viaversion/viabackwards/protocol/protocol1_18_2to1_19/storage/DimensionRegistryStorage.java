package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage;

import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.Protocol1_18_2To1_19;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class DimensionRegistryStorage implements StorableObject {
   private final Map<String, CompoundTag> dimensions = new HashMap<>();
   private final Int2ObjectMap<CompoundTag> chatTypes = new Int2ObjectOpenHashMap<>();

   @Nullable
   public CompoundTag dimension(String dimensionKey) {
      CompoundTag compoundTag = this.dimensions.get(dimensionKey);
      return compoundTag != null ? compoundTag.copy() : null;
   }

   public void addDimension(String dimensionKey, CompoundTag dimension) {
      this.dimensions.put(dimensionKey, dimension);
   }

   @Nullable
   public CompoundTag chatType(int id) {
      return this.chatTypes.isEmpty() ? Protocol1_18_2To1_19.MAPPINGS.chatType(id) : this.chatTypes.get(id);
   }

   public void addChatType(int id, CompoundTag chatType) {
      this.chatTypes.put(id, chatType);
   }

   public void clear() {
      this.dimensions.clear();
      this.chatTypes.clear();
   }

   @Override
   public boolean clearOnServerSwitch() {
      return false;
   }
}
