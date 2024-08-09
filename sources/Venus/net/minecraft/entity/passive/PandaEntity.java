/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PandaEntity
extends AnimalEntity {
    private static final DataParameter<Integer> UNHAPPY_COUNTER = EntityDataManager.createKey(PandaEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> SNEEZE_COUNTER = EntityDataManager.createKey(PandaEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> EAT_COUNTER = EntityDataManager.createKey(PandaEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Byte> MAIN_GENE = EntityDataManager.createKey(PandaEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> HIDDEN_GENE = EntityDataManager.createKey(PandaEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> PANDA_FLAGS = EntityDataManager.createKey(PandaEntity.class, DataSerializers.BYTE);
    private static final EntityPredicate BREED_TARGETING = new EntityPredicate().setDistance(8.0).allowFriendlyFire().allowInvulnerable();
    private boolean gotBamboo;
    private boolean didBite;
    public int rollCounter;
    private Vector3d rollDelta;
    private float sitAmount;
    private float sitAmountO;
    private float onBackAmount;
    private float onBackAmountO;
    private float rollAmount;
    private float rollAmountO;
    private WatchGoal watchGoal;
    private static final Predicate<ItemEntity> PANDA_ITEMS = PandaEntity::lambda$static$0;

    public PandaEntity(EntityType<? extends PandaEntity> entityType, World world) {
        super((EntityType<? extends AnimalEntity>)entityType, world);
        this.moveController = new MoveHelperController(this);
        if (!this.isChild()) {
            this.setCanPickUpLoot(false);
        }
    }

    @Override
    public boolean canPickUpItem(ItemStack itemStack) {
        EquipmentSlotType equipmentSlotType = MobEntity.getSlotForItemStack(itemStack);
        if (!this.getItemStackFromSlot(equipmentSlotType).isEmpty()) {
            return true;
        }
        return equipmentSlotType == EquipmentSlotType.MAINHAND && super.canPickUpItem(itemStack);
    }

    public int getUnhappyCounter() {
        return this.dataManager.get(UNHAPPY_COUNTER);
    }

    public void setUnhappyCounter(int n) {
        this.dataManager.set(UNHAPPY_COUNTER, n);
    }

    public boolean func_213539_dW() {
        return this.getPandaFlag(1);
    }

    public boolean func_213556_dX() {
        return this.getPandaFlag(1);
    }

    public void func_213553_r(boolean bl) {
        this.setPandaFlag(8, bl);
    }

    public boolean func_213567_dY() {
        return this.getPandaFlag(1);
    }

    public void func_213542_s(boolean bl) {
        this.setPandaFlag(16, bl);
    }

    public boolean func_213578_dZ() {
        return this.dataManager.get(EAT_COUNTER) > 0;
    }

    public void func_213534_t(boolean bl) {
        this.dataManager.set(EAT_COUNTER, bl ? 1 : 0);
    }

    private int getEatCounter() {
        return this.dataManager.get(EAT_COUNTER);
    }

    private void setEatCounter(int n) {
        this.dataManager.set(EAT_COUNTER, n);
    }

    public void func_213581_u(boolean bl) {
        this.setPandaFlag(2, bl);
        if (!bl) {
            this.setSneezeCounter(0);
        }
    }

    public int getSneezeCounter() {
        return this.dataManager.get(SNEEZE_COUNTER);
    }

    public void setSneezeCounter(int n) {
        this.dataManager.set(SNEEZE_COUNTER, n);
    }

    public Gene getMainGene() {
        return Gene.byIndex(this.dataManager.get(MAIN_GENE).byteValue());
    }

    public void setMainGene(Gene gene) {
        if (gene.getIndex() > 6) {
            gene = Gene.getRandomType(this.rand);
        }
        this.dataManager.set(MAIN_GENE, (byte)gene.getIndex());
    }

    public Gene getHiddenGene() {
        return Gene.byIndex(this.dataManager.get(HIDDEN_GENE).byteValue());
    }

    public void setHiddenGene(Gene gene) {
        if (gene.getIndex() > 6) {
            gene = Gene.getRandomType(this.rand);
        }
        this.dataManager.set(HIDDEN_GENE, (byte)gene.getIndex());
    }

    public boolean func_213564_eh() {
        return this.getPandaFlag(1);
    }

    public void func_213576_v(boolean bl) {
        this.setPandaFlag(4, bl);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(UNHAPPY_COUNTER, 0);
        this.dataManager.register(SNEEZE_COUNTER, 0);
        this.dataManager.register(MAIN_GENE, (byte)0);
        this.dataManager.register(HIDDEN_GENE, (byte)0);
        this.dataManager.register(PANDA_FLAGS, (byte)0);
        this.dataManager.register(EAT_COUNTER, 0);
    }

    private boolean getPandaFlag(int n) {
        return (this.dataManager.get(PANDA_FLAGS) & n) != 0;
    }

    private void setPandaFlag(int n, boolean bl) {
        byte by = this.dataManager.get(PANDA_FLAGS);
        if (bl) {
            this.dataManager.set(PANDA_FLAGS, (byte)(by | n));
        } else {
            this.dataManager.set(PANDA_FLAGS, (byte)(by & ~n));
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putString("MainGene", this.getMainGene().getName());
        compoundNBT.putString("HiddenGene", this.getHiddenGene().getName());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setMainGene(Gene.byName(compoundNBT.getString("MainGene")));
        this.setHiddenGene(Gene.byName(compoundNBT.getString("HiddenGene")));
    }

    @Override
    @Nullable
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        PandaEntity pandaEntity = EntityType.PANDA.create(serverWorld);
        if (ageableEntity instanceof PandaEntity) {
            pandaEntity.getGenesForChildFromParents(this, (PandaEntity)ageableEntity);
        }
        pandaEntity.setAttributes();
        return pandaEntity;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 2.0));
        this.goalSelector.addGoal(2, new MateGoal(this, this, 1.0));
        this.goalSelector.addGoal(3, new AttackGoal(this, (double)1.2f, true));
        this.goalSelector.addGoal(4, new TemptGoal((CreatureEntity)this, 1.0, Ingredient.fromItems(Blocks.BAMBOO.asItem()), false));
        this.goalSelector.addGoal(6, new AvoidGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 2.0, 2.0));
        this.goalSelector.addGoal(6, new AvoidGoal<MonsterEntity>(this, MonsterEntity.class, 4.0f, 2.0, 2.0));
        this.goalSelector.addGoal(7, new SitGoal(this));
        this.goalSelector.addGoal(8, new LieBackGoal(this));
        this.goalSelector.addGoal(8, new ChildPlayGoal(this));
        this.watchGoal = new WatchGoal(this, PlayerEntity.class, 6.0f);
        this.goalSelector.addGoal(9, this.watchGoal);
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(12, new RollGoal(this));
        this.goalSelector.addGoal(13, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(14, new WaterAvoidingRandomWalkingGoal(this, 1.0));
        this.targetSelector.addGoal(1, new RevengeGoal(this, new Class[0]).setCallsForHelp(new Class[0]));
    }

    public static AttributeModifierMap.MutableAttribute func_234204_eW_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.15f).createMutableAttribute(Attributes.ATTACK_DAMAGE, 6.0);
    }

    public Gene func_213590_ei() {
        return Gene.func_221101_b(this.getMainGene(), this.getHiddenGene());
    }

    public boolean isLazy() {
        return this.func_213590_ei() == Gene.LAZY;
    }

    public boolean isWorried() {
        return this.func_213590_ei() == Gene.WORRIED;
    }

    public boolean isPlayful() {
        return this.func_213590_ei() == Gene.PLAYFUL;
    }

    public boolean isWeak() {
        return this.func_213590_ei() == Gene.WEAK;
    }

    @Override
    public boolean isAggressive() {
        return this.func_213590_ei() == Gene.AGGRESSIVE;
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity playerEntity) {
        return true;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity2) {
        this.playSound(SoundEvents.ENTITY_PANDA_BITE, 1.0f, 1.0f);
        if (!this.isAggressive()) {
            this.didBite = true;
        }
        return super.attackEntityAsMob(entity2);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isWorried()) {
            if (this.world.isThundering() && !this.isInWater()) {
                this.func_213553_r(false);
                this.func_213534_t(true);
            } else if (!this.func_213578_dZ()) {
                this.func_213553_r(true);
            }
        }
        if (this.getAttackTarget() == null) {
            this.gotBamboo = false;
            this.didBite = false;
        }
        if (this.getUnhappyCounter() > 0) {
            if (this.getAttackTarget() != null) {
                this.faceEntity(this.getAttackTarget(), 90.0f, 90.0f);
            }
            if (this.getUnhappyCounter() == 29 || this.getUnhappyCounter() == 14) {
                this.playSound(SoundEvents.ENTITY_PANDA_CANT_BREED, 1.0f, 1.0f);
            }
            this.setUnhappyCounter(this.getUnhappyCounter() - 1);
        }
        if (this.func_213539_dW()) {
            this.setSneezeCounter(this.getSneezeCounter() + 1);
            if (this.getSneezeCounter() > 20) {
                this.func_213581_u(true);
                this.func_213577_ez();
            } else if (this.getSneezeCounter() == 1) {
                this.playSound(SoundEvents.ENTITY_PANDA_PRE_SNEEZE, 1.0f, 1.0f);
            }
        }
        if (this.func_213564_eh()) {
            this.func_213535_ey();
        } else {
            this.rollCounter = 0;
        }
        if (this.func_213556_dX()) {
            this.rotationPitch = 0.0f;
        }
        this.func_213574_ev();
        this.func_213546_et();
        this.func_213563_ew();
        this.func_213550_ex();
    }

    public boolean func_213566_eo() {
        return this.isWorried() && this.world.isThundering();
    }

    private void func_213546_et() {
        if (!this.func_213578_dZ() && this.func_213556_dX() && !this.func_213566_eo() && !this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty() && this.rand.nextInt(80) == 1) {
            this.func_213534_t(false);
        } else if (this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty() || !this.func_213556_dX()) {
            this.func_213534_t(true);
        }
        if (this.func_213578_dZ()) {
            this.func_213533_eu();
            if (!this.world.isRemote && this.getEatCounter() > 80 && this.rand.nextInt(20) == 1) {
                if (this.getEatCounter() > 100 && this.isBreedingItemOrCake(this.getItemStackFromSlot(EquipmentSlotType.MAINHAND))) {
                    if (!this.world.isRemote) {
                        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
                    }
                    this.func_213553_r(true);
                }
                this.func_213534_t(true);
                return;
            }
            this.setEatCounter(this.getEatCounter() + 1);
        }
    }

    private void func_213533_eu() {
        if (this.getEatCounter() % 5 == 0) {
            this.playSound(SoundEvents.ENTITY_PANDA_EAT, 0.5f + 0.5f * (float)this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            for (int i = 0; i < 6; ++i) {
                Vector3d vector3d = new Vector3d(((double)this.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, ((double)this.rand.nextFloat() - 0.5) * 0.1);
                vector3d = vector3d.rotatePitch(-this.rotationPitch * ((float)Math.PI / 180));
                vector3d = vector3d.rotateYaw(-this.rotationYaw * ((float)Math.PI / 180));
                double d = (double)(-this.rand.nextFloat()) * 0.6 - 0.3;
                Vector3d vector3d2 = new Vector3d(((double)this.rand.nextFloat() - 0.5) * 0.8, d, 1.0 + ((double)this.rand.nextFloat() - 0.5) * 0.4);
                vector3d2 = vector3d2.rotateYaw(-this.renderYawOffset * ((float)Math.PI / 180));
                vector3d2 = vector3d2.add(this.getPosX(), this.getPosYEye() + 1.0, this.getPosZ());
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItemStackFromSlot(EquipmentSlotType.MAINHAND)), vector3d2.x, vector3d2.y, vector3d2.z, vector3d.x, vector3d.y + 0.05, vector3d.z);
            }
        }
    }

    private void func_213574_ev() {
        this.sitAmountO = this.sitAmount;
        this.sitAmount = this.func_213556_dX() ? Math.min(1.0f, this.sitAmount + 0.15f) : Math.max(0.0f, this.sitAmount - 0.19f);
    }

    private void func_213563_ew() {
        this.onBackAmountO = this.onBackAmount;
        this.onBackAmount = this.func_213567_dY() ? Math.min(1.0f, this.onBackAmount + 0.15f) : Math.max(0.0f, this.onBackAmount - 0.19f);
    }

    private void func_213550_ex() {
        this.rollAmountO = this.rollAmount;
        this.rollAmount = this.func_213564_eh() ? Math.min(1.0f, this.rollAmount + 0.15f) : Math.max(0.0f, this.rollAmount - 0.19f);
    }

    public float func_213561_v(float f) {
        return MathHelper.lerp(f, this.sitAmountO, this.sitAmount);
    }

    public float func_213583_w(float f) {
        return MathHelper.lerp(f, this.onBackAmountO, this.onBackAmount);
    }

    public float func_213591_x(float f) {
        return MathHelper.lerp(f, this.rollAmountO, this.rollAmount);
    }

    private void func_213535_ey() {
        ++this.rollCounter;
        if (this.rollCounter > 32) {
            this.func_213576_v(true);
        } else if (!this.world.isRemote) {
            Vector3d vector3d = this.getMotion();
            if (this.rollCounter == 1) {
                float f = this.rotationYaw * ((float)Math.PI / 180);
                float f2 = this.isChild() ? 0.1f : 0.2f;
                this.rollDelta = new Vector3d(vector3d.x + (double)(-MathHelper.sin(f) * f2), 0.0, vector3d.z + (double)(MathHelper.cos(f) * f2));
                this.setMotion(this.rollDelta.add(0.0, 0.27, 0.0));
            } else if ((float)this.rollCounter != 7.0f && (float)this.rollCounter != 15.0f && (float)this.rollCounter != 23.0f) {
                this.setMotion(this.rollDelta.x, vector3d.y, this.rollDelta.z);
            } else {
                this.setMotion(0.0, this.onGround ? 0.27 : vector3d.y, 0.0);
            }
        }
    }

    private void func_213577_ez() {
        Vector3d vector3d = this.getMotion();
        this.world.addParticle(ParticleTypes.SNEEZE, this.getPosX() - (double)(this.getWidth() + 1.0f) * 0.5 * (double)MathHelper.sin(this.renderYawOffset * ((float)Math.PI / 180)), this.getPosYEye() - (double)0.1f, this.getPosZ() + (double)(this.getWidth() + 1.0f) * 0.5 * (double)MathHelper.cos(this.renderYawOffset * ((float)Math.PI / 180)), vector3d.x, 0.0, vector3d.z);
        this.playSound(SoundEvents.ENTITY_PANDA_SNEEZE, 1.0f, 1.0f);
        for (PandaEntity pandaEntity : this.world.getEntitiesWithinAABB(PandaEntity.class, this.getBoundingBox().grow(10.0))) {
            if (pandaEntity.isChild() || !pandaEntity.onGround || pandaEntity.isInWater() || !pandaEntity.canPerformAction()) continue;
            pandaEntity.jump();
        }
        if (!this.world.isRemote() && this.rand.nextInt(700) == 0 && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            this.entityDropItem(Items.SLIME_BALL);
        }
    }

    @Override
    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        if (this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty() && PANDA_ITEMS.test(itemEntity)) {
            this.triggerItemPickupTrigger(itemEntity);
            ItemStack itemStack = itemEntity.getItem();
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemStack);
            this.inventoryHandsDropChances[EquipmentSlotType.MAINHAND.getIndex()] = 2.0f;
            this.onItemPickup(itemEntity, itemStack.getCount());
            itemEntity.remove();
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        this.func_213553_r(true);
        return super.attackEntityFrom(damageSource, f);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        this.setMainGene(Gene.getRandomType(this.rand));
        this.setHiddenGene(Gene.getRandomType(this.rand));
        this.setAttributes();
        if (iLivingEntityData == null) {
            iLivingEntityData = new AgeableEntity.AgeableData(0.2f);
        }
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    public void getGenesForChildFromParents(PandaEntity pandaEntity, @Nullable PandaEntity pandaEntity2) {
        if (pandaEntity2 == null) {
            if (this.rand.nextBoolean()) {
                this.setMainGene(pandaEntity.getOneOfGenesRandomly());
                this.setHiddenGene(Gene.getRandomType(this.rand));
            } else {
                this.setMainGene(Gene.getRandomType(this.rand));
                this.setHiddenGene(pandaEntity.getOneOfGenesRandomly());
            }
        } else if (this.rand.nextBoolean()) {
            this.setMainGene(pandaEntity.getOneOfGenesRandomly());
            this.setHiddenGene(pandaEntity2.getOneOfGenesRandomly());
        } else {
            this.setMainGene(pandaEntity2.getOneOfGenesRandomly());
            this.setHiddenGene(pandaEntity.getOneOfGenesRandomly());
        }
        if (this.rand.nextInt(32) == 0) {
            this.setMainGene(Gene.getRandomType(this.rand));
        }
        if (this.rand.nextInt(32) == 0) {
            this.setHiddenGene(Gene.getRandomType(this.rand));
        }
    }

    private Gene getOneOfGenesRandomly() {
        return this.rand.nextBoolean() ? this.getMainGene() : this.getHiddenGene();
    }

    public void setAttributes() {
        if (this.isWeak()) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10.0);
        }
        if (this.isLazy()) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.07f);
        }
    }

    private void tryToSit() {
        if (!this.isInWater()) {
            this.setMoveForward(0.0f);
            this.getNavigator().clearPath();
            this.func_213553_r(false);
        }
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (this.func_213566_eo()) {
            return ActionResultType.PASS;
        }
        if (this.func_213567_dY()) {
            this.func_213542_s(true);
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        if (this.isBreedingItem(itemStack)) {
            if (this.getAttackTarget() != null) {
                this.gotBamboo = true;
            }
            if (this.isChild()) {
                this.consumeItemFromStack(playerEntity, itemStack);
                this.ageUp((int)((float)(-this.getGrowingAge() / 20) * 0.1f), false);
            } else if (!this.world.isRemote && this.getGrowingAge() == 0 && this.canFallInLove()) {
                this.consumeItemFromStack(playerEntity, itemStack);
                this.setInLove(playerEntity);
            } else {
                if (this.world.isRemote || this.func_213556_dX() || this.isInWater()) {
                    return ActionResultType.PASS;
                }
                this.tryToSit();
                this.func_213534_t(false);
                ItemStack itemStack2 = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
                if (!itemStack2.isEmpty() && !playerEntity.abilities.isCreativeMode) {
                    this.entityDropItem(itemStack2);
                }
                this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(itemStack.getItem(), 1));
                this.consumeItemFromStack(playerEntity, itemStack);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        if (this.isAggressive()) {
            return SoundEvents.ENTITY_PANDA_AGGRESSIVE_AMBIENT;
        }
        return this.isWorried() ? SoundEvents.ENTITY_PANDA_WORRIED_AMBIENT : SoundEvents.ENTITY_PANDA_AMBIENT;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_PANDA_STEP, 0.15f, 1.0f);
    }

    @Override
    public boolean isBreedingItem(ItemStack itemStack) {
        return itemStack.getItem() == Blocks.BAMBOO.asItem();
    }

    private boolean isBreedingItemOrCake(ItemStack itemStack) {
        return this.isBreedingItem(itemStack) || itemStack.getItem() == Blocks.CAKE.asItem();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PANDA_DEATH;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_PANDA_HURT;
    }

    public boolean canPerformAction() {
        return !this.func_213567_dY() && !this.func_213566_eo() && !this.func_213578_dZ() && !this.func_213564_eh() && !this.func_213556_dX();
    }

    private static boolean lambda$static$0(ItemEntity itemEntity) {
        Item item = itemEntity.getItem().getItem();
        return (item == Blocks.BAMBOO.asItem() || item == Blocks.CAKE.asItem()) && itemEntity.isAlive() && !itemEntity.cannotPickup();
    }

    static Random access$000(PandaEntity pandaEntity) {
        return pandaEntity.rand;
    }

    static Random access$100(PandaEntity pandaEntity) {
        return pandaEntity.rand;
    }

    static Random access$200(PandaEntity pandaEntity) {
        return pandaEntity.rand;
    }

    static Random access$300(PandaEntity pandaEntity) {
        return pandaEntity.rand;
    }

    static Random access$400(PandaEntity pandaEntity) {
        return pandaEntity.rand;
    }

    static boolean access$500(PandaEntity pandaEntity) {
        return pandaEntity.onGround;
    }

    static Random access$600(PandaEntity pandaEntity) {
        return pandaEntity.rand;
    }

    static Random access$700(PandaEntity pandaEntity) {
        return pandaEntity.rand;
    }

    static Random access$800(PandaEntity pandaEntity) {
        return pandaEntity.rand;
    }

    static Random access$900(PandaEntity pandaEntity) {
        return pandaEntity.rand;
    }

    static Random access$1000(PandaEntity pandaEntity) {
        return pandaEntity.rand;
    }

    static Random access$1100(PandaEntity pandaEntity) {
        return pandaEntity.rand;
    }

    static class MoveHelperController
    extends MovementController {
        private final PandaEntity panda;

        public MoveHelperController(PandaEntity pandaEntity) {
            super(pandaEntity);
            this.panda = pandaEntity;
        }

        @Override
        public void tick() {
            if (this.panda.canPerformAction()) {
                super.tick();
            }
        }
    }

    public static enum Gene {
        NORMAL(0, "normal", false),
        LAZY(1, "lazy", false),
        WORRIED(2, "worried", false),
        PLAYFUL(3, "playful", false),
        BROWN(4, "brown", true),
        WEAK(5, "weak", true),
        AGGRESSIVE(6, "aggressive", false);

        private static final Gene[] field_221109_h;
        private final int index;
        private final String name;
        private final boolean field_221112_k;

        private Gene(int n2, String string2, boolean bl) {
            this.index = n2;
            this.name = string2;
            this.field_221112_k = bl;
        }

        public int getIndex() {
            return this.index;
        }

        public String getName() {
            return this.name;
        }

        public boolean func_221107_c() {
            return this.field_221112_k;
        }

        private static Gene func_221101_b(Gene gene, Gene gene2) {
            if (gene.func_221107_c()) {
                return gene == gene2 ? gene : NORMAL;
            }
            return gene;
        }

        public static Gene byIndex(int n) {
            if (n < 0 || n >= field_221109_h.length) {
                n = 0;
            }
            return field_221109_h[n];
        }

        public static Gene byName(String string) {
            for (Gene gene : Gene.values()) {
                if (!gene.name.equals(string)) continue;
                return gene;
            }
            return NORMAL;
        }

        public static Gene getRandomType(Random random2) {
            int n = random2.nextInt(16);
            if (n == 0) {
                return LAZY;
            }
            if (n == 1) {
                return WORRIED;
            }
            if (n == 2) {
                return PLAYFUL;
            }
            if (n == 4) {
                return AGGRESSIVE;
            }
            if (n < 9) {
                return WEAK;
            }
            return n < 11 ? BROWN : NORMAL;
        }

        private static Gene[] lambda$static$0(int n) {
            return new Gene[n];
        }

        static {
            field_221109_h = (Gene[])Arrays.stream(Gene.values()).sorted(Comparator.comparingInt(Gene::getIndex)).toArray(Gene::lambda$static$0);
        }
    }

    static class PanicGoal
    extends net.minecraft.entity.ai.goal.PanicGoal {
        private final PandaEntity panda;

        public PanicGoal(PandaEntity pandaEntity, double d) {
            super(pandaEntity, d);
            this.panda = pandaEntity;
        }

        @Override
        public boolean shouldExecute() {
            if (!this.panda.isBurning()) {
                return true;
            }
            BlockPos blockPos = this.getRandPos(this.creature.world, this.creature, 5, 4);
            if (blockPos != null) {
                this.randPosX = blockPos.getX();
                this.randPosY = blockPos.getY();
                this.randPosZ = blockPos.getZ();
                return false;
            }
            return this.findRandomPosition();
        }

        @Override
        public boolean shouldContinueExecuting() {
            if (this.panda.func_213556_dX()) {
                this.panda.getNavigator().clearPath();
                return true;
            }
            return super.shouldContinueExecuting();
        }
    }

    class MateGoal
    extends BreedGoal {
        private final PandaEntity panda;
        private int field_220694_f;
        final PandaEntity this$0;

        public MateGoal(PandaEntity pandaEntity, PandaEntity pandaEntity2, double d) {
            this.this$0 = pandaEntity;
            super(pandaEntity2, d);
            this.panda = pandaEntity2;
        }

        @Override
        public boolean shouldExecute() {
            if (super.shouldExecute() && this.panda.getUnhappyCounter() == 0) {
                if (!this.func_220691_h()) {
                    if (this.field_220694_f <= this.panda.ticksExisted) {
                        this.panda.setUnhappyCounter(32);
                        this.field_220694_f = this.panda.ticksExisted + 600;
                        if (this.panda.isServerWorld()) {
                            PlayerEntity playerEntity = this.world.getClosestPlayer(BREED_TARGETING, this.panda);
                            this.panda.watchGoal.func_229975_a_(playerEntity);
                        }
                    }
                    return true;
                }
                return false;
            }
            return true;
        }

        private boolean func_220691_h() {
            BlockPos blockPos = this.panda.getPosition();
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 8; ++j) {
                    int n = 0;
                    while (n <= j) {
                        int n2;
                        int n3 = n2 = n < j && n > -j ? j : 0;
                        while (n2 <= j) {
                            mutable.setAndOffset(blockPos, n, i, n2);
                            if (this.world.getBlockState(mutable).isIn(Blocks.BAMBOO)) {
                                return false;
                            }
                            n2 = n2 > 0 ? -n2 : 1 - n2;
                        }
                        n = n > 0 ? -n : 1 - n;
                    }
                }
            }
            return true;
        }
    }

    static class AttackGoal
    extends MeleeAttackGoal {
        private final PandaEntity panda;

        public AttackGoal(PandaEntity pandaEntity, double d, boolean bl) {
            super(pandaEntity, d, bl);
            this.panda = pandaEntity;
        }

        @Override
        public boolean shouldExecute() {
            return this.panda.canPerformAction() && super.shouldExecute();
        }
    }

    static class AvoidGoal<T extends LivingEntity>
    extends AvoidEntityGoal<T> {
        private final PandaEntity panda;

        public AvoidGoal(PandaEntity pandaEntity, Class<T> clazz, float f, double d, double d2) {
            super(pandaEntity, clazz, f, d, d2, EntityPredicates.NOT_SPECTATING::test);
            this.panda = pandaEntity;
        }

        @Override
        public boolean shouldExecute() {
            return this.panda.isWorried() && this.panda.canPerformAction() && super.shouldExecute();
        }
    }

    class SitGoal
    extends Goal {
        private int field_220832_b;
        final PandaEntity this$0;

        public SitGoal(PandaEntity pandaEntity) {
            this.this$0 = pandaEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            if (this.field_220832_b <= this.this$0.ticksExisted && !this.this$0.isChild() && !this.this$0.isInWater() && this.this$0.canPerformAction() && this.this$0.getUnhappyCounter() <= 0) {
                List<ItemEntity> list = this.this$0.world.getEntitiesWithinAABB(ItemEntity.class, this.this$0.getBoundingBox().grow(6.0, 6.0, 6.0), PANDA_ITEMS);
                return !list.isEmpty() || !this.this$0.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty();
            }
            return true;
        }

        @Override
        public boolean shouldContinueExecuting() {
            if (!this.this$0.isInWater() && (this.this$0.isLazy() || PandaEntity.access$800(this.this$0).nextInt(600) != 1)) {
                return PandaEntity.access$900(this.this$0).nextInt(2000) != 1;
            }
            return true;
        }

        @Override
        public void tick() {
            if (!this.this$0.func_213556_dX() && !this.this$0.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty()) {
                this.this$0.tryToSit();
            }
        }

        @Override
        public void startExecuting() {
            List<ItemEntity> list = this.this$0.world.getEntitiesWithinAABB(ItemEntity.class, this.this$0.getBoundingBox().grow(8.0, 8.0, 8.0), PANDA_ITEMS);
            if (!list.isEmpty() && this.this$0.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty()) {
                this.this$0.getNavigator().tryMoveToEntityLiving(list.get(0), 1.2f);
            } else if (!this.this$0.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty()) {
                this.this$0.tryToSit();
            }
            this.field_220832_b = 0;
        }

        @Override
        public void resetTask() {
            ItemStack itemStack = this.this$0.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
            if (!itemStack.isEmpty()) {
                this.this$0.entityDropItem(itemStack);
                this.this$0.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
                int n = this.this$0.isLazy() ? PandaEntity.access$1000(this.this$0).nextInt(50) + 10 : PandaEntity.access$1100(this.this$0).nextInt(150) + 10;
                this.field_220832_b = this.this$0.ticksExisted + n * 20;
            }
            this.this$0.func_213553_r(true);
        }
    }

    static class LieBackGoal
    extends Goal {
        private final PandaEntity panda;
        private int field_220829_b;

        public LieBackGoal(PandaEntity pandaEntity) {
            this.panda = pandaEntity;
        }

        @Override
        public boolean shouldExecute() {
            return this.field_220829_b < this.panda.ticksExisted && this.panda.isLazy() && this.panda.canPerformAction() && PandaEntity.access$200(this.panda).nextInt(400) == 1;
        }

        @Override
        public boolean shouldContinueExecuting() {
            if (!this.panda.isInWater() && (this.panda.isLazy() || PandaEntity.access$300(this.panda).nextInt(600) != 1)) {
                return PandaEntity.access$400(this.panda).nextInt(2000) != 1;
            }
            return true;
        }

        @Override
        public void startExecuting() {
            this.panda.func_213542_s(false);
            this.field_220829_b = 0;
        }

        @Override
        public void resetTask() {
            this.panda.func_213542_s(true);
            this.field_220829_b = this.panda.ticksExisted + 200;
        }
    }

    static class ChildPlayGoal
    extends Goal {
        private final PandaEntity panda;

        public ChildPlayGoal(PandaEntity pandaEntity) {
            this.panda = pandaEntity;
        }

        @Override
        public boolean shouldExecute() {
            if (this.panda.isChild() && this.panda.canPerformAction()) {
                if (this.panda.isWeak() && PandaEntity.access$000(this.panda).nextInt(500) == 1) {
                    return false;
                }
                return PandaEntity.access$100(this.panda).nextInt(6000) == 1;
            }
            return true;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return true;
        }

        @Override
        public void startExecuting() {
            this.panda.func_213581_u(false);
        }
    }

    static class WatchGoal
    extends LookAtGoal {
        private final PandaEntity field_220718_f;

        public WatchGoal(PandaEntity pandaEntity, Class<? extends LivingEntity> clazz, float f) {
            super(pandaEntity, clazz, f);
            this.field_220718_f = pandaEntity;
        }

        public void func_229975_a_(LivingEntity livingEntity) {
            this.closestEntity = livingEntity;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.closestEntity != null && super.shouldContinueExecuting();
        }

        @Override
        public boolean shouldExecute() {
            if (this.entity.getRNG().nextFloat() >= this.chance) {
                return true;
            }
            if (this.closestEntity == null) {
                this.closestEntity = this.watchedClass == PlayerEntity.class ? this.entity.world.getClosestPlayer(this.field_220716_e, this.entity, this.entity.getPosX(), this.entity.getPosYEye(), this.entity.getPosZ()) : this.entity.world.func_225318_b(this.watchedClass, this.field_220716_e, this.entity, this.entity.getPosX(), this.entity.getPosYEye(), this.entity.getPosZ(), this.entity.getBoundingBox().grow(this.maxDistance, 3.0, this.maxDistance));
            }
            return this.field_220718_f.canPerformAction() && this.closestEntity != null;
        }

        @Override
        public void tick() {
            if (this.closestEntity != null) {
                super.tick();
            }
        }
    }

    static class RollGoal
    extends Goal {
        private final PandaEntity panda;

        public RollGoal(PandaEntity pandaEntity) {
            this.panda = pandaEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }

        @Override
        public boolean shouldExecute() {
            if ((this.panda.isChild() || this.panda.isPlayful()) && PandaEntity.access$500(this.panda)) {
                if (!this.panda.canPerformAction()) {
                    return true;
                }
                float f = this.panda.rotationYaw * ((float)Math.PI / 180);
                int n = 0;
                int n2 = 0;
                float f2 = -MathHelper.sin(f);
                float f3 = MathHelper.cos(f);
                if ((double)Math.abs(f2) > 0.5) {
                    n = (int)((float)n + f2 / Math.abs(f2));
                }
                if ((double)Math.abs(f3) > 0.5) {
                    n2 = (int)((float)n2 + f3 / Math.abs(f3));
                }
                if (this.panda.world.getBlockState(this.panda.getPosition().add(n, -1, n2)).isAir()) {
                    return false;
                }
                if (this.panda.isPlayful() && PandaEntity.access$600(this.panda).nextInt(60) == 1) {
                    return false;
                }
                return PandaEntity.access$700(this.panda).nextInt(500) == 1;
            }
            return true;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return true;
        }

        @Override
        public void startExecuting() {
            this.panda.func_213576_v(false);
        }

        @Override
        public boolean isPreemptible() {
            return true;
        }
    }

    static class RevengeGoal
    extends HurtByTargetGoal {
        private final PandaEntity panda;

        public RevengeGoal(PandaEntity pandaEntity, Class<?> ... classArray) {
            super(pandaEntity, classArray);
            this.panda = pandaEntity;
        }

        @Override
        public boolean shouldContinueExecuting() {
            if (!this.panda.gotBamboo && !this.panda.didBite) {
                return super.shouldContinueExecuting();
            }
            this.panda.setAttackTarget(null);
            return true;
        }

        @Override
        protected void setAttackTarget(MobEntity mobEntity, LivingEntity livingEntity) {
            if (mobEntity instanceof PandaEntity && ((PandaEntity)mobEntity).isAggressive()) {
                mobEntity.setAttackTarget(livingEntity);
            }
        }
    }
}

