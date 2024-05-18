package net.minecraft.block;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.inventory.*;
import net.minecraft.dispenser.*;
import net.minecraft.tileentity.*;

public class BlockDropper extends BlockDispenser
{
    private final IBehaviorDispenseItem dropBehavior;
    
    @Override
    protected IBehaviorDispenseItem getBehavior(final ItemStack itemStack) {
        return this.dropBehavior;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void dispense(final World world, final BlockPos blockPos) {
        final BlockSourceImpl blockSourceImpl = new BlockSourceImpl(world, blockPos);
        final TileEntityDispenser tileEntityDispenser = blockSourceImpl.getBlockTileEntity();
        if (tileEntityDispenser != null) {
            final int dispenseSlot = tileEntityDispenser.getDispenseSlot();
            if (dispenseSlot < 0) {
                world.playAuxSFX(385 + 704 - 665 + 577, blockPos, "".length());
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                final ItemStack stackInSlot = tileEntityDispenser.getStackInSlot(dispenseSlot);
                if (stackInSlot != null) {
                    final EnumFacing enumFacing = world.getBlockState(blockPos).getValue((IProperty<EnumFacing>)BlockDropper.FACING);
                    final BlockPos offset = blockPos.offset(enumFacing);
                    final IInventory inventoryAtPosition = TileEntityHopper.getInventoryAtPosition(world, offset.getX(), offset.getY(), offset.getZ());
                    ItemStack itemStack;
                    if (inventoryAtPosition == null) {
                        itemStack = this.dropBehavior.dispense(blockSourceImpl, stackInSlot);
                        if (itemStack != null && itemStack.stackSize <= 0) {
                            itemStack = null;
                            "".length();
                            if (4 != 4) {
                                throw null;
                            }
                        }
                    }
                    else if (TileEntityHopper.putStackInInventoryAllSlots(inventoryAtPosition, stackInSlot.copy().splitStack(" ".length()), enumFacing.getOpposite()) == null) {
                        final ItemStack copy;
                        itemStack = (copy = stackInSlot.copy());
                        if ((copy.stackSize -= " ".length()) <= 0) {
                            itemStack = null;
                            "".length();
                            if (-1 == 0) {
                                throw null;
                            }
                        }
                    }
                    else {
                        itemStack = stackInSlot.copy();
                    }
                    tileEntityDispenser.setInventorySlotContents(dispenseSlot, itemStack);
                }
            }
        }
    }
    
    public BlockDropper() {
        this.dropBehavior = new BehaviorDefaultDispenseItem();
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityDropper();
    }
}
