/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.data.entity;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.DimensionData;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface EntityTracker {
    public UserConnection user();

    public void addEntity(int var1, EntityType var2);

    public boolean hasEntity(int var1);

    public @Nullable TrackedEntity entity(int var1);

    public @Nullable EntityType entityType(int var1);

    public void removeEntity(int var1);

    public void clearEntities();

    public @Nullable StoredEntityData entityData(int var1);

    public @Nullable StoredEntityData entityDataIfPresent(int var1);

    public int clientEntityId();

    public void setClientEntityId(int var1);

    public int currentWorldSectionHeight();

    public void setCurrentWorldSectionHeight(int var1);

    public int currentMinY();

    public void setCurrentMinY(int var1);

    public @Nullable String currentWorld();

    public void setCurrentWorld(String var1);

    public int biomesSent();

    public void setBiomesSent(int var1);

    public EntityType playerType();

    public @Nullable DimensionData dimensionData(String var1);

    public void setDimensions(Map<String, DimensionData> var1);

    public boolean trackClientEntity();
}

