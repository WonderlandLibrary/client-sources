package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class BooleanType extends Type<Boolean> implements TypeConverter<Boolean> {
   public BooleanType() {
      super(Boolean.class);
   }

   public Boolean read(ByteBuf buffer) {
      return buffer.readBoolean();
   }

   public void write(ByteBuf buffer, Boolean object) {
      buffer.writeBoolean(object);
   }

   public Boolean from(Object o) {
      return o instanceof Number ? ((Number)o).intValue() == 1 : (Boolean)o;
   }
}
