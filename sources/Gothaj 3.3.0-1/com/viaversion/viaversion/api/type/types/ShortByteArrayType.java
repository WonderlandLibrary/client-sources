package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class ShortByteArrayType extends Type<byte[]> {
   public ShortByteArrayType() {
      super(byte[].class);
   }

   public void write(ByteBuf buffer, byte[] object) throws Exception {
      buffer.writeShort(object.length);
      buffer.writeBytes(object);
   }

   public byte[] read(ByteBuf buffer) throws Exception {
      byte[] array = new byte[buffer.readShort()];
      buffer.readBytes(array);
      return array;
   }
}
