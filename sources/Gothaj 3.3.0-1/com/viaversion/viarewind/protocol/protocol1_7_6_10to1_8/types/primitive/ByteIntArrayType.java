package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.primitive;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class ByteIntArrayType extends Type<int[]> {
   public ByteIntArrayType() {
      super(int[].class);
   }

   public int[] read(ByteBuf byteBuf) throws Exception {
      byte size = byteBuf.readByte();
      int[] array = new int[size];

      for (byte i = 0; i < size; i++) {
         array[i] = byteBuf.readInt();
      }

      return array;
   }

   public void write(ByteBuf byteBuf, int[] array) throws Exception {
      byteBuf.writeByte(array.length);

      for (int i : array) {
         byteBuf.writeInt(i);
      }
   }
}
