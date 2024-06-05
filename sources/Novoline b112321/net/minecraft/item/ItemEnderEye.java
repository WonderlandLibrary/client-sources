package net.minecraft.item;

import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition$MovingObjectType;
import net.minecraft.world.World;

public class ItemEnderEye extends Item {
   public ItemEnderEye() {
      this.setCreativeTab(CreativeTabs.tabMisc);
   }

   public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      IBlockState var9 = var3.getBlockState(var4);
      if(var2.a(var4.offset(var5), var5, var1) && var9.getBlock() == Blocks.end_portal_frame && !((Boolean)var9.getValue(BlockEndPortalFrame.EYE)).booleanValue()) {
         if(!var3.isRemote) {
            var3.setBlockState(var4, var9.withProperty(BlockEndPortalFrame.EYE, Boolean.TRUE), 2);
            var3.updateComparatorOutputLevel(var4, Blocks.end_portal_frame);
            --var1.stackSize;

            for(int var10 = 0; var10 < 16; ++var10) {
               double var11 = (double)((float)var4.getX() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F);
               double var13 = (double)((float)var4.getY() + 0.8125F);
               double var15 = (double)((float)var4.getZ() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F);
               double var17 = 0.0D;
               double var19 = 0.0D;
               double var21 = 0.0D;
               var3.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var11, var13, var15, var17, var19, var21, new int[0]);
            }

            EnumFacing var23 = (EnumFacing)var9.getValue(BlockEndPortalFrame.FACING);
            int var24 = 0;
            int var12 = 0;
            boolean var25 = false;
            boolean var14 = true;
            EnumFacing var30 = var23.rotateY();

            for(int var16 = -2; var16 <= 2; ++var16) {
               BlockPos var34 = var4.offset(var30, var16);
               IBlockState var18 = var3.getBlockState(var34);
               if(var18.getBlock() == Blocks.end_portal_frame) {
                  if(!((Boolean)var18.getValue(BlockEndPortalFrame.EYE)).booleanValue()) {
                     var14 = false;
                     break;
                  }

                  var12 = var16;
                  var24 = var16;
                  var25 = true;
               }
            }

            if(var12 == var24 + 2) {
               BlockPos var31 = var4.offset(var23, 4);

               for(int var35 = var24; var35 <= var12; ++var35) {
                  BlockPos var38 = var31.offset(var30, var35);
                  IBlockState var41 = var3.getBlockState(var38);
                  if(var41.getBlock() != Blocks.end_portal_frame || !((Boolean)var41.getValue(BlockEndPortalFrame.EYE)).booleanValue()) {
                     var14 = false;
                     break;
                  }
               }

               for(int var36 = var24 - 1; var36 <= var12 + 1; var36 += 4) {
                  var31 = var4.offset(var30, var36);

                  for(int var39 = 1; var39 <= 3; ++var39) {
                     BlockPos var42 = var31.offset(var23, var39);
                     IBlockState var20 = var3.getBlockState(var42);
                     if(var20.getBlock() != Blocks.end_portal_frame || !((Boolean)var20.getValue(BlockEndPortalFrame.EYE)).booleanValue()) {
                        var14 = false;
                        break;
                     }
                  }
               }

               for(int var37 = var24; var37 <= var12; ++var37) {
                  var31 = var4.offset(var30, var37);

                  for(int var40 = 1; var40 <= 3; ++var40) {
                     BlockPos var43 = var31.offset(var23, var40);
                     var3.setBlockState(var43, Blocks.end_portal.getDefaultState(), 2);
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
      MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(var2, var3, false);
      if((var4.typeOfHit != MovingObjectPosition$MovingObjectType.BLOCK || var2.getBlockState(var4.getBlockPos()).getBlock() != Blocks.end_portal_frame) && !var2.isRemote) {
         BlockPos var5 = var2.getStrongholdPos("Stronghold", new BlockPos(var3));
         EntityEnderEye var6 = new EntityEnderEye(var2, var3.posX, var3.posY, var3.posZ);
         var6.moveTowards(var5);
         var2.spawnEntityInWorld(var6);
         var2.playSoundAtEntity(var3, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
         var2.playAuxSFXAtEntity((EntityPlayer)null, 1002, new BlockPos(var3), 0);
         if(!var3.abilities.isCreative()) {
            --var1.stackSize;
         }

         var3.triggerAchievement(StatList.objectUseStats[Item.b(this)]);
      }

      return var1;
   }
}
