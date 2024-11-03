package com.viaversion.viaversion.api.type.types.math;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class PositionType1_8 extends Type<Position> {
   public PositionType1_8() {
      super(Position.class);
   }

   public Position read(ByteBuf buffer) {
      long val = buffer.readLong();
      long x = val >> 38;
      long y = val << 26 >> 52;
      long z = val << 38 >> 38;
      return new Position((int)x, (short)((int)y), (int)z);
   }

   public void write(ByteBuf buffer, Position object) {
      buffer.writeLong(((long)object.x() & 67108863L) << 38 | ((long)object.y() & 4095L) << 26 | (long)object.z() & 67108863L);
   }

   public static final class OptionalPositionType extends OptionalType<Position> {
      public OptionalPositionType() {
         super(Type.POSITION1_8);
      }
   }
}
