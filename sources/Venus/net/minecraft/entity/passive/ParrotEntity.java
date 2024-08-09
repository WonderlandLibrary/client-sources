/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LandOnOwnersShoulderGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.ShoulderRidingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ParrotEntity
extends ShoulderRidingEntity
implements IFlyingAnimal {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(ParrotEntity.class, DataSerializers.VARINT);
    private static final Predicate<MobEntity> CAN_MIMIC = new Predicate<MobEntity>(){

        @Override
        public boolean test(@Nullable MobEntity mobEntity) {
            return mobEntity != null && IMITATION_SOUND_EVENTS.containsKey(mobEntity.getType());
        }

        @Override
        public boolean test(@Nullable Object object) {
            return this.test((MobEntity)object);
        }
    };
    private static final Item DEADLY_ITEM = Items.COOKIE;
    private static final Set<Item> TAME_ITEMS = Sets.newHashSet(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);
    private static final Map<EntityType<?>, SoundEvent> IMITATION_SOUND_EVENTS = Util.make(Maps.newHashMap(), ParrotEntity::lambda$static$0);
    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    private float flapping = 1.0f;
    private boolean partyParrot;
    private BlockPos jukeboxPosition;

    public ParrotEntity(EntityType<? extends ParrotEntity> entityType, World world) {
        super((EntityType<? extends ShoulderRidingEntity>)entityType, world);
        this.moveController = new FlyingMovementController(this, 10, false);
        this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, -1.0f);
        this.setPathPriority(PathNodeType.COCOA, -1.0f);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        this.setVariant(this.rand.nextInt(5));
        if (iLivingEntityData == null) {
            iLivingEntityData = new AgeableEntity.AgeableData(false);
        }
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    @Override
    public boolean isChild() {
        return true;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0, 5.0f, 1.0f, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LandOnOwnersShoulderGoal(this));
        this.goalSelector.addGoal(3, new FollowMobGoal(this, 1.0, 3.0f, 7.0f));
    }

    public static AttributeModifierMap.MutableAttribute func_234213_eS_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 6.0).createMutableAttribute(Attributes.FLYING_SPEED, 0.4f).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2f);
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        FlyingPathNavigator flyingPathNavigator = new FlyingPathNavigator(this, world);
        flyingPathNavigator.setCanOpenDoors(true);
        flyingPathNavigator.setCanSwim(false);
        flyingPathNavigator.setCanEnterDoors(false);
        return flyingPathNavigator;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return entitySize.height * 0.6f;
    }

    @Override
    public void livingTick() {
        if (this.jukeboxPosition == null || !this.jukeboxPosition.withinDistance(this.getPositionVec(), 3.46) || !this.world.getBlockState(this.jukeboxPosition).isIn(Blocks.JUKEBOX)) {
            this.partyParrot = false;
            this.jukeboxPosition = null;
        }
        if (this.world.rand.nextInt(400) == 0) {
            ParrotEntity.playMimicSound(this.world, this);
        }
        super.livingTick();
        this.calculateFlapping();
    }

    @Override
    public void setPartying(BlockPos blockPos, boolean bl) {
        this.jukeboxPosition = blockPos;
        this.partyParrot = bl;
    }

    public boolean isPartying() {
        return this.partyParrot;
    }

    private void calculateFlapping() {
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed = (float)((double)this.flapSpeed + (double)(!this.onGround && !this.isPassenger() ? 4 : -1) * 0.3);
        this.flapSpeed = MathHelper.clamp(this.flapSpeed, 0.0f, 1.0f);
        if (!this.onGround && this.flapping < 1.0f) {
            this.flapping = 1.0f;
        }
        this.flapping = (float)((double)this.flapping * 0.9);
        Vector3d vector3d = this.getMotion();
        if (!this.onGround && vector3d.y < 0.0) {
            this.setMotion(vector3d.mul(1.0, 0.6, 1.0));
        }
        this.flap += this.flapping * 2.0f;
    }

    public static boolean playMimicSound(World world, Entity entity2) {
        if (entity2.isAlive() && !entity2.isSilent() && world.rand.nextInt(2) == 0) {
            MobEntity mobEntity;
            List<MobEntity> list = world.getEntitiesWithinAABB(MobEntity.class, entity2.getBoundingBox().grow(20.0), CAN_MIMIC);
            if (!list.isEmpty() && !(mobEntity = list.get(world.rand.nextInt(list.size()))).isSilent()) {
                SoundEvent soundEvent = ParrotEntity.getMimicSound(mobEntity.getType());
                world.playSound(null, entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), soundEvent, entity2.getSoundCategory(), 0.7f, ParrotEntity.getPitch(world.rand));
                return false;
            }
            return true;
        }
        return true;
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (!this.isTamed() && TAME_ITEMS.contains(itemStack.getItem())) {
            if (!playerEntity.abilities.isCreativeMode) {
                itemStack.shrink(1);
            }
            if (!this.isSilent()) {
                this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_PARROT_EAT, this.getSoundCategory(), 1.0f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f);
            }
            if (!this.world.isRemote) {
                if (this.rand.nextInt(10) == 0) {
                    this.setTamedBy(playerEntity);
                    this.world.setEntityState(this, (byte)7);
                } else {
                    this.world.setEntityState(this, (byte)6);
                }
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        if (itemStack.getItem() == DEADLY_ITEM) {
            if (!playerEntity.abilities.isCreativeMode) {
                itemStack.shrink(1);
            }
            this.addPotionEffect(new EffectInstance(Effects.POISON, 900));
            if (playerEntity.isCreative() || !this.isInvulnerable()) {
                this.attackEntityFrom(DamageSource.causePlayerDamage(playerEntity), Float.MAX_VALUE);
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        if (!this.isFlying() && this.isTamed() && this.isOwner(playerEntity)) {
            if (!this.world.isRemote) {
                this.func_233687_w_(!this.isSitting());
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        return super.func_230254_b_(playerEntity, hand);
    }

    @Override
    public boolean isBreedingItem(ItemStack itemStack) {
        return true;
    }

    public static boolean func_223317_c(EntityType<ParrotEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        BlockState blockState = iWorld.getBlockState(blockPos.down());
        return (blockState.isIn(BlockTags.LEAVES) || blockState.isIn(Blocks.GRASS_BLOCK) || blockState.isIn(BlockTags.LOGS) || blockState.isIn(Blocks.AIR)) && iWorld.getLightSubtracted(blockPos, 0) > 8;
    }

    @Override
    public boolean onLivingFall(float f, float f2) {
        return true;
    }

    @Override
    protected void updateFallState(double d, boolean bl, BlockState blockState, BlockPos blockPos) {
    }

    @Override
    public boolean canMateWith(AnimalEntity animalEntity) {
        return true;
    }

    @Override
    @Nullable
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return null;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity2) {
        return entity2.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0f);
    }

    @Override
    @Nullable
    public SoundEvent getAmbientSound() {
        return ParrotEntity.func_234212_a_(this.world, this.world.rand);
    }

    public static SoundEvent func_234212_a_(World world, Random random2) {
        if (world.getDifficulty() != Difficulty.PEACEFUL && random2.nextInt(1000) == 0) {
            ArrayList<EntityType<?>> arrayList = Lists.newArrayList(IMITATION_SOUND_EVENTS.keySet());
            return ParrotEntity.getMimicSound((EntityType)arrayList.get(random2.nextInt(arrayList.size())));
        }
        return SoundEvents.ENTITY_PARROT_AMBIENT;
    }

    private static SoundEvent getMimicSound(EntityType<?> entityType) {
        return IMITATION_SOUND_EVENTS.getOrDefault(entityType, SoundEvents.ENTITY_PARROT_AMBIENT);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_PARROT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PARROT_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_PARROT_STEP, 0.15f, 1.0f);
    }

    @Override
    protected float playFlySound(float f) {
        this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15f, 1.0f);
        return f + this.flapSpeed / 2.0f;
    }

    @Override
    protected boolean makeFlySound() {
        return false;
    }

    @Override
    protected float getSoundPitch() {
        return ParrotEntity.getPitch(this.rand);
    }

    public static float getPitch(Random random2) {
        return (random2.nextFloat() - random2.nextFloat()) * 0.2f + 1.0f;
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.NEUTRAL;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity entity2) {
        if (!(entity2 instanceof PlayerEntity)) {
            super.collideWithEntity(entity2);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        this.func_233687_w_(true);
        return super.attackEntityFrom(damageSource, f);
    }

    public int getVariant() {
        return MathHelper.clamp(this.dataManager.get(VARIANT), 0, 4);
    }

    public void setVariant(int n) {
        this.dataManager.set(VARIANT, n);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(VARIANT, 0);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("Variant", this.getVariant());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setVariant(compoundNBT.getInt("Variant"));
    }

    public boolean isFlying() {
        return !this.onGround;
    }

    @Override
    public Vector3d func_241205_ce_() {
        return new Vector3d(0.0, 0.5f * this.getEyeHeight(), this.getWidth() * 0.4f);
    }

    private static void lambda$static$0(HashMap hashMap) {
        hashMap.put(EntityType.BLAZE, SoundEvents.ENTITY_PARROT_IMITATE_BLAZE);
        hashMap.put(EntityType.CAVE_SPIDER, SoundEvents.ENTITY_PARROT_IMITATE_SPIDER);
        hashMap.put(EntityType.CREEPER, SoundEvents.ENTITY_PARROT_IMITATE_CREEPER);
        hashMap.put(EntityType.DROWNED, SoundEvents.ENTITY_PARROT_IMITATE_DROWNED);
        hashMap.put(EntityType.ELDER_GUARDIAN, SoundEvents.ENTITY_PARROT_IMITATE_ELDER_GUARDIAN);
        hashMap.put(EntityType.ENDER_DRAGON, SoundEvents.ENTITY_PARROT_IMITATE_ENDER_DRAGON);
        hashMap.put(EntityType.ENDERMITE, SoundEvents.ENTITY_PARROT_IMITATE_ENDERMITE);
        hashMap.put(EntityType.EVOKER, SoundEvents.ENTITY_PARROT_IMITATE_EVOKER);
        hashMap.put(EntityType.GHAST, SoundEvents.ENTITY_PARROT_IMITATE_GHAST);
        hashMap.put(EntityType.GUARDIAN, SoundEvents.ENTITY_PARROT_IMITATE_GUARDIAN);
        hashMap.put(EntityType.HOGLIN, SoundEvents.ENTITY_PARROT_IMITATE_HOGLIN);
        hashMap.put(EntityType.HUSK, SoundEvents.ENTITY_PARROT_IMITATE_HUSK);
        hashMap.put(EntityType.ILLUSIONER, SoundEvents.ENTITY_PARROT_IMITATE_ILLUSIONER);
        hashMap.put(EntityType.MAGMA_CUBE, SoundEvents.ENTITY_PARROT_IMITATE_MAGMA_CUBE);
        hashMap.put(EntityType.PHANTOM, SoundEvents.ENTITY_PARROT_IMITATE_PHANTOM);
        hashMap.put(EntityType.PIGLIN, SoundEvents.ENTITY_PARROT_IMITATE_PIGLIN);
        hashMap.put(EntityType.field_242287_aj, SoundEvents.ENTITY_PARROT_IMITATE_PIGLIN_BRUTE);
        hashMap.put(EntityType.PILLAGER, SoundEvents.ENTITY_PARROT_IMITATE_PILLAGER);
        hashMap.put(EntityType.RAVAGER, SoundEvents.ENTITY_PARROT_IMITATE_RAVAGER);
        hashMap.put(EntityType.SHULKER, SoundEvents.ENTITY_PARROT_IMITATE_SHULKER);
        hashMap.put(EntityType.SILVERFISH, SoundEvents.ENTITY_PARROT_IMITATE_SILVERFISH);
        hashMap.put(EntityType.SKELETON, SoundEvents.ENTITY_PARROT_IMITATE_SKELETON);
        hashMap.put(EntityType.SLIME, SoundEvents.ENTITY_PARROT_IMITATE_SLIME);
        hashMap.put(EntityType.SPIDER, SoundEvents.ENTITY_PARROT_IMITATE_SPIDER);
        hashMap.put(EntityType.STRAY, SoundEvents.ENTITY_PARROT_IMITATE_STRAY);
        hashMap.put(EntityType.VEX, SoundEvents.ENTITY_PARROT_IMITATE_VEX);
        hashMap.put(EntityType.VINDICATOR, SoundEvents.ENTITY_PARROT_IMITATE_VINDICATOR);
        hashMap.put(EntityType.WITCH, SoundEvents.ENTITY_PARROT_IMITATE_WITCH);
        hashMap.put(EntityType.WITHER, SoundEvents.ENTITY_PARROT_IMITATE_WITHER);
        hashMap.put(EntityType.WITHER_SKELETON, SoundEvents.ENTITY_PARROT_IMITATE_WITHER_SKELETON);
        hashMap.put(EntityType.ZOGLIN, SoundEvents.ENTITY_PARROT_IMITATE_ZOGLIN);
        hashMap.put(EntityType.ZOMBIE, SoundEvents.ENTITY_PARROT_IMITATE_ZOMBIE);
        hashMap.put(EntityType.ZOMBIE_VILLAGER, SoundEvents.ENTITY_PARROT_IMITATE_ZOMBIE_VILLAGER);
    }
}

