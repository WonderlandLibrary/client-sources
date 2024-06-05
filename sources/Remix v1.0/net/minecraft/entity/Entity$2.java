package net.minecraft.entity;

import java.util.concurrent.*;

class Entity$2 implements Callable {
    @Override
    public String call() {
        return Entity.this.getName();
    }
}