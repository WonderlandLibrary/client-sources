/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.api.type.types.minecraft;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.minecraft.AbstractMetaListType;

public abstract class ModernMetaListType
extends AbstractMetaListType {
    @Override
    protected void writeEnd(Type<Metadata> type, ByteBuf buffer) throws Exception {
        type.write(buffer, null);
    }
}

