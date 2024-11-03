package com.viaversion.viaversion.api.type.types.metadata;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_12;

public class MetadataType1_12 extends ModernMetaType {
   @Override
   protected MetaType getType(int index) {
      return MetaType1_12.byId(index);
   }
}
