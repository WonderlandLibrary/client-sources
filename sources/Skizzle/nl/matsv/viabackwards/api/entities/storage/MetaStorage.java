/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package nl.matsv.viabackwards.api.entities.storage;

import java.util.List;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;

public class MetaStorage {
    private List<Metadata> metaDataList;

    public MetaStorage(List<Metadata> metaDataList) {
        this.metaDataList = metaDataList;
    }

    public boolean has(Metadata data) {
        return this.metaDataList.contains(data);
    }

    public void delete(Metadata data) {
        this.metaDataList.remove(data);
    }

    public void delete(int index) {
        this.metaDataList.removeIf(meta -> meta.getId() == index);
    }

    public void add(Metadata data) {
        this.metaDataList.add(data);
    }

    @Nullable
    public Metadata get(int index) {
        for (Metadata meta : this.metaDataList) {
            if (index != meta.getId()) continue;
            return meta;
        }
        return null;
    }

    public Metadata getOrDefault(int index, Metadata data) {
        return this.getOrDefault(index, false, data);
    }

    public Metadata getOrDefault(int index, boolean removeIfExists, Metadata data) {
        Metadata existingData = this.get(index);
        if (removeIfExists && existingData != null) {
            this.delete(existingData);
        }
        return existingData != null ? existingData : data;
    }

    public List<Metadata> getMetaDataList() {
        return this.metaDataList;
    }

    public void setMetaDataList(List<Metadata> metaDataList) {
        this.metaDataList = metaDataList;
    }

    public String toString() {
        return "MetaStorage{metaDataList=" + this.metaDataList + '}';
    }
}

