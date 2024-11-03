package com.viaversion.viaversion.api.type.types.metadata;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;

public class MetadataType1_8 extends OldMetaType {
   @Override
   protected MetaType getType(int index) {
      return MetaType1_8.byId(index);
   }
}
