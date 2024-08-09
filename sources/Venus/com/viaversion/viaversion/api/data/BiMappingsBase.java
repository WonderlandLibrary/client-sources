/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.data.BiMappings;
import com.viaversion.viaversion.api.data.Mappings;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BiMappingsBase
implements BiMappings {
    protected final Mappings mappings;
    private final BiMappingsBase inverse;

    protected BiMappingsBase(Mappings mappings, Mappings mappings2) {
        this.mappings = mappings;
        this.inverse = new BiMappingsBase(mappings2, this);
    }

    private BiMappingsBase(Mappings mappings, BiMappingsBase biMappingsBase) {
        this.mappings = mappings;
        this.inverse = biMappingsBase;
    }

    @Override
    public int getNewId(int n) {
        return this.mappings.getNewId(n);
    }

    @Override
    public void setNewId(int n, int n2) {
        this.mappings.setNewId(n, n2);
        this.inverse.mappings.setNewId(n2, n);
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

    @Override
    public Mappings inverse() {
        return this.inverse();
    }
}

