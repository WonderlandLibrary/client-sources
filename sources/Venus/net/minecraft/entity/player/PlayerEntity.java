/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import mpp.venusfr.events.EventLivingUpdate;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.misc.AntiPush;
import mpp.venusfr.functions.impl.movement.AutoSprint;
import mpp.venusfr.functions.impl.player.AutoTool;
import mpp.venusfr.venusfr;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffers;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.potion.Effects;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.CommandBlockLogic;
import net.minecraft.tileentity.CommandBlockTileEntity;
import net.minecraft.tileentity.JigsawTileEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.FoodStats;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class PlayerEntity
extends LivingEntity {
    public static final EntitySize STANDING_SIZE = EntitySize.flexible(0.6f, 1.8f);
    private static final Map<Pose, EntitySize> SIZE_BY_POSE = ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.SLEEPING, SLEEPING_SIZE).put(Pose.FALL_FLYING, EntitySize.flexible(0.6f, 0.6f)).put(Pose.SWIMMING, EntitySize.flexible(0.6f, 0.6f)).put(Pose.SPIN_ATTACK, EntitySize.flexible(0.6f, 0.6f)).put(Pose.CROUCHING, EntitySize.flexible(0.6f, 1.5f)).put(Pose.DYING, EntitySize.fixed(0.2f, 0.2f)).build();
    private static final DataParameter<Float> ABSORPTION = EntityDataManager.createKey(PlayerEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> PLAYER_SCORE = EntityDataManager.createKey(PlayerEntity.class, DataSerializers.VARINT);
    protected static final DataParameter<Byte> PLAYER_MODEL_FLAG = EntityDataManager.createKey(PlayerEntity.class, DataSerializers.BYTE);
    protected static final DataParameter<Byte> MAIN_HAND = EntityDataManager.createKey(PlayerEntity.class, DataSerializers.BYTE);
    protected static final DataParameter<CompoundNBT> LEFT_SHOULDER_ENTITY = EntityDataManager.createKey(PlayerEntity.class, DataSerializers.COMPOUND_NBT);
    protected static final DataParameter<CompoundNBT> RIGHT_SHOULDER_ENTITY = EntityDataManager.createKey(PlayerEntity.class, DataSerializers.COMPOUND_NBT);
    private long timeEntitySatOnShoulder;
    public PlayerInventory inventory = new PlayerInventory(this);
    protected EnderChestInventory enterChestInventory = new EnderChestInventory();
    public PlayerContainer container;
    public boolean isBot;
    public Container openContainer;
    protected FoodStats foodStats = new FoodStats();
    protected int flyToggleTimer;
    public float prevCameraYaw;
    public float cameraYaw;
    public int xpCooldown;
    public double prevChasingPosX;
    public double prevChasingPosY;
    public double prevChasingPosZ;
    public double chasingPosX;
    public double chasingPosY;
    public double chasingPosZ;
    private int sleepTimer;
    protected boolean eyesInWaterPlayer;
    public final PlayerAbilities abilities = new PlayerAbilities();
    public int experienceLevel;
    public int experienceTotal;
    public float experience;
    protected int xpSeed;
    public float speedInAir = 0.02f;
    private int lastXPSound;
    private final GameProfile gameProfile;
    private boolean hasReducedDebug;
    private ItemStack itemStackMainHand = ItemStack.EMPTY;
    private final CooldownTracker cooldownTracker = this.createCooldownTracker();
    @Nullable
    public FishingBobberEntity fishingBobber;

    public PlayerEntity(World world, BlockPos blockPos, float f, GameProfile gameProfile) {
        super((EntityType<? extends LivingEntity>)EntityType.PLAYER, world);
        this.setUniqueId(PlayerEntity.getUUID(gameProfile));
        this.gameProfile = gameProfile;
        this.container = new PlayerContainer(this.inventory, !world.isRemote, this);
        this.openContainer = this.container;
        this.setLocationAndAngles((double)blockPos.getX() + 0.5, blockPos.getY() + 1, (double)blockPos.getZ() + 0.5, f, 0.0f);
        this.unused180 = 180.0f;
    }

    public boolean blockActionRestricted(World world, BlockPos blockPos, GameType gameType) {
        if (!gameType.hasLimitedInteractions()) {
            return true;
        }
        if (gameType == GameType.SPECTATOR) {
            return false;
        }
        if (this.isAllowEdit()) {
            return true;
        }
        ItemStack itemStack = this.getHeldItemMainhand();
        return itemStack.isEmpty() || !itemStack.canDestroy(world.getTags(), new CachedBlockInfo(world, blockPos, false));
    }

    public static AttributeModifierMap.MutableAttribute func_234570_el_() {
        return LivingEntity.registerAttributes().createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.1f).createMutableAttribute(Attributes.ATTACK_SPEED).createMutableAttribute(Attributes.LUCK);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(ABSORPTION, Float.valueOf(0.0f));
        this.dataManager.register(PLAYER_SCORE, 0);
        this.dataManager.register(PLAYER_MODEL_FLAG, (byte)0);
        this.dataManager.register(MAIN_HAND, (byte)1);
        this.dataManager.register(LEFT_SHOULDER_ENTITY, new CompoundNBT());
        this.dataManager.register(RIGHT_SHOULDER_ENTITY, new CompoundNBT());
    }

    @Override
    public void tick() {
        this.noClip = this.isSpectator();
        EventLivingUpdate eventLivingUpdate = new EventLivingUpdate();
        venusfr.getInstance().getEventBus().post(eventLivingUpdate);
        if (eventLivingUpdate.isCancel()) {
            eventLivingUpdate.open();
            return;
        }
        if (this.isSpectator()) {
            this.onGround = false;
        }
        if (this.xpCooldown > 0) {
            --this.xpCooldown;
        }
        if (this.isSleeping()) {
            ++this.sleepTimer;
            if (this.sleepTimer > 100) {
                this.sleepTimer = 100;
            }
            if (!this.world.isRemote && this.world.isDaytime()) {
                this.stopSleepInBed(false, false);
            }
        } else if (this.sleepTimer > 0) {
            ++this.sleepTimer;
            if (this.sleepTimer >= 110) {
                this.sleepTimer = 0;
            }
        }
        this.updateEyesInWaterPlayer();
        super.tick();
        if (!this.world.isRemote && this.openContainer != null && !this.openContainer.canInteractWith(this)) {
            this.closeScreen();
            this.openContainer = this.container;
        }
        this.updateCape();
        if (!this.world.isRemote) {
            this.foodStats.tick(this);
            this.addStat(Stats.PLAY_ONE_MINUTE);
            if (this.isAlive()) {
                this.addStat(Stats.TIME_SINCE_DEATH);
            }
            if (this.isDiscrete()) {
                this.addStat(Stats.SNEAK_TIME);
            }
            if (!this.isSleeping()) {
                this.addStat(Stats.TIME_SINCE_REST);
            }
        }
        int n = 29999999;
        double d = MathHelper.clamp(this.getPosX(), -2.9999999E7, 2.9999999E7);
        double d2 = MathHelper.clamp(this.getPosZ(), -2.9999999E7, 2.9999999E7);
        if (d != this.getPosX() || d2 != this.getPosZ()) {
            this.setPosition(d, this.getPosY(), d2);
        }
        ++this.ticksSinceLastSwing;
        ItemStack itemStack = this.getHeldItemMainhand();
        if (!ItemStack.areItemStacksEqual(this.itemStackMainHand, itemStack)) {
            if (!ItemStack.areItemsEqualIgnoreDurability(this.itemStackMainHand, itemStack)) {
                this.resetCooldown();
            }
            this.itemStackMainHand = itemStack.copy();
        }
        this.updateTurtleHelmet();
        this.cooldownTracker.tick();
        this.updatePose();
    }

    public boolean isSecondaryUseActive() {
        return this.isSneaking();
    }

    protected boolean wantsToStopRiding() {
        return this.isSneaking();
    }

    protected boolean isStayingOnGroundSurface() {
        return this.isSneaking();
    }

    protected boolean updateEyesInWaterPlayer() {
        this.eyesInWaterPlayer = this.areEyesInFluid(FluidTags.WATER);
        return this.eyesInWaterPlayer;
    }

    private void updateTurtleHelmet() {
        ItemStack itemStack = this.getItemStackFromSlot(EquipmentSlotType.HEAD);
        if (itemStack.getItem() == Items.TURTLE_HELMET && !this.areEyesInFluid(FluidTags.WATER)) {
            this.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 200, 0, false, false, true));
        }
    }

    protected CooldownTracker createCooldownTracker() {
        return new CooldownTracker();
    }

    private void updateCape() {
        this.prevChasingPosX = this.chasingPosX;
        this.prevChasingPosY = this.chasingPosY;
        this.prevChasingPosZ = this.chasingPosZ;
        double d = this.getPosX() - this.chasingPosX;
        double d2 = this.getPosY() - this.chasingPosY;
        double d3 = this.getPosZ() - this.chasingPosZ;
        double d4 = 10.0;
        if (d > 10.0) {
            this.prevChasingPosX = this.chasingPosX = this.getPosX();
        }
        if (d3 > 10.0) {
            this.prevChasingPosZ = this.chasingPosZ = this.getPosZ();
        }
        if (d2 > 10.0) {
            this.prevChasingPosY = this.chasingPosY = this.getPosY();
        }
        if (d < -10.0) {
            this.prevChasingPosX = this.chasingPosX = this.getPosX();
        }
        if (d3 < -10.0) {
            this.prevChasingPosZ = this.chasingPosZ = this.getPosZ();
        }
        if (d2 < -10.0) {
            this.prevChasingPosY = this.chasingPosY = this.getPosY();
        }
        this.chasingPosX += d * 0.25;
        this.chasingPosZ += d3 * 0.25;
        this.chasingPosY += d2 * 0.25;
    }

    protected void updatePose() {
        if (this.isPoseClear(Pose.SWIMMING)) {
            Pose pose = this.isElytraFlying() ? Pose.FALL_FLYING : (this.isSleeping() ? Pose.SLEEPING : (this.isSwimming() ? Pose.SWIMMING : (this.isSpinAttacking() ? Pose.SPIN_ATTACK : (this.isSneaking() && !this.abilities.isFlying ? Pose.CROUCHING : Pose.STANDING))));
            Pose pose2 = !(this.isSpectator() || this.isPassenger() || this.isPoseClear(pose)) ? (this.isPoseClear(Pose.CROUCHING) ? Pose.CROUCHING : Pose.SWIMMING) : pose;
            this.setPose(pose2);
        }
    }

    @Override
    public int getMaxInPortalTime() {
        return this.abilities.disableDamage ? 1 : 80;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_PLAYER_SWIM;
    }

    @Override
    protected SoundEvent getSplashSound() {
        return SoundEvents.ENTITY_PLAYER_SPLASH;
    }

    @Override
    protected SoundEvent getHighspeedSplashSound() {
        return SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED;
    }

    @Override
    public int getPortalCooldown() {
        return 1;
    }

    @Override
    public void playSound(SoundEvent soundEvent, float f, float f2) {
        this.world.playSound(this, this.getPosX(), this.getPosY(), this.getPosZ(), soundEvent, this.getSoundCategory(), f, f2);
    }

    public void playSound(SoundEvent soundEvent, SoundCategory soundCategory, float f, float f2) {
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.PLAYERS;
    }

    @Override
    protected int getFireImmuneTicks() {
        return 1;
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 9) {
            this.onItemUseFinish();
        } else if (by == 23) {
            this.hasReducedDebug = false;
        } else if (by == 22) {
            this.hasReducedDebug = true;
        } else if (by == 43) {
            this.addParticlesAroundSelf(ParticleTypes.CLOUD);
        } else {
            super.handleStatusUpdate(by);
        }
    }

    private void addParticlesAroundSelf(IParticleData iParticleData) {
        for (int i = 0; i < 5; ++i) {
            double d = this.rand.nextGaussian() * 0.02;
            double d2 = this.rand.nextGaussian() * 0.02;
            double d3 = this.rand.nextGaussian() * 0.02;
            this.world.addParticle(iParticleData, this.getPosXRandom(1.0), this.getPosYRandom() + 1.0, this.getPosZRandom(1.0), d, d2, d3);
        }
    }

    protected void closeScreen() {
        this.openContainer = this.container;
    }

    @Override
    public void updateRidden() {
        if (this.wantsToStopRiding() && this.isPassenger()) {
            this.stopRiding();
            this.setSneaking(true);
        } else {
            double d = this.getPosX();
            double d2 = this.getPosY();
            double d3 = this.getPosZ();
            super.updateRidden();
            this.prevCameraYaw = this.cameraYaw;
            this.cameraYaw = 0.0f;
            this.addMountedMovementStat(this.getPosX() - d, this.getPosY() - d2, this.getPosZ() - d3);
        }
    }

    @Override
    public void preparePlayerToSpawn() {
        this.setPose(Pose.STANDING);
        super.preparePlayerToSpawn();
        this.setHealth(this.getMaxHealth());
        this.deathTime = 0;
    }

    @Override
    protected void updateEntityActionState() {
        super.updateEntityActionState();
        this.updateArmSwingProgress();
        this.rotationYawHead = this.rotationYaw;
        this.rotationPitchHead = this.rotationPitch;
    }

    @Override
    public void livingTick() {
        if (this.flyToggleTimer > 0) {
            --this.flyToggleTimer;
        }
        if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION)) {
            if (this.getHealth() < this.getMaxHealth() && this.ticksExisted % 20 == 0) {
                this.heal(1.0f);
            }
            if (this.foodStats.needFood() && this.ticksExisted % 10 == 0) {
                this.foodStats.setFoodLevel(this.foodStats.getFoodLevel() + 1);
            }
        }
        this.inventory.tick();
        this.prevCameraYaw = this.cameraYaw;
        super.livingTick();
        this.jumpMovementFactor = 0.02f;
        if (this.isSprinting()) {
            this.jumpMovementFactor = (float)((double)this.jumpMovementFactor + 0.005999999865889549);
        }
        this.setAIMoveSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
        float f = this.onGround && !this.getShouldBeDead() && !this.isSwimming() ? Math.min(0.1f, MathHelper.sqrt(PlayerEntity.horizontalMag(this.getMotion()))) : 0.0f;
        this.cameraYaw += (f - this.cameraYaw) * 0.4f;
        if (this.getHealth() > 0.0f && !this.isSpectator()) {
            AxisAlignedBB axisAlignedBB = this.isPassenger() && !this.getRidingEntity().removed ? this.getBoundingBox().union(this.getRidingEntity().getBoundingBox()).grow(1.0, 0.0, 1.0) : this.getBoundingBox().grow(1.0, 0.5, 1.0);
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisAlignedBB);
            for (int i = 0; i < list.size(); ++i) {
                Entity entity2 = list.get(i);
                if (entity2.removed) continue;
                this.collideWithPlayer(entity2);
            }
        }
        this.playShoulderEntityAmbientSound(this.getLeftShoulderEntity());
        this.playShoulderEntityAmbientSound(this.getRightShoulderEntity());
        if (!this.world.isRemote && (this.fallDistance > 0.5f || this.isInWater()) || this.abilities.isFlying || this.isSleeping()) {
            this.spawnShoulderEntities();
        }
    }

    private void playShoulderEntityAmbientSound(@Nullable CompoundNBT compoundNBT) {
        if (!(compoundNBT == null || compoundNBT.contains("Silent") && compoundNBT.getBoolean("Silent") || this.world.rand.nextInt(200) != 0)) {
            String string = compoundNBT.getString("id");
            EntityType.byKey(string).filter(PlayerEntity::lambda$playShoulderEntityAmbientSound$0).ifPresent(this::lambda$playShoulderEntityAmbientSound$1);
        }
    }

    private void collideWithPlayer(Entity entity2) {
        entity2.onCollideWithPlayer(this);
    }

    public int getScore() {
        return this.dataManager.get(PLAYER_SCORE);
    }

    public void setScore(int n) {
        this.dataManager.set(PLAYER_SCORE, n);
    }

    public void addScore(int n) {
        int n2 = this.getScore();
        this.dataManager.set(PLAYER_SCORE, n2 + n);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        this.recenterBoundingBox();
        if (!this.isSpectator()) {
            this.spawnDrops(damageSource);
        }
        if (damageSource != null) {
            this.setMotion(-MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * ((float)Math.PI / 180)) * 0.1f, 0.1f, -MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * ((float)Math.PI / 180)) * 0.1f);
        } else {
            this.setMotion(0.0, 0.1, 0.0);
        }
        this.addStat(Stats.DEATHS);
        this.takeStat(Stats.CUSTOM.get(Stats.TIME_SINCE_DEATH));
        this.takeStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
        this.extinguish();
        this.setFlag(0, true);
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (!this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
            this.destroyVanishingCursedItems();
            this.inventory.dropAllItems();
        }
    }

    protected void destroyVanishingCursedItems() {
        for (int i = 0; i < this.inventory.getSizeInventory(); ++i) {
            ItemStack itemStack = this.inventory.getStackInSlot(i);
            if (itemStack.isEmpty() || !EnchantmentHelper.hasVanishingCurse(itemStack)) continue;
            this.inventory.removeStackFromSlot(i);
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (damageSource == DamageSource.ON_FIRE) {
            return SoundEvents.ENTITY_PLAYER_HURT_ON_FIRE;
        }
        if (damageSource == DamageSource.DROWN) {
            return SoundEvents.ENTITY_PLAYER_HURT_DROWN;
        }
        return damageSource == DamageSource.SWEET_BERRY_BUSH ? SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH : SoundEvents.ENTITY_PLAYER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PLAYER_DEATH;
    }

    public boolean drop(boolean bl) {
        return this.dropItem(this.inventory.decrStackSize(this.inventory.currentItem, bl && !this.inventory.getCurrentItem().isEmpty() ? this.inventory.getCurrentItem().getCount() : 1), false, false) != null;
    }

    @Nullable
    public ItemEntity dropItem(ItemStack itemStack, boolean bl) {
        return this.dropItem(itemStack, false, bl);
    }

    @Nullable
    public ItemEntity dropItem(ItemStack itemStack, boolean bl, boolean bl2) {
        if (itemStack.isEmpty()) {
            return null;
        }
        if (this.world.isRemote) {
            this.swingArm(Hand.MAIN_HAND);
        }
        double d = this.getPosYEye() - (double)0.3f;
        ItemEntity itemEntity = new ItemEntity(this.world, this.getPosX(), d, this.getPosZ(), itemStack);
        itemEntity.setPickupDelay(40);
        if (bl2) {
            itemEntity.setThrowerId(this.getUniqueID());
        }
        if (bl) {
            float f = this.rand.nextFloat() * 0.5f;
            float f2 = this.rand.nextFloat() * ((float)Math.PI * 2);
            itemEntity.setMotion(-MathHelper.sin(f2) * f, 0.2f, MathHelper.cos(f2) * f);
        } else {
            float f = 0.3f;
            float f3 = MathHelper.sin(this.rotationPitch * ((float)Math.PI / 180));
            float f4 = MathHelper.cos(this.rotationPitch * ((float)Math.PI / 180));
            float f5 = MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180));
            float f6 = MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180));
            float f7 = this.rand.nextFloat() * ((float)Math.PI * 2);
            float f8 = 0.02f * this.rand.nextFloat();
            itemEntity.setMotion((double)(-f5 * f4 * 0.3f) + Math.cos(f7) * (double)f8, -f3 * 0.3f + 0.1f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f, (double)(f6 * f4 * 0.3f) + Math.sin(f7) * (double)f8);
        }
        return itemEntity;
    }

    public float getDigSpeed(BlockState blockState) {
        float f = this.inventory.getDestroySpeed(blockState);
        if (f > 1.0f) {
            int n = EnchantmentHelper.getEfficiencyModifier(this);
            ItemStack itemStack = this.getHeldItemMainhand();
            if (n > 0 && !itemStack.isEmpty()) {
                f += (float)(n * n + 1);
            }
        }
        if (EffectUtils.hasMiningSpeedup(this)) {
            f *= 1.0f + (float)(EffectUtils.getMiningSpeedup(this) + 1) * 0.2f;
        }
        if (this.isPotionActive(Effects.MINING_FATIGUE)) {
            f *= (switch (this.getActivePotionEffect(Effects.MINING_FATIGUE).getAmplifier()) {
                case 0 -> 0.3f;
                case 1 -> 0.09f;
                case 2 -> 0.0027f;
                default -> 8.1E-4f;
            });
        }
        if (this.areEyesInFluid(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(this)) {
            f /= 5.0f;
        }
        if (!this.onGround) {
            f /= 5.0f;
        }
        return f;
    }

    public float getDigSpeed(BlockState blockState, ItemStack itemStack, int n) {
        int n2;
        float f = this.inventory.getDestroySpeed(blockState, n);
        if (f > 1.0f && (n2 = EnchantmentHelper.getEfficiencyModifier(this)) > 0 && !itemStack.isEmpty()) {
            f += (float)(n2 * n2 + 1);
        }
        if (EffectUtils.hasMiningSpeedup(this)) {
            f *= 1.0f + (float)(EffectUtils.getMiningSpeedup(this) + 1) * 0.2f;
        }
        if (this.isPotionActive(Effects.MINING_FATIGUE)) {
            f *= (switch (this.getActivePotionEffect(Effects.MINING_FATIGUE).getAmplifier()) {
                case 0 -> 0.3f;
                case 1 -> 0.09f;
                case 2 -> 0.0027f;
                default -> 8.1E-4f;
            });
        }
        if (this.areEyesInFluid(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(this)) {
            f /= 5.0f;
        }
        if (!this.onGround) {
            f /= 5.0f;
        }
        return f;
    }

    public boolean func_234569_d_(BlockState blockState) {
        AutoTool autoTool = venusfr.getInstance().getFunctionRegistry().getAutoTool();
        return !blockState.getRequiresTool() || (autoTool.isState() && (Boolean)autoTool.silent.get() != false && autoTool.itemIndex != -1 ? this.inventory.getStackInSlot(autoTool.itemIndex).canHarvestBlock(blockState) : this.inventory.getCurrentItem().canHarvestBlock(blockState));
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setUniqueId(PlayerEntity.getUUID(this.gameProfile));
        ListNBT listNBT = compoundNBT.getList("Inventory", 10);
        this.inventory.read(listNBT);
        this.inventory.currentItem = compoundNBT.getInt("SelectedItemSlot");
        this.sleepTimer = compoundNBT.getShort("SleepTimer");
        this.experience = compoundNBT.getFloat("XpP");
        this.experienceLevel = compoundNBT.getInt("XpLevel");
        this.experienceTotal = compoundNBT.getInt("XpTotal");
        this.xpSeed = compoundNBT.getInt("XpSeed");
        if (this.xpSeed == 0) {
            this.xpSeed = this.rand.nextInt();
        }
        this.setScore(compoundNBT.getInt("Score"));
        this.foodStats.read(compoundNBT);
        this.abilities.read(compoundNBT);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.abilities.getWalkSpeed());
        if (compoundNBT.contains("EnderItems", 0)) {
            this.enterChestInventory.read(compoundNBT.getList("EnderItems", 10));
        }
        if (compoundNBT.contains("ShoulderEntityLeft", 1)) {
            this.setLeftShoulderEntity(compoundNBT.getCompound("ShoulderEntityLeft"));
        }
        if (compoundNBT.contains("ShoulderEntityRight", 1)) {
            this.setRightShoulderEntity(compoundNBT.getCompound("ShoulderEntityRight"));
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("DataVersion", SharedConstants.getVersion().getWorldVersion());
        compoundNBT.put("Inventory", this.inventory.write(new ListNBT()));
        compoundNBT.putInt("SelectedItemSlot", this.inventory.currentItem);
        compoundNBT.putShort("SleepTimer", (short)this.sleepTimer);
        compoundNBT.putFloat("XpP", this.experience);
        compoundNBT.putInt("XpLevel", this.experienceLevel);
        compoundNBT.putInt("XpTotal", this.experienceTotal);
        compoundNBT.putInt("XpSeed", this.xpSeed);
        compoundNBT.putInt("Score", this.getScore());
        this.foodStats.write(compoundNBT);
        this.abilities.write(compoundNBT);
        compoundNBT.put("EnderItems", this.enterChestInventory.write());
        if (!this.getLeftShoulderEntity().isEmpty()) {
            compoundNBT.put("ShoulderEntityLeft", this.getLeftShoulderEntity());
        }
        if (!this.getRightShoulderEntity().isEmpty()) {
            compoundNBT.put("ShoulderEntityRight", this.getRightShoulderEntity());
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (super.isInvulnerableTo(damageSource)) {
            return false;
        }
        if (damageSource == DamageSource.DROWN) {
            return !this.world.getGameRules().getBoolean(GameRules.DROWNING_DAMAGE);
        }
        if (damageSource == DamageSource.FALL) {
            return !this.world.getGameRules().getBoolean(GameRules.FALL_DAMAGE);
        }
        if (damageSource.isFireDamage()) {
            return !this.world.getGameRules().getBoolean(GameRules.FIRE_DAMAGE);
        }
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        if (this.abilities.disableDamage && !damageSource.canHarmInCreative()) {
            return true;
        }
        this.idleTime = 0;
        if (this.getShouldBeDead()) {
            return true;
        }
        this.spawnShoulderEntities();
        if (damageSource.isDifficultyScaled()) {
            if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
                f = 0.0f;
            }
            if (this.world.getDifficulty() == Difficulty.EASY) {
                f = Math.min(f / 2.0f + 1.0f, f);
            }
            if (this.world.getDifficulty() == Difficulty.HARD) {
                f = f * 3.0f / 2.0f;
            }
        }
        return f == 0.0f ? false : super.attackEntityFrom(damageSource, f);
    }

    @Override
    protected void blockUsingShield(LivingEntity livingEntity) {
        super.blockUsingShield(livingEntity);
        if (livingEntity.getHeldItemMainhand().getItem() instanceof AxeItem) {
            this.disableShield(false);
        }
    }

    public boolean canAttackPlayer(PlayerEntity playerEntity) {
        Team team = this.getTeam();
        Team team2 = playerEntity.getTeam();
        if (team == null) {
            return false;
        }
        return !team.isSameTeam(team2) ? true : team.getAllowFriendlyFire();
    }

    @Override
    protected void damageArmor(DamageSource damageSource, float f) {
        this.inventory.func_234563_a_(damageSource, f);
    }

    @Override
    protected void damageShield(float f) {
        if (this.activeItemStack.getItem() == Items.SHIELD) {
            if (!this.world.isRemote) {
                this.addStat(Stats.ITEM_USED.get(this.activeItemStack.getItem()));
            }
            if (f >= 3.0f) {
                int n = 1 + MathHelper.floor(f);
                Hand hand = this.getActiveHand();
                this.activeItemStack.damageItem(n, this, arg_0 -> PlayerEntity.lambda$damageShield$2(hand, arg_0));
                if (this.activeItemStack.isEmpty()) {
                    if (hand == Hand.MAIN_HAND) {
                        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
                    } else {
                        this.setItemStackToSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
                    }
                    this.activeItemStack = ItemStack.EMPTY;
                    this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8f, 0.8f + this.world.rand.nextFloat() * 0.4f);
                }
            }
        }
    }

    @Override
    protected void damageEntity(DamageSource damageSource, float f) {
        if (!this.isInvulnerableTo(damageSource)) {
            f = this.applyArmorCalculations(damageSource, f);
            f = this.applyPotionDamageCalculations(damageSource, f);
            float f2 = Math.max(f - this.getAbsorptionAmount(), 0.0f);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (f - f2));
            float f3 = f - f2;
            if (f3 > 0.0f && f3 < 3.4028235E37f) {
                this.addStat(Stats.DAMAGE_ABSORBED, Math.round(f3 * 10.0f));
            }
            if (f2 != 0.0f) {
                this.addExhaustion(damageSource.getHungerDamage());
                float f4 = this.getHealth();
                this.setHealth(this.getHealth() - f2);
                this.getCombatTracker().trackDamage(damageSource, f4, f2);
                if (f2 < 3.4028235E37f) {
                    this.addStat(Stats.DAMAGE_TAKEN, Math.round(f2 * 10.0f));
                }
            }
        }
    }

    @Override
    protected boolean func_230296_cM_() {
        return !this.abilities.isFlying && super.func_230296_cM_();
    }

    public void openSignEditor(SignTileEntity signTileEntity) {
    }

    public void openMinecartCommandBlock(CommandBlockLogic commandBlockLogic) {
    }

    public void openCommandBlock(CommandBlockTileEntity commandBlockTileEntity) {
    }

    public void openStructureBlock(StructureBlockTileEntity structureBlockTileEntity) {
    }

    public void openJigsaw(JigsawTileEntity jigsawTileEntity) {
    }

    public void openHorseInventory(AbstractHorseEntity abstractHorseEntity, IInventory iInventory) {
    }

    public OptionalInt openContainer(@Nullable INamedContainerProvider iNamedContainerProvider) {
        return OptionalInt.empty();
    }

    public void openMerchantContainer(int n, MerchantOffers merchantOffers, int n2, int n3, boolean bl, boolean bl2) {
    }

    public void openBook(ItemStack itemStack, Hand hand) {
    }

    public ActionResultType interactOn(Entity entity2, Hand hand) {
        if (this.isSpectator()) {
            if (entity2 instanceof INamedContainerProvider) {
                this.openContainer((INamedContainerProvider)((Object)entity2));
            }
            return ActionResultType.PASS;
        }
        ItemStack itemStack = this.getHeldItem(hand);
        ItemStack itemStack2 = itemStack.copy();
        ActionResultType actionResultType = entity2.processInitialInteract(this, hand);
        if (actionResultType.isSuccessOrConsume()) {
            if (this.abilities.isCreativeMode && itemStack == this.getHeldItem(hand) && itemStack.getCount() < itemStack2.getCount()) {
                itemStack.setCount(itemStack2.getCount());
            }
            return actionResultType;
        }
        if (!itemStack.isEmpty() && entity2 instanceof LivingEntity) {
            ActionResultType actionResultType2;
            if (this.abilities.isCreativeMode) {
                itemStack = itemStack2;
            }
            if ((actionResultType2 = itemStack.interactWithEntity(this, (LivingEntity)entity2, hand)).isSuccessOrConsume()) {
                if (itemStack.isEmpty() && !this.abilities.isCreativeMode) {
                    this.setHeldItem(hand, ItemStack.EMPTY);
                }
                return actionResultType2;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public double getYOffset() {
        return -0.35;
    }

    @Override
    public void dismount() {
        super.dismount();
        this.rideCooldown = 0;
    }

    @Override
    protected boolean isMovementBlocked() {
        return super.isMovementBlocked() || this.isSleeping();
    }

    @Override
    public boolean func_241208_cS_() {
        return !this.abilities.isFlying;
    }

    @Override
    protected Vector3d maybeBackOffFromEdge(Vector3d vector3d, MoverType moverType) {
        if (!this.abilities.isFlying && (moverType == MoverType.SELF || moverType == MoverType.PLAYER) && this.isStayingOnGroundSurface() && this.func_242375_q()) {
            double d = vector3d.x;
            double d2 = vector3d.z;
            double d3 = 0.05;
            while (d != 0.0 && this.world.hasNoCollisions(this, this.getBoundingBox().offset(d, -this.stepHeight, 0.0))) {
                if (d < 0.05 && d >= -0.05) {
                    d = 0.0;
                    continue;
                }
                if (d > 0.0) {
                    d -= 0.05;
                    continue;
                }
                d += 0.05;
            }
            while (d2 != 0.0 && this.world.hasNoCollisions(this, this.getBoundingBox().offset(0.0, -this.stepHeight, d2))) {
                if (d2 < 0.05 && d2 >= -0.05) {
                    d2 = 0.0;
                    continue;
                }
                if (d2 > 0.0) {
                    d2 -= 0.05;
                    continue;
                }
                d2 += 0.05;
            }
            while (d != 0.0 && d2 != 0.0 && this.world.hasNoCollisions(this, this.getBoundingBox().offset(d, -this.stepHeight, d2))) {
                d = d < 0.05 && d >= -0.05 ? 0.0 : (d > 0.0 ? (d -= 0.05) : (d += 0.05));
                if (d2 < 0.05 && d2 >= -0.05) {
                    d2 = 0.0;
                    continue;
                }
                if (d2 > 0.0) {
                    d2 -= 0.05;
                    continue;
                }
                d2 += 0.05;
            }
            vector3d = new Vector3d(d, vector3d.y, d2);
        }
        return vector3d;
    }

    private boolean func_242375_q() {
        return this.onGround || this.fallDistance < this.stepHeight && !this.world.hasNoCollisions(this, this.getBoundingBox().offset(0.0, this.fallDistance - this.stepHeight, 0.0));
    }

    public void attackTargetEntityWithCurrentItem(Entity entity2) {
        if (entity2.canBeAttackedWithItem() && !entity2.hitByEntity(this)) {
            float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float f2 = entity2 instanceof LivingEntity ? EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((LivingEntity)entity2).getCreatureAttribute()) : EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), CreatureAttribute.UNDEFINED);
            float f3 = this.getCooledAttackStrength(0.5f);
            f2 *= f3;
            this.resetCooldown();
            if ((f *= 0.2f + f3 * f3 * 0.8f) > 0.0f || f2 > 0.0f) {
                ItemStack itemStack;
                boolean bl = f3 > 0.9f;
                boolean bl2 = false;
                int n = 0;
                n += EnchantmentHelper.getKnockbackModifier(this);
                if (this.isSprinting() && bl) {
                    this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, this.getSoundCategory(), 1.0f, 1.0f);
                    ++n;
                    bl2 = true;
                }
                boolean bl3 = bl && this.fallDistance > 0.0f && !this.onGround && !this.isOnLadder() && !this.isInWater() && !this.isPotionActive(Effects.BLINDNESS) && !this.isPassenger() && entity2 instanceof LivingEntity;
                boolean bl4 = bl3 = bl3 && !this.isSprinting();
                if (bl3) {
                    f *= 1.5f;
                }
                f += f2;
                boolean bl5 = false;
                double d = this.distanceWalkedModified - this.prevDistanceWalkedModified;
                if (bl && !bl3 && !bl2 && this.onGround && d < (double)this.getAIMoveSpeed() && (itemStack = this.getHeldItem(Hand.MAIN_HAND)).getItem() instanceof SwordItem) {
                    bl5 = true;
                }
                float f4 = 0.0f;
                boolean bl6 = false;
                int n2 = EnchantmentHelper.getFireAspectModifier(this);
                if (entity2 instanceof LivingEntity) {
                    f4 = ((LivingEntity)entity2).getHealth();
                    if (n2 > 0 && !entity2.isBurning()) {
                        bl6 = true;
                        entity2.setFire(1);
                    }
                }
                Vector3d vector3d = entity2.getMotion();
                boolean bl7 = entity2.attackEntityFrom(DamageSource.causePlayerDamage(this), f);
                if (bl7) {
                    Object object;
                    Object object2;
                    if (n > 0) {
                        boolean bl8;
                        if (entity2 instanceof LivingEntity) {
                            ((LivingEntity)entity2).applyKnockback((float)n * 0.5f, MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180)), -MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180)));
                        } else {
                            entity2.addVelocity(-MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180)) * (float)n * 0.5f, 0.1, MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180)) * (float)n * 0.5f);
                        }
                        object2 = venusfr.getInstance().getFunctionRegistry();
                        object = ((FunctionRegistry)object2).getAutoSprint();
                        boolean bl9 = bl8 = ((Function)object).isState() && (Boolean)((AutoSprint)object).saveSprint.get() != false && !Minecraft.getInstance().player.isInWater() && this instanceof ClientPlayerEntity;
                        if (!bl8) {
                            this.setMotion(this.getMotion().mul(0.6, 1.0, 0.6));
                            this.setSprinting(true);
                        }
                    }
                    if (bl5) {
                        float f5 = 1.0f + EnchantmentHelper.getSweepingDamageRatio(this) * f;
                        for (LivingEntity livingEntity : this.world.getEntitiesWithinAABB(LivingEntity.class, entity2.getBoundingBox().grow(1.0, 0.25, 1.0))) {
                            if (livingEntity == this || livingEntity == entity2 || this.isOnSameTeam(livingEntity) || livingEntity instanceof ArmorStandEntity && ((ArmorStandEntity)livingEntity).hasMarker() || !(this.getDistanceSq(livingEntity) < 9.0)) continue;
                            livingEntity.applyKnockback(0.4f, MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180)), -MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180)));
                            livingEntity.attackEntityFrom(DamageSource.causePlayerDamage(this), f5);
                        }
                        this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, this.getSoundCategory(), 1.0f, 1.0f);
                        this.spawnSweepParticles();
                    }
                    if (entity2 instanceof ServerPlayerEntity && entity2.velocityChanged) {
                        ((ServerPlayerEntity)entity2).connection.sendPacket(new SEntityVelocityPacket(entity2));
                        entity2.velocityChanged = false;
                        entity2.setMotion(vector3d);
                    }
                    if (bl3) {
                        this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, this.getSoundCategory(), 1.0f, 1.0f);
                        this.onCriticalHit(entity2);
                    }
                    if (!bl3 && !bl5) {
                        if (bl) {
                            this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, this.getSoundCategory(), 1.0f, 1.0f);
                        } else {
                            this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, this.getSoundCategory(), 1.0f, 1.0f);
                        }
                    }
                    if (f2 > 0.0f) {
                        this.onEnchantmentCritical(entity2);
                    }
                    this.setLastAttackedEntity(entity2);
                    if (entity2 instanceof LivingEntity) {
                        EnchantmentHelper.applyThornEnchantments((LivingEntity)entity2, this);
                    }
                    EnchantmentHelper.applyArthropodEnchantments(this, entity2);
                    object2 = this.getHeldItemMainhand();
                    object = entity2;
                    if (entity2 instanceof EnderDragonPartEntity) {
                        object = ((EnderDragonPartEntity)entity2).dragon;
                    }
                    if (!this.world.isRemote && !((ItemStack)object2).isEmpty() && object instanceof LivingEntity) {
                        ((ItemStack)object2).hitEntity((LivingEntity)object, this);
                        if (((ItemStack)object2).isEmpty()) {
                            this.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
                        }
                    }
                    if (entity2 instanceof LivingEntity) {
                        float f6 = f4 - ((LivingEntity)entity2).getHealth();
                        this.addStat(Stats.DAMAGE_DEALT, Math.round(f6 * 10.0f));
                        if (n2 > 0) {
                            entity2.setFire(n2 * 4);
                        }
                        if (this.world instanceof ServerWorld && f6 > 2.0f) {
                            int n3 = (int)((double)f6 * 0.5);
                            ((ServerWorld)this.world).spawnParticle(ParticleTypes.DAMAGE_INDICATOR, entity2.getPosX(), entity2.getPosYHeight(0.5), entity2.getPosZ(), n3, 0.1, 0.0, 0.1, 0.2);
                        }
                    }
                    this.addExhaustion(0.1f);
                } else {
                    this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, this.getSoundCategory(), 1.0f, 1.0f);
                    if (bl6) {
                        entity2.extinguish();
                    }
                }
            }
        }
    }

    @Override
    protected void spinAttack(LivingEntity livingEntity) {
        this.attackTargetEntityWithCurrentItem(livingEntity);
    }

    public void disableShield(boolean bl) {
        float f = 0.25f + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05f;
        if (bl) {
            f += 0.75f;
        }
        if (this.rand.nextFloat() < f) {
            this.getCooldownTracker().setCooldown(Items.SHIELD, 100);
            this.resetActiveHand();
            this.world.setEntityState(this, (byte)30);
        }
    }

    public void onCriticalHit(Entity entity2) {
    }

    public void onEnchantmentCritical(Entity entity2) {
    }

    public void spawnSweepParticles() {
        double d = -MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180));
        double d2 = MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180));
        if (this.world instanceof ServerWorld) {
            ((ServerWorld)this.world).spawnParticle(ParticleTypes.SWEEP_ATTACK, this.getPosX() + d, this.getPosYHeight(0.5), this.getPosZ() + d2, 0, d, 0.0, d2, 0.0);
        }
    }

    public void respawnPlayer() {
    }

    @Override
    public void remove() {
        super.remove();
        this.container.onContainerClosed(this);
        if (this.openContainer != null) {
            this.openContainer.onContainerClosed(this);
        }
    }

    public boolean isUser() {
        return true;
    }

    public GameProfile getGameProfile() {
        return this.gameProfile;
    }

    public Either<SleepResult, Unit> trySleep(BlockPos blockPos) {
        this.startSleeping(blockPos);
        this.sleepTimer = 0;
        return Either.right(Unit.INSTANCE);
    }

    public void stopSleepInBed(boolean bl, boolean bl2) {
        super.wakeUp();
        if (this.world instanceof ServerWorld && bl2) {
            ((ServerWorld)this.world).updateAllPlayersSleepingFlag();
        }
        this.sleepTimer = bl ? 0 : 100;
    }

    @Override
    public void wakeUp() {
        this.stopSleepInBed(true, false);
    }

    public static Optional<Vector3d> func_242374_a(ServerWorld serverWorld, BlockPos blockPos, float f, boolean bl, boolean bl2) {
        BlockState blockState = serverWorld.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block instanceof RespawnAnchorBlock && blockState.get(RespawnAnchorBlock.CHARGES) > 0 && RespawnAnchorBlock.doesRespawnAnchorWork(serverWorld)) {
            Optional<Vector3d> optional = RespawnAnchorBlock.findRespawnPoint(EntityType.PLAYER, serverWorld, blockPos);
            if (!bl2 && optional.isPresent()) {
                serverWorld.setBlockState(blockPos, (BlockState)blockState.with(RespawnAnchorBlock.CHARGES, blockState.get(RespawnAnchorBlock.CHARGES) - 1), 0);
            }
            return optional;
        }
        if (block instanceof BedBlock && BedBlock.doesBedWork(serverWorld)) {
            return BedBlock.func_242652_a(EntityType.PLAYER, serverWorld, blockPos, f);
        }
        if (!bl) {
            return Optional.empty();
        }
        boolean bl3 = block.canSpawnInBlock();
        boolean bl4 = serverWorld.getBlockState(blockPos.up()).getBlock().canSpawnInBlock();
        return bl3 && bl4 ? Optional.of(new Vector3d((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.1, (double)blockPos.getZ() + 0.5)) : Optional.empty();
    }

    public boolean isPlayerFullyAsleep() {
        return this.isSleeping() && this.sleepTimer >= 100;
    }

    public int getSleepTimer() {
        return this.sleepTimer;
    }

    public void sendStatusMessage(ITextComponent iTextComponent, boolean bl) {
    }

    public void addStat(ResourceLocation resourceLocation) {
        this.addStat(Stats.CUSTOM.get(resourceLocation));
    }

    public void addStat(ResourceLocation resourceLocation, int n) {
        this.addStat(Stats.CUSTOM.get(resourceLocation), n);
    }

    public void addStat(Stat<?> stat) {
        this.addStat(stat, 1);
    }

    public void addStat(Stat<?> stat, int n) {
    }

    public void takeStat(Stat<?> stat) {
    }

    public int unlockRecipes(Collection<IRecipe<?>> collection) {
        return 1;
    }

    public void unlockRecipes(ResourceLocation[] resourceLocationArray) {
    }

    public int resetRecipes(Collection<IRecipe<?>> collection) {
        return 1;
    }

    @Override
    public void jump() {
        super.jump();
        this.addStat(Stats.JUMP);
        if (this.isSprinting()) {
            this.addExhaustion(0.2f);
        } else {
            this.addExhaustion(0.05f);
        }
    }

    @Override
    public void travel(Vector3d vector3d) {
        double d;
        double d2 = this.getPosX();
        double d3 = this.getPosY();
        double d4 = this.getPosZ();
        if (this.isSwimming() && !this.isPassenger()) {
            double d5;
            d = this.getLookVec().y;
            double d6 = d5 = d < -0.2 ? 0.085 : 0.06;
            if (d <= 0.0 || this.isJumping || !this.world.getBlockState(new BlockPos(this.getPosX(), this.getPosY() + 1.0 - 0.1, this.getPosZ())).getFluidState().isEmpty()) {
                Vector3d vector3d2 = this.getMotion();
                this.setMotion(vector3d2.add(0.0, (d - vector3d2.y) * d5, 0.0));
            }
        }
        if (this.abilities.isFlying && !this.isPassenger()) {
            d = this.getMotion().y;
            float f = this.jumpMovementFactor;
            this.jumpMovementFactor = this.abilities.getFlySpeed() * (float)(this.isSprinting() ? 2 : 1);
            super.travel(vector3d);
            Vector3d vector3d3 = this.getMotion();
            this.setMotion(vector3d3.x, d * 0.6, vector3d3.z);
            this.jumpMovementFactor = f;
            this.fallDistance = 0.0f;
            this.setFlag(7, true);
        } else {
            super.travel(vector3d);
        }
        this.addMovementStat(this.getPosX() - d2, this.getPosY() - d3, this.getPosZ() - d4);
    }

    @Override
    public void updateSwimming() {
        if (this.abilities.isFlying) {
            this.setSwimming(true);
        } else {
            super.updateSwimming();
        }
    }

    protected boolean isNormalCube(BlockPos blockPos) {
        return !this.world.getBlockState(blockPos).isSuffocating(this.world, blockPos);
    }

    @Override
    public float getAIMoveSpeed() {
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    public void addMovementStat(double d, double d2, double d3) {
        if (!this.isPassenger()) {
            if (this.isSwimming()) {
                int n = Math.round(MathHelper.sqrt(d * d + d2 * d2 + d3 * d3) * 100.0f);
                if (n > 0) {
                    this.addStat(Stats.SWIM_ONE_CM, n);
                    this.addExhaustion(0.01f * (float)n * 0.01f);
                }
            } else if (this.areEyesInFluid(FluidTags.WATER)) {
                int n = Math.round(MathHelper.sqrt(d * d + d2 * d2 + d3 * d3) * 100.0f);
                if (n > 0) {
                    this.addStat(Stats.WALK_UNDER_WATER_ONE_CM, n);
                    this.addExhaustion(0.01f * (float)n * 0.01f);
                }
            } else if (this.isInWater()) {
                int n = Math.round(MathHelper.sqrt(d * d + d3 * d3) * 100.0f);
                if (n > 0) {
                    this.addStat(Stats.WALK_ON_WATER_ONE_CM, n);
                    this.addExhaustion(0.01f * (float)n * 0.01f);
                }
            } else if (this.isOnLadder()) {
                if (d2 > 0.0) {
                    this.addStat(Stats.CLIMB_ONE_CM, (int)Math.round(d2 * 100.0));
                }
            } else if (this.onGround) {
                int n = Math.round(MathHelper.sqrt(d * d + d3 * d3) * 100.0f);
                if (n > 0) {
                    if (this.isSprinting()) {
                        this.addStat(Stats.SPRINT_ONE_CM, n);
                        this.addExhaustion(0.1f * (float)n * 0.01f);
                    } else if (this.isCrouching()) {
                        this.addStat(Stats.CROUCH_ONE_CM, n);
                        this.addExhaustion(0.0f * (float)n * 0.01f);
                    } else {
                        this.addStat(Stats.WALK_ONE_CM, n);
                        this.addExhaustion(0.0f * (float)n * 0.01f);
                    }
                }
            } else if (this.isElytraFlying()) {
                int n = Math.round(MathHelper.sqrt(d * d + d2 * d2 + d3 * d3) * 100.0f);
                this.addStat(Stats.AVIATE_ONE_CM, n);
            } else {
                int n = Math.round(MathHelper.sqrt(d * d + d3 * d3) * 100.0f);
                if (n > 25) {
                    this.addStat(Stats.FLY_ONE_CM, n);
                }
            }
        }
    }

    private void addMountedMovementStat(double d, double d2, double d3) {
        int n;
        if (this.isPassenger() && (n = Math.round(MathHelper.sqrt(d * d + d2 * d2 + d3 * d3) * 100.0f)) > 0) {
            Entity entity2 = this.getRidingEntity();
            if (entity2 instanceof AbstractMinecartEntity) {
                this.addStat(Stats.MINECART_ONE_CM, n);
            } else if (entity2 instanceof BoatEntity) {
                this.addStat(Stats.BOAT_ONE_CM, n);
            } else if (entity2 instanceof PigEntity) {
                this.addStat(Stats.PIG_ONE_CM, n);
            } else if (entity2 instanceof AbstractHorseEntity) {
                this.addStat(Stats.HORSE_ONE_CM, n);
            } else if (entity2 instanceof StriderEntity) {
                this.addStat(Stats.field_232862_C_, n);
            }
        }
    }

    @Override
    public boolean onLivingFall(float f, float f2) {
        if (this.abilities.allowFlying) {
            return true;
        }
        if (f >= 2.0f) {
            this.addStat(Stats.FALL_ONE_CM, (int)Math.round((double)f * 100.0));
        }
        return super.onLivingFall(f, f2);
    }

    public boolean tryToStartFallFlying() {
        ItemStack itemStack;
        if (!(this.onGround || this.isElytraFlying() || this.isInWater() || this.isPotionActive(Effects.LEVITATION) || (itemStack = this.getItemStackFromSlot(EquipmentSlotType.CHEST)).getItem() != Items.ELYTRA || !ElytraItem.isUsable(itemStack))) {
            this.startFallFlying();
            return false;
        }
        return true;
    }

    public void startFallFlying() {
        this.setFlag(7, false);
    }

    public void stopFallFlying() {
        this.setFlag(7, false);
        this.setFlag(7, true);
    }

    @Override
    protected void doWaterSplashEffect() {
        if (!this.isSpectator()) {
            super.doWaterSplashEffect();
        }
    }

    @Override
    protected SoundEvent getFallSound(int n) {
        return n > 4 ? SoundEvents.ENTITY_PLAYER_BIG_FALL : SoundEvents.ENTITY_PLAYER_SMALL_FALL;
    }

    @Override
    public void func_241847_a(ServerWorld serverWorld, LivingEntity livingEntity) {
        this.addStat(Stats.ENTITY_KILLED.get(livingEntity.getType()));
    }

    @Override
    public void setMotionMultiplier(BlockState blockState, Vector3d vector3d) {
        if (!this.abilities.isFlying) {
            super.setMotionMultiplier(blockState, vector3d);
        }
    }

    public void giveExperiencePoints(int n) {
        this.addScore(n);
        this.experience += (float)n / (float)this.xpBarCap();
        this.experienceTotal = MathHelper.clamp(this.experienceTotal + n, 0, Integer.MAX_VALUE);
        while (this.experience < 0.0f) {
            float f = this.experience * (float)this.xpBarCap();
            if (this.experienceLevel > 0) {
                this.addExperienceLevel(-1);
                this.experience = 1.0f + f / (float)this.xpBarCap();
                continue;
            }
            this.addExperienceLevel(-1);
            this.experience = 0.0f;
        }
        while (this.experience >= 1.0f) {
            this.experience = (this.experience - 1.0f) * (float)this.xpBarCap();
            this.addExperienceLevel(1);
            this.experience /= (float)this.xpBarCap();
        }
    }

    public int getXPSeed() {
        return this.xpSeed;
    }

    public void onEnchant(ItemStack itemStack, int n) {
        this.experienceLevel -= n;
        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experience = 0.0f;
            this.experienceTotal = 0;
        }
        this.xpSeed = this.rand.nextInt();
    }

    public void addExperienceLevel(int n) {
        this.experienceLevel += n;
        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experience = 0.0f;
            this.experienceTotal = 0;
        }
        if (n > 0 && this.experienceLevel % 5 == 0 && (float)this.lastXPSound < (float)this.ticksExisted - 100.0f) {
            float f = this.experienceLevel > 30 ? 1.0f : (float)this.experienceLevel / 30.0f;
            this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_PLAYER_LEVELUP, this.getSoundCategory(), f * 0.75f, 1.0f);
            this.lastXPSound = this.ticksExisted;
        }
    }

    public int xpBarCap() {
        if (this.experienceLevel >= 30) {
            return 112 + (this.experienceLevel - 30) * 9;
        }
        return this.experienceLevel >= 15 ? 37 + (this.experienceLevel - 15) * 5 : 7 + this.experienceLevel * 2;
    }

    public void addExhaustion(float f) {
        if (!this.abilities.disableDamage && !this.world.isRemote) {
            this.foodStats.addExhaustion(f);
        }
    }

    public FoodStats getFoodStats() {
        return this.foodStats;
    }

    public boolean canEat(boolean bl) {
        return this.abilities.disableDamage || bl || this.foodStats.needFood();
    }

    public boolean shouldHeal() {
        return this.getHealth() > 0.0f && this.getHealth() < this.getMaxHealth();
    }

    public boolean isAllowEdit() {
        return this.abilities.allowEdit;
    }

    public boolean canPlayerEdit(BlockPos blockPos, Direction direction, ItemStack itemStack) {
        if (this.abilities.allowEdit) {
            return false;
        }
        BlockPos blockPos2 = blockPos.offset(direction.getOpposite());
        CachedBlockInfo cachedBlockInfo = new CachedBlockInfo(this.world, blockPos2, false);
        return itemStack.canPlaceOn(this.world.getTags(), cachedBlockInfo);
    }

    @Override
    protected int getExperiencePoints(PlayerEntity playerEntity) {
        if (!this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY) && !this.isSpectator()) {
            int n = this.experienceLevel * 7;
            return n > 100 ? 100 : n;
        }
        return 1;
    }

    @Override
    protected boolean isPlayer() {
        return false;
    }

    @Override
    public boolean getAlwaysRenderNameTagForRender() {
        return false;
    }

    @Override
    protected boolean canTriggerWalking() {
        return !this.abilities.isFlying && (!this.onGround || !this.isDiscrete());
    }

    public void sendPlayerAbilities() {
    }

    public void setGameType(GameType gameType) {
    }

    @Override
    public ITextComponent getName() {
        return new StringTextComponent(this.gameProfile.getName());
    }

    public EnderChestInventory getInventoryEnderChest() {
        return this.enterChestInventory;
    }

    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType equipmentSlotType) {
        if (equipmentSlotType == EquipmentSlotType.MAINHAND) {
            return this.inventory.getCurrentItem();
        }
        if (equipmentSlotType == EquipmentSlotType.OFFHAND) {
            return this.inventory.offHandInventory.get(0);
        }
        return equipmentSlotType.getSlotType() == EquipmentSlotType.Group.ARMOR ? this.inventory.armorInventory.get(equipmentSlotType.getIndex()) : ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType equipmentSlotType, ItemStack itemStack) {
        if (equipmentSlotType == EquipmentSlotType.MAINHAND) {
            this.playEquipSound(itemStack);
            this.inventory.mainInventory.set(this.inventory.currentItem, itemStack);
        } else if (equipmentSlotType == EquipmentSlotType.OFFHAND) {
            this.playEquipSound(itemStack);
            this.inventory.offHandInventory.set(0, itemStack);
        } else if (equipmentSlotType.getSlotType() == EquipmentSlotType.Group.ARMOR) {
            this.playEquipSound(itemStack);
            this.inventory.armorInventory.set(equipmentSlotType.getIndex(), itemStack);
        }
    }

    public boolean addItemStackToInventory(ItemStack itemStack) {
        this.playEquipSound(itemStack);
        return this.inventory.addItemStackToInventory(itemStack);
    }

    @Override
    public Iterable<ItemStack> getHeldEquipment() {
        return Lists.newArrayList(this.getHeldItemMainhand(), this.getHeldItemOffhand());
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return this.inventory.armorInventory;
    }

    public boolean addShoulderEntity(CompoundNBT compoundNBT) {
        if (!this.isPassenger() && this.onGround && !this.isInWater()) {
            if (this.getLeftShoulderEntity().isEmpty()) {
                this.setLeftShoulderEntity(compoundNBT);
                this.timeEntitySatOnShoulder = this.world.getGameTime();
                return false;
            }
            if (this.getRightShoulderEntity().isEmpty()) {
                this.setRightShoulderEntity(compoundNBT);
                this.timeEntitySatOnShoulder = this.world.getGameTime();
                return false;
            }
            return true;
        }
        return true;
    }

    protected void spawnShoulderEntities() {
        if (this.timeEntitySatOnShoulder + 20L < this.world.getGameTime()) {
            this.spawnShoulderEntity(this.getLeftShoulderEntity());
            this.setLeftShoulderEntity(new CompoundNBT());
            this.spawnShoulderEntity(this.getRightShoulderEntity());
            this.setRightShoulderEntity(new CompoundNBT());
        }
    }

    private void spawnShoulderEntity(CompoundNBT compoundNBT) {
        if (!this.world.isRemote && !compoundNBT.isEmpty()) {
            EntityType.loadEntityUnchecked(compoundNBT, this.world).ifPresent(this::lambda$spawnShoulderEntity$3);
        }
    }

    @Override
    public abstract boolean isSpectator();

    @Override
    public boolean isSwimming() {
        return !this.abilities.isFlying && !this.isSpectator() && super.isSwimming();
    }

    public abstract boolean isCreative();

    @Override
    public boolean isPushedByWater() {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        AntiPush antiPush = functionRegistry.getAntiPush();
        if (antiPush.isState() && ((Boolean)antiPush.getModes().getValueByName("\u0412\u043e\u0434\u0430").get()).booleanValue() && this instanceof ClientPlayerEntity) {
            return true;
        }
        return !this.abilities.isFlying;
    }

    public Scoreboard getWorldScoreboard() {
        return this.world.getScoreboard();
    }

    @Override
    public ITextComponent getDisplayName() {
        IFormattableTextComponent iFormattableTextComponent = ScorePlayerTeam.formatPlayerName(this.getTeam(), this.getName());
        return this.addTellEvent(iFormattableTextComponent);
    }

    private IFormattableTextComponent addTellEvent(IFormattableTextComponent iFormattableTextComponent) {
        String string = this.getGameProfile().getName();
        return iFormattableTextComponent.modifyStyle(arg_0 -> this.lambda$addTellEvent$4(string, arg_0));
    }

    @Override
    public String getScoreboardName() {
        return this.getGameProfile().getName();
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        switch (1.$SwitchMap$net$minecraft$entity$Pose[pose.ordinal()]) {
            case 1: 
            case 2: 
            case 3: {
                return 0.4f;
            }
            case 4: {
                return 1.27f;
            }
        }
        return 1.62f;
    }

    @Override
    public void setAbsorptionAmount(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        this.getDataManager().set(ABSORPTION, Float.valueOf(f));
    }

    @Override
    public float getAbsorptionAmount() {
        return this.getDataManager().get(ABSORPTION).floatValue();
    }

    public static UUID getUUID(GameProfile gameProfile) {
        UUID uUID = gameProfile.getId();
        if (uUID == null) {
            uUID = PlayerEntity.getOfflineUUID(gameProfile.getName());
        }
        return uUID;
    }

    public static UUID getOfflineUUID(String string) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + string).getBytes(StandardCharsets.UTF_8));
    }

    public boolean isWearing(PlayerModelPart playerModelPart) {
        return (this.getDataManager().get(PLAYER_MODEL_FLAG) & playerModelPart.getPartMask()) == playerModelPart.getPartMask();
    }

    @Override
    public boolean replaceItemInInventory(int n, ItemStack itemStack) {
        if (n >= 0 && n < this.inventory.mainInventory.size()) {
            this.inventory.setInventorySlotContents(n, itemStack);
            return false;
        }
        EquipmentSlotType equipmentSlotType = n == 100 + EquipmentSlotType.HEAD.getIndex() ? EquipmentSlotType.HEAD : (n == 100 + EquipmentSlotType.CHEST.getIndex() ? EquipmentSlotType.CHEST : (n == 100 + EquipmentSlotType.LEGS.getIndex() ? EquipmentSlotType.LEGS : (n == 100 + EquipmentSlotType.FEET.getIndex() ? EquipmentSlotType.FEET : null)));
        if (n == 98) {
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemStack);
            return false;
        }
        if (n == 99) {
            this.setItemStackToSlot(EquipmentSlotType.OFFHAND, itemStack);
            return false;
        }
        if (equipmentSlotType == null) {
            int n2 = n - 200;
            if (n2 >= 0 && n2 < this.enterChestInventory.getSizeInventory()) {
                this.enterChestInventory.setInventorySlotContents(n2, itemStack);
                return false;
            }
            return true;
        }
        if (!itemStack.isEmpty() && (!(itemStack.getItem() instanceof ArmorItem) && !(itemStack.getItem() instanceof ElytraItem) ? equipmentSlotType != EquipmentSlotType.HEAD : MobEntity.getSlotForItemStack(itemStack) != equipmentSlotType)) {
            return true;
        }
        this.inventory.setInventorySlotContents(equipmentSlotType.getIndex() + this.inventory.mainInventory.size(), itemStack);
        return false;
    }

    public boolean hasReducedDebug() {
        return this.hasReducedDebug;
    }

    public void setReducedDebug(boolean bl) {
        this.hasReducedDebug = bl;
    }

    @Override
    public void forceFireTicks(int n) {
        super.forceFireTicks(this.abilities.disableDamage ? Math.min(n, 1) : n);
    }

    @Override
    public HandSide getPrimaryHand() {
        return this.dataManager.get(MAIN_HAND) == 0 ? HandSide.LEFT : HandSide.RIGHT;
    }

    public void setPrimaryHand(HandSide handSide) {
        this.dataManager.set(MAIN_HAND, (byte)(handSide != HandSide.LEFT ? 1 : 0));
    }

    public CompoundNBT getLeftShoulderEntity() {
        return this.dataManager.get(LEFT_SHOULDER_ENTITY);
    }

    protected void setLeftShoulderEntity(CompoundNBT compoundNBT) {
        this.dataManager.set(LEFT_SHOULDER_ENTITY, compoundNBT);
    }

    public CompoundNBT getRightShoulderEntity() {
        return this.dataManager.get(RIGHT_SHOULDER_ENTITY);
    }

    protected void setRightShoulderEntity(CompoundNBT compoundNBT) {
        this.dataManager.set(RIGHT_SHOULDER_ENTITY, compoundNBT);
    }

    public float getCooldownPeriod() {
        return (float)(1.0 / this.getAttributeValue(Attributes.ATTACK_SPEED) * 20.0);
    }

    public float getCooledAttackStrength(float f) {
        return MathHelper.clamp(((float)this.ticksSinceLastSwing + f) / this.getCooldownPeriod(), 0.0f, 1.0f);
    }

    public void resetCooldown() {
        this.ticksSinceLastSwing = 0;
    }

    public CooldownTracker getCooldownTracker() {
        return this.cooldownTracker;
    }

    @Override
    protected float getSpeedFactor() {
        return !this.abilities.isFlying && !this.isElytraFlying() ? super.getSpeedFactor() : 1.0f;
    }

    public float getLuck() {
        return (float)this.getAttributeValue(Attributes.LUCK);
    }

    public boolean canUseCommandBlock() {
        return this.abilities.isCreativeMode && this.getPermissionLevel() >= 2;
    }

    @Override
    public boolean canPickUpItem(ItemStack itemStack) {
        EquipmentSlotType equipmentSlotType = MobEntity.getSlotForItemStack(itemStack);
        return this.getItemStackFromSlot(equipmentSlotType).isEmpty();
    }

    @Override
    public EntitySize getSize(Pose pose) {
        return SIZE_BY_POSE.getOrDefault((Object)pose, STANDING_SIZE);
    }

    @Override
    public ImmutableList<Pose> getAvailablePoses() {
        return ImmutableList.of(Pose.STANDING, Pose.CROUCHING, Pose.SWIMMING);
    }

    @Override
    public ItemStack findAmmo(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof ShootableItem)) {
            return ItemStack.EMPTY;
        }
        Predicate<ItemStack> predicate = ((ShootableItem)itemStack.getItem()).getAmmoPredicate();
        ItemStack itemStack2 = ShootableItem.getHeldAmmo(this, predicate);
        if (!itemStack2.isEmpty()) {
            return itemStack2;
        }
        predicate = ((ShootableItem)itemStack.getItem()).getInventoryAmmoPredicate();
        for (int i = 0; i < this.inventory.getSizeInventory(); ++i) {
            ItemStack itemStack3 = this.inventory.getStackInSlot(i);
            if (!predicate.test(itemStack3)) continue;
            return itemStack3;
        }
        return this.abilities.isCreativeMode ? new ItemStack(Items.ARROW) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack onFoodEaten(World world, ItemStack itemStack) {
        this.getFoodStats().consume(itemStack.getItem(), itemStack);
        this.addStat(Stats.ITEM_USED.get(itemStack.getItem()));
        world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
        if (this instanceof ServerPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)this, itemStack);
        }
        return super.onFoodEaten(world, itemStack);
    }

    @Override
    protected boolean func_230295_b_(BlockState blockState) {
        return this.abilities.isFlying || super.func_230295_b_(blockState);
    }

    @Override
    public Vector3d getLeashPosition(float f) {
        float f2;
        double d = 0.22 * (this.getPrimaryHand() == HandSide.RIGHT ? -1.0 : 1.0);
        float f3 = MathHelper.lerp(f * 0.5f, this.rotationPitch, this.prevRotationPitch) * ((float)Math.PI / 180);
        float f4 = MathHelper.lerp(f, this.prevRenderYawOffset, this.renderYawOffset) * ((float)Math.PI / 180);
        if (!this.isElytraFlying() && !this.isSpinAttacking()) {
            if (this.isActualySwimming()) {
                return this.func_242282_l(f).add(new Vector3d(d, 0.2, -0.15).rotatePitch(-f3).rotateYaw(-f4));
            }
            double d2 = this.getBoundingBox().getYSize() - 1.0;
            double d3 = this.isCrouching() ? -0.2 : 0.07;
            return this.func_242282_l(f).add(new Vector3d(d, d2, d3).rotateYaw(-f4));
        }
        Vector3d vector3d = this.getLook(f);
        Vector3d vector3d2 = this.getMotion();
        double d4 = Entity.horizontalMag(vector3d2);
        double d5 = Entity.horizontalMag(vector3d);
        if (d4 > 0.0 && d5 > 0.0) {
            double d6 = (vector3d2.x * vector3d.x + vector3d2.z * vector3d.z) / Math.sqrt(d4 * d5);
            double d7 = vector3d2.x * vector3d.z - vector3d2.z * vector3d.x;
            f2 = (float)(Math.signum(d7) * Math.acos(d6));
        } else {
            f2 = 0.0f;
        }
        return this.func_242282_l(f).add(new Vector3d(d, -0.11, 0.85).rotateRoll(-f2).rotatePitch(-f3).rotateYaw(-f4));
    }

    private Style lambda$addTellEvent$4(String string, Style style) {
        return style.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + string + " ")).setHoverEvent(this.getHoverEvent()).setInsertion(string);
    }

    private void lambda$spawnShoulderEntity$3(Entity entity2) {
        if (entity2 instanceof TameableEntity) {
            ((TameableEntity)entity2).setOwnerId(this.entityUniqueID);
        }
        entity2.setPosition(this.getPosX(), this.getPosY() + (double)0.7f, this.getPosZ());
        ((ServerWorld)this.world).summonEntity(entity2);
    }

    private static void lambda$damageShield$2(Hand hand, PlayerEntity playerEntity) {
        playerEntity.sendBreakAnimation(hand);
    }

    private void lambda$playShoulderEntityAmbientSound$1(EntityType entityType) {
        if (!ParrotEntity.playMimicSound(this.world, this)) {
            this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), ParrotEntity.func_234212_a_(this.world, this.world.rand), this.getSoundCategory(), 1.0f, ParrotEntity.getPitch(this.world.rand));
        }
    }

    private static boolean lambda$playShoulderEntityAmbientSound$0(EntityType entityType) {
        return entityType == EntityType.PARROT;
    }

    public static enum SleepResult {
        NOT_POSSIBLE_HERE,
        NOT_POSSIBLE_NOW(new TranslationTextComponent("block.minecraft.bed.no_sleep")),
        TOO_FAR_AWAY(new TranslationTextComponent("block.minecraft.bed.too_far_away")),
        OBSTRUCTED(new TranslationTextComponent("block.minecraft.bed.obstructed")),
        OTHER_PROBLEM,
        NOT_SAFE(new TranslationTextComponent("block.minecraft.bed.not_safe"));

        @Nullable
        private final ITextComponent message;

        private SleepResult() {
            this.message = null;
        }

        private SleepResult(ITextComponent iTextComponent) {
            this.message = iTextComponent;
        }

        @Nullable
        public ITextComponent getMessage() {
            return this.message;
        }
    }
}

