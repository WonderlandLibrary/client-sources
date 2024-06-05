package net.minecraft.world.gen.structure;

import java.util.concurrent.*;
import net.minecraft.world.*;

class MapGenStructure$2 implements Callable {
    final /* synthetic */ int val$p_180701_2_;
    final /* synthetic */ int val$p_180701_3_;
    
    @Override
    public String call() {
        return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(this.val$p_180701_2_, this.val$p_180701_3_));
    }
}