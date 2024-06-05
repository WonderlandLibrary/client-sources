package net.minecraft.entity;

import java.util.concurrent.*;

class Entity$4 implements Callable {
    public String func_180116_a() {
        return Entity.this.ridingEntity.toString();
    }
    
    @Override
    public Object call() {
        return this.func_180116_a();
    }
}