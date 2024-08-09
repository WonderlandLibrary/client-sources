/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveTowardsRaidGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.PatrollerEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.raid.RaidManager;
import net.minecraft.world.server.ServerWorld;

public abstract class AbstractRaiderEntity
extends PatrollerEntity {
    protected static final DataParameter<Boolean> CELEBRATING = EntityDataManager.createKey(AbstractRaiderEntity.class, DataSerializers.BOOLEAN);
    private static final Predicate<ItemEntity> bannerPredicate = AbstractRaiderEntity::lambda$static$0;
    @Nullable
    protected Raid raid;
    private int wave;
    private boolean canJoinRaid;
    private int joinDelay;

    protected AbstractRaiderEntity(EntityType<? extends AbstractRaiderEntity> entityType, World world) {
        super((EntityType<? extends PatrollerEntity>)entityType, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new PromoteLeaderGoal(this, this));
        this.goalSelector.addGoal(3, new MoveTowardsRaidGoal<AbstractRaiderEntity>(this));
        this.goalSelector.addGoal(4, new InvadeHomeGoal(this, 1.05f, 1));
        this.goalSelector.addGoal(5, new CelebrateRaidLossGoal(this, this));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(CELEBRATING, false);
    }

    public abstract void applyWaveBonus(int var1, boolean var2);

    public boolean canJoinRaid() {
        return this.canJoinRaid;
    }

    public void setCanJoinRaid(boolean bl) {
        this.canJoinRaid = bl;
    }

    @Override
    public void livingTick() {
        if (this.world instanceof ServerWorld && this.isAlive()) {
            Raid raid = this.getRaid();
            if (this.canJoinRaid()) {
                if (raid == null) {
                    Raid raid2;
                    if (this.world.getGameTime() % 20L == 0L && (raid2 = ((ServerWorld)this.world).findRaid(this.getPosition())) != null && RaidManager.canJoinRaid(this, raid2)) {
                        raid2.joinRaid(raid2.getGroupsSpawned(), this, null, false);
                    }
                } else {
                    LivingEntity livingEntity = this.getAttackTarget();
                    if (livingEntity != null && (livingEntity.getType() == EntityType.PLAYER || livingEntity.getType() == EntityType.IRON_GOLEM)) {
                        this.idleTime = 0;
                    }
                }
            }
        }
        super.livingTick();
    }

    @Override
    protected void idle() {
        this.idleTime += 2;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        if (this.world instanceof ServerWorld) {
            Entity entity2 = damageSource.getTrueSource();
            Raid raid = this.getRaid();
            if (raid != null) {
                if (this.isLeader()) {
                    raid.removeLeader(this.getWave());
                }
                if (entity2 != null && entity2.getType() == EntityType.PLAYER) {
                    raid.addHero(entity2);
                }
                raid.leaveRaid(this, true);
            }
            if (this.isLeader() && raid == null && ((ServerWorld)this.world).findRaid(this.getPosition()) == null) {
                Object object;
                ItemStack itemStack = this.getItemStackFromSlot(EquipmentSlotType.HEAD);
                PlayerEntity playerEntity = null;
                if (entity2 instanceof PlayerEntity) {
                    playerEntity = (PlayerEntity)entity2;
                } else if (entity2 instanceof WolfEntity) {
                    object = (WolfEntity)entity2;
                    LivingEntity livingEntity = ((TameableEntity)object).getOwner();
                    if (((TameableEntity)object).isTamed() && livingEntity instanceof PlayerEntity) {
                        playerEntity = (PlayerEntity)livingEntity;
                    }
                }
                if (!itemStack.isEmpty() && ItemStack.areItemStacksEqual(itemStack, Raid.createIllagerBanner()) && playerEntity != null) {
                    object = playerEntity.getActivePotionEffect(Effects.BAD_OMEN);
                    int n = 1;
                    if (object != null) {
                        n += ((EffectInstance)object).getAmplifier();
                        playerEntity.removeActivePotionEffect(Effects.BAD_OMEN);
                    } else {
                        --n;
                    }
                    n = MathHelper.clamp(n, 0, 4);
                    EffectInstance effectInstance = new EffectInstance(Effects.BAD_OMEN, 120000, n, false, false, true);
                    if (!this.world.getGameRules().getBoolean(GameRules.DISABLE_RAIDS)) {
                        playerEntity.addPotionEffect(effectInstance);
                    }
                }
            }
        }
        super.onDeath(damageSource);
    }

    @Override
    public boolean notInRaid() {
        return !this.isRaidActive();
    }

    public void setRaid(@Nullable Raid raid) {
        this.raid = raid;
    }

    @Nullable
    public Raid getRaid() {
        return this.raid;
    }

    public boolean isRaidActive() {
        return this.getRaid() != null && this.getRaid().isActive();
    }

    public void setWave(int n) {
        this.wave = n;
    }

    public int getWave() {
        return this.wave;
    }

    public boolean getCelebrating() {
        return this.dataManager.get(CELEBRATING);
    }

    public void setCelebrating(boolean bl) {
        this.dataManager.set(CELEBRATING, bl);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("Wave", this.wave);
        compoundNBT.putBoolean("CanJoinRaid", this.canJoinRaid);
        if (this.raid != null) {
            compoundNBT.putInt("RaidId", this.raid.getId());
        }
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.wave = compoundNBT.getInt("Wave");
        this.canJoinRaid = compoundNBT.getBoolean("CanJoinRaid");
        if (compoundNBT.contains("RaidId", 0)) {
            if (this.world instanceof ServerWorld) {
                this.raid = ((ServerWorld)this.world).getRaids().get(compoundNBT.getInt("RaidId"));
            }
            if (this.raid != null) {
                this.raid.joinRaid(this.wave, this, true);
                if (this.isLeader()) {
                    this.raid.setLeader(this.wave, this);
                }
            }
        }
    }

    @Override
    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        boolean bl;
        ItemStack itemStack = itemEntity.getItem();
        boolean bl2 = bl = this.isRaidActive() && this.getRaid().getLeader(this.getWave()) != null;
        if (this.isRaidActive() && !bl && ItemStack.areItemStacksEqual(itemStack, Raid.createIllagerBanner())) {
            EquipmentSlotType equipmentSlotType = EquipmentSlotType.HEAD;
            ItemStack itemStack2 = this.getItemStackFromSlot(equipmentSlotType);
            double d = this.getDropChance(equipmentSlotType);
            if (!itemStack2.isEmpty() && (double)Math.max(this.rand.nextFloat() - 0.1f, 0.0f) < d) {
                this.entityDropItem(itemStack2);
            }
            this.triggerItemPickupTrigger(itemEntity);
            this.setItemStackToSlot(equipmentSlotType, itemStack);
            this.onItemPickup(itemEntity, itemStack.getCount());
            itemEntity.remove();
            this.getRaid().setLeader(this.getWave(), this);
            this.setLeader(false);
        } else {
            super.updateEquipmentIfNeeded(itemEntity);
        }
    }

    @Override
    public boolean canDespawn(double d) {
        return this.getRaid() == null ? super.canDespawn(d) : false;
    }

    @Override
    public boolean preventDespawn() {
        return super.preventDespawn() || this.getRaid() != null;
    }

    public int getJoinDelay() {
        return this.joinDelay;
    }

    public void setJoinDelay(int n) {
        this.joinDelay = n;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isRaidActive()) {
            this.getRaid().updateBarPercentage();
        }
        return super.attackEntityFrom(damageSource, f);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        this.setCanJoinRaid(this.getType() != EntityType.WITCH || spawnReason != SpawnReason.NATURAL);
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    public abstract SoundEvent getRaidLossSound();

    private static boolean lambda$static$0(ItemEntity itemEntity) {
        return !itemEntity.cannotPickup() && itemEntity.isAlive() && ItemStack.areItemStacksEqual(itemEntity.getItem(), Raid.createIllagerBanner());
    }

    static Random access$000(AbstractRaiderEntity abstractRaiderEntity) {
        return abstractRaiderEntity.rand;
    }

    static float access$100(AbstractRaiderEntity abstractRaiderEntity) {
        return abstractRaiderEntity.getSoundVolume();
    }

    static float access$200(AbstractRaiderEntity abstractRaiderEntity) {
        return abstractRaiderEntity.getSoundPitch();
    }

    static Random access$300(AbstractRaiderEntity abstractRaiderEntity) {
        return abstractRaiderEntity.rand;
    }

    static Random access$400(AbstractRaiderEntity abstractRaiderEntity) {
        return abstractRaiderEntity.rand;
    }

    static Random access$500(AbstractRaiderEntity abstractRaiderEntity) {
        return abstractRaiderEntity.rand;
    }

    public class PromoteLeaderGoal<T extends AbstractRaiderEntity>
    extends Goal {
        private final T raiderEntity;
        final AbstractRaiderEntity this$0;

        public PromoteLeaderGoal(T t) {
            this.this$0 = var1_1;
            this.raiderEntity = t;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            Raid raid = ((AbstractRaiderEntity)this.raiderEntity).getRaid();
            if (((AbstractRaiderEntity)this.raiderEntity).isRaidActive() && !((AbstractRaiderEntity)this.raiderEntity).getRaid().isOver() && ((PatrollerEntity)this.raiderEntity).canBeLeader() && !ItemStack.areItemStacksEqual(((MobEntity)this.raiderEntity).getItemStackFromSlot(EquipmentSlotType.HEAD), Raid.createIllagerBanner())) {
                List<ItemEntity> list;
                AbstractRaiderEntity abstractRaiderEntity = raid.getLeader(((AbstractRaiderEntity)this.raiderEntity).getWave());
                if (!(abstractRaiderEntity != null && abstractRaiderEntity.isAlive() || (list = ((AbstractRaiderEntity)this.raiderEntity).world.getEntitiesWithinAABB(ItemEntity.class, ((Entity)this.raiderEntity).getBoundingBox().grow(16.0, 8.0, 16.0), bannerPredicate)).isEmpty())) {
                    return ((MobEntity)this.raiderEntity).getNavigator().tryMoveToEntityLiving(list.get(0), 1.15f);
                }
                return true;
            }
            return true;
        }

        @Override
        public void tick() {
            List<ItemEntity> list;
            if (((MobEntity)this.raiderEntity).getNavigator().getTargetPos().withinDistance(((Entity)this.raiderEntity).getPositionVec(), 1.414) && !(list = ((AbstractRaiderEntity)this.raiderEntity).world.getEntitiesWithinAABB(ItemEntity.class, ((Entity)this.raiderEntity).getBoundingBox().grow(4.0, 4.0, 4.0), bannerPredicate)).isEmpty()) {
                ((AbstractRaiderEntity)this.raiderEntity).updateEquipmentIfNeeded(list.get(0));
            }
        }
    }

    static class InvadeHomeGoal
    extends Goal {
        private final AbstractRaiderEntity raiderEntity;
        private final double speed;
        private BlockPos blockPosPOI;
        private final List<BlockPos> cachedPointOfIntresste = Lists.newArrayList();
        private final int distance;
        private boolean idling;

        public InvadeHomeGoal(AbstractRaiderEntity abstractRaiderEntity, double d, int n) {
            this.raiderEntity = abstractRaiderEntity;
            this.speed = d;
            this.distance = n;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            this.clearLastCachedPoint();
            return this.isActive() && this.findValidHome() && this.raiderEntity.getAttackTarget() == null;
        }

        private boolean isActive() {
            return this.raiderEntity.isRaidActive() && !this.raiderEntity.getRaid().isOver();
        }

        private boolean findValidHome() {
            ServerWorld serverWorld = (ServerWorld)this.raiderEntity.world;
            BlockPos blockPos = this.raiderEntity.getPosition();
            Optional<BlockPos> optional = serverWorld.getPointOfInterestManager().getRandom(InvadeHomeGoal::lambda$findValidHome$0, this::isValidDoorPosition, PointOfInterestManager.Status.ANY, blockPos, 48, AbstractRaiderEntity.access$500(this.raiderEntity));
            if (!optional.isPresent()) {
                return true;
            }
            this.blockPosPOI = optional.get().toImmutable();
            return false;
        }

        @Override
        public boolean shouldContinueExecuting() {
            if (this.raiderEntity.getNavigator().noPath()) {
                return true;
            }
            return this.raiderEntity.getAttackTarget() == null && !this.blockPosPOI.withinDistance(this.raiderEntity.getPositionVec(), (double)(this.raiderEntity.getWidth() + (float)this.distance)) && !this.idling;
        }

        @Override
        public void resetTask() {
            if (this.blockPosPOI.withinDistance(this.raiderEntity.getPositionVec(), (double)this.distance)) {
                this.cachedPointOfIntresste.add(this.blockPosPOI);
            }
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
            this.raiderEntity.setIdleTime(0);
            this.raiderEntity.getNavigator().tryMoveToXYZ(this.blockPosPOI.getX(), this.blockPosPOI.getY(), this.blockPosPOI.getZ(), this.speed);
            this.idling = false;
        }

        @Override
        public void tick() {
            if (this.raiderEntity.getNavigator().noPath()) {
                Vector3d vector3d = Vector3d.copyCenteredHorizontally(this.blockPosPOI);
                Vector3d vector3d2 = RandomPositionGenerator.findRandomTargetTowardsScaled(this.raiderEntity, 16, 7, vector3d, 0.3141592741012573);
                if (vector3d2 == null) {
                    vector3d2 = RandomPositionGenerator.findRandomTargetBlockTowards(this.raiderEntity, 8, 7, vector3d);
                }
                if (vector3d2 == null) {
                    this.idling = true;
                    return;
                }
                this.raiderEntity.getNavigator().tryMoveToXYZ(vector3d2.x, vector3d2.y, vector3d2.z, this.speed);
            }
        }

        private boolean isValidDoorPosition(BlockPos blockPos) {
            for (BlockPos blockPos2 : this.cachedPointOfIntresste) {
                if (!Objects.equals(blockPos, blockPos2)) continue;
                return true;
            }
            return false;
        }

        private void clearLastCachedPoint() {
            if (this.cachedPointOfIntresste.size() > 2) {
                this.cachedPointOfIntresste.remove(0);
            }
        }

        private static boolean lambda$findValidHome$0(PointOfInterestType pointOfInterestType) {
            return pointOfInterestType == PointOfInterestType.HOME;
        }
    }

    public class CelebrateRaidLossGoal
    extends Goal {
        private final AbstractRaiderEntity raiderEntity;
        final AbstractRaiderEntity this$0;

        CelebrateRaidLossGoal(AbstractRaiderEntity abstractRaiderEntity, AbstractRaiderEntity abstractRaiderEntity2) {
            this.this$0 = abstractRaiderEntity;
            this.raiderEntity = abstractRaiderEntity2;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            Raid raid = this.raiderEntity.getRaid();
            return this.raiderEntity.isAlive() && this.raiderEntity.getAttackTarget() == null && raid != null && raid.isLoss();
        }

        @Override
        public void startExecuting() {
            this.raiderEntity.setCelebrating(false);
            super.startExecuting();
        }

        @Override
        public void resetTask() {
            this.raiderEntity.setCelebrating(true);
            super.resetTask();
        }

        @Override
        public void tick() {
            if (!this.raiderEntity.isSilent() && AbstractRaiderEntity.access$000(this.raiderEntity).nextInt(100) == 0) {
                this.this$0.playSound(this.this$0.getRaidLossSound(), AbstractRaiderEntity.access$100(this.this$0), AbstractRaiderEntity.access$200(this.this$0));
            }
            if (!this.raiderEntity.isPassenger() && AbstractRaiderEntity.access$300(this.raiderEntity).nextInt(50) == 0) {
                this.raiderEntity.getJumpController().setJumping();
            }
            super.tick();
        }
    }

    public class FindTargetGoal
    extends Goal {
        private final AbstractRaiderEntity raiderEntity;
        private final float findTargetRange;
        public final EntityPredicate findTargetPredicate;
        final AbstractRaiderEntity this$0;

        public FindTargetGoal(AbstractRaiderEntity abstractRaiderEntity, AbstractIllagerEntity abstractIllagerEntity, float f) {
            this.this$0 = abstractRaiderEntity;
            this.findTargetPredicate = new EntityPredicate().setDistance(8.0).setSkipAttackChecks().allowInvulnerable().allowFriendlyFire().setLineOfSiteRequired().setUseInvisibilityCheck();
            this.raiderEntity = abstractIllagerEntity;
            this.findTargetRange = f * f;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean shouldExecute() {
            LivingEntity livingEntity = this.raiderEntity.getRevengeTarget();
            return this.raiderEntity.getRaid() == null && this.raiderEntity.isPatrolling() && this.raiderEntity.getAttackTarget() != null && !this.raiderEntity.isAggressive() && (livingEntity == null || livingEntity.getType() != EntityType.PLAYER);
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
            this.raiderEntity.getNavigator().clearPath();
            for (AbstractRaiderEntity abstractRaiderEntity : this.raiderEntity.world.getTargettableEntitiesWithinAABB(AbstractRaiderEntity.class, this.findTargetPredicate, this.raiderEntity, this.raiderEntity.getBoundingBox().grow(8.0, 8.0, 8.0))) {
                abstractRaiderEntity.setAttackTarget(this.raiderEntity.getAttackTarget());
            }
        }

        @Override
        public void resetTask() {
            super.resetTask();
            LivingEntity livingEntity = this.raiderEntity.getAttackTarget();
            if (livingEntity != null) {
                for (AbstractRaiderEntity abstractRaiderEntity : this.raiderEntity.world.getTargettableEntitiesWithinAABB(AbstractRaiderEntity.class, this.findTargetPredicate, this.raiderEntity, this.raiderEntity.getBoundingBox().grow(8.0, 8.0, 8.0))) {
                    abstractRaiderEntity.setAttackTarget(livingEntity);
                    abstractRaiderEntity.setAggroed(false);
                }
                this.raiderEntity.setAggroed(false);
            }
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.raiderEntity.getAttackTarget();
            if (livingEntity != null) {
                if (this.raiderEntity.getDistanceSq(livingEntity) > (double)this.findTargetRange) {
                    this.raiderEntity.getLookController().setLookPositionWithEntity(livingEntity, 30.0f, 30.0f);
                    if (AbstractRaiderEntity.access$400(this.raiderEntity).nextInt(50) == 0) {
                        this.raiderEntity.playAmbientSound();
                    }
                } else {
                    this.raiderEntity.setAggroed(false);
                }
                super.tick();
            }
        }
    }
}

