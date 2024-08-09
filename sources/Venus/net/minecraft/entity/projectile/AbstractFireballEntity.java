/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public abstract class AbstractFireballEntity
extends DamagingProjectileEntity
implements IRendersAsItem {
    private static final DataParameter<ItemStack> STACK = EntityDataManager.createKey(AbstractFireballEntity.class, DataSerializers.ITEMSTACK);

    public AbstractFireballEntity(EntityType<? extends AbstractFireballEntity> entityType, World world) {
        super((EntityType<? extends DamagingProjectileEntity>)entityType, world);
    }

    public AbstractFireballEntity(EntityType<? extends AbstractFireballEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, World world) {
        super(entityType, d, d2, d3, d4, d5, d6, world);
    }

    public AbstractFireballEntity(EntityType<? extends AbstractFireballEntity> entityType, LivingEntity livingEntity, double d, double d2, double d3, World world) {
        super(entityType, livingEntity, d, d2, d3, world);
    }

    public void setStack(ItemStack itemStack) {
        if (itemStack.getItem() != Items.FIRE_CHARGE || itemStack.hasTag()) {
            this.getDataManager().set(STACK, Util.make(itemStack.copy(), AbstractFireballEntity::lambda$setStack$0));
        }
    }

    protected ItemStack getStack() {
        return this.getDataManager().get(STACK);
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getStack();
        return itemStack.isEmpty() ? new ItemStack(Items.FIRE_CHARGE) : itemStack;
    }

    @Override
    protected void registerData() {
        this.getDataManager().register(STACK, ItemStack.EMPTY);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        ItemStack itemStack = this.getStack();
        if (!itemStack.isEmpty()) {
            compoundNBT.put("Item", itemStack.write(new CompoundNBT()));
        }
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        ItemStack itemStack = ItemStack.read(compoundNBT.getCompound("Item"));
        this.setStack(itemStack);
    }

    private static void lambda$setStack$0(ItemStack itemStack) {
        itemStack.setCount(1);
    }
}

