/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;

public class PhantomEntity
extends FlyingEntity
implements IMob {
    private static final DataParameter<Integer> SIZE = EntityDataManager.createKey(PhantomEntity.class, DataSerializers.VARINT);
    private Vector3d orbitOffset = Vector3d.ZERO;
    private BlockPos orbitPosition = BlockPos.ZERO;
    private AttackPhase attackPhase = AttackPhase.CIRCLE;

    public PhantomEntity(EntityType<? extends PhantomEntity> entityType, World world) {
        super((EntityType<? extends FlyingEntity>)entityType, world);
        this.experienceValue = 5;
        this.moveController = new MoveHelperController(this, this);
        this.lookController = new LookHelperController(this, this);
    }

    @Override
    protected BodyController createBodyController() {
        return new BodyHelperController(this, this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PickAttackGoal(this));
        this.goalSelector.addGoal(2, new SweepAttackGoal(this));
        this.goalSelector.addGoal(3, new OrbitPointGoal(this));
        this.targetSelector.addGoal(1, new AttackPlayerGoal(this));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SIZE, 0);
    }

    public void setPhantomSize(int n) {
        this.dataManager.set(SIZE, MathHelper.clamp(n, 0, 64));
    }

    private void updatePhantomSize() {
        this.recalculateSize();
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6 + this.getPhantomSize());
    }

    public int getPhantomSize() {
        return this.dataManager.get(SIZE);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return entitySize.height * 0.35f;
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        if (SIZE.equals(dataParameter)) {
            this.updatePhantomSize();
        }
        super.notifyDataManagerChange(dataParameter);
    }

    @Override
    protected boolean isDespawnPeaceful() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isRemote) {
            float f = MathHelper.cos((float)(this.getEntityId() * 3 + this.ticksExisted) * 0.13f + (float)Math.PI);
            float f2 = MathHelper.cos((float)(this.getEntityId() * 3 + this.ticksExisted + 1) * 0.13f + (float)Math.PI);
            if (f > 0.0f && f2 <= 0.0f) {
                this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_PHANTOM_FLAP, this.getSoundCategory(), 0.95f + this.rand.nextFloat() * 0.05f, 0.95f + this.rand.nextFloat() * 0.05f, true);
            }
            int n = this.getPhantomSize();
            float f3 = MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180)) * (1.3f + 0.21f * (float)n);
            float f4 = MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180)) * (1.3f + 0.21f * (float)n);
            float f5 = (0.3f + f * 0.45f) * ((float)n * 0.2f + 1.0f);
            this.world.addParticle(ParticleTypes.MYCELIUM, this.getPosX() + (double)f3, this.getPosY() + (double)f5, this.getPosZ() + (double)f4, 0.0, 0.0, 0.0);
            this.world.addParticle(ParticleTypes.MYCELIUM, this.getPosX() - (double)f3, this.getPosY() + (double)f5, this.getPosZ() - (double)f4, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void livingTick() {
        if (this.isAlive() && this.isInDaylight()) {
            this.setFire(8);
        }
        super.livingTick();
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        this.orbitPosition = this.getPosition().up(5);
        this.setPhantomSize(0);
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("AX")) {
            this.orbitPosition = new BlockPos(compoundNBT.getInt("AX"), compoundNBT.getInt("AY"), compoundNBT.getInt("AZ"));
        }
        this.setPhantomSize(compoundNBT.getInt("Size"));
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("AX", this.orbitPosition.getX());
        compoundNBT.putInt("AY", this.orbitPosition.getY());
        compoundNBT.putInt("AZ", this.orbitPosition.getZ());
        compoundNBT.putInt("Size", this.getPhantomSize());
    }

    @Override
    public boolean isInRangeToRenderDist(double d) {
        return false;
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PHANTOM_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_PHANTOM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PHANTOM_DEATH;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    @Override
    protected float getSoundVolume() {
        return 1.0f;
    }

    @Override
    public boolean canAttack(EntityType<?> entityType) {
        return false;
    }

    @Override
    public EntitySize getSize(Pose pose) {
        int n = this.getPhantomSize();
        EntitySize entitySize = super.getSize(pose);
        float f = (entitySize.width + 0.2f * (float)n) / entitySize.width;
        return entitySize.scale(f);
    }

    static Random access$000(PhantomEntity phantomEntity) {
        return phantomEntity.rand;
    }

    static Random access$100(PhantomEntity phantomEntity) {
        return phantomEntity.rand;
    }

    static Random access$200(PhantomEntity phantomEntity) {
        return phantomEntity.rand;
    }

    static Random access$300(PhantomEntity phantomEntity) {
        return phantomEntity.rand;
    }

    static Random access$400(PhantomEntity phantomEntity) {
        return phantomEntity.rand;
    }

    static Random access$500(PhantomEntity phantomEntity) {
        return phantomEntity.rand;
    }

    static Random access$600(PhantomEntity phantomEntity) {
        return phantomEntity.rand;
    }

    static Random access$700(PhantomEntity phantomEntity) {
        return phantomEntity.rand;
    }

    static Random access$800(PhantomEntity phantomEntity) {
        return phantomEntity.rand;
    }

    static Random access$900(PhantomEntity phantomEntity) {
        return phantomEntity.rand;
    }

    static Random access$1000(PhantomEntity phantomEntity) {
        return phantomEntity.rand;
    }

    static Random access$1100(PhantomEntity phantomEntity) {
        return phantomEntity.rand;
    }

    static enum AttackPhase {
        CIRCLE,
        SWOOP;

    }

    class MoveHelperController
    extends MovementController {
        private float speedFactor;
        final PhantomEntity this$0;

        public MoveHelperController(PhantomEntity phantomEntity, MobEntity mobEntity) {
            this.this$0 = phantomEntity;
            super(mobEntity);
            this.speedFactor = 0.1f;
        }

        @Override
        public void tick() {
            float f;
            if (this.this$0.collidedHorizontally) {
                this.this$0.rotationYaw += 180.0f;
                this.speedFactor = 0.1f;
            }
            float f2 = (float)(this.this$0.orbitOffset.x - this.this$0.getPosX());
            float f3 = (float)(this.this$0.orbitOffset.y - this.this$0.getPosY());
            float f4 = (float)(this.this$0.orbitOffset.z - this.this$0.getPosZ());
            double d = MathHelper.sqrt(f2 * f2 + f4 * f4);
            double d2 = 1.0 - (double)MathHelper.abs(f3 * 0.7f) / d;
            f2 = (float)((double)f2 * d2);
            f4 = (float)((double)f4 * d2);
            d = MathHelper.sqrt(f2 * f2 + f4 * f4);
            double d3 = MathHelper.sqrt(f2 * f2 + f4 * f4 + f3 * f3);
            float f5 = this.this$0.rotationYaw;
            float f6 = (float)MathHelper.atan2(f4, f2);
            float f7 = MathHelper.wrapDegrees(this.this$0.rotationYaw + 90.0f);
            float f8 = MathHelper.wrapDegrees(f6 * 57.295776f);
            this.this$0.renderYawOffset = this.this$0.rotationYaw = MathHelper.approachDegrees(f7, f8, 4.0f) - 90.0f;
            this.speedFactor = MathHelper.degreesDifferenceAbs(f5, this.this$0.rotationYaw) < 3.0f ? MathHelper.approach(this.speedFactor, 1.8f, 0.005f * (1.8f / this.speedFactor)) : MathHelper.approach(this.speedFactor, 0.2f, 0.025f);
            this.this$0.rotationPitch = f = (float)(-(MathHelper.atan2(-f3, d) * 57.2957763671875));
            float f9 = this.this$0.rotationYaw + 90.0f;
            double d4 = (double)(this.speedFactor * MathHelper.cos(f9 * ((float)Math.PI / 180))) * Math.abs((double)f2 / d3);
            double d5 = (double)(this.speedFactor * MathHelper.sin(f9 * ((float)Math.PI / 180))) * Math.abs((double)f4 / d3);
            double d6 = (double)(this.speedFactor * MathHelper.sin(f * ((float)Math.PI / 180))) * Math.abs((double)f3 / d3);
            Vector3d vector3d = this.this$0.getMotion();
            this.this$0.setMotion(vector3d.add(new Vector3d(d4, d6, d5).subtract(vector3d).scale(0.2)));
        }
    }

    class LookHelperController
    extends LookController {
        final PhantomEntity this$0;

        public LookHelperController(PhantomEntity phantomEntity, MobEntity mobEntity) {
            this.this$0 = phantomEntity;
            super(mobEntity);
        }

        @Override
        public void tick() {
        }
    }

    class BodyHelperController
    extends BodyController {
        final PhantomEntity this$0;

        public BodyHelperController(PhantomEntity phantomEntity, MobEntity mobEntity) {
            this.this$0 = phantomEntity;
            super(mobEntity);
        }

        @Override
        public void updateRenderAngles() {
            this.this$0.rotationYawHead = this.this$0.renderYawOffset;
            this.this$0.renderYawOffset = this.this$0.rotationYaw;
        }
    }

    class PickAttackGoal
    extends Goal {
        private int tickDelay;
        final PhantomEntity this$0;

        private PickAttackGoal(PhantomEntity phantomEntity) {
            this.this$0 = phantomEntity;
        }

        @Override
        public boolean shouldExecute() {
            LivingEntity livingEntity = this.this$0.getAttackTarget();
            return livingEntity != null ? this.this$0.canAttack(this.this$0.getAttackTarget(), EntityPredicate.DEFAULT) : false;
        }

        @Override
        public void startExecuting() {
            this.tickDelay = 10;
            this.this$0.attackPhase = AttackPhase.CIRCLE;
            this.func_203143_f();
        }

        @Override
        public void resetTask() {
            this.this$0.orbitPosition = this.this$0.world.getHeight(Heightmap.Type.MOTION_BLOCKING, this.this$0.orbitPosition).up(10 + PhantomEntity.access$800(this.this$0).nextInt(20));
        }

        @Override
        public void tick() {
            if (this.this$0.attackPhase == AttackPhase.CIRCLE) {
                --this.tickDelay;
                if (this.tickDelay <= 0) {
                    this.this$0.attackPhase = AttackPhase.SWOOP;
                    this.func_203143_f();
                    this.tickDelay = (8 + PhantomEntity.access$900(this.this$0).nextInt(4)) * 20;
                    this.this$0.playSound(SoundEvents.ENTITY_PHANTOM_SWOOP, 10.0f, 0.95f + PhantomEntity.access$1000(this.this$0).nextFloat() * 0.1f);
                }
            }
        }

        private void func_203143_f() {
            this.this$0.orbitPosition = this.this$0.getAttackTarget().getPosition().up(20 + PhantomEntity.access$1100(this.this$0).nextInt(20));
            if (this.this$0.orbitPosition.getY() < this.this$0.world.getSeaLevel()) {
                this.this$0.orbitPosition = new BlockPos(this.this$0.orbitPosition.getX(), this.this$0.world.getSeaLevel() + 1, this.this$0.orbitPosition.getZ());
            }
        }
    }

    class SweepAttackGoal
    extends MoveGoal {
        final PhantomEntity this$0;

        private SweepAttackGoal(PhantomEntity phantomEntity) {
            this.this$0 = phantomEntity;
            super(phantomEntity);
        }

        @Override
        public boolean shouldExecute() {
            return this.this$0.getAttackTarget() != null && this.this$0.attackPhase == AttackPhase.SWOOP;
        }

        @Override
        public boolean shouldContinueExecuting() {
            LivingEntity livingEntity = this.this$0.getAttackTarget();
            if (livingEntity == null) {
                return true;
            }
            if (!livingEntity.isAlive()) {
                return true;
            }
            if (!(livingEntity instanceof PlayerEntity) || !((PlayerEntity)livingEntity).isSpectator() && !((PlayerEntity)livingEntity).isCreative()) {
                List<Entity> list;
                if (!this.shouldExecute()) {
                    return true;
                }
                if (this.this$0.ticksExisted % 20 == 0 && !(list = this.this$0.world.getEntitiesWithinAABB(CatEntity.class, this.this$0.getBoundingBox().grow(16.0), EntityPredicates.IS_ALIVE)).isEmpty()) {
                    for (CatEntity catEntity : list) {
                        catEntity.func_213420_ej();
                    }
                    return true;
                }
                return false;
            }
            return true;
        }

        @Override
        public void startExecuting() {
        }

        @Override
        public void resetTask() {
            this.this$0.setAttackTarget(null);
            this.this$0.attackPhase = AttackPhase.CIRCLE;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.this$0.getAttackTarget();
            this.this$0.orbitOffset = new Vector3d(livingEntity.getPosX(), livingEntity.getPosYHeight(0.5), livingEntity.getPosZ());
            if (this.this$0.getBoundingBox().grow(0.2f).intersects(livingEntity.getBoundingBox())) {
                this.this$0.attackEntityAsMob(livingEntity);
                this.this$0.attackPhase = AttackPhase.CIRCLE;
                if (!this.this$0.isSilent()) {
                    this.this$0.world.playEvent(1039, this.this$0.getPosition(), 0);
                }
            } else if (this.this$0.collidedHorizontally || this.this$0.hurtTime > 0) {
                this.this$0.attackPhase = AttackPhase.CIRCLE;
            }
        }
    }

    class OrbitPointGoal
    extends MoveGoal {
        private float field_203150_c;
        private float field_203151_d;
        private float field_203152_e;
        private float field_203153_f;
        final PhantomEntity this$0;

        private OrbitPointGoal(PhantomEntity phantomEntity) {
            this.this$0 = phantomEntity;
            super(phantomEntity);
        }

        @Override
        public boolean shouldExecute() {
            return this.this$0.getAttackTarget() == null || this.this$0.attackPhase == AttackPhase.CIRCLE;
        }

        @Override
        public void startExecuting() {
            this.field_203151_d = 5.0f + PhantomEntity.access$000(this.this$0).nextFloat() * 10.0f;
            this.field_203152_e = -4.0f + PhantomEntity.access$100(this.this$0).nextFloat() * 9.0f;
            this.field_203153_f = PhantomEntity.access$200(this.this$0).nextBoolean() ? 1.0f : -1.0f;
            this.func_203148_i();
        }

        @Override
        public void tick() {
            if (PhantomEntity.access$300(this.this$0).nextInt(350) == 0) {
                this.field_203152_e = -4.0f + PhantomEntity.access$400(this.this$0).nextFloat() * 9.0f;
            }
            if (PhantomEntity.access$500(this.this$0).nextInt(250) == 0) {
                this.field_203151_d += 1.0f;
                if (this.field_203151_d > 15.0f) {
                    this.field_203151_d = 5.0f;
                    this.field_203153_f = -this.field_203153_f;
                }
            }
            if (PhantomEntity.access$600(this.this$0).nextInt(450) == 0) {
                this.field_203150_c = PhantomEntity.access$700(this.this$0).nextFloat() * 2.0f * (float)Math.PI;
                this.func_203148_i();
            }
            if (this.func_203146_f()) {
                this.func_203148_i();
            }
            if (this.this$0.orbitOffset.y < this.this$0.getPosY() && !this.this$0.world.isAirBlock(this.this$0.getPosition().down(1))) {
                this.field_203152_e = Math.max(1.0f, this.field_203152_e);
                this.func_203148_i();
            }
            if (this.this$0.orbitOffset.y > this.this$0.getPosY() && !this.this$0.world.isAirBlock(this.this$0.getPosition().up(1))) {
                this.field_203152_e = Math.min(-1.0f, this.field_203152_e);
                this.func_203148_i();
            }
        }

        private void func_203148_i() {
            if (BlockPos.ZERO.equals(this.this$0.orbitPosition)) {
                this.this$0.orbitPosition = this.this$0.getPosition();
            }
            this.field_203150_c += this.field_203153_f * 15.0f * ((float)Math.PI / 180);
            this.this$0.orbitOffset = Vector3d.copy(this.this$0.orbitPosition).add(this.field_203151_d * MathHelper.cos(this.field_203150_c), -4.0f + this.field_203152_e, this.field_203151_d * MathHelper.sin(this.field_203150_c));
        }
    }

    class AttackPlayerGoal
    extends Goal {
        private final EntityPredicate field_220842_b;
        private int tickDelay;
        final PhantomEntity this$0;

        private AttackPlayerGoal(PhantomEntity phantomEntity) {
            this.this$0 = phantomEntity;
            this.field_220842_b = new EntityPredicate().setDistance(64.0);
            this.tickDelay = 20;
        }

        @Override
        public boolean shouldExecute() {
            if (this.tickDelay > 0) {
                --this.tickDelay;
                return true;
            }
            this.tickDelay = 60;
            List<PlayerEntity> list = this.this$0.world.getTargettablePlayersWithinAABB(this.field_220842_b, this.this$0, this.this$0.getBoundingBox().grow(16.0, 64.0, 16.0));
            if (!list.isEmpty()) {
                list.sort(Comparator.comparing(Entity::getPosY).reversed());
                for (PlayerEntity playerEntity : list) {
                    if (!this.this$0.canAttack(playerEntity, EntityPredicate.DEFAULT)) continue;
                    this.this$0.setAttackTarget(playerEntity);
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean shouldContinueExecuting() {
            LivingEntity livingEntity = this.this$0.getAttackTarget();
            return livingEntity != null ? this.this$0.canAttack(livingEntity, EntityPredicate.DEFAULT) : false;
        }
    }

    abstract class MoveGoal
    extends Goal {
        final PhantomEntity this$0;

        public MoveGoal(PhantomEntity phantomEntity) {
            this.this$0 = phantomEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        protected boolean func_203146_f() {
            return this.this$0.orbitOffset.squareDistanceTo(this.this$0.getPosX(), this.this$0.getPosY(), this.this$0.getPosZ()) < 4.0;
        }
    }
}

