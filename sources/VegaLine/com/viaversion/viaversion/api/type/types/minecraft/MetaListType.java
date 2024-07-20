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

    public MetaListType(Type<Metadata> type2) {
        Preconditions.checkNotNull(type2);
        this.type = type2;
    }

    @Override
    public List<Metadata> read(ByteBuf buffer) throws Exception {
        Metadata meta;
        ArrayList<Metadata> list = new ArrayList<Metadata>();
        do {
            if ((meta = (Metadata)this.type.read(buffer)) == null) continue;
            list.add(meta);
        } while (meta != null);
        return list;
    }

    @Override
    public void write(ByteBuf buffer, List<Metadata> object) throws Exception {
        for (Metadata metadata : object) {
            this.type.write(buffer, metadata);
        }
        this.type.write(buffer, null);
    }
}

