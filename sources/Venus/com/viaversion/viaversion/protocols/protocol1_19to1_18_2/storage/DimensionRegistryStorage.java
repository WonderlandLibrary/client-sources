/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class DimensionRegistryStorage
implements StorableObject {
    private Map<CompoundTag, String> dimensions;

    public @Nullable String dimensionKey(CompoundTag compoundTag) {
        return this.dimensions.get(compoundTag);
    }

    public void setDimensions(Map<CompoundTag, String> map) {
        this.dimensions = map;
    }

    public Map<CompoundTag, String> dimensions() {
        return this.dimensions;
    }

    @Override
    public boolean clearOnServerSwitch() {
        return true;
    }
}

