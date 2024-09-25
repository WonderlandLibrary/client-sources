/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.api.type.types.minecraft;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.minecraft.metadata.MetaType;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.type.types.minecraft.MetaTypeTemplate;

public abstract class ModernMetaType
extends MetaTypeTemplate {
    @Override
    public Metadata read(ByteBuf buffer) throws Exception {
        short index = buffer.readUnsignedByte();
        if (index == 255) {
            return null;
        }
        MetaType type = this.getType(buffer.readByte());
        return new Metadata(index, type, type.getType().read(buffer));
    }

    protected abstract MetaType getType(int var1);

    @Override
    public void write(ByteBuf buffer, Metadata object) throws Exception {
        if (object == null) {
            buffer.writeByte(255);
        } else {
            buffer.writeByte(object.getId());
            MetaType type = object.getMetaType();
            buffer.writeByte(type.getTypeID());
            type.getType().write(buffer, object.getValue());
        }
    }
}

