/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

public interface IServerWorld
extends IWorld {
    public ServerWorld getWorld();

    default public void func_242417_l(Entity entity2) {
        entity2.getSelfAndPassengers().forEach(this::addEntity);
    }
}

