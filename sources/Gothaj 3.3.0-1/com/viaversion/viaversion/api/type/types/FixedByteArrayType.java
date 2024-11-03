package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class FixedByteArrayType extends Type<byte[]> {
   private final int arrayLength;

   public FixedByteArrayType(int arrayLength) {
      super(byte[].class);
      this.arrayLength = arrayLength;
   }

   public byte[] read(ByteBuf byteBuf) throws Exception {
      if (byteBuf.readableBytes() < this.arrayLength) {
         throw new RuntimeException("Readable bytes does not match expected!");
      } else {
         byte[] byteArray = new byte[this.arrayLength];
         byteBuf.readBytes(byteArray);
         return byteArray;
      }
   }

   public void write(ByteBuf byteBuf, byte[] bytes) throws Exception {
      byteBuf.writeBytes(bytes);
   }
}
