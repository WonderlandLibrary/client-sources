/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.data;

import com.viaversion.viaversion.api.data.BiMappingsBase;
import com.viaversion.viaversion.api.data.Mappings;

public final class ItemMappings
extends BiMappingsBase {
    private ItemMappings(Mappings mappings, Mappings inverse) {
        super(mappings, inverse);
    }

    public static ItemMappings of(Mappings mappings, Mappings inverse) {
        return new ItemMappings(mappings, inverse);
    }

    @Override
    public void setNewId(int id, int mappedId) {
        this.mappings.setNewId(id, mappedId);
    }
}

