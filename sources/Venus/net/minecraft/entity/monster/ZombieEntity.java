/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.BreakBlockGoal;
import net.minecraft.entity.ai.goal.BreakDoorGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.GroundPathHelper;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;

public class ZombieEntity
extends MonsterEntity {
    private static final UUID BABY_SPEED_BOOST_ID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
    private static final AttributeModifier BABY_SPEED_BOOST = new AttributeModifier(BABY_SPEED_BOOST_ID, "Baby speed boost", 0.5, AttributeModifier.Operation.MULTIPLY_BASE);
    private static final DataParameter<Boolean> IS_CHILD = EntityDataManager.createKey(ZombieEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> VILLAGER_TYPE = EntityDataManager.createKey(ZombieEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> DROWNING = EntityDataManager.createKey(ZombieEntity.class, DataSerializers.BOOLEAN);
    private static final Predicate<Difficulty> HARD_DIFFICULTY_PREDICATE = ZombieEntity::lambda$static$0;
    private final BreakDoorGoal breakDoor = new BreakDoorGoal(this, HARD_DIFFICULTY_PREDICATE);
    private boolean isBreakDoorsTaskSet;
    private int inWaterTime;
    private int drownedConversionTime;

    public ZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super((EntityType<? extends MonsterEntity>)entityType, world);
    }

    public ZombieEntity(World world) {
        this((EntityType<? extends ZombieEntity>)EntityType.ZOMBIE, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new AttackTurtleEggGoal(this, (CreatureEntity)this, 1.0, 3));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.goalSelector.addGoal(2, new ZombieAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0, true, 4, this::isBreakDoorsTaskSet));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setCallsForHelp(ZombifiedPiglinEntity.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<AbstractVillagerEntity>((MobEntity)this, AbstractVillagerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolemEntity>((MobEntity)this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<TurtleEntity>(this, TurtleEntity.class, 10, true, false, TurtleEntity.TARGET_DRY_BABY));
    }

    public static AttributeModifierMap.MutableAttribute func_234342_eQ_() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.23f).createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0).createMutableAttribute(Attributes.ARMOR, 2.0).createMutableAttribute(Attributes.ZOMBIE_SPAWN_REINFORCEMENTS);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(IS_CHILD, false);
        this.getDataManager().register(VILLAGER_TYPE, 0);
        this.getDataManager().register(DROWNING, false);
    }

    public boolean isDrowning() {
        return this.getDataManager().get(DROWNING);
    }

    public boolean isBreakDoorsTaskSet() {
        return this.isBreakDoorsTaskSet;
    }

    public void setBreakDoorsAItask(boolean bl) {
        if (this.canBreakDoors() && GroundPathHelper.isGroundNavigator(this)) {
            if (this.isBreakDoorsTaskSet != bl) {
                this.isBreakDoorsTaskSet = bl;
                ((GroundPathNavigator)this.getNavigator()).setBreakDoors(bl);
                if (bl) {
                    this.goalSelector.addGoal(1, this.breakDoor);
                } else {
                    this.goalSelector.removeGoal(this.breakDoor);
                }
            }
        } else if (this.isBreakDoorsTaskSet) {
            this.goalSelector.removeGoal(this.breakDoor);
            this.isBreakDoorsTaskSet = false;
        }
    }

    protected boolean canBreakDoors() {
        return false;
    }

    @Override
    public boolean isChild() {
        return this.getDataManager().get(IS_CHILD);
    }

    @Override
    protected int getExperiencePoints(PlayerEntity playerEntity) {
        if (this.isChild()) {
            this.experienceValue = (int)((float)this.experienceValue * 2.5f);
        }
        return super.getExperiencePoints(playerEntity);
    }

    @Override
    public void setChild(boolean bl) {
        this.getDataManager().set(IS_CHILD, bl);
        if (this.world != null && !this.world.isRemote) {
            ModifiableAttributeInstance modifiableAttributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
            modifiableAttributeInstance.removeModifier(BABY_SPEED_BOOST);
            if (bl) {
                modifiableAttributeInstance.applyNonPersistentModifier(BABY_SPEED_BOOST);
            }
        }
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        if (IS_CHILD.equals(dataParameter)) {
            this.recalculateSize();
        }
        super.notifyDataManagerChange(dataParameter);
    }

    protected boolean shouldDrown() {
        return false;
    }

    @Override
    public void tick() {
        if (!this.world.isRemote && this.isAlive() && !this.isAIDisabled()) {
            if (this.isDrowning()) {
                --this.drownedConversionTime;
                if (this.drownedConversionTime < 0) {
                    this.onDrowned();
                }
            } else if (this.shouldDrown()) {
                if (this.areEyesInFluid(FluidTags.WATER)) {
                    ++this.inWaterTime;
                    if (this.inWaterTime >= 600) {
                        this.startDrowning(300);
                    }
                } else {
                    this.inWaterTime = -1;
                }
            }
        }
        super.tick();
    }

    @Override
    public void livingTick() {
        if (this.isAlive()) {
            boolean bl;
            boolean bl2 = bl = this.shouldBurnInDay() && this.isInDaylight();
            if (bl) {
                ItemStack itemStack = this.getItemStackFromSlot(EquipmentSlotType.HEAD);
                if (!itemStack.isEmpty()) {
                    if (itemStack.isDamageable()) {
                        itemStack.setDamage(itemStack.getDamage() + this.rand.nextInt(2));
                        if (itemStack.getDamage() >= itemStack.getMaxDamage()) {
                            this.sendBreakAnimation(EquipmentSlotType.HEAD);
                            this.setItemStackToSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
                        }
                    }
                    bl = false;
                }
                if (bl) {
                    this.setFire(8);
                }
            }
        }
        super.livingTick();
    }

    private void startDrowning(int n) {
        this.drownedConversionTime = n;
        this.getDataManager().set(DROWNING, true);
    }

    protected void onDrowned() {
        this.func_234341_c_(EntityType.DROWNED);
        if (!this.isSilent()) {
            this.world.playEvent(null, 1040, this.getPosition(), 0);
        }
    }

    protected void func_234341_c_(EntityType<? extends ZombieEntity> entityType) {
        ZombieEntity zombieEntity = this.func_233656_b_(entityType, false);
        if (zombieEntity != null) {
            zombieEntity.applyAttributeBonuses(zombieEntity.world.getDifficultyForLocation(zombieEntity.getPosition()).getClampedAdditionalDifficulty());
            zombieEntity.setBreakDoorsAItask(zombieEntity.canBreakDoors() && this.isBreakDoorsTaskSet());
        }
    }

    protected boolean shouldBurnInDay() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (!super.attackEntityFrom(damageSource, f)) {
            return true;
        }
        if (!(this.world instanceof ServerWorld)) {
            return true;
        }
        ServerWorld serverWorld = (ServerWorld)this.world;
        LivingEntity livingEntity = this.getAttackTarget();
        if (livingEntity == null && damageSource.getTrueSource() instanceof LivingEntity) {
            livingEntity = (LivingEntity)damageSource.getTrueSource();
        }
        if (livingEntity != null && this.world.getDifficulty() == Difficulty.HARD && (double)this.rand.nextFloat() < this.getAttributeValue(Attributes.ZOMBIE_SPAWN_REINFORCEMENTS) && this.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
            int n = MathHelper.floor(this.getPosX());
            int n2 = MathHelper.floor(this.getPosY());
            int n3 = MathHelper.floor(this.getPosZ());
            ZombieEntity zombieEntity = new ZombieEntity(this.world);
            for (int i = 0; i < 50; ++i) {
                int n4 = n + MathHelper.nextInt(this.rand, 7, 40) * MathHelper.nextInt(this.rand, -1, 1);
                int n5 = n2 + MathHelper.nextInt(this.rand, 7, 40) * MathHelper.nextInt(this.rand, -1, 1);
                int n6 = n3 + MathHelper.nextInt(this.rand, 7, 40) * MathHelper.nextInt(this.rand, -1, 1);
                BlockPos blockPos = new BlockPos(n4, n5, n6);
                EntityType<?> entityType = zombieEntity.getType();
                EntitySpawnPlacementRegistry.PlacementType placementType = EntitySpawnPlacementRegistry.getPlacementType(entityType);
                if (!WorldEntitySpawner.canCreatureTypeSpawnAtLocation(placementType, this.world, blockPos, entityType) || !EntitySpawnPlacementRegistry.canSpawnEntity(entityType, serverWorld, SpawnReason.REINFORCEMENT, blockPos, this.world.rand)) continue;
                zombieEntity.setPosition(n4, n5, n6);
                if (this.world.isPlayerWithin(n4, n5, n6, 7.0) || !this.world.checkNoEntityCollision(zombieEntity) || !this.world.hasNoCollisions(zombieEntity) || this.world.containsAnyLiquid(zombieEntity.getBoundingBox())) continue;
                zombieEntity.setAttackTarget(livingEntity);
                zombieEntity.onInitialSpawn(serverWorld, this.world.getDifficultyForLocation(zombieEntity.getPosition()), SpawnReason.REINFORCEMENT, null, null);
                serverWorld.func_242417_l(zombieEntity);
                this.getAttribute(Attributes.ZOMBIE_SPAWN_REINFORCEMENTS).applyPersistentModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05f, AttributeModifier.Operation.ADDITION));
                zombieEntity.getAttribute(Attributes.ZOMBIE_SPAWN_REINFORCEMENTS).applyPersistentModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05f, AttributeModifier.Operation.ADDITION));
                break;
            }
        }
        return false;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity2) {
        boolean bl = super.attackEntityAsMob(entity2);
        if (bl) {
            float f = this.world.getDifficultyForLocation(this.getPosition()).getAdditionalDifficulty();
            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3f) {
                entity2.setFire(2 * (int)f);
            }
        }
        return bl;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_ZOMBIE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ZOMBIE_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(this.getStepSound(), 0.15f, 1.0f);
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
        super.setEquipmentBasedOnDifficulty(difficultyInstance);
        float f = this.rand.nextFloat();
        float f2 = this.world.getDifficulty() == Difficulty.HARD ? 0.05f : 0.01f;
        if (f < f2) {
            int n = this.rand.nextInt(3);
            if (n == 0) {
                this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
            } else {
                this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
            }
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putBoolean("IsBaby", this.isChild());
        compoundNBT.putBoolean("CanBreakDoors", this.isBreakDoorsTaskSet());
        compoundNBT.putInt("InWaterTime", this.isInWater() ? this.inWaterTime : -1);
        compoundNBT.putInt("DrownedConversionTime", this.isDrowning() ? this.drownedConversionTime : -1);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setChild(compoundNBT.getBoolean("IsBaby"));
        this.setBreakDoorsAItask(compoundNBT.getBoolean("CanBreakDoors"));
        this.inWaterTime = compoundNBT.getInt("InWaterTime");
        if (compoundNBT.contains("DrownedConversionTime", 0) && compoundNBT.getInt("DrownedConversionTime") > -1) {
            this.startDrowning(compoundNBT.getInt("DrownedConversionTime"));
        }
    }

    @Override
    public void func_241847_a(ServerWorld serverWorld, LivingEntity livingEntity) {
        super.func_241847_a(serverWorld, livingEntity);
        if ((serverWorld.getDifficulty() == Difficulty.NORMAL || serverWorld.getDifficulty() == Difficulty.HARD) && livingEntity instanceof VillagerEntity) {
            if (serverWorld.getDifficulty() != Difficulty.HARD && this.rand.nextBoolean()) {
                return;
            }
            VillagerEntity villagerEntity = (VillagerEntity)livingEntity;
            ZombieVillagerEntity zombieVillagerEntity = villagerEntity.func_233656_b_(EntityType.ZOMBIE_VILLAGER, true);
            zombieVillagerEntity.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(zombieVillagerEntity.getPosition()), SpawnReason.CONVERSION, new GroupData(false, true), null);
            zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
            zombieVillagerEntity.setGossips(villagerEntity.getGossip().write(NBTDynamicOps.INSTANCE).getValue());
            zombieVillagerEntity.setOffers(villagerEntity.getOffers().write());
            zombieVillagerEntity.setEXP(villagerEntity.getXp());
            if (!this.isSilent()) {
                serverWorld.playEvent(null, 1026, this.getPosition(), 0);
            }
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return this.isChild() ? 0.93f : 1.74f;
    }

    @Override
    public boolean canEquipItem(ItemStack itemStack) {
        return itemStack.getItem() == Items.EGG && this.isChild() && this.isPassenger() ? false : super.canEquipItem(itemStack);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        Object object;
        iLivingEntityData = super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
        float f = difficultyInstance.getClampedAdditionalDifficulty();
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55f * f);
        if (iLivingEntityData == null) {
            iLivingEntityData = new GroupData(ZombieEntity.func_241399_a_(iServerWorld.getRandom()), true);
        }
        if (iLivingEntityData instanceof GroupData) {
            object = (GroupData)iLivingEntityData;
            if (((GroupData)object).isChild) {
                this.setChild(false);
                if (((GroupData)object).field_241400_b_) {
                    if ((double)iServerWorld.getRandom().nextFloat() < 0.05) {
                        var8_8 = iServerWorld.getEntitiesWithinAABB(ChickenEntity.class, this.getBoundingBox().grow(5.0, 3.0, 5.0), EntityPredicates.IS_STANDALONE);
                        if (!var8_8.isEmpty()) {
                            ChickenEntity chickenEntity = (ChickenEntity)var8_8.get(0);
                            chickenEntity.setChickenJockey(false);
                            this.startRiding(chickenEntity);
                        }
                    } else if ((double)iServerWorld.getRandom().nextFloat() < 0.05) {
                        var8_8 = EntityType.CHICKEN.create(this.world);
                        ((Entity)((Object)var8_8)).setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0f);
                        ((AgeableEntity)((Object)var8_8)).onInitialSpawn(iServerWorld, difficultyInstance, SpawnReason.JOCKEY, null, null);
                        ((ChickenEntity)((Object)var8_8)).setChickenJockey(false);
                        this.startRiding((Entity)((Object)var8_8));
                        iServerWorld.addEntity((Entity)((Object)var8_8));
                    }
                }
            }
            this.setBreakDoorsAItask(this.canBreakDoors() && this.rand.nextFloat() < f * 0.1f);
            this.setEquipmentBasedOnDifficulty(difficultyInstance);
            this.setEnchantmentBasedOnDifficulty(difficultyInstance);
        }
        if (this.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty()) {
            object = LocalDate.now();
            int n = ((LocalDate)object).get(ChronoField.DAY_OF_MONTH);
            int n2 = ((LocalDate)object).get(ChronoField.MONTH_OF_YEAR);
            if (n2 == 10 && n == 31 && this.rand.nextFloat() < 0.25f) {
                this.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(this.rand.nextFloat() < 0.1f ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
                this.inventoryArmorDropChances[EquipmentSlotType.HEAD.getIndex()] = 0.0f;
            }
        }
        this.applyAttributeBonuses(f);
        return iLivingEntityData;
    }

    public static boolean func_241399_a_(Random random2) {
        return random2.nextFloat() < 0.05f;
    }

    protected void applyAttributeBonuses(float f) {
        this.func_230291_eT_();
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).applyPersistentModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * (double)0.05f, AttributeModifier.Operation.ADDITION));
        double d = this.rand.nextDouble() * 1.5 * (double)f;
        if (d > 1.0) {
            this.getAttribute(Attributes.FOLLOW_RANGE).applyPersistentModifier(new AttributeModifier("Random zombie-spawn bonus", d, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        if (this.rand.nextFloat() < f * 0.05f) {
            this.getAttribute(Attributes.ZOMBIE_SPAWN_REINFORCEMENTS).applyPersistentModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 0.25 + 0.5, AttributeModifier.Operation.ADDITION));
            this.getAttribute(Attributes.MAX_HEALTH).applyPersistentModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0 + 1.0, AttributeModifier.Operation.MULTIPLY_TOTAL));
            this.setBreakDoorsAItask(this.canBreakDoors());
        }
    }

    protected void func_230291_eT_() {
        this.getAttribute(Attributes.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(this.rand.nextDouble() * (double)0.1f);
    }

    @Override
    public double getYOffset() {
        return this.isChild() ? 0.0 : -0.45;
    }

    @Override
    protected void dropSpecialItems(DamageSource damageSource, int n, boolean bl) {
        ItemStack itemStack;
        CreeperEntity creeperEntity;
        super.dropSpecialItems(damageSource, n, bl);
        Entity entity2 = damageSource.getTrueSource();
        if (entity2 instanceof CreeperEntity && (creeperEntity = (CreeperEntity)entity2).ableToCauseSkullDrop() && !(itemStack = this.getSkullDrop()).isEmpty()) {
            creeperEntity.incrementDroppedSkulls();
            this.entityDropItem(itemStack);
        }
    }

    protected ItemStack getSkullDrop() {
        return new ItemStack(Items.ZOMBIE_HEAD);
    }

    private static boolean lambda$static$0(Difficulty difficulty) {
        return difficulty == Difficulty.HARD;
    }

    static Random access$000(ZombieEntity zombieEntity) {
        return zombieEntity.rand;
    }

    class AttackTurtleEggGoal
    extends BreakBlockGoal {
        final ZombieEntity this$0;

        AttackTurtleEggGoal(ZombieEntity zombieEntity, CreatureEntity creatureEntity, double d, int n) {
            this.this$0 = zombieEntity;
            super(Blocks.TURTLE_EGG, creatureEntity, d, n);
        }

        @Override
        public void playBreakingSound(IWorld iWorld, BlockPos blockPos) {
            iWorld.playSound(null, blockPos, SoundEvents.ENTITY_ZOMBIE_DESTROY_EGG, SoundCategory.HOSTILE, 0.5f, 0.9f + ZombieEntity.access$000(this.this$0).nextFloat() * 0.2f);
        }

        @Override
        public void playBrokenSound(World world, BlockPos blockPos) {
            world.playSound(null, blockPos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7f, 0.9f + world.rand.nextFloat() * 0.2f);
        }

        @Override
        public double getTargetDistanceSq() {
            return 1.14;
        }
    }

    public static class GroupData
    implements ILivingEntityData {
        public final boolean isChild;
        public final boolean field_241400_b_;

        public GroupData(boolean bl, boolean bl2) {
            this.isChild = bl;
            this.field_241400_b_ = bl2;
        }
    }
}

