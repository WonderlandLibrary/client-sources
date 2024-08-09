/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageAtNightGoal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FoxEntity
extends AnimalEntity {
    private static final DataParameter<Integer> FOX_TYPE = EntityDataManager.createKey(FoxEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Byte> FOX_FLAGS = EntityDataManager.createKey(FoxEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Optional<UUID>> TRUSTED_UUID_SECONDARY = EntityDataManager.createKey(FoxEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private static final DataParameter<Optional<UUID>> TRUSTED_UUID_MAIN = EntityDataManager.createKey(FoxEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private static final Predicate<ItemEntity> TRUSTED_TARGET_SELECTOR = FoxEntity::lambda$static$0;
    private static final Predicate<Entity> STALKABLE_PREY = FoxEntity::lambda$static$1;
    private static final Predicate<Entity> IS_PREY = FoxEntity::lambda$static$2;
    private static final Predicate<Entity> SHOULD_AVOID = FoxEntity::lambda$static$3;
    private Goal attackAnimals;
    private Goal attackTurtles;
    private Goal attackFish;
    private float interestedAngle;
    private float interestedAngleO;
    private float crouchAmount;
    private float crouchAmountO;
    private int eatTicks;

    public FoxEntity(EntityType<? extends FoxEntity> entityType, World world) {
        super((EntityType<? extends AnimalEntity>)entityType, world);
        this.lookController = new LookHelperController(this);
        this.moveController = new MoveHelperController(this);
        this.setPathPriority(PathNodeType.DANGER_OTHER, 0.0f);
        this.setPathPriority(PathNodeType.DAMAGE_OTHER, 0.0f);
        this.setCanPickUpLoot(false);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(TRUSTED_UUID_SECONDARY, Optional.empty());
        this.dataManager.register(TRUSTED_UUID_MAIN, Optional.empty());
        this.dataManager.register(FOX_TYPE, 0);
        this.dataManager.register(FOX_FLAGS, (byte)0);
    }

    @Override
    protected void registerGoals() {
        this.attackAnimals = new NearestAttackableTargetGoal<AnimalEntity>(this, AnimalEntity.class, 10, false, false, FoxEntity::lambda$registerGoals$4);
        this.attackTurtles = new NearestAttackableTargetGoal<TurtleEntity>(this, TurtleEntity.class, 10, false, false, TurtleEntity.TARGET_DRY_BABY);
        this.attackFish = new NearestAttackableTargetGoal<AbstractFishEntity>(this, AbstractFishEntity.class, 20, false, false, FoxEntity::lambda$registerGoals$5);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new JumpGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 2.2));
        this.goalSelector.addGoal(3, new MateGoal(this, 1.0));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<PlayerEntity>(this, PlayerEntity.class, 16.0f, 1.6, 1.4, this::lambda$registerGoals$6));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<WolfEntity>(this, WolfEntity.class, 8.0f, 1.6, 1.4, this::lambda$registerGoals$7));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<PolarBearEntity>(this, PolarBearEntity.class, 8.0f, 1.6, 1.4, this::lambda$registerGoals$8));
        this.goalSelector.addGoal(5, new FollowTargetGoal(this));
        this.goalSelector.addGoal(6, new PounceGoal(this));
        this.goalSelector.addGoal(6, new FindShelterGoal(this, 1.25));
        this.goalSelector.addGoal(7, new BiteGoal(this, (double)1.2f, true));
        this.goalSelector.addGoal(7, new SleepGoal(this));
        this.goalSelector.addGoal(8, new FollowGoal(this, this, 1.25));
        this.goalSelector.addGoal(9, new StrollGoal(this, 32, 200));
        this.goalSelector.addGoal(10, new EatBerriesGoal(this, (double)1.2f, 12, 2));
        this.goalSelector.addGoal(10, new LeapAtTargetGoal(this, 0.4f));
        this.goalSelector.addGoal(11, new WaterAvoidingRandomWalkingGoal(this, 1.0));
        this.goalSelector.addGoal(11, new FindItemsGoal(this));
        this.goalSelector.addGoal(12, new WatchGoal(this, this, PlayerEntity.class, 24.0f));
        this.goalSelector.addGoal(13, new SitAndLookGoal(this));
        this.targetSelector.addGoal(3, new RevengeGoal(this, LivingEntity.class, false, false, this::lambda$registerGoals$9));
    }

    @Override
    public SoundEvent getEatSound(ItemStack itemStack) {
        return SoundEvents.ENTITY_FOX_EAT;
    }

    @Override
    public void livingTick() {
        if (!this.world.isRemote && this.isAlive() && this.isServerWorld()) {
            Object object;
            ++this.eatTicks;
            ItemStack itemStack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
            if (this.canEatItem(itemStack)) {
                if (this.eatTicks > 600) {
                    object = itemStack.onItemUseFinish(this.world, this);
                    if (!((ItemStack)object).isEmpty()) {
                        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, (ItemStack)object);
                    }
                    this.eatTicks = 0;
                } else if (this.eatTicks > 560 && this.rand.nextFloat() < 0.1f) {
                    this.playSound(this.getEatSound(itemStack), 1.0f, 1.0f);
                    this.world.setEntityState(this, (byte)45);
                }
            }
            if ((object = this.getAttackTarget()) == null || !((LivingEntity)object).isAlive()) {
                this.setCrouching(true);
                this.func_213502_u(true);
            }
        }
        if (this.isSleeping() || this.isMovementBlocked()) {
            this.isJumping = false;
            this.moveStrafing = 0.0f;
            this.moveForward = 0.0f;
        }
        super.livingTick();
        if (this.isFoxAggroed() && this.rand.nextFloat() < 0.05f) {
            this.playSound(SoundEvents.ENTITY_FOX_AGGRO, 1.0f, 1.0f);
        }
    }

    @Override
    protected boolean isMovementBlocked() {
        return this.getShouldBeDead();
    }

    private boolean canEatItem(ItemStack itemStack) {
        return itemStack.getItem().isFood() && this.getAttackTarget() == null && this.onGround && !this.isSleeping();
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
        if (this.rand.nextFloat() < 0.2f) {
            float f = this.rand.nextFloat();
            ItemStack itemStack = f < 0.05f ? new ItemStack(Items.EMERALD) : (f < 0.2f ? new ItemStack(Items.EGG) : (f < 0.4f ? (this.rand.nextBoolean() ? new ItemStack(Items.RABBIT_FOOT) : new ItemStack(Items.RABBIT_HIDE)) : (f < 0.6f ? new ItemStack(Items.WHEAT) : (f < 0.8f ? new ItemStack(Items.LEATHER) : new ItemStack(Items.FEATHER)))));
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemStack);
        }
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 45) {
            ItemStack itemStack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
            if (!itemStack.isEmpty()) {
                for (int i = 0; i < 8; ++i) {
                    Vector3d vector3d = new Vector3d(((double)this.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0).rotatePitch(-this.rotationPitch * ((float)Math.PI / 180)).rotateYaw(-this.rotationYaw * ((float)Math.PI / 180));
                    this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, itemStack), this.getPosX() + this.getLookVec().x / 2.0, this.getPosY(), this.getPosZ() + this.getLookVec().z / 2.0, vector3d.x, vector3d.y + 0.05, vector3d.z);
                }
            }
        } else {
            super.handleStatusUpdate(by);
        }
    }

    public static AttributeModifierMap.MutableAttribute func_234192_eI_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3f).createMutableAttribute(Attributes.MAX_HEALTH, 10.0).createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0).createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0);
    }

    @Override
    public FoxEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        FoxEntity foxEntity = EntityType.FOX.create(serverWorld);
        foxEntity.setVariantType(this.rand.nextBoolean() ? this.getVariantType() : ((FoxEntity)ageableEntity).getVariantType());
        return foxEntity;
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        Optional<RegistryKey<Biome>> optional = iServerWorld.func_242406_i(this.getPosition());
        Type type = Type.func_242325_a(optional);
        boolean bl = false;
        if (iLivingEntityData instanceof FoxData) {
            type = ((FoxData)iLivingEntityData).field_220366_a;
            if (((FoxData)iLivingEntityData).getIndexInGroup() >= 2) {
                bl = true;
            }
        } else {
            iLivingEntityData = new FoxData(type);
        }
        this.setVariantType(type);
        if (bl) {
            this.setGrowingAge(-24000);
        }
        if (iServerWorld instanceof ServerWorld) {
            this.setAttackGoals();
        }
        this.setEquipmentBasedOnDifficulty(difficultyInstance);
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    private void setAttackGoals() {
        if (this.getVariantType() == Type.RED) {
            this.targetSelector.addGoal(4, this.attackAnimals);
            this.targetSelector.addGoal(4, this.attackTurtles);
            this.targetSelector.addGoal(6, this.attackFish);
        } else {
            this.targetSelector.addGoal(4, this.attackFish);
            this.targetSelector.addGoal(6, this.attackAnimals);
            this.targetSelector.addGoal(6, this.attackTurtles);
        }
    }

    @Override
    protected void consumeItemFromStack(PlayerEntity playerEntity, ItemStack itemStack) {
        if (this.isBreedingItem(itemStack)) {
            this.playSound(this.getEatSound(itemStack), 1.0f, 1.0f);
        }
        super.consumeItemFromStack(playerEntity, itemStack);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return this.isChild() ? entitySize.height * 0.85f : 0.4f;
    }

    public Type getVariantType() {
        return Type.getTypeByIndex(this.dataManager.get(FOX_TYPE));
    }

    private void setVariantType(Type type) {
        this.dataManager.set(FOX_TYPE, type.getIndex());
    }

    private List<UUID> getTrustedUUIDs() {
        ArrayList<UUID> arrayList = Lists.newArrayList();
        arrayList.add(this.dataManager.get(TRUSTED_UUID_SECONDARY).orElse(null));
        arrayList.add(this.dataManager.get(TRUSTED_UUID_MAIN).orElse(null));
        return arrayList;
    }

    private void addTrustedUUID(@Nullable UUID uUID) {
        if (this.dataManager.get(TRUSTED_UUID_SECONDARY).isPresent()) {
            this.dataManager.set(TRUSTED_UUID_MAIN, Optional.ofNullable(uUID));
        } else {
            this.dataManager.set(TRUSTED_UUID_SECONDARY, Optional.ofNullable(uUID));
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        List<UUID> list = this.getTrustedUUIDs();
        ListNBT listNBT = new ListNBT();
        for (UUID uUID : list) {
            if (uUID == null) continue;
            listNBT.add(NBTUtil.func_240626_a_(uUID));
        }
        compoundNBT.put("Trusted", listNBT);
        compoundNBT.putBoolean("Sleeping", this.isSleeping());
        compoundNBT.putString("Type", this.getVariantType().getName());
        compoundNBT.putBoolean("Sitting", this.isSitting());
        compoundNBT.putBoolean("Crouching", this.isCrouching());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        ListNBT listNBT = compoundNBT.getList("Trusted", 11);
        for (int i = 0; i < listNBT.size(); ++i) {
            this.addTrustedUUID(NBTUtil.readUniqueId(listNBT.get(i)));
        }
        this.setSleeping(compoundNBT.getBoolean("Sleeping"));
        this.setVariantType(Type.getTypeByName(compoundNBT.getString("Type")));
        this.setSitting(compoundNBT.getBoolean("Sitting"));
        this.setCrouching(compoundNBT.getBoolean("Crouching"));
        if (this.world instanceof ServerWorld) {
            this.setAttackGoals();
        }
    }

    public boolean isSitting() {
        return this.getFoxFlag(0);
    }

    public void setSitting(boolean bl) {
        this.setFoxFlag(1, bl);
    }

    public boolean isStuck() {
        return this.getFoxFlag(1);
    }

    private void setStuck(boolean bl) {
        this.setFoxFlag(64, bl);
    }

    private boolean isFoxAggroed() {
        return this.getFoxFlag(1);
    }

    private void setFoxAggroed(boolean bl) {
        this.setFoxFlag(128, bl);
    }

    @Override
    public boolean isSleeping() {
        return this.getFoxFlag(1);
    }

    private void setSleeping(boolean bl) {
        this.setFoxFlag(32, bl);
    }

    private void setFoxFlag(int n, boolean bl) {
        if (bl) {
            this.dataManager.set(FOX_FLAGS, (byte)(this.dataManager.get(FOX_FLAGS) | n));
        } else {
            this.dataManager.set(FOX_FLAGS, (byte)(this.dataManager.get(FOX_FLAGS) & ~n));
        }
    }

    private boolean getFoxFlag(int n) {
        return (this.dataManager.get(FOX_FLAGS) & n) != 0;
    }

    @Override
    public boolean canPickUpItem(ItemStack itemStack) {
        EquipmentSlotType equipmentSlotType = MobEntity.getSlotForItemStack(itemStack);
        if (!this.getItemStackFromSlot(equipmentSlotType).isEmpty()) {
            return true;
        }
        return equipmentSlotType == EquipmentSlotType.MAINHAND && super.canPickUpItem(itemStack);
    }

    @Override
    public boolean canEquipItem(ItemStack itemStack) {
        Item item = itemStack.getItem();
        ItemStack itemStack2 = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
        return itemStack2.isEmpty() || this.eatTicks > 0 && item.isFood() && !itemStack2.getItem().isFood();
    }

    private void spitOutItem(ItemStack itemStack) {
        if (!itemStack.isEmpty() && !this.world.isRemote) {
            ItemEntity itemEntity = new ItemEntity(this.world, this.getPosX() + this.getLookVec().x, this.getPosY() + 1.0, this.getPosZ() + this.getLookVec().z, itemStack);
            itemEntity.setPickupDelay(40);
            itemEntity.setThrowerId(this.getUniqueID());
            this.playSound(SoundEvents.ENTITY_FOX_SPIT, 1.0f, 1.0f);
            this.world.addEntity(itemEntity);
        }
    }

    private void spawnItem(ItemStack itemStack) {
        ItemEntity itemEntity = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), itemStack);
        this.world.addEntity(itemEntity);
    }

    @Override
    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getItem();
        if (this.canEquipItem(itemStack)) {
            int n = itemStack.getCount();
            if (n > 1) {
                this.spawnItem(itemStack.split(n - 1));
            }
            this.spitOutItem(this.getItemStackFromSlot(EquipmentSlotType.MAINHAND));
            this.triggerItemPickupTrigger(itemEntity);
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemStack.split(1));
            this.inventoryHandsDropChances[EquipmentSlotType.MAINHAND.getIndex()] = 2.0f;
            this.onItemPickup(itemEntity, itemStack.getCount());
            itemEntity.remove();
            this.eatTicks = 0;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isServerWorld()) {
            boolean bl = this.isInWater();
            if (bl || this.getAttackTarget() != null || this.world.isThundering()) {
                this.func_213454_em();
            }
            if (bl || this.isSleeping()) {
                this.setSitting(true);
            }
            if (this.isStuck() && this.world.rand.nextFloat() < 0.2f) {
                BlockPos blockPos = this.getPosition();
                BlockState blockState = this.world.getBlockState(blockPos);
                this.world.playEvent(2001, blockPos, Block.getStateId(blockState));
            }
        }
        this.interestedAngleO = this.interestedAngle;
        this.interestedAngle = this.func_213467_eg() ? (this.interestedAngle += (1.0f - this.interestedAngle) * 0.4f) : (this.interestedAngle += (0.0f - this.interestedAngle) * 0.4f);
        this.crouchAmountO = this.crouchAmount;
        if (this.isCrouching()) {
            this.crouchAmount += 0.2f;
            if (this.crouchAmount > 3.0f) {
                this.crouchAmount = 3.0f;
            }
        } else {
            this.crouchAmount = 0.0f;
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack itemStack) {
        return itemStack.getItem() == Items.SWEET_BERRIES;
    }

    @Override
    protected void onChildSpawnFromEgg(PlayerEntity playerEntity, MobEntity mobEntity) {
        ((FoxEntity)mobEntity).addTrustedUUID(playerEntity.getUniqueID());
    }

    public boolean func_213480_dY() {
        return this.getFoxFlag(1);
    }

    public void func_213461_s(boolean bl) {
        this.setFoxFlag(16, bl);
    }

    public boolean func_213490_ee() {
        return this.crouchAmount == 3.0f;
    }

    public void setCrouching(boolean bl) {
        this.setFoxFlag(4, bl);
    }

    @Override
    public boolean isCrouching() {
        return this.getFoxFlag(1);
    }

    public void func_213502_u(boolean bl) {
        this.setFoxFlag(8, bl);
    }

    public boolean func_213467_eg() {
        return this.getFoxFlag(1);
    }

    public float func_213475_v(float f) {
        return MathHelper.lerp(f, this.interestedAngleO, this.interestedAngle) * 0.11f * (float)Math.PI;
    }

    public float func_213503_w(float f) {
        return MathHelper.lerp(f, this.crouchAmountO, this.crouchAmount);
    }

    @Override
    public void setAttackTarget(@Nullable LivingEntity livingEntity) {
        if (this.isFoxAggroed() && livingEntity == null) {
            this.setFoxAggroed(true);
        }
        super.setAttackTarget(livingEntity);
    }

    @Override
    protected int calculateFallDamage(float f, float f2) {
        return MathHelper.ceil((f - 5.0f) * f2);
    }

    private void func_213454_em() {
        this.setSleeping(true);
    }

    private void func_213499_en() {
        this.func_213502_u(true);
        this.setCrouching(true);
        this.setSitting(true);
        this.setSleeping(true);
        this.setFoxAggroed(true);
        this.setStuck(true);
    }

    private boolean func_213478_eo() {
        return !this.isSleeping() && !this.isSitting() && !this.isStuck();
    }

    @Override
    public void playAmbientSound() {
        SoundEvent soundEvent = this.getAmbientSound();
        if (soundEvent == SoundEvents.ENTITY_FOX_SCREECH) {
            this.playSound(soundEvent, 2.0f, this.getSoundPitch());
        } else {
            super.playAmbientSound();
        }
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        List<Entity> list;
        if (this.isSleeping()) {
            return SoundEvents.ENTITY_FOX_SLEEP;
        }
        if (!this.world.isDaytime() && this.rand.nextFloat() < 0.1f && (list = this.world.getEntitiesWithinAABB(PlayerEntity.class, this.getBoundingBox().grow(16.0, 16.0, 16.0), EntityPredicates.NOT_SPECTATING)).isEmpty()) {
            return SoundEvents.ENTITY_FOX_SCREECH;
        }
        return SoundEvents.ENTITY_FOX_AMBIENT;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_FOX_HURT;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_FOX_DEATH;
    }

    private boolean isTrustedUUID(UUID uUID) {
        return this.getTrustedUUIDs().contains(uUID);
    }

    @Override
    protected void spawnDrops(DamageSource damageSource) {
        ItemStack itemStack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
        if (!itemStack.isEmpty()) {
            this.entityDropItem(itemStack);
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
        }
        super.spawnDrops(damageSource);
    }

    public static boolean func_213481_a(FoxEntity foxEntity, LivingEntity livingEntity) {
        double d = livingEntity.getPosZ() - foxEntity.getPosZ();
        double d2 = livingEntity.getPosX() - foxEntity.getPosX();
        double d3 = d / d2;
        int n = 6;
        for (int i = 0; i < 6; ++i) {
            double d4 = d3 == 0.0 ? 0.0 : d * (double)((float)i / 6.0f);
            double d5 = d3 == 0.0 ? d2 * (double)((float)i / 6.0f) : d4 / d3;
            for (int j = 1; j < 4; ++j) {
                if (foxEntity.world.getBlockState(new BlockPos(foxEntity.getPosX() + d5, foxEntity.getPosY() + (double)j, foxEntity.getPosZ() + d4)).getMaterial().isReplaceable()) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public Vector3d func_241205_ce_() {
        return new Vector3d(0.0, 0.55f * this.getEyeHeight(), this.getWidth() * 0.4f);
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return this.func_241840_a(serverWorld, ageableEntity);
    }

    private boolean lambda$registerGoals$9(LivingEntity livingEntity) {
        return STALKABLE_PREY.test(livingEntity) && !this.isTrustedUUID(livingEntity.getUniqueID());
    }

    private boolean lambda$registerGoals$8(LivingEntity livingEntity) {
        return !this.isFoxAggroed();
    }

    private boolean lambda$registerGoals$7(LivingEntity livingEntity) {
        return !((WolfEntity)livingEntity).isTamed() && !this.isFoxAggroed();
    }

    private boolean lambda$registerGoals$6(LivingEntity livingEntity) {
        return SHOULD_AVOID.test(livingEntity) && !this.isTrustedUUID(livingEntity.getUniqueID()) && !this.isFoxAggroed();
    }

    private static boolean lambda$registerGoals$5(LivingEntity livingEntity) {
        return livingEntity instanceof AbstractGroupFishEntity;
    }

    private static boolean lambda$registerGoals$4(LivingEntity livingEntity) {
        return livingEntity instanceof ChickenEntity || livingEntity instanceof RabbitEntity;
    }

    private static boolean lambda$static$3(Entity entity2) {
        return !entity2.isDiscrete() && EntityPredicates.CAN_AI_TARGET.test(entity2);
    }

    private static boolean lambda$static$2(Entity entity2) {
        return entity2 instanceof ChickenEntity || entity2 instanceof RabbitEntity;
    }

    private static boolean lambda$static$1(Entity entity2) {
        if (!(entity2 instanceof LivingEntity)) {
            return true;
        }
        LivingEntity livingEntity = (LivingEntity)entity2;
        return livingEntity.getLastAttackedEntity() != null && livingEntity.getLastAttackedEntityTime() < livingEntity.ticksExisted + 600;
    }

    private static boolean lambda$static$0(ItemEntity itemEntity) {
        return !itemEntity.cannotPickup() && itemEntity.isAlive();
    }

    static Random access$000(FoxEntity foxEntity) {
        return foxEntity.rand;
    }

    static boolean access$100(FoxEntity foxEntity) {
        return foxEntity.isJumping;
    }

    static boolean access$200(FoxEntity foxEntity) {
        return foxEntity.onGround;
    }

    static boolean access$300(FoxEntity foxEntity) {
        return foxEntity.onGround;
    }

    static Random access$400(FoxEntity foxEntity) {
        return foxEntity.rand;
    }

    static Random access$500(FoxEntity foxEntity) {
        return foxEntity.rand;
    }

    public class LookHelperController
    extends LookController {
        final FoxEntity this$0;

        public LookHelperController(FoxEntity foxEntity) {
            this.this$0 = foxEntity;
            super(foxEntity);
        }

        @Override
        public void tick() {
            if (!this.this$0.isSleeping()) {
                super.tick();
            }
        }

        @Override
        protected boolean shouldResetPitch() {
            return !this.this$0.func_213480_dY() && !this.this$0.isCrouching() && !this.this$0.func_213467_eg() & !this.this$0.isStuck();
        }
    }

    class MoveHelperController
    extends MovementController {
        final FoxEntity this$0;

        public MoveHelperController(FoxEntity foxEntity) {
            this.this$0 = foxEntity;
            super(foxEntity);
        }

        @Override
        public void tick() {
            if (this.this$0.func_213478_eo()) {
                super.tick();
            }
        }
    }

    class SwimGoal
    extends net.minecraft.entity.ai.goal.SwimGoal {
        final FoxEntity this$0;

        public SwimGoal(FoxEntity foxEntity) {
            this.this$0 = foxEntity;
            super(foxEntity);
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
            this.this$0.func_213499_en();
        }

        @Override
        public boolean shouldExecute() {
            return this.this$0.isInWater() && this.this$0.func_233571_b_(FluidTags.WATER) > 0.25 || this.this$0.isInLava();
        }
    }

    class JumpGoal
    extends Goal {
        int delay;
        final FoxEntity this$0;

        public JumpGoal(FoxEntity foxEntity) {
            this.this$0 = foxEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            return this.this$0.isStuck();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.shouldExecute() && this.delay > 0;
        }

        @Override
        public void startExecuting() {
            this.delay = 40;
        }

        @Override
        public void resetTask() {
            this.this$0.setStuck(true);
        }

        @Override
        public void tick() {
            --this.delay;
        }
    }

    class PanicGoal
    extends net.minecraft.entity.ai.goal.PanicGoal {
        final FoxEntity this$0;

        public PanicGoal(FoxEntity foxEntity, double d) {
            this.this$0 = foxEntity;
            super(foxEntity, d);
        }

        @Override
        public boolean shouldExecute() {
            return !this.this$0.isFoxAggroed() && super.shouldExecute();
        }
    }

    class MateGoal
    extends BreedGoal {
        final FoxEntity this$0;

        public MateGoal(FoxEntity foxEntity, double d) {
            this.this$0 = foxEntity;
            super(foxEntity, d);
        }

        @Override
        public void startExecuting() {
            ((FoxEntity)this.animal).func_213499_en();
            ((FoxEntity)this.targetMate).func_213499_en();
            super.startExecuting();
        }

        @Override
        protected void spawnBaby() {
            ServerWorld serverWorld = (ServerWorld)this.world;
            FoxEntity foxEntity = (FoxEntity)this.animal.func_241840_a(serverWorld, this.targetMate);
            if (foxEntity != null) {
                ServerPlayerEntity serverPlayerEntity = this.animal.getLoveCause();
                ServerPlayerEntity serverPlayerEntity2 = this.targetMate.getLoveCause();
                ServerPlayerEntity serverPlayerEntity3 = serverPlayerEntity;
                if (serverPlayerEntity != null) {
                    foxEntity.addTrustedUUID(serverPlayerEntity.getUniqueID());
                } else {
                    serverPlayerEntity3 = serverPlayerEntity2;
                }
                if (serverPlayerEntity2 != null && serverPlayerEntity != serverPlayerEntity2) {
                    foxEntity.addTrustedUUID(serverPlayerEntity2.getUniqueID());
                }
                if (serverPlayerEntity3 != null) {
                    serverPlayerEntity3.addStat(Stats.ANIMALS_BRED);
                    CriteriaTriggers.BRED_ANIMALS.trigger(serverPlayerEntity3, this.animal, this.targetMate, foxEntity);
                }
                this.animal.setGrowingAge(6000);
                this.targetMate.setGrowingAge(6000);
                this.animal.resetInLove();
                this.targetMate.resetInLove();
                foxEntity.setGrowingAge(-24000);
                foxEntity.setLocationAndAngles(this.animal.getPosX(), this.animal.getPosY(), this.animal.getPosZ(), 0.0f, 0.0f);
                serverWorld.func_242417_l(foxEntity);
                this.world.setEntityState(this.animal, (byte)18);
                if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                    this.world.addEntity(new ExperienceOrbEntity(this.world, this.animal.getPosX(), this.animal.getPosY(), this.animal.getPosZ(), this.animal.getRNG().nextInt(7) + 1));
                }
            }
        }
    }

    class FollowTargetGoal
    extends Goal {
        final FoxEntity this$0;

        public FollowTargetGoal(FoxEntity foxEntity) {
            this.this$0 = foxEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean shouldExecute() {
            if (this.this$0.isSleeping()) {
                return true;
            }
            LivingEntity livingEntity = this.this$0.getAttackTarget();
            return livingEntity != null && livingEntity.isAlive() && IS_PREY.test(livingEntity) && this.this$0.getDistanceSq(livingEntity) > 36.0 && !this.this$0.isCrouching() && !this.this$0.func_213467_eg() && !FoxEntity.access$100(this.this$0);
        }

        @Override
        public void startExecuting() {
            this.this$0.setSitting(true);
            this.this$0.setStuck(true);
        }

        @Override
        public void resetTask() {
            LivingEntity livingEntity = this.this$0.getAttackTarget();
            if (livingEntity != null && FoxEntity.func_213481_a(this.this$0, livingEntity)) {
                this.this$0.func_213502_u(false);
                this.this$0.setCrouching(false);
                this.this$0.getNavigator().clearPath();
                this.this$0.getLookController().setLookPositionWithEntity(livingEntity, this.this$0.getHorizontalFaceSpeed(), this.this$0.getVerticalFaceSpeed());
            } else {
                this.this$0.func_213502_u(true);
                this.this$0.setCrouching(true);
            }
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.this$0.getAttackTarget();
            this.this$0.getLookController().setLookPositionWithEntity(livingEntity, this.this$0.getHorizontalFaceSpeed(), this.this$0.getVerticalFaceSpeed());
            if (this.this$0.getDistanceSq(livingEntity) <= 36.0) {
                this.this$0.func_213502_u(false);
                this.this$0.setCrouching(false);
                this.this$0.getNavigator().clearPath();
            } else {
                this.this$0.getNavigator().tryMoveToEntityLiving(livingEntity, 1.5);
            }
        }
    }

    public class PounceGoal
    extends net.minecraft.entity.ai.goal.JumpGoal {
        final FoxEntity this$0;

        public PounceGoal(FoxEntity foxEntity) {
            this.this$0 = foxEntity;
        }

        @Override
        public boolean shouldExecute() {
            if (!this.this$0.func_213490_ee()) {
                return true;
            }
            LivingEntity livingEntity = this.this$0.getAttackTarget();
            if (livingEntity != null && livingEntity.isAlive()) {
                if (livingEntity.getAdjustedHorizontalFacing() != livingEntity.getHorizontalFacing()) {
                    return true;
                }
                boolean bl = FoxEntity.func_213481_a(this.this$0, livingEntity);
                if (!bl) {
                    this.this$0.getNavigator().getPathToEntity(livingEntity, 0);
                    this.this$0.setCrouching(true);
                    this.this$0.func_213502_u(true);
                }
                return bl;
            }
            return true;
        }

        @Override
        public boolean shouldContinueExecuting() {
            LivingEntity livingEntity = this.this$0.getAttackTarget();
            if (livingEntity != null && livingEntity.isAlive()) {
                double d = this.this$0.getMotion().y;
                return !(d * d < (double)0.05f && Math.abs(this.this$0.rotationPitch) < 15.0f && FoxEntity.access$200(this.this$0) || this.this$0.isStuck());
            }
            return true;
        }

        @Override
        public boolean isPreemptible() {
            return true;
        }

        @Override
        public void startExecuting() {
            this.this$0.setJumping(false);
            this.this$0.func_213461_s(false);
            this.this$0.func_213502_u(true);
            LivingEntity livingEntity = this.this$0.getAttackTarget();
            this.this$0.getLookController().setLookPositionWithEntity(livingEntity, 60.0f, 30.0f);
            Vector3d vector3d = new Vector3d(livingEntity.getPosX() - this.this$0.getPosX(), livingEntity.getPosY() - this.this$0.getPosY(), livingEntity.getPosZ() - this.this$0.getPosZ()).normalize();
            this.this$0.setMotion(this.this$0.getMotion().add(vector3d.x * 0.8, 0.9, vector3d.z * 0.8));
            this.this$0.getNavigator().clearPath();
        }

        @Override
        public void resetTask() {
            this.this$0.setCrouching(true);
            this.this$0.crouchAmount = 0.0f;
            this.this$0.crouchAmountO = 0.0f;
            this.this$0.func_213502_u(true);
            this.this$0.func_213461_s(true);
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.this$0.getAttackTarget();
            if (livingEntity != null) {
                this.this$0.getLookController().setLookPositionWithEntity(livingEntity, 60.0f, 30.0f);
            }
            if (!this.this$0.isStuck()) {
                Vector3d vector3d = this.this$0.getMotion();
                if (vector3d.y * vector3d.y < (double)0.03f && this.this$0.rotationPitch != 0.0f) {
                    this.this$0.rotationPitch = MathHelper.rotLerp(this.this$0.rotationPitch, 0.0f, 0.2f);
                } else {
                    double d = Math.sqrt(Entity.horizontalMag(vector3d));
                    double d2 = Math.signum(-vector3d.y) * Math.acos(d / vector3d.length()) * 57.2957763671875;
                    this.this$0.rotationPitch = (float)d2;
                }
            }
            if (livingEntity != null && this.this$0.getDistance(livingEntity) <= 2.0f) {
                this.this$0.attackEntityAsMob(livingEntity);
            } else if (this.this$0.rotationPitch > 0.0f && FoxEntity.access$300(this.this$0) && (float)this.this$0.getMotion().y != 0.0f && this.this$0.world.getBlockState(this.this$0.getPosition()).isIn(Blocks.SNOW)) {
                this.this$0.rotationPitch = 60.0f;
                this.this$0.setAttackTarget(null);
                this.this$0.setStuck(false);
            }
        }
    }

    class FindShelterGoal
    extends FleeSunGoal {
        private int cooldown;
        final FoxEntity this$0;

        public FindShelterGoal(FoxEntity foxEntity, double d) {
            this.this$0 = foxEntity;
            super(foxEntity, d);
            this.cooldown = 100;
        }

        @Override
        public boolean shouldExecute() {
            if (!this.this$0.isSleeping() && this.creature.getAttackTarget() == null) {
                if (this.this$0.world.isThundering()) {
                    return false;
                }
                if (this.cooldown > 0) {
                    --this.cooldown;
                    return true;
                }
                this.cooldown = 100;
                BlockPos blockPos = this.creature.getPosition();
                return this.this$0.world.isDaytime() && this.this$0.world.canSeeSky(blockPos) && !((ServerWorld)this.this$0.world).isVillage(blockPos) && this.isPossibleShelter();
            }
            return true;
        }

        @Override
        public void startExecuting() {
            this.this$0.func_213499_en();
            super.startExecuting();
        }
    }

    class BiteGoal
    extends MeleeAttackGoal {
        final FoxEntity this$0;

        public BiteGoal(FoxEntity foxEntity, double d, boolean bl) {
            this.this$0 = foxEntity;
            super(foxEntity, d, bl);
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity livingEntity, double d) {
            double d2 = this.getAttackReachSqr(livingEntity);
            if (d <= d2 && this.func_234040_h_()) {
                this.func_234039_g_();
                this.attacker.attackEntityAsMob(livingEntity);
                this.this$0.playSound(SoundEvents.ENTITY_FOX_BITE, 1.0f, 1.0f);
            }
        }

        @Override
        public void startExecuting() {
            this.this$0.func_213502_u(true);
            super.startExecuting();
        }

        @Override
        public boolean shouldExecute() {
            return !this.this$0.isSitting() && !this.this$0.isSleeping() && !this.this$0.isCrouching() && !this.this$0.isStuck() && super.shouldExecute();
        }
    }

    class SleepGoal
    extends BaseGoal {
        private int field_220825_c;
        final FoxEntity this$0;

        public SleepGoal(FoxEntity foxEntity) {
            this.this$0 = foxEntity;
            super(foxEntity);
            this.field_220825_c = FoxEntity.access$400(this.this$0).nextInt(140);
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }

        @Override
        public boolean shouldExecute() {
            if (this.this$0.moveStrafing == 0.0f && this.this$0.moveVertical == 0.0f && this.this$0.moveForward == 0.0f) {
                return this.func_220823_j() || this.this$0.isSleeping();
            }
            return true;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.func_220823_j();
        }

        private boolean func_220823_j() {
            if (this.field_220825_c > 0) {
                --this.field_220825_c;
                return true;
            }
            return this.this$0.world.isDaytime() && this.func_220813_g() && !this.func_220814_h();
        }

        @Override
        public void resetTask() {
            this.field_220825_c = FoxEntity.access$500(this.this$0).nextInt(140);
            this.this$0.func_213499_en();
        }

        @Override
        public void startExecuting() {
            this.this$0.setSitting(true);
            this.this$0.setCrouching(true);
            this.this$0.func_213502_u(true);
            this.this$0.setJumping(true);
            this.this$0.setSleeping(false);
            this.this$0.getNavigator().clearPath();
            this.this$0.getMoveHelper().setMoveTo(this.this$0.getPosX(), this.this$0.getPosY(), this.this$0.getPosZ(), 0.0);
        }
    }

    class FollowGoal
    extends FollowParentGoal {
        private final FoxEntity owner;
        final FoxEntity this$0;

        public FollowGoal(FoxEntity foxEntity, FoxEntity foxEntity2, double d) {
            this.this$0 = foxEntity;
            super(foxEntity2, d);
            this.owner = foxEntity2;
        }

        @Override
        public boolean shouldExecute() {
            return !this.owner.isFoxAggroed() && super.shouldExecute();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return !this.owner.isFoxAggroed() && super.shouldContinueExecuting();
        }

        @Override
        public void startExecuting() {
            this.owner.func_213499_en();
            super.startExecuting();
        }
    }

    class StrollGoal
    extends MoveThroughVillageAtNightGoal {
        final FoxEntity this$0;

        public StrollGoal(FoxEntity foxEntity, int n, int n2) {
            this.this$0 = foxEntity;
            super(foxEntity, n2);
        }

        @Override
        public void startExecuting() {
            this.this$0.func_213499_en();
            super.startExecuting();
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && this.func_220759_g();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting() && this.func_220759_g();
        }

        private boolean func_220759_g() {
            return !this.this$0.isSleeping() && !this.this$0.isSitting() && !this.this$0.isFoxAggroed() && this.this$0.getAttackTarget() == null;
        }
    }

    public class EatBerriesGoal
    extends MoveToBlockGoal {
        protected int field_220731_g;
        final FoxEntity this$0;

        public EatBerriesGoal(FoxEntity foxEntity, double d, int n, int n2) {
            this.this$0 = foxEntity;
            super(foxEntity, d, n, n2);
        }

        @Override
        public double getTargetDistanceSq() {
            return 2.0;
        }

        @Override
        public boolean shouldMove() {
            return this.timeoutCounter % 100 == 0;
        }

        @Override
        protected boolean shouldMoveTo(IWorldReader iWorldReader, BlockPos blockPos) {
            BlockState blockState = iWorldReader.getBlockState(blockPos);
            return blockState.isIn(Blocks.SWEET_BERRY_BUSH) && blockState.get(SweetBerryBushBlock.AGE) >= 2;
        }

        @Override
        public void tick() {
            if (this.getIsAboveDestination()) {
                if (this.field_220731_g >= 40) {
                    this.eatBerry();
                } else {
                    ++this.field_220731_g;
                }
            } else if (!this.getIsAboveDestination() && FoxEntity.access$000(this.this$0).nextFloat() < 0.05f) {
                this.this$0.playSound(SoundEvents.ENTITY_FOX_SNIFF, 1.0f, 1.0f);
            }
            super.tick();
        }

        protected void eatBerry() {
            BlockState blockState;
            if (this.this$0.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING) && (blockState = this.this$0.world.getBlockState(this.destinationBlock)).isIn(Blocks.SWEET_BERRY_BUSH)) {
                int n = blockState.get(SweetBerryBushBlock.AGE);
                blockState.with(SweetBerryBushBlock.AGE, 1);
                int n2 = 1 + this.this$0.world.rand.nextInt(2) + (n == 3 ? 1 : 0);
                ItemStack itemStack = this.this$0.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
                if (itemStack.isEmpty()) {
                    this.this$0.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.SWEET_BERRIES));
                    --n2;
                }
                if (n2 > 0) {
                    Block.spawnAsEntity(this.this$0.world, this.destinationBlock, new ItemStack(Items.SWEET_BERRIES, n2));
                }
                this.this$0.playSound(SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, 1.0f, 1.0f);
                this.this$0.world.setBlockState(this.destinationBlock, (BlockState)blockState.with(SweetBerryBushBlock.AGE, 1), 1);
            }
        }

        @Override
        public boolean shouldExecute() {
            return !this.this$0.isSleeping() && super.shouldExecute();
        }

        @Override
        public void startExecuting() {
            this.field_220731_g = 0;
            this.this$0.setSitting(true);
            super.startExecuting();
        }
    }

    class FindItemsGoal
    extends Goal {
        final FoxEntity this$0;

        public FindItemsGoal(FoxEntity foxEntity) {
            this.this$0 = foxEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            if (!this.this$0.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty()) {
                return true;
            }
            if (this.this$0.getAttackTarget() == null && this.this$0.getRevengeTarget() == null) {
                if (!this.this$0.func_213478_eo()) {
                    return true;
                }
                if (this.this$0.getRNG().nextInt(10) != 0) {
                    return true;
                }
                List<ItemEntity> list = this.this$0.world.getEntitiesWithinAABB(ItemEntity.class, this.this$0.getBoundingBox().grow(8.0, 8.0, 8.0), TRUSTED_TARGET_SELECTOR);
                return !list.isEmpty() && this.this$0.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty();
            }
            return true;
        }

        @Override
        public void tick() {
            List<ItemEntity> list = this.this$0.world.getEntitiesWithinAABB(ItemEntity.class, this.this$0.getBoundingBox().grow(8.0, 8.0, 8.0), TRUSTED_TARGET_SELECTOR);
            ItemStack itemStack = this.this$0.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
            if (itemStack.isEmpty() && !list.isEmpty()) {
                this.this$0.getNavigator().tryMoveToEntityLiving(list.get(0), 1.2f);
            }
        }

        @Override
        public void startExecuting() {
            List<ItemEntity> list = this.this$0.world.getEntitiesWithinAABB(ItemEntity.class, this.this$0.getBoundingBox().grow(8.0, 8.0, 8.0), TRUSTED_TARGET_SELECTOR);
            if (!list.isEmpty()) {
                this.this$0.getNavigator().tryMoveToEntityLiving(list.get(0), 1.2f);
            }
        }
    }

    class WatchGoal
    extends LookAtGoal {
        final FoxEntity this$0;

        public WatchGoal(FoxEntity foxEntity, MobEntity mobEntity, Class<? extends LivingEntity> clazz, float f) {
            this.this$0 = foxEntity;
            super(mobEntity, clazz, f);
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && !this.this$0.isStuck() && !this.this$0.func_213467_eg();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting() && !this.this$0.isStuck() && !this.this$0.func_213467_eg();
        }
    }

    class SitAndLookGoal
    extends BaseGoal {
        private double field_220819_c;
        private double field_220820_d;
        private int field_220821_e;
        private int field_220822_f;
        final FoxEntity this$0;

        public SitAndLookGoal(FoxEntity foxEntity) {
            this.this$0 = foxEntity;
            super(foxEntity);
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean shouldExecute() {
            return this.this$0.getRevengeTarget() == null && this.this$0.getRNG().nextFloat() < 0.02f && !this.this$0.isSleeping() && this.this$0.getAttackTarget() == null && this.this$0.getNavigator().noPath() && !this.func_220814_h() && !this.this$0.func_213480_dY() && !this.this$0.isCrouching();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.field_220822_f > 0;
        }

        @Override
        public void startExecuting() {
            this.func_220817_j();
            this.field_220822_f = 2 + this.this$0.getRNG().nextInt(3);
            this.this$0.setSitting(false);
            this.this$0.getNavigator().clearPath();
        }

        @Override
        public void resetTask() {
            this.this$0.setSitting(true);
        }

        @Override
        public void tick() {
            --this.field_220821_e;
            if (this.field_220821_e <= 0) {
                --this.field_220822_f;
                this.func_220817_j();
            }
            this.this$0.getLookController().setLookPosition(this.this$0.getPosX() + this.field_220819_c, this.this$0.getPosYEye(), this.this$0.getPosZ() + this.field_220820_d, this.this$0.getHorizontalFaceSpeed(), this.this$0.getVerticalFaceSpeed());
        }

        private void func_220817_j() {
            double d = Math.PI * 2 * this.this$0.getRNG().nextDouble();
            this.field_220819_c = Math.cos(d);
            this.field_220820_d = Math.sin(d);
            this.field_220821_e = 80 + this.this$0.getRNG().nextInt(20);
        }
    }

    class RevengeGoal
    extends NearestAttackableTargetGoal<LivingEntity> {
        @Nullable
        private LivingEntity field_220786_j;
        private LivingEntity field_220787_k;
        private int field_220788_l;
        final FoxEntity this$0;

        public RevengeGoal(FoxEntity foxEntity, Class<LivingEntity> clazz, boolean bl, @Nullable boolean bl2, Predicate<LivingEntity> predicate) {
            this.this$0 = foxEntity;
            super(foxEntity, clazz, 10, bl, bl2, predicate);
        }

        @Override
        public boolean shouldExecute() {
            if (this.targetChance > 0 && this.goalOwner.getRNG().nextInt(this.targetChance) != 0) {
                return true;
            }
            for (UUID uUID : this.this$0.getTrustedUUIDs()) {
                LivingEntity livingEntity;
                Entity entity2;
                if (uUID == null || !(this.this$0.world instanceof ServerWorld) || !((entity2 = ((ServerWorld)this.this$0.world).getEntityByUuid(uUID)) instanceof LivingEntity)) continue;
                this.field_220787_k = livingEntity = (LivingEntity)entity2;
                this.field_220786_j = livingEntity.getRevengeTarget();
                int n = livingEntity.getRevengeTimer();
                return n != this.field_220788_l && this.isSuitableTarget(this.field_220786_j, this.targetEntitySelector);
            }
            return true;
        }

        @Override
        public void startExecuting() {
            this.setNearestTarget(this.field_220786_j);
            this.nearestTarget = this.field_220786_j;
            if (this.field_220787_k != null) {
                this.field_220788_l = this.field_220787_k.getRevengeTimer();
            }
            this.this$0.playSound(SoundEvents.ENTITY_FOX_AGGRO, 1.0f, 1.0f);
            this.this$0.setFoxAggroed(false);
            this.this$0.func_213454_em();
            super.startExecuting();
        }
    }

    public static enum Type {
        RED(0, "red", Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.TAIGA_MOUNTAINS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.GIANT_SPRUCE_TAIGA_HILLS),
        SNOW(1, "snow", Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS);

        private static final Type[] field_221088_c;
        private static final Map<String, Type> TYPES_BY_NAME;
        private final int index;
        private final String name;
        private final List<RegistryKey<Biome>> spawnBiomes;

        private Type(int n2, String string2, RegistryKey<Biome> ... registryKeyArray) {
            this.index = n2;
            this.name = string2;
            this.spawnBiomes = Arrays.asList(registryKeyArray);
        }

        public String getName() {
            return this.name;
        }

        public int getIndex() {
            return this.index;
        }

        public static Type getTypeByName(String string) {
            return TYPES_BY_NAME.getOrDefault(string, RED);
        }

        public static Type getTypeByIndex(int n) {
            if (n < 0 || n > field_221088_c.length) {
                n = 0;
            }
            return field_221088_c[n];
        }

        public static Type func_242325_a(Optional<RegistryKey<Biome>> optional) {
            return optional.isPresent() && Type.SNOW.spawnBiomes.contains(optional.get()) ? SNOW : RED;
        }

        private static Type lambda$static$1(Type type) {
            return type;
        }

        private static Type[] lambda$static$0(int n) {
            return new Type[n];
        }

        static {
            field_221088_c = (Type[])Arrays.stream(Type.values()).sorted(Comparator.comparingInt(Type::getIndex)).toArray(Type::lambda$static$0);
            TYPES_BY_NAME = Arrays.stream(Type.values()).collect(Collectors.toMap(Type::getName, Type::lambda$static$1));
        }
    }

    public static class FoxData
    extends AgeableEntity.AgeableData {
        public final Type field_220366_a;

        public FoxData(Type type) {
            super(false);
            this.field_220366_a = type;
        }
    }

    abstract class BaseGoal
    extends Goal {
        private final EntityPredicate field_220816_b;
        final FoxEntity this$0;

        private BaseGoal(FoxEntity foxEntity) {
            this.this$0 = foxEntity;
            this.field_220816_b = new EntityPredicate().setDistance(12.0).setLineOfSiteRequired().setCustomPredicate(new AlertablePredicate(this.this$0));
        }

        protected boolean func_220813_g() {
            BlockPos blockPos = new BlockPos(this.this$0.getPosX(), this.this$0.getBoundingBox().maxY, this.this$0.getPosZ());
            return !this.this$0.world.canSeeSky(blockPos) && this.this$0.getBlockPathWeight(blockPos) >= 0.0f;
        }

        protected boolean func_220814_h() {
            return !this.this$0.world.getTargettableEntitiesWithinAABB(LivingEntity.class, this.field_220816_b, this.this$0, this.this$0.getBoundingBox().grow(12.0, 6.0, 12.0)).isEmpty();
        }
    }

    public class AlertablePredicate
    implements Predicate<LivingEntity> {
        final FoxEntity this$0;

        public AlertablePredicate(FoxEntity foxEntity) {
            this.this$0 = foxEntity;
        }

        @Override
        public boolean test(LivingEntity livingEntity) {
            if (livingEntity instanceof FoxEntity) {
                return true;
            }
            if (!(livingEntity instanceof ChickenEntity || livingEntity instanceof RabbitEntity || livingEntity instanceof MonsterEntity)) {
                if (livingEntity instanceof TameableEntity) {
                    return !((TameableEntity)livingEntity).isTamed();
                }
                if (!(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity)livingEntity).isCreative()) {
                    if (this.this$0.isTrustedUUID(livingEntity.getUniqueID())) {
                        return true;
                    }
                    return !livingEntity.isSleeping() && !livingEntity.isDiscrete();
                }
                return true;
            }
            return false;
        }

        @Override
        public boolean test(Object object) {
            return this.test((LivingEntity)object);
        }
    }
}

