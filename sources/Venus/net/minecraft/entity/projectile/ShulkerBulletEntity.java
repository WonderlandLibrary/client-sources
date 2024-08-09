/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ShulkerBulletEntity
extends ProjectileEntity {
    private Entity target;
    @Nullable
    private Direction direction;
    private int steps;
    private double targetDeltaX;
    private double targetDeltaY;
    private double targetDeltaZ;
    @Nullable
    private UUID targetUniqueId;

    public ShulkerBulletEntity(EntityType<? extends ShulkerBulletEntity> entityType, World world) {
        super((EntityType<? extends ProjectileEntity>)entityType, world);
        this.noClip = true;
    }

    public ShulkerBulletEntity(World world, double d, double d2, double d3, double d4, double d5, double d6) {
        this((EntityType<? extends ShulkerBulletEntity>)EntityType.SHULKER_BULLET, world);
        this.setLocationAndAngles(d, d2, d3, this.rotationYaw, this.rotationPitch);
        this.setMotion(d4, d5, d6);
    }

    public ShulkerBulletEntity(World world, LivingEntity livingEntity, Entity entity2, Direction.Axis axis) {
        this((EntityType<? extends ShulkerBulletEntity>)EntityType.SHULKER_BULLET, world);
        this.setShooter(livingEntity);
        BlockPos blockPos = livingEntity.getPosition();
        double d = (double)blockPos.getX() + 0.5;
        double d2 = (double)blockPos.getY() + 0.5;
        double d3 = (double)blockPos.getZ() + 0.5;
        this.setLocationAndAngles(d, d2, d3, this.rotationYaw, this.rotationPitch);
        this.target = entity2;
        this.direction = Direction.UP;
        this.selectNextMoveDirection(axis);
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        if (this.target != null) {
            compoundNBT.putUniqueId("Target", this.target.getUniqueID());
        }
        if (this.direction != null) {
            compoundNBT.putInt("Dir", this.direction.getIndex());
        }
        compoundNBT.putInt("Steps", this.steps);
        compoundNBT.putDouble("TXD", this.targetDeltaX);
        compoundNBT.putDouble("TYD", this.targetDeltaY);
        compoundNBT.putDouble("TZD", this.targetDeltaZ);
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.steps = compoundNBT.getInt("Steps");
        this.targetDeltaX = compoundNBT.getDouble("TXD");
        this.targetDeltaY = compoundNBT.getDouble("TYD");
        this.targetDeltaZ = compoundNBT.getDouble("TZD");
        if (compoundNBT.contains("Dir", 0)) {
            this.direction = Direction.byIndex(compoundNBT.getInt("Dir"));
        }
        if (compoundNBT.hasUniqueId("Target")) {
            this.targetUniqueId = compoundNBT.getUniqueId("Target");
        }
    }

    @Override
    protected void registerData() {
    }

    private void setDirection(@Nullable Direction direction) {
        this.direction = direction;
    }

    private void selectNextMoveDirection(@Nullable Direction.Axis axis) {
        BlockPos blockPos;
        double d = 0.5;
        if (this.target == null) {
            blockPos = this.getPosition().down();
        } else {
            d = (double)this.target.getHeight() * 0.5;
            blockPos = new BlockPos(this.target.getPosX(), this.target.getPosY() + d, this.target.getPosZ());
        }
        double d2 = (double)blockPos.getX() + 0.5;
        double d3 = (double)blockPos.getY() + d;
        double d4 = (double)blockPos.getZ() + 0.5;
        Direction direction = null;
        if (!blockPos.withinDistance(this.getPositionVec(), 2.0)) {
            BlockPos blockPos2 = this.getPosition();
            ArrayList<Direction> arrayList = Lists.newArrayList();
            if (axis != Direction.Axis.X) {
                if (blockPos2.getX() < blockPos.getX() && this.world.isAirBlock(blockPos2.east())) {
                    arrayList.add(Direction.EAST);
                } else if (blockPos2.getX() > blockPos.getX() && this.world.isAirBlock(blockPos2.west())) {
                    arrayList.add(Direction.WEST);
                }
            }
            if (axis != Direction.Axis.Y) {
                if (blockPos2.getY() < blockPos.getY() && this.world.isAirBlock(blockPos2.up())) {
                    arrayList.add(Direction.UP);
                } else if (blockPos2.getY() > blockPos.getY() && this.world.isAirBlock(blockPos2.down())) {
                    arrayList.add(Direction.DOWN);
                }
            }
            if (axis != Direction.Axis.Z) {
                if (blockPos2.getZ() < blockPos.getZ() && this.world.isAirBlock(blockPos2.south())) {
                    arrayList.add(Direction.SOUTH);
                } else if (blockPos2.getZ() > blockPos.getZ() && this.world.isAirBlock(blockPos2.north())) {
                    arrayList.add(Direction.NORTH);
                }
            }
            direction = Direction.getRandomDirection(this.rand);
            if (arrayList.isEmpty()) {
                for (int i = 5; !this.world.isAirBlock(blockPos2.offset(direction)) && i > 0; --i) {
                    direction = Direction.getRandomDirection(this.rand);
                }
            } else {
                direction = (Direction)arrayList.get(this.rand.nextInt(arrayList.size()));
            }
            d2 = this.getPosX() + (double)direction.getXOffset();
            d3 = this.getPosY() + (double)direction.getYOffset();
            d4 = this.getPosZ() + (double)direction.getZOffset();
        }
        this.setDirection(direction);
        double d5 = d2 - this.getPosX();
        double d6 = d3 - this.getPosY();
        double d7 = d4 - this.getPosZ();
        double d8 = MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
        if (d8 == 0.0) {
            this.targetDeltaX = 0.0;
            this.targetDeltaY = 0.0;
            this.targetDeltaZ = 0.0;
        } else {
            this.targetDeltaX = d5 / d8 * 0.15;
            this.targetDeltaY = d6 / d8 * 0.15;
            this.targetDeltaZ = d7 / d8 * 0.15;
        }
        this.isAirBorne = true;
        this.steps = 10 + this.rand.nextInt(5) * 10;
    }

    @Override
    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
            this.remove();
        }
    }

    @Override
    public void tick() {
        Object object;
        super.tick();
        if (!this.world.isRemote) {
            if (this.target == null && this.targetUniqueId != null) {
                this.target = ((ServerWorld)this.world).getEntityByUuid(this.targetUniqueId);
                if (this.target == null) {
                    this.targetUniqueId = null;
                }
            }
            if (this.target == null || !this.target.isAlive() || this.target instanceof PlayerEntity && ((PlayerEntity)this.target).isSpectator()) {
                if (!this.hasNoGravity()) {
                    this.setMotion(this.getMotion().add(0.0, -0.04, 0.0));
                }
            } else {
                this.targetDeltaX = MathHelper.clamp(this.targetDeltaX * 1.025, -1.0, 1.0);
                this.targetDeltaY = MathHelper.clamp(this.targetDeltaY * 1.025, -1.0, 1.0);
                this.targetDeltaZ = MathHelper.clamp(this.targetDeltaZ * 1.025, -1.0, 1.0);
                object = this.getMotion();
                this.setMotion(((Vector3d)object).add((this.targetDeltaX - ((Vector3d)object).x) * 0.2, (this.targetDeltaY - ((Vector3d)object).y) * 0.2, (this.targetDeltaZ - ((Vector3d)object).z) * 0.2));
            }
            if (((RayTraceResult)(object = ProjectileHelper.func_234618_a_(this, this::func_230298_a_))).getType() != RayTraceResult.Type.MISS) {
                this.onImpact((RayTraceResult)object);
            }
        }
        this.doBlockCollisions();
        object = this.getMotion();
        this.setPosition(this.getPosX() + ((Vector3d)object).x, this.getPosY() + ((Vector3d)object).y, this.getPosZ() + ((Vector3d)object).z);
        ProjectileHelper.rotateTowardsMovement(this, 0.5f);
        if (this.world.isRemote) {
            this.world.addParticle(ParticleTypes.END_ROD, this.getPosX() - ((Vector3d)object).x, this.getPosY() - ((Vector3d)object).y + 0.15, this.getPosZ() - ((Vector3d)object).z, 0.0, 0.0, 0.0);
        } else if (this.target != null && !this.target.removed) {
            if (this.steps > 0) {
                --this.steps;
                if (this.steps == 0) {
                    this.selectNextMoveDirection(this.direction == null ? null : this.direction.getAxis());
                }
            }
            if (this.direction != null) {
                BlockPos blockPos = this.getPosition();
                Direction.Axis axis = this.direction.getAxis();
                if (this.world.isTopSolid(blockPos.offset(this.direction), this)) {
                    this.selectNextMoveDirection(axis);
                } else {
                    BlockPos blockPos2 = this.target.getPosition();
                    if (axis == Direction.Axis.X && blockPos.getX() == blockPos2.getX() || axis == Direction.Axis.Z && blockPos.getZ() == blockPos2.getZ() || axis == Direction.Axis.Y && blockPos.getY() == blockPos2.getY()) {
                        this.selectNextMoveDirection(axis);
                    }
                }
            }
        }
    }

    @Override
    protected boolean func_230298_a_(Entity entity2) {
        return super.func_230298_a_(entity2) && !entity2.noClip;
    }

    @Override
    public boolean isBurning() {
        return true;
    }

    @Override
    public boolean isInRangeToRenderDist(double d) {
        return d < 16384.0;
    }

    @Override
    public float getBrightness() {
        return 1.0f;
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        super.onEntityHit(entityRayTraceResult);
        Entity entity2 = entityRayTraceResult.getEntity();
        Entity entity3 = this.func_234616_v_();
        LivingEntity livingEntity = entity3 instanceof LivingEntity ? (LivingEntity)entity3 : null;
        boolean bl = entity2.attackEntityFrom(DamageSource.causeIndirectDamage(this, livingEntity).setProjectile(), 4.0f);
        if (bl) {
            this.applyEnchantments(livingEntity, entity2);
            if (entity2 instanceof LivingEntity) {
                ((LivingEntity)entity2).addPotionEffect(new EffectInstance(Effects.LEVITATION, 200));
            }
        }
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult blockRayTraceResult) {
        super.func_230299_a_(blockRayTraceResult);
        ((ServerWorld)this.world).spawnParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY(), this.getPosZ(), 2, 0.2, 0.2, 0.2, 0.0);
        this.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0f, 1.0f);
    }

    @Override
    protected void onImpact(RayTraceResult rayTraceResult) {
        super.onImpact(rayTraceResult);
        this.remove();
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (!this.world.isRemote) {
            this.playSound(SoundEvents.ENTITY_SHULKER_BULLET_HURT, 1.0f, 1.0f);
            ((ServerWorld)this.world).spawnParticle(ParticleTypes.CRIT, this.getPosX(), this.getPosY(), this.getPosZ(), 15, 0.2, 0.2, 0.2, 0.0);
            this.remove();
        }
        return false;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }
}

