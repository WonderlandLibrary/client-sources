/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.merchant.villager;

import com.google.common.collect.Sets;
import java.util.HashSet;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.INPC;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class AbstractVillagerEntity
extends AgeableEntity
implements INPC,
IMerchant {
    private static final DataParameter<Integer> SHAKE_HEAD_TICKS = EntityDataManager.createKey(AbstractVillagerEntity.class, DataSerializers.VARINT);
    @Nullable
    private PlayerEntity customer;
    @Nullable
    protected MerchantOffers offers;
    private final Inventory villagerInventory = new Inventory(8);

    public AbstractVillagerEntity(EntityType<? extends AbstractVillagerEntity> entityType, World world) {
        super((EntityType<? extends AgeableEntity>)entityType, world);
        this.setPathPriority(PathNodeType.DANGER_FIRE, 16.0f);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, -1.0f);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        if (iLivingEntityData == null) {
            iLivingEntityData = new AgeableEntity.AgeableData(false);
        }
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    public int getShakeHeadTicks() {
        return this.dataManager.get(SHAKE_HEAD_TICKS);
    }

    public void setShakeHeadTicks(int n) {
        this.dataManager.set(SHAKE_HEAD_TICKS, n);
    }

    @Override
    public int getXp() {
        return 1;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return this.isChild() ? 0.81f : 1.62f;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHAKE_HEAD_TICKS, 0);
    }

    @Override
    public void setCustomer(@Nullable PlayerEntity playerEntity) {
        this.customer = playerEntity;
    }

    @Override
    @Nullable
    public PlayerEntity getCustomer() {
        return this.customer;
    }

    public boolean hasCustomer() {
        return this.customer != null;
    }

    @Override
    public MerchantOffers getOffers() {
        if (this.offers == null) {
            this.offers = new MerchantOffers();
            this.populateTradeData();
        }
        return this.offers;
    }

    @Override
    public void setClientSideOffers(@Nullable MerchantOffers merchantOffers) {
    }

    @Override
    public void setXP(int n) {
    }

    @Override
    public void onTrade(MerchantOffer merchantOffer) {
        merchantOffer.increaseUses();
        this.livingSoundTime = -this.getTalkInterval();
        this.onVillagerTrade(merchantOffer);
        if (this.customer instanceof ServerPlayerEntity) {
            CriteriaTriggers.VILLAGER_TRADE.test((ServerPlayerEntity)this.customer, this, merchantOffer.getSellingStack());
        }
    }

    protected abstract void onVillagerTrade(MerchantOffer var1);

    @Override
    public boolean hasXPBar() {
        return false;
    }

    @Override
    public void verifySellingItem(ItemStack itemStack) {
        if (!this.world.isRemote && this.livingSoundTime > -this.getTalkInterval() + 20) {
            this.livingSoundTime = -this.getTalkInterval();
            this.playSound(this.getVillagerYesNoSound(!itemStack.isEmpty()), this.getSoundVolume(), this.getSoundPitch());
        }
    }

    @Override
    public SoundEvent getYesSound() {
        return SoundEvents.ENTITY_VILLAGER_YES;
    }

    protected SoundEvent getVillagerYesNoSound(boolean bl) {
        return bl ? SoundEvents.ENTITY_VILLAGER_YES : SoundEvents.ENTITY_VILLAGER_NO;
    }

    public void playCelebrateSound() {
        this.playSound(SoundEvents.ENTITY_VILLAGER_CELEBRATE, this.getSoundVolume(), this.getSoundPitch());
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        MerchantOffers merchantOffers = this.getOffers();
        if (!merchantOffers.isEmpty()) {
            compoundNBT.put("Offers", merchantOffers.write());
        }
        compoundNBT.put("Inventory", this.villagerInventory.write());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("Offers", 1)) {
            this.offers = new MerchantOffers(compoundNBT.getCompound("Offers"));
        }
        this.villagerInventory.read(compoundNBT.getList("Inventory", 10));
    }

    @Override
    @Nullable
    public Entity changeDimension(ServerWorld serverWorld) {
        this.resetCustomer();
        return super.changeDimension(serverWorld);
    }

    protected void resetCustomer() {
        this.setCustomer(null);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        this.resetCustomer();
    }

    protected void spawnParticles(IParticleData iParticleData) {
        for (int i = 0; i < 5; ++i) {
            double d = this.rand.nextGaussian() * 0.02;
            double d2 = this.rand.nextGaussian() * 0.02;
            double d3 = this.rand.nextGaussian() * 0.02;
            this.world.addParticle(iParticleData, this.getPosXRandom(1.0), this.getPosYRandom() + 1.0, this.getPosZRandom(1.0), d, d2, d3);
        }
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity playerEntity) {
        return true;
    }

    public Inventory getVillagerInventory() {
        return this.villagerInventory;
    }

    @Override
    public boolean replaceItemInInventory(int n, ItemStack itemStack) {
        if (super.replaceItemInInventory(n, itemStack)) {
            return false;
        }
        int n2 = n - 300;
        if (n2 >= 0 && n2 < this.villagerInventory.getSizeInventory()) {
            this.villagerInventory.setInventorySlotContents(n2, itemStack);
            return false;
        }
        return true;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    protected abstract void populateTradeData();

    protected void addTrades(MerchantOffers merchantOffers, VillagerTrades.ITrade[] iTradeArray, int n) {
        HashSet<Integer> hashSet = Sets.newHashSet();
        if (iTradeArray.length > n) {
            while (hashSet.size() < n) {
                hashSet.add(this.rand.nextInt(iTradeArray.length));
            }
        } else {
            for (int i = 0; i < iTradeArray.length; ++i) {
                hashSet.add(i);
            }
        }
        for (Integer n2 : hashSet) {
            VillagerTrades.ITrade iTrade = iTradeArray[n2];
            MerchantOffer merchantOffer = iTrade.getOffer(this, this.rand);
            if (merchantOffer == null) continue;
            merchantOffers.add(merchantOffer);
        }
    }

    @Override
    public Vector3d getLeashPosition(float f) {
        float f2 = MathHelper.lerp(f, this.prevRenderYawOffset, this.renderYawOffset) * ((float)Math.PI / 180);
        Vector3d vector3d = new Vector3d(0.0, this.getBoundingBox().getYSize() - 1.0, 0.2);
        return this.func_242282_l(f).add(vector3d.rotateYaw(-f2));
    }
}

