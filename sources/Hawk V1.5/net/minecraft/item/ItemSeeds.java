package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSeeds extends Item {
   private Block soilBlockID;
   private Block field_150925_a;
   private static final String __OBFID = "CL_00000061";

   public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var5 != EnumFacing.UP) {
         return false;
      } else if (!var2.func_175151_a(var4.offset(var5), var5, var1)) {
         return false;
      } else if (var3.getBlockState(var4).getBlock() == this.soilBlockID && var3.isAirBlock(var4.offsetUp())) {
         var3.setBlockState(var4.offsetUp(), this.field_150925_a.getDefaultState());
         --var1.stackSize;
         return true;
      } else {
         return false;
      }
   }

   public ItemSeeds(Block var1, Block var2) {
      this.field_150925_a = var1;
      this.soilBlockID = var2;
      this.setCreativeTab(CreativeTabs.tabMaterials);
   }
}
