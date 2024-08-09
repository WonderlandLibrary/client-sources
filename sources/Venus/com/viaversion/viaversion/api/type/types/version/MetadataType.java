/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaTypes;
import com.viaversion.viaversion.api.type.types.minecraft.ModernMetaType;

public final class MetadataType
extends ModernMetaType {
    private final MetaTypes metaTypes;

    public MetadataType(MetaTypes metaTypes) {
        this.metaTypes = metaTypes;
    }

    @Override
    protected MetaType getType(int n) {
        return this.metaTypes.byId(n);
    }
}

