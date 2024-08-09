/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.NBTIO;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class NBTType
extends Type<CompoundTag> {
    private static final int MAX_NBT_BYTES = 0x200000;
    private static final int MAX_NESTING_LEVEL = 512;

    public NBTType() {
        super(CompoundTag.class);
    }

    @Override
    public CompoundTag read(ByteBuf byteBuf) throws Exception {
        int n = byteBuf.readerIndex();
        byte by = byteBuf.readByte();
        if (by == 0) {
            return null;
        }
        byteBuf.readerIndex(n);
        return NBTIO.readTag(new ByteBufInputStream(byteBuf), TagLimiter.create(0x200000, 512));
    }

    @Override
    public void write(ByteBuf byteBuf, CompoundTag compoundTag) throws Exception {
        if (compoundTag == null) {
            byteBuf.writeByte(0);
        } else {
            ByteBufOutputStream byteBufOutputStream = new ByteBufOutputStream(byteBuf);
            NBTIO.writeTag(byteBufOutputStream, compoundTag);
        }
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (CompoundTag)object);
    }
}

