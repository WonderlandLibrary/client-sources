/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.lighting;

import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.lighting.ILightListener;

public interface IWorldLightListener
extends ILightListener {
    @Nullable
    public NibbleArray getData(SectionPos var1);

    public int getLightFor(BlockPos var1);

    public static enum Dummy implements IWorldLightListener
    {
        INSTANCE;


        @Override
        @Nullable
        public NibbleArray getData(SectionPos sectionPos) {
            return null;
        }

        @Override
        public int getLightFor(BlockPos blockPos) {
            return 1;
        }

        @Override
        public void updateSectionStatus(SectionPos sectionPos, boolean bl) {
        }
    }
}

