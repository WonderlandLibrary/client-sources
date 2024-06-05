package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemDoor extends Item {
   private static final String __OBFID = "CL_00000020";
   private Block field_179236_a;

   public ItemDoor(Block var1) {
      this.field_179236_a = var1;
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var5 != EnumFacing.UP) {
         return false;
      } else {
         IBlockState var9 = var3.getBlockState(var4);
         Block var10 = var9.getBlock();
         if (!var10.isReplaceable(var3, var4)) {
            var4 = var4.offset(var5);
         }

         if (!var2.func_175151_a(var4, var5, var1)) {
            return false;
         } else if (!this.field_179236_a.canPlaceBlockAt(var3, var4)) {
            return false;
         } else {
            func_179235_a(var3, var4, EnumFacing.fromAngle((double)var2.rotationYaw), this.field_179236_a);
            --var1.stackSize;
            return true;
         }
      }
   }

   public static void func_179235_a(World var0, BlockPos var1, EnumFacing var2, Block var3) {
      BlockPos var4 = var1.offset(var2.rotateY());
      BlockPos var5 = var1.offset(var2.rotateYCCW());
      int var6 = (var0.getBlockState(var5).getBlock().isNormalCube() ? 1 : 0) + (var0.getBlockState(var5.offsetUp()).getBlock().isNormalCube() ? 1 : 0);
      int var7 = (var0.getBlockState(var4).getBlock().isNormalCube() ? 1 : 0) + (var0.getBlockState(var4.offsetUp()).getBlock().isNormalCube() ? 1 : 0);
      boolean var8 = var0.getBlockState(var5).getBlock() == var3 || var0.getBlockState(var5.offsetUp()).getBlock() == var3;
      boolean var9 = var0.getBlockState(var4).getBlock() == var3 || var0.getBlockState(var4.offsetUp()).getBlock() == var3;
      boolean var10 = false;
      if (var8 && !var9 || var7 > var6) {
         var10 = true;
      }

      BlockPos var11 = var1.offsetUp();
      IBlockState var12 = var3.getDefaultState().withProperty(BlockDoor.FACING_PROP, var2).withProperty(BlockDoor.HINGEPOSITION_PROP, var10 ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT);
      var0.setBlockState(var1, var12.withProperty(BlockDoor.HALF_PROP, BlockDoor.EnumDoorHalf.LOWER), 2);
      var0.setBlockState(var11, var12.withProperty(BlockDoor.HALF_PROP, BlockDoor.EnumDoorHalf.UPPER), 2);
      var0.notifyNeighborsOfStateChange(var1, var3);
      var0.notifyNeighborsOfStateChange(var11, var3);
   }
}
