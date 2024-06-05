package net.minecraft.entity;

import java.util.concurrent.*;

class Entity$1 implements Callable {
    @Override
    public String call() {
        return EntityList.getEntityString(Entity.this) + " (" + Entity.this.getClass().getCanonicalName() + ")";
    }
}