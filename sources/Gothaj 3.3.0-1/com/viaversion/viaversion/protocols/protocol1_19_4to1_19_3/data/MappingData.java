package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.data;

import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public final class MappingData extends MappingDataBase {
   private CompoundTag damageTypesRegistry;

   public MappingData() {
      super("1.19.3", "1.19.4");
   }

   @Override
   protected void loadExtras(CompoundTag data) {
      this.damageTypesRegistry = MappingDataLoader.loadNBTFromFile("damage-types-1.19.4.nbt");
   }

   public CompoundTag damageTypesRegistry() {
      return this.damageTypesRegistry.copy();
   }
}
