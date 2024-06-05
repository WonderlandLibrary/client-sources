package net.minecraft.world.gen.structure;

import java.util.concurrent.*;

class MapGenStructure$1 implements Callable {
    final /* synthetic */ int val$p_180701_2_;
    final /* synthetic */ int val$p_180701_3_;
    
    @Override
    public String call() {
        return MapGenStructure.this.canSpawnStructureAtCoords(this.val$p_180701_2_, this.val$p_180701_3_) ? "True" : "False";
    }
}