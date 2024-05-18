package net.minecraft.src;

import java.io.*;

public interface IChunkLoader
{
    Chunk loadChunk(final World p0, final int p1, final int p2) throws IOException;
    
    void saveChunk(final World p0, final Chunk p1) throws MinecraftException, IOException;
    
    void saveExtraChunkData(final World p0, final Chunk p1);
    
    void chunkTick();
    
    void saveExtraData();
}
