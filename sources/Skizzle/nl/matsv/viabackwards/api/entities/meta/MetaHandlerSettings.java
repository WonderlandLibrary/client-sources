/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package nl.matsv.viabackwards.api.entities.meta;

import nl.matsv.viabackwards.api.entities.meta.MetaHandler;
import nl.matsv.viabackwards.api.exceptions.RemovedValueException;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.entities.EntityType;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;

public class MetaHandlerSettings {
    private EntityType filterType;
    private boolean filterFamily;
    private int filterIndex = -1;
    private MetaHandler handler;

    public MetaHandlerSettings filter(EntityType type) {
        return this.filter(type, this.filterFamily, this.filterIndex);
    }

    public MetaHandlerSettings filter(EntityType type, boolean filterFamily) {
        return this.filter(type, filterFamily, this.filterIndex);
    }

    public MetaHandlerSettings filter(int index) {
        return this.filter(this.filterType, this.filterFamily, index);
    }

    public MetaHandlerSettings filter(EntityType type, int index) {
        return this.filter(type, this.filterFamily, index);
    }

    public MetaHandlerSettings filter(EntityType type, boolean filterFamily, int index) {
        this.filterType = type;
        this.filterFamily = filterFamily;
        this.filterIndex = index;
        return this;
    }

    public void handle(@Nullable MetaHandler handler) {
        this.handler = handler;
    }

    public void handleIndexChange(int newIndex) {
        this.handle(e -> {
            Metadata data = e.getData();
            data.setId(newIndex);
            return data;
        });
    }

    public void removed() {
        this.handle(e -> {
            throw RemovedValueException.EX;
        });
    }

    public boolean hasHandler() {
        return this.handler != null;
    }

    public boolean hasType() {
        return this.filterType != null;
    }

    public boolean hasIndex() {
        return this.filterIndex > -1;
    }

    public boolean isFilterFamily() {
        return this.filterFamily;
    }

    public boolean isGucci(EntityType type, Metadata metadata) {
        if (!this.hasHandler()) {
            return false;
        }
        if (this.hasType() && (this.filterFamily ? !type.isOrHasParent(this.filterType) : !this.filterType.is(type))) {
            return false;
        }
        return !this.hasIndex() || metadata.getId() == this.filterIndex;
    }

    public EntityType getFilterType() {
        return this.filterType;
    }

    public int getFilterIndex() {
        return this.filterIndex;
    }

    @Nullable
    public MetaHandler getHandler() {
        return this.handler;
    }

    public String toString() {
        return "MetaHandlerSettings{filterType=" + this.filterType + ", filterFamily=" + this.filterFamily + ", filterIndex=" + this.filterIndex + ", handler=" + this.handler + '}';
    }
}

