package net.minecraft.world;

import java.util.concurrent.*;

class World$4 implements Callable {
    @Override
    public String call() {
        return World.this.chunkProvider.makeString();
    }
}