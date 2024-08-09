/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.data.entity;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.ClientEntityIdChangeListener;
import com.viaversion.viaversion.api.data.entity.DimensionData;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.data.entity.TrackedEntityImpl;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap;
import java.util.Collections;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class EntityTrackerBase
implements EntityTracker,
ClientEntityIdChangeListener {
    private final Int2ObjectMap<TrackedEntity> entities = Int2ObjectSyncMap.hashmap();
    private final UserConnection connection;
    private final EntityType playerType;
    private int clientEntityId = -1;
    private int currentWorldSectionHeight = 16;
    private int currentMinY;
    private String currentWorld;
    private int biomesSent = -1;
    private Map<String, DimensionData> dimensions = Collections.emptyMap();

    public EntityTrackerBase(UserConnection userConnection, @Nullable EntityType entityType) {
        this.connection = userConnection;
        this.playerType = entityType;
    }

    @Override
    public UserConnection user() {
        return this.connection;
    }

    @Override
    public void addEntity(int n, EntityType entityType) {
        this.entities.put(n, (TrackedEntity)new TrackedEntityImpl(entityType));
    }

    @Override
    public boolean hasEntity(int n) {
        return this.entities.containsKey(n);
    }

    @Override
    public @Nullable TrackedEntity entity(int n) {
        return (TrackedEntity)this.entities.get(n);
    }

    @Override
    public @Nullable EntityType entityType(int n) {
        TrackedEntity trackedEntity = (TrackedEntity)this.entities.get(n);
        return trackedEntity != null ? trackedEntity.entityType() : null;
    }

    @Override
    public @Nullable StoredEntityData entityData(int n) {
        TrackedEntity trackedEntity = (TrackedEntity)this.entities.get(n);
        return trackedEntity != null ? trackedEntity.data() : null;
    }

    @Override
    public @Nullable StoredEntityData entityDataIfPresent(int n) {
        TrackedEntity trackedEntity = (TrackedEntity)this.entities.get(n);
        return trackedEntity != null && trackedEntity.hasData() ? trackedEntity.data() : null;
    }

    @Override
    public void removeEntity(int n) {
        this.entities.remove(n);
    }

    @Override
    public void clearEntities() {
        this.entities.clear();
    }

    @Override
    public int clientEntityId() {
        return this.clientEntityId;
    }

    @Override
    public void setClientEntityId(int n) {
        TrackedEntity trackedEntity;
        Preconditions.checkNotNull(this.playerType);
        if (this.clientEntityId != -1 && (trackedEntity = (TrackedEntity)this.entities.remove(this.clientEntityId)) != null) {
            this.entities.put(n, trackedEntity);
        } else {
            this.entities.put(n, (TrackedEntity)new TrackedEntityImpl(this.playerType));
        }
        this.clientEntityId = n;
    }

    @Override
    public boolean trackClientEntity() {
        if (this.clientEntityId != -1) {
            this.entities.put(this.clientEntityId, (TrackedEntity)new TrackedEntityImpl(this.playerType));
            return false;
        }
        return true;
    }

    @Override
    public int currentWorldSectionHeight() {
        return this.currentWorldSectionHeight;
    }

    @Override
    public void setCurrentWorldSectionHeight(int n) {
        this.currentWorldSectionHeight = n;
    }

    @Override
    public int currentMinY() {
        return this.currentMinY;
    }

    @Override
    public void setCurrentMinY(int n) {
        this.currentMinY = n;
    }

    @Override
    public @Nullable String currentWorld() {
        return this.currentWorld;
    }

    @Override
    public void setCurrentWorld(String string) {
        this.currentWorld = string;
    }

    @Override
    public int biomesSent() {
        return this.biomesSent;
    }

    @Override
    public void setBiomesSent(int n) {
        this.biomesSent = n;
    }

    @Override
    public EntityType playerType() {
        return this.playerType;
    }

    @Override
    public @Nullable DimensionData dimensionData(String string) {
        return this.dimensions.get(string);
    }

    @Override
    public void setDimensions(Map<String, DimensionData> map) {
        this.dimensions = map;
    }
}

