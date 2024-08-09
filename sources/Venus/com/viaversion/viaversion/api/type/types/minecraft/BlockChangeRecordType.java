/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BlockChangeRecordType
extends Type<BlockChangeRecord> {
    public BlockChangeRecordType() {
        super(BlockChangeRecord.class);
    }

    @Override
    public BlockChangeRecord read(ByteBuf byteBuf) throws Exception {
        short s = Type.SHORT.readPrimitive(byteBuf);
        int n = Type.VAR_INT.readPrimitive(byteBuf);
        return new BlockChangeRecord1_8(s >> 12 & 0xF, s & 0xFF, s >> 8 & 0xF, n);
    }

    @Override
    public void write(ByteBuf byteBuf, BlockChangeRecord blockChangeRecord) throws Exception {
        Type.SHORT.writePrimitive(byteBuf, (short)(blockChangeRecord.getSectionX() << 12 | blockChangeRecord.getSectionZ() << 8 | blockChangeRecord.getY()));
        Type.VAR_INT.writePrimitive(byteBuf, blockChangeRecord.getBlockId());
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (BlockChangeRecord)object);
    }
}

