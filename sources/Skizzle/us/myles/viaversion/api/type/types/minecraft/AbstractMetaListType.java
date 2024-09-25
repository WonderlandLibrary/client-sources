/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.api.type.types.minecraft;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.minecraft.MetaListTypeTemplate;

public abstract class AbstractMetaListType
extends MetaListTypeTemplate {
    protected abstract Type<Metadata> getType();

    @Override
    public List<Metadata> read(ByteBuf buffer) throws Exception {
        Metadata meta;
        Type<Metadata> type = this.getType();
        ArrayList<Metadata> list = new ArrayList<Metadata>();
        do {
            if ((meta = (Metadata)type.read(buffer)) == null) continue;
            list.add(meta);
        } while (meta != null);
        return list;
    }

    @Override
    public void write(ByteBuf buffer, List<Metadata> object) throws Exception {
        Type<Metadata> type = this.getType();
        for (Metadata metadata : object) {
            type.write(buffer, metadata);
        }
        this.writeEnd(type, buffer);
    }

    protected abstract void writeEnd(Type<Metadata> var1, ByteBuf var2) throws Exception;
}

