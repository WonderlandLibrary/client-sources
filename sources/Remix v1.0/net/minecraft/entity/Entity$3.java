package net.minecraft.entity;

import java.util.concurrent.*;

class Entity$3 implements Callable {
    public String func_180118_a() {
        return Entity.this.riddenByEntity.toString();
    }
    
    @Override
    public Object call() {
        return this.func_180118_a();
    }
}