/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.api.type.types.version;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.minecraft.AbstractMetaListType;
import us.myles.ViaVersion.api.type.types.version.Types1_8;

public class MetadataList1_8Type
extends AbstractMetaListType {
    @Override
    protected Type<Metadata> getType() {
        return Types1_8.METADATA;
    }

    @Override
    protected void writeEnd(Type<Metadata> type, ByteBuf buffer) throws Exception {
        buffer.writeByte(127);
    }
}

