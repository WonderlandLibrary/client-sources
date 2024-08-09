/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.MetaListTypeTemplate;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;

public final class MetaListType
extends MetaListTypeTemplate {
    private final Type<Metadata> type;

    public MetaListType(Type<Metadata> type) {
        Preconditions.checkNotNull(type);
        this.type = type;
    }

    @Override
    public List<Metadata> read(ByteBuf byteBuf) throws Exception {
        Metadata metadata;
        ArrayList<Metadata> arrayList = new ArrayList<Metadata>();
        do {
            if ((metadata = (Metadata)this.type.read(byteBuf)) == null) continue;
            arrayList.add(metadata);
        } while (metadata != null);
        return arrayList;
    }

    @Override
    public void write(ByteBuf byteBuf, List<Metadata> list) throws Exception {
        for (Metadata metadata : list) {
            this.type.write(byteBuf, metadata);
        }
        this.type.write(byteBuf, null);
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (List)object);
    }
}

