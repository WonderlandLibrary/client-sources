/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.datasync;

import net.minecraft.network.datasync.IDataSerializer;

public class DataParameter<T> {
    private final int id;
    private final IDataSerializer<T> serializer;

    public DataParameter(int n, IDataSerializer<T> iDataSerializer) {
        this.id = n;
        this.serializer = iDataSerializer;
    }

    public int getId() {
        return this.id;
    }

    public IDataSerializer<T> getSerializer() {
        return this.serializer;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            DataParameter dataParameter = (DataParameter)object;
            return this.id == dataParameter.id;
        }
        return true;
    }

    public int hashCode() {
        return this.id;
    }

    public String toString() {
        return "<entity data: " + this.id + ">";
    }
}

