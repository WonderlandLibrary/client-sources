package net.minecraft.world.gen.structure;

import java.util.concurrent.*;

class MapGenStructure$3 implements Callable {
    @Override
    public String call() {
        return MapGenStructure.this.getClass().getCanonicalName();
    }
}