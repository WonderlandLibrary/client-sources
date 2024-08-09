/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.datasync;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;

public interface IDataSerializer<T> {
    public void write(PacketBuffer var1, T var2);

    public T read(PacketBuffer var1);

    default public DataParameter<T> createKey(int n) {
        return new DataParameter(n, this);
    }

    public T copyValue(T var1);
}

