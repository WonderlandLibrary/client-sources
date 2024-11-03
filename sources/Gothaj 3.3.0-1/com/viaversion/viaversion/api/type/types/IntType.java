package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class IntType extends Type<Integer> implements TypeConverter<Integer> {
   public IntType() {
      super(Integer.class);
   }

   public Integer read(ByteBuf buffer) {
      return buffer.readInt();
   }

   public void write(ByteBuf buffer, Integer object) {
      buffer.writeInt(object);
   }

   public Integer from(Object o) {
      if (o instanceof Number) {
         return ((Number)o).intValue();
      } else {
         return o instanceof Boolean ? (Boolean)o ? 1 : 0 : (Integer)o;
      }
   }
}
