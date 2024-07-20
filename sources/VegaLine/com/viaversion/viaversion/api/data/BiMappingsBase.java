/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.data.BiMappings;
import com.viaversion.viaversion.api.data.Mappings;

public class BiMappingsBase
implements BiMappings {
    protected final Mappings mappings;
    private final BiMappingsBase inverse;

    protected BiMappingsBase(Mappings mappings, Mappings inverse) {
        this.mappings = mappings;
        this.inverse = new BiMappingsBase(inverse, this);
    }

    private BiMappingsBase(Mappings mappings, BiMappingsBase inverse) {
        this.mappings = mappings;
        this.inverse = inverse;
    }

    @Override
    public int getNewId(int id) {
        return this.mappings.getNewId(id);
    }

    @Override
    public void setNewId(int id, int mappedId) {
        this.mappings.setNewId(id, mappedId);
        this.inverse.mappings.setNewId(mappedId, id);
    }

    @Override
    public int size() {
        return this.mappings.size();
    }

    @Override
    public int mappedSize() {
        return this.mappings.mappedSize();
    }

    @Override
    public BiMappings inverse() {
        return this.inverse;
    }
}

