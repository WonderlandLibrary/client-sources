package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition$MovingObjectType;
import net.minecraft.world.World;

public class ItemLilyPad extends ItemColored {
   public ItemLilyPad(Block var1) {
      super(var1, false);
   }

   public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
      MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(var2, var3, true);
      if(var4.typeOfHit == MovingObjectPosition$MovingObjectType.BLOCK) {
         BlockPos var5 = var4.getBlockPos();
         if(!var2.isBlockModifiable(var3, var5)) {
            return var1;
         }

         if(!var3.a(var5.offset(var4.facing), var4.facing, var1)) {
            return var1;
         }

         BlockPos var6 = var5.up();
         IBlockState var7 = var2.getBlockState(var5);
         if(var7.getBlock().getMaterial() == Material.water && ((Integer)var7.getValue(BlockLiquid.P)).intValue() == 0 && var2.isAirBlock(var6)) {
            var2.setBlockState(var6, Blocks.waterlily.getDefaultState());
            if(!var3.abilities.isCreative()) {
               --var1.stackSize;
            }

            var3.triggerAchievement(StatList.objectUseStats[Item.b(this)]);
         }
      }

      return var1;
   }

   public int getColorFromItemStack(ItemStack var1, int var2) {
      return Blocks.waterlily.getRenderColor(Blocks.waterlily.getStateFromMeta(var1.getMetadata()));
   }
}
