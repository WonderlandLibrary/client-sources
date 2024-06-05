package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;
import java.util.List;

public abstract class MetaListTypeTemplate extends Type {
   protected MetaListTypeTemplate() {
      super("MetaData List", List.class);
   }

   public Class getBaseClass() {
      return MetaListTypeTemplate.class;
   }
}
