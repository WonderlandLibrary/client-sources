package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;
import net.Gh;

public class ByteType extends Type implements TypeConverter {
   public ByteType() {
      super("Byte", Byte.class);
   }

   public Byte read(ByteBuf var1) {
      return Byte.valueOf(var1.readByte());
   }

   public void write(ByteBuf var1, Byte var2) {
      var1.writeByte(var2.byteValue());
   }

   public Byte from(Object var1) {
      String var2 = Gh.b();
      return var1 instanceof Number?Byte.valueOf(((Number)var1).byteValue()):(var1 instanceof Boolean?Byte.valueOf((byte)(((Boolean)var1).booleanValue()?1:0)):(Byte)var1);
   }
}
