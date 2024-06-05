package net.minecraft.world;

import java.util.concurrent.*;

class World$3 implements Callable {
    @Override
    public String call() {
        return World.this.playerEntities.size() + " total; " + World.this.playerEntities.toString();
    }
}