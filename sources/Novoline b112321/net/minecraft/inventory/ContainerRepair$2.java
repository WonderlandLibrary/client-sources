package net.minecraft.inventory;

import net.minecraft.block.BlockAnvil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

class ContainerRepair$2 extends Slot {
   final World val$worldIn;
   final BlockPos val$blockPosIn;
   final ContainerRepair this$0;

   ContainerRepair$2(ContainerRepair var1, IInventory var2, int var3, int var4, int var5, World var6, BlockPos var7) {
      super(var2, var3, var4, var5);
      this.this$0 = var1;
      this.val$worldIn = var6;
      this.val$blockPosIn = var7;
   }

   public boolean isItemValid(ItemStack var1) {
      return false;
   }

   public boolean canTakeStack(EntityPlayer var1) {
      return (var1.abilities.isCreative() || var1.experienceLevel >= this.this$0.maximumCost) && this.this$0.maximumCost > 0 && this.getHasStack();
   }

   public void onPickupFromSlot(EntityPlayer var1, ItemStack var2) {
      if(!var1.abilities.isCreative()) {
         var1.addExperienceLevel(-this.this$0.maximumCost);
      }

      ContainerRepair.access$000(this.this$0).setInventorySlotContents(0, (ItemStack)null);
      if(ContainerRepair.access$100(this.this$0) > 0) {
         ItemStack var3 = ContainerRepair.access$000(this.this$0).getStackInSlot(1);
         if(var3.stackSize > ContainerRepair.access$100(this.this$0)) {
            var3.stackSize -= ContainerRepair.access$100(this.this$0);
            ContainerRepair.access$000(this.this$0).setInventorySlotContents(1, var3);
         } else {
            ContainerRepair.access$000(this.this$0).setInventorySlotContents(1, (ItemStack)null);
         }
      } else {
         ContainerRepair.access$000(this.this$0).setInventorySlotContents(1, (ItemStack)null);
      }

      this.this$0.maximumCost = 0;
      IBlockState var5 = this.val$worldIn.getBlockState(this.val$blockPosIn);
      if(!var1.abilities.isCreative() && !this.val$worldIn.isRemote && var5.getBlock() == Blocks.anvil && var1.getRNG().nextFloat() < 0.12F) {
         int var4 = ((Integer)var5.getValue(BlockAnvil.Q)).intValue();
         ++var4;
         if(var4 > 2) {
            this.val$worldIn.setBlockToAir(this.val$blockPosIn);
            this.val$worldIn.playAuxSFX(1020, this.val$blockPosIn, 0);
         } else {
            this.val$worldIn.setBlockState(this.val$blockPosIn, var5.withProperty(BlockAnvil.Q, Integer.valueOf(var4)), 2);
            this.val$worldIn.playAuxSFX(1021, this.val$blockPosIn, 0);
         }
      } else if(!this.val$worldIn.isRemote) {
         this.val$worldIn.playAuxSFX(1021, this.val$blockPosIn, 0);
      }

   }
}
