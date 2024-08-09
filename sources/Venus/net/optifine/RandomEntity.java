/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.optifine.IRandomEntity;

public class RandomEntity
implements IRandomEntity {
    private Entity entity;

    @Override
    public int getId() {
        UUID uUID = this.entity.getUniqueID();
        long l = uUID.getLeastSignificantBits();
        return (int)(l & Integer.MAX_VALUE);
    }

    @Override
    public BlockPos getSpawnPosition() {
        return this.entity.getDataManager().spawnPosition;
    }

    @Override
    public Biome getSpawnBiome() {
        return this.entity.getDataManager().spawnBiome;
    }

    @Override
    public String getName() {
        return this.entity.hasCustomName() ? this.entity.getCustomName().getString() : null;
    }

    @Override
    public int getHealth() {
        if (!(this.entity instanceof LivingEntity)) {
            return 1;
        }
        LivingEntity livingEntity = (LivingEntity)this.entity;
        return (int)livingEntity.getHealth();
    }

    @Override
    public int getMaxHealth() {
        if (!(this.entity instanceof LivingEntity)) {
            return 1;
        }
        LivingEntity livingEntity = (LivingEntity)this.entity;
        return (int)livingEntity.getMaxHealth();
    }

    public Entity getEntity() {
        return this.entity;
    }

    public void setEntity(Entity entity2) {
        this.entity = entity2;
    }
}

