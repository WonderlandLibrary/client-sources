/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.MetaTypeTemplate;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class ModernMetaType
extends MetaTypeTemplate {
    private static final int END = 255;

    @Override
    public Metadata read(ByteBuf byteBuf) throws Exception {
        short s = byteBuf.readUnsignedByte();
        if (s == 255) {
            return null;
        }
        MetaType metaType = this.getType(Type.VAR_INT.readPrimitive(byteBuf));
        return new Metadata(s, metaType, metaType.type().read(byteBuf));
    }

    protected abstract MetaType getType(int var1);

    @Override
    public void write(ByteBuf byteBuf, Metadata metadata) throws Exception {
        if (metadata == null) {
            byteBuf.writeByte(255);
        } else {
            byteBuf.writeByte(metadata.id());
            MetaType metaType = metadata.metaType();
            Type.VAR_INT.writePrimitive(byteBuf, metaType.typeId());
            metaType.type().write(byteBuf, metadata.getValue());
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

