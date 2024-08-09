/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public abstract class ShoulderRidingEntity
extends TameableEntity {
    private int rideCooldownCounter;

    protected ShoulderRidingEntity(EntityType<? extends ShoulderRidingEntity> entityType, World world) {
        super((EntityType<? extends TameableEntity>)entityType, world);
    }

    public boolean func_213439_d(ServerPlayerEntity serverPlayerEntity) {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("id", this.getEntityString());
        this.writeWithoutTypeId(compoundNBT);
        if (serverPlayerEntity.addShoulderEntity(compoundNBT)) {
            this.remove();
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        ++this.rideCooldownCounter;
        super.tick();
    }

    public boolean canSitOnShoulder() {
        return this.rideCooldownCounter > 100;
    }
}

