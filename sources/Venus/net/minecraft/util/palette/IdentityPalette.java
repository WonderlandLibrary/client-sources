/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.palette;

import java.util.function.Predicate;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.palette.IPalette;

public class IdentityPalette<T>
implements IPalette<T> {
    private final ObjectIntIdentityMap<T> registry;
    private final T defaultState;

    public IdentityPalette(ObjectIntIdentityMap<T> objectIntIdentityMap, T t) {
        this.registry = objectIntIdentityMap;
        this.defaultState = t;
    }

    @Override
    public int idFor(T t) {
        int n = this.registry.getId(t);
        return n == -1 ? 0 : n;
    }

    @Override
    public boolean func_230341_a_(Predicate<T> predicate) {
        return false;
    }

    @Override
    public T get(int n) {
        T t = this.registry.getByValue(n);
        return t == null ? this.defaultState : t;
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
    }

    @Override
    public int getSerializedSize() {
        return PacketBuffer.getVarIntSize(0);
    }

    @Override
    public void read(ListNBT listNBT) {
    }
}

