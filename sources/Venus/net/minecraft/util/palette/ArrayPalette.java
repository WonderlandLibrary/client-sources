/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.palette;

import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.palette.IPalette;
import net.minecraft.util.palette.IResizeCallback;

public class ArrayPalette<T>
implements IPalette<T> {
    private final ObjectIntIdentityMap<T> registry;
    private final T[] states;
    private final IResizeCallback<T> resizeHandler;
    private final Function<CompoundNBT, T> deserializer;
    private final int bits;
    private int arraySize;

    public ArrayPalette(ObjectIntIdentityMap<T> objectIntIdentityMap, int n, IResizeCallback<T> iResizeCallback, Function<CompoundNBT, T> function) {
        this.registry = objectIntIdentityMap;
        this.states = new Object[1 << n];
        this.bits = n;
        this.resizeHandler = iResizeCallback;
        this.deserializer = function;
    }

    @Override
    public int idFor(T t) {
        int n;
        for (n = 0; n < this.arraySize; ++n) {
            if (this.states[n] != t) continue;
            return n;
        }
        if ((n = this.arraySize++) < this.states.length) {
            this.states[n] = t;
            return n;
        }
        return this.resizeHandler.onResize(this.bits + 1, t);
    }

    @Override
    public boolean func_230341_a_(Predicate<T> predicate) {
        for (int i = 0; i < this.arraySize; ++i) {
            if (!predicate.test(this.states[i])) continue;
            return false;
        }
        return true;
    }

    @Override
    @Nullable
    public T get(int n) {
        return n >= 0 && n < this.arraySize ? (T)this.states[n] : null;
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        this.arraySize = packetBuffer.readVarInt();
        for (int i = 0; i < this.arraySize; ++i) {
            this.states[i] = this.registry.getByValue(packetBuffer.readVarInt());
        }
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeVarInt(this.arraySize);
        for (int i = 0; i < this.arraySize; ++i) {
            packetBuffer.writeVarInt(this.registry.getId(this.states[i]));
        }
    }

    @Override
    public int getSerializedSize() {
        int n = PacketBuffer.getVarIntSize(this.getPaletteSize());
        for (int i = 0; i < this.getPaletteSize(); ++i) {
            n += PacketBuffer.getVarIntSize(this.registry.getId(this.states[i]));
        }
        return n;
    }

    public int getPaletteSize() {
        return this.arraySize;
    }

    @Override
    public void read(ListNBT listNBT) {
        for (int i = 0; i < listNBT.size(); ++i) {
            this.states[i] = this.deserializer.apply(listNBT.getCompound(i));
        }
        this.arraySize = listNBT.size();
    }
}

