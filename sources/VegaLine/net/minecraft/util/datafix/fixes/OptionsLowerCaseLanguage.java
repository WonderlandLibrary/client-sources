/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import java.util.Locale;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class OptionsLowerCaseLanguage
implements IFixableData {
    @Override
    public int getFixVersion() {
        return 816;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        if (compound.hasKey("lang", 8)) {
            compound.setString("lang", compound.getString("lang").toLowerCase(Locale.ROOT));
        }
        return compound;
    }
}

