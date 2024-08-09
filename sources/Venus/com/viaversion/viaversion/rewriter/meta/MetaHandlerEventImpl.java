/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.rewriter.meta;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MetaHandlerEventImpl
implements MetaHandlerEvent {
    private final UserConnection connection;
    private final TrackedEntity trackedEntity;
    private final int entityId;
    private final List<Metadata> metadataList;
    private final Metadata meta;
    private List<Metadata> extraData;
    private boolean cancel;

    public MetaHandlerEventImpl(UserConnection userConnection, @Nullable TrackedEntity trackedEntity, int n, Metadata metadata, List<Metadata> list) {
        this.connection = userConnection;
        this.trackedEntity = trackedEntity;
        this.entityId = n;
        this.meta = metadata;
        this.metadataList = list;
    }

    @Override
    public UserConnection user() {
        return this.connection;
    }

    @Override
    public int entityId() {
        return this.entityId;
    }

    @Override
    public @Nullable TrackedEntity trackedEntity() {
        return this.trackedEntity;
    }

    @Override
    public Metadata meta() {
        return this.meta;
    }

    @Override
    public void cancel() {
        this.cancel = true;
    }

    @Override
    public boolean cancelled() {
        return this.cancel;
    }

    @Override
    public @Nullable Metadata metaAtIndex(int n) {
        for (Metadata metadata : this.metadataList) {
            if (n != metadata.id()) continue;
            return metadata;
        }
        return null;
    }

    @Override
    public List<Metadata> metadataList() {
        return Collections.unmodifiableList(this.metadataList);
    }

    @Override
    public @Nullable List<Metadata> extraMeta() {
        return this.extraData;
    }

    @Override
    public void createExtraMeta(Metadata metadata) {
        (this.extraData != null ? this.extraData : (this.extraData = new ArrayList<Metadata>())).add(metadata);
    }
}

