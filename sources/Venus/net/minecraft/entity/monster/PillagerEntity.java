/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.raid.Raid;

public class PillagerEntity
extends AbstractIllagerEntity
implements ICrossbowUser {
    private static final DataParameter<Boolean> DATA_CHARGING_STATE = EntityDataManager.createKey(PillagerEntity.class, DataSerializers.BOOLEAN);
    private final Inventory inventory = new Inventory(5);

    public PillagerEntity(EntityType<? extends PillagerEntity> entityType, World world) {
        super((EntityType<? extends AbstractIllagerEntity>)entityType, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new AbstractRaiderEntity.FindTargetGoal(this, this, 10.0f));
        this.goalSelector.addGoal(3, new RangedCrossbowAttackGoal<PillagerEntity>(this, 1.0, 8.0f));
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 15.0f, 1.0f));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 15.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, AbstractRaiderEntity.class).setCallsForHelp(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<AbstractVillagerEntity>((MobEntity)this, AbstractVillagerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolemEntity>((MobEntity)this, IronGolemEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute func_234296_eI_() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35f).createMutableAttribute(Attributes.MAX_HEALTH, 24.0).createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0).createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_CHARGING_STATE, false);
    }

    @Override
    public boolean func_230280_a_(ShootableItem shootableItem) {
        return shootableItem == Items.CROSSBOW;
    }

    public boolean isCharging() {
        return this.dataManager.get(DATA_CHARGING_STATE);
    }

    @Override
    public void setCharging(boolean bl) {
        this.dataManager.set(DATA_CHARGING_STATE, bl);
    }

    @Override
    public void func_230283_U__() {
        this.idleTime = 0;
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        ListNBT listNBT = new ListNBT();
        for (int i = 0; i < this.inventory.getSizeInventory(); ++i) {
            ItemStack itemStack = this.inventory.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            listNBT.add(itemStack.write(new CompoundNBT()));
        }
        compoundNBT.put("Inventory", listNBT);
    }

    @Override
    public AbstractIllagerEntity.ArmPose getArmPose() {
        if (this.isCharging()) {
            return AbstractIllagerEntity.ArmPose.CROSSBOW_CHARGE;
        }
        if (this.canEquip(Items.CROSSBOW)) {
            return AbstractIllagerEntity.ArmPose.CROSSBOW_HOLD;
        }
        return this.isAggressive() ? AbstractIllagerEntity.ArmPose.ATTACKING : AbstractIllagerEntity.ArmPose.NEUTRAL;
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        ListNBT listNBT = compoundNBT.getList("Inventory", 10);
        for (int i = 0; i < listNBT.size(); ++i) {
            ItemStack itemStack = ItemStack.read(listNBT.getCompound(i));
            if (itemStack.isEmpty()) continue;
            this.inventory.addItem(itemStack);
        }
        this.setCanPickUpLoot(false);
    }

    @Override
    public float getBlockPathWeight(BlockPos blockPos, IWorldReader iWorldReader) {
        BlockState blockState = iWorldReader.getBlockState(blockPos.down());
        return !blockState.isIn(Blocks.GRASS_BLOCK) && !blockState.isIn(Blocks.SAND) ? 0.5f - iWorldReader.getBrightness(blockPos) : 10.0f;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 0;
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        this.setEquipmentBasedOnDifficulty(difficultyInstance);
        this.setEnchantmentBasedOnDifficulty(difficultyInstance);
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.CROSSBOW));
    }

    @Override
    protected void func_241844_w(float f) {
        ItemStack itemStack;
        super.func_241844_w(f);
        if (this.rand.nextInt(300) == 0 && (itemStack = this.getHeldItemMainhand()).getItem() == Items.CROSSBOW) {
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);
            map.putIfAbsent(Enchantments.PIERCING, 1);
            EnchantmentHelper.setEnchantments(map, itemStack);
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemStack);
        }
    }

    @Override
    public boolean isOnSameTeam(Entity entity2) {
        if (super.isOnSameTeam(entity2)) {
            return false;
        }
        if (entity2 instanceof LivingEntity && ((LivingEntity)entity2).getCreatureAttribute() == CreatureAttribute.ILLAGER) {
            return this.getTeam() == null && entity2.getTeam() == null;
        }
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PILLAGER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_PILLAGER_HURT;
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
    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getItem();
        if (itemStack.getItem() instanceof BannerItem) {
            super.updateEquipmentIfNeeded(itemEntity);
        } else {
            Item item = itemStack.getItem();
            if (this.func_213672_b(item)) {
                this.triggerItemPickupTrigger(itemEntity);
                ItemStack itemStack2 = this.inventory.addItem(itemStack);
                if (itemStack2.isEmpty()) {
                    itemEntity.remove();
                } else {
                    itemStack.setCount(itemStack2.getCount());
                }
            }
        }
    }

    private boolean func_213672_b(Item item) {
        return this.isRaidActive() && item == Items.WHITE_BANNER;
    }

    @Override
    public boolean replaceItemInInventory(int n, ItemStack itemStack) {
        if (super.replaceItemInInventory(n, itemStack)) {
            return false;
        }
        int n2 = n - 300;
        if (n2 >= 0 && n2 < this.inventory.getSizeInventory()) {
            this.inventory.setInventorySlotContents(n2, itemStack);
            return false;
        }
        return true;
    }

    @Override
    public void applyWaveBonus(int n, boolean bl) {
        boolean bl2;
        Raid raid = this.getRaid();
        boolean bl3 = bl2 = this.rand.nextFloat() <= raid.getEnchantOdds();
        if (bl2) {
            ItemStack itemStack = new ItemStack(Items.CROSSBOW);
            HashMap<Enchantment, Integer> hashMap = Maps.newHashMap();
            if (n > raid.getWaves(Difficulty.NORMAL)) {
                hashMap.put(Enchantments.QUICK_CHARGE, 2);
            } else if (n > raid.getWaves(Difficulty.EASY)) {
                hashMap.put(Enchantments.QUICK_CHARGE, 1);
            }
            hashMap.put(Enchantments.MULTISHOT, 1);
            EnchantmentHelper.setEnchantments(hashMap, itemStack);
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemStack);
        }
    }

    @Override
    public SoundEvent getRaidLossSound() {
        return SoundEvents.ENTITY_PILLAGER_CELEBRATE;
    }
}

