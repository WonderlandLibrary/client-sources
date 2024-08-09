/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.type.Type;

public abstract class BaseChunkBulkType
extends Type<Chunk[]> {
    protected BaseChunkBulkType() {
        super(Chunk[].class);
    }

    protected BaseChunkBulkType(String string) {
        super(string, Chunk[].class);
    }

    @Override
    public Class<? extends Type> getBaseClass() {
        return BaseChunkBulkType.class;
    }
}

