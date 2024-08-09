/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntityImpl;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BlockEntityType1_18
extends Type<BlockEntity> {
    public BlockEntityType1_18() {
        super(BlockEntity.class);
    }

    @Override
    public BlockEntity read(ByteBuf byteBuf) throws Exception {
        byte by = byteBuf.readByte();
        short s = byteBuf.readShort();
        int n = Type.VAR_INT.readPrimitive(byteBuf);
        CompoundTag compoundTag = (CompoundTag)Type.NBT.read(byteBuf);
        return new BlockEntityImpl(by, s, n, compoundTag);
    }

    @Override
    public void write(ByteBuf byteBuf, BlockEntity blockEntity) throws Exception {
        byteBuf.writeByte(blockEntity.packedXZ());
        byteBuf.writeShort(blockEntity.y());
        Type.VAR_INT.writePrimitive(byteBuf, blockEntity.typeId());
        Type.NBT.write(byteBuf, blockEntity.tag());
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (BlockEntity)object);
    }
}

