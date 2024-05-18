package net.minecraft.world.chunk.storage;

import net.minecraft.world.chunk.*;
import net.minecraft.world.*;
import java.io.*;

public interface IChunkLoader
{
    void chunkTick();
    
    void saveExtraData();
    
    void saveChunk(final World p0, final Chunk p1) throws MinecraftException, IOException;
    
    void saveExtraChunkData(final World p0, final Chunk p1) throws IOException;
    
    Chunk loadChunk(final World p0, final int p1, final int p2) throws IOException;
}
