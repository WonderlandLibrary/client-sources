/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.AbstractZombieModel;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;

public class ZombieModel<T extends ZombieEntity>
extends AbstractZombieModel<T> {
    public ZombieModel(float f, boolean bl) {
        this(f, 0.0f, 64, bl ? 32 : 64);
    }

    protected ZombieModel(float f, float f2, int n, int n2) {
        super(f, f2, n, n2);
    }

    @Override
    public boolean isAggressive(T t) {
        return ((MobEntity)t).isAggressive();
    }

    @Override
    public boolean isAggressive(MonsterEntity monsterEntity) {
        return this.isAggressive((T)((ZombieEntity)monsterEntity));
    }
}

