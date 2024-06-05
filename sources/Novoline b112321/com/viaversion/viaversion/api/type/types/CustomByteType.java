package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.PartialType;
import io.netty.buffer.ByteBuf;
import net.Gh;

public class CustomByteType extends PartialType {
   public CustomByteType(Integer var1) {
      super(var1, "byte[]", byte[].class);
   }

   public byte[] read(ByteBuf var1, Integer var2) throws Exception {
      String var3 = Gh.b();
      if(var1.readableBytes() < var2.intValue()) {
         throw new RuntimeException("Readable bytes does not match expected!");
      } else {
         byte[] var4 = new byte[var2.intValue()];
         var1.readBytes(var4);
         return var4;
      }
   }

   public void write(ByteBuf var1, Integer var2, byte[] var3) throws Exception {
      var1.writeBytes(var3);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
