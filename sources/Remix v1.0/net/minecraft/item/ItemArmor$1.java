package net.minecraft.item;

import net.minecraft.dispenser.*;
import net.minecraft.block.*;
import net.minecraft.command.*;
import com.google.common.base.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;

static final class ItemArmor$1 extends BehaviorDefaultDispenseItem {
    @Override
    protected ItemStack dispenseStack(final IBlockSource source, final ItemStack stack) {
        final BlockPos var3 = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
        final int var4 = var3.getX();
        final int var5 = var3.getY();
        final int var6 = var3.getZ();
        final AxisAlignedBB var7 = new AxisAlignedBB(var4, var5, var6, var4 + 1, var5 + 1, var6 + 1);
        final List var8 = source.getWorld().func_175647_a(EntityLivingBase.class, var7, Predicates.and(IEntitySelector.field_180132_d, (Predicate)new IEntitySelector.ArmoredMob(stack)));
        if (var8.size() > 0) {
            final EntityLivingBase var9 = var8.get(0);
            final int var10 = (var9 instanceof EntityPlayer) ? 1 : 0;
            final int var11 = EntityLiving.getArmorPosition(stack);
            final ItemStack var12 = stack.copy();
            var12.stackSize = 1;
            var9.setCurrentItemOrArmor(var11 - var10, var12);
            if (var9 instanceof EntityLiving) {
                ((EntityLiving)var9).setEquipmentDropChance(var11, 2.0f);
            }
            --stack.stackSize;
            return stack;
        }
        return super.dispenseStack(source, stack);
    }
}