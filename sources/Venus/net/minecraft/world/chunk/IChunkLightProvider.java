/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk;

import javax.annotation.Nullable;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;

public interface IChunkLightProvider {
    @Nullable
    public IBlockReader getChunkForLight(int var1, int var2);

    default public void markLightChanged(LightType lightType, SectionPos sectionPos) {
    }

    public IBlockReader getWorld();
}

