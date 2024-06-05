package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSnow extends ItemBlock {
   private static final String __OBFID = "CL_00000068";

   public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.stackSize == 0) {
         return false;
      } else if (!var2.func_175151_a(var4, var5, var1)) {
         return false;
      } else {
         IBlockState var9 = var3.getBlockState(var4);
         Block var10 = var9.getBlock();
         if (var10 != this.block && var5 != EnumFacing.UP) {
            var4 = var4.offset(var5);
            var9 = var3.getBlockState(var4);
            var10 = var9.getBlock();
         }

         if (var10 == this.block) {
            int var11 = (Integer)var9.getValue(BlockSnow.LAYERS_PROP);
            if (var11 <= 7) {
               IBlockState var12 = var9.withProperty(BlockSnow.LAYERS_PROP, var11 + 1);
               if (var3.checkNoEntityCollision(this.block.getCollisionBoundingBox(var3, var4, var12)) && var3.setBlockState(var4, var12, 2)) {
                  var3.playSoundEffect((double)((float)var4.getX() + 0.5F), (double)((float)var4.getY() + 0.5F), (double)((float)var4.getZ() + 0.5F), this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0F) / 2.0F, this.block.stepSound.getFrequency() * 0.8F);
                  --var1.stackSize;
                  return true;
               }
            }
         }

         return super.onItemUse(var1, var2, var3, var4, var5, var6, var7, var8);
      }
   }

   public ItemSnow(Block var1) {
      super(var1);
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
   }

   public int getMetadata(int var1) {
      return var1;
   }
}
