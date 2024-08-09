/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_12;
import com.viaversion.viaversion.api.type.types.minecraft.ModernMetaType;

public class Metadata1_12Type
extends ModernMetaType {
    @Override
    protected MetaType getType(int n) {
        return MetaType1_12.byId(n);
    }
}

