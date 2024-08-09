package net.minecraft.world.chunk;

import net.minecraft.util.math.SectionPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;

import javax.annotation.Nullable;

public interface IChunkLightProvider
{
    @Nullable
    IBlockReader getChunkForLight(int chunkX, int chunkZ);

default void markLightChanged(LightType type, SectionPos pos)
    {
    }

    IBlockReader getWorld();
}
