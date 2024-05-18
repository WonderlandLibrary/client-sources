package net.minecraft.src;

import java.util.concurrent.*;

class CallableChunkPosHash implements Callable
{
    final int field_85165_a;
    final int field_85163_b;
    final MapGenStructure theMapStructureGenerator;
    
    CallableChunkPosHash(final MapGenStructure par1MapGenStructure, final int par2, final int par3) {
        this.theMapStructureGenerator = par1MapGenStructure;
        this.field_85165_a = par2;
        this.field_85163_b = par3;
    }
    
    public String callChunkPositionHash() {
        return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(this.field_85165_a, this.field_85163_b));
    }
    
    @Override
    public Object call() {
        return this.callChunkPositionHash();
    }
}
