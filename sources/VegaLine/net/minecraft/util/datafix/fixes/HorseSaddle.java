/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class HorseSaddle
implements IFixableData {
    @Override
    public int getFixVersion() {
        return 110;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        if ("EntityHorse".equals(compound.getString("id")) && !compound.hasKey("SaddleItem", 10) && compound.getBoolean("Saddle")) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setString("id", "minecraft:saddle");
            nbttagcompound.setByte("Count", (byte)1);
            nbttagcompound.setShort("Damage", (short)0);
            compound.setTag("SaddleItem", nbttagcompound);
            compound.removeTag("Saddle");
        }
        return compound;
    }
}

