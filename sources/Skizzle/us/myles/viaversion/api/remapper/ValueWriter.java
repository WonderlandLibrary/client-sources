/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.remapper;

import us.myles.ViaVersion.api.PacketWrapper;

@FunctionalInterface
public interface ValueWriter<T> {
    public void write(PacketWrapper var1, T var2) throws Exception;
}

