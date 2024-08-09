/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class ProjectileDispenseBehavior
extends DefaultDispenseItemBehavior {
    @Override
    public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        ServerWorld serverWorld = iBlockSource.getWorld();
        IPosition iPosition = DispenserBlock.getDispensePosition(iBlockSource);
        Direction direction = iBlockSource.getBlockState().get(DispenserBlock.FACING);
        ProjectileEntity projectileEntity = this.getProjectileEntity(serverWorld, iPosition, itemStack);
        projectileEntity.shoot(direction.getXOffset(), (float)direction.getYOffset() + 0.1f, direction.getZOffset(), this.getProjectileVelocity(), this.getProjectileInaccuracy());
        serverWorld.addEntity(projectileEntity);
        itemStack.shrink(1);
        return itemStack;
    }

    @Override
    protected void playDispenseSound(IBlockSource iBlockSource) {
        iBlockSource.getWorld().playEvent(1002, iBlockSource.getBlockPos(), 0);
    }

    protected abstract ProjectileEntity getProjectileEntity(World var1, IPosition var2, ItemStack var3);

    protected float getProjectileInaccuracy() {
        return 6.0f;
    }

    protected float getProjectileVelocity() {
        return 1.1f;
    }
}

