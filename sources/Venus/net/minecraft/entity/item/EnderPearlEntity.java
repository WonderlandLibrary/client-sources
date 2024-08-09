/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item;

import javax.annotation.Nullable;
import mpp.venusfr.events.EventDamageReceive;
import mpp.venusfr.venusfr;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EnderPearlEntity
extends ProjectileItemEntity {
    public EnderPearlEntity(EntityType<? extends EnderPearlEntity> entityType, World world) {
        super((EntityType<? extends ProjectileItemEntity>)entityType, world);
    }

    public EnderPearlEntity(World world, LivingEntity livingEntity) {
        super((EntityType<? extends ProjectileItemEntity>)EntityType.ENDER_PEARL, livingEntity, world);
    }

    public EnderPearlEntity(World world, double d, double d2, double d3) {
        super((EntityType<? extends ProjectileItemEntity>)EntityType.ENDER_PEARL, d, d2, d3, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.ENDER_PEARL;
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        super.onEntityHit(entityRayTraceResult);
        entityRayTraceResult.getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), 0.0f);
    }

    @Override
    protected void onImpact(RayTraceResult rayTraceResult) {
        super.onImpact(rayTraceResult);
        Entity entity2 = this.func_234616_v_();
        venusfr.getInstance().getEventBus().post(new EventDamageReceive(EventDamageReceive.DamageType.ENDER_PEARL));
        for (int i = 0; i < 32; ++i) {
            this.world.addParticle(ParticleTypes.PORTAL, this.getPosX(), this.getPosY() + this.rand.nextDouble() * 2.0, this.getPosZ(), this.rand.nextGaussian(), 0.0, this.rand.nextGaussian());
        }
        if (!this.world.isRemote && !this.removed) {
            if (entity2 instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity2;
                if (serverPlayerEntity.connection.getNetworkManager().isChannelOpen() && serverPlayerEntity.world == this.world && !serverPlayerEntity.isSleeping()) {
                    if (this.rand.nextFloat() < 0.05f && this.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
                        EndermiteEntity endermiteEntity = EntityType.ENDERMITE.create(this.world);
                        endermiteEntity.setSpawnedByPlayer(false);
                        endermiteEntity.setLocationAndAngles(entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), entity2.rotationYaw, entity2.rotationPitch);
                        this.world.addEntity(endermiteEntity);
                    }
                    if (entity2.isPassenger()) {
                        entity2.stopRiding();
                    }
                    entity2.setPositionAndUpdate(this.getPosX(), this.getPosY(), this.getPosZ());
                    entity2.fallDistance = 0.0f;
                    entity2.attackEntityFrom(DamageSource.FALL, 5.0f);
                }
            } else if (entity2 != null) {
                entity2.setPositionAndUpdate(this.getPosX(), this.getPosY(), this.getPosZ());
                entity2.fallDistance = 0.0f;
            }
            this.remove();
        }
    }

    @Override
    public void tick() {
        Entity entity2 = this.func_234616_v_();
        if (entity2 instanceof PlayerEntity && !entity2.isAlive()) {
            this.remove();
        } else {
            super.tick();
        }
    }

    @Override
    @Nullable
    public Entity changeDimension(ServerWorld serverWorld) {
        Entity entity2 = this.func_234616_v_();
        if (entity2 != null && entity2.world.getDimensionKey() != serverWorld.getDimensionKey()) {
            this.setShooter(null);
        }
        return super.changeDimension(serverWorld);
    }
}

