/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class BedItemColor
implements IFixableData {
    @Override
    public int getFixVersion() {
        return 1125;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        if ("minecraft:bed".equals(compound.getString("id")) && compound.getShort("Damage") == 0) {
            compound.setShort("Damage", (short)EnumDyeColor.RED.getMetadata());
        }
        return compound;
    }
}

