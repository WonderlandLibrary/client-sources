/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class DispenseBoatBehavior
extends DefaultDispenseItemBehavior {
    private final DefaultDispenseItemBehavior dispenseItemBehaviour = new DefaultDispenseItemBehavior();
    private final BoatEntity.Type type;

    public DispenseBoatBehavior(BoatEntity.Type type) {
        this.type = type;
    }

    @Override
    public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        double d;
        Direction direction = iBlockSource.getBlockState().get(DispenserBlock.FACING);
        ServerWorld serverWorld = iBlockSource.getWorld();
        double d2 = iBlockSource.getX() + (double)((float)direction.getXOffset() * 1.125f);
        double d3 = iBlockSource.getY() + (double)((float)direction.getYOffset() * 1.125f);
        double d4 = iBlockSource.getZ() + (double)((float)direction.getZOffset() * 1.125f);
        BlockPos blockPos = iBlockSource.getBlockPos().offset(direction);
        if (serverWorld.getFluidState(blockPos).isTagged(FluidTags.WATER)) {
            d = 1.0;
        } else {
            if (!serverWorld.getBlockState(blockPos).isAir() || !serverWorld.getFluidState(blockPos.down()).isTagged(FluidTags.WATER)) {
                return this.dispenseItemBehaviour.dispense(iBlockSource, itemStack);
            }
            d = 0.0;
        }
        BoatEntity boatEntity = new BoatEntity(serverWorld, d2, d3 + d, d4);
        boatEntity.setBoatType(this.type);
        boatEntity.rotationYaw = direction.getHorizontalAngle();
        serverWorld.addEntity(boatEntity);
        itemStack.shrink(1);
        return itemStack;
    }

    @Override
    protected void playDispenseSound(IBlockSource iBlockSource) {
        iBlockSource.getWorld().playEvent(1000, iBlockSource.getBlockPos(), 0);
    }
}

