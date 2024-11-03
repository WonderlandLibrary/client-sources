package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class VarIntType extends Type<Integer> implements TypeConverter<Integer> {
   private static final int CONTINUE_BIT = 128;
   private static final int VALUE_BITS = 127;
   private static final int MULTI_BYTE_BITS = -128;
   private static final int MAX_BYTES = 5;

   public VarIntType() {
      super("VarInt", Integer.class);
   }

   public int readPrimitive(ByteBuf buffer) {
      int value = 0;
      int bytes = 0;

      byte in;
      do {
         in = buffer.readByte();
         value |= (in & 127) << bytes++ * 7;
         if (bytes > 5) {
            throw new RuntimeException("VarInt too big");
         }
      } while ((in & 128) == 128);

      return value;
   }

   public void writePrimitive(ByteBuf buffer, int value) {
      while ((value & -128) != 0) {
         buffer.writeByte(value & 127 | 128);
         value >>>= 7;
      }

      buffer.writeByte(value);
   }

   @Deprecated
   public Integer read(ByteBuf buffer) {
      return this.readPrimitive(buffer);
   }

   @Deprecated
   public void write(ByteBuf buffer, Integer object) {
      this.writePrimitive(buffer, object);
   }

   public Integer from(Object o) {
      if (o instanceof Number) {
         return ((Number)o).intValue();
      } else {
         return o instanceof Boolean ? (Boolean)o ? 1 : 0 : (Integer)o;
      }
   }
}
