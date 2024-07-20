/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Combat;

import net.minecraft.entity.Entity;

public class TotemCounterHelper {
    Entity entity;
    int count;
    long time;

    public TotemCounterHelper(Entity entity, int count) {
        this.count = count;
        this.entity = entity;
        this.time = System.currentTimeMillis();
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return this.time;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

