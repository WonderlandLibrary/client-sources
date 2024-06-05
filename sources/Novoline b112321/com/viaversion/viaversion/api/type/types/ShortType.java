package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;
import net.Gh;

public class ShortType extends Type implements TypeConverter {
   public ShortType() {
      super("Short", Short.class);
   }

   public short readPrimitive(ByteBuf var1) {
      return var1.readShort();
   }

   public void writePrimitive(ByteBuf var1, short var2) {
      var1.writeShort(var2);
   }

   /** @deprecated */
   @Deprecated
   public Short read(ByteBuf var1) {
      return Short.valueOf(var1.readShort());
   }

   /** @deprecated */
   @Deprecated
   public void write(ByteBuf var1, Short var2) {
      var1.writeShort(var2.shortValue());
   }

   public Short from(Object var1) {
      String var2 = Gh.b();
      return var1 instanceof Number?Short.valueOf(((Number)var1).shortValue()):(var1 instanceof Boolean?Short.valueOf((short)(((Boolean)var1).booleanValue()?1:0)):(Short)var1);
   }
}
