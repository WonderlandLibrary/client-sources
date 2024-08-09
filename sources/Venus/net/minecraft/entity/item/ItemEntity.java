/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item;

import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ItemEntity
extends Entity {
    private static final DataParameter<ItemStack> ITEM = EntityDataManager.createKey(ItemEntity.class, DataSerializers.ITEMSTACK);
    private int age;
    private int pickupDelay;
    private int health = 5;
    private UUID thrower;
    private UUID owner;
    public final float hoverStart;

    public ItemEntity(EntityType<? extends ItemEntity> entityType, World world) {
        super(entityType, world);
        this.hoverStart = (float)(Math.random() * Math.PI * 2.0);
    }

    public ItemEntity(World world, double d, double d2, double d3) {
        this((EntityType<? extends ItemEntity>)EntityType.ITEM, world);
        this.setPosition(d, d2, d3);
        this.rotationYaw = this.rand.nextFloat() * 360.0f;
        this.setMotion(this.rand.nextDouble() * 0.2 - 0.1, 0.2, this.rand.nextDouble() * 0.2 - 0.1);
    }

    public ItemEntity(World world, double d, double d2, double d3, ItemStack itemStack) {
        this(world, d, d2, d3);
        this.setItem(itemStack);
    }

    private ItemEntity(ItemEntity itemEntity) {
        super(itemEntity.getType(), itemEntity.world);
        this.setItem(itemEntity.getItem().copy());
        this.copyLocationAndAnglesFrom(itemEntity);
        this.age = itemEntity.age;
        this.hoverStart = itemEntity.hoverStart;
    }

    @Override
    protected boolean canTriggerWalking() {
        return true;
    }

    @Override
    protected void registerData() {
        this.getDataManager().register(ITEM, ItemStack.EMPTY);
    }

    @Override
    public void tick() {
        if (this.getItem().isEmpty()) {
            this.remove();
        } else {
            double d;
            int n;
            super.tick();
            if (this.pickupDelay > 0 && this.pickupDelay != Short.MAX_VALUE) {
                --this.pickupDelay;
            }
            this.prevPosX = this.getPosX();
            this.prevPosY = this.getPosY();
            this.prevPosZ = this.getPosZ();
            Vector3d vector3d = this.getMotion();
            float f = this.getEyeHeight() - 0.11111111f;
            if (this.isInWater() && this.func_233571_b_(FluidTags.WATER) > (double)f) {
                this.applyFloatMotion();
            } else if (this.isInLava() && this.func_233571_b_(FluidTags.LAVA) > (double)f) {
                this.func_234274_v_();
            } else if (!this.hasNoGravity()) {
                this.setMotion(this.getMotion().add(0.0, -0.04, 0.0));
            }
            if (this.world.isRemote) {
                this.noClip = false;
            } else {
                boolean bl = this.noClip = !this.world.hasNoCollisions(this);
                if (this.noClip) {
                    this.pushOutOfBlocks(this.getPosX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0, this.getPosZ());
                }
            }
            if (!this.onGround || ItemEntity.horizontalMag(this.getMotion()) > (double)1.0E-5f || (this.ticksExisted + this.getEntityId()) % 4 == 0) {
                this.move(MoverType.SELF, this.getMotion());
                float f2 = 0.98f;
                if (this.onGround) {
                    f2 = this.world.getBlockState(new BlockPos(this.getPosX(), this.getPosY() - 1.0, this.getPosZ())).getBlock().getSlipperiness() * 0.98f;
                }
                this.setMotion(this.getMotion().mul(f2, 0.98, f2));
                if (this.onGround) {
                    Vector3d vector3d2 = this.getMotion();
                    if (vector3d2.y < 0.0) {
                        this.setMotion(vector3d2.mul(1.0, -0.5, 1.0));
                    }
                }
            }
            boolean bl = MathHelper.floor(this.prevPosX) != MathHelper.floor(this.getPosX()) || MathHelper.floor(this.prevPosY) != MathHelper.floor(this.getPosY()) || MathHelper.floor(this.prevPosZ) != MathHelper.floor(this.getPosZ());
            int n2 = n = bl ? 2 : 40;
            if (this.ticksExisted % n == 0) {
                if (this.world.getFluidState(this.getPosition()).isTagged(FluidTags.LAVA) && !this.isImmuneToFire()) {
                    this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4f, 2.0f + this.rand.nextFloat() * 0.4f);
                }
                if (!this.world.isRemote && this.func_213857_z()) {
                    this.searchForOtherItemsNearby();
                }
            }
            if (this.age != Short.MIN_VALUE) {
                ++this.age;
            }
            this.isAirBorne |= this.func_233566_aG_();
            if (!this.world.isRemote && (d = this.getMotion().subtract(vector3d).lengthSquared()) > 0.01) {
                this.isAirBorne = true;
            }
            if (!this.world.isRemote && this.age >= 6000) {
                this.remove();
            }
        }
    }

    private void applyFloatMotion() {
        Vector3d vector3d = this.getMotion();
        this.setMotion(vector3d.x * (double)0.99f, vector3d.y + (double)(vector3d.y < (double)0.06f ? 5.0E-4f : 0.0f), vector3d.z * (double)0.99f);
    }

    private void func_234274_v_() {
        Vector3d vector3d = this.getMotion();
        this.setMotion(vector3d.x * (double)0.95f, vector3d.y + (double)(vector3d.y < (double)0.06f ? 5.0E-4f : 0.0f), vector3d.z * (double)0.95f);
    }

    private void searchForOtherItemsNearby() {
        if (this.func_213857_z()) {
            for (ItemEntity itemEntity : this.world.getEntitiesWithinAABB(ItemEntity.class, this.getBoundingBox().grow(0.5, 0.0, 0.5), this::lambda$searchForOtherItemsNearby$0)) {
                if (!itemEntity.func_213857_z()) continue;
                this.func_226530_a_(itemEntity);
                if (!this.removed) continue;
                break;
            }
        }
    }

    private boolean func_213857_z() {
        ItemStack itemStack = this.getItem();
        return this.isAlive() && this.pickupDelay != Short.MAX_VALUE && this.age != Short.MIN_VALUE && this.age < 6000 && itemStack.getCount() < itemStack.getMaxStackSize();
    }

    private void func_226530_a_(ItemEntity itemEntity) {
        ItemStack itemStack = this.getItem();
        ItemStack itemStack2 = itemEntity.getItem();
        if (Objects.equals(this.getOwnerId(), itemEntity.getOwnerId()) && ItemEntity.canMergeStacks(itemStack, itemStack2)) {
            if (itemStack2.getCount() < itemStack.getCount()) {
                ItemEntity.func_213858_a(this, itemStack, itemEntity, itemStack2);
            } else {
                ItemEntity.func_213858_a(itemEntity, itemStack2, this, itemStack);
            }
        }
    }

    public static boolean canMergeStacks(ItemStack itemStack, ItemStack itemStack2) {
        if (itemStack2.getItem() != itemStack.getItem()) {
            return true;
        }
        if (itemStack2.getCount() + itemStack.getCount() > itemStack2.getMaxStackSize()) {
            return true;
        }
        if (itemStack2.hasTag() ^ itemStack.hasTag()) {
            return true;
        }
        return !itemStack2.hasTag() || itemStack2.getTag().equals(itemStack.getTag());
    }

    public static ItemStack mergeStacks(ItemStack itemStack, ItemStack itemStack2, int n) {
        int n2 = Math.min(Math.min(itemStack.getMaxStackSize(), n) - itemStack.getCount(), itemStack2.getCount());
        ItemStack itemStack3 = itemStack.copy();
        itemStack3.grow(n2);
        itemStack2.shrink(n2);
        return itemStack3;
    }

    private static void func_226531_a_(ItemEntity itemEntity, ItemStack itemStack, ItemStack itemStack2) {
        ItemStack itemStack3 = ItemEntity.mergeStacks(itemStack, itemStack2, 64);
        itemEntity.setItem(itemStack3);
    }

    private static void func_213858_a(ItemEntity itemEntity, ItemStack itemStack, ItemEntity itemEntity2, ItemStack itemStack2) {
        ItemEntity.func_226531_a_(itemEntity, itemStack, itemStack2);
        itemEntity.pickupDelay = Math.max(itemEntity.pickupDelay, itemEntity2.pickupDelay);
        itemEntity.age = Math.min(itemEntity.age, itemEntity2.age);
        if (itemStack2.isEmpty()) {
            itemEntity2.remove();
        }
    }

    @Override
    public boolean isImmuneToFire() {
        return this.getItem().getItem().isImmuneToFire() || super.isImmuneToFire();
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        if (!this.getItem().isEmpty() && this.getItem().getItem() == Items.NETHER_STAR && damageSource.isExplosion()) {
            return true;
        }
        if (!this.getItem().getItem().isDamageable(damageSource)) {
            return true;
        }
        this.markVelocityChanged();
        this.health = (int)((float)this.health - f);
        if (this.health <= 0) {
            this.remove();
        }
        return true;
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        compoundNBT.putShort("Health", (short)this.health);
        compoundNBT.putShort("Age", (short)this.age);
        compoundNBT.putShort("PickupDelay", (short)this.pickupDelay);
        if (this.getThrowerId() != null) {
            compoundNBT.putUniqueId("Thrower", this.getThrowerId());
        }
        if (this.getOwnerId() != null) {
            compoundNBT.putUniqueId("Owner", this.getOwnerId());
        }
        if (!this.getItem().isEmpty()) {
            compoundNBT.put("Item", this.getItem().write(new CompoundNBT()));
        }
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        this.health = compoundNBT.getShort("Health");
        this.age = compoundNBT.getShort("Age");
        if (compoundNBT.contains("PickupDelay")) {
            this.pickupDelay = compoundNBT.getShort("PickupDelay");
        }
        if (compoundNBT.hasUniqueId("Owner")) {
            this.owner = compoundNBT.getUniqueId("Owner");
        }
        if (compoundNBT.hasUniqueId("Thrower")) {
            this.thrower = compoundNBT.getUniqueId("Thrower");
        }
        CompoundNBT compoundNBT2 = compoundNBT.getCompound("Item");
        this.setItem(ItemStack.read(compoundNBT2));
        if (this.getItem().isEmpty()) {
            this.remove();
        }
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity playerEntity) {
        if (!this.world.isRemote) {
            ItemStack itemStack = this.getItem();
            Item item = itemStack.getItem();
            int n = itemStack.getCount();
            if (this.pickupDelay == 0 && (this.owner == null || this.owner.equals(playerEntity.getUniqueID())) && playerEntity.inventory.addItemStackToInventory(itemStack)) {
                playerEntity.onItemPickup(this, n);
                if (itemStack.isEmpty()) {
                    this.remove();
                    itemStack.setCount(n);
                }
                playerEntity.addStat(Stats.ITEM_PICKED_UP.get(item), n);
                playerEntity.triggerItemPickupTrigger(this);
            }
        }
    }

    @Override
    public ITextComponent getName() {
        ITextComponent iTextComponent = this.getCustomName();
        return iTextComponent != null ? iTextComponent : new TranslationTextComponent(this.getItem().getTranslationKey());
    }

    @Override
    public boolean canBeAttackedWithItem() {
        return true;
    }

    @Override
    @Nullable
    public Entity changeDimension(ServerWorld serverWorld) {
        Entity entity2 = super.changeDimension(serverWorld);
        if (!this.world.isRemote && entity2 instanceof ItemEntity) {
            ((ItemEntity)entity2).searchForOtherItemsNearby();
        }
        return entity2;
    }

    public ItemStack getItem() {
        return this.getDataManager().get(ITEM);
    }

    public void setItem(ItemStack itemStack) {
        this.getDataManager().set(ITEM, itemStack);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        super.notifyDataManagerChange(dataParameter);
        if (ITEM.equals(dataParameter)) {
            this.getItem().setAttachedEntity(this);
        }
    }

    @Nullable
    public UUID getOwnerId() {
        return this.owner;
    }

    public void setOwnerId(@Nullable UUID uUID) {
        this.owner = uUID;
    }

    @Nullable
    public UUID getThrowerId() {
        return this.thrower;
    }

    public void setThrowerId(@Nullable UUID uUID) {
        this.thrower = uUID;
    }

    public int getAge() {
        return this.age;
    }

    public void setDefaultPickupDelay() {
        this.pickupDelay = 10;
    }

    public void setNoPickupDelay() {
        this.pickupDelay = 0;
    }

    public void setInfinitePickupDelay() {
        this.pickupDelay = Short.MAX_VALUE;
    }

    public void setPickupDelay(int n) {
        this.pickupDelay = n;
    }

    public boolean cannotPickup() {
        return this.pickupDelay > 0;
    }

    public void setNoDespawn() {
        this.age = -6000;
    }

    public void makeFakeItem() {
        this.setInfinitePickupDelay();
        this.age = 5999;
    }

    public float getItemHover(float f) {
        return ((float)this.getAge() + f) / 20.0f + this.hoverStart;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }

    public ItemEntity func_234273_t_() {
        return new ItemEntity(this);
    }

    private boolean lambda$searchForOtherItemsNearby$0(ItemEntity itemEntity) {
        return itemEntity != this && itemEntity.func_213857_z();
    }
}

