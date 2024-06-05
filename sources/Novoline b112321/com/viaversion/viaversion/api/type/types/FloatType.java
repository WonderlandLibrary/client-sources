package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;
import net.Gh;

public class FloatType extends Type implements TypeConverter {
   public FloatType() {
      super("Float", Float.class);
   }

   public float readPrimitive(ByteBuf var1) {
      return var1.readFloat();
   }

   public void writePrimitive(ByteBuf var1, float var2) {
      var1.writeFloat(var2);
   }

   /** @deprecated */
   @Deprecated
   public Float read(ByteBuf var1) {
      return Float.valueOf(var1.readFloat());
   }

   /** @deprecated */
   @Deprecated
   public void write(ByteBuf var1, Float var2) {
      var1.writeFloat(var2.floatValue());
   }

   public Float from(Object var1) {
      String var2 = Gh.b();
      return var1 instanceof Number?Float.valueOf(((Number)var1).floatValue()):(var1 instanceof Boolean?Float.valueOf(((Boolean)var1).booleanValue()?1.0F:0.0F):(Float)var1);
   }
}
