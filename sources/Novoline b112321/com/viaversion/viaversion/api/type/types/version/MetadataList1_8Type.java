package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.AbstractMetaListType;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import io.netty.buffer.ByteBuf;

public class MetadataList1_8Type extends AbstractMetaListType {
   protected Type getType() {
      return Types1_8.METADATA;
   }

   protected void writeEnd(Type var1, ByteBuf var2) throws Exception {
      var2.writeByte(127);
   }
}
