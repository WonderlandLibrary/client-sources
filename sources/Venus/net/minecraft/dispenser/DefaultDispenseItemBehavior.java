/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class DefaultDispenseItemBehavior
implements IDispenseItemBehavior {
    @Override
    public final ItemStack dispense(IBlockSource iBlockSource, ItemStack itemStack) {
        ItemStack itemStack2 = this.dispenseStack(iBlockSource, itemStack);
        this.playDispenseSound(iBlockSource);
        this.spawnDispenseParticles(iBlockSource, iBlockSource.getBlockState().get(DispenserBlock.FACING));
        return itemStack2;
    }

    protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        Direction direction = iBlockSource.getBlockState().get(DispenserBlock.FACING);
        IPosition iPosition = DispenserBlock.getDispensePosition(iBlockSource);
        ItemStack itemStack2 = itemStack.split(1);
        DefaultDispenseItemBehavior.doDispense(iBlockSource.getWorld(), itemStack2, 6, direction, iPosition);
        return itemStack;
    }

    public static void doDispense(World world, ItemStack itemStack, int n, Direction direction, IPosition iPosition) {
        double d = iPosition.getX();
        double d2 = iPosition.getY();
        double d3 = iPosition.getZ();
        d2 = direction.getAxis() == Direction.Axis.Y ? (d2 -= 0.125) : (d2 -= 0.15625);
        ItemEntity itemEntity = new ItemEntity(world, d, d2, d3, itemStack);
        double d4 = world.rand.nextDouble() * 0.1 + 0.2;
        itemEntity.setMotion(world.rand.nextGaussian() * (double)0.0075f * (double)n + (double)direction.getXOffset() * d4, world.rand.nextGaussian() * (double)0.0075f * (double)n + (double)0.2f, world.rand.nextGaussian() * (double)0.0075f * (double)n + (double)direction.getZOffset() * d4);
        world.addEntity(itemEntity);
    }

    protected void playDispenseSound(IBlockSource iBlockSource) {
        iBlockSource.getWorld().playEvent(1000, iBlockSource.getBlockPos(), 0);
    }

    protected void spawnDispenseParticles(IBlockSource iBlockSource, Direction direction) {
        iBlockSource.getWorld().playEvent(2000, iBlockSource.getBlockPos(), direction.getIndex());
    }
}

