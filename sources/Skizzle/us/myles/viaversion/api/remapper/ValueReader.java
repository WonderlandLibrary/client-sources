/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.remapper;

import us.myles.ViaVersion.api.PacketWrapper;

@FunctionalInterface
public interface ValueReader<T> {
    public T read(PacketWrapper var1) throws Exception;
}

