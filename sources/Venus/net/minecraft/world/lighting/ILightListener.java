/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.lighting;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;

public interface ILightListener {
    default public void func_215567_a(BlockPos blockPos, boolean bl) {
        this.updateSectionStatus(SectionPos.from(blockPos), bl);
    }

    public void updateSectionStatus(SectionPos var1, boolean var2);
}

