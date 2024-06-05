package net.minecraft.init;

import net.minecraft.dispenser.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

static final class Bootstrap$13 extends BehaviorDefaultDispenseItem {
    private boolean field_150838_b = true;
    
    @Override
    protected ItemStack dispenseStack(final IBlockSource source, final ItemStack stack) {
        if (EnumDyeColor.WHITE == EnumDyeColor.func_176766_a(stack.getMetadata())) {
            final World var3 = source.getWorld();
            final BlockPos var4 = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
            if (ItemDye.func_179234_a(stack, var3, var4)) {
                if (!var3.isRemote) {
                    var3.playAuxSFX(2005, var4, 0);
                }
            }
            else {
                this.field_150838_b = false;
            }
            return stack;
        }
        return super.dispenseStack(source, stack);
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource source) {
        if (this.field_150838_b) {
            source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
        }
        else {
            source.getWorld().playAuxSFX(1001, source.getBlockPos(), 0);
        }
    }
}