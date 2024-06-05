package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition$MovingObjectType;
import net.minecraft.world.World;

public class ItemBucket extends Item {
   private Block isFull;

   public ItemBucket(Block var1) {
      this.maxStackSize = 1;
      this.isFull = var1;
      this.setCreativeTab(CreativeTabs.tabMisc);
   }

   public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
      boolean var4 = this.isFull == Blocks.air;
      MovingObjectPosition var5 = this.getMovingObjectPositionFromPlayer(var2, var3, var4);
      if(var5.typeOfHit == MovingObjectPosition$MovingObjectType.BLOCK) {
         BlockPos var6 = var5.getBlockPos();
         if(!var2.isBlockModifiable(var3, var6)) {
            return var1;
         }

         if(!var3.a(var6.offset(var5.facing), var5.facing, var1)) {
            return var1;
         }

         IBlockState var7 = var2.getBlockState(var6);
         Material var8 = var7.getBlock().getMaterial();
         if(var8 == Material.water && ((Integer)var7.getValue(BlockLiquid.P)).intValue() == 0) {
            var2.setBlockToAir(var6);
            var3.triggerAchievement(StatList.objectUseStats[Item.b(this)]);
            return this.fillBucket(var1, var3, Items.water_bucket);
         }

         if(var8 == Material.lava && ((Integer)var7.getValue(BlockLiquid.P)).intValue() == 0) {
            var2.setBlockToAir(var6);
            var3.triggerAchievement(StatList.objectUseStats[Item.b(this)]);
            return this.fillBucket(var1, var3, Items.lava_bucket);
         }
      }

      return var1;
   }

   private ItemStack fillBucket(ItemStack var1, EntityPlayer var2, Item var3) {
      if(var2.abilities.isCreative()) {
         return var1;
      } else if(--var1.stackSize <= 0) {
         return new ItemStack(var3);
      } else {
         if(!var2.inventory.addItemStackToInventory(new ItemStack(var3))) {
            var2.dropPlayerItemWithRandomChoice(new ItemStack(var3, 1, 0), false);
         }

         return var1;
      }
   }

   public boolean tryPlaceContainedLiquid(World var1, BlockPos var2) {
      if(this.isFull == Blocks.air) {
         return false;
      } else {
         Material var3 = var1.getBlockState(var2).getBlock().getMaterial();
         boolean var4 = !var3.isSolid();
         if(!var1.isAirBlock(var2)) {
            return false;
         } else {
            if(var1.provider.doesWaterVaporize() && this.isFull == Blocks.flowing_water) {
               int var5 = var2.getX();
               int var6 = var2.getY();
               int var7 = var2.getZ();
               var1.playSoundEffect((double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), (double)((float)var7 + 0.5F), "random.fizz", 0.5F, 2.6F + (var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.8F);

               for(int var8 = 0; var8 < 8; ++var8) {
                  var1.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double)var5 + Math.random(), (double)var6 + Math.random(), (double)var7 + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
               }
            } else {
               if(!var1.isRemote && !var3.isLiquid()) {
                  var1.destroyBlock(var2, true);
               }

               var1.setBlockState(var2, this.isFull.getDefaultState(), 3);
            }

            return true;
         }
      }
   }
}
