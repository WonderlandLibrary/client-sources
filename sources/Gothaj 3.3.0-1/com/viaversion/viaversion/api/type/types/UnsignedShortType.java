package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class UnsignedShortType extends Type<Integer> implements TypeConverter<Integer> {
   public UnsignedShortType() {
      super(Integer.class);
   }

   public Integer read(ByteBuf buffer) {
      return buffer.readUnsignedShort();
   }

   public void write(ByteBuf buffer, Integer object) {
      buffer.writeShort(object);
   }

   public Integer from(Object o) {
      if (o instanceof Number) {
         return ((Number)o).intValue();
      } else {
         return o instanceof Boolean ? (Boolean)o ? 1 : 0 : (Integer)o;
      }
   }
}
