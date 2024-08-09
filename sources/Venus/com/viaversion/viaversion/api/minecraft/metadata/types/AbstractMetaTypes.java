/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.metadata.types;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaTypes;
import com.viaversion.viaversion.api.type.Type;

public abstract class AbstractMetaTypes
implements MetaTypes {
    private final MetaType[] values;

    protected AbstractMetaTypes(int n) {
        this.values = new MetaType[n];
    }

    @Override
    public MetaType byId(int n) {
        return this.values[n];
    }

    @Override
    public MetaType[] values() {
        return this.values;
    }

    protected MetaType add(int n, Type<?> type) {
        MetaType metaType;
        this.values[n] = metaType = MetaType.create(n, type);
        return metaType;
    }
}

