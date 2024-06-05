package net.minecraft.inventory;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

class ContainerRepair$2 extends Slot {
    final /* synthetic */ World val$worldIn;
    final /* synthetic */ BlockPos val$p_i45807_3_;
    
    @Override
    public boolean isItemValid(final ItemStack stack) {
        return false;
    }
    
    @Override
    public boolean canTakeStack(final EntityPlayer p_82869_1_) {
        return (p_82869_1_.capabilities.isCreativeMode || p_82869_1_.experienceLevel >= ContainerRepair.this.maximumCost) && ContainerRepair.this.maximumCost > 0 && this.getHasStack();
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer playerIn, final ItemStack stack) {
        if (!playerIn.capabilities.isCreativeMode) {
            playerIn.addExperienceLevel(-ContainerRepair.this.maximumCost);
        }
        ContainerRepair.access$000(ContainerRepair.this).setInventorySlotContents(0, null);
        if (ContainerRepair.access$100(ContainerRepair.this) > 0) {
            final ItemStack var3 = ContainerRepair.access$000(ContainerRepair.this).getStackInSlot(1);
            if (var3 != null && var3.stackSize > ContainerRepair.access$100(ContainerRepair.this)) {
                final ItemStack itemStack = var3;
                itemStack.stackSize -= ContainerRepair.access$100(ContainerRepair.this);
                ContainerRepair.access$000(ContainerRepair.this).setInventorySlotContents(1, var3);
            }
            else {
                ContainerRepair.access$000(ContainerRepair.this).setInventorySlotContents(1, null);
            }
        }
        else {
            ContainerRepair.access$000(ContainerRepair.this).setInventorySlotContents(1, null);
        }
        ContainerRepair.this.maximumCost = 0;
        final IBlockState var4 = this.val$worldIn.getBlockState(this.val$p_i45807_3_);
        if (!playerIn.capabilities.isCreativeMode && !this.val$worldIn.isRemote && var4.getBlock() == Blocks.anvil && playerIn.getRNG().nextFloat() < 0.12f) {
            int var5 = (int)var4.getValue(BlockAnvil.DAMAGE);
            if (++var5 > 2) {
                this.val$worldIn.setBlockToAir(this.val$p_i45807_3_);
                this.val$worldIn.playAuxSFX(1020, this.val$p_i45807_3_, 0);
            }
            else {
                this.val$worldIn.setBlockState(this.val$p_i45807_3_, var4.withProperty(BlockAnvil.DAMAGE, var5), 2);
                this.val$worldIn.playAuxSFX(1021, this.val$p_i45807_3_, 0);
            }
        }
        else if (!this.val$worldIn.isRemote) {
            this.val$worldIn.playAuxSFX(1021, this.val$p_i45807_3_, 0);
        }
    }
}