/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_16_2;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class VarLongBlockChangeRecordType
extends Type<BlockChangeRecord> {
    public VarLongBlockChangeRecordType() {
        super(BlockChangeRecord.class);
    }

    @Override
    public BlockChangeRecord read(ByteBuf byteBuf) throws Exception {
        long l = Type.VAR_LONG.readPrimitive(byteBuf);
        short s = (short)(l & 0xFFFL);
        return new BlockChangeRecord1_16_2(s >>> 8 & 0xF, s & 0xF, s >>> 4 & 0xF, (int)(l >>> 12));
    }

    @Override
    public void write(ByteBuf byteBuf, BlockChangeRecord blockChangeRecord) throws Exception {
        short s = (short)(blockChangeRecord.getSectionX() << 8 | blockChangeRecord.getSectionZ() << 4 | blockChangeRecord.getSectionY());
        Type.VAR_LONG.writePrimitive(byteBuf, (long)blockChangeRecord.getBlockId() << 12 | (long)s);
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

