/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.type.types.minecraft.OldMetaType;

public class Metadata1_8Type
extends OldMetaType {
    @Override
    protected MetaType getType(int n) {
        return MetaType1_8.byId(n);
    }
}

