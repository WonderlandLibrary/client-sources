/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.rewriter.meta;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface MetaHandlerEvent {
    public UserConnection user();

    public int entityId();

    public @Nullable TrackedEntity trackedEntity();

    default public @Nullable EntityType entityType() {
        return this.trackedEntity() != null ? this.trackedEntity().entityType() : null;
    }

    default public int index() {
        return this.meta().id();
    }

    default public void setIndex(int n) {
        this.meta().setId(n);
    }

    public Metadata meta();

    public void cancel();

    public boolean cancelled();

    public @Nullable Metadata metaAtIndex(int var1);

    public List<Metadata> metadataList();

    public @Nullable List<Metadata> extraMeta();

    default public boolean hasExtraMeta() {
        return this.extraMeta() != null;
    }

    public void createExtraMeta(Metadata var1);
}

