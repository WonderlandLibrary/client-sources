package com.viaversion.viaversion.api.type.types.math;

import com.viaversion.viaversion.api.minecraft.GlobalPosition;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class GlobalPositionType extends Type<GlobalPosition> {
   public GlobalPositionType() {
      super(GlobalPosition.class);
   }

   public GlobalPosition read(ByteBuf buffer) throws Exception {
      String dimension = Type.STRING.read(buffer);
      return Type.POSITION1_14.read(buffer).withDimension(dimension);
   }

   public void write(ByteBuf buffer, GlobalPosition object) throws Exception {
      Type.STRING.write(buffer, object.dimension());
      Type.POSITION1_14.write(buffer, object);
   }

   public static final class OptionalGlobalPositionType extends OptionalType<GlobalPosition> {
      public OptionalGlobalPositionType() {
         super(Type.GLOBAL_POSITION);
      }
   }
}
