/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.ProxyBlockSource;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.DropperTileEntity;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

public class DropperBlock
extends DispenserBlock {
    private static final IDispenseItemBehavior DISPENSE_BEHAVIOR = new DefaultDispenseItemBehavior();

    public DropperBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    protected IDispenseItemBehavior getBehavior(ItemStack itemStack) {
        return DISPENSE_BEHAVIOR;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new DropperTileEntity();
    }

    @Override
    protected void dispense(ServerWorld serverWorld, BlockPos blockPos) {
        ProxyBlockSource proxyBlockSource = new ProxyBlockSource(serverWorld, blockPos);
        DispenserTileEntity dispenserTileEntity = (DispenserTileEntity)proxyBlockSource.getBlockTileEntity();
        int n = dispenserTileEntity.getDispenseSlot();
        if (n < 0) {
            serverWorld.playEvent(1001, blockPos, 0);
        } else {
            ItemStack itemStack = dispenserTileEntity.getStackInSlot(n);
            if (!itemStack.isEmpty()) {
                ItemStack itemStack2;
                Direction direction = serverWorld.getBlockState(blockPos).get(FACING);
                IInventory iInventory = HopperTileEntity.getInventoryAtPosition(serverWorld, blockPos.offset(direction));
                if (iInventory == null) {
                    itemStack2 = DISPENSE_BEHAVIOR.dispense(proxyBlockSource, itemStack);
                } else {
                    itemStack2 = HopperTileEntity.putStackInInventoryAllSlots(dispenserTileEntity, iInventory, itemStack.copy().split(1), direction.getOpposite());
                    if (itemStack2.isEmpty()) {
                        itemStack2 = itemStack.copy();
                        itemStack2.shrink(1);
                    } else {
                        itemStack2 = itemStack.copy();
                    }
                }
                dispenserTileEntity.setInventorySlotContents(n, itemStack2);
            }
        }
    }
}

