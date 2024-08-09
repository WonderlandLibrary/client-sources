/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;

public class WeightedSpawnerEntity
extends WeightedRandom.Item {
    private final CompoundNBT nbt;

    public WeightedSpawnerEntity() {
        super(1);
        this.nbt = new CompoundNBT();
        this.nbt.putString("id", "minecraft:pig");
    }

    public WeightedSpawnerEntity(CompoundNBT compoundNBT) {
        this(compoundNBT.contains("Weight", 0) ? compoundNBT.getInt("Weight") : 1, compoundNBT.getCompound("Entity"));
    }

    public WeightedSpawnerEntity(int n, CompoundNBT compoundNBT) {
        super(n);
        this.nbt = compoundNBT;
        ResourceLocation resourceLocation = ResourceLocation.tryCreate(compoundNBT.getString("id"));
        if (resourceLocation != null) {
            compoundNBT.putString("id", resourceLocation.toString());
        } else {
            compoundNBT.putString("id", "minecraft:pig");
        }
    }

    public CompoundNBT toCompoundTag() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("Entity", this.nbt);
        compoundNBT.putInt("Weight", this.itemWeight);
        return compoundNBT;
    }

    public CompoundNBT getNbt() {
        return this.nbt;
    }
}

