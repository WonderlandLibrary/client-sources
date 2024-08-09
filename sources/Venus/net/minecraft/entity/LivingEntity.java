/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import mpp.venusfr.events.EventDamageReceive;
import mpp.venusfr.events.JumpEvent;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.misc.AntiPush;
import mpp.venusfr.functions.impl.render.SwingAnimation;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.venusfr;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.HoneyBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.network.play.server.SCollectItemPacket;
import net.minecraft.network.play.server.SEntityEquipmentPacket;
import net.minecraft.network.play.server.SEntityStatusPacket;
import net.minecraft.network.play.server.SSpawnMobPacket;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.CombatRules;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TeleportationRepositioner;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

public abstract class LivingEntity
extends Entity {
    private static final UUID SPRINTING_SPEED_BOOST_ID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
    private static final UUID SOUL_SPEED_BOOT_ID = UUID.fromString("87f46a96-686f-4796-b035-22e16ee9e038");
    private static final AttributeModifier SPRINTING_SPEED_BOOST = new AttributeModifier(SPRINTING_SPEED_BOOST_ID, "Sprinting speed boost", (double)0.3f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    protected static final DataParameter<Byte> LIVING_FLAGS = EntityDataManager.createKey(LivingEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Float> HEALTH = EntityDataManager.createKey(LivingEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> POTION_EFFECTS = EntityDataManager.createKey(LivingEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> HIDE_PARTICLES = EntityDataManager.createKey(LivingEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> ARROW_COUNT_IN_ENTITY = EntityDataManager.createKey(LivingEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> BEE_STING_COUNT = EntityDataManager.createKey(LivingEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Optional<BlockPos>> BED_POSITION = EntityDataManager.createKey(LivingEntity.class, DataSerializers.OPTIONAL_BLOCK_POS);
    protected static final EntitySize SLEEPING_SIZE = EntitySize.fixed(0.2f, 0.2f);
    private final AttributeModifierManager attributes;
    private final CombatTracker combatTracker = new CombatTracker(this);
    private final Map<Effect, EffectInstance> activePotionsMap = Maps.newHashMap();
    private final NonNullList<ItemStack> handInventory = NonNullList.withSize(2, ItemStack.EMPTY);
    private final NonNullList<ItemStack> armorArray = NonNullList.withSize(4, ItemStack.EMPTY);
    public boolean isSwingInProgress;
    public Hand swingingHand;
    public int swingProgressInt;
    public int arrowHitTimer;
    public int beeStingRemovalCooldown;
    public int hurtTime;
    public int maxHurtTime;
    public float attackedAtYaw;
    public int deathTime;
    public float prevSwingProgress;
    public float swingProgress;
    protected int ticksSinceLastSwing;
    public float prevLimbSwingAmount;
    public float limbSwingAmount;
    public float limbSwing;
    public final int maxHurtResistantTime = 20;
    public final float randomUnused2;
    public final float randomUnused1;
    public float renderYawOffset;
    public float prevRenderYawOffset;
    public float rotationYawHead;
    public float prevRotationYawHead;
    public float rotationPitchHead;
    public float prevRotationPitchHead;
    public float jumpMovementFactor = 0.02f;
    @Nullable
    protected PlayerEntity attackingPlayer;
    protected int recentlyHit;
    protected boolean dead;
    protected int idleTime;
    protected float prevOnGroundSpeedFactor;
    protected float onGroundSpeedFactor;
    protected float movedDistance;
    protected float prevMovedDistance;
    protected float unused180;
    protected int scoreValue;
    protected float lastDamage;
    protected boolean isJumping;
    public float moveStrafing;
    public float moveVertical;
    public float moveForward;
    protected int newPosRotationIncrements;
    protected double interpTargetX;
    protected double interpTargetY;
    protected double interpTargetZ;
    protected double interpTargetYaw;
    protected double interpTargetPitch;
    protected double interpTargetHeadYaw;
    protected int interpTicksHead;
    private boolean potionsNeedUpdate = true;
    @Nullable
    private LivingEntity revengeTarget;
    private int revengeTimer;
    private LivingEntity lastAttackedEntity;
    private int lastAttackedEntityTime;
    private float landMovementFactor;
    public int jumpTicks;
    private float absorptionAmount;
    public ItemStack activeItemStack = ItemStack.EMPTY;
    protected int activeItemStackUseCount;
    protected int ticksElytraFlying;
    private BlockPos prevBlockpos;
    private Optional<BlockPos> field_233624_bE_ = Optional.empty();
    private DamageSource lastDamageSource;
    private long lastDamageStamp;
    protected int spinAttackDuration;
    private float swimAnimation;
    private float lastSwimAnimation;
    protected Brain<?> brain;
    private final JumpEvent jumpEvent = new JumpEvent();

    protected LivingEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        this.attributes = new AttributeModifierManager(GlobalEntityTypeAttributes.getAttributesForEntity(entityType));
        this.setHealth(this.getMaxHealth());
        this.preventEntitySpawning = true;
        this.randomUnused1 = (float)((Math.random() + 1.0) * (double)0.01f);
        this.recenterBoundingBox();
        this.randomUnused2 = (float)Math.random() * 12398.0f;
        this.rotationYawHead = this.rotationYaw = (float)(Math.random() * 6.2831854820251465);
        this.stepHeight = 0.6f;
        NBTDynamicOps nBTDynamicOps = NBTDynamicOps.INSTANCE;
        this.brain = this.createBrain(new Dynamic<INBT>(nBTDynamicOps, nBTDynamicOps.createMap(ImmutableMap.of(nBTDynamicOps.createString("memories"), (INBT)nBTDynamicOps.emptyMap()))));
    }

    public Brain<?> getBrain() {
        return this.brain;
    }

    protected Brain.BrainCodec<?> getBrainCodec() {
        return Brain.createCodec(ImmutableList.of(), ImmutableList.of());
    }

    protected Brain<?> createBrain(Dynamic<?> dynamic) {
        return this.getBrainCodec().deserialize(dynamic);
    }

    @Override
    public void onKillCommand() {
        this.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
    }

    public boolean canAttack(EntityType<?> entityType) {
        return false;
    }

    @Override
    protected void registerData() {
        this.dataManager.register(LIVING_FLAGS, (byte)0);
        this.dataManager.register(POTION_EFFECTS, 0);
        this.dataManager.register(HIDE_PARTICLES, false);
        this.dataManager.register(ARROW_COUNT_IN_ENTITY, 0);
        this.dataManager.register(BEE_STING_COUNT, 0);
        this.dataManager.register(HEALTH, Float.valueOf(1.0f));
        this.dataManager.register(BED_POSITION, Optional.empty());
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return AttributeModifierMap.createMutableAttribute().createMutableAttribute(Attributes.MAX_HEALTH).createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE).createMutableAttribute(Attributes.MOVEMENT_SPEED).createMutableAttribute(Attributes.ARMOR).createMutableAttribute(Attributes.ARMOR_TOUGHNESS);
    }

    @Override
    protected void updateFallState(double d, boolean bl, BlockState blockState, BlockPos blockPos) {
        if (!this.isInWater()) {
            this.func_233567_aH_();
        }
        if (!this.world.isRemote && bl && this.fallDistance > 0.0f) {
            this.func_233641_cN_();
            this.func_233642_cO_();
        }
        if (!this.world.isRemote && this.fallDistance > 3.0f && bl) {
            float f = MathHelper.ceil(this.fallDistance - 3.0f);
            if (!blockState.isAir()) {
                double d2 = Math.min((double)(0.2f + f / 15.0f), 2.5);
                int n = (int)(150.0 * d2);
                ((ServerWorld)this.world).spawnParticle(new BlockParticleData(ParticleTypes.BLOCK, blockState), this.getPosX(), this.getPosY(), this.getPosZ(), n, 0.0, 0.0, 0.0, 0.15f);
            }
        }
        super.updateFallState(d, bl, blockState, blockPos);
    }

    public boolean canBreatheUnderwater() {
        return this.getCreatureAttribute() == CreatureAttribute.UNDEAD;
    }

    public float getSwimAnimation(float f) {
        return MathHelper.lerp(f, this.lastSwimAnimation, this.swimAnimation);
    }

    @Override
    public void baseTick() {
        boolean bl;
        this.prevSwingProgress = this.swingProgress;
        if (this.firstUpdate) {
            this.getBedPosition().ifPresent(this::setSleepingPosition);
        }
        if (this.getMovementSpeed()) {
            this.addSprintingEffect();
        }
        super.baseTick();
        this.world.getProfiler().startSection("livingEntityBaseTick");
        boolean bl2 = this instanceof PlayerEntity;
        if (this.isAlive()) {
            double d;
            double d2;
            if (this.isEntityInsideOpaqueBlock()) {
                this.attackEntityFrom(DamageSource.IN_WALL, 1.0f);
            } else if (bl2 && !this.world.getWorldBorder().contains(this.getBoundingBox()) && (d2 = this.world.getWorldBorder().getClosestDistance(this) + this.world.getWorldBorder().getDamageBuffer()) < 0.0 && (d = this.world.getWorldBorder().getDamagePerBlock()) > 0.0) {
                this.attackEntityFrom(DamageSource.IN_WALL, Math.max(1, MathHelper.floor(-d2 * d)));
            }
        }
        if (this.isImmuneToFire() || this.world.isRemote) {
            this.extinguish();
        }
        boolean bl3 = bl = bl2 && ((PlayerEntity)this).abilities.disableDamage;
        if (this.isAlive()) {
            Object object;
            if (this.areEyesInFluid(FluidTags.WATER) && !this.world.getBlockState(new BlockPos(this.getPosX(), this.getPosYEye(), this.getPosZ())).isIn(Blocks.BUBBLE_COLUMN)) {
                if (!(this.canBreatheUnderwater() || EffectUtils.canBreatheUnderwater(this) || bl)) {
                    this.setAir(this.decreaseAirSupply(this.getAir()));
                    if (this.getAir() == -20) {
                        this.setAir(0);
                        object = this.getMotion();
                        for (int i = 0; i < 8; ++i) {
                            double d = this.rand.nextDouble() - this.rand.nextDouble();
                            double d3 = this.rand.nextDouble() - this.rand.nextDouble();
                            double d4 = this.rand.nextDouble() - this.rand.nextDouble();
                            this.world.addParticle(ParticleTypes.BUBBLE, this.getPosX() + d, this.getPosY() + d3, this.getPosZ() + d4, ((Vector3d)object).x, ((Vector3d)object).y, ((Vector3d)object).z);
                        }
                        this.attackEntityFrom(DamageSource.DROWN, 2.0f);
                    }
                }
                if (!this.world.isRemote && this.isPassenger() && this.getRidingEntity() != null && !this.getRidingEntity().canBeRiddenInWater()) {
                    this.stopRiding();
                }
            } else if (this.getAir() < this.getMaxAir()) {
                this.setAir(this.determineNextAir(this.getAir()));
            }
            if (!this.world.isRemote && !Objects.equal(this.prevBlockpos, object = this.getPosition())) {
                this.prevBlockpos = object;
                this.frostWalk((BlockPos)object);
            }
        }
        if (this.isAlive() && this.isInWaterRainOrBubbleColumn()) {
            this.extinguish();
        }
        if (this.hurtTime > 0) {
            --this.hurtTime;
        }
        if (this.hurtResistantTime > 0 && !(this instanceof ServerPlayerEntity)) {
            --this.hurtResistantTime;
        }
        if (this.getShouldBeDead()) {
            this.onDeathUpdate();
        }
        if (this.recentlyHit > 0) {
            --this.recentlyHit;
        } else {
            this.attackingPlayer = null;
        }
        if (this.lastAttackedEntity != null && !this.lastAttackedEntity.isAlive()) {
            this.lastAttackedEntity = null;
        }
        if (this.revengeTarget != null) {
            if (!this.revengeTarget.isAlive()) {
                this.setRevengeTarget(null);
            } else if (this.ticksExisted - this.revengeTimer > 100) {
                this.setRevengeTarget(null);
            }
        }
        this.updatePotionEffects();
        this.prevMovedDistance = this.movedDistance;
        this.prevRenderYawOffset = this.renderYawOffset;
        this.prevRotationYawHead = this.rotationYawHead;
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationPitchHead = this.rotationPitchHead;
        this.world.getProfiler().endSection();
    }

    public boolean getMovementSpeed() {
        return this.ticksExisted % 5 == 0 && this.getMotion().x != 0.0 && this.getMotion().z != 0.0 && !this.isSpectator() && EnchantmentHelper.hasSoulSpeed(this) && this.func_230296_cM_();
    }

    protected void addSprintingEffect() {
        Vector3d vector3d = this.getMotion();
        this.world.addParticle(ParticleTypes.SOUL, this.getPosX() + (this.rand.nextDouble() - 0.5) * (double)this.getWidth(), this.getPosY() + 0.1, this.getPosZ() + (this.rand.nextDouble() - 0.5) * (double)this.getWidth(), vector3d.x * -0.2, 0.1, vector3d.z * -0.2);
        float f = this.rand.nextFloat() * 0.4f + this.rand.nextFloat() > 0.9f ? 0.6f : 0.0f;
        this.playSound(SoundEvents.PARTICLE_SOUL_ESCAPE, f, 0.6f + this.rand.nextFloat() * 0.4f);
    }

    protected boolean func_230296_cM_() {
        return this.world.getBlockState(this.getPositionUnderneath()).isIn(BlockTags.SOUL_SPEED_BLOCKS);
    }

    @Override
    protected float getSpeedFactor() {
        return this.func_230296_cM_() && EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.SOUL_SPEED, this) > 0 ? 1.0f : super.getSpeedFactor();
    }

    protected boolean func_230295_b_(BlockState blockState) {
        return !blockState.isAir() || this.isElytraFlying();
    }

    protected void func_233641_cN_() {
        ModifiableAttributeInstance modifiableAttributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (modifiableAttributeInstance != null && modifiableAttributeInstance.getModifier(SOUL_SPEED_BOOT_ID) != null) {
            modifiableAttributeInstance.removeModifier(SOUL_SPEED_BOOT_ID);
        }
    }

    protected void func_233642_cO_() {
        int n;
        if (!this.getStateBelow().isAir() && (n = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.SOUL_SPEED, this)) > 0 && this.func_230296_cM_()) {
            ModifiableAttributeInstance modifiableAttributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
            if (modifiableAttributeInstance == null) {
                return;
            }
            modifiableAttributeInstance.applyNonPersistentModifier(new AttributeModifier(SOUL_SPEED_BOOT_ID, "Soul speed boost", (double)(0.03f * (1.0f + (float)n * 0.35f)), AttributeModifier.Operation.ADDITION));
            if (this.getRNG().nextFloat() < 0.04f) {
                ItemStack itemStack = this.getItemStackFromSlot(EquipmentSlotType.FEET);
                itemStack.damageItem(1, this, LivingEntity::lambda$func_233642_cO_$0);
            }
        }
    }

    protected void frostWalk(BlockPos blockPos) {
        int n = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FROST_WALKER, this);
        if (n > 0) {
            FrostWalkerEnchantment.freezeNearby(this, this.world, blockPos, n);
        }
        if (this.func_230295_b_(this.getStateBelow())) {
            this.func_233641_cN_();
        }
        this.func_233642_cO_();
    }

    public boolean isChild() {
        return true;
    }

    public float getRenderScale() {
        return this.isChild() ? 0.5f : 1.0f;
    }

    protected boolean func_241208_cS_() {
        return false;
    }

    @Override
    public boolean canBeRiddenInWater() {
        return true;
    }

    protected void onDeathUpdate() {
        ++this.deathTime;
        if (this.deathTime == 20) {
            this.remove();
            for (int i = 0; i < 20; ++i) {
                double d = this.rand.nextGaussian() * 0.02;
                double d2 = this.rand.nextGaussian() * 0.02;
                double d3 = this.rand.nextGaussian() * 0.02;
                this.world.addParticle(ParticleTypes.POOF, this.getPosXRandom(1.0), this.getPosYRandom(), this.getPosZRandom(1.0), d, d2, d3);
            }
        }
    }

    protected boolean canDropLoot() {
        return !this.isChild();
    }

    protected boolean func_230282_cS_() {
        return !this.isChild();
    }

    protected int decreaseAirSupply(int n) {
        int n2 = EnchantmentHelper.getRespirationModifier(this);
        return n2 > 0 && this.rand.nextInt(n2 + 1) > 0 ? n : n - 1;
    }

    protected int determineNextAir(int n) {
        return Math.min(n + 4, this.getMaxAir());
    }

    protected int getExperiencePoints(PlayerEntity playerEntity) {
        return 1;
    }

    protected boolean isPlayer() {
        return true;
    }

    public Random getRNG() {
        return this.rand;
    }

    @Nullable
    public LivingEntity getRevengeTarget() {
        return this.revengeTarget;
    }

    public int getRevengeTimer() {
        return this.revengeTimer;
    }

    public void func_230246_e_(@Nullable PlayerEntity playerEntity) {
        this.attackingPlayer = playerEntity;
        this.recentlyHit = this.ticksExisted;
    }

    public void setRevengeTarget(@Nullable LivingEntity livingEntity) {
        this.revengeTarget = livingEntity;
        this.revengeTimer = this.ticksExisted;
    }

    @Nullable
    public LivingEntity getLastAttackedEntity() {
        return this.lastAttackedEntity;
    }

    public int getLastAttackedEntityTime() {
        return this.lastAttackedEntityTime;
    }

    public void setLastAttackedEntity(Entity entity2) {
        this.lastAttackedEntity = entity2 instanceof LivingEntity ? (LivingEntity)entity2 : null;
        this.lastAttackedEntityTime = this.ticksExisted;
    }

    public int getIdleTime() {
        return this.idleTime;
    }

    public void setIdleTime(int n) {
        this.idleTime = n;
    }

    protected void playEquipSound(ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            SoundEvent soundEvent = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
            Item item = itemStack.getItem();
            if (item instanceof ArmorItem) {
                soundEvent = ((ArmorItem)item).getArmorMaterial().getSoundEvent();
            } else if (item == Items.ELYTRA) {
                soundEvent = SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA;
            }
            this.playSound(soundEvent, 1.0f, 1.0f);
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        Object object;
        compoundNBT.putFloat("Health", this.getHealth());
        compoundNBT.putShort("HurtTime", (short)this.hurtTime);
        compoundNBT.putInt("HurtByTimestamp", this.revengeTimer);
        compoundNBT.putShort("DeathTime", (short)this.deathTime);
        compoundNBT.putFloat("AbsorptionAmount", this.getAbsorptionAmount());
        compoundNBT.put("Attributes", this.getAttributeManager().serialize());
        if (!this.activePotionsMap.isEmpty()) {
            object = new ListNBT();
            for (EffectInstance effectInstance : this.activePotionsMap.values()) {
                ((AbstractList)object).add(effectInstance.write(new CompoundNBT()));
            }
            compoundNBT.put("ActiveEffects", (INBT)object);
        }
        compoundNBT.putBoolean("FallFlying", this.isElytraFlying());
        this.getBedPosition().ifPresent(arg_0 -> LivingEntity.lambda$writeAdditional$1(compoundNBT, arg_0));
        object = this.brain.encode(NBTDynamicOps.INSTANCE);
        ((DataResult)object).resultOrPartial(LOGGER::error).ifPresent(arg_0 -> LivingEntity.lambda$writeAdditional$2(compoundNBT, arg_0));
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        Object object;
        this.setAbsorptionAmount(compoundNBT.getFloat("AbsorptionAmount"));
        if (compoundNBT.contains("Attributes", 0) && this.world != null && !this.world.isRemote) {
            this.getAttributeManager().deserialize(compoundNBT.getList("Attributes", 10));
        }
        if (compoundNBT.contains("ActiveEffects", 0)) {
            object = compoundNBT.getList("ActiveEffects", 10);
            for (int i = 0; i < ((ListNBT)object).size(); ++i) {
                CompoundNBT compoundNBT2 = ((ListNBT)object).getCompound(i);
                EffectInstance effectInstance = EffectInstance.read(compoundNBT2);
                if (effectInstance == null) continue;
                this.activePotionsMap.put(effectInstance.getPotion(), effectInstance);
            }
        }
        if (compoundNBT.contains("Health", 0)) {
            this.setHealth(compoundNBT.getFloat("Health"));
        }
        this.hurtTime = compoundNBT.getShort("HurtTime");
        this.deathTime = compoundNBT.getShort("DeathTime");
        this.revengeTimer = compoundNBT.getInt("HurtByTimestamp");
        if (compoundNBT.contains("Team", 1)) {
            boolean bl;
            object = compoundNBT.getString("Team");
            ScorePlayerTeam scorePlayerTeam = this.world.getScoreboard().getTeam((String)object);
            boolean bl2 = bl = scorePlayerTeam != null && this.world.getScoreboard().addPlayerToTeam(this.getCachedUniqueIdString(), scorePlayerTeam);
            if (!bl) {
                LOGGER.warn("Unable to add mob to team \"{}\" (that team probably doesn't exist)", object);
            }
        }
        if (compoundNBT.getBoolean("FallFlying")) {
            this.setFlag(7, false);
        }
        if (compoundNBT.contains("SleepingX", 0) && compoundNBT.contains("SleepingY", 0) && compoundNBT.contains("SleepingZ", 0)) {
            object = new BlockPos(compoundNBT.getInt("SleepingX"), compoundNBT.getInt("SleepingY"), compoundNBT.getInt("SleepingZ"));
            this.setBedPosition((BlockPos)object);
            this.dataManager.set(POSE, Pose.SLEEPING);
            if (!this.firstUpdate) {
                this.setSleepingPosition((BlockPos)object);
            }
        }
        if (compoundNBT.contains("Brain", 1)) {
            this.brain = this.createBrain(new Dynamic<INBT>(NBTDynamicOps.INSTANCE, compoundNBT.get("Brain")));
        }
    }

    protected void updatePotionEffects() {
        Iterator<Effect> iterator2 = this.activePotionsMap.keySet().iterator();
        try {
            while (iterator2.hasNext()) {
                Effect effect = iterator2.next();
                EffectInstance effectInstance = this.activePotionsMap.get(effect);
                if (!effectInstance.tick(this, () -> this.lambda$updatePotionEffects$3(effectInstance))) {
                    if (this.world.isRemote) continue;
                    iterator2.remove();
                    this.onFinishedPotionEffect(effectInstance);
                    continue;
                }
                if (effectInstance.getDuration() % 600 != 0) continue;
                this.onChangedPotionEffect(effectInstance, true);
            }
        } catch (ConcurrentModificationException concurrentModificationException) {
            // empty catch block
        }
        if (this.potionsNeedUpdate) {
            if (!this.world.isRemote) {
                this.updatePotionMetadata();
            }
            this.potionsNeedUpdate = false;
        }
        int n = this.dataManager.get(POTION_EFFECTS);
        boolean bl = this.dataManager.get(HIDE_PARTICLES);
        if (n > 0) {
            boolean bl2 = this.isInvisible() ? this.rand.nextInt(15) == 0 : this.rand.nextBoolean();
            if (bl) {
                bl2 &= this.rand.nextInt(5) == 0;
            }
            if (bl2 && n > 0) {
                double d = (double)(n >> 16 & 0xFF) / 255.0;
                double d2 = (double)(n >> 8 & 0xFF) / 255.0;
                double d3 = (double)(n >> 0 & 0xFF) / 255.0;
                this.world.addParticle(bl ? ParticleTypes.AMBIENT_ENTITY_EFFECT : ParticleTypes.ENTITY_EFFECT, this.getPosXRandom(0.5), this.getPosYRandom(), this.getPosZRandom(0.5), d, d2, d3);
            }
        }
    }

    protected void updatePotionMetadata() {
        if (this.activePotionsMap.isEmpty()) {
            this.resetPotionEffectMetadata();
            this.setInvisible(true);
        } else {
            Collection<EffectInstance> collection = this.activePotionsMap.values();
            this.dataManager.set(HIDE_PARTICLES, LivingEntity.areAllPotionsAmbient(collection));
            this.dataManager.set(POTION_EFFECTS, PotionUtils.getPotionColorFromEffectList(collection));
            this.setInvisible(this.isPotionActive(Effects.INVISIBILITY));
        }
    }

    public double getVisibilityMultiplier(@Nullable Entity entity2) {
        double d = 1.0;
        if (this.isDiscrete()) {
            d *= 0.8;
        }
        if (this.isInvisible()) {
            float f = this.getArmorCoverPercentage();
            if (f < 0.1f) {
                f = 0.1f;
            }
            d *= 0.7 * (double)f;
        }
        if (entity2 != null) {
            ItemStack itemStack = this.getItemStackFromSlot(EquipmentSlotType.HEAD);
            Item item = itemStack.getItem();
            EntityType<?> entityType = entity2.getType();
            if (entityType == EntityType.SKELETON && item == Items.SKELETON_SKULL || entityType == EntityType.ZOMBIE && item == Items.ZOMBIE_HEAD || entityType == EntityType.CREEPER && item == Items.CREEPER_HEAD) {
                d *= 0.5;
            }
        }
        return d;
    }

    public boolean canAttack(LivingEntity livingEntity) {
        return false;
    }

    public boolean canAttack(LivingEntity livingEntity, EntityPredicate entityPredicate) {
        return entityPredicate.canTarget(this, livingEntity);
    }

    public static boolean areAllPotionsAmbient(Collection<EffectInstance> collection) {
        for (EffectInstance effectInstance : collection) {
            if (effectInstance.isAmbient()) continue;
            return true;
        }
        return false;
    }

    protected void resetPotionEffectMetadata() {
        this.dataManager.set(HIDE_PARTICLES, false);
        this.dataManager.set(POTION_EFFECTS, 0);
    }

    public boolean clearActivePotions() {
        if (this.world.isRemote) {
            return true;
        }
        Iterator<EffectInstance> iterator2 = this.activePotionsMap.values().iterator();
        boolean bl = false;
        while (iterator2.hasNext()) {
            this.onFinishedPotionEffect(iterator2.next());
            iterator2.remove();
            bl = true;
        }
        return bl;
    }

    public Collection<EffectInstance> getActivePotionEffects() {
        return this.activePotionsMap.values();
    }

    public Map<Effect, EffectInstance> getActivePotionMap() {
        return this.activePotionsMap;
    }

    public boolean isPotionActive(Effect effect) {
        return this.activePotionsMap.containsKey(effect);
    }

    @Nullable
    public EffectInstance getActivePotionEffect(Effect effect) {
        return this.activePotionsMap.get(effect);
    }

    public boolean addPotionEffect(EffectInstance effectInstance) {
        if (!this.isPotionApplicable(effectInstance)) {
            return true;
        }
        EffectInstance effectInstance2 = this.activePotionsMap.get(effectInstance.getPotion());
        if (effectInstance2 == null) {
            this.activePotionsMap.put(effectInstance.getPotion(), effectInstance);
            this.onNewPotionEffect(effectInstance);
            return false;
        }
        if (effectInstance2.combine(effectInstance)) {
            this.onChangedPotionEffect(effectInstance2, false);
            return false;
        }
        return true;
    }

    public boolean isPotionApplicable(EffectInstance effectInstance) {
        Effect effect;
        return this.getCreatureAttribute() == CreatureAttribute.UNDEAD && ((effect = effectInstance.getPotion()) == Effects.REGENERATION || effect == Effects.POISON);
    }

    public void func_233646_e_(EffectInstance effectInstance) {
        if (this.isPotionApplicable(effectInstance)) {
            EffectInstance effectInstance2 = this.activePotionsMap.put(effectInstance.getPotion(), effectInstance);
            if (effectInstance2 == null) {
                this.onNewPotionEffect(effectInstance);
            } else {
                this.onChangedPotionEffect(effectInstance, false);
            }
        }
    }

    public boolean isEntityUndead() {
        return this.getCreatureAttribute() == CreatureAttribute.UNDEAD;
    }

    @Nullable
    public EffectInstance removeActivePotionEffect(@Nullable Effect effect) {
        return this.activePotionsMap.remove(effect);
    }

    public boolean removePotionEffect(Effect effect) {
        EffectInstance effectInstance = this.removeActivePotionEffect(effect);
        if (effectInstance != null) {
            this.onFinishedPotionEffect(effectInstance);
            return false;
        }
        return true;
    }

    protected void onNewPotionEffect(EffectInstance effectInstance) {
        this.potionsNeedUpdate = true;
        if (!this.world.isRemote) {
            effectInstance.getPotion().applyAttributesModifiersToEntity(this, this.getAttributeManager(), effectInstance.getAmplifier());
        }
    }

    protected void onChangedPotionEffect(EffectInstance effectInstance, boolean bl) {
        this.potionsNeedUpdate = true;
        if (bl && !this.world.isRemote) {
            Effect effect = effectInstance.getPotion();
            effect.removeAttributesModifiersFromEntity(this, this.getAttributeManager(), effectInstance.getAmplifier());
            effect.applyAttributesModifiersToEntity(this, this.getAttributeManager(), effectInstance.getAmplifier());
        }
    }

    protected void onFinishedPotionEffect(EffectInstance effectInstance) {
        this.potionsNeedUpdate = true;
        if (!this.world.isRemote) {
            effectInstance.getPotion().removeAttributesModifiersFromEntity(this, this.getAttributeManager(), effectInstance.getAmplifier());
        }
    }

    public void heal(float f) {
        float f2 = this.getHealth();
        if (f2 > 0.0f) {
            this.setHealth(f2 + f);
        }
    }

    public float getHealth() {
        return this.dataManager.get(HEALTH).floatValue();
    }

    public void setHealth(float f) {
        this.dataManager.set(HEALTH, Float.valueOf(MathHelper.clamp(f, 0.0f, this.getMaxHealth())));
    }

    public boolean getShouldBeDead() {
        return this.getHealth() <= 0.0f;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        boolean bl;
        Object object;
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        if (this.world.isRemote) {
            return true;
        }
        if (this.getShouldBeDead()) {
            return true;
        }
        if (damageSource.isFireDamage() && this.isPotionActive(Effects.FIRE_RESISTANCE)) {
            return true;
        }
        if (this.isSleeping() && !this.world.isRemote) {
            this.wakeUp();
        }
        this.idleTime = 0;
        float f2 = f;
        if (!(damageSource != DamageSource.ANVIL && damageSource != DamageSource.FALLING_BLOCK || this.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty())) {
            this.getItemStackFromSlot(EquipmentSlotType.HEAD).damageItem((int)(f * 4.0f + this.rand.nextFloat() * f * 2.0f), this, LivingEntity::lambda$attackEntityFrom$4);
            f *= 0.75f;
        }
        boolean bl2 = false;
        float f3 = 0.0f;
        if (f > 0.0f && this.canBlockDamageSource(damageSource)) {
            Entity entity2;
            this.damageShield(f);
            f3 = f;
            f = 0.0f;
            if (!damageSource.isProjectile() && (entity2 = damageSource.getImmediateSource()) instanceof LivingEntity) {
                this.blockUsingShield((LivingEntity)entity2);
            }
            bl2 = true;
        }
        this.limbSwingAmount = 1.5f;
        boolean bl3 = true;
        if ((float)this.hurtResistantTime > 10.0f) {
            if (f <= this.lastDamage) {
                return true;
            }
            this.damageEntity(damageSource, f - this.lastDamage);
            this.lastDamage = f;
            bl3 = false;
        } else {
            this.lastDamage = f;
            this.hurtResistantTime = 20;
            this.damageEntity(damageSource, f);
            this.hurtTime = this.maxHurtTime = 10;
        }
        this.attackedAtYaw = 0.0f;
        Entity entity3 = damageSource.getTrueSource();
        if (entity3 != null) {
            if (entity3 instanceof LivingEntity) {
                this.setRevengeTarget((LivingEntity)entity3);
            }
            if (entity3 instanceof PlayerEntity) {
                this.recentlyHit = 100;
                this.attackingPlayer = (PlayerEntity)entity3;
            } else if (entity3 instanceof WolfEntity && ((TameableEntity)(object = (WolfEntity)entity3)).isTamed()) {
                this.recentlyHit = 100;
                LivingEntity livingEntity = ((TameableEntity)object).getOwner();
                this.attackingPlayer = livingEntity != null && livingEntity.getType() == EntityType.PLAYER ? (PlayerEntity)livingEntity : null;
            }
        }
        if (bl3) {
            if (bl2) {
                this.world.setEntityState(this, (byte)29);
            } else if (damageSource instanceof EntityDamageSource && ((EntityDamageSource)damageSource).getIsThornsDamage()) {
                this.world.setEntityState(this, (byte)33);
            } else {
                int n = damageSource == DamageSource.DROWN ? 36 : (damageSource.isFireDamage() ? 37 : (damageSource == DamageSource.SWEET_BERRY_BUSH ? 44 : 2));
                this.world.setEntityState(this, (byte)n);
            }
            if (damageSource != DamageSource.DROWN && (!bl2 || f > 0.0f)) {
                this.markVelocityChanged();
            }
            if (entity3 != null) {
                double d = entity3.getPosX() - this.getPosX();
                double d2 = entity3.getPosZ() - this.getPosZ();
                while (d * d + d2 * d2 < 1.0E-4) {
                    d = (Math.random() - Math.random()) * 0.01;
                    d2 = (Math.random() - Math.random()) * 0.01;
                }
                this.attackedAtYaw = (float)(MathHelper.atan2(d2, d) * 57.2957763671875 - (double)this.rotationYaw);
                this.applyKnockback(0.4f, d, d2);
            } else {
                this.attackedAtYaw = (int)(Math.random() * 2.0) * 180;
            }
        }
        if (this.getShouldBeDead()) {
            if (!this.checkTotemDeathProtection(damageSource)) {
                object = this.getDeathSound();
                if (bl3 && object != null) {
                    this.playSound((SoundEvent)object, this.getSoundVolume(), this.getSoundPitch());
                }
                this.onDeath(damageSource);
            }
        } else if (bl3) {
            this.playHurtSound(damageSource);
        }
        boolean bl4 = bl = !bl2 || f > 0.0f;
        if (bl) {
            this.lastDamageSource = damageSource;
            this.lastDamageStamp = this.world.getGameTime();
        }
        if (this instanceof ServerPlayerEntity) {
            CriteriaTriggers.ENTITY_HURT_PLAYER.trigger((ServerPlayerEntity)this, damageSource, f2, f, bl2);
            if (f3 > 0.0f && f3 < 3.4028235E37f) {
                ((ServerPlayerEntity)this).addStat(Stats.DAMAGE_BLOCKED_BY_SHIELD, Math.round(f3 * 10.0f));
            }
        }
        if (entity3 instanceof ServerPlayerEntity) {
            CriteriaTriggers.PLAYER_HURT_ENTITY.trigger((ServerPlayerEntity)entity3, this, damageSource, f2, f, bl2);
        }
        return bl;
    }

    protected void blockUsingShield(LivingEntity livingEntity) {
        livingEntity.constructKnockBackVector(this);
    }

    protected void constructKnockBackVector(LivingEntity livingEntity) {
        livingEntity.applyKnockback(0.5f, livingEntity.getPosX() - this.getPosX(), livingEntity.getPosZ() - this.getPosZ());
    }

    private boolean checkTotemDeathProtection(DamageSource damageSource) {
        if (damageSource.canHarmInCreative()) {
            return true;
        }
        ItemStack itemStack = null;
        for (Hand hand : Hand.values()) {
            ItemStack itemStack2 = this.getHeldItem(hand);
            if (itemStack2.getItem() != Items.TOTEM_OF_UNDYING) continue;
            itemStack = itemStack2.copy();
            itemStack2.shrink(1);
            break;
        }
        if (itemStack != null) {
            if (this instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)this;
                serverPlayerEntity.addStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING));
                CriteriaTriggers.USED_TOTEM.trigger(serverPlayerEntity, itemStack);
            }
            this.setHealth(1.0f);
            this.clearActivePotions();
            this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 900, 1));
            this.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 100, 1));
            this.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 800, 0));
            this.world.setEntityState(this, (byte)35);
        }
        return itemStack != null;
    }

    @Nullable
    public DamageSource getLastDamageSource() {
        if (this.world.getGameTime() - this.lastDamageStamp > 40L) {
            this.lastDamageSource = null;
        }
        return this.lastDamageSource;
    }

    protected void playHurtSound(DamageSource damageSource) {
        SoundEvent soundEvent = this.getHurtSound(damageSource);
        if (soundEvent != null) {
            this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
        }
    }

    private boolean canBlockDamageSource(DamageSource damageSource) {
        Object object;
        Entity entity2 = damageSource.getImmediateSource();
        boolean bl = false;
        if (entity2 instanceof AbstractArrowEntity && ((AbstractArrowEntity)(object = (AbstractArrowEntity)entity2)).getPierceLevel() > 0) {
            bl = true;
        }
        if (!damageSource.isUnblockable() && this.isActiveItemStackBlocking() && !bl && (object = damageSource.getDamageLocation()) != null) {
            Vector3d vector3d = this.getLook(1.0f);
            Vector3d vector3d2 = ((Vector3d)object).subtractReverse(this.getPositionVec()).normalize();
            vector3d2 = new Vector3d(vector3d2.x, 0.0, vector3d2.z);
            if (vector3d2.dotProduct(vector3d) < 0.0) {
                return false;
            }
        }
        return true;
    }

    private void renderBrokenItemStack(ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            if (!this.isSilent()) {
                this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_ITEM_BREAK, this.getSoundCategory(), 0.8f, 0.8f + this.world.rand.nextFloat() * 0.4f, true);
            }
            this.addItemParticles(itemStack, 5);
        }
    }

    public void onDeath(DamageSource damageSource) {
        if (!this.removed && !this.dead) {
            Entity entity2 = damageSource.getTrueSource();
            LivingEntity livingEntity = this.getAttackingEntity();
            if (this.scoreValue >= 0 && livingEntity != null) {
                livingEntity.awardKillScore(this, this.scoreValue, damageSource);
            }
            if (this.isSleeping()) {
                this.wakeUp();
            }
            this.dead = true;
            this.getCombatTracker().reset();
            if (this.world instanceof ServerWorld) {
                if (entity2 != null) {
                    entity2.func_241847_a((ServerWorld)this.world, this);
                }
                this.spawnDrops(damageSource);
                this.createWitherRose(livingEntity);
            }
            this.world.setEntityState(this, (byte)3);
            this.setPose(Pose.DYING);
        }
    }

    protected void createWitherRose(@Nullable LivingEntity livingEntity) {
        if (!this.world.isRemote) {
            boolean bl = false;
            if (livingEntity instanceof WitherEntity) {
                Object object;
                if (this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
                    object = this.getPosition();
                    BlockState blockState = Blocks.WITHER_ROSE.getDefaultState();
                    if (this.world.getBlockState((BlockPos)object).isAir() && blockState.isValidPosition(this.world, (BlockPos)object)) {
                        this.world.setBlockState((BlockPos)object, blockState, 0);
                        bl = true;
                    }
                }
                if (!bl) {
                    object = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(Items.WITHER_ROSE));
                    this.world.addEntity((Entity)object);
                }
            }
        }
    }

    protected void spawnDrops(DamageSource damageSource) {
        boolean bl;
        Entity entity2 = damageSource.getTrueSource();
        int n = entity2 instanceof PlayerEntity ? EnchantmentHelper.getLootingModifier((LivingEntity)entity2) : 0;
        boolean bl2 = bl = this.recentlyHit > 0;
        if (this.func_230282_cS_() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            this.dropLoot(damageSource, bl);
            this.dropSpecialItems(damageSource, n, bl);
        }
        this.dropInventory();
        this.dropExperience();
    }

    protected void dropInventory() {
    }

    protected void dropExperience() {
        if (!this.world.isRemote && (this.isPlayer() || this.recentlyHit > 0 && this.canDropLoot() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT))) {
            int n;
            for (int i = this.getExperiencePoints(this.attackingPlayer); i > 0; i -= n) {
                n = ExperienceOrbEntity.getXPSplit(i);
                this.world.addEntity(new ExperienceOrbEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), n));
            }
        }
    }

    protected void dropSpecialItems(DamageSource damageSource, int n, boolean bl) {
    }

    public ResourceLocation getLootTableResourceLocation() {
        return this.getType().getLootTable();
    }

    protected void dropLoot(DamageSource damageSource, boolean bl) {
        ResourceLocation resourceLocation = this.getLootTableResourceLocation();
        LootTable lootTable = this.world.getServer().getLootTableManager().getLootTableFromLocation(resourceLocation);
        LootContext.Builder builder = this.getLootContextBuilder(bl, damageSource);
        lootTable.generate(builder.build(LootParameterSets.ENTITY), this::entityDropItem);
    }

    protected LootContext.Builder getLootContextBuilder(boolean bl, DamageSource damageSource) {
        LootContext.Builder builder = new LootContext.Builder((ServerWorld)this.world).withRandom(this.rand).withParameter(LootParameters.THIS_ENTITY, this).withParameter(LootParameters.field_237457_g_, this.getPositionVec()).withParameter(LootParameters.DAMAGE_SOURCE, damageSource).withNullableParameter(LootParameters.KILLER_ENTITY, damageSource.getTrueSource()).withNullableParameter(LootParameters.DIRECT_KILLER_ENTITY, damageSource.getImmediateSource());
        if (bl && this.attackingPlayer != null) {
            builder = builder.withParameter(LootParameters.LAST_DAMAGE_PLAYER, this.attackingPlayer).withLuck(this.attackingPlayer.getLuck());
        }
        return builder;
    }

    public void applyKnockback(float f, double d, double d2) {
        if (!((f = (float)((double)f * (1.0 - this.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)))) <= 0.0f)) {
            this.isAirBorne = true;
            Vector3d vector3d = this.getMotion();
            Vector3d vector3d2 = new Vector3d(d, 0.0, d2).normalize().scale(f);
            this.setMotion(vector3d.x / 2.0 - vector3d2.x, this.onGround ? Math.min(0.4, vector3d.y / 2.0 + (double)f) : vector3d.y, vector3d.z / 2.0 - vector3d2.z);
        }
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_GENERIC_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GENERIC_DEATH;
    }

    protected SoundEvent getFallSound(int n) {
        return n > 4 ? SoundEvents.ENTITY_GENERIC_BIG_FALL : SoundEvents.ENTITY_GENERIC_SMALL_FALL;
    }

    protected SoundEvent getDrinkSound(ItemStack itemStack) {
        return itemStack.getDrinkSound();
    }

    public SoundEvent getEatSound(ItemStack itemStack) {
        return itemStack.getEatSound();
    }

    @Override
    public void setOnGround(boolean bl) {
        super.setOnGround(bl);
        if (bl) {
            this.field_233624_bE_ = Optional.empty();
        }
    }

    public Optional<BlockPos> func_233644_dn_() {
        return this.field_233624_bE_;
    }

    public boolean isOnLadder() {
        if (this.isSpectator()) {
            return true;
        }
        BlockPos blockPos = this.getPosition();
        BlockState blockState = this.getBlockState();
        Block block = blockState.getBlock();
        if (block.isIn(BlockTags.CLIMBABLE)) {
            this.field_233624_bE_ = Optional.of(blockPos);
            return false;
        }
        if (block instanceof TrapDoorBlock && this.canGoThroughtTrapDoorOnLadder(blockPos, blockState)) {
            this.field_233624_bE_ = Optional.of(blockPos);
            return false;
        }
        return true;
    }

    public BlockState getBlockState() {
        return this.world.getBlockState(this.getPosition());
    }

    private boolean canGoThroughtTrapDoorOnLadder(BlockPos blockPos, BlockState blockState) {
        BlockState blockState2;
        return blockState.get(TrapDoorBlock.OPEN) == false || !(blockState2 = this.world.getBlockState(blockPos.down())).isIn(Blocks.LADDER) || blockState2.get(LadderBlock.FACING) != blockState.get(TrapDoorBlock.HORIZONTAL_FACING);
    }

    @Override
    public boolean isAlive() {
        return !this.removed && this.getHealth() > 0.0f;
    }

    @Override
    public boolean onLivingFall(float f, float f2) {
        boolean bl = super.onLivingFall(f, f2);
        int n = this.calculateFallDamage(f, f2);
        if (n > 0) {
            this.playSound(this.getFallSound(n), 1.0f, 1.0f);
            this.playFallSound();
            this.attackEntityFrom(DamageSource.FALL, n);
            if (this instanceof ClientPlayerEntity) {
                venusfr.getInstance().getEventBus().post(new EventDamageReceive(EventDamageReceive.DamageType.FALL));
            }
            return false;
        }
        return bl;
    }

    protected int calculateFallDamage(float f, float f2) {
        EffectInstance effectInstance = this.getActivePotionEffect(Effects.JUMP_BOOST);
        float f3 = effectInstance == null ? 0.0f : (float)(effectInstance.getAmplifier() + 1);
        return MathHelper.ceil((f - 3.0f - f3) * f2);
    }

    protected void playFallSound() {
        int n;
        int n2;
        int n3;
        BlockState blockState;
        if (!this.isSilent() && !(blockState = this.world.getBlockState(new BlockPos(n3 = MathHelper.floor(this.getPosX()), n2 = MathHelper.floor(this.getPosY() - (double)0.2f), n = MathHelper.floor(this.getPosZ())))).isAir()) {
            SoundType soundType = blockState.getSoundType();
            this.playSound(soundType.getFallSound(), soundType.getVolume() * 0.5f, soundType.getPitch() * 0.75f);
        }
    }

    @Override
    public void performHurtAnimation() {
        this.hurtTime = this.maxHurtTime = 10;
        this.attackedAtYaw = 0.0f;
    }

    public int getTotalArmorValue() {
        return MathHelper.floor(this.getAttributeValue(Attributes.ARMOR));
    }

    protected void damageArmor(DamageSource damageSource, float f) {
    }

    protected void damageShield(float f) {
    }

    protected float applyArmorCalculations(DamageSource damageSource, float f) {
        if (!damageSource.isUnblockable()) {
            this.damageArmor(damageSource, f);
            f = CombatRules.getDamageAfterAbsorb(f, this.getTotalArmorValue(), (float)this.getAttributeValue(Attributes.ARMOR_TOUGHNESS));
        }
        return f;
    }

    protected float applyPotionDamageCalculations(DamageSource damageSource, float f) {
        int n;
        int n2;
        float f2;
        float f3;
        float f4;
        if (damageSource.isDamageAbsolute()) {
            return f;
        }
        if (this.isPotionActive(Effects.RESISTANCE) && damageSource != DamageSource.OUT_OF_WORLD && (f4 = (f3 = f) - (f = Math.max((f2 = f * (float)(n2 = 25 - (n = (this.getActivePotionEffect(Effects.RESISTANCE).getAmplifier() + 1) * 5))) / 25.0f, 0.0f))) > 0.0f && f4 < 3.4028235E37f) {
            if (this instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity)this).addStat(Stats.DAMAGE_RESISTED, Math.round(f4 * 10.0f));
            } else if (damageSource.getTrueSource() instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity)damageSource.getTrueSource()).addStat(Stats.DAMAGE_DEALT_RESISTED, Math.round(f4 * 10.0f));
            }
        }
        if (f <= 0.0f) {
            return 0.0f;
        }
        n = EnchantmentHelper.getEnchantmentModifierDamage(this.getArmorInventoryList(), damageSource);
        if (n > 0) {
            f = CombatRules.getDamageAfterMagicAbsorb(f, n);
        }
        return f;
    }

    protected void damageEntity(DamageSource damageSource, float f) {
        if (!this.isInvulnerableTo(damageSource)) {
            f = this.applyArmorCalculations(damageSource, f);
            f = this.applyPotionDamageCalculations(damageSource, f);
            float f2 = Math.max(f - this.getAbsorptionAmount(), 0.0f);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (f - f2));
            float f3 = f - f2;
            if (f3 > 0.0f && f3 < 3.4028235E37f && damageSource.getTrueSource() instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity)damageSource.getTrueSource()).addStat(Stats.DAMAGE_DEALT_ABSORBED, Math.round(f3 * 10.0f));
            }
            if (f2 != 0.0f) {
                float f4 = this.getHealth();
                this.setHealth(f4 - f2);
                this.getCombatTracker().trackDamage(damageSource, f4, f2);
                this.setAbsorptionAmount(this.getAbsorptionAmount() - f2);
            }
        }
    }

    public CombatTracker getCombatTracker() {
        return this.combatTracker;
    }

    @Nullable
    public LivingEntity getAttackingEntity() {
        if (this.combatTracker.getBestAttacker() != null) {
            return this.combatTracker.getBestAttacker();
        }
        if (this.attackingPlayer != null) {
            return this.attackingPlayer;
        }
        return this.revengeTarget != null ? this.revengeTarget : null;
    }

    public final float getMaxHealth() {
        return (float)this.getAttributeValue(Attributes.MAX_HEALTH);
    }

    public final int getArrowCountInEntity() {
        return this.dataManager.get(ARROW_COUNT_IN_ENTITY);
    }

    public final void setArrowCountInEntity(int n) {
        this.dataManager.set(ARROW_COUNT_IN_ENTITY, n);
    }

    public final int getBeeStingCount() {
        return this.dataManager.get(BEE_STING_COUNT);
    }

    public final void setBeeStingCount(int n) {
        this.dataManager.set(BEE_STING_COUNT, n);
    }

    private int getArmSwingAnimationEnd() {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        SwingAnimation swingAnimation = functionRegistry.getSwingAnimation();
        if (swingAnimation.isState() && this instanceof ClientPlayerEntity) {
            return 25 - ((Float)swingAnimation.swingSpeed.get()).intValue() * 2;
        }
        if (EffectUtils.hasMiningSpeedup(this)) {
            return 6 - (1 + EffectUtils.getMiningSpeedup(this));
        }
        return this.isPotionActive(Effects.MINING_FATIGUE) ? 6 + (1 + this.getActivePotionEffect(Effects.MINING_FATIGUE).getAmplifier()) * 2 : 6;
    }

    public void swingArm(Hand hand) {
        this.swing(hand, true);
    }

    public void swing(Hand hand, boolean bl) {
        if (!this.isSwingInProgress || this.swingProgressInt >= this.getArmSwingAnimationEnd() / 2 || this.swingProgressInt < 0) {
            this.swingProgressInt = -1;
            this.isSwingInProgress = true;
            this.swingingHand = hand;
            if (this.world instanceof ServerWorld) {
                SAnimateHandPacket sAnimateHandPacket = new SAnimateHandPacket(this, hand == Hand.MAIN_HAND ? 0 : 3);
                ServerChunkProvider serverChunkProvider = ((ServerWorld)this.world).getChunkProvider();
                if (bl) {
                    serverChunkProvider.sendToTrackingAndSelf(this, sAnimateHandPacket);
                } else {
                    serverChunkProvider.sendToAllTracking(this, sAnimateHandPacket);
                }
            }
        }
    }

    @Override
    public void handleStatusUpdate(byte by) {
        switch (by) {
            case 2: 
            case 33: 
            case 36: 
            case 37: 
            case 44: {
                DamageSource damageSource;
                SoundEvent soundEvent;
                boolean bl = by == 33;
                boolean bl2 = by == 36;
                boolean bl3 = by == 37;
                boolean bl4 = by == 44;
                this.limbSwingAmount = 1.5f;
                this.hurtResistantTime = 20;
                this.hurtTime = this.maxHurtTime = 10;
                this.attackedAtYaw = 0.0f;
                if (bl) {
                    this.playSound(SoundEvents.ENCHANT_THORNS_HIT, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
                }
                if ((soundEvent = this.getHurtSound(damageSource = bl3 ? DamageSource.ON_FIRE : (bl2 ? DamageSource.DROWN : (bl4 ? DamageSource.SWEET_BERRY_BUSH : DamageSource.GENERIC)))) != null) {
                    this.playSound(soundEvent, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
                }
                this.attackEntityFrom(DamageSource.GENERIC, 0.0f);
                break;
            }
            case 3: {
                SoundEvent soundEvent = this.getDeathSound();
                if (soundEvent != null) {
                    this.playSound(soundEvent, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
                }
                if (this instanceof PlayerEntity) break;
                this.setHealth(0.0f);
                this.onDeath(DamageSource.GENERIC);
                break;
            }
            default: {
                super.handleStatusUpdate(by);
                break;
            }
            case 29: {
                this.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0f, 0.8f + this.world.rand.nextFloat() * 0.4f);
                break;
            }
            case 30: {
                this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8f, 0.8f + this.world.rand.nextFloat() * 0.4f);
                break;
            }
            case 46: {
                int n = 128;
                for (int i = 0; i < 128; ++i) {
                    double d = (double)i / 127.0;
                    float f = (this.rand.nextFloat() - 0.5f) * 0.2f;
                    float f2 = (this.rand.nextFloat() - 0.5f) * 0.2f;
                    float f3 = (this.rand.nextFloat() - 0.5f) * 0.2f;
                    double d2 = MathHelper.lerp(d, this.prevPosX, this.getPosX()) + (this.rand.nextDouble() - 0.5) * (double)this.getWidth() * 2.0;
                    double d3 = MathHelper.lerp(d, this.prevPosY, this.getPosY()) + this.rand.nextDouble() * (double)this.getHeight();
                    double d4 = MathHelper.lerp(d, this.prevPosZ, this.getPosZ()) + (this.rand.nextDouble() - 0.5) * (double)this.getWidth() * 2.0;
                    this.world.addParticle(ParticleTypes.PORTAL, d2, d3, d4, f, f2, f3);
                }
                break;
            }
            case 47: {
                this.renderBrokenItemStack(this.getItemStackFromSlot(EquipmentSlotType.MAINHAND));
                break;
            }
            case 48: {
                this.renderBrokenItemStack(this.getItemStackFromSlot(EquipmentSlotType.OFFHAND));
                break;
            }
            case 49: {
                this.renderBrokenItemStack(this.getItemStackFromSlot(EquipmentSlotType.HEAD));
                break;
            }
            case 50: {
                this.renderBrokenItemStack(this.getItemStackFromSlot(EquipmentSlotType.CHEST));
                break;
            }
            case 51: {
                this.renderBrokenItemStack(this.getItemStackFromSlot(EquipmentSlotType.LEGS));
                break;
            }
            case 52: {
                this.renderBrokenItemStack(this.getItemStackFromSlot(EquipmentSlotType.FEET));
                break;
            }
            case 54: {
                HoneyBlock.livingSlideParticles(this);
                break;
            }
            case 55: {
                this.swapHands();
            }
        }
    }

    private void swapHands() {
        ItemStack itemStack = this.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
        this.setItemStackToSlot(EquipmentSlotType.OFFHAND, this.getItemStackFromSlot(EquipmentSlotType.MAINHAND));
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemStack);
    }

    @Override
    protected void outOfWorld() {
        this.attackEntityFrom(DamageSource.OUT_OF_WORLD, 4.0f);
    }

    protected void updateArmSwingProgress() {
        int n = this.getArmSwingAnimationEnd();
        if (this.isSwingInProgress) {
            ++this.swingProgressInt;
            if (this.swingProgressInt >= n) {
                this.swingProgressInt = 0;
                this.isSwingInProgress = false;
            }
        } else {
            this.swingProgressInt = 0;
        }
        this.swingProgress = (float)this.swingProgressInt / (float)n;
    }

    @Nullable
    public ModifiableAttributeInstance getAttribute(Attribute attribute) {
        return this.getAttributeManager().createInstanceIfAbsent(attribute);
    }

    public double getAttributeValue(Attribute attribute) {
        return this.getAttributeManager().getAttributeValue(attribute);
    }

    public double getBaseAttributeValue(Attribute attribute) {
        return this.getAttributeManager().getAttributeBaseValue(attribute);
    }

    public AttributeModifierManager getAttributeManager() {
        return this.attributes;
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEFINED;
    }

    public ItemStack getHeldItemMainhand() {
        return this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
    }

    public ItemStack getHeldItemOffhand() {
        return this.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
    }

    public boolean canEquip(Item item) {
        return this.func_233634_a_(arg_0 -> LivingEntity.lambda$canEquip$5(item, arg_0));
    }

    public boolean func_233634_a_(Predicate<Item> predicate) {
        return predicate.test(this.getHeldItemMainhand().getItem()) || predicate.test(this.getHeldItemOffhand().getItem());
    }

    public ItemStack getHeldItem(Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            return this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
        }
        if (hand == Hand.OFF_HAND) {
            return this.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
        }
        throw new IllegalArgumentException("Invalid hand " + hand);
    }

    public void setHeldItem(Hand hand, ItemStack itemStack) {
        if (hand == Hand.MAIN_HAND) {
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemStack);
        } else {
            if (hand != Hand.OFF_HAND) {
                throw new IllegalArgumentException("Invalid hand " + hand);
            }
            this.setItemStackToSlot(EquipmentSlotType.OFFHAND, itemStack);
        }
    }

    public boolean hasItemInSlot(EquipmentSlotType equipmentSlotType) {
        return !this.getItemStackFromSlot(equipmentSlotType).isEmpty();
    }

    @Override
    public abstract Iterable<ItemStack> getArmorInventoryList();

    public abstract ItemStack getItemStackFromSlot(EquipmentSlotType var1);

    @Override
    public abstract void setItemStackToSlot(EquipmentSlotType var1, ItemStack var2);

    public float getArmorCoverPercentage() {
        Iterable<ItemStack> iterable = this.getArmorInventoryList();
        int n = 0;
        int n2 = 0;
        for (ItemStack itemStack : iterable) {
            if (!itemStack.isEmpty()) {
                ++n2;
            }
            ++n;
        }
        return n > 0 ? (float)n2 / (float)n : 0.0f;
    }

    @Override
    public void setSprinting(boolean bl) {
        super.setSprinting(bl);
        ModifiableAttributeInstance modifiableAttributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (modifiableAttributeInstance.getModifier(SPRINTING_SPEED_BOOST_ID) != null) {
            modifiableAttributeInstance.removeModifier(SPRINTING_SPEED_BOOST);
        }
        if (bl) {
            modifiableAttributeInstance.applyNonPersistentModifier(SPRINTING_SPEED_BOOST);
        }
    }

    protected float getSoundVolume() {
        return 1.0f;
    }

    protected float getSoundPitch() {
        return this.isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.5f : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f;
    }

    protected boolean isMovementBlocked() {
        return this.getShouldBeDead();
    }

    @Override
    public void applyEntityCollision(Entity entity2) {
        if (!this.isSleeping()) {
            super.applyEntityCollision(entity2);
        }
    }

    private void func_233628_a_(Entity entity2) {
        Vector3d vector3d = !entity2.removed && !this.world.getBlockState(entity2.getPosition()).getBlock().isIn(BlockTags.PORTALS) ? entity2.func_230268_c_(this) : new Vector3d(entity2.getPosX(), entity2.getPosY() + (double)entity2.getHeight(), entity2.getPosZ());
        this.setPositionAndUpdate(vector3d.x, vector3d.y, vector3d.z);
    }

    @Override
    public boolean getAlwaysRenderNameTagForRender() {
        return this.isCustomNameVisible();
    }

    protected float getJumpUpwardsMotion() {
        return 0.42f * this.getJumpFactor();
    }

    protected void jump() {
        Object object = this;
        if (object instanceof ClientPlayerEntity) {
            ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity)object;
            venusfr.getInstance().getEventBus().post(this.jumpEvent);
        }
        float f = this.getJumpUpwardsMotion();
        if (this.isPotionActive(Effects.JUMP_BOOST)) {
            f += 0.1f * (float)(this.getActivePotionEffect(Effects.JUMP_BOOST).getAmplifier() + 1);
        }
        object = this.getMotion();
        this.setMotion(((Vector3d)object).x, f, ((Vector3d)object).z);
        if (this.isSprinting()) {
            float f2 = (this.rotationYawOffset == -2.14748365E9f ? this.rotationYaw : this.rotationYawOffset) * ((float)Math.PI / 180);
            this.setMotion(this.getMotion().add(-MathHelper.sin(f2) * 0.2f, 0.0, MathHelper.cos(f2) * 0.2f));
        }
        this.isAirBorne = true;
    }

    public void jump(float f, float f2) {
        Vector3d vector3d = this.getMotion();
        this.setMotion(vector3d.x, f, vector3d.z);
        if (this.isSprinting()) {
            float f3 = (this.rotationYawOffset == -2.14748365E9f ? this.rotationYaw : this.rotationYawOffset) * ((float)Math.PI / 180);
            this.setMotion(this.getMotion().add(-MathHelper.sin(f3) * f2, 0.0, MathHelper.cos(f3) * f2));
        }
        this.isAirBorne = true;
    }

    protected void handleFluidSneak() {
        this.setMotion(this.getMotion().add(0.0, -0.04f, 0.0));
    }

    protected void handleFluidJump(ITag<Fluid> iTag) {
        this.setMotion(this.getMotion().add(0.0, 0.04f, 0.0));
    }

    protected float getWaterSlowDown() {
        return 0.8f;
    }

    public boolean func_230285_a_(Fluid fluid) {
        return true;
    }

    public void travel(Vector3d vector3d) {
        if (this.isServerWorld() || this.canPassengerSteer()) {
            boolean bl;
            double d = 0.08;
            boolean bl2 = bl = this.getMotion().y <= 0.0;
            if (bl && this.isPotionActive(Effects.SLOW_FALLING)) {
                d = 0.01;
                this.fallDistance = 0.0f;
            }
            FluidState fluidState = this.world.getFluidState(this.getPosition());
            if (this.isInWater() && this.func_241208_cS_() && !this.func_230285_a_(fluidState.getFluid())) {
                double d2 = this.getPosY();
                float f = this.isSprinting() ? 0.9f : this.getWaterSlowDown();
                float f2 = 0.02f;
                float f3 = EnchantmentHelper.getDepthStriderModifier(this);
                if (f3 > 3.0f) {
                    f3 = 3.0f;
                }
                if (!this.onGround) {
                    f3 *= 0.5f;
                }
                if (f3 > 0.0f) {
                    f += (0.54600006f - f) * f3 / 3.0f;
                    f2 += (this.getAIMoveSpeed() - f2) * f3 / 3.0f;
                }
                if (this.isPotionActive(Effects.DOLPHINS_GRACE)) {
                    f = 0.96f;
                }
                this.moveRelative(f2, vector3d);
                this.move(MoverType.SELF, this.getMotion());
                Vector3d vector3d2 = this.getMotion();
                if (this.collidedHorizontally && this.isOnLadder()) {
                    vector3d2 = new Vector3d(vector3d2.x, 0.2, vector3d2.z);
                }
                this.setMotion(vector3d2.mul(f, 0.8f, f));
                Vector3d vector3d3 = this.func_233626_a_(d, bl, this.getMotion());
                this.setMotion(vector3d3);
                if (this.collidedHorizontally && this.isOffsetPositionInLiquid(vector3d3.x, vector3d3.y + (double)0.6f - this.getPosY() + d2, vector3d3.z)) {
                    this.setMotion(vector3d3.x, 0.3f, vector3d3.z);
                }
            } else if (this.isInLava() && this.func_241208_cS_() && !this.func_230285_a_(fluidState.getFluid())) {
                Vector3d vector3d4;
                double d3 = this.getPosY();
                this.moveRelative(0.02f, vector3d);
                this.move(MoverType.SELF, this.getMotion());
                if (this.func_233571_b_(FluidTags.LAVA) <= this.func_233579_cu_()) {
                    this.setMotion(this.getMotion().mul(0.5, 0.8f, 0.5));
                    vector3d4 = this.func_233626_a_(d, bl, this.getMotion());
                    this.setMotion(vector3d4);
                } else {
                    this.setMotion(this.getMotion().scale(0.5));
                }
                if (!this.hasNoGravity()) {
                    this.setMotion(this.getMotion().add(0.0, -d / 4.0, 0.0));
                }
                vector3d4 = this.getMotion();
                if (this.collidedHorizontally && this.isOffsetPositionInLiquid(vector3d4.x, vector3d4.y + (double)0.6f - this.getPosY() + d3, vector3d4.z)) {
                    this.setMotion(vector3d4.x, 0.3f, vector3d4.z);
                }
            } else if (this.isElytraFlying()) {
                double d4;
                float f;
                double d5;
                Vector3d vector3d5 = this.getMotion();
                if (vector3d5.y > -0.5) {
                    this.fallDistance = 1.0f;
                }
                Vector3d vector3d6 = this.getLookVec();
                float f4 = this.rotationPitch * ((float)Math.PI / 180);
                double d6 = Math.sqrt(vector3d6.x * vector3d6.x + vector3d6.z * vector3d6.z);
                double d7 = Math.sqrt(LivingEntity.horizontalMag(vector3d5));
                double d8 = vector3d6.length();
                float f5 = MathHelper.cos(f4);
                f5 = (float)((double)f5 * (double)f5 * Math.min(1.0, d8 / 0.4));
                vector3d5 = this.getMotion().add(0.0, d * (-1.0 + (double)f5 * 0.75), 0.0);
                if (vector3d5.y < 0.0 && d6 > 0.0) {
                    d5 = vector3d5.y * -0.1 * (double)f5;
                    vector3d5 = vector3d5.add(vector3d6.x * d5 / d6, d5, vector3d6.z * d5 / d6);
                }
                if (f4 < 0.0f && d6 > 0.0) {
                    d5 = d7 * (double)(-MathHelper.sin(f4)) * 0.04;
                    vector3d5 = vector3d5.add(-vector3d6.x * d5 / d6, d5 * 3.2, -vector3d6.z * d5 / d6);
                }
                if (d6 > 0.0) {
                    vector3d5 = vector3d5.add((vector3d6.x / d6 * d7 - vector3d5.x) * 0.1, 0.0, (vector3d6.z / d6 * d7 - vector3d5.z) * 0.1);
                }
                this.setMotion(vector3d5.mul(0.99f, 0.98f, 0.99f));
                this.move(MoverType.SELF, this.getMotion());
                if (this.collidedHorizontally && !this.world.isRemote && (f = (float)((d4 = d7 - (d5 = Math.sqrt(LivingEntity.horizontalMag(this.getMotion())))) * 10.0 - 3.0)) > 0.0f) {
                    this.playSound(this.getFallSound((int)f), 1.0f, 1.0f);
                    this.attackEntityFrom(DamageSource.FLY_INTO_WALL, f);
                }
                if (this.onGround && !this.world.isRemote) {
                    this.setFlag(7, true);
                }
            } else {
                BlockPos blockPos = this.getPositionUnderneath();
                float f = this.world.getBlockState(blockPos).getBlock().getSlipperiness();
                float f6 = this.onGround ? f * 0.91f : 0.91f;
                Vector3d vector3d7 = this.func_233633_a_(vector3d, f);
                double d9 = vector3d7.y;
                if (this.isPotionActive(Effects.LEVITATION)) {
                    d9 += (0.05 * (double)(this.getActivePotionEffect(Effects.LEVITATION).getAmplifier() + 1) - vector3d7.y) * 0.2;
                    this.fallDistance = 0.0f;
                } else if (this.world.isRemote && !this.world.isBlockLoaded(blockPos)) {
                    d9 = this.getPosY() > 0.0 ? -0.1 : 0.0;
                } else if (!this.hasNoGravity()) {
                    d9 -= d;
                }
                this.setMotion(vector3d7.x * (double)f6, d9 * (double)0.98f, vector3d7.z * (double)f6);
            }
        }
        this.func_233629_a_(this, this instanceof IFlyingAnimal);
    }

    public void func_233629_a_(LivingEntity livingEntity, boolean bl) {
        double d;
        double d2;
        livingEntity.prevLimbSwingAmount = livingEntity.limbSwingAmount;
        double d3 = livingEntity.getPosX() - livingEntity.prevPosX;
        float f = MathHelper.sqrt(d3 * d3 + (d2 = bl ? livingEntity.getPosY() - livingEntity.prevPosY : 0.0) * d2 + (d = livingEntity.getPosZ() - livingEntity.prevPosZ) * d) * 4.0f;
        if (f > 1.0f) {
            f = 1.0f;
        }
        livingEntity.limbSwingAmount += (f - livingEntity.limbSwingAmount) * 0.4f;
        livingEntity.limbSwing += livingEntity.limbSwingAmount;
    }

    public Vector3d func_233633_a_(Vector3d vector3d, float f) {
        this.moveRelative(this.getRelevantMoveFactor(f), vector3d);
        this.setMotion(this.handleOnClimbable(this.getMotion()));
        this.move(MoverType.SELF, this.getMotion());
        Vector3d vector3d2 = this.getMotion();
        if ((this.collidedHorizontally || this.isJumping) && this.isOnLadder()) {
            vector3d2 = new Vector3d(vector3d2.x, 0.2, vector3d2.z);
        }
        return vector3d2;
    }

    public Vector3d func_233626_a_(double d, boolean bl, Vector3d vector3d) {
        if (!this.hasNoGravity() && !this.isSprinting()) {
            double d2 = bl && Math.abs(vector3d.y - 0.005) >= 0.003 && Math.abs(vector3d.y - d / 16.0) < 0.003 ? -0.003 : vector3d.y - d / 16.0;
            return new Vector3d(vector3d.x, d2, vector3d.z);
        }
        return vector3d;
    }

    private Vector3d handleOnClimbable(Vector3d vector3d) {
        if (this.isOnLadder()) {
            this.fallDistance = 0.0f;
            float f = 0.15f;
            double d = MathHelper.clamp(vector3d.x, (double)-0.15f, (double)0.15f);
            double d2 = MathHelper.clamp(vector3d.z, (double)-0.15f, (double)0.15f);
            double d3 = Math.max(vector3d.y, (double)-0.15f);
            if (d3 < 0.0 && !this.getBlockState().isIn(Blocks.SCAFFOLDING) && this.hasStoppedClimbing() && this instanceof PlayerEntity) {
                d3 = 0.0;
            }
            vector3d = new Vector3d(d, d3, d2);
        }
        return vector3d;
    }

    private float getRelevantMoveFactor(float f) {
        return this.onGround ? this.getAIMoveSpeed() * (0.21600002f / (f * f * f)) : this.jumpMovementFactor;
    }

    public float getAIMoveSpeed() {
        return this.landMovementFactor;
    }

    public void setAIMoveSpeed(float f) {
        this.landMovementFactor = f;
    }

    public boolean attackEntityAsMob(Entity entity2) {
        this.setLastAttackedEntity(entity2);
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        this.updateActiveHand();
        this.updateSwimAnimation();
        if (!this.world.isRemote) {
            int n;
            int n2 = this.getArrowCountInEntity();
            if (n2 > 0) {
                if (this.arrowHitTimer <= 0) {
                    this.arrowHitTimer = 20 * (30 - n2);
                }
                --this.arrowHitTimer;
                if (this.arrowHitTimer <= 0) {
                    this.setArrowCountInEntity(n2 - 1);
                }
            }
            if ((n = this.getBeeStingCount()) > 0) {
                if (this.beeStingRemovalCooldown <= 0) {
                    this.beeStingRemovalCooldown = 20 * (30 - n);
                }
                --this.beeStingRemovalCooldown;
                if (this.beeStingRemovalCooldown <= 0) {
                    this.setBeeStingCount(n - 1);
                }
            }
            this.func_241353_q_();
            if (this.ticksExisted % 20 == 0) {
                this.getCombatTracker().reset();
            }
            if (!this.glowing) {
                boolean bl = this.isPotionActive(Effects.GLOWING);
                if (this.getFlag(1) != bl) {
                    this.setFlag(6, bl);
                }
            }
            if (this.isSleeping() && !this.isInValidBed()) {
                this.wakeUp();
            }
        }
        this.livingTick();
        double d = this.getPosX() - this.prevPosX;
        double d2 = this.getPosZ() - this.prevPosZ;
        float f = (float)(d * d + d2 * d2);
        float f2 = this.renderYawOffset;
        float f3 = 0.0f;
        this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
        float f4 = 0.0f;
        if (f > 0.0025000002f) {
            f4 = 1.0f;
            f3 = (float)Math.sqrt(f) * 3.0f;
            float f5 = (float)MathHelper.atan2(d2, d) * 57.295776f - 90.0f;
            float f6 = MathHelper.abs(MathHelper.wrapDegrees(this.rotationYaw) - f5);
            f2 = 95.0f < f6 && f6 < 265.0f ? f5 - 180.0f : f5;
        }
        if (this.swingProgress > 0.0f) {
            f2 = this.rotationYaw;
        }
        if (!this.onGround) {
            f4 = 0.0f;
        }
        this.onGroundSpeedFactor += (f4 - this.onGroundSpeedFactor) * 0.3f;
        this.world.getProfiler().startSection("headTurn");
        f3 = this.updateDistance(f2, f3);
        this.world.getProfiler().endSection();
        this.world.getProfiler().startSection("rangeChecks");
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        while (this.renderYawOffset - this.prevRenderYawOffset < -180.0f) {
            this.prevRenderYawOffset -= 360.0f;
        }
        while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0f) {
            this.prevRenderYawOffset += 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        while (this.rotationYawHead - this.prevRotationYawHead < -180.0f) {
            this.prevRotationYawHead -= 360.0f;
        }
        while (this.rotationYawHead - this.prevRotationYawHead >= 180.0f) {
            this.prevRotationYawHead += 360.0f;
        }
        this.world.getProfiler().endSection();
        this.movedDistance += f3;
        this.ticksElytraFlying = this.isElytraFlying() ? ++this.ticksElytraFlying : 0;
        if (this.isSleeping()) {
            this.rotationPitch = 0.0f;
        }
    }

    private void func_241353_q_() {
        Map<EquipmentSlotType, ItemStack> map = this.func_241354_r_();
        if (map != null) {
            this.func_241342_a_(map);
            if (!map.isEmpty()) {
                this.func_241344_b_(map);
            }
        }
    }

    @Nullable
    private Map<EquipmentSlotType, ItemStack> func_241354_r_() {
        EnumMap<EquipmentSlotType, ItemStack> enumMap = null;
        block4: for (EquipmentSlotType equipmentSlotType : EquipmentSlotType.values()) {
            ItemStack itemStack;
            switch (equipmentSlotType.getSlotType()) {
                case HAND: {
                    itemStack = this.getItemInHand(equipmentSlotType);
                    break;
                }
                case ARMOR: {
                    itemStack = this.getArmorInSlot(equipmentSlotType);
                    break;
                }
                default: {
                    continue block4;
                }
            }
            ItemStack itemStack2 = this.getItemStackFromSlot(equipmentSlotType);
            if (ItemStack.areItemStacksEqual(itemStack2, itemStack)) continue;
            if (enumMap == null) {
                enumMap = Maps.newEnumMap(EquipmentSlotType.class);
            }
            enumMap.put(equipmentSlotType, itemStack2);
            if (!itemStack.isEmpty()) {
                this.getAttributeManager().removeModifiers(itemStack.getAttributeModifiers(equipmentSlotType));
            }
            if (itemStack2.isEmpty()) continue;
            this.getAttributeManager().reapplyModifiers(itemStack2.getAttributeModifiers(equipmentSlotType));
        }
        return enumMap;
    }

    private void func_241342_a_(Map<EquipmentSlotType, ItemStack> map) {
        ItemStack itemStack = map.get((Object)EquipmentSlotType.MAINHAND);
        ItemStack itemStack2 = map.get((Object)EquipmentSlotType.OFFHAND);
        if (itemStack != null && itemStack2 != null && ItemStack.areItemStacksEqual(itemStack, this.getItemInHand(EquipmentSlotType.OFFHAND)) && ItemStack.areItemStacksEqual(itemStack2, this.getItemInHand(EquipmentSlotType.MAINHAND))) {
            ((ServerWorld)this.world).getChunkProvider().sendToAllTracking(this, new SEntityStatusPacket(this, 55));
            map.remove((Object)EquipmentSlotType.MAINHAND);
            map.remove((Object)EquipmentSlotType.OFFHAND);
            this.setItemInHand(EquipmentSlotType.MAINHAND, itemStack.copy());
            this.setItemInHand(EquipmentSlotType.OFFHAND, itemStack2.copy());
        }
    }

    private void func_241344_b_(Map<EquipmentSlotType, ItemStack> map) {
        ArrayList<Pair<EquipmentSlotType, ItemStack>> arrayList = Lists.newArrayListWithCapacity(map.size());
        map.forEach((arg_0, arg_1) -> this.lambda$func_241344_b_$6(arrayList, arg_0, arg_1));
        ((ServerWorld)this.world).getChunkProvider().sendToAllTracking(this, new SEntityEquipmentPacket(this.getEntityId(), arrayList));
    }

    private ItemStack getArmorInSlot(EquipmentSlotType equipmentSlotType) {
        return this.armorArray.get(equipmentSlotType.getIndex());
    }

    private void setArmorInSlot(EquipmentSlotType equipmentSlotType, ItemStack itemStack) {
        this.armorArray.set(equipmentSlotType.getIndex(), itemStack);
    }

    private ItemStack getItemInHand(EquipmentSlotType equipmentSlotType) {
        return this.handInventory.get(equipmentSlotType.getIndex());
    }

    private void setItemInHand(EquipmentSlotType equipmentSlotType, ItemStack itemStack) {
        this.handInventory.set(equipmentSlotType.getIndex(), itemStack);
    }

    protected float updateDistance(float f, float f2) {
        boolean bl;
        float f3 = MathHelper.wrapDegrees(f - this.renderYawOffset);
        this.renderYawOffset += f3 * 0.3f;
        float f4 = MathHelper.wrapDegrees(this.rotationYaw - this.renderYawOffset);
        boolean bl2 = bl = f4 < -90.0f || f4 >= 90.0f;
        if (f4 < -75.0f) {
            f4 = -75.0f;
        }
        if (f4 >= 75.0f) {
            f4 = 75.0f;
        }
        this.renderYawOffset = this.rotationYaw - f4;
        if (f4 * f4 > 2500.0f) {
            this.renderYawOffset += f4 * 0.2f;
        }
        if (bl) {
            f2 *= -1.0f;
        }
        return f2;
    }

    public void livingTick() {
        if (this.jumpTicks > 0) {
            --this.jumpTicks;
        }
        if (this.canPassengerSteer()) {
            this.newPosRotationIncrements = 0;
            this.setPacketCoordinates(this.getPosX(), this.getPosY(), this.getPosZ());
        }
        if (this.newPosRotationIncrements > 0) {
            double d = this.getPosX() + (this.interpTargetX - this.getPosX()) / (double)this.newPosRotationIncrements;
            double d2 = this.getPosY() + (this.interpTargetY - this.getPosY()) / (double)this.newPosRotationIncrements;
            double d3 = this.getPosZ() + (this.interpTargetZ - this.getPosZ()) / (double)this.newPosRotationIncrements;
            double d4 = MathHelper.wrapDegrees(this.interpTargetYaw - (double)this.rotationYaw);
            this.rotationYaw = (float)((double)this.rotationYaw + d4 / (double)this.newPosRotationIncrements);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.interpTargetPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
            --this.newPosRotationIncrements;
            this.setPosition(d, d2, d3);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        } else if (!this.isServerWorld()) {
            this.setMotion(this.getMotion().scale(0.98));
        }
        if (this.interpTicksHead > 0) {
            this.rotationYawHead = (float)((double)this.rotationYawHead + MathHelper.wrapDegrees(this.interpTargetHeadYaw - (double)this.rotationYawHead) / (double)this.interpTicksHead);
            --this.interpTicksHead;
        }
        Vector3d vector3d = this.getMotion();
        double d = vector3d.x;
        double d5 = vector3d.y;
        double d6 = vector3d.z;
        if (Math.abs(vector3d.x) < 0.003) {
            d = 0.0;
        }
        if (Math.abs(vector3d.y) < 0.003) {
            d5 = 0.0;
        }
        if (Math.abs(vector3d.z) < 0.003) {
            d6 = 0.0;
        }
        this.setMotion(d, d5, d6);
        this.world.getProfiler().startSection("ai");
        if (this.isMovementBlocked()) {
            this.isJumping = false;
            this.moveStrafing = 0.0f;
            this.moveForward = 0.0f;
        } else if (this.isServerWorld()) {
            this.world.getProfiler().startSection("newAi");
            this.updateEntityActionState();
            this.world.getProfiler().endSection();
        }
        this.world.getProfiler().endSection();
        this.world.getProfiler().startSection("jump");
        if (this.isJumping && this.func_241208_cS_()) {
            double d7 = this.isInLava() ? this.func_233571_b_(FluidTags.LAVA) : this.func_233571_b_(FluidTags.WATER);
            boolean bl = this.isInWater() && d7 > 0.0;
            double d8 = this.func_233579_cu_();
            if (!bl || this.onGround && !(d7 > d8)) {
                if (!this.isInLava() || this.onGround && !(d7 > d8)) {
                    if ((this.onGround || bl && d7 <= d8) && this.jumpTicks == 0) {
                        this.jump();
                        this.jumpTicks = 10;
                    }
                } else {
                    this.handleFluidJump(FluidTags.LAVA);
                }
            } else {
                this.handleFluidJump(FluidTags.WATER);
            }
        } else {
            this.jumpTicks = 0;
        }
        this.world.getProfiler().endSection();
        this.world.getProfiler().startSection("travel");
        this.moveStrafing *= 0.98f;
        this.moveForward *= 0.98f;
        this.updateElytra();
        AxisAlignedBB axisAlignedBB = this.getBoundingBox();
        this.travel(new Vector3d(this.moveStrafing, this.moveVertical, this.moveForward));
        this.world.getProfiler().endSection();
        this.world.getProfiler().startSection("push");
        if (this.spinAttackDuration > 0) {
            --this.spinAttackDuration;
            this.updateSpinAttack(axisAlignedBB, this.getBoundingBox());
        }
        this.collideWithNearbyEntities();
        this.world.getProfiler().endSection();
        if (!this.world.isRemote && this.isWaterSensitive() && this.isInWaterRainOrBubbleColumn()) {
            this.attackEntityFrom(DamageSource.DROWN, 1.0f);
        }
    }

    public boolean isWaterSensitive() {
        return true;
    }

    private void updateElytra() {
        boolean bl = this.getFlag(0);
        if (bl && !this.onGround && !this.isPassenger() && !this.isPotionActive(Effects.LEVITATION)) {
            ItemStack itemStack = this.getItemStackFromSlot(EquipmentSlotType.CHEST);
            if (itemStack.getItem() == Items.ELYTRA && ElytraItem.isUsable(itemStack)) {
                bl = true;
                if (!this.world.isRemote && (this.ticksElytraFlying + 1) % 20 == 0) {
                    itemStack.damageItem(1, this, LivingEntity::lambda$updateElytra$7);
                }
            } else {
                bl = false;
            }
        } else {
            bl = false;
        }
        if (!this.world.isRemote) {
            this.setFlag(7, bl);
        }
    }

    protected void updateEntityActionState() {
    }

    protected void collideWithNearbyEntities() {
        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox(), EntityPredicates.pushableBy(this));
        if (!list.isEmpty()) {
            int n;
            int n2 = this.world.getGameRules().getInt(GameRules.MAX_ENTITY_CRAMMING);
            if (n2 > 0 && list.size() > n2 - 1 && this.rand.nextInt(4) == 0) {
                n = 0;
                for (int i = 0; i < list.size(); ++i) {
                    if (list.get(i).isPassenger()) continue;
                    ++n;
                }
                if (n > n2 - 1) {
                    this.attackEntityFrom(DamageSource.CRAMMING, 6.0f);
                }
            }
            for (n = 0; n < list.size(); ++n) {
                Entity entity2 = list.get(n);
                this.collideWithEntity(entity2);
            }
        }
    }

    protected void updateSpinAttack(AxisAlignedBB axisAlignedBB, AxisAlignedBB axisAlignedBB2) {
        AxisAlignedBB axisAlignedBB3 = axisAlignedBB.union(axisAlignedBB2);
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisAlignedBB3);
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); ++i) {
                Entity entity2 = list.get(i);
                if (!(entity2 instanceof LivingEntity)) continue;
                this.spinAttack((LivingEntity)entity2);
                this.spinAttackDuration = 0;
                this.setMotion(this.getMotion().scale(-0.2));
                break;
            }
        } else if (this.collidedHorizontally) {
            this.spinAttackDuration = 0;
        }
        if (!this.world.isRemote && this.spinAttackDuration <= 0) {
            this.setLivingFlag(4, true);
        }
    }

    protected void collideWithEntity(Entity entity2) {
        entity2.applyEntityCollision(this);
    }

    protected void spinAttack(LivingEntity livingEntity) {
    }

    public void startSpinAttack(int n) {
        this.spinAttackDuration = n;
        if (!this.world.isRemote) {
            this.setLivingFlag(4, false);
        }
    }

    public boolean isSpinAttacking() {
        return (this.dataManager.get(LIVING_FLAGS) & 4) != 0;
    }

    @Override
    public void stopRiding() {
        Entity entity2 = this.getRidingEntity();
        super.stopRiding();
        if (entity2 != null && entity2 != this.getRidingEntity() && !this.world.isRemote) {
            this.func_233628_a_(entity2);
        }
    }

    @Override
    public void updateRidden() {
        super.updateRidden();
        this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
        this.onGroundSpeedFactor = 0.0f;
        this.fallDistance = 0.0f;
    }

    @Override
    public void setPositionAndRotationDirect(double d, double d2, double d3, float f, float f2, int n, boolean bl) {
        this.interpTargetX = d;
        this.interpTargetY = d2;
        this.interpTargetZ = d3;
        this.interpTargetYaw = f;
        this.interpTargetPitch = f2;
        this.newPosRotationIncrements = n;
    }

    @Override
    public void setHeadRotation(float f, int n) {
        this.interpTargetHeadYaw = f;
        this.interpTicksHead = n;
    }

    public void setJumping(boolean bl) {
        this.isJumping = bl;
    }

    public void triggerItemPickupTrigger(ItemEntity itemEntity) {
        PlayerEntity playerEntity;
        PlayerEntity playerEntity2 = playerEntity = itemEntity.getThrowerId() != null ? this.world.getPlayerByUuid(itemEntity.getThrowerId()) : null;
        if (playerEntity instanceof ServerPlayerEntity) {
            CriteriaTriggers.THROWN_ITEM_PICKED_UP_BY_ENTITY.test((ServerPlayerEntity)playerEntity, itemEntity.getItem(), this);
        }
    }

    public void onItemPickup(Entity entity2, int n) {
        if (!entity2.removed && !this.world.isRemote && (entity2 instanceof ItemEntity || entity2 instanceof AbstractArrowEntity || entity2 instanceof ExperienceOrbEntity)) {
            ((ServerWorld)this.world).getChunkProvider().sendToAllTracking(entity2, new SCollectItemPacket(entity2.getEntityId(), this.getEntityId(), n));
        }
    }

    public boolean canEntityBeSeen(Entity entity2) {
        Vector3d vector3d;
        Vector3d vector3d2 = new Vector3d(this.getPosX(), this.getPosYEye(), this.getPosZ());
        return this.world.rayTraceBlocks(new RayTraceContext(vector3d2, vector3d = new Vector3d(entity2.getPosX(), entity2.getPosYEye(), entity2.getPosZ()), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this)).getType() == RayTraceResult.Type.MISS;
    }

    public boolean canEntityBeSeen(Vector3d vector3d) {
        Vector3d vector3d2;
        Vector3d vector3d3 = new Vector3d(this.getPosX(), this.getPosYEye(), this.getPosZ());
        return this.world.rayTraceBlocks(new RayTraceContext(vector3d3, vector3d2 = new Vector3d(vector3d.x, vector3d.y, vector3d.z), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this)).getType() == RayTraceResult.Type.MISS;
    }

    public Vector3d getPositon(float f) {
        return MathUtil.interpolate(this.getPositionVec(), new Vector3d(this.lastTickPosX, this.lastTickPosY, this.lastTickPosZ), f);
    }

    public boolean canVectorBeSeenFixed(Vector3d vector3d) {
        Vector3d vector3d2;
        Vector3d vector3d3 = Minecraft.getInstance().getRenderManager().info.getProjectedView();
        BlockRayTraceResult blockRayTraceResult = this.world.rayTraceBlocks(new RayTraceContext(vector3d3, vector3d2 = new Vector3d(vector3d.getX(), vector3d.getY(), vector3d.getZ()), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
        if (this.world.getBlockState(blockRayTraceResult.getPos()).getBlock() instanceof GlassBlock) {
            return false;
        }
        return blockRayTraceResult.getType() == RayTraceResult.Type.MISS;
    }

    @Override
    public float getYaw(float f) {
        return f == 1.0f ? this.rotationYawHead : MathHelper.lerp(f, this.prevRotationYawHead, this.rotationYawHead);
    }

    public float getSwingProgress(float f) {
        float f2 = this.swingProgress - this.prevSwingProgress;
        if (f2 < 0.0f) {
            f2 += 1.0f;
        }
        return this.prevSwingProgress + f2 * f;
    }

    public boolean isServerWorld() {
        return !this.world.isRemote;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    @Override
    public boolean canBePushed() {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        AntiPush antiPush = functionRegistry.getAntiPush();
        if (antiPush.isState() && ((Boolean)antiPush.getModes().getValueByName("\u0418\u0433\u0440\u043e\u043a\u0438").get()).booleanValue() && this instanceof ClientPlayerEntity) {
            return true;
        }
        return this.isAlive() && !this.isSpectator() && !this.isOnLadder();
    }

    @Override
    protected void markVelocityChanged() {
        this.velocityChanged = this.rand.nextDouble() >= this.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
    }

    @Override
    public float getRotationYawHead() {
        return this.rotationYawHead;
    }

    @Override
    public void setRotationYawHead(float f) {
        this.rotationYawHead = f;
    }

    @Override
    public void setRenderYawOffset(float f) {
        this.renderYawOffset = f;
    }

    @Override
    protected Vector3d func_241839_a(Direction.Axis axis, TeleportationRepositioner.Result result) {
        return LivingEntity.func_242288_h(super.func_241839_a(axis, result));
    }

    public static Vector3d func_242288_h(Vector3d vector3d) {
        return new Vector3d(vector3d.x, vector3d.y, 0.0);
    }

    public float getAbsorptionAmount() {
        return this.absorptionAmount;
    }

    public void setAbsorptionAmount(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        this.absorptionAmount = f;
    }

    public void sendEnterCombat() {
    }

    public void sendEndCombat() {
    }

    protected void markPotionsDirty() {
        this.potionsNeedUpdate = true;
    }

    public abstract HandSide getPrimaryHand();

    public boolean isHandActive() {
        return (this.dataManager.get(LIVING_FLAGS) & 1) > 0;
    }

    public Hand getActiveHand() {
        return (this.dataManager.get(LIVING_FLAGS) & 2) > 0 ? Hand.OFF_HAND : Hand.MAIN_HAND;
    }

    private void updateActiveHand() {
        if (this.isHandActive()) {
            if (ItemStack.areItemsEqualIgnoreDurability(this.getHeldItem(this.getActiveHand()), this.activeItemStack)) {
                this.activeItemStack = this.getHeldItem(this.getActiveHand());
                this.activeItemStack.onItemUsed(this.world, this, this.getItemInUseCount());
                if (this.shouldTriggerItemUseEffects()) {
                    this.triggerItemUseEffects(this.activeItemStack, 5);
                }
                if (--this.activeItemStackUseCount == 0 && !this.world.isRemote && !this.activeItemStack.isCrossbowStack()) {
                    this.onItemUseFinish();
                }
            } else {
                this.resetActiveHand();
            }
        }
    }

    private boolean shouldTriggerItemUseEffects() {
        int n = this.getItemInUseCount();
        Food food = this.activeItemStack.getItem().getFood();
        boolean bl = food != null && food.isFastEating();
        return (bl |= n <= this.activeItemStack.getUseDuration() - 7) && n % 4 == 0;
    }

    private void updateSwimAnimation() {
        this.lastSwimAnimation = this.swimAnimation;
        this.swimAnimation = this.isActualySwimming() ? Math.min(1.0f, this.swimAnimation + 0.09f) : Math.max(0.0f, this.swimAnimation - 0.09f);
    }

    protected void setLivingFlag(int n, boolean bl) {
        int n2 = this.dataManager.get(LIVING_FLAGS).byteValue();
        n2 = bl ? (n2 |= n) : (n2 &= ~n);
        this.dataManager.set(LIVING_FLAGS, (byte)n2);
    }

    public void setActiveHand(Hand hand) {
        ItemStack itemStack = this.getHeldItem(hand);
        if (!itemStack.isEmpty() && !this.isHandActive()) {
            this.activeItemStack = itemStack;
            this.activeItemStackUseCount = itemStack.getUseDuration();
            if (!this.world.isRemote) {
                this.setLivingFlag(1, false);
                this.setLivingFlag(2, hand == Hand.OFF_HAND);
            }
        }
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        super.notifyDataManagerChange(dataParameter);
        if (BED_POSITION.equals(dataParameter)) {
            if (this.world.isRemote) {
                this.getBedPosition().ifPresent(this::setSleepingPosition);
            }
        } else if (LIVING_FLAGS.equals(dataParameter) && this.world.isRemote) {
            if (this.isHandActive() && this.activeItemStack.isEmpty()) {
                this.activeItemStack = this.getHeldItem(this.getActiveHand());
                if (!this.activeItemStack.isEmpty()) {
                    this.activeItemStackUseCount = this.activeItemStack.getUseDuration();
                }
            } else if (!this.isHandActive() && !this.activeItemStack.isEmpty()) {
                this.activeItemStack = ItemStack.EMPTY;
                this.activeItemStackUseCount = 0;
            }
        }
    }

    @Override
    public void lookAt(EntityAnchorArgument.Type type, Vector3d vector3d) {
        super.lookAt(type, vector3d);
        this.prevRotationYawHead = this.rotationYawHead;
        this.prevRenderYawOffset = this.renderYawOffset = this.rotationYawHead;
    }

    protected void triggerItemUseEffects(ItemStack itemStack, int n) {
        if (!itemStack.isEmpty() && this.isHandActive()) {
            if (itemStack.getUseAction() == UseAction.DRINK) {
                this.playSound(this.getDrinkSound(itemStack), 0.5f, this.world.rand.nextFloat() * 0.1f + 0.9f);
            }
            if (itemStack.getUseAction() == UseAction.EAT) {
                this.addItemParticles(itemStack, n);
                this.playSound(this.getEatSound(itemStack), 0.5f + 0.5f * (float)this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
        }
    }

    private void addItemParticles(ItemStack itemStack, int n) {
        for (int i = 0; i < n; ++i) {
            Vector3d vector3d = new Vector3d(((double)this.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
            vector3d = vector3d.rotatePitch(-this.rotationPitch * ((float)Math.PI / 180));
            vector3d = vector3d.rotateYaw(-this.rotationYaw * ((float)Math.PI / 180));
            double d = (double)(-this.rand.nextFloat()) * 0.6 - 0.3;
            Vector3d vector3d2 = new Vector3d(((double)this.rand.nextFloat() - 0.5) * 0.3, d, 0.6);
            vector3d2 = vector3d2.rotatePitch(-this.rotationPitch * ((float)Math.PI / 180));
            vector3d2 = vector3d2.rotateYaw(-this.rotationYaw * ((float)Math.PI / 180));
            vector3d2 = vector3d2.add(this.getPosX(), this.getPosYEye(), this.getPosZ());
            this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, itemStack), vector3d2.x, vector3d2.y, vector3d2.z, vector3d.x, vector3d.y + 0.05, vector3d.z);
        }
    }

    protected void onItemUseFinish() {
        Hand hand = this.getActiveHand();
        if (!this.activeItemStack.equals(this.getHeldItem(hand))) {
            this.stopActiveHand();
        } else if (!this.activeItemStack.isEmpty() && this.isHandActive()) {
            this.triggerItemUseEffects(this.activeItemStack, 16);
            ItemStack itemStack = this.activeItemStack.onItemUseFinish(this.world, this);
            if (itemStack != this.activeItemStack) {
                this.setHeldItem(hand, itemStack);
            }
            this.resetActiveHand();
        }
    }

    public ItemStack getActiveItemStack() {
        return this.activeItemStack;
    }

    public int getItemInUseCount() {
        return this.activeItemStackUseCount;
    }

    public int getItemInUseMaxCount() {
        return this.isHandActive() ? this.activeItemStack.getUseDuration() - this.getItemInUseCount() : 0;
    }

    public void stopActiveHand() {
        if (!this.activeItemStack.isEmpty()) {
            this.activeItemStack.onPlayerStoppedUsing(this.world, this, this.getItemInUseCount());
            if (this.activeItemStack.isCrossbowStack()) {
                this.updateActiveHand();
            }
        }
        this.resetActiveHand();
    }

    public void resetActiveHand() {
        if (!this.world.isRemote) {
            this.setLivingFlag(1, true);
        }
        this.activeItemStack = ItemStack.EMPTY;
        this.activeItemStackUseCount = 0;
    }

    public boolean isActiveItemStackBlocking() {
        if (this.isHandActive() && !this.activeItemStack.isEmpty()) {
            Item item = this.activeItemStack.getItem();
            if (item.getUseAction(this.activeItemStack) != UseAction.BLOCK) {
                return true;
            }
            return item.getUseDuration(this.activeItemStack) - this.activeItemStackUseCount >= 5;
        }
        return true;
    }

    public boolean hasStoppedClimbing() {
        return this.isSneaking();
    }

    public boolean isElytraFlying() {
        return this.getFlag(0);
    }

    @Override
    public boolean isActualySwimming() {
        return super.isActualySwimming() || !this.isElytraFlying() && this.getPose() == Pose.FALL_FLYING;
    }

    public int getTicksElytraFlying() {
        return this.ticksElytraFlying;
    }

    public boolean attemptTeleport(double d, double d2, double d3, boolean bl) {
        double d4 = this.getPosX();
        double d5 = this.getPosY();
        double d6 = this.getPosZ();
        double d7 = d2;
        boolean bl2 = false;
        World world = this.world;
        BlockPos blockPos = new BlockPos(d, d2, d3);
        if (world.isBlockLoaded(blockPos)) {
            boolean bl3 = false;
            while (!bl3 && blockPos.getY() > 0) {
                BlockPos blockPos2 = blockPos.down();
                BlockState blockState = world.getBlockState(blockPos2);
                if (blockState.getMaterial().blocksMovement()) {
                    bl3 = true;
                    continue;
                }
                d7 -= 1.0;
                blockPos = blockPos2;
            }
            if (bl3) {
                this.setPositionAndUpdate(d, d7, d3);
                if (world.hasNoCollisions(this) && !world.containsAnyLiquid(this.getBoundingBox())) {
                    bl2 = true;
                }
            }
        }
        if (!bl2) {
            this.setPositionAndUpdate(d4, d5, d6);
            return true;
        }
        if (bl) {
            world.setEntityState(this, (byte)46);
        }
        if (this instanceof CreatureEntity) {
            ((CreatureEntity)this).getNavigator().clearPath();
        }
        return false;
    }

    public boolean canBeHitWithPotion() {
        return false;
    }

    public boolean attackable() {
        return false;
    }

    public void setPartying(BlockPos blockPos, boolean bl) {
    }

    public boolean canPickUpItem(ItemStack itemStack) {
        return true;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnMobPacket(this);
    }

    @Override
    public EntitySize getSize(Pose pose) {
        return pose == Pose.SLEEPING ? SLEEPING_SIZE : super.getSize(pose).scale(this.getRenderScale());
    }

    public ImmutableList<Pose> getAvailablePoses() {
        return ImmutableList.of(Pose.STANDING);
    }

    public AxisAlignedBB getPoseAABB(Pose pose) {
        EntitySize entitySize = this.getSize(pose);
        return new AxisAlignedBB(-entitySize.width / 2.0f, 0.0, -entitySize.width / 2.0f, entitySize.width / 2.0f, entitySize.height, entitySize.width / 2.0f);
    }

    public Optional<BlockPos> getBedPosition() {
        return this.dataManager.get(BED_POSITION);
    }

    public void setBedPosition(BlockPos blockPos) {
        this.dataManager.set(BED_POSITION, Optional.of(blockPos));
    }

    public void clearBedPosition() {
        this.dataManager.set(BED_POSITION, Optional.empty());
    }

    public boolean isSleeping() {
        return this.getBedPosition().isPresent();
    }

    public void startSleeping(BlockPos blockPos) {
        BlockState blockState;
        if (this.isPassenger()) {
            this.stopRiding();
        }
        if ((blockState = this.world.getBlockState(blockPos)).getBlock() instanceof BedBlock) {
            this.world.setBlockState(blockPos, (BlockState)blockState.with(BedBlock.OCCUPIED, true), 0);
        }
        this.setPose(Pose.SLEEPING);
        this.setSleepingPosition(blockPos);
        this.setBedPosition(blockPos);
        this.setMotion(Vector3d.ZERO);
        this.isAirBorne = true;
    }

    private void setSleepingPosition(BlockPos blockPos) {
        this.setPosition((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.6875, (double)blockPos.getZ() + 0.5);
    }

    private boolean isInValidBed() {
        return this.getBedPosition().map(this::lambda$isInValidBed$8).orElse(false);
    }

    public void wakeUp() {
        this.getBedPosition().filter(this.world::isBlockLoaded).ifPresent(this::lambda$wakeUp$10);
        Vector3d vector3d = this.getPositionVec();
        this.setPose(Pose.STANDING);
        this.setPosition(vector3d.x, vector3d.y, vector3d.z);
        this.clearBedPosition();
    }

    @Nullable
    public Direction getBedDirection() {
        BlockPos blockPos = this.getBedPosition().orElse(null);
        return blockPos != null ? BedBlock.getBedDirection(this.world, blockPos) : null;
    }

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        return !this.isSleeping() && super.isEntityInsideOpaqueBlock();
    }

    @Override
    protected final float getEyeHeight(Pose pose, EntitySize entitySize) {
        return pose == Pose.SLEEPING ? 0.2f : this.getStandingEyeHeight(pose, entitySize);
    }

    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return super.getEyeHeight(pose, entitySize);
    }

    public ItemStack findAmmo(ItemStack itemStack) {
        return ItemStack.EMPTY;
    }

    public ItemStack onFoodEaten(World world, ItemStack itemStack) {
        if (itemStack.isFood()) {
            world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), this.getEatSound(itemStack), SoundCategory.NEUTRAL, 1.0f, 1.0f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4f);
            this.applyFoodEffects(itemStack, world, this);
            if (!(this instanceof PlayerEntity) || !((PlayerEntity)this).abilities.isCreativeMode) {
                itemStack.shrink(1);
            }
        }
        return itemStack;
    }

    private void applyFoodEffects(ItemStack itemStack, World world, LivingEntity livingEntity) {
        Item item = itemStack.getItem();
        if (item.isFood()) {
            for (Pair<EffectInstance, Float> pair : item.getFood().getEffects()) {
                if (world.isRemote || pair.getFirst() == null || !(world.rand.nextFloat() < pair.getSecond().floatValue())) continue;
                livingEntity.addPotionEffect(new EffectInstance(pair.getFirst()));
            }
        }
    }

    private static byte equipmentSlotToEntityState(EquipmentSlotType equipmentSlotType) {
        switch (equipmentSlotType) {
            case MAINHAND: {
                return 0;
            }
            case OFFHAND: {
                return 1;
            }
            case HEAD: {
                return 0;
            }
            case CHEST: {
                return 1;
            }
            case FEET: {
                return 1;
            }
            case LEGS: {
                return 0;
            }
        }
        return 0;
    }

    public void sendBreakAnimation(EquipmentSlotType equipmentSlotType) {
        this.world.setEntityState(this, LivingEntity.equipmentSlotToEntityState(equipmentSlotType));
    }

    public void sendBreakAnimation(Hand hand) {
        this.sendBreakAnimation(hand == Hand.MAIN_HAND ? EquipmentSlotType.MAINHAND : EquipmentSlotType.OFFHAND);
    }

    public boolean isBlocking() {
        return this.isHandActive() && this.activeItemStack.getItem().getUseAction(this.activeItemStack) == UseAction.BLOCK;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if (this.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == Items.DRAGON_HEAD) {
            float f = 0.5f;
            return this.getBoundingBox().grow(0.5, 0.5, 0.5);
        }
        return super.getRenderBoundingBox();
    }

    private void lambda$wakeUp$10(BlockPos blockPos) {
        BlockState blockState = this.world.getBlockState(blockPos);
        if (blockState.getBlock() instanceof BedBlock) {
            this.world.setBlockState(blockPos, (BlockState)blockState.with(BedBlock.OCCUPIED, false), 0);
            Vector3d vector3d = BedBlock.func_242652_a(this.getType(), this.world, blockPos, this.rotationYaw).orElseGet(() -> LivingEntity.lambda$wakeUp$9(blockPos));
            Vector3d vector3d2 = Vector3d.copyCenteredHorizontally(blockPos).subtract(vector3d).normalize();
            float f = (float)MathHelper.wrapDegrees(MathHelper.atan2(vector3d2.z, vector3d2.x) * 57.2957763671875 - 90.0);
            this.setPosition(vector3d.x, vector3d.y, vector3d.z);
            this.rotationYaw = f;
            this.rotationPitch = 0.0f;
        }
    }

    private static Vector3d lambda$wakeUp$9(BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.up();
        return new Vector3d((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.1, (double)blockPos2.getZ() + 0.5);
    }

    private Boolean lambda$isInValidBed$8(BlockPos blockPos) {
        return this.world.getBlockState(blockPos).getBlock() instanceof BedBlock;
    }

    private static void lambda$updateElytra$7(LivingEntity livingEntity) {
        livingEntity.sendBreakAnimation(EquipmentSlotType.CHEST);
    }

    private void lambda$func_241344_b_$6(List list, EquipmentSlotType equipmentSlotType, ItemStack itemStack) {
        ItemStack itemStack2 = itemStack.copy();
        list.add(Pair.of(equipmentSlotType, itemStack2));
        switch (equipmentSlotType.getSlotType()) {
            case HAND: {
                this.setItemInHand(equipmentSlotType, itemStack2);
                break;
            }
            case ARMOR: {
                this.setArmorInSlot(equipmentSlotType, itemStack2);
            }
        }
    }

    private static boolean lambda$canEquip$5(Item item, Item item2) {
        return item2 == item;
    }

    private static void lambda$attackEntityFrom$4(LivingEntity livingEntity) {
        livingEntity.sendBreakAnimation(EquipmentSlotType.HEAD);
    }

    private void lambda$updatePotionEffects$3(EffectInstance effectInstance) {
        this.onChangedPotionEffect(effectInstance, false);
    }

    private static void lambda$writeAdditional$2(CompoundNBT compoundNBT, INBT iNBT) {
        compoundNBT.put("Brain", iNBT);
    }

    private static void lambda$writeAdditional$1(CompoundNBT compoundNBT, BlockPos blockPos) {
        compoundNBT.putInt("SleepingX", blockPos.getX());
        compoundNBT.putInt("SleepingY", blockPos.getY());
        compoundNBT.putInt("SleepingZ", blockPos.getZ());
    }

    private static void lambda$func_233642_cO_$0(LivingEntity livingEntity) {
        livingEntity.sendBreakAnimation(EquipmentSlotType.FEET);
    }
}

