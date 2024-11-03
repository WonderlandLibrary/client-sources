package com.viaversion.viaversion.api.type.types.block;

import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntityImpl;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;

public class BlockEntityType1_18 extends Type<BlockEntity> {
   public BlockEntityType1_18() {
      super(BlockEntity.class);
   }

   public BlockEntity read(ByteBuf buffer) throws Exception {
      byte xz = buffer.readByte();
      short y = buffer.readShort();
      int typeId = Type.VAR_INT.readPrimitive(buffer);
      CompoundTag tag = Type.NAMED_COMPOUND_TAG.read(buffer);
      return new BlockEntityImpl(xz, y, typeId, tag);
   }

   public void write(ByteBuf buffer, BlockEntity entity) throws Exception {
      buffer.writeByte(entity.packedXZ());
      buffer.writeShort(entity.y());
      Type.VAR_INT.writePrimitive(buffer, entity.typeId());
      Type.NAMED_COMPOUND_TAG.write(buffer, entity.tag());
   }
}
