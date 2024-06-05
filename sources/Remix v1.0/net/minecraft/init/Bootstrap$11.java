package net.minecraft.init;

import net.minecraft.dispenser.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.item.*;

static final class Bootstrap$11 extends BehaviorDefaultDispenseItem {
    private final BehaviorDefaultDispenseItem field_150840_b = new BehaviorDefaultDispenseItem();
    
    public ItemStack dispenseStack(final IBlockSource source, final ItemStack stack) {
        final World var3 = source.getWorld();
        final BlockPos var4 = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
        final IBlockState var5 = var3.getBlockState(var4);
        final Block var6 = var5.getBlock();
        final Material var7 = var6.getMaterial();
        Item var8;
        if (Material.water.equals(var7) && var6 instanceof BlockLiquid && (int)var5.getValue(BlockLiquid.LEVEL) == 0) {
            var8 = Items.water_bucket;
        }
        else {
            if (!Material.lava.equals(var7) || !(var6 instanceof BlockLiquid) || (int)var5.getValue(BlockLiquid.LEVEL) != 0) {
                return super.dispenseStack(source, stack);
            }
            var8 = Items.lava_bucket;
        }
        var3.setBlockToAir(var4);
        final int stackSize = stack.stackSize - 1;
        stack.stackSize = stackSize;
        if (stackSize == 0) {
            stack.setItem(var8);
            stack.stackSize = 1;
        }
        else if (((TileEntityDispenser)source.getBlockTileEntity()).func_146019_a(new ItemStack(var8)) < 0) {
            this.field_150840_b.dispense(source, new ItemStack(var8));
        }
        return stack;
    }
}