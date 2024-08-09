/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinAction;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PiglinEntity
extends AbstractPiglinEntity
implements ICrossbowUser {
    private static final DataParameter<Boolean> field_234415_d_ = EntityDataManager.createKey(PiglinEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> field_234409_bv_ = EntityDataManager.createKey(PiglinEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DANCING = EntityDataManager.createKey(PiglinEntity.class, DataSerializers.BOOLEAN);
    private static final UUID BABY_SPEED_MODIFIER_IDENTIFIER = UUID.fromString("766bfa64-11f3-11ea-8d71-362b9e155667");
    private static final AttributeModifier BABY_SPEED_MODIFIER = new AttributeModifier(BABY_SPEED_MODIFIER_IDENTIFIER, "Baby speed boost", (double)0.2f, AttributeModifier.Operation.MULTIPLY_BASE);
    private final Inventory inventory = new Inventory(8);
    private boolean field_234407_bB_ = false;
    protected static final ImmutableList<SensorType<? extends Sensor<? super PiglinEntity>>> field_234405_b_ = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS, SensorType.HURT_BY, SensorType.PIGLIN_SPECIFIC_SENSOR);
    protected static final ImmutableList<MemoryModuleType<?>> field_234414_c_ = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.OPENED_DOORS, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, MemoryModuleType.NEAREST_ADULT_PIGLINS, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.PATH, MemoryModuleType.ANGRY_AT, MemoryModuleType.UNIVERSAL_ANGER, MemoryModuleType.AVOID_TARGET, MemoryModuleType.ADMIRING_ITEM, MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, MemoryModuleType.ADMIRING_DISABLED, MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM, MemoryModuleType.CELEBRATE_LOCATION, MemoryModuleType.DANCING, MemoryModuleType.HUNTED_RECENTLY, MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, MemoryModuleType.RIDE_TARGET, MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, MemoryModuleType.ATE_RECENTLY, MemoryModuleType.NEAREST_REPELLENT);

    public PiglinEntity(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super(entityType, world);
        this.experienceValue = 5;
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        if (this.isChild()) {
            compoundNBT.putBoolean("IsBaby", false);
        }
        if (this.field_234407_bB_) {
            compoundNBT.putBoolean("CannotHunt", false);
        }
        compoundNBT.put("Inventory", this.inventory.write());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setChild(compoundNBT.getBoolean("IsBaby"));
        this.setCannotHunt(compoundNBT.getBoolean("CannotHunt"));
        this.inventory.read(compoundNBT.getList("Inventory", 10));
    }

    @Override
    protected void dropSpecialItems(DamageSource damageSource, int n, boolean bl) {
        super.dropSpecialItems(damageSource, n, bl);
        this.inventory.func_233543_f_().forEach(this::entityDropItem);
    }

    protected ItemStack func_234436_k_(ItemStack itemStack) {
        return this.inventory.addItem(itemStack);
    }

    protected boolean func_234437_l_(ItemStack itemStack) {
        return this.inventory.func_233541_b_(itemStack);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(field_234415_d_, false);
        this.dataManager.register(field_234409_bv_, false);
        this.dataManager.register(DANCING, false);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        super.notifyDataManagerChange(dataParameter);
        if (field_234415_d_.equals(dataParameter)) {
            this.recalculateSize();
        }
    }

    public static AttributeModifierMap.MutableAttribute func_234420_eI_() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 16.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35f).createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0);
    }

    public static boolean func_234418_b_(EntityType<PiglinEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        return !iWorld.getBlockState(blockPos.down()).isIn(Blocks.NETHER_WART_BLOCK);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        if (spawnReason != SpawnReason.STRUCTURE) {
            if (iServerWorld.getRandom().nextFloat() < 0.2f) {
                this.setChild(false);
            } else if (this.func_242337_eM()) {
                this.setItemStackToSlot(EquipmentSlotType.MAINHAND, this.func_234432_eW_());
            }
        }
        PiglinTasks.func_234466_a_(this);
        this.setEquipmentBasedOnDifficulty(difficultyInstance);
        this.setEnchantmentBasedOnDifficulty(difficultyInstance);
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    @Override
    protected boolean isDespawnPeaceful() {
        return true;
    }

    @Override
    public boolean canDespawn(double d) {
        return !this.isNoDespawnRequired();
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
        if (this.func_242337_eM()) {
            this.func_234419_d_(EquipmentSlotType.HEAD, new ItemStack(Items.GOLDEN_HELMET));
            this.func_234419_d_(EquipmentSlotType.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
            this.func_234419_d_(EquipmentSlotType.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
            this.func_234419_d_(EquipmentSlotType.FEET, new ItemStack(Items.GOLDEN_BOOTS));
        }
    }

    private void func_234419_d_(EquipmentSlotType equipmentSlotType, ItemStack itemStack) {
        if (this.world.rand.nextFloat() < 0.1f) {
            this.setItemStackToSlot(equipmentSlotType, itemStack);
        }
    }

    protected Brain.BrainCodec<PiglinEntity> getBrainCodec() {
        return Brain.createCodec(field_234414_c_, field_234405_b_);
    }

    @Override
    protected Brain<?> createBrain(Dynamic<?> dynamic) {
        return PiglinTasks.func_234469_a_(this, this.getBrainCodec().deserialize(dynamic));
    }

    public Brain<PiglinEntity> getBrain() {
        return super.getBrain();
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ActionResultType actionResultType = super.func_230254_b_(playerEntity, hand);
        if (actionResultType.isSuccessOrConsume()) {
            return actionResultType;
        }
        if (!this.world.isRemote) {
            return PiglinTasks.func_234471_a_(this, playerEntity, hand);
        }
        boolean bl = PiglinTasks.func_234489_b_(this, playerEntity.getHeldItem(hand)) && this.func_234424_eM_() != PiglinAction.ADMIRING_ITEM;
        return bl ? ActionResultType.SUCCESS : ActionResultType.PASS;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return this.isChild() ? 0.93f : 1.74f;
    }

    @Override
    public double getMountedYOffset() {
        return (double)this.getHeight() * 0.92;
    }

    @Override
    public void setChild(boolean bl) {
        this.getDataManager().set(field_234415_d_, bl);
        if (!this.world.isRemote) {
            ModifiableAttributeInstance modifiableAttributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
            modifiableAttributeInstance.removeModifier(BABY_SPEED_MODIFIER);
            if (bl) {
                modifiableAttributeInstance.applyNonPersistentModifier(BABY_SPEED_MODIFIER);
            }
        }
    }

    @Override
    public boolean isChild() {
        return this.getDataManager().get(field_234415_d_);
    }

    private void setCannotHunt(boolean bl) {
        this.field_234407_bB_ = bl;
    }

    @Override
    protected boolean func_234422_eK_() {
        return !this.field_234407_bB_;
    }

    @Override
    protected void updateAITasks() {
        this.world.getProfiler().startSection("piglinBrain");
        this.getBrain().tick((ServerWorld)this.world, this);
        this.world.getProfiler().endSection();
        PiglinTasks.func_234486_b_(this);
        super.updateAITasks();
    }

    @Override
    protected int getExperiencePoints(PlayerEntity playerEntity) {
        return this.experienceValue;
    }

    @Override
    protected void func_234416_a_(ServerWorld serverWorld) {
        PiglinTasks.func_234496_c_(this);
        this.inventory.func_233543_f_().forEach(this::entityDropItem);
        super.func_234416_a_(serverWorld);
    }

    private ItemStack func_234432_eW_() {
        return (double)this.rand.nextFloat() < 0.5 ? new ItemStack(Items.CROSSBOW) : new ItemStack(Items.GOLDEN_SWORD);
    }

    private boolean func_234433_eX_() {
        return this.dataManager.get(field_234409_bv_);
    }

    @Override
    public void setCharging(boolean bl) {
        this.dataManager.set(field_234409_bv_, bl);
    }

    @Override
    public void func_230283_U__() {
        this.idleTime = 0;
    }

    @Override
    public PiglinAction func_234424_eM_() {
        if (this.func_234425_eN_()) {
            return PiglinAction.DANCING;
        }
        if (PiglinTasks.func_234480_a_(this.getHeldItemOffhand().getItem())) {
            return PiglinAction.ADMIRING_ITEM;
        }
        if (this.isAggressive() && this.func_242338_eO()) {
            return PiglinAction.ATTACKING_WITH_MELEE_WEAPON;
        }
        if (this.func_234433_eX_()) {
            return PiglinAction.CROSSBOW_CHARGE;
        }
        return this.isAggressive() && this.canEquip(Items.CROSSBOW) ? PiglinAction.CROSSBOW_HOLD : PiglinAction.DEFAULT;
    }

    public boolean func_234425_eN_() {
        return this.dataManager.get(DANCING);
    }

    public void func_234442_u_(boolean bl) {
        this.dataManager.set(DANCING, bl);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        boolean bl = super.attackEntityFrom(damageSource, f);
        if (this.world.isRemote) {
            return true;
        }
        if (bl && damageSource.getTrueSource() instanceof LivingEntity) {
            PiglinTasks.func_234468_a_(this, (LivingEntity)damageSource.getTrueSource());
        }
        return bl;
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity livingEntity, float f) {
        this.func_234281_b_(this, 1.6f);
    }

    @Override
    public void func_230284_a_(LivingEntity livingEntity, ItemStack itemStack, ProjectileEntity projectileEntity, float f) {
        this.func_234279_a_(this, livingEntity, projectileEntity, f, 1.6f);
    }

    @Override
    public boolean func_230280_a_(ShootableItem shootableItem) {
        return shootableItem == Items.CROSSBOW;
    }

    protected void func_234438_m_(ItemStack itemStack) {
        this.func_233657_b_(EquipmentSlotType.MAINHAND, itemStack);
    }

    protected void func_234439_n_(ItemStack itemStack) {
        if (itemStack.getItem() == PiglinTasks.field_234444_a_) {
            this.setItemStackToSlot(EquipmentSlotType.OFFHAND, itemStack);
            this.func_233663_d_(EquipmentSlotType.OFFHAND);
        } else {
            this.func_233657_b_(EquipmentSlotType.OFFHAND, itemStack);
        }
    }

    @Override
    public boolean func_230293_i_(ItemStack itemStack) {
        return this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING) && this.canPickUpLoot() && PiglinTasks.func_234474_a_(this, itemStack);
    }

    protected boolean func_234440_o_(ItemStack itemStack) {
        EquipmentSlotType equipmentSlotType = MobEntity.getSlotForItemStack(itemStack);
        ItemStack itemStack2 = this.getItemStackFromSlot(equipmentSlotType);
        return this.shouldExchangeEquipment(itemStack, itemStack2);
    }

    @Override
    protected boolean shouldExchangeEquipment(ItemStack itemStack, ItemStack itemStack2) {
        boolean bl;
        if (EnchantmentHelper.hasBindingCurse(itemStack2)) {
            return true;
        }
        boolean bl2 = PiglinTasks.func_234480_a_(itemStack.getItem()) || itemStack.getItem() == Items.CROSSBOW;
        boolean bl3 = bl = PiglinTasks.func_234480_a_(itemStack2.getItem()) || itemStack2.getItem() == Items.CROSSBOW;
        if (bl2 && !bl) {
            return false;
        }
        if (!bl2 && bl) {
            return true;
        }
        return this.func_242337_eM() && itemStack.getItem() != Items.CROSSBOW && itemStack2.getItem() == Items.CROSSBOW ? false : super.shouldExchangeEquipment(itemStack, itemStack2);
    }

    @Override
    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        this.triggerItemPickupTrigger(itemEntity);
        PiglinTasks.func_234470_a_(this, itemEntity);
    }

    @Override
    public boolean startRiding(Entity entity2, boolean bl) {
        if (this.isChild() && entity2.getType() == EntityType.HOGLIN) {
            entity2 = this.func_234417_b_(entity2, 3);
        }
        return super.startRiding(entity2, bl);
    }

    private Entity func_234417_b_(Entity entity2, int n) {
        List<Entity> list = entity2.getPassengers();
        return n != 1 && !list.isEmpty() ? this.func_234417_b_(list.get(0), n - 1) : entity2;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.world.isRemote ? null : PiglinTasks.func_241429_d_(this).orElse(null);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_PIGLIN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIGLIN_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_PIGLIN_STEP, 0.15f, 1.0f);
    }

    protected void func_241417_a_(SoundEvent soundEvent) {
        this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
    }

    @Override
    protected void func_241848_eP() {
        this.func_241417_a_(SoundEvents.ENTITY_PIGLIN_CONVERTED_TO_ZOMBIFIED);
    }
}

