/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.controller.JumpController;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.loot.LootContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SMountEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;

public abstract class MobEntity
extends LivingEntity {
    private static final DataParameter<Byte> AI_FLAGS = EntityDataManager.createKey(MobEntity.class, DataSerializers.BYTE);
    public int livingSoundTime;
    protected int experienceValue;
    protected LookController lookController;
    protected MovementController moveController;
    protected JumpController jumpController;
    private final BodyController bodyController;
    protected PathNavigator navigator;
    protected final GoalSelector goalSelector;
    protected final GoalSelector targetSelector;
    private LivingEntity attackTarget;
    private final EntitySenses senses;
    private final NonNullList<ItemStack> inventoryHands = NonNullList.withSize(2, ItemStack.EMPTY);
    protected final float[] inventoryHandsDropChances = new float[2];
    private final NonNullList<ItemStack> inventoryArmor = NonNullList.withSize(4, ItemStack.EMPTY);
    protected final float[] inventoryArmorDropChances = new float[4];
    private boolean canPickUpLoot;
    private boolean persistenceRequired;
    private final Map<PathNodeType, Float> mapPathPriority = Maps.newEnumMap(PathNodeType.class);
    private ResourceLocation deathLootTable;
    private long deathLootTableSeed;
    @Nullable
    private Entity leashHolder;
    private int leashHolderID;
    @Nullable
    private CompoundNBT leashNBTTag;
    private BlockPos homePosition = BlockPos.ZERO;
    private float maximumHomeDistance = -1.0f;

    protected MobEntity(EntityType<? extends MobEntity> entityType, World world) {
        super((EntityType<? extends LivingEntity>)entityType, world);
        this.goalSelector = new GoalSelector(world.getWorldProfiler());
        this.targetSelector = new GoalSelector(world.getWorldProfiler());
        this.lookController = new LookController(this);
        this.moveController = new MovementController(this);
        this.jumpController = new JumpController(this);
        this.bodyController = this.createBodyController();
        this.navigator = this.createNavigator(world);
        this.senses = new EntitySenses(this);
        Arrays.fill(this.inventoryArmorDropChances, 0.085f);
        Arrays.fill(this.inventoryHandsDropChances, 0.085f);
        if (world != null && !world.isRemote) {
            this.registerGoals();
        }
    }

    protected void registerGoals() {
    }

    public static AttributeModifierMap.MutableAttribute func_233666_p_() {
        return LivingEntity.registerAttributes().createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0).createMutableAttribute(Attributes.ATTACK_KNOCKBACK);
    }

    protected PathNavigator createNavigator(World world) {
        return new GroundPathNavigator(this, world);
    }

    protected boolean func_230286_q_() {
        return true;
    }

    public float getPathPriority(PathNodeType pathNodeType) {
        MobEntity mobEntity = this.getRidingEntity() instanceof MobEntity && ((MobEntity)this.getRidingEntity()).func_230286_q_() ? (MobEntity)this.getRidingEntity() : this;
        Float f = mobEntity.mapPathPriority.get((Object)pathNodeType);
        return f == null ? pathNodeType.getPriority() : f.floatValue();
    }

    public void setPathPriority(PathNodeType pathNodeType, float f) {
        this.mapPathPriority.put(pathNodeType, Float.valueOf(f));
    }

    public boolean func_233660_b_(PathNodeType pathNodeType) {
        return pathNodeType != PathNodeType.DANGER_FIRE && pathNodeType != PathNodeType.DANGER_CACTUS && pathNodeType != PathNodeType.DANGER_OTHER && pathNodeType != PathNodeType.WALKABLE_DOOR;
    }

    protected BodyController createBodyController() {
        return new BodyController(this);
    }

    public LookController getLookController() {
        return this.lookController;
    }

    public MovementController getMoveHelper() {
        if (this.isPassenger() && this.getRidingEntity() instanceof MobEntity) {
            MobEntity mobEntity = (MobEntity)this.getRidingEntity();
            return mobEntity.getMoveHelper();
        }
        return this.moveController;
    }

    public JumpController getJumpController() {
        return this.jumpController;
    }

    public PathNavigator getNavigator() {
        if (this.isPassenger() && this.getRidingEntity() instanceof MobEntity) {
            MobEntity mobEntity = (MobEntity)this.getRidingEntity();
            return mobEntity.getNavigator();
        }
        return this.navigator;
    }

    public EntitySenses getEntitySenses() {
        return this.senses;
    }

    @Nullable
    public LivingEntity getAttackTarget() {
        return this.attackTarget;
    }

    public void setAttackTarget(@Nullable LivingEntity livingEntity) {
        this.attackTarget = livingEntity;
        Reflector.callVoid(Reflector.ForgeHooks_onLivingSetAttackTarget, this, livingEntity);
    }

    @Override
    public boolean canAttack(EntityType<?> entityType) {
        return entityType != EntityType.GHAST;
    }

    public boolean func_230280_a_(ShootableItem shootableItem) {
        return true;
    }

    public void eatGrassBonus() {
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(AI_FLAGS, (byte)0);
    }

    public int getTalkInterval() {
        return 1;
    }

    public void playAmbientSound() {
        SoundEvent soundEvent = this.getAmbientSound();
        if (soundEvent != null) {
            this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.world.getProfiler().startSection("mobBaseTick");
        if (this.isAlive() && this.rand.nextInt(1000) < this.livingSoundTime++) {
            this.func_241821_eG();
            this.playAmbientSound();
        }
        this.world.getProfiler().endSection();
    }

    @Override
    protected void playHurtSound(DamageSource damageSource) {
        this.func_241821_eG();
        super.playHurtSound(damageSource);
    }

    private void func_241821_eG() {
        this.livingSoundTime = -this.getTalkInterval();
    }

    @Override
    protected int getExperiencePoints(PlayerEntity playerEntity) {
        if (this.experienceValue > 0) {
            int n;
            int n2 = this.experienceValue;
            for (n = 0; n < this.inventoryArmor.size(); ++n) {
                if (this.inventoryArmor.get(n).isEmpty() || !(this.inventoryArmorDropChances[n] <= 1.0f)) continue;
                n2 += 1 + this.rand.nextInt(3);
            }
            for (n = 0; n < this.inventoryHands.size(); ++n) {
                if (this.inventoryHands.get(n).isEmpty() || !(this.inventoryHandsDropChances[n] <= 1.0f)) continue;
                n2 += 1 + this.rand.nextInt(3);
            }
            return n2;
        }
        return this.experienceValue;
    }

    public void spawnExplosionParticle() {
        if (this.world.isRemote) {
            for (int i = 0; i < 20; ++i) {
                double d = this.rand.nextGaussian() * 0.02;
                double d2 = this.rand.nextGaussian() * 0.02;
                double d3 = this.rand.nextGaussian() * 0.02;
                double d4 = 10.0;
                this.world.addParticle(ParticleTypes.POOF, this.getPosXWidth(1.0) - d * 10.0, this.getPosYRandom() - d2 * 10.0, this.getPosZRandom(1.0) - d3 * 10.0, d, d2, d3);
            }
        } else {
            this.world.setEntityState(this, (byte)20);
        }
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 20) {
            this.spawnExplosionParticle();
        } else {
            super.handleStatusUpdate(by);
        }
    }

    @Override
    public void tick() {
        if (Config.isSmoothWorld() && this.canSkipUpdate()) {
            this.onUpdateMinimal();
        } else {
            super.tick();
            if (!this.world.isRemote) {
                this.updateLeashedState();
                if (this.ticksExisted % 5 == 0) {
                    this.updateMovementGoalFlags();
                }
            }
        }
    }

    protected void updateMovementGoalFlags() {
        boolean bl = !(this.getControllingPassenger() instanceof MobEntity);
        boolean bl2 = !(this.getRidingEntity() instanceof BoatEntity);
        this.goalSelector.setFlag(Goal.Flag.MOVE, bl);
        this.goalSelector.setFlag(Goal.Flag.JUMP, bl && bl2);
        this.goalSelector.setFlag(Goal.Flag.LOOK, bl);
    }

    @Override
    protected float updateDistance(float f, float f2) {
        this.bodyController.updateRenderAngles();
        return f2;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putBoolean("CanPickUpLoot", this.canPickUpLoot());
        compoundNBT.putBoolean("PersistenceRequired", this.persistenceRequired);
        ListNBT listNBT = new ListNBT();
        for (ItemStack object22 : this.inventoryArmor) {
            CompoundNBT compoundNBT2 = new CompoundNBT();
            if (!object22.isEmpty()) {
                object22.write(compoundNBT2);
            }
            listNBT.add(compoundNBT2);
        }
        compoundNBT.put("ArmorItems", listNBT);
        ListNBT listNBT2 = new ListNBT();
        for (ItemStack itemStack : this.inventoryHands) {
            CompoundNBT compoundNBT3 = new CompoundNBT();
            if (!itemStack.isEmpty()) {
                itemStack.write(compoundNBT3);
            }
            listNBT2.add(compoundNBT3);
        }
        compoundNBT.put("HandItems", listNBT2);
        ListNBT listNBT3 = new ListNBT();
        for (float f : this.inventoryArmorDropChances) {
            listNBT3.add(FloatNBT.valueOf(f));
        }
        compoundNBT.put("ArmorDropChances", listNBT3);
        ListNBT listNBT4 = new ListNBT();
        for (float f : this.inventoryHandsDropChances) {
            listNBT4.add(FloatNBT.valueOf(f));
        }
        compoundNBT.put("HandDropChances", listNBT4);
        if (this.leashHolder != null) {
            Object object = new CompoundNBT();
            if (this.leashHolder instanceof LivingEntity) {
                UUID uUID = this.leashHolder.getUniqueID();
                ((CompoundNBT)object).putUniqueId("UUID", uUID);
            } else if (this.leashHolder instanceof HangingEntity) {
                BlockPos blockPos = ((HangingEntity)this.leashHolder).getHangingPosition();
                ((CompoundNBT)object).putInt("X", blockPos.getX());
                ((CompoundNBT)object).putInt("Y", blockPos.getY());
                ((CompoundNBT)object).putInt("Z", blockPos.getZ());
            }
            compoundNBT.put("Leash", (INBT)object);
        } else if (this.leashNBTTag != null) {
            compoundNBT.put("Leash", this.leashNBTTag.copy());
        }
        compoundNBT.putBoolean("LeftHanded", this.isLeftHanded());
        if (this.deathLootTable != null) {
            compoundNBT.putString("DeathLootTable", this.deathLootTable.toString());
            if (this.deathLootTableSeed != 0L) {
                compoundNBT.putLong("DeathLootTableSeed", this.deathLootTableSeed);
            }
        }
        if (this.isAIDisabled()) {
            compoundNBT.putBoolean("NoAI", this.isAIDisabled());
        }
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        int n;
        ListNBT listNBT;
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("CanPickUpLoot", 0)) {
            this.setCanPickUpLoot(compoundNBT.getBoolean("CanPickUpLoot"));
        }
        this.persistenceRequired = compoundNBT.getBoolean("PersistenceRequired");
        if (compoundNBT.contains("ArmorItems", 0)) {
            listNBT = compoundNBT.getList("ArmorItems", 10);
            for (n = 0; n < this.inventoryArmor.size(); ++n) {
                this.inventoryArmor.set(n, ItemStack.read(listNBT.getCompound(n)));
            }
        }
        if (compoundNBT.contains("HandItems", 0)) {
            listNBT = compoundNBT.getList("HandItems", 10);
            for (n = 0; n < this.inventoryHands.size(); ++n) {
                this.inventoryHands.set(n, ItemStack.read(listNBT.getCompound(n)));
            }
        }
        if (compoundNBT.contains("ArmorDropChances", 0)) {
            listNBT = compoundNBT.getList("ArmorDropChances", 5);
            for (n = 0; n < listNBT.size(); ++n) {
                this.inventoryArmorDropChances[n] = listNBT.getFloat(n);
            }
        }
        if (compoundNBT.contains("HandDropChances", 0)) {
            listNBT = compoundNBT.getList("HandDropChances", 5);
            for (n = 0; n < listNBT.size(); ++n) {
                this.inventoryHandsDropChances[n] = listNBT.getFloat(n);
            }
        }
        if (compoundNBT.contains("Leash", 1)) {
            this.leashNBTTag = compoundNBT.getCompound("Leash");
        }
        this.setLeftHanded(compoundNBT.getBoolean("LeftHanded"));
        if (compoundNBT.contains("DeathLootTable", 1)) {
            this.deathLootTable = new ResourceLocation(compoundNBT.getString("DeathLootTable"));
            this.deathLootTableSeed = compoundNBT.getLong("DeathLootTableSeed");
        }
        this.setNoAI(compoundNBT.getBoolean("NoAI"));
    }

    @Override
    protected void dropLoot(DamageSource damageSource, boolean bl) {
        super.dropLoot(damageSource, bl);
        this.deathLootTable = null;
    }

    @Override
    protected LootContext.Builder getLootContextBuilder(boolean bl, DamageSource damageSource) {
        return super.getLootContextBuilder(bl, damageSource).withSeededRandom(this.deathLootTableSeed, this.rand);
    }

    @Override
    public final ResourceLocation getLootTableResourceLocation() {
        return this.deathLootTable == null ? this.getLootTable() : this.deathLootTable;
    }

    protected ResourceLocation getLootTable() {
        return super.getLootTableResourceLocation();
    }

    public void setMoveForward(float f) {
        this.moveForward = f;
    }

    public void setMoveVertical(float f) {
        this.moveVertical = f;
    }

    public void setMoveStrafing(float f) {
        this.moveStrafing = f;
    }

    @Override
    public void setAIMoveSpeed(float f) {
        super.setAIMoveSpeed(f);
        this.setMoveForward(f);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        this.world.getProfiler().startSection("looting");
        boolean bl = this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING);
        if (Reflector.ForgeEventFactory_getMobGriefingEvent.exists()) {
            bl = Reflector.callBoolean(Reflector.ForgeEventFactory_getMobGriefingEvent, this.world, this);
        }
        if (!this.world.isRemote && this.canPickUpLoot() && this.isAlive() && !this.dead && bl) {
            for (ItemEntity itemEntity : this.world.getEntitiesWithinAABB(ItemEntity.class, this.getBoundingBox().grow(1.0, 0.0, 1.0))) {
                if (itemEntity.removed || itemEntity.getItem().isEmpty() || itemEntity.cannotPickup() || !this.func_230293_i_(itemEntity.getItem())) continue;
                this.updateEquipmentIfNeeded(itemEntity);
            }
        }
        this.world.getProfiler().endSection();
    }

    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getItem();
        if (this.func_233665_g_(itemStack)) {
            this.triggerItemPickupTrigger(itemEntity);
            this.onItemPickup(itemEntity, itemStack.getCount());
            itemEntity.remove();
        }
    }

    public boolean func_233665_g_(ItemStack itemStack) {
        EquipmentSlotType equipmentSlotType = MobEntity.getSlotForItemStack(itemStack);
        ItemStack itemStack2 = this.getItemStackFromSlot(equipmentSlotType);
        boolean bl = this.shouldExchangeEquipment(itemStack, itemStack2);
        if (bl && this.canEquipItem(itemStack)) {
            double d = this.getDropChance(equipmentSlotType);
            if (!itemStack2.isEmpty() && (double)Math.max(this.rand.nextFloat() - 0.1f, 0.0f) < d) {
                this.entityDropItem(itemStack2);
            }
            this.func_233657_b_(equipmentSlotType, itemStack);
            this.playEquipSound(itemStack);
            return false;
        }
        return true;
    }

    protected void func_233657_b_(EquipmentSlotType equipmentSlotType, ItemStack itemStack) {
        this.setItemStackToSlot(equipmentSlotType, itemStack);
        this.func_233663_d_(equipmentSlotType);
        this.persistenceRequired = true;
    }

    public void func_233663_d_(EquipmentSlotType equipmentSlotType) {
        switch (equipmentSlotType.getSlotType()) {
            case HAND: {
                this.inventoryHandsDropChances[equipmentSlotType.getIndex()] = 2.0f;
                break;
            }
            case ARMOR: {
                this.inventoryArmorDropChances[equipmentSlotType.getIndex()] = 2.0f;
            }
        }
    }

    protected boolean shouldExchangeEquipment(ItemStack itemStack, ItemStack itemStack2) {
        if (itemStack2.isEmpty()) {
            return false;
        }
        if (itemStack.getItem() instanceof SwordItem) {
            if (!(itemStack2.getItem() instanceof SwordItem)) {
                return false;
            }
            SwordItem swordItem = (SwordItem)itemStack.getItem();
            SwordItem swordItem2 = (SwordItem)itemStack2.getItem();
            if (swordItem.getAttackDamage() != swordItem2.getAttackDamage()) {
                return swordItem.getAttackDamage() > swordItem2.getAttackDamage();
            }
            return this.func_233659_b_(itemStack, itemStack2);
        }
        if (itemStack.getItem() instanceof BowItem && itemStack2.getItem() instanceof BowItem) {
            return this.func_233659_b_(itemStack, itemStack2);
        }
        if (itemStack.getItem() instanceof CrossbowItem && itemStack2.getItem() instanceof CrossbowItem) {
            return this.func_233659_b_(itemStack, itemStack2);
        }
        if (itemStack.getItem() instanceof ArmorItem) {
            if (EnchantmentHelper.hasBindingCurse(itemStack2)) {
                return true;
            }
            if (!(itemStack2.getItem() instanceof ArmorItem)) {
                return false;
            }
            ArmorItem armorItem = (ArmorItem)itemStack.getItem();
            ArmorItem armorItem2 = (ArmorItem)itemStack2.getItem();
            if (armorItem.getDamageReduceAmount() != armorItem2.getDamageReduceAmount()) {
                return armorItem.getDamageReduceAmount() > armorItem2.getDamageReduceAmount();
            }
            if (armorItem.func_234657_f_() != armorItem2.func_234657_f_()) {
                return armorItem.func_234657_f_() > armorItem2.func_234657_f_();
            }
            return this.func_233659_b_(itemStack, itemStack2);
        }
        if (itemStack.getItem() instanceof ToolItem) {
            if (itemStack2.getItem() instanceof BlockItem) {
                return false;
            }
            if (itemStack2.getItem() instanceof ToolItem) {
                ToolItem toolItem = (ToolItem)itemStack.getItem();
                ToolItem toolItem2 = (ToolItem)itemStack2.getItem();
                if (toolItem.getAttackDamage() != toolItem2.getAttackDamage()) {
                    return toolItem.getAttackDamage() > toolItem2.getAttackDamage();
                }
                return this.func_233659_b_(itemStack, itemStack2);
            }
        }
        return true;
    }

    public boolean func_233659_b_(ItemStack itemStack, ItemStack itemStack2) {
        if (itemStack.getDamage() >= itemStack2.getDamage() && (!itemStack.hasTag() || itemStack2.hasTag())) {
            if (itemStack.hasTag() && itemStack2.hasTag()) {
                return itemStack.getTag().keySet().stream().anyMatch(MobEntity::lambda$func_233659_b_$0) && !itemStack2.getTag().keySet().stream().anyMatch(MobEntity::lambda$func_233659_b_$1);
            }
            return true;
        }
        return false;
    }

    public boolean canEquipItem(ItemStack itemStack) {
        return false;
    }

    public boolean func_230293_i_(ItemStack itemStack) {
        return this.canEquipItem(itemStack);
    }

    public boolean canDespawn(double d) {
        return false;
    }

    public boolean preventDespawn() {
        return this.isPassenger();
    }

    protected boolean isDespawnPeaceful() {
        return true;
    }

    @Override
    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.isDespawnPeaceful()) {
            this.remove();
        } else if (!this.isNoDespawnRequired() && !this.preventDespawn()) {
            PlayerEntity playerEntity = this.world.getClosestPlayer(this, -1.0);
            if (Reflector.ForgeEventFactory_canEntityDespawn.exists()) {
                Object object = Reflector.ForgeEventFactory_canEntityDespawn.call((Object)this);
                if (object == ReflectorForge.EVENT_RESULT_DENY) {
                    this.idleTime = 0;
                    playerEntity = null;
                } else if (object == ReflectorForge.EVENT_RESULT_ALLOW) {
                    this.remove();
                    playerEntity = null;
                }
            }
            if (playerEntity != null) {
                int n;
                int n2;
                double d = playerEntity.getDistanceSq(this);
                if (d > (double)(n2 = (n = this.getType().getClassification().getInstantDespawnDistance()) * n) && this.canDespawn(d)) {
                    this.remove();
                }
                int n3 = this.getType().getClassification().getRandomDespawnDistance();
                int n4 = n3 * n3;
                if (this.idleTime > 600 && this.rand.nextInt(800) == 0 && d > (double)n4 && this.canDespawn(d)) {
                    this.remove();
                } else if (d < (double)n4) {
                    this.idleTime = 0;
                }
            }
        } else {
            this.idleTime = 0;
        }
    }

    @Override
    protected final void updateEntityActionState() {
        ++this.idleTime;
        this.world.getProfiler().startSection("sensing");
        this.senses.tick();
        this.world.getProfiler().endSection();
        this.world.getProfiler().startSection("targetSelector");
        this.targetSelector.tick();
        this.world.getProfiler().endSection();
        this.world.getProfiler().startSection("goalSelector");
        this.goalSelector.tick();
        this.world.getProfiler().endSection();
        this.world.getProfiler().startSection("navigation");
        this.navigator.tick();
        this.world.getProfiler().endSection();
        this.world.getProfiler().startSection("mob tick");
        this.updateAITasks();
        this.world.getProfiler().endSection();
        this.world.getProfiler().startSection("controls");
        this.world.getProfiler().startSection("move");
        this.moveController.tick();
        this.world.getProfiler().endStartSection("look");
        this.lookController.tick();
        this.world.getProfiler().endStartSection("jump");
        this.jumpController.tick();
        this.world.getProfiler().endSection();
        this.world.getProfiler().endSection();
        this.sendDebugPackets();
    }

    protected void sendDebugPackets() {
        DebugPacketSender.sendGoal(this.world, this, this.goalSelector);
    }

    protected void updateAITasks() {
    }

    public int getVerticalFaceSpeed() {
        return 1;
    }

    public int getHorizontalFaceSpeed() {
        return 0;
    }

    public int getFaceRotSpeed() {
        return 1;
    }

    public void faceEntity(Entity entity2, float f, float f2) {
        double d;
        double d2 = entity2.getPosX() - this.getPosX();
        double d3 = entity2.getPosZ() - this.getPosZ();
        if (entity2 instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity2;
            d = livingEntity.getPosYEye() - this.getPosYEye();
        } else {
            d = (entity2.getBoundingBox().minY + entity2.getBoundingBox().maxY) / 2.0 - this.getPosYEye();
        }
        double d4 = MathHelper.sqrt(d2 * d2 + d3 * d3);
        float f3 = (float)(MathHelper.atan2(d3, d2) * 57.2957763671875) - 90.0f;
        float f4 = (float)(-(MathHelper.atan2(d, d4) * 57.2957763671875));
        this.rotationPitch = this.updateRotation(this.rotationPitch, f4, f2);
        this.rotationYaw = this.updateRotation(this.rotationYaw, f3, f);
    }

    private float updateRotation(float f, float f2, float f3) {
        float f4 = MathHelper.wrapDegrees(f2 - f);
        if (f4 > f3) {
            f4 = f3;
        }
        if (f4 < -f3) {
            f4 = -f3;
        }
        return f + f4;
    }

    public static boolean canSpawnOn(EntityType<? extends MobEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        BlockPos blockPos2 = blockPos.down();
        return spawnReason == SpawnReason.SPAWNER || iWorld.getBlockState(blockPos2).canEntitySpawn(iWorld, blockPos2, entityType);
    }

    public boolean canSpawn(IWorld iWorld, SpawnReason spawnReason) {
        return false;
    }

    public boolean isNotColliding(IWorldReader iWorldReader) {
        return !iWorldReader.containsAnyLiquid(this.getBoundingBox()) && iWorldReader.checkNoEntityCollision(this);
    }

    public int getMaxSpawnedInChunk() {
        return 1;
    }

    public boolean isMaxGroupSize(int n) {
        return true;
    }

    @Override
    public int getMaxFallHeight() {
        if (this.getAttackTarget() == null) {
            return 0;
        }
        int n = (int)(this.getHealth() - this.getMaxHealth() * 0.33f);
        if ((n -= (3 - this.world.getDifficulty().getId()) * 4) < 0) {
            n = 0;
        }
        return n + 3;
    }

    @Override
    public Iterable<ItemStack> getHeldEquipment() {
        return this.inventoryHands;
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return this.inventoryArmor;
    }

    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType equipmentSlotType) {
        switch (equipmentSlotType.getSlotType()) {
            case HAND: {
                return this.inventoryHands.get(equipmentSlotType.getIndex());
            }
            case ARMOR: {
                return this.inventoryArmor.get(equipmentSlotType.getIndex());
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType equipmentSlotType, ItemStack itemStack) {
        switch (equipmentSlotType.getSlotType()) {
            case HAND: {
                this.inventoryHands.set(equipmentSlotType.getIndex(), itemStack);
                break;
            }
            case ARMOR: {
                this.inventoryArmor.set(equipmentSlotType.getIndex(), itemStack);
            }
        }
    }

    @Override
    protected void dropSpecialItems(DamageSource damageSource, int n, boolean bl) {
        super.dropSpecialItems(damageSource, n, bl);
        for (EquipmentSlotType equipmentSlotType : EquipmentSlotType.values()) {
            boolean bl2;
            ItemStack itemStack = this.getItemStackFromSlot(equipmentSlotType);
            float f = this.getDropChance(equipmentSlotType);
            boolean bl3 = bl2 = f > 1.0f;
            if (itemStack.isEmpty() || EnchantmentHelper.hasVanishingCurse(itemStack) || !bl && !bl2 || !(Math.max(this.rand.nextFloat() - (float)n * 0.01f, 0.0f) < f)) continue;
            if (!bl2 && itemStack.isDamageable()) {
                itemStack.setDamage(itemStack.getMaxDamage() - this.rand.nextInt(1 + this.rand.nextInt(Math.max(itemStack.getMaxDamage() - 3, 1))));
            }
            this.entityDropItem(itemStack);
            this.setItemStackToSlot(equipmentSlotType, ItemStack.EMPTY);
        }
    }

    protected float getDropChance(EquipmentSlotType equipmentSlotType) {
        return switch (equipmentSlotType.getSlotType()) {
            case EquipmentSlotType.Group.HAND -> this.inventoryHandsDropChances[equipmentSlotType.getIndex()];
            case EquipmentSlotType.Group.ARMOR -> this.inventoryArmorDropChances[equipmentSlotType.getIndex()];
            default -> 0.0f;
        };
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
        if (this.rand.nextFloat() < 0.15f * difficultyInstance.getClampedAdditionalDifficulty()) {
            float f;
            int n = this.rand.nextInt(2);
            float f2 = f = this.world.getDifficulty() == Difficulty.HARD ? 0.1f : 0.25f;
            if (this.rand.nextFloat() < 0.095f) {
                ++n;
            }
            if (this.rand.nextFloat() < 0.095f) {
                ++n;
            }
            if (this.rand.nextFloat() < 0.095f) {
                ++n;
            }
            boolean bl = true;
            for (EquipmentSlotType equipmentSlotType : EquipmentSlotType.values()) {
                Item item;
                if (equipmentSlotType.getSlotType() != EquipmentSlotType.Group.ARMOR) continue;
                ItemStack itemStack = this.getItemStackFromSlot(equipmentSlotType);
                if (!bl && this.rand.nextFloat() < f) break;
                bl = false;
                if (!itemStack.isEmpty() || (item = MobEntity.getArmorByChance(equipmentSlotType, n)) == null) continue;
                this.setItemStackToSlot(equipmentSlotType, new ItemStack(item));
            }
        }
    }

    public static EquipmentSlotType getSlotForItemStack(ItemStack itemStack) {
        Object object;
        if (Reflector.IForgeItemStack_getEquipmentSlot.exists() && (object = (EquipmentSlotType)((Object)Reflector.call(itemStack, Reflector.IForgeItemStack_getEquipmentSlot, new Object[0]))) != null) {
            return object;
        }
        object = itemStack.getItem();
        if (!(object == Blocks.CARVED_PUMPKIN.asItem() || object instanceof BlockItem && ((BlockItem)object).getBlock() instanceof AbstractSkullBlock)) {
            if (object instanceof ArmorItem) {
                return ((ArmorItem)object).getEquipmentSlot();
            }
            if (object == Items.ELYTRA) {
                return EquipmentSlotType.CHEST;
            }
            return ReflectorForge.isShield(itemStack, null) ? EquipmentSlotType.OFFHAND : EquipmentSlotType.MAINHAND;
        }
        return EquipmentSlotType.HEAD;
    }

    @Nullable
    public static Item getArmorByChance(EquipmentSlotType equipmentSlotType, int n) {
        switch (equipmentSlotType) {
            case HEAD: {
                if (n == 0) {
                    return Items.LEATHER_HELMET;
                }
                if (n == 1) {
                    return Items.GOLDEN_HELMET;
                }
                if (n == 2) {
                    return Items.CHAINMAIL_HELMET;
                }
                if (n == 3) {
                    return Items.IRON_HELMET;
                }
                if (n == 4) {
                    return Items.DIAMOND_HELMET;
                }
            }
            case CHEST: {
                if (n == 0) {
                    return Items.LEATHER_CHESTPLATE;
                }
                if (n == 1) {
                    return Items.GOLDEN_CHESTPLATE;
                }
                if (n == 2) {
                    return Items.CHAINMAIL_CHESTPLATE;
                }
                if (n == 3) {
                    return Items.IRON_CHESTPLATE;
                }
                if (n == 4) {
                    return Items.DIAMOND_CHESTPLATE;
                }
            }
            case LEGS: {
                if (n == 0) {
                    return Items.LEATHER_LEGGINGS;
                }
                if (n == 1) {
                    return Items.GOLDEN_LEGGINGS;
                }
                if (n == 2) {
                    return Items.CHAINMAIL_LEGGINGS;
                }
                if (n == 3) {
                    return Items.IRON_LEGGINGS;
                }
                if (n == 4) {
                    return Items.DIAMOND_LEGGINGS;
                }
            }
            case FEET: {
                if (n == 0) {
                    return Items.LEATHER_BOOTS;
                }
                if (n == 1) {
                    return Items.GOLDEN_BOOTS;
                }
                if (n == 2) {
                    return Items.CHAINMAIL_BOOTS;
                }
                if (n == 3) {
                    return Items.IRON_BOOTS;
                }
                if (n != 4) break;
                return Items.DIAMOND_BOOTS;
            }
        }
        return null;
    }

    protected void setEnchantmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
        float f = difficultyInstance.getClampedAdditionalDifficulty();
        this.func_241844_w(f);
        for (EquipmentSlotType equipmentSlotType : EquipmentSlotType.values()) {
            if (equipmentSlotType.getSlotType() != EquipmentSlotType.Group.ARMOR) continue;
            this.func_242289_a(f, equipmentSlotType);
        }
    }

    protected void func_241844_w(float f) {
        if (!this.getHeldItemMainhand().isEmpty() && this.rand.nextFloat() < 0.25f * f) {
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItemMainhand(), (int)(5.0f + f * (float)this.rand.nextInt(18)), false));
        }
    }

    protected void func_242289_a(float f, EquipmentSlotType equipmentSlotType) {
        ItemStack itemStack = this.getItemStackFromSlot(equipmentSlotType);
        if (!itemStack.isEmpty() && this.rand.nextFloat() < 0.5f * f) {
            this.setItemStackToSlot(equipmentSlotType, EnchantmentHelper.addRandomEnchantment(this.rand, itemStack, (int)(5.0f + f * (float)this.rand.nextInt(18)), false));
        }
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        this.getAttribute(Attributes.FOLLOW_RANGE).applyPersistentModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05, AttributeModifier.Operation.MULTIPLY_BASE));
        if (this.rand.nextFloat() < 0.05f) {
            this.setLeftHanded(false);
        } else {
            this.setLeftHanded(true);
        }
        return iLivingEntityData;
    }

    public boolean canBeSteered() {
        return true;
    }

    public void enablePersistence() {
        this.persistenceRequired = true;
    }

    public void setDropChance(EquipmentSlotType equipmentSlotType, float f) {
        switch (equipmentSlotType.getSlotType()) {
            case HAND: {
                this.inventoryHandsDropChances[equipmentSlotType.getIndex()] = f;
                break;
            }
            case ARMOR: {
                this.inventoryArmorDropChances[equipmentSlotType.getIndex()] = f;
            }
        }
    }

    public boolean canPickUpLoot() {
        return this.canPickUpLoot;
    }

    public void setCanPickUpLoot(boolean bl) {
        this.canPickUpLoot = bl;
    }

    @Override
    public boolean canPickUpItem(ItemStack itemStack) {
        EquipmentSlotType equipmentSlotType = MobEntity.getSlotForItemStack(itemStack);
        return this.getItemStackFromSlot(equipmentSlotType).isEmpty() && this.canPickUpLoot();
    }

    public boolean isNoDespawnRequired() {
        return this.persistenceRequired;
    }

    @Override
    public final ActionResultType processInitialInteract(PlayerEntity playerEntity, Hand hand) {
        if (!this.isAlive()) {
            return ActionResultType.PASS;
        }
        if (this.getLeashHolder() == playerEntity) {
            this.clearLeashed(true, !playerEntity.abilities.isCreativeMode);
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        ActionResultType actionResultType = this.func_233661_c_(playerEntity, hand);
        if (actionResultType.isSuccessOrConsume()) {
            return actionResultType;
        }
        actionResultType = this.func_230254_b_(playerEntity, hand);
        return actionResultType.isSuccessOrConsume() ? actionResultType : super.processInitialInteract(playerEntity, hand);
    }

    private ActionResultType func_233661_c_(PlayerEntity playerEntity, Hand hand) {
        Object object;
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (itemStack.getItem() == Items.LEAD && this.canBeLeashedTo(playerEntity)) {
            this.setLeashHolder(playerEntity, false);
            itemStack.shrink(1);
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        if (itemStack.getItem() == Items.NAME_TAG && ((ActionResultType)((Object)(object = itemStack.interactWithEntity(playerEntity, this, hand)))).isSuccessOrConsume()) {
            return object;
        }
        if (itemStack.getItem() instanceof SpawnEggItem) {
            if (this.world instanceof ServerWorld) {
                object = (SpawnEggItem)itemStack.getItem();
                Optional<MobEntity> optional = ((SpawnEggItem)object).getChildToSpawn(playerEntity, this, this.getType(), (ServerWorld)this.world, this.getPositionVec(), itemStack);
                optional.ifPresent(arg_0 -> this.lambda$func_233661_c_$2(playerEntity, arg_0));
                return optional.isPresent() ? ActionResultType.SUCCESS : ActionResultType.PASS;
            }
            return ActionResultType.CONSUME;
        }
        return ActionResultType.PASS;
    }

    protected void onChildSpawnFromEgg(PlayerEntity playerEntity, MobEntity mobEntity) {
    }

    protected ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        return ActionResultType.PASS;
    }

    public boolean isWithinHomeDistanceCurrentPosition() {
        return this.isWithinHomeDistanceFromPosition(this.getPosition());
    }

    public boolean isWithinHomeDistanceFromPosition(BlockPos blockPos) {
        if (this.maximumHomeDistance == -1.0f) {
            return false;
        }
        return this.homePosition.distanceSq(blockPos) < (double)(this.maximumHomeDistance * this.maximumHomeDistance);
    }

    public void setHomePosAndDistance(BlockPos blockPos, int n) {
        this.homePosition = blockPos;
        this.maximumHomeDistance = n;
    }

    public BlockPos getHomePosition() {
        return this.homePosition;
    }

    public float getMaximumHomeDistance() {
        return this.maximumHomeDistance;
    }

    public boolean detachHome() {
        return this.maximumHomeDistance != -1.0f;
    }

    @Nullable
    public <T extends MobEntity> T func_233656_b_(EntityType<T> entityType, boolean bl) {
        if (this.removed) {
            return (T)((MobEntity)null);
        }
        MobEntity mobEntity = (MobEntity)entityType.create(this.world);
        mobEntity.copyLocationAndAnglesFrom(this);
        mobEntity.setChild(this.isChild());
        mobEntity.setNoAI(this.isAIDisabled());
        if (this.hasCustomName()) {
            mobEntity.setCustomName(this.getCustomName());
            mobEntity.setCustomNameVisible(this.isCustomNameVisible());
        }
        if (this.isNoDespawnRequired()) {
            mobEntity.enablePersistence();
        }
        mobEntity.setInvulnerable(this.isInvulnerable());
        if (bl) {
            mobEntity.setCanPickUpLoot(this.canPickUpLoot());
            for (EquipmentSlotType equipmentSlotType : EquipmentSlotType.values()) {
                ItemStack itemStack = this.getItemStackFromSlot(equipmentSlotType);
                if (itemStack.isEmpty()) continue;
                mobEntity.setItemStackToSlot(equipmentSlotType, itemStack.copy());
                mobEntity.setDropChance(equipmentSlotType, this.getDropChance(equipmentSlotType));
                itemStack.setCount(0);
            }
        }
        this.world.addEntity(mobEntity);
        if (this.isPassenger()) {
            Entity entity2 = this.getRidingEntity();
            this.stopRiding();
            mobEntity.startRiding(entity2, false);
        }
        this.remove();
        return (T)mobEntity;
    }

    protected void updateLeashedState() {
        if (this.leashNBTTag != null) {
            this.recreateLeash();
        }
        if (!(this.leashHolder == null || this.isAlive() && this.leashHolder.isAlive())) {
            this.clearLeashed(true, false);
        }
    }

    public void clearLeashed(boolean bl, boolean bl2) {
        if (this.leashHolder != null) {
            this.forceSpawn = false;
            if (!(this.leashHolder instanceof PlayerEntity)) {
                this.leashHolder.forceSpawn = false;
            }
            this.leashHolder = null;
            this.leashNBTTag = null;
            if (!this.world.isRemote && bl2) {
                this.entityDropItem(Items.LEAD);
            }
            if (!this.world.isRemote && bl && this.world instanceof ServerWorld) {
                ((ServerWorld)this.world).getChunkProvider().sendToAllTracking(this, new SMountEntityPacket(this, null));
            }
        }
    }

    public boolean canBeLeashedTo(PlayerEntity playerEntity) {
        return !this.getLeashed() && !(this instanceof IMob);
    }

    public boolean getLeashed() {
        return this.leashHolder != null;
    }

    @Nullable
    public Entity getLeashHolder() {
        if (this.leashHolder == null && this.leashHolderID != 0 && this.world.isRemote) {
            this.leashHolder = this.world.getEntityByID(this.leashHolderID);
        }
        return this.leashHolder;
    }

    public void setLeashHolder(Entity entity2, boolean bl) {
        this.leashHolder = entity2;
        this.leashNBTTag = null;
        this.forceSpawn = true;
        if (!(this.leashHolder instanceof PlayerEntity)) {
            this.leashHolder.forceSpawn = true;
        }
        if (!this.world.isRemote && bl && this.world instanceof ServerWorld) {
            ((ServerWorld)this.world).getChunkProvider().sendToAllTracking(this, new SMountEntityPacket(this, this.leashHolder));
        }
        if (this.isPassenger()) {
            this.stopRiding();
        }
    }

    public void setVehicleEntityId(int n) {
        this.leashHolderID = n;
        this.clearLeashed(false, true);
    }

    @Override
    public boolean startRiding(Entity entity2, boolean bl) {
        boolean bl2 = super.startRiding(entity2, bl);
        if (bl2 && this.getLeashed()) {
            this.clearLeashed(true, false);
        }
        return bl2;
    }

    private void recreateLeash() {
        if (this.leashNBTTag != null && this.world instanceof ServerWorld) {
            if (this.leashNBTTag.hasUniqueId("UUID")) {
                UUID uUID = this.leashNBTTag.getUniqueId("UUID");
                Entity entity2 = ((ServerWorld)this.world).getEntityByUuid(uUID);
                if (entity2 != null) {
                    this.setLeashHolder(entity2, false);
                    return;
                }
            } else if (this.leashNBTTag.contains("X", 0) && this.leashNBTTag.contains("Y", 0) && this.leashNBTTag.contains("Z", 0)) {
                BlockPos blockPos = new BlockPos(this.leashNBTTag.getInt("X"), this.leashNBTTag.getInt("Y"), this.leashNBTTag.getInt("Z"));
                this.setLeashHolder(LeashKnotEntity.create(this.world, blockPos), false);
                return;
            }
            if (this.ticksExisted > 100) {
                this.entityDropItem(Items.LEAD);
                this.leashNBTTag = null;
            }
        }
    }

    @Override
    public boolean replaceItemInInventory(int n, ItemStack itemStack) {
        EquipmentSlotType equipmentSlotType;
        if (n == 98) {
            equipmentSlotType = EquipmentSlotType.MAINHAND;
        } else if (n == 99) {
            equipmentSlotType = EquipmentSlotType.OFFHAND;
        } else if (n == 100 + EquipmentSlotType.HEAD.getIndex()) {
            equipmentSlotType = EquipmentSlotType.HEAD;
        } else if (n == 100 + EquipmentSlotType.CHEST.getIndex()) {
            equipmentSlotType = EquipmentSlotType.CHEST;
        } else if (n == 100 + EquipmentSlotType.LEGS.getIndex()) {
            equipmentSlotType = EquipmentSlotType.LEGS;
        } else {
            if (n != 100 + EquipmentSlotType.FEET.getIndex()) {
                return true;
            }
            equipmentSlotType = EquipmentSlotType.FEET;
        }
        if (!itemStack.isEmpty() && !MobEntity.isItemStackInSlot(equipmentSlotType, itemStack) && equipmentSlotType != EquipmentSlotType.HEAD) {
            return true;
        }
        this.setItemStackToSlot(equipmentSlotType, itemStack);
        return false;
    }

    @Override
    public boolean canPassengerSteer() {
        return this.canBeSteered() && super.canPassengerSteer();
    }

    public static boolean isItemStackInSlot(EquipmentSlotType equipmentSlotType, ItemStack itemStack) {
        EquipmentSlotType equipmentSlotType2 = MobEntity.getSlotForItemStack(itemStack);
        return equipmentSlotType2 == equipmentSlotType || equipmentSlotType2 == EquipmentSlotType.MAINHAND && equipmentSlotType == EquipmentSlotType.OFFHAND || equipmentSlotType2 == EquipmentSlotType.OFFHAND && equipmentSlotType == EquipmentSlotType.MAINHAND;
    }

    @Override
    public boolean isServerWorld() {
        return super.isServerWorld() && !this.isAIDisabled();
    }

    public void setNoAI(boolean bl) {
        byte by = this.dataManager.get(AI_FLAGS);
        this.dataManager.set(AI_FLAGS, bl ? (byte)(by | 1) : (byte)(by & 0xFFFFFFFE));
    }

    public void setLeftHanded(boolean bl) {
        byte by = this.dataManager.get(AI_FLAGS);
        this.dataManager.set(AI_FLAGS, bl ? (byte)(by | 2) : (byte)(by & 0xFFFFFFFD));
    }

    public void setAggroed(boolean bl) {
        byte by = this.dataManager.get(AI_FLAGS);
        this.dataManager.set(AI_FLAGS, bl ? (byte)(by | 4) : (byte)(by & 0xFFFFFFFB));
    }

    public boolean isAIDisabled() {
        return (this.dataManager.get(AI_FLAGS) & 1) != 0;
    }

    public boolean isLeftHanded() {
        return (this.dataManager.get(AI_FLAGS) & 2) != 0;
    }

    public boolean isAggressive() {
        return (this.dataManager.get(AI_FLAGS) & 4) != 0;
    }

    public void setChild(boolean bl) {
    }

    @Override
    public HandSide getPrimaryHand() {
        return this.isLeftHanded() ? HandSide.LEFT : HandSide.RIGHT;
    }

    @Override
    public boolean canAttack(LivingEntity livingEntity) {
        return livingEntity.getType() == EntityType.PLAYER && ((PlayerEntity)livingEntity).abilities.disableDamage ? false : super.canAttack(livingEntity);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity2) {
        boolean bl;
        int n;
        float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f2 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (entity2 instanceof LivingEntity) {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((LivingEntity)entity2).getCreatureAttribute());
            f2 += (float)EnchantmentHelper.getKnockbackModifier(this);
        }
        if ((n = EnchantmentHelper.getFireAspectModifier(this)) > 0) {
            entity2.setFire(n * 4);
        }
        if (bl = entity2.attackEntityFrom(DamageSource.causeMobDamage(this), f)) {
            if (f2 > 0.0f && entity2 instanceof LivingEntity) {
                ((LivingEntity)entity2).applyKnockback(f2 * 0.5f, MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180)), -MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180)));
                this.setMotion(this.getMotion().mul(0.6, 1.0, 0.6));
            }
            if (entity2 instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity)entity2;
                this.func_233655_a_(playerEntity, this.getHeldItemMainhand(), playerEntity.isHandActive() ? playerEntity.getActiveItemStack() : ItemStack.EMPTY);
            }
            this.applyEnchantments(this, entity2);
            this.setLastAttackedEntity(entity2);
        }
        return bl;
    }

    private void func_233655_a_(PlayerEntity playerEntity, ItemStack itemStack, ItemStack itemStack2) {
        if (!itemStack.isEmpty() && !itemStack2.isEmpty() && itemStack.getItem() instanceof AxeItem && itemStack2.getItem() == Items.SHIELD) {
            float f = 0.25f + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05f;
            if (this.rand.nextFloat() < f) {
                playerEntity.getCooldownTracker().setCooldown(Items.SHIELD, 100);
                this.world.setEntityState(playerEntity, (byte)30);
            }
        }
    }

    protected boolean isInDaylight() {
        if (this.world.isDaytime() && !this.world.isRemote) {
            BlockPos blockPos;
            float f = this.getBrightness();
            BlockPos blockPos2 = blockPos = this.getRidingEntity() instanceof BoatEntity ? new BlockPos(this.getPosX(), Math.round(this.getPosY()), this.getPosZ()).up() : new BlockPos(this.getPosX(), Math.round(this.getPosY()), this.getPosZ());
            if (f > 0.5f && this.rand.nextFloat() * 30.0f < (f - 0.4f) * 2.0f && this.world.canSeeSky(blockPos)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void handleFluidJump(ITag<Fluid> iTag) {
        if (this.getNavigator().getCanSwim()) {
            super.handleFluidJump(iTag);
        } else {
            this.setMotion(this.getMotion().add(0.0, 0.3, 0.0));
        }
    }

    @Override
    protected void setDead() {
        super.setDead();
        this.clearLeashed(true, true);
    }

    private boolean canSkipUpdate() {
        double d;
        if (this.isChild()) {
            return true;
        }
        if (this.hurtTime > 0) {
            return true;
        }
        if (this.ticksExisted < 20) {
            return true;
        }
        List list = this.getListPlayers(this.getEntityWorld());
        if (list == null) {
            return true;
        }
        if (list.size() != 1) {
            return true;
        }
        Entity entity2 = (Entity)list.get(0);
        double d2 = Math.max(Math.abs(this.getPosX() - entity2.getPosX()) - 16.0, 0.0);
        double d3 = d2 * d2 + (d = Math.max(Math.abs(this.getPosZ() - entity2.getPosZ()) - 16.0, 0.0)) * d;
        return !this.isInRangeToRenderDist(d3);
    }

    private List getListPlayers(World world) {
        World world2 = this.getEntityWorld();
        if (world2 instanceof ClientWorld) {
            ClientWorld clientWorld = (ClientWorld)world2;
            return clientWorld.getPlayers();
        }
        if (world2 instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)world2;
            return serverWorld.getPlayers();
        }
        return null;
    }

    private void onUpdateMinimal() {
        ++this.idleTime;
        if (this instanceof MonsterEntity) {
            float f = this.getBrightness();
            boolean bl = this instanceof AbstractRaiderEntity;
            if (f > 0.5f || bl) {
                this.idleTime += 2;
            }
        }
    }

    private void lambda$func_233661_c_$2(PlayerEntity playerEntity, MobEntity mobEntity) {
        this.onChildSpawnFromEgg(playerEntity, mobEntity);
    }

    private static boolean lambda$func_233659_b_$1(String string) {
        return !string.equals("Damage");
    }

    private static boolean lambda$func_233659_b_$0(String string) {
        return !string.equals("Damage");
    }
}

