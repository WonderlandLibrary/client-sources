package com.viaversion.viaversion.api.type.types.math;

import com.viaversion.viaversion.api.minecraft.metadata.ChunkPosition;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class ChunkPositionType extends Type<ChunkPosition> {
   public ChunkPositionType() {
      super(ChunkPosition.class);
   }

   public ChunkPosition read(ByteBuf buffer) throws Exception {
      long chunkKey = Type.LONG.readPrimitive(buffer);
      return new ChunkPosition(chunkKey);
   }

   public void write(ByteBuf buffer, ChunkPosition chunkPosition) throws Exception {
      Type.LONG.writePrimitive(buffer, chunkPosition.chunkKey());
   }
}
