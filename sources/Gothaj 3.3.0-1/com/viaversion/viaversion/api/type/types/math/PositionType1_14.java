package com.viaversion.viaversion.api.type.types.math;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class PositionType1_14 extends Type<Position> {
   public PositionType1_14() {
      super(Position.class);
   }

   public Position read(ByteBuf buffer) {
      long val = buffer.readLong();
      long x = val >> 38;
      long y = val << 52 >> 52;
      long z = val << 26 >> 38;
      return new Position((int)x, (int)y, (int)z);
   }

   public void write(ByteBuf buffer, Position object) {
      buffer.writeLong(((long)object.x() & 67108863L) << 38 | (long)(object.y() & 4095) | ((long)object.z() & 67108863L) << 12);
   }

   public static final class OptionalPosition1_14Type extends OptionalType<Position> {
      public OptionalPosition1_14Type() {
         super(Type.POSITION1_14);
      }
   }
}
