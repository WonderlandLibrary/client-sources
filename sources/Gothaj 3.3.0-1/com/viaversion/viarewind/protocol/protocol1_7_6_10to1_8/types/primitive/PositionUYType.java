package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.primitive;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;

public class PositionUYType<T extends Number> extends Type<Position> {
   private final Type<T> yType;
   private final IntFunction<T> toY;

   public PositionUYType(Type<T> yType, IntFunction<T> toY) {
      super(Position.class);
      this.yType = yType;
      this.toY = toY;
   }

   public Position read(ByteBuf buffer) throws Exception {
      int x = buffer.readInt();
      int y = this.yType.read(buffer).intValue();
      int z = buffer.readInt();
      return new Position(x, y, z);
   }

   public void write(ByteBuf buffer, Position value) throws Exception {
      buffer.writeInt(value.x());
      this.yType.write(buffer, this.toY.apply(value.y()));
      buffer.writeInt(value.z());
   }
}
