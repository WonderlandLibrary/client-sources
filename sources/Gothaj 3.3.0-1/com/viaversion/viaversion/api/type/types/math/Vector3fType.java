package com.viaversion.viaversion.api.type.types.math;

import com.viaversion.viaversion.api.minecraft.Vector3f;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class Vector3fType extends Type<Vector3f> {
   public Vector3fType() {
      super(Vector3f.class);
   }

   public Vector3f read(ByteBuf buffer) throws Exception {
      float x = buffer.readFloat();
      float y = buffer.readFloat();
      float z = buffer.readFloat();
      return new Vector3f(x, y, z);
   }

   public void write(ByteBuf buffer, Vector3f object) throws Exception {
      buffer.writeFloat(object.x());
      buffer.writeFloat(object.y());
      buffer.writeFloat(object.z());
   }
}
