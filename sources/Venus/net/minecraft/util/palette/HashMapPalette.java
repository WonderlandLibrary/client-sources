/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.palette;

import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.palette.IPalette;
import net.minecraft.util.palette.IResizeCallback;

public class HashMapPalette<T>
implements IPalette<T> {
    private final ObjectIntIdentityMap<T> registry;
    private final IntIdentityHashBiMap<T> statePaletteMap;
    private final IResizeCallback<T> paletteResizer;
    private final Function<CompoundNBT, T> deserializer;
    private final Function<T, CompoundNBT> serializer;
    private final int bits;

    public HashMapPalette(ObjectIntIdentityMap<T> objectIntIdentityMap, int n, IResizeCallback<T> iResizeCallback, Function<CompoundNBT, T> function, Function<T, CompoundNBT> function2) {
        this.registry = objectIntIdentityMap;
        this.bits = n;
        this.paletteResizer = iResizeCallback;
        this.deserializer = function;
        this.serializer = function2;
        this.statePaletteMap = new IntIdentityHashBiMap(1 << n);
    }

    @Override
    public int idFor(T t) {
        int n = this.statePaletteMap.getId(t);
        if (n == -1 && (n = this.statePaletteMap.add(t)) >= 1 << this.bits) {
            n = this.paletteResizer.onResize(this.bits + 1, t);
        }
        return n;
    }

    @Override
    public boolean func_230341_a_(Predicate<T> predicate) {
        for (int i = 0; i < this.getPaletteSize(); ++i) {
            if (!predicate.test(this.statePaletteMap.getByValue(i))) continue;
            return false;
        }
        return true;
    }

    @Override
    @Nullable
    public T get(int n) {
        return this.statePaletteMap.getByValue(n);
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        this.statePaletteMap.clear();
        int n = packetBuffer.readVarInt();
        for (int i = 0; i < n; ++i) {
            this.statePaletteMap.add(this.registry.getByValue(packetBuffer.readVarInt()));
        }
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        int n = this.getPaletteSize();
        packetBuffer.writeVarInt(n);
        for (int i = 0; i < n; ++i) {
            packetBuffer.writeVarInt(this.registry.getId(this.statePaletteMap.getByValue(i)));
        }
    }

    @Override
    public int getSerializedSize() {
        int n = PacketBuffer.getVarIntSize(this.getPaletteSize());
        for (int i = 0; i < this.getPaletteSize(); ++i) {
            n += PacketBuffer.getVarIntSize(this.registry.getId(this.statePaletteMap.getByValue(i)));
        }
        return n;
    }

    public int getPaletteSize() {
        return this.statePaletteMap.size();
    }

    @Override
    public void read(ListNBT listNBT) {
        this.statePaletteMap.clear();
        for (int i = 0; i < listNBT.size(); ++i) {
            this.statePaletteMap.add(this.deserializer.apply(listNBT.getCompound(i)));
        }
    }

    public void writePaletteToList(ListNBT listNBT) {
        for (int i = 0; i < this.getPaletteSize(); ++i) {
            listNBT.add((INBT)this.serializer.apply(this.statePaletteMap.getByValue(i)));
        }
    }
}

