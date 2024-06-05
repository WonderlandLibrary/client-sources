package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_16_2;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class VarLongBlockChangeRecordType extends Type {
   public VarLongBlockChangeRecordType() {
      super("BlockChangeRecord", BlockChangeRecord.class);
   }

   public BlockChangeRecord read(ByteBuf var1) throws Exception {
      long var2 = Type.VAR_LONG.readPrimitive(var1);
      short var4 = (short)((int)(var2 & 4095L));
      return new BlockChangeRecord1_16_2(var4 >>> 8 & 15, var4 & 15, var4 >>> 4 & 15, (int)(var2 >>> 12));
   }

   public void write(ByteBuf var1, BlockChangeRecord var2) throws Exception {
      short var3 = (short)(var2.getSectionX() << 8 | var2.getSectionZ() << 4 | var2.getSectionY());
      Type.VAR_LONG.writePrimitive(var1, (long)var2.getBlockId() << 12 | (long)var3);
   }
}
