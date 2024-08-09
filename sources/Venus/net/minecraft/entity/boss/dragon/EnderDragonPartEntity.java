/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss.dragon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;

public class EnderDragonPartEntity
extends Entity {
    public final EnderDragonEntity dragon;
    public final String field_213853_c;
    private final EntitySize field_213854_d;

    public EnderDragonPartEntity(EnderDragonEntity enderDragonEntity, String string, float f, float f2) {
        super(enderDragonEntity.getType(), enderDragonEntity.world);
        this.field_213854_d = EntitySize.flexible(f, f2);
        this.recalculateSize();
        this.dragon = enderDragonEntity;
        this.field_213853_c = string;
    }

    @Override
    protected void registerData() {
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        return this.isInvulnerableTo(damageSource) ? false : this.dragon.attackEntityPartFrom(this, damageSource, f);
    }

    @Override
    public boolean isEntityEqual(Entity entity2) {
        return this == entity2 || this.dragon == entity2;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntitySize getSize(Pose pose) {
        return this.field_213854_d;
    }
}

