/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class WrappedMetadata {
    private final List<Metadata> metadataList;

    public WrappedMetadata(List<Metadata> list) {
        this.metadataList = list;
    }

    public boolean has(Metadata metadata) {
        return this.metadataList.contains(metadata);
    }

    public void remove(Metadata metadata) {
        this.metadataList.remove(metadata);
    }

    public void remove(int n) {
        this.metadataList.removeIf(arg_0 -> WrappedMetadata.lambda$remove$0(n, arg_0));
    }

    public void add(Metadata metadata) {
        this.metadataList.add(metadata);
    }

    public @Nullable Metadata get(int n) {
        for (Metadata metadata : this.metadataList) {
            if (n != metadata.id()) continue;
            return metadata;
        }
        return null;
    }

    public List<Metadata> metadataList() {
        return this.metadataList;
    }

    public String toString() {
        return "MetaStorage{metaDataList=" + this.metadataList + '}';
    }

    private static boolean lambda$remove$0(int n, Metadata metadata) {
        return metadata.id() == n;
    }
}

