/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss;

import com.google.common.collect.ImmutableList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IChargeableMob;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;

public class WitherEntity
extends MonsterEntity
implements IChargeableMob,
IRangedAttackMob {
    private static final DataParameter<Integer> FIRST_HEAD_TARGET = EntityDataManager.createKey(WitherEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> SECOND_HEAD_TARGET = EntityDataManager.createKey(WitherEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> THIRD_HEAD_TARGET = EntityDataManager.createKey(WitherEntity.class, DataSerializers.VARINT);
    private static final List<DataParameter<Integer>> HEAD_TARGETS = ImmutableList.of(FIRST_HEAD_TARGET, SECOND_HEAD_TARGET, THIRD_HEAD_TARGET);
    private static final DataParameter<Integer> INVULNERABILITY_TIME = EntityDataManager.createKey(WitherEntity.class, DataSerializers.VARINT);
    private final float[] xRotationHeads = new float[2];
    private final float[] yRotationHeads = new float[2];
    private final float[] xRotOHeads = new float[2];
    private final float[] yRotOHeads = new float[2];
    private final int[] nextHeadUpdate = new int[2];
    private final int[] idleHeadUpdates = new int[2];
    private int blockBreakCounter;
    private final ServerBossInfo bossInfo = (ServerBossInfo)new ServerBossInfo(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS).setDarkenSky(false);
    private static final Predicate<LivingEntity> NOT_UNDEAD = WitherEntity::lambda$static$0;
    private static final EntityPredicate ENEMY_CONDITION = new EntityPredicate().setDistance(20.0).setCustomPredicate(NOT_UNDEAD);

    public WitherEntity(EntityType<? extends WitherEntity> entityType, World world) {
        super((EntityType<? extends MonsterEntity>)entityType, world);
        this.setHealth(this.getMaxHealth());
        this.getNavigator().setCanSwim(false);
        this.experienceValue = 50;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new DoNothingGoal(this));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0, 40, 20.0f));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<MobEntity>(this, MobEntity.class, 0, false, false, NOT_UNDEAD));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(FIRST_HEAD_TARGET, 0);
        this.dataManager.register(SECOND_HEAD_TARGET, 0);
        this.dataManager.register(THIRD_HEAD_TARGET, 0);
        this.dataManager.register(INVULNERABILITY_TIME, 0);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("Invul", this.getInvulTime());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setInvulTime(compoundNBT.getInt("Invul"));
        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }
    }

    @Override
    public void setCustomName(@Nullable ITextComponent iTextComponent) {
        super.setCustomName(iTextComponent);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WITHER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_WITHER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WITHER_DEATH;
    }

    @Override
    public void livingTick() {
        int n;
        int n2;
        Entity entity2;
        Vector3d vector3d = this.getMotion().mul(1.0, 0.6, 1.0);
        if (!this.world.isRemote && this.getWatchedTargetId(0) > 0 && (entity2 = this.world.getEntityByID(this.getWatchedTargetId(0))) != null) {
            double d = vector3d.y;
            if (this.getPosY() < entity2.getPosY() || !this.isCharged() && this.getPosY() < entity2.getPosY() + 5.0) {
                d = Math.max(0.0, d);
                d += 0.3 - d * (double)0.6f;
            }
            vector3d = new Vector3d(vector3d.x, d, vector3d.z);
            Vector3d vector3d2 = new Vector3d(entity2.getPosX() - this.getPosX(), 0.0, entity2.getPosZ() - this.getPosZ());
            if (WitherEntity.horizontalMag(vector3d2) > 9.0) {
                Vector3d vector3d3 = vector3d2.normalize();
                vector3d = vector3d.add(vector3d3.x * 0.3 - vector3d.x * 0.6, 0.0, vector3d3.z * 0.3 - vector3d.z * 0.6);
            }
        }
        this.setMotion(vector3d);
        if (WitherEntity.horizontalMag(vector3d) > 0.05) {
            this.rotationYaw = (float)MathHelper.atan2(vector3d.z, vector3d.x) * 57.295776f - 90.0f;
        }
        super.livingTick();
        for (n2 = 0; n2 < 2; ++n2) {
            this.yRotOHeads[n2] = this.yRotationHeads[n2];
            this.xRotOHeads[n2] = this.xRotationHeads[n2];
        }
        for (n2 = 0; n2 < 2; ++n2) {
            int n3 = this.getWatchedTargetId(n2 + 1);
            Entity entity3 = null;
            if (n3 > 0) {
                entity3 = this.world.getEntityByID(n3);
            }
            if (entity3 != null) {
                double d = this.getHeadX(n2 + 1);
                double d2 = this.getHeadY(n2 + 1);
                double d3 = this.getHeadZ(n2 + 1);
                double d4 = entity3.getPosX() - d;
                double d5 = entity3.getPosYEye() - d2;
                double d6 = entity3.getPosZ() - d3;
                double d7 = MathHelper.sqrt(d4 * d4 + d6 * d6);
                float f = (float)(MathHelper.atan2(d6, d4) * 57.2957763671875) - 90.0f;
                float f2 = (float)(-(MathHelper.atan2(d5, d7) * 57.2957763671875));
                this.xRotationHeads[n2] = this.rotlerp(this.xRotationHeads[n2], f2, 40.0f);
                this.yRotationHeads[n2] = this.rotlerp(this.yRotationHeads[n2], f, 10.0f);
                continue;
            }
            this.yRotationHeads[n2] = this.rotlerp(this.yRotationHeads[n2], this.renderYawOffset, 10.0f);
        }
        n2 = this.isCharged() ? 1 : 0;
        for (n = 0; n < 3; ++n) {
            double d = this.getHeadX(n);
            double d8 = this.getHeadY(n);
            double d9 = this.getHeadZ(n);
            this.world.addParticle(ParticleTypes.SMOKE, d + this.rand.nextGaussian() * (double)0.3f, d8 + this.rand.nextGaussian() * (double)0.3f, d9 + this.rand.nextGaussian() * (double)0.3f, 0.0, 0.0, 0.0);
            if (n2 == 0 || this.world.rand.nextInt(4) != 0) continue;
            this.world.addParticle(ParticleTypes.ENTITY_EFFECT, d + this.rand.nextGaussian() * (double)0.3f, d8 + this.rand.nextGaussian() * (double)0.3f, d9 + this.rand.nextGaussian() * (double)0.3f, 0.7f, 0.7f, 0.5);
        }
        if (this.getInvulTime() > 0) {
            for (n = 0; n < 3; ++n) {
                this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getPosX() + this.rand.nextGaussian(), this.getPosY() + (double)(this.rand.nextFloat() * 3.3f), this.getPosZ() + this.rand.nextGaussian(), 0.7f, 0.7f, 0.9f);
            }
        }
    }

    @Override
    protected void updateAITasks() {
        if (this.getInvulTime() > 0) {
            int n = this.getInvulTime() - 1;
            if (n <= 0) {
                Explosion.Mode mode = this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
                this.world.createExplosion(this, this.getPosX(), this.getPosYEye(), this.getPosZ(), 7.0f, false, mode);
                if (!this.isSilent()) {
                    this.world.playBroadcastSound(1023, this.getPosition(), 0);
                }
            }
            this.setInvulTime(n);
            if (this.ticksExisted % 10 == 0) {
                this.heal(10.0f);
            }
        } else {
            int n;
            int n2;
            super.updateAITasks();
            block0: for (n2 = 1; n2 < 3; ++n2) {
                Object object;
                if (this.ticksExisted < this.nextHeadUpdate[n2 - 1]) continue;
                this.nextHeadUpdate[n2 - 1] = this.ticksExisted + 10 + this.rand.nextInt(10);
                if (this.world.getDifficulty() == Difficulty.NORMAL || this.world.getDifficulty() == Difficulty.HARD) {
                    n = n2 - 1;
                    int n3 = this.idleHeadUpdates[n2 - 1];
                    this.idleHeadUpdates[n] = this.idleHeadUpdates[n2 - 1] + 1;
                    if (n3 > 15) {
                        float f = 10.0f;
                        float f2 = 5.0f;
                        double d = MathHelper.nextDouble(this.rand, this.getPosX() - 10.0, this.getPosX() + 10.0);
                        double d2 = MathHelper.nextDouble(this.rand, this.getPosY() - 5.0, this.getPosY() + 5.0);
                        double d3 = MathHelper.nextDouble(this.rand, this.getPosZ() - 10.0, this.getPosZ() + 10.0);
                        this.launchWitherSkullToCoords(n2 + 1, d, d2, d3, false);
                        this.idleHeadUpdates[n2 - 1] = 0;
                    }
                }
                if ((n = this.getWatchedTargetId(n2)) > 0) {
                    object = this.world.getEntityByID(n);
                    if (object != null && ((Entity)object).isAlive() && !(this.getDistanceSq((Entity)object) > 900.0) && this.canEntityBeSeen((Entity)object)) {
                        if (object instanceof PlayerEntity && ((PlayerEntity)object).abilities.disableDamage) {
                            this.updateWatchedTargetId(n2, 0);
                            continue;
                        }
                        this.launchWitherSkullToEntity(n2 + 1, (LivingEntity)object);
                        this.nextHeadUpdate[n2 - 1] = this.ticksExisted + 40 + this.rand.nextInt(20);
                        this.idleHeadUpdates[n2 - 1] = 0;
                        continue;
                    }
                    this.updateWatchedTargetId(n2, 0);
                    continue;
                }
                object = this.world.getTargettableEntitiesWithinAABB(LivingEntity.class, ENEMY_CONDITION, this, this.getBoundingBox().grow(20.0, 8.0, 20.0));
                for (int i = 0; i < 10 && !object.isEmpty(); ++i) {
                    LivingEntity livingEntity = (LivingEntity)object.get(this.rand.nextInt(object.size()));
                    if (livingEntity != this && livingEntity.isAlive() && this.canEntityBeSeen(livingEntity)) {
                        if (livingEntity instanceof PlayerEntity) {
                            if (((PlayerEntity)livingEntity).abilities.disableDamage) continue block0;
                            this.updateWatchedTargetId(n2, livingEntity.getEntityId());
                            continue block0;
                        }
                        this.updateWatchedTargetId(n2, livingEntity.getEntityId());
                        continue block0;
                    }
                    object.remove(livingEntity);
                }
            }
            if (this.getAttackTarget() != null) {
                this.updateWatchedTargetId(0, this.getAttackTarget().getEntityId());
            } else {
                this.updateWatchedTargetId(0, 0);
            }
            if (this.blockBreakCounter > 0) {
                --this.blockBreakCounter;
                if (this.blockBreakCounter == 0 && this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
                    n2 = MathHelper.floor(this.getPosY());
                    n = MathHelper.floor(this.getPosX());
                    int n4 = MathHelper.floor(this.getPosZ());
                    boolean bl = false;
                    for (int i = -1; i <= 1; ++i) {
                        for (int j = -1; j <= 1; ++j) {
                            for (int k = 0; k <= 3; ++k) {
                                int n5 = n + i;
                                int n6 = n2 + k;
                                int n7 = n4 + j;
                                BlockPos blockPos = new BlockPos(n5, n6, n7);
                                BlockState blockState = this.world.getBlockState(blockPos);
                                if (!WitherEntity.canDestroyBlock(blockState)) continue;
                                bl = this.world.destroyBlock(blockPos, true, this) || bl;
                            }
                        }
                    }
                    if (bl) {
                        this.world.playEvent(null, 1022, this.getPosition(), 0);
                    }
                }
            }
            if (this.ticksExisted % 20 == 0) {
                this.heal(1.0f);
            }
            this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
        }
    }

    public static boolean canDestroyBlock(BlockState blockState) {
        return !blockState.isAir() && !BlockTags.WITHER_IMMUNE.contains(blockState.getBlock());
    }

    public void ignite() {
        this.setInvulTime(220);
        this.setHealth(this.getMaxHealth() / 3.0f);
    }

    @Override
    public void setMotionMultiplier(BlockState blockState, Vector3d vector3d) {
    }

    @Override
    public void addTrackingPlayer(ServerPlayerEntity serverPlayerEntity) {
        super.addTrackingPlayer(serverPlayerEntity);
        this.bossInfo.addPlayer(serverPlayerEntity);
    }

    @Override
    public void removeTrackingPlayer(ServerPlayerEntity serverPlayerEntity) {
        super.removeTrackingPlayer(serverPlayerEntity);
        this.bossInfo.removePlayer(serverPlayerEntity);
    }

    private double getHeadX(int n) {
        if (n <= 0) {
            return this.getPosX();
        }
        float f = (this.renderYawOffset + (float)(180 * (n - 1))) * ((float)Math.PI / 180);
        float f2 = MathHelper.cos(f);
        return this.getPosX() + (double)f2 * 1.3;
    }

    private double getHeadY(int n) {
        return n <= 0 ? this.getPosY() + 3.0 : this.getPosY() + 2.2;
    }

    private double getHeadZ(int n) {
        if (n <= 0) {
            return this.getPosZ();
        }
        float f = (this.renderYawOffset + (float)(180 * (n - 1))) * ((float)Math.PI / 180);
        float f2 = MathHelper.sin(f);
        return this.getPosZ() + (double)f2 * 1.3;
    }

    private float rotlerp(float f, float f2, float f3) {
        float f4 = MathHelper.wrapDegrees(f2 - f);
        if (f4 > f3) {
            f4 = f3;
        }
        if (f4 < -f3) {
            f4 = -f3;
        }
        return f + f4;
    }

    private void launchWitherSkullToEntity(int n, LivingEntity livingEntity) {
        this.launchWitherSkullToCoords(n, livingEntity.getPosX(), livingEntity.getPosY() + (double)livingEntity.getEyeHeight() * 0.5, livingEntity.getPosZ(), n == 0 && this.rand.nextFloat() < 0.001f);
    }

    private void launchWitherSkullToCoords(int n, double d, double d2, double d3, boolean bl) {
        if (!this.isSilent()) {
            this.world.playEvent(null, 1024, this.getPosition(), 0);
        }
        double d4 = this.getHeadX(n);
        double d5 = this.getHeadY(n);
        double d6 = this.getHeadZ(n);
        double d7 = d - d4;
        double d8 = d2 - d5;
        double d9 = d3 - d6;
        WitherSkullEntity witherSkullEntity = new WitherSkullEntity(this.world, this, d7, d8, d9);
        witherSkullEntity.setShooter(this);
        if (bl) {
            witherSkullEntity.setSkullInvulnerable(false);
        }
        witherSkullEntity.setRawPosition(d4, d5, d6);
        this.world.addEntity(witherSkullEntity);
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity livingEntity, float f) {
        this.launchWitherSkullToEntity(0, livingEntity);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        if (damageSource != DamageSource.DROWN && !(damageSource.getTrueSource() instanceof WitherEntity)) {
            Entity entity2;
            if (this.getInvulTime() > 0 && damageSource != DamageSource.OUT_OF_WORLD) {
                return true;
            }
            if (this.isCharged() && (entity2 = damageSource.getImmediateSource()) instanceof AbstractArrowEntity) {
                return true;
            }
            entity2 = damageSource.getTrueSource();
            if (entity2 != null && !(entity2 instanceof PlayerEntity) && entity2 instanceof LivingEntity && ((LivingEntity)entity2).getCreatureAttribute() == this.getCreatureAttribute()) {
                return true;
            }
            if (this.blockBreakCounter <= 0) {
                this.blockBreakCounter = 20;
            }
            int n = 0;
            while (n < this.idleHeadUpdates.length) {
                int n2 = n++;
                this.idleHeadUpdates[n2] = this.idleHeadUpdates[n2] + 3;
            }
            return super.attackEntityFrom(damageSource, f);
        }
        return true;
    }

    @Override
    protected void dropSpecialItems(DamageSource damageSource, int n, boolean bl) {
        super.dropSpecialItems(damageSource, n, bl);
        ItemEntity itemEntity = this.entityDropItem(Items.NETHER_STAR);
        if (itemEntity != null) {
            itemEntity.setNoDespawn();
        }
    }

    @Override
    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.isDespawnPeaceful()) {
            this.remove();
        } else {
            this.idleTime = 0;
        }
    }

    @Override
    public boolean onLivingFall(float f, float f2) {
        return true;
    }

    @Override
    public boolean addPotionEffect(EffectInstance effectInstance) {
        return true;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 300.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.6f).createMutableAttribute(Attributes.FOLLOW_RANGE, 40.0).createMutableAttribute(Attributes.ARMOR, 4.0);
    }

    public float getHeadYRotation(int n) {
        return this.yRotationHeads[n];
    }

    public float getHeadXRotation(int n) {
        return this.xRotationHeads[n];
    }

    public int getInvulTime() {
        return this.dataManager.get(INVULNERABILITY_TIME);
    }

    public void setInvulTime(int n) {
        this.dataManager.set(INVULNERABILITY_TIME, n);
    }

    public int getWatchedTargetId(int n) {
        return this.dataManager.get(HEAD_TARGETS.get(n));
    }

    public void updateWatchedTargetId(int n, int n2) {
        this.dataManager.set(HEAD_TARGETS.get(n), n2);
    }

    @Override
    public boolean isCharged() {
        return this.getHealth() <= this.getMaxHealth() / 2.0f;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    @Override
    protected boolean canBeRidden(Entity entity2) {
        return true;
    }

    @Override
    public boolean isNonBoss() {
        return true;
    }

    @Override
    public boolean isPotionApplicable(EffectInstance effectInstance) {
        return effectInstance.getPotion() == Effects.WITHER ? false : super.isPotionApplicable(effectInstance);
    }

    private static boolean lambda$static$0(LivingEntity livingEntity) {
        return livingEntity.getCreatureAttribute() != CreatureAttribute.UNDEAD && livingEntity.attackable();
    }

    class DoNothingGoal
    extends Goal {
        final WitherEntity this$0;

        public DoNothingGoal(WitherEntity witherEntity) {
            this.this$0 = witherEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
        }

        @Override
        public boolean shouldExecute() {
            return this.this$0.getInvulTime() > 0;
        }
    }
}

