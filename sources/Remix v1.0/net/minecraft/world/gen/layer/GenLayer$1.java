package net.minecraft.world.gen.layer;

import java.util.concurrent.*;
import net.minecraft.world.biome.*;

static final class GenLayer$1 implements Callable {
    final /* synthetic */ BiomeGenBase val$var2;
    
    @Override
    public String call() {
        return String.valueOf(this.val$var2);
    }
}