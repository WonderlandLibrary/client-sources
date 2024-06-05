package net.minecraft.init;

import net.minecraft.dispenser.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

static final class Bootstrap$10 extends BehaviorDefaultDispenseItem {
    private final BehaviorDefaultDispenseItem field_150841_b = new BehaviorDefaultDispenseItem();
    
    public ItemStack dispenseStack(final IBlockSource source, final ItemStack stack) {
        final ItemBucket var3 = (ItemBucket)stack.getItem();
        final BlockPos var4 = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
        if (var3.func_180616_a(source.getWorld(), var4)) {
            stack.setItem(Items.bucket);
            stack.stackSize = 1;
            return stack;
        }
        return this.field_150841_b.dispense(source, stack);
    }
}