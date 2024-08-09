/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.data;

import com.viaversion.viaversion.api.data.BiMappingsBase;
import com.viaversion.viaversion.api.data.Mappings;

public final class ItemMappings
extends BiMappingsBase {
    private ItemMappings(Mappings mappings, Mappings mappings2) {
        super(mappings, mappings2);
    }

    public static ItemMappings of(Mappings mappings, Mappings mappings2) {
        return new ItemMappings(mappings, mappings2);
    }

    @Override
    public void setNewId(int n, int n2) {
        this.mappings.setNewId(n, n2);
    }
}

