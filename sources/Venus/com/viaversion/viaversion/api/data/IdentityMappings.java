/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.data.Mappings;

public class IdentityMappings
implements Mappings {
    private final int size;
    private final int mappedSize;

    public IdentityMappings(int n, int n2) {
        this.size = n;
        this.mappedSize = n2;
    }

    @Override
    public int getNewId(int n) {
        return n >= 0 && n < this.size ? n : -1;
    }

    @Override
    public void setNewId(int n, int n2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int mappedSize() {
        return this.mappedSize;
    }

    @Override
    public Mappings inverse() {
        return new IdentityMappings(this.mappedSize, this.size);
    }
}

