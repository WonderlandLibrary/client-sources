/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.merchant.IReputationType;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.villager.IVillagerDataHolder;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ZombieVillagerEntity
extends ZombieEntity
implements IVillagerDataHolder {
    private static final DataParameter<Boolean> CONVERTING = EntityDataManager.createKey(ZombieVillagerEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<VillagerData> VILLAGER_DATA = EntityDataManager.createKey(ZombieVillagerEntity.class, DataSerializers.VILLAGER_DATA);
    private int conversionTime;
    private UUID converstionStarter;
    private INBT gossips;
    private CompoundNBT offers;
    private int xp;

    public ZombieVillagerEntity(EntityType<? extends ZombieVillagerEntity> entityType, World world) {
        super((EntityType<? extends ZombieEntity>)entityType, world);
        this.setVillagerData(this.getVillagerData().withProfession(Registry.VILLAGER_PROFESSION.getRandom(this.rand)));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(CONVERTING, false);
        this.dataManager.register(VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        VillagerData.CODEC.encodeStart(NBTDynamicOps.INSTANCE, this.getVillagerData()).resultOrPartial(LOGGER::error).ifPresent(arg_0 -> ZombieVillagerEntity.lambda$writeAdditional$0(compoundNBT, arg_0));
        if (this.offers != null) {
            compoundNBT.put("Offers", this.offers);
        }
        if (this.gossips != null) {
            compoundNBT.put("Gossips", this.gossips);
        }
        compoundNBT.putInt("ConversionTime", this.isConverting() ? this.conversionTime : -1);
        if (this.converstionStarter != null) {
            compoundNBT.putUniqueId("ConversionPlayer", this.converstionStarter);
        }
        compoundNBT.putInt("Xp", this.xp);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("VillagerData", 1)) {
            DataResult dataResult = VillagerData.CODEC.parse(new Dynamic<INBT>(NBTDynamicOps.INSTANCE, compoundNBT.get("VillagerData")));
            dataResult.resultOrPartial(LOGGER::error).ifPresent(this::setVillagerData);
        }
        if (compoundNBT.contains("Offers", 1)) {
            this.offers = compoundNBT.getCompound("Offers");
        }
        if (compoundNBT.contains("Gossips", 1)) {
            this.gossips = compoundNBT.getList("Gossips", 10);
        }
        if (compoundNBT.contains("ConversionTime", 0) && compoundNBT.getInt("ConversionTime") > -1) {
            this.startConverting(compoundNBT.hasUniqueId("ConversionPlayer") ? compoundNBT.getUniqueId("ConversionPlayer") : null, compoundNBT.getInt("ConversionTime"));
        }
        if (compoundNBT.contains("Xp", 0)) {
            this.xp = compoundNBT.getInt("Xp");
        }
    }

    @Override
    public void tick() {
        if (!this.world.isRemote && this.isAlive() && this.isConverting()) {
            int n = this.getConversionProgress();
            this.conversionTime -= n;
            if (this.conversionTime <= 0) {
                this.cureZombie((ServerWorld)this.world);
            }
        }
        super.tick();
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (itemStack.getItem() == Items.GOLDEN_APPLE) {
            if (this.isPotionActive(Effects.WEAKNESS)) {
                if (!playerEntity.abilities.isCreativeMode) {
                    itemStack.shrink(1);
                }
                if (!this.world.isRemote) {
                    this.startConverting(playerEntity.getUniqueID(), this.rand.nextInt(2401) + 3600);
                }
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.CONSUME;
        }
        return super.func_230254_b_(playerEntity, hand);
    }

    @Override
    protected boolean shouldDrown() {
        return true;
    }

    @Override
    public boolean canDespawn(double d) {
        return !this.isConverting() && this.xp == 0;
    }

    public boolean isConverting() {
        return this.getDataManager().get(CONVERTING);
    }

    private void startConverting(@Nullable UUID uUID, int n) {
        this.converstionStarter = uUID;
        this.conversionTime = n;
        this.getDataManager().set(CONVERTING, true);
        this.removePotionEffect(Effects.WEAKNESS);
        this.addPotionEffect(new EffectInstance(Effects.STRENGTH, n, Math.min(this.world.getDifficulty().getId() - 1, 0)));
        this.world.setEntityState(this, (byte)16);
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 16) {
            if (!this.isSilent()) {
                this.world.playSound(this.getPosX(), this.getPosYEye(), this.getPosZ(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, this.getSoundCategory(), 1.0f + this.rand.nextFloat(), this.rand.nextFloat() * 0.7f + 0.3f, true);
            }
        } else {
            super.handleStatusUpdate(by);
        }
    }

    private void cureZombie(ServerWorld serverWorld) {
        VillagerEntity villagerEntity = this.func_233656_b_(EntityType.VILLAGER, true);
        Object object = EquipmentSlotType.values();
        int n = ((EquipmentSlotType[])object).length;
        for (int i = 0; i < n; ++i) {
            EquipmentSlotType equipmentSlotType = object[i];
            ItemStack itemStack = this.getItemStackFromSlot(equipmentSlotType);
            if (itemStack.isEmpty()) continue;
            if (EnchantmentHelper.hasBindingCurse(itemStack)) {
                villagerEntity.replaceItemInInventory(equipmentSlotType.getIndex() + 300, itemStack);
                continue;
            }
            double d = this.getDropChance(equipmentSlotType);
            if (!(d > 1.0)) continue;
            this.entityDropItem(itemStack);
        }
        villagerEntity.setVillagerData(this.getVillagerData());
        if (this.gossips != null) {
            villagerEntity.setGossips(this.gossips);
        }
        if (this.offers != null) {
            villagerEntity.setOffers(new MerchantOffers(this.offers));
        }
        villagerEntity.setXp(this.xp);
        villagerEntity.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(villagerEntity.getPosition()), SpawnReason.CONVERSION, null, null);
        if (this.converstionStarter != null && (object = serverWorld.getPlayerByUuid(this.converstionStarter)) instanceof ServerPlayerEntity) {
            CriteriaTriggers.CURED_ZOMBIE_VILLAGER.trigger((ServerPlayerEntity)object, this, villagerEntity);
            serverWorld.updateReputation(IReputationType.ZOMBIE_VILLAGER_CURED, (Entity)object, villagerEntity);
        }
        villagerEntity.addPotionEffect(new EffectInstance(Effects.NAUSEA, 200, 0));
        if (!this.isSilent()) {
            serverWorld.playEvent(null, 1027, this.getPosition(), 0);
        }
    }

    private int getConversionProgress() {
        int n = 1;
        if (this.rand.nextFloat() < 0.01f) {
            int n2 = 0;
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (int i = (int)this.getPosX() - 4; i < (int)this.getPosX() + 4 && n2 < 14; ++i) {
                for (int j = (int)this.getPosY() - 4; j < (int)this.getPosY() + 4 && n2 < 14; ++j) {
                    for (int k = (int)this.getPosZ() - 4; k < (int)this.getPosZ() + 4 && n2 < 14; ++k) {
                        Block block = this.world.getBlockState(mutable.setPos(i, j, k)).getBlock();
                        if (block != Blocks.IRON_BARS && !(block instanceof BedBlock)) continue;
                        if (this.rand.nextFloat() < 0.3f) {
                            ++n;
                        }
                        ++n2;
                    }
                }
            }
        }
        return n;
    }

    @Override
    protected float getSoundPitch() {
        return this.isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 2.0f : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ZOMBIE_VILLAGER_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_ZOMBIE_VILLAGER_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ZOMBIE_VILLAGER_DEATH;
    }

    @Override
    public SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_VILLAGER_STEP;
    }

    @Override
    protected ItemStack getSkullDrop() {
        return ItemStack.EMPTY;
    }

    public void setOffers(CompoundNBT compoundNBT) {
        this.offers = compoundNBT;
    }

    public void setGossips(INBT iNBT) {
        this.gossips = iNBT;
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        this.setVillagerData(this.getVillagerData().withType(VillagerType.func_242371_a(iServerWorld.func_242406_i(this.getPosition()))));
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    public void setVillagerData(VillagerData villagerData) {
        VillagerData villagerData2 = this.getVillagerData();
        if (villagerData2.getProfession() != villagerData.getProfession()) {
            this.offers = null;
        }
        this.dataManager.set(VILLAGER_DATA, villagerData);
    }

    @Override
    public VillagerData getVillagerData() {
        return this.dataManager.get(VILLAGER_DATA);
    }

    public void setEXP(int n) {
        this.xp = n;
    }

    private static void lambda$writeAdditional$0(CompoundNBT compoundNBT, INBT iNBT) {
        compoundNBT.put("VillagerData", iNBT);
    }
}

