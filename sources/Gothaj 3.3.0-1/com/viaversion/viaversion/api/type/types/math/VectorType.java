package com.viaversion.viaversion.api.type.types.math;

import com.viaversion.viaversion.api.minecraft.Vector;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class VectorType extends Type<Vector> {
   public VectorType() {
      super(Vector.class);
   }

   public Vector read(ByteBuf buffer) throws Exception {
      int x = Type.INT.read(buffer);
      int y = Type.INT.read(buffer);
      int z = Type.INT.read(buffer);
      return new Vector(x, y, z);
   }

   public void write(ByteBuf buffer, Vector object) throws Exception {
      Type.INT.write(buffer, object.blockX());
      Type.INT.write(buffer, object.blockY());
      Type.INT.write(buffer, object.blockZ());
   }
}
