/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AmbientEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BatEntity
extends AmbientEntity {
    private static final DataParameter<Byte> HANGING = EntityDataManager.createKey(BatEntity.class, DataSerializers.BYTE);
    private static final EntityPredicate field_213813_c = new EntityPredicate().setDistance(4.0).allowFriendlyFire();
    private BlockPos spawnPosition;

    public BatEntity(EntityType<? extends BatEntity> entityType, World world) {
        super((EntityType<? extends AmbientEntity>)entityType, world);
        this.setIsBatHanging(false);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HANGING, (byte)0);
    }

    @Override
    protected float getSoundVolume() {
        return 0.1f;
    }

    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.95f;
    }

    @Override
    @Nullable
    public SoundEvent getAmbientSound() {
        return this.getIsBatHanging() && this.rand.nextInt(4) != 0 ? null : SoundEvents.ENTITY_BAT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    protected void collideWithEntity(Entity entity2) {
    }

    @Override
    protected void collideWithNearbyEntities() {
    }

    public static AttributeModifierMap.MutableAttribute func_234175_m_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 6.0);
    }

    public boolean getIsBatHanging() {
        return (this.dataManager.get(HANGING) & 1) != 0;
    }

    public void setIsBatHanging(boolean bl) {
        byte by = this.dataManager.get(HANGING);
        if (bl) {
            this.dataManager.set(HANGING, (byte)(by | 1));
        } else {
            this.dataManager.set(HANGING, (byte)(by & 0xFFFFFFFE));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getIsBatHanging()) {
            this.setMotion(Vector3d.ZERO);
            this.setRawPosition(this.getPosX(), (double)MathHelper.floor(this.getPosY()) + 1.0 - (double)this.getHeight(), this.getPosZ());
        } else {
            this.setMotion(this.getMotion().mul(1.0, 0.6, 1.0));
        }
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        BlockPos blockPos = this.getPosition();
        BlockPos blockPos2 = blockPos.up();
        if (this.getIsBatHanging()) {
            boolean bl = this.isSilent();
            if (this.world.getBlockState(blockPos2).isNormalCube(this.world, blockPos)) {
                if (this.rand.nextInt(200) == 0) {
                    this.rotationYawHead = this.rand.nextInt(360);
                }
                if (this.world.getClosestPlayer(field_213813_c, this) != null) {
                    this.setIsBatHanging(true);
                    if (!bl) {
                        this.world.playEvent(null, 1025, blockPos, 0);
                    }
                }
            } else {
                this.setIsBatHanging(true);
                if (!bl) {
                    this.world.playEvent(null, 1025, blockPos, 0);
                }
            }
        } else {
            if (!(this.spawnPosition == null || this.world.isAirBlock(this.spawnPosition) && this.spawnPosition.getY() >= 1)) {
                this.spawnPosition = null;
            }
            if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.withinDistance(this.getPositionVec(), 2.0)) {
                this.spawnPosition = new BlockPos(this.getPosX() + (double)this.rand.nextInt(7) - (double)this.rand.nextInt(7), this.getPosY() + (double)this.rand.nextInt(6) - 2.0, this.getPosZ() + (double)this.rand.nextInt(7) - (double)this.rand.nextInt(7));
            }
            double d = (double)this.spawnPosition.getX() + 0.5 - this.getPosX();
            double d2 = (double)this.spawnPosition.getY() + 0.1 - this.getPosY();
            double d3 = (double)this.spawnPosition.getZ() + 0.5 - this.getPosZ();
            Vector3d vector3d = this.getMotion();
            Vector3d vector3d2 = vector3d.add((Math.signum(d) * 0.5 - vector3d.x) * (double)0.1f, (Math.signum(d2) * (double)0.7f - vector3d.y) * (double)0.1f, (Math.signum(d3) * 0.5 - vector3d.z) * (double)0.1f);
            this.setMotion(vector3d2);
            float f = (float)(MathHelper.atan2(vector3d2.z, vector3d2.x) * 57.2957763671875) - 90.0f;
            float f2 = MathHelper.wrapDegrees(f - this.rotationYaw);
            this.moveForward = 0.5f;
            this.rotationYaw += f2;
            if (this.rand.nextInt(100) == 0 && this.world.getBlockState(blockPos2).isNormalCube(this.world, blockPos2)) {
                this.setIsBatHanging(false);
            }
        }
    }

    @Override
    protected boolean canTriggerWalking() {
        return true;
    }

    @Override
    public boolean onLivingFall(float f, float f2) {
        return true;
    }

    @Override
    protected void updateFallState(double d, boolean bl, BlockState blockState, BlockPos blockPos) {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        if (!this.world.isRemote && this.getIsBatHanging()) {
            this.setIsBatHanging(true);
        }
        return super.attackEntityFrom(damageSource, f);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.dataManager.set(HANGING, compoundNBT.getByte("BatFlags"));
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putByte("BatFlags", this.dataManager.get(HANGING));
    }

    public static boolean canSpawn(EntityType<BatEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        if (blockPos.getY() >= iWorld.getSeaLevel()) {
            return true;
        }
        int n = iWorld.getLight(blockPos);
        int n2 = 4;
        if (BatEntity.isNearHalloween()) {
            n2 = 7;
        } else if (random2.nextBoolean()) {
            return true;
        }
        return n > random2.nextInt(n2) ? false : BatEntity.canSpawnOn(entityType, iWorld, spawnReason, blockPos, random2);
    }

    private static boolean isNearHalloween() {
        LocalDate localDate = LocalDate.now();
        int n = localDate.get(ChronoField.DAY_OF_MONTH);
        int n2 = localDate.get(ChronoField.MONTH_OF_YEAR);
        return n2 == 10 && n >= 20 || n2 == 11 && n <= 3;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return entitySize.height / 2.0f;
    }
}

