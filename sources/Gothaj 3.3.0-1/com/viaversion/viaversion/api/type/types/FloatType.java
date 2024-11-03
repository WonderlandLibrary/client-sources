package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class FloatType extends Type<Float> implements TypeConverter<Float> {
   public FloatType() {
      super(Float.class);
   }

   public float readPrimitive(ByteBuf buffer) {
      return buffer.readFloat();
   }

   public void writePrimitive(ByteBuf buffer, float object) {
      buffer.writeFloat(object);
   }

   @Deprecated
   public Float read(ByteBuf buffer) {
      return buffer.readFloat();
   }

   @Deprecated
   public void write(ByteBuf buffer, Float object) {
      buffer.writeFloat(object);
   }

   public Float from(Object o) {
      if (o instanceof Number) {
         return ((Number)o).floatValue();
      } else {
         return o instanceof Boolean ? (Boolean)o ? 1.0F : 0.0F : (Float)o;
      }
   }

   public static final class OptionalFloatType extends OptionalType<Float> {
      public OptionalFloatType() {
         super(Type.FLOAT);
      }
   }
}
