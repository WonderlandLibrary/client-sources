package com.viaversion.viaversion.api.type.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class ByteArrayType extends Type {
   public ByteArrayType() {
      super("byte[]", byte[].class);
   }

   public void write(ByteBuf var1, byte[] var2) throws Exception {
      Type.VAR_INT.writePrimitive(var1, var2.length);
      var1.writeBytes(var2);
   }

   public byte[] read(ByteBuf var1) throws Exception {
      int var2 = Type.VAR_INT.readPrimitive(var1);
      Preconditions.checkArgument(var1.isReadable(var2), "Length is fewer than readable bytes");
      byte[] var3 = new byte[var2];
      var1.readBytes(var3);
      return var3;
   }
}
