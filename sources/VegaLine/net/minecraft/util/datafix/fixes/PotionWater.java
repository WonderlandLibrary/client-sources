/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class PotionWater
implements IFixableData {
    @Override
    public int getFixVersion() {
        return 806;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        String s = compound.getString("id");
        if ("minecraft:potion".equals(s) || "minecraft:splash_potion".equals(s) || "minecraft:lingering_potion".equals(s) || "minecraft:tipped_arrow".equals(s)) {
            NBTTagCompound nbttagcompound = compound.getCompoundTag("tag");
            if (!nbttagcompound.hasKey("Potion", 8)) {
                nbttagcompound.setString("Potion", "minecraft:water");
            }
            if (!compound.hasKey("tag", 10)) {
                compound.setTag("tag", nbttagcompound);
            }
        }
        return compound;
    }
}

