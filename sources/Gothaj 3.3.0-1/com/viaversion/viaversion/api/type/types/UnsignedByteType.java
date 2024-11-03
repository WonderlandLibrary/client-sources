package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class UnsignedByteType extends Type<Short> implements TypeConverter<Short> {
   public UnsignedByteType() {
      super("Unsigned Byte", Short.class);
   }

   public Short read(ByteBuf buffer) {
      return buffer.readUnsignedByte();
   }

   public void write(ByteBuf buffer, Short object) {
      buffer.writeByte(object);
   }

   public Short from(Object o) {
      if (o instanceof Number) {
         return ((Number)o).shortValue();
      } else {
         return o instanceof Boolean ? Short.valueOf((short)((Boolean)o ? 1 : 0)) : (Short)o;
      }
   }
}
