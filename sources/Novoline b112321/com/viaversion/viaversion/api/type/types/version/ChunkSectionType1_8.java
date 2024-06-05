package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.nio.ByteOrder;
import net.aes;
import net.aiV;

public class ChunkSectionType1_8 extends Type {
   public ChunkSectionType1_8() {
      super("Chunk Section Type", aiV.class);
   }

   public aiV a(ByteBuf var1) throws Exception {
      aes.b();
      aiV var3 = new aiV();
      var3.a(0);
      ByteBuf var4 = var1.order(ByteOrder.LITTLE_ENDIAN);
      int var5 = 0;
      if(var5 < 4096) {
         short var6 = var4.readShort();
         int var7 = var6 >> 4;
         int var8 = var6 & 15;
         var3.a(var5, var7, var8);
         ++var5;
      }

      return var3;
   }

   public void a(ByteBuf var1, aiV var2) throws Exception {
      throw new UnsupportedOperationException();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
