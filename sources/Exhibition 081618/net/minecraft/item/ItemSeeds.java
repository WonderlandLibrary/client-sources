package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSeeds extends Item {
   private Block cropBlock;
   private Block soilBlockID;

   public ItemSeeds(Block cropBlock, Block soilBlock) {
      this.cropBlock = cropBlock;
      this.soilBlockID = soilBlock;
      this.setCreativeTab(CreativeTabs.tabMaterials);
   }

   public Block getCropBlock() {
      return this.cropBlock;
   }

   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
      if (side != EnumFacing.UP) {
         return false;
      } else if (!playerIn.func_175151_a(pos.offset(side), side, stack)) {
         return false;
      } else if (worldIn.getBlockState(pos).getBlock() == this.soilBlockID && worldIn.isAirBlock(pos.offsetUp())) {
         worldIn.setBlockState(pos.offsetUp(), this.cropBlock.getDefaultState());
         --stack.stackSize;
         return true;
      } else {
         return false;
      }
   }
}
