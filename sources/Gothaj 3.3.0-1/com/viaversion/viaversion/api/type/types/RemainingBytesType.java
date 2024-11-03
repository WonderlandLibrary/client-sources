package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class RemainingBytesType extends Type<byte[]> {
   public RemainingBytesType() {
      super(byte[].class);
   }

   public byte[] read(ByteBuf buffer) {
      byte[] array = new byte[buffer.readableBytes()];
      buffer.readBytes(array);
      return array;
   }

   public void write(ByteBuf buffer, byte[] object) {
      buffer.writeBytes(object);
   }
}
