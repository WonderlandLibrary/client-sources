package net;

import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotations;
import net.minecraft.world.World;

public class ajY extends Item {
   public ajY() {
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if(var5 == EnumFacing.DOWN) {
         return false;
      } else {
         boolean var9 = var3.getBlockState(var4).getBlock().isReplaceable(var3, var4);
         if(!var2.a(var4, var5, var1)) {
            return false;
         } else {
            BlockPos var11 = var4.up();
            boolean var12 = !var3.isAirBlock(var4) && !var3.getBlockState(var4).getBlock().isReplaceable(var3, var4);
            var12 = var12 | (!var3.isAirBlock(var11) && !var3.getBlockState(var11).getBlock().isReplaceable(var3, var11));
            return false;
         }
      }
   }

   private void a(EntityArmorStand var1, Random var2) {
      Rotations var3 = var1.getHeadRotation();
      float var4 = var2.nextFloat() * 5.0F;
      float var5 = var2.nextFloat() * 20.0F - 10.0F;
      Rotations var6 = new Rotations(var3.getX() + var4, var3.getY() + var5, var3.getZ());
      var1.setHeadRotation(var6);
      var3 = var1.getBodyRotation();
      var4 = var2.nextFloat() * 10.0F - 5.0F;
      var6 = new Rotations(var3.getX(), var3.getY() + var4, var3.getZ());
      var1.setBodyRotation(var6);
   }
}
