/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.minecraft.metadata;

import java.util.Objects;
import us.myles.ViaVersion.api.minecraft.metadata.MetaType;

public class Metadata {
    private int id;
    private MetaType metaType;
    private Object value;

    public Metadata(int id, MetaType metaType, Object value) {
        this.id = id;
        this.metaType = metaType;
        this.value = value;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MetaType getMetaType() {
        return this.metaType;
    }

    public void setMetaType(MetaType metaType) {
        this.metaType = metaType;
    }

    public Object getValue() {
        return this.value;
    }

    public <T> T getCastedValue() {
        return (T)this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Metadata metadata = (Metadata)o;
        if (this.id != metadata.id) {
            return false;
        }
        if (!Objects.equals(this.metaType, metadata.metaType)) {
            return false;
        }
        return Objects.equals(this.value, metadata.value);
    }

    public int hashCode() {
        int result = this.id;
        result = 31 * result + (this.metaType != null ? this.metaType.hashCode() : 0);
        result = 31 * result + (this.value != null ? this.value.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "Metadata{id=" + this.id + ", metaType=" + this.metaType + ", value=" + this.value + '}';
    }
}

