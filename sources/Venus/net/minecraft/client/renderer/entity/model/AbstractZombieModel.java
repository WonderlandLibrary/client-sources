/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;

public abstract class AbstractZombieModel<T extends MonsterEntity>
extends BipedModel<T> {
    protected AbstractZombieModel(float f, float f2, int n, int n2) {
        super(f, f2, n, n2);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setRotationAngles(t, f, f2, f3, f4, f5);
        ModelHelper.func_239105_a_(this.bipedLeftArm, this.bipedRightArm, this.isAggressive(t), this.swingProgress, f3);
    }

    public abstract boolean isAggressive(T var1);

    @Override
    public void setRotationAngles(LivingEntity livingEntity, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((MonsterEntity)livingEntity), f, f2, f3, f4, f5);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((MonsterEntity)entity2), f, f2, f3, f4, f5);
    }
}

