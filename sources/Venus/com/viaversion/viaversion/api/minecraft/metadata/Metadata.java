/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.metadata;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class Metadata {
    private int id;
    private MetaType metaType;
    private Object value;

    public Metadata(int n, MetaType metaType, @Nullable Object object) {
        this.id = n;
        this.metaType = metaType;
        this.value = this.checkValue(metaType, object);
    }

    public int id() {
        return this.id;
    }

    public void setId(int n) {
        this.id = n;
    }

    public MetaType metaType() {
        return this.metaType;
    }

    public void setMetaType(MetaType metaType) {
        this.checkValue(metaType, this.value);
        this.metaType = metaType;
    }

    public <T> @Nullable T value() {
        return (T)this.value;
    }

    public @Nullable Object getValue() {
        return this.value;
    }

    public void setValue(@Nullable Object object) {
        this.value = this.checkValue(this.metaType, object);
    }

    public void setTypeAndValue(MetaType metaType, @Nullable Object object) {
        this.value = this.checkValue(metaType, object);
        this.metaType = metaType;
    }

    private Object checkValue(MetaType metaType, @Nullable Object object) {
        Preconditions.checkNotNull(metaType);
        if (object != null && !metaType.type().getOutputClass().isAssignableFrom(object.getClass())) {
            throw new IllegalArgumentException("Metadata value and metaType are incompatible. Type=" + metaType + ", value=" + object + " (" + object.getClass().getSimpleName() + ")");
        }
        return object;
    }

    @Deprecated
    public void setMetaTypeUnsafe(MetaType metaType) {
        this.metaType = metaType;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        Metadata metadata = (Metadata)object;
        if (this.id != metadata.id) {
            return true;
        }
        if (this.metaType != metadata.metaType) {
            return true;
        }
        return Objects.equals(this.value, metadata.value);
    }

    public int hashCode() {
        int n = this.id;
        n = 31 * n + this.metaType.hashCode();
        n = 31 * n + (this.value != null ? this.value.hashCode() : 0);
        return n;
    }

    public String toString() {
        return "Metadata{id=" + this.id + ", metaType=" + this.metaType + ", value=" + this.value + '}';
    }
}

