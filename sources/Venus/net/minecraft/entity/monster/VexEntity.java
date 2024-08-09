/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class VexEntity
extends MonsterEntity {
    protected static final DataParameter<Byte> VEX_FLAGS = EntityDataManager.createKey(VexEntity.class, DataSerializers.BYTE);
    private MobEntity owner;
    @Nullable
    private BlockPos boundOrigin;
    private boolean limitedLifespan;
    private int limitedLifeTicks;

    public VexEntity(EntityType<? extends VexEntity> entityType, World world) {
        super((EntityType<? extends MonsterEntity>)entityType, world);
        this.moveController = new MoveHelperController(this, this);
        this.experienceValue = 3;
    }

    @Override
    public void move(MoverType moverType, Vector3d vector3d) {
        super.move(moverType, vector3d);
        this.doBlockCollisions();
    }

    @Override
    public void tick() {
        this.noClip = true;
        super.tick();
        this.noClip = false;
        this.setNoGravity(false);
        if (this.limitedLifespan && --this.limitedLifeTicks <= 0) {
            this.limitedLifeTicks = 20;
            this.attackEntityFrom(DamageSource.STARVE, 1.0f);
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(4, new ChargeAttackGoal(this));
        this.goalSelector.addGoal(8, new MoveRandomGoal(this));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, AbstractRaiderEntity.class).setCallsForHelp(new Class[0]));
        this.targetSelector.addGoal(2, new CopyOwnerTargetGoal(this, this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute func_234321_m_() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 14.0).createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(VEX_FLAGS, (byte)0);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("BoundX")) {
            this.boundOrigin = new BlockPos(compoundNBT.getInt("BoundX"), compoundNBT.getInt("BoundY"), compoundNBT.getInt("BoundZ"));
        }
        if (compoundNBT.contains("LifeTicks")) {
            this.setLimitedLife(compoundNBT.getInt("LifeTicks"));
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        if (this.boundOrigin != null) {
            compoundNBT.putInt("BoundX", this.boundOrigin.getX());
            compoundNBT.putInt("BoundY", this.boundOrigin.getY());
            compoundNBT.putInt("BoundZ", this.boundOrigin.getZ());
        }
        if (this.limitedLifespan) {
            compoundNBT.putInt("LifeTicks", this.limitedLifeTicks);
        }
    }

    public MobEntity getOwner() {
        return this.owner;
    }

    @Nullable
    public BlockPos getBoundOrigin() {
        return this.boundOrigin;
    }

    public void setBoundOrigin(@Nullable BlockPos blockPos) {
        this.boundOrigin = blockPos;
    }

    private boolean getVexFlag(int n) {
        byte by = this.dataManager.get(VEX_FLAGS);
        return (by & n) != 0;
    }

    private void setVexFlag(int n, boolean bl) {
        int n2 = this.dataManager.get(VEX_FLAGS).byteValue();
        n2 = bl ? (n2 |= n) : (n2 &= ~n);
        this.dataManager.set(VEX_FLAGS, (byte)(n2 & 0xFF));
    }

    public boolean isCharging() {
        return this.getVexFlag(0);
    }

    public void setCharging(boolean bl) {
        this.setVexFlag(1, bl);
    }

    public void setOwner(MobEntity mobEntity) {
        this.owner = mobEntity;
    }

    public void setLimitedLife(int n) {
        this.limitedLifespan = true;
        this.limitedLifeTicks = n;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_VEX_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VEX_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_VEX_HURT;
    }

    @Override
    public float getBrightness() {
        return 1.0f;
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        this.setEquipmentBasedOnDifficulty(difficultyInstance);
        this.setEnchantmentBasedOnDifficulty(difficultyInstance);
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
    }

    static Random access$000(VexEntity vexEntity) {
        return vexEntity.rand;
    }

    static MovementController access$100(VexEntity vexEntity) {
        return vexEntity.moveController;
    }

    static MovementController access$200(VexEntity vexEntity) {
        return vexEntity.moveController;
    }

    static Random access$300(VexEntity vexEntity) {
        return vexEntity.rand;
    }

    static Random access$400(VexEntity vexEntity) {
        return vexEntity.rand;
    }

    static Random access$500(VexEntity vexEntity) {
        return vexEntity.rand;
    }

    static Random access$600(VexEntity vexEntity) {
        return vexEntity.rand;
    }

    static MovementController access$700(VexEntity vexEntity) {
        return vexEntity.moveController;
    }

    class MoveHelperController
    extends MovementController {
        final VexEntity this$0;

        public MoveHelperController(VexEntity vexEntity, VexEntity vexEntity2) {
            this.this$0 = vexEntity;
            super(vexEntity2);
        }

        @Override
        public void tick() {
            if (this.action == MovementController.Action.MOVE_TO) {
                Vector3d vector3d = new Vector3d(this.posX - this.this$0.getPosX(), this.posY - this.this$0.getPosY(), this.posZ - this.this$0.getPosZ());
                double d = vector3d.length();
                if (d < this.this$0.getBoundingBox().getAverageEdgeLength()) {
                    this.action = MovementController.Action.WAIT;
                    this.this$0.setMotion(this.this$0.getMotion().scale(0.5));
                } else {
                    this.this$0.setMotion(this.this$0.getMotion().add(vector3d.scale(this.speed * 0.05 / d)));
                    if (this.this$0.getAttackTarget() == null) {
                        Vector3d vector3d2 = this.this$0.getMotion();
                        this.this$0.renderYawOffset = this.this$0.rotationYaw = -((float)MathHelper.atan2(vector3d2.x, vector3d2.z)) * 57.295776f;
                    } else {
                        double d2 = this.this$0.getAttackTarget().getPosX() - this.this$0.getPosX();
                        double d3 = this.this$0.getAttackTarget().getPosZ() - this.this$0.getPosZ();
                        this.this$0.renderYawOffset = this.this$0.rotationYaw = -((float)MathHelper.atan2(d2, d3)) * 57.295776f;
                    }
                }
            }
        }
    }

    class ChargeAttackGoal
    extends Goal {
        final VexEntity this$0;

        public ChargeAttackGoal(VexEntity vexEntity) {
            this.this$0 = vexEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            if (this.this$0.getAttackTarget() != null && !this.this$0.getMoveHelper().isUpdating() && VexEntity.access$000(this.this$0).nextInt(7) == 0) {
                return this.this$0.getDistanceSq(this.this$0.getAttackTarget()) > 4.0;
            }
            return true;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.this$0.getMoveHelper().isUpdating() && this.this$0.isCharging() && this.this$0.getAttackTarget() != null && this.this$0.getAttackTarget().isAlive();
        }

        @Override
        public void startExecuting() {
            LivingEntity livingEntity = this.this$0.getAttackTarget();
            Vector3d vector3d = livingEntity.getEyePosition(1.0f);
            VexEntity.access$100(this.this$0).setMoveTo(vector3d.x, vector3d.y, vector3d.z, 1.0);
            this.this$0.setCharging(false);
            this.this$0.playSound(SoundEvents.ENTITY_VEX_CHARGE, 1.0f, 1.0f);
        }

        @Override
        public void resetTask() {
            this.this$0.setCharging(true);
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.this$0.getAttackTarget();
            if (this.this$0.getBoundingBox().intersects(livingEntity.getBoundingBox())) {
                this.this$0.attackEntityAsMob(livingEntity);
                this.this$0.setCharging(true);
            } else {
                double d = this.this$0.getDistanceSq(livingEntity);
                if (d < 9.0) {
                    Vector3d vector3d = livingEntity.getEyePosition(1.0f);
                    VexEntity.access$200(this.this$0).setMoveTo(vector3d.x, vector3d.y, vector3d.z, 1.0);
                }
            }
        }
    }

    class MoveRandomGoal
    extends Goal {
        final VexEntity this$0;

        public MoveRandomGoal(VexEntity vexEntity) {
            this.this$0 = vexEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            return !this.this$0.getMoveHelper().isUpdating() && VexEntity.access$300(this.this$0).nextInt(7) == 0;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return true;
        }

        @Override
        public void tick() {
            BlockPos blockPos = this.this$0.getBoundOrigin();
            if (blockPos == null) {
                blockPos = this.this$0.getPosition();
            }
            for (int i = 0; i < 3; ++i) {
                BlockPos blockPos2 = blockPos.add(VexEntity.access$400(this.this$0).nextInt(15) - 7, VexEntity.access$500(this.this$0).nextInt(11) - 5, VexEntity.access$600(this.this$0).nextInt(15) - 7);
                if (!this.this$0.world.isAirBlock(blockPos2)) continue;
                VexEntity.access$700(this.this$0).setMoveTo((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.5, (double)blockPos2.getZ() + 0.5, 0.25);
                if (this.this$0.getAttackTarget() != null) break;
                this.this$0.getLookController().setLookPosition((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.5, (double)blockPos2.getZ() + 0.5, 180.0f, 20.0f);
                break;
            }
        }
    }

    class CopyOwnerTargetGoal
    extends TargetGoal {
        private final EntityPredicate field_220803_b;
        final VexEntity this$0;

        public CopyOwnerTargetGoal(VexEntity vexEntity, CreatureEntity creatureEntity) {
            this.this$0 = vexEntity;
            super(creatureEntity, false);
            this.field_220803_b = new EntityPredicate().setLineOfSiteRequired().setUseInvisibilityCheck();
        }

        @Override
        public boolean shouldExecute() {
            return this.this$0.owner != null && this.this$0.owner.getAttackTarget() != null && this.isSuitableTarget(this.this$0.owner.getAttackTarget(), this.field_220803_b);
        }

        @Override
        public void startExecuting() {
            this.this$0.setAttackTarget(this.this$0.owner.getAttackTarget());
            super.startExecuting();
        }
    }
}

