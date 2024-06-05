package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;
import net.Gh;

public class UnsignedByteType extends Type implements TypeConverter {
   public UnsignedByteType() {
      super("Unsigned Byte", Short.class);
   }

   public Short read(ByteBuf var1) {
      return Short.valueOf(var1.readUnsignedByte());
   }

   public void write(ByteBuf var1, Short var2) {
      var1.writeByte(var2.shortValue());
   }

   public Short from(Object var1) {
      String var2 = Gh.b();
      return var1 instanceof Number?Short.valueOf(((Number)var1).shortValue()):(var1 instanceof Boolean?Short.valueOf((short)(((Boolean)var1).booleanValue()?1:0)):(Short)var1);
   }
}
