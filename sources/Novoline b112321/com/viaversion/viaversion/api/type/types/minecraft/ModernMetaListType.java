package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.AbstractMetaListType;
import io.netty.buffer.ByteBuf;

public abstract class ModernMetaListType extends AbstractMetaListType {
   protected void writeEnd(Type var1, ByteBuf var2) throws Exception {
      var1.write(var2, (Object)null);
   }
}
