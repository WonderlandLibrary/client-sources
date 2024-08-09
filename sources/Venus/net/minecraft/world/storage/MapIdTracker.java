/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.storage.WorldSavedData;

public class MapIdTracker
extends WorldSavedData {
    private final Object2IntMap<String> usedIds = new Object2IntOpenHashMap<String>();

    public MapIdTracker() {
        super("idcounts");
        this.usedIds.defaultReturnValue(-1);
    }

    @Override
    public void read(CompoundNBT compoundNBT) {
        this.usedIds.clear();
        for (String string : compoundNBT.keySet()) {
            if (!compoundNBT.contains(string, 0)) continue;
            this.usedIds.put(string, compoundNBT.getInt(string));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        for (Object2IntMap.Entry entry : this.usedIds.object2IntEntrySet()) {
            compoundNBT.putInt((String)entry.getKey(), entry.getIntValue());
        }
        return compoundNBT;
    }

    public int getNextId() {
        int n = this.usedIds.getInt("map") + 1;
        this.usedIds.put("map", n);
        this.markDirty();
        return n;
    }
}

