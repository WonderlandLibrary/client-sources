package com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class DimensionRegistryStorage implements StorableObject {
   private Map<CompoundTag, String> dimensions;

   @Nullable
   public String dimensionKey(CompoundTag dimensionData) {
      return this.dimensions.get(dimensionData);
   }

   public void setDimensions(Map<CompoundTag, String> dimensions) {
      this.dimensions = dimensions;
   }

   public Map<CompoundTag, String> dimensions() {
      return this.dimensions;
   }

   @Override
   public boolean clearOnServerSwitch() {
      return false;
   }
}
