/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.data.entity;

import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.data.entity.StoredEntityDataImpl;

public final class TrackedEntityImpl
implements TrackedEntity {
    private final EntityType entityType;
    private StoredEntityData data;
    private boolean sentMetadata;

    public TrackedEntityImpl(EntityType entityType) {
        this.entityType = entityType;
    }

    @Override
    public EntityType entityType() {
        return this.entityType;
    }

    @Override
    public StoredEntityData data() {
        if (this.data == null) {
            this.data = new StoredEntityDataImpl(this.entityType);
        }
        return this.data;
    }

    @Override
    public boolean hasData() {
        return this.data != null;
    }

    @Override
    public boolean hasSentMetadata() {
        return this.sentMetadata;
    }

    @Override
    public void sentMetadata(boolean bl) {
        this.sentMetadata = bl;
    }
}

