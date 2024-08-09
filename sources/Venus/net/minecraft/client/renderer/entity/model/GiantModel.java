/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.AbstractZombieModel;
import net.minecraft.entity.monster.GiantEntity;
import net.minecraft.entity.monster.MonsterEntity;

public class GiantModel
extends AbstractZombieModel<GiantEntity> {
    public GiantModel() {
        this(0.0f, false);
    }

    public GiantModel(float f, boolean bl) {
        super(f, 0.0f, 64, bl ? 32 : 64);
    }

    @Override
    public boolean isAggressive(GiantEntity giantEntity) {
        return true;
    }

    @Override
    public boolean isAggressive(MonsterEntity monsterEntity) {
        return this.isAggressive((GiantEntity)monsterEntity);
    }
}

