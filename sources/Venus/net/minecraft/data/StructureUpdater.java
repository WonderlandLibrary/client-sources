/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import net.minecraft.data.SNBTToNBTConverter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.world.gen.feature.template.Template;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureUpdater
implements SNBTToNBTConverter.ITransformer {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public CompoundNBT func_225371_a(String string, CompoundNBT compoundNBT) {
        return string.startsWith("data/minecraft/structures/") ? StructureUpdater.updateSNBT(string, StructureUpdater.addDataVersion(compoundNBT)) : compoundNBT;
    }

    private static CompoundNBT addDataVersion(CompoundNBT compoundNBT) {
        if (!compoundNBT.contains("DataVersion", 0)) {
            compoundNBT.putInt("DataVersion", 500);
        }
        return compoundNBT;
    }

    private static CompoundNBT updateSNBT(String string, CompoundNBT compoundNBT) {
        Template template = new Template();
        int n = compoundNBT.getInt("DataVersion");
        int n2 = 2532;
        if (n < 2532) {
            LOGGER.warn("SNBT Too old, do not forget to update: " + n + " < 2532: " + string);
        }
        CompoundNBT compoundNBT2 = NBTUtil.update(DataFixesManager.getDataFixer(), DefaultTypeReferences.STRUCTURE, compoundNBT, n);
        template.read(compoundNBT2);
        return template.writeToNBT(new CompoundNBT());
    }
}

