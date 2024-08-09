/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.storage.WorldSavedData;

public class StructureIndexesSavedData
extends WorldSavedData {
    private LongSet all = new LongOpenHashSet();
    private LongSet remaining = new LongOpenHashSet();

    public StructureIndexesSavedData(String string) {
        super(string);
    }

    @Override
    public void read(CompoundNBT compoundNBT) {
        this.all = new LongOpenHashSet(compoundNBT.getLongArray("All"));
        this.remaining = new LongOpenHashSet(compoundNBT.getLongArray("Remaining"));
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        compoundNBT.putLongArray("All", this.all.toLongArray());
        compoundNBT.putLongArray("Remaining", this.remaining.toLongArray());
        return compoundNBT;
    }

    public void func_201763_a(long l) {
        this.all.add(l);
        this.remaining.add(l);
    }

    public boolean func_208024_b(long l) {
        return this.all.contains(l);
    }

    public boolean func_208023_c(long l) {
        return this.remaining.contains(l);
    }

    public void func_201762_c(long l) {
        this.remaining.remove(l);
    }

    public LongSet getAll() {
        return this.all;
    }
}

