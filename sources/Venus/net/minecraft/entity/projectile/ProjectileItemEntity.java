/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public abstract class ProjectileItemEntity
extends ThrowableEntity
implements IRendersAsItem {
    private static final DataParameter<ItemStack> ITEMSTACK_DATA = EntityDataManager.createKey(ProjectileItemEntity.class, DataSerializers.ITEMSTACK);

    public ProjectileItemEntity(EntityType<? extends ProjectileItemEntity> entityType, World world) {
        super((EntityType<? extends ThrowableEntity>)entityType, world);
    }

    public ProjectileItemEntity(EntityType<? extends ProjectileItemEntity> entityType, double d, double d2, double d3, World world) {
        super(entityType, d, d2, d3, world);
    }

    public ProjectileItemEntity(EntityType<? extends ProjectileItemEntity> entityType, LivingEntity livingEntity, World world) {
        super(entityType, livingEntity, world);
    }

    public void setItem(ItemStack itemStack) {
        if (itemStack.getItem() != this.getDefaultItem() || itemStack.hasTag()) {
            this.getDataManager().set(ITEMSTACK_DATA, Util.make(itemStack.copy(), ProjectileItemEntity::lambda$setItem$0));
        }
    }

    protected abstract Item getDefaultItem();

    protected ItemStack func_213882_k() {
        return this.getDataManager().get(ITEMSTACK_DATA);
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.func_213882_k();
        return itemStack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemStack;
    }

    @Override
    protected void registerData() {
        this.getDataManager().register(ITEMSTACK_DATA, ItemStack.EMPTY);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        ItemStack itemStack = this.func_213882_k();
        if (!itemStack.isEmpty()) {
            compoundNBT.put("Item", itemStack.write(new CompoundNBT()));
        }
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        ItemStack itemStack = ItemStack.read(compoundNBT.getCompound("Item"));
        this.setItem(itemStack);
    }

    private static void lambda$setItem$0(ItemStack itemStack) {
        itemStack.setCount(1);
    }
}

