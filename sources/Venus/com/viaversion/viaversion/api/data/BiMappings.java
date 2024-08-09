/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.data.BiMappingsBase;
import com.viaversion.viaversion.api.data.Mappings;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface BiMappings
extends Mappings {
    @Override
    public BiMappings inverse();

    public static BiMappings of(Mappings mappings) {
        return BiMappings.of(mappings, mappings.inverse());
    }

    public static BiMappings of(Mappings mappings, Mappings mappings2) {
        return new BiMappingsBase(mappings, mappings2);
    }

    @Override
    default public Mappings inverse() {
        return this.inverse();
    }
}

