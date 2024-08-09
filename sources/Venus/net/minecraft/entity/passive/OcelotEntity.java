/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OcelotAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class OcelotEntity
extends AnimalEntity {
    private static final Ingredient BREEDING_ITEMS = Ingredient.fromItems(Items.COD, Items.SALMON);
    private static final DataParameter<Boolean> IS_TRUSTING = EntityDataManager.createKey(OcelotEntity.class, DataSerializers.BOOLEAN);
    private AvoidEntityGoal<PlayerEntity> field_213531_bB;
    private TemptGoal aiTempt;

    public OcelotEntity(EntityType<? extends OcelotEntity> entityType, World world) {
        super((EntityType<? extends AnimalEntity>)entityType, world);
        this.func_213529_dV();
    }

    private boolean isTrusting() {
        return this.dataManager.get(IS_TRUSTING);
    }

    private void setTrusting(boolean bl) {
        this.dataManager.set(IS_TRUSTING, bl);
        this.func_213529_dV();
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putBoolean("Trusting", this.isTrusting());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setTrusting(compoundNBT.getBoolean("Trusting"));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(IS_TRUSTING, false);
    }

    @Override
    protected void registerGoals() {
        this.aiTempt = new TemptGoal(this, 0.6, BREEDING_ITEMS, true);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(3, this.aiTempt);
        this.goalSelector.addGoal(7, new LeapAtTargetGoal(this, 0.3f));
        this.goalSelector.addGoal(8, new OcelotAttackGoal(this));
        this.goalSelector.addGoal(9, new BreedGoal(this, 0.8));
        this.goalSelector.addGoal(10, new WaterAvoidingRandomWalkingGoal((CreatureEntity)this, 0.8, 1.0000001E-5f));
        this.goalSelector.addGoal(11, new LookAtGoal(this, PlayerEntity.class, 10.0f));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<ChickenEntity>((MobEntity)this, ChickenEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<TurtleEntity>(this, TurtleEntity.class, 10, false, false, TurtleEntity.TARGET_DRY_BABY));
    }

    @Override
    public void updateAITasks() {
        if (this.getMoveHelper().isUpdating()) {
            double d = this.getMoveHelper().getSpeed();
            if (d == 0.6) {
                this.setPose(Pose.CROUCHING);
                this.setSprinting(true);
            } else if (d == 1.33) {
                this.setPose(Pose.STANDING);
                this.setSprinting(false);
            } else {
                this.setPose(Pose.STANDING);
                this.setSprinting(true);
            }
        } else {
            this.setPose(Pose.STANDING);
            this.setSprinting(true);
        }
    }

    @Override
    public boolean canDespawn(double d) {
        return !this.isTrusting() && this.ticksExisted > 2400;
    }

    public static AttributeModifierMap.MutableAttribute func_234201_eI_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3f).createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0);
    }

    @Override
    public boolean onLivingFall(float f, float f2) {
        return true;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_OCELOT_AMBIENT;
    }

    @Override
    public int getTalkInterval() {
        return 1;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_OCELOT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_OCELOT_DEATH;
    }

    private float func_226517_es_() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity2) {
        return entity2.attackEntityFrom(DamageSource.causeMobDamage(this), this.func_226517_es_());
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        return this.isInvulnerableTo(damageSource) ? false : super.attackEntityFrom(damageSource, f);
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if ((this.aiTempt == null || this.aiTempt.isRunning()) && !this.isTrusting() && this.isBreedingItem(itemStack) && playerEntity.getDistanceSq(this) < 9.0) {
            this.consumeItemFromStack(playerEntity, itemStack);
            if (!this.world.isRemote) {
                if (this.rand.nextInt(3) == 0) {
                    this.setTrusting(false);
                    this.func_213527_s(false);
                    this.world.setEntityState(this, (byte)41);
                } else {
                    this.func_213527_s(true);
                    this.world.setEntityState(this, (byte)40);
                }
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        return super.func_230254_b_(playerEntity, hand);
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 41) {
            this.func_213527_s(false);
        } else if (by == 40) {
            this.func_213527_s(true);
        } else {
            super.handleStatusUpdate(by);
        }
    }

    private void func_213527_s(boolean bl) {
        BasicParticleType basicParticleType = ParticleTypes.HEART;
        if (!bl) {
            basicParticleType = ParticleTypes.SMOKE;
        }
        for (int i = 0; i < 7; ++i) {
            double d = this.rand.nextGaussian() * 0.02;
            double d2 = this.rand.nextGaussian() * 0.02;
            double d3 = this.rand.nextGaussian() * 0.02;
            this.world.addParticle(basicParticleType, this.getPosXRandom(1.0), this.getPosYRandom() + 0.5, this.getPosZRandom(1.0), d, d2, d3);
        }
    }

    protected void func_213529_dV() {
        if (this.field_213531_bB == null) {
            this.field_213531_bB = new AvoidEntityGoal<PlayerEntity>(this, PlayerEntity.class, 16.0f, 0.8, 1.33);
        }
        this.goalSelector.removeGoal(this.field_213531_bB);
        if (!this.isTrusting()) {
            this.goalSelector.addGoal(4, this.field_213531_bB);
        }
    }

    @Override
    public OcelotEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return EntityType.OCELOT.create(serverWorld);
    }

    @Override
    public boolean isBreedingItem(ItemStack itemStack) {
        return BREEDING_ITEMS.test(itemStack);
    }

    public static boolean func_223319_c(EntityType<OcelotEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        return random2.nextInt(3) != 0;
    }

    @Override
    public boolean isNotColliding(IWorldReader iWorldReader) {
        if (iWorldReader.checkNoEntityCollision(this) && !iWorldReader.containsAnyLiquid(this.getBoundingBox())) {
            BlockPos blockPos = this.getPosition();
            if (blockPos.getY() < iWorldReader.getSeaLevel()) {
                return true;
            }
            BlockState blockState = iWorldReader.getBlockState(blockPos.down());
            if (blockState.isIn(Blocks.GRASS_BLOCK) || blockState.isIn(BlockTags.LEAVES)) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        if (iLivingEntityData == null) {
            iLivingEntityData = new AgeableEntity.AgeableData(1.0f);
        }
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    @Override
    public Vector3d func_241205_ce_() {
        return new Vector3d(0.0, 0.5f * this.getEyeHeight(), this.getWidth() * 0.4f);
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return this.func_241840_a(serverWorld, ageableEntity);
    }

    static class TemptGoal
    extends net.minecraft.entity.ai.goal.TemptGoal {
        private final OcelotEntity ocelot;

        public TemptGoal(OcelotEntity ocelotEntity, double d, Ingredient ingredient, boolean bl) {
            super((CreatureEntity)ocelotEntity, d, ingredient, bl);
            this.ocelot = ocelotEntity;
        }

        @Override
        protected boolean isScaredByPlayerMovement() {
            return super.isScaredByPlayerMovement() && !this.ocelot.isTrusting();
        }
    }

    static class AvoidEntityGoal<T extends LivingEntity>
    extends net.minecraft.entity.ai.goal.AvoidEntityGoal<T> {
        private final OcelotEntity ocelot;

        public AvoidEntityGoal(OcelotEntity ocelotEntity, Class<T> clazz, float f, double d, double d2) {
            super(ocelotEntity, clazz, f, d, d2, EntityPredicates.CAN_AI_TARGET::test);
            this.ocelot = ocelotEntity;
        }

        @Override
        public boolean shouldExecute() {
            return !this.ocelot.isTrusting() && super.shouldExecute();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return !this.ocelot.isTrusting() && super.shouldContinueExecuting();
        }
    }
}

