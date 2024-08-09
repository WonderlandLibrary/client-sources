/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.types.minecraft.MetaTypeTemplate;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class OldMetaType
extends MetaTypeTemplate {
    private static final int END = 127;

    @Override
    public Metadata read(ByteBuf byteBuf) throws Exception {
        byte by = byteBuf.readByte();
        if (by == 127) {
            return null;
        }
        MetaType metaType = this.getType((by & 0xE0) >> 5);
        return new Metadata(by & 0x1F, metaType, metaType.type().read(byteBuf));
    }

    protected abstract MetaType getType(int var1);

    @Override
    public void write(ByteBuf byteBuf, Metadata metadata) throws Exception {
        if (metadata == null) {
            byteBuf.writeByte(127);
        } else {
            int n = (metadata.metaType().typeId() << 5 | metadata.id() & 0x1F) & 0xFF;
            byteBuf.writeByte(n);
            metadata.metaType().type().write(byteBuf, metadata.getValue());
        }
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Metadata)object);
    }
}

