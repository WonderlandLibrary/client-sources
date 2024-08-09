/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive.horse;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RunAroundLikeCrazyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.HandSide;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TransportationHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class AbstractHorseEntity
extends AnimalEntity
implements IInventoryChangedListener,
IJumpingMount,
IEquipable {
    private static final Predicate<LivingEntity> IS_HORSE_BREEDING = AbstractHorseEntity::lambda$static$0;
    private static final EntityPredicate MOMMY_TARGETING = new EntityPredicate().setDistance(16.0).allowInvulnerable().allowFriendlyFire().setLineOfSiteRequired().setCustomPredicate(IS_HORSE_BREEDING);
    private static final Ingredient field_234235_bE_ = Ingredient.fromItems(Items.WHEAT, Items.SUGAR, Blocks.HAY_BLOCK.asItem(), Items.APPLE, Items.GOLDEN_CARROT, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE);
    private static final DataParameter<Byte> STATUS = EntityDataManager.createKey(AbstractHorseEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(AbstractHorseEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private int eatingCounter;
    private int openMouthCounter;
    private int jumpRearingCounter;
    public int tailCounter;
    public int sprintCounter;
    protected boolean horseJumping;
    protected Inventory horseChest;
    protected int temper;
    protected float jumpPower;
    private boolean allowStandSliding;
    private float headLean;
    private float prevHeadLean;
    private float rearingAmount;
    private float prevRearingAmount;
    private float mouthOpenness;
    private float prevMouthOpenness;
    protected boolean canGallop = true;
    protected int gallopTime;

    protected AbstractHorseEntity(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super((EntityType<? extends AnimalEntity>)entityType, world);
        this.stepHeight = 1.0f;
        this.initHorseChest();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2));
        this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0, AbstractHorseEntity.class));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.7));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.initExtraAI();
    }

    protected void initExtraAI() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(STATUS, (byte)0);
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.empty());
    }

    protected boolean getHorseWatchableBoolean(int n) {
        return (this.dataManager.get(STATUS) & n) != 0;
    }

    protected void setHorseWatchableBoolean(int n, boolean bl) {
        byte by = this.dataManager.get(STATUS);
        if (bl) {
            this.dataManager.set(STATUS, (byte)(by | n));
        } else {
            this.dataManager.set(STATUS, (byte)(by & ~n));
        }
    }

    public boolean isTame() {
        return this.getHorseWatchableBoolean(1);
    }

    @Nullable
    public UUID getOwnerUniqueId() {
        return this.dataManager.get(OWNER_UNIQUE_ID).orElse(null);
    }

    public void setOwnerUniqueId(@Nullable UUID uUID) {
        this.dataManager.set(OWNER_UNIQUE_ID, Optional.ofNullable(uUID));
    }

    public boolean isHorseJumping() {
        return this.horseJumping;
    }

    public void setHorseTamed(boolean bl) {
        this.setHorseWatchableBoolean(2, bl);
    }

    public void setHorseJumping(boolean bl) {
        this.horseJumping = bl;
    }

    @Override
    protected void onLeashDistance(float f) {
        if (f > 6.0f && this.isEatingHaystack()) {
            this.setEatingHaystack(true);
        }
    }

    public boolean isEatingHaystack() {
        return this.getHorseWatchableBoolean(1);
    }

    public boolean isRearing() {
        return this.getHorseWatchableBoolean(1);
    }

    public boolean isBreeding() {
        return this.getHorseWatchableBoolean(1);
    }

    public void setBreeding(boolean bl) {
        this.setHorseWatchableBoolean(8, bl);
    }

    @Override
    public boolean func_230264_L__() {
        return this.isAlive() && !this.isChild() && this.isTame();
    }

    @Override
    public void func_230266_a_(@Nullable SoundCategory soundCategory) {
        this.horseChest.setInventorySlotContents(0, new ItemStack(Items.SADDLE));
        if (soundCategory != null) {
            this.world.playMovingSound(null, this, SoundEvents.ENTITY_HORSE_SADDLE, soundCategory, 0.5f, 1.0f);
        }
    }

    @Override
    public boolean isHorseSaddled() {
        return this.getHorseWatchableBoolean(1);
    }

    public int getTemper() {
        return this.temper;
    }

    public void setTemper(int n) {
        this.temper = n;
    }

    public int increaseTemper(int n) {
        int n2 = MathHelper.clamp(this.getTemper() + n, 0, this.getMaxTemper());
        this.setTemper(n2);
        return n2;
    }

    @Override
    public boolean canBePushed() {
        return !this.isBeingRidden();
    }

    private void eatingHorse() {
        SoundEvent soundEvent;
        this.openHorseMouth();
        if (!this.isSilent() && (soundEvent = this.func_230274_fe_()) != null) {
            this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), soundEvent, this.getSoundCategory(), 1.0f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f);
        }
    }

    @Override
    public boolean onLivingFall(float f, float f2) {
        int n;
        if (f > 1.0f) {
            this.playSound(SoundEvents.ENTITY_HORSE_LAND, 0.4f, 1.0f);
        }
        if ((n = this.calculateFallDamage(f, f2)) <= 0) {
            return true;
        }
        this.attackEntityFrom(DamageSource.FALL, n);
        if (this.isBeingRidden()) {
            for (Entity entity2 : this.getRecursivePassengers()) {
                entity2.attackEntityFrom(DamageSource.FALL, n);
            }
        }
        this.playFallSound();
        return false;
    }

    @Override
    protected int calculateFallDamage(float f, float f2) {
        return MathHelper.ceil((f * 0.5f - 3.0f) * f2);
    }

    protected int getInventorySize() {
        return 1;
    }

    protected void initHorseChest() {
        Inventory inventory = this.horseChest;
        this.horseChest = new Inventory(this.getInventorySize());
        if (inventory != null) {
            inventory.removeListener(this);
            int n = Math.min(inventory.getSizeInventory(), this.horseChest.getSizeInventory());
            for (int i = 0; i < n; ++i) {
                ItemStack itemStack = inventory.getStackInSlot(i);
                if (itemStack.isEmpty()) continue;
                this.horseChest.setInventorySlotContents(i, itemStack.copy());
            }
        }
        this.horseChest.addListener(this);
        this.func_230275_fc_();
    }

    protected void func_230275_fc_() {
        if (!this.world.isRemote) {
            this.setHorseWatchableBoolean(4, !this.horseChest.getStackInSlot(0).isEmpty());
        }
    }

    @Override
    public void onInventoryChanged(IInventory iInventory) {
        boolean bl = this.isHorseSaddled();
        this.func_230275_fc_();
        if (this.ticksExisted > 20 && !bl && this.isHorseSaddled()) {
            this.playSound(SoundEvents.ENTITY_HORSE_SADDLE, 0.5f, 1.0f);
        }
    }

    public double getHorseJumpStrength() {
        return this.getAttributeValue(Attributes.HORSE_JUMP_STRENGTH);
    }

    @Nullable
    protected SoundEvent func_230274_fe_() {
        return null;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.rand.nextInt(3) == 0) {
            this.makeHorseRear();
        }
        return null;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        if (this.rand.nextInt(10) == 0 && !this.isMovementBlocked()) {
            this.makeHorseRear();
        }
        return null;
    }

    @Nullable
    protected SoundEvent getAngrySound() {
        this.makeHorseRear();
        return null;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        if (!blockState.getMaterial().isLiquid()) {
            BlockState blockState2 = this.world.getBlockState(blockPos.up());
            SoundType soundType = blockState.getSoundType();
            if (blockState2.isIn(Blocks.SNOW)) {
                soundType = blockState2.getSoundType();
            }
            if (this.isBeingRidden() && this.canGallop) {
                ++this.gallopTime;
                if (this.gallopTime > 5 && this.gallopTime % 3 == 0) {
                    this.playGallopSound(soundType);
                } else if (this.gallopTime <= 5) {
                    this.playSound(SoundEvents.ENTITY_HORSE_STEP_WOOD, soundType.getVolume() * 0.15f, soundType.getPitch());
                }
            } else if (soundType == SoundType.WOOD) {
                this.playSound(SoundEvents.ENTITY_HORSE_STEP_WOOD, soundType.getVolume() * 0.15f, soundType.getPitch());
            } else {
                this.playSound(SoundEvents.ENTITY_HORSE_STEP, soundType.getVolume() * 0.15f, soundType.getPitch());
            }
        }
    }

    protected void playGallopSound(SoundType soundType) {
        this.playSound(SoundEvents.ENTITY_HORSE_GALLOP, soundType.getVolume() * 0.15f, soundType.getPitch());
    }

    public static AttributeModifierMap.MutableAttribute func_234237_fg_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.HORSE_JUMP_STRENGTH).createMutableAttribute(Attributes.MAX_HEALTH, 53.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.225f);
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    public int getMaxTemper() {
        return 1;
    }

    @Override
    protected float getSoundVolume() {
        return 0.8f;
    }

    @Override
    public int getTalkInterval() {
        return 1;
    }

    public void openGUI(PlayerEntity playerEntity) {
        if (!this.world.isRemote && (!this.isBeingRidden() || this.isPassenger(playerEntity)) && this.isTame()) {
            playerEntity.openHorseInventory(this, this.horseChest);
        }
    }

    public ActionResultType func_241395_b_(PlayerEntity playerEntity, ItemStack itemStack) {
        boolean bl = this.handleEating(playerEntity, itemStack);
        if (!playerEntity.abilities.isCreativeMode) {
            itemStack.shrink(1);
        }
        if (this.world.isRemote) {
            return ActionResultType.CONSUME;
        }
        return bl ? ActionResultType.SUCCESS : ActionResultType.PASS;
    }

    protected boolean handleEating(PlayerEntity playerEntity, ItemStack itemStack) {
        boolean bl = false;
        float f = 0.0f;
        int n = 0;
        int n2 = 0;
        Item item = itemStack.getItem();
        if (item == Items.WHEAT) {
            f = 2.0f;
            n = 20;
            n2 = 3;
        } else if (item == Items.SUGAR) {
            f = 1.0f;
            n = 30;
            n2 = 3;
        } else if (item == Blocks.HAY_BLOCK.asItem()) {
            f = 20.0f;
            n = 180;
        } else if (item == Items.APPLE) {
            f = 3.0f;
            n = 60;
            n2 = 3;
        } else if (item == Items.GOLDEN_CARROT) {
            f = 4.0f;
            n = 60;
            n2 = 5;
            if (!this.world.isRemote && this.isTame() && this.getGrowingAge() == 0 && !this.isInLove()) {
                bl = true;
                this.setInLove(playerEntity);
            }
        } else if (item == Items.GOLDEN_APPLE || item == Items.ENCHANTED_GOLDEN_APPLE) {
            f = 10.0f;
            n = 240;
            n2 = 10;
            if (!this.world.isRemote && this.isTame() && this.getGrowingAge() == 0 && !this.isInLove()) {
                bl = true;
                this.setInLove(playerEntity);
            }
        }
        if (this.getHealth() < this.getMaxHealth() && f > 0.0f) {
            this.heal(f);
            bl = true;
        }
        if (this.isChild() && n > 0) {
            this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getPosXRandom(1.0), this.getPosYRandom() + 0.5, this.getPosZRandom(1.0), 0.0, 0.0, 0.0);
            if (!this.world.isRemote) {
                this.addGrowth(n);
            }
            bl = true;
        }
        if (n2 > 0 && (bl || !this.isTame()) && this.getTemper() < this.getMaxTemper()) {
            bl = true;
            if (!this.world.isRemote) {
                this.increaseTemper(n2);
            }
        }
        if (bl) {
            this.eatingHorse();
        }
        return bl;
    }

    protected void mountTo(PlayerEntity playerEntity) {
        this.setEatingHaystack(true);
        this.setRearing(true);
        if (!this.world.isRemote) {
            playerEntity.rotationYaw = this.rotationYaw;
            playerEntity.rotationPitch = this.rotationPitch;
            playerEntity.startRiding(this);
        }
    }

    @Override
    protected boolean isMovementBlocked() {
        return super.isMovementBlocked() && this.isBeingRidden() && this.isHorseSaddled() || this.isEatingHaystack() || this.isRearing();
    }

    @Override
    public boolean isBreedingItem(ItemStack itemStack) {
        return field_234235_bE_.test(itemStack);
    }

    private void moveTail() {
        this.tailCounter = 1;
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (this.horseChest != null) {
            for (int i = 0; i < this.horseChest.getSizeInventory(); ++i) {
                ItemStack itemStack = this.horseChest.getStackInSlot(i);
                if (itemStack.isEmpty() || EnchantmentHelper.hasVanishingCurse(itemStack)) continue;
                this.entityDropItem(itemStack);
            }
        }
    }

    @Override
    public void livingTick() {
        if (this.rand.nextInt(200) == 0) {
            this.moveTail();
        }
        super.livingTick();
        if (!this.world.isRemote && this.isAlive()) {
            if (this.rand.nextInt(900) == 0 && this.deathTime == 0) {
                this.heal(1.0f);
            }
            if (this.canEatGrass()) {
                if (!this.isEatingHaystack() && !this.isBeingRidden() && this.rand.nextInt(300) == 0 && this.world.getBlockState(this.getPosition().down()).isIn(Blocks.GRASS_BLOCK)) {
                    this.setEatingHaystack(false);
                }
                if (this.isEatingHaystack() && ++this.eatingCounter > 50) {
                    this.eatingCounter = 0;
                    this.setEatingHaystack(true);
                }
            }
            this.followMother();
        }
    }

    protected void followMother() {
        AbstractHorseEntity abstractHorseEntity;
        if (this.isBreeding() && this.isChild() && !this.isEatingHaystack() && (abstractHorseEntity = this.world.getClosestEntityWithinAABB(AbstractHorseEntity.class, MOMMY_TARGETING, this, this.getPosX(), this.getPosY(), this.getPosZ(), this.getBoundingBox().grow(16.0))) != null && this.getDistanceSq(abstractHorseEntity) > 4.0) {
            this.navigator.getPathToEntity(abstractHorseEntity, 0);
        }
    }

    public boolean canEatGrass() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.openMouthCounter > 0 && ++this.openMouthCounter > 30) {
            this.openMouthCounter = 0;
            this.setHorseWatchableBoolean(64, true);
        }
        if ((this.canPassengerSteer() || this.isServerWorld()) && this.jumpRearingCounter > 0 && ++this.jumpRearingCounter > 20) {
            this.jumpRearingCounter = 0;
            this.setRearing(true);
        }
        if (this.tailCounter > 0 && ++this.tailCounter > 8) {
            this.tailCounter = 0;
        }
        if (this.sprintCounter > 0) {
            ++this.sprintCounter;
            if (this.sprintCounter > 300) {
                this.sprintCounter = 0;
            }
        }
        this.prevHeadLean = this.headLean;
        if (this.isEatingHaystack()) {
            this.headLean += (1.0f - this.headLean) * 0.4f + 0.05f;
            if (this.headLean > 1.0f) {
                this.headLean = 1.0f;
            }
        } else {
            this.headLean += (0.0f - this.headLean) * 0.4f - 0.05f;
            if (this.headLean < 0.0f) {
                this.headLean = 0.0f;
            }
        }
        this.prevRearingAmount = this.rearingAmount;
        if (this.isRearing()) {
            this.prevHeadLean = this.headLean = 0.0f;
            this.rearingAmount += (1.0f - this.rearingAmount) * 0.4f + 0.05f;
            if (this.rearingAmount > 1.0f) {
                this.rearingAmount = 1.0f;
            }
        } else {
            this.allowStandSliding = false;
            this.rearingAmount += (0.8f * this.rearingAmount * this.rearingAmount * this.rearingAmount - this.rearingAmount) * 0.6f - 0.05f;
            if (this.rearingAmount < 0.0f) {
                this.rearingAmount = 0.0f;
            }
        }
        this.prevMouthOpenness = this.mouthOpenness;
        if (this.getHorseWatchableBoolean(1)) {
            this.mouthOpenness += (1.0f - this.mouthOpenness) * 0.7f + 0.05f;
            if (this.mouthOpenness > 1.0f) {
                this.mouthOpenness = 1.0f;
            }
        } else {
            this.mouthOpenness += (0.0f - this.mouthOpenness) * 0.7f - 0.05f;
            if (this.mouthOpenness < 0.0f) {
                this.mouthOpenness = 0.0f;
            }
        }
    }

    private void openHorseMouth() {
        if (!this.world.isRemote) {
            this.openMouthCounter = 1;
            this.setHorseWatchableBoolean(64, false);
        }
    }

    public void setEatingHaystack(boolean bl) {
        this.setHorseWatchableBoolean(16, bl);
    }

    public void setRearing(boolean bl) {
        if (bl) {
            this.setEatingHaystack(true);
        }
        this.setHorseWatchableBoolean(32, bl);
    }

    private void makeHorseRear() {
        if (this.canPassengerSteer() || this.isServerWorld()) {
            this.jumpRearingCounter = 1;
            this.setRearing(false);
        }
    }

    public void makeMad() {
        if (!this.isRearing()) {
            this.makeHorseRear();
            SoundEvent soundEvent = this.getAngrySound();
            if (soundEvent != null) {
                this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
            }
        }
    }

    public boolean setTamedBy(PlayerEntity playerEntity) {
        this.setOwnerUniqueId(playerEntity.getUniqueID());
        this.setHorseTamed(false);
        if (playerEntity instanceof ServerPlayerEntity) {
            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayerEntity)playerEntity, this);
        }
        this.world.setEntityState(this, (byte)7);
        return false;
    }

    @Override
    public void travel(Vector3d vector3d) {
        if (this.isAlive()) {
            if (this.isBeingRidden() && this.canBeSteered() && this.isHorseSaddled()) {
                LivingEntity livingEntity = (LivingEntity)this.getControllingPassenger();
                this.prevRotationYaw = this.rotationYaw = livingEntity.rotationYaw;
                this.rotationPitch = livingEntity.rotationPitch * 0.5f;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
                float f = livingEntity.moveStrafing * 0.5f;
                float f2 = livingEntity.moveForward;
                if (f2 <= 0.0f) {
                    f2 *= 0.25f;
                    this.gallopTime = 0;
                }
                if (this.onGround && this.jumpPower == 0.0f && this.isRearing() && !this.allowStandSliding) {
                    f = 0.0f;
                    f2 = 0.0f;
                }
                if (this.jumpPower > 0.0f && !this.isHorseJumping() && this.onGround) {
                    double d = this.getHorseJumpStrength() * (double)this.jumpPower * (double)this.getJumpFactor();
                    double d2 = this.isPotionActive(Effects.JUMP_BOOST) ? d + (double)((float)(this.getActivePotionEffect(Effects.JUMP_BOOST).getAmplifier() + 1) * 0.1f) : d;
                    Vector3d vector3d2 = this.getMotion();
                    this.setMotion(vector3d2.x, d2, vector3d2.z);
                    this.setHorseJumping(false);
                    this.isAirBorne = true;
                    if (f2 > 0.0f) {
                        float f3 = MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180));
                        float f4 = MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180));
                        this.setMotion(this.getMotion().add(-0.4f * f3 * this.jumpPower, 0.0, 0.4f * f4 * this.jumpPower));
                    }
                    this.jumpPower = 0.0f;
                }
                this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1f;
                if (this.canPassengerSteer()) {
                    this.setAIMoveSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vector3d(f, vector3d.y, f2));
                } else if (livingEntity instanceof PlayerEntity) {
                    this.setMotion(Vector3d.ZERO);
                }
                if (this.onGround) {
                    this.jumpPower = 0.0f;
                    this.setHorseJumping(true);
                }
                this.func_233629_a_(this, true);
            } else {
                this.jumpMovementFactor = 0.02f;
                super.travel(vector3d);
            }
        }
    }

    protected void playJumpSound() {
        this.playSound(SoundEvents.ENTITY_HORSE_JUMP, 0.4f, 1.0f);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putBoolean("EatingHaystack", this.isEatingHaystack());
        compoundNBT.putBoolean("Bred", this.isBreeding());
        compoundNBT.putInt("Temper", this.getTemper());
        compoundNBT.putBoolean("Tame", this.isTame());
        if (this.getOwnerUniqueId() != null) {
            compoundNBT.putUniqueId("Owner", this.getOwnerUniqueId());
        }
        if (!this.horseChest.getStackInSlot(0).isEmpty()) {
            compoundNBT.put("SaddleItem", this.horseChest.getStackInSlot(0).write(new CompoundNBT()));
        }
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        Object object;
        UUID uUID;
        super.readAdditional(compoundNBT);
        this.setEatingHaystack(compoundNBT.getBoolean("EatingHaystack"));
        this.setBreeding(compoundNBT.getBoolean("Bred"));
        this.setTemper(compoundNBT.getInt("Temper"));
        this.setHorseTamed(compoundNBT.getBoolean("Tame"));
        if (compoundNBT.hasUniqueId("Owner")) {
            uUID = compoundNBT.getUniqueId("Owner");
        } else {
            object = compoundNBT.getString("Owner");
            uUID = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), (String)object);
        }
        if (uUID != null) {
            this.setOwnerUniqueId(uUID);
        }
        if (compoundNBT.contains("SaddleItem", 1) && ((ItemStack)(object = ItemStack.read(compoundNBT.getCompound("SaddleItem")))).getItem() == Items.SADDLE) {
            this.horseChest.setInventorySlotContents(0, (ItemStack)object);
        }
        this.func_230275_fc_();
    }

    @Override
    public boolean canMateWith(AnimalEntity animalEntity) {
        return true;
    }

    protected boolean canMate() {
        return !this.isBeingRidden() && !this.isPassenger() && this.isTame() && !this.isChild() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
    }

    @Override
    @Nullable
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return null;
    }

    protected void setOffspringAttributes(AgeableEntity ageableEntity, AbstractHorseEntity abstractHorseEntity) {
        double d = this.getBaseAttributeValue(Attributes.MAX_HEALTH) + ageableEntity.getBaseAttributeValue(Attributes.MAX_HEALTH) + (double)this.getModifiedMaxHealth();
        abstractHorseEntity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(d / 3.0);
        double d2 = this.getBaseAttributeValue(Attributes.HORSE_JUMP_STRENGTH) + ageableEntity.getBaseAttributeValue(Attributes.HORSE_JUMP_STRENGTH) + this.getModifiedJumpStrength();
        abstractHorseEntity.getAttribute(Attributes.HORSE_JUMP_STRENGTH).setBaseValue(d2 / 3.0);
        double d3 = this.getBaseAttributeValue(Attributes.MOVEMENT_SPEED) + ageableEntity.getBaseAttributeValue(Attributes.MOVEMENT_SPEED) + this.getModifiedMovementSpeed();
        abstractHorseEntity.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(d3 / 3.0);
    }

    @Override
    public boolean canBeSteered() {
        return this.getControllingPassenger() instanceof LivingEntity;
    }

    public float getGrassEatingAmount(float f) {
        return MathHelper.lerp(f, this.prevHeadLean, this.headLean);
    }

    public float getRearingAmount(float f) {
        return MathHelper.lerp(f, this.prevRearingAmount, this.rearingAmount);
    }

    public float getMouthOpennessAngle(float f) {
        return MathHelper.lerp(f, this.prevMouthOpenness, this.mouthOpenness);
    }

    @Override
    public void setJumpPower(int n) {
        if (this.isHorseSaddled()) {
            if (n < 0) {
                n = 0;
            } else {
                this.allowStandSliding = true;
                this.makeHorseRear();
            }
            this.jumpPower = n >= 90 ? 1.0f : 0.4f + 0.4f * (float)n / 90.0f;
        }
    }

    @Override
    public boolean canJump() {
        return this.isHorseSaddled();
    }

    @Override
    public void handleStartJump(int n) {
        this.allowStandSliding = true;
        this.makeHorseRear();
        this.playJumpSound();
    }

    @Override
    public void handleStopJump() {
    }

    protected void spawnHorseParticles(boolean bl) {
        BasicParticleType basicParticleType = bl ? ParticleTypes.HEART : ParticleTypes.SMOKE;
        for (int i = 0; i < 7; ++i) {
            double d = this.rand.nextGaussian() * 0.02;
            double d2 = this.rand.nextGaussian() * 0.02;
            double d3 = this.rand.nextGaussian() * 0.02;
            this.world.addParticle(basicParticleType, this.getPosXRandom(1.0), this.getPosYRandom() + 0.5, this.getPosZRandom(1.0), d, d2, d3);
        }
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 7) {
            this.spawnHorseParticles(false);
        } else if (by == 6) {
            this.spawnHorseParticles(true);
        } else {
            super.handleStatusUpdate(by);
        }
    }

    @Override
    public void updatePassenger(Entity entity2) {
        super.updatePassenger(entity2);
        if (entity2 instanceof MobEntity) {
            MobEntity mobEntity = (MobEntity)entity2;
            this.renderYawOffset = mobEntity.renderYawOffset;
        }
        if (this.prevRearingAmount > 0.0f) {
            float f = MathHelper.sin(this.renderYawOffset * ((float)Math.PI / 180));
            float f2 = MathHelper.cos(this.renderYawOffset * ((float)Math.PI / 180));
            float f3 = 0.7f * this.prevRearingAmount;
            float f4 = 0.15f * this.prevRearingAmount;
            entity2.setPosition(this.getPosX() + (double)(f3 * f), this.getPosY() + this.getMountedYOffset() + entity2.getYOffset() + (double)f4, this.getPosZ() - (double)(f3 * f2));
            if (entity2 instanceof LivingEntity) {
                ((LivingEntity)entity2).renderYawOffset = this.renderYawOffset;
            }
        }
    }

    protected float getModifiedMaxHealth() {
        return 15.0f + (float)this.rand.nextInt(8) + (float)this.rand.nextInt(9);
    }

    protected double getModifiedJumpStrength() {
        return (double)0.4f + this.rand.nextDouble() * 0.2 + this.rand.nextDouble() * 0.2 + this.rand.nextDouble() * 0.2;
    }

    protected double getModifiedMovementSpeed() {
        return ((double)0.45f + this.rand.nextDouble() * 0.3 + this.rand.nextDouble() * 0.3 + this.rand.nextDouble() * 0.3) * 0.25;
    }

    @Override
    public boolean isOnLadder() {
        return true;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return entitySize.height * 0.95f;
    }

    public boolean func_230276_fq_() {
        return true;
    }

    public boolean func_230277_fr_() {
        return !this.getItemStackFromSlot(EquipmentSlotType.CHEST).isEmpty();
    }

    public boolean isArmor(ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean replaceItemInInventory(int n, ItemStack itemStack) {
        int n2 = n - 400;
        if (n2 >= 0 && n2 < 2 && n2 < this.horseChest.getSizeInventory()) {
            if (n2 == 0 && itemStack.getItem() != Items.SADDLE) {
                return true;
            }
            if (n2 != 1 || this.func_230276_fq_() && this.isArmor(itemStack)) {
                this.horseChest.setInventorySlotContents(n2, itemStack);
                this.func_230275_fc_();
                return false;
            }
            return true;
        }
        int n3 = n - 500 + 2;
        if (n3 >= 2 && n3 < this.horseChest.getSizeInventory()) {
            this.horseChest.setInventorySlotContents(n3, itemStack);
            return false;
        }
        return true;
    }

    @Override
    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Nullable
    private Vector3d func_234236_a_(Vector3d vector3d, LivingEntity livingEntity) {
        double d = this.getPosX() + vector3d.x;
        double d2 = this.getBoundingBox().minY;
        double d3 = this.getPosZ() + vector3d.z;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        block0: for (Pose pose : livingEntity.getAvailablePoses()) {
            mutable.setPos(d, d2, d3);
            double d4 = this.getBoundingBox().maxY + 0.75;
            do {
                Vector3d vector3d2;
                AxisAlignedBB axisAlignedBB;
                double d5 = this.world.func_242403_h(mutable);
                if ((double)mutable.getY() + d5 > d4) continue block0;
                if (TransportationHelper.func_234630_a_(d5) && TransportationHelper.func_234631_a_(this.world, livingEntity, (axisAlignedBB = livingEntity.getPoseAABB(pose)).offset(vector3d2 = new Vector3d(d, (double)mutable.getY() + d5, d3)))) {
                    livingEntity.setPose(pose);
                    return vector3d2;
                }
                mutable.move(Direction.UP);
            } while ((double)mutable.getY() < d4);
        }
        return null;
    }

    @Override
    public Vector3d func_230268_c_(LivingEntity livingEntity) {
        Vector3d vector3d = AbstractHorseEntity.func_233559_a_(this.getWidth(), livingEntity.getWidth(), this.rotationYaw + (livingEntity.getPrimaryHand() == HandSide.RIGHT ? 90.0f : -90.0f));
        Vector3d vector3d2 = this.func_234236_a_(vector3d, livingEntity);
        if (vector3d2 != null) {
            return vector3d2;
        }
        Vector3d vector3d3 = AbstractHorseEntity.func_233559_a_(this.getWidth(), livingEntity.getWidth(), this.rotationYaw + (livingEntity.getPrimaryHand() == HandSide.LEFT ? 90.0f : -90.0f));
        Vector3d vector3d4 = this.func_234236_a_(vector3d3, livingEntity);
        return vector3d4 != null ? vector3d4 : this.getPositionVec();
    }

    protected void func_230273_eI_() {
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        if (iLivingEntityData == null) {
            iLivingEntityData = new AgeableEntity.AgeableData(0.2f);
        }
        this.func_230273_eI_();
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    private static boolean lambda$static$0(LivingEntity livingEntity) {
        return livingEntity instanceof AbstractHorseEntity && ((AbstractHorseEntity)livingEntity).isBreeding();
    }
}

