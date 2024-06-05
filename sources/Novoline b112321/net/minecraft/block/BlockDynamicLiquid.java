package net.minecraft.block;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.world.World;

public class BlockDynamicLiquid extends BlockLiquid {
   int adjacentSourceBlocks;

   protected BlockDynamicLiquid(Material var1) {
      super(var1);
   }

   private void placeStaticBlock(World var1, BlockPos var2, IBlockState var3) {
      var1.setBlockState(var2, getStaticBlock(this.blockMaterial).getDefaultState().withProperty(P, var3.getValue(P)), 2);
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      int var5 = ((Integer)var3.getValue(P)).intValue();
      byte var6 = 1;
      if(this.blockMaterial == Material.lava && !var1.provider.doesWaterVaporize()) {
         var6 = 2;
      }

      int var7 = this.tickRate(var1);
      int var8 = -100;
      this.adjacentSourceBlocks = 0;

      for(Object var10 : EnumFacing$Plane.HORIZONTAL) {
         var8 = this.a(var1, var2.offset((EnumFacing)var10), var8);
      }

      int var15 = var8 + var6;
      if(var15 < 8) {
         ;
      }

      var15 = -1;
      if(this.getLevel(var1, var2.up()) >= 0) {
         int var18 = this.getLevel(var1, var2.up());
         if(var18 >= 8) {
            var15 = var18;
         } else {
            var15 = var18 + 8;
         }
      }

      if(this.adjacentSourceBlocks >= 2 && this.blockMaterial == Material.water) {
         IBlockState var19 = var1.getBlockState(var2.down());
         if(var19.getBlock().getMaterial().isSolid()) {
            var15 = 0;
         } else if(var19.getBlock().getMaterial() == this.blockMaterial && ((Integer)var19.getValue(P)).intValue() == 0) {
            var15 = 0;
         }
      }

      if(this.blockMaterial == Material.lava && var5 < 8 && var15 < 8 && var15 > var5 && var4.nextInt(4) != 0) {
         var7 = var7 * 4;
      }

      if(var15 == var5) {
         this.placeStaticBlock(var1, var2, var3);
      } else {
         var5 = var15;
         var1.setBlockToAir(var2);
      }

      IBlockState var14 = var1.getBlockState(var2.down());
      if(this.canFlowInto(var1, var2.down(), var14)) {
         if(this.blockMaterial == Material.lava && var1.getBlockState(var2.down()).getBlock().getMaterial() == Material.water) {
            var1.setBlockState(var2.down(), Blocks.stone.getDefaultState());
            this.triggerMixEffects(var1, var2.down());
            return;
         }

         if(var5 >= 8) {
            this.tryFlowInto(var1, var2.down(), var14, var5);
         } else {
            this.tryFlowInto(var1, var2.down(), var14, var5 + 8);
         }
      } else if(this.isBlocked(var1, var2.down(), var14)) {
         Set var17 = this.getPossibleFlowDirections(var1, var2);
         int var20 = var5 + var6;
         if(var5 >= 8) {
            var20 = 1;
         }

         if(var20 >= 8) {
            return;
         }

         for(EnumFacing var12 : var17) {
            this.tryFlowInto(var1, var2.offset(var12), var1.getBlockState(var2.offset(var12)), var20);
         }
      }

   }

   private void tryFlowInto(World var1, BlockPos var2, IBlockState var3, int var4) {
      if(this.canFlowInto(var1, var2, var3)) {
         if(var3.getBlock() != Blocks.air) {
            if(this.blockMaterial == Material.lava) {
               this.triggerMixEffects(var1, var2);
            } else {
               var3.getBlock().dropBlockAsItem(var1, var2, var3, 0);
            }
         }

         var1.setBlockState(var2, this.getDefaultState().withProperty(P, Integer.valueOf(var4)), 3);
      }

   }

   private int func_176374_a(World var1, BlockPos var2, int var3, EnumFacing var4) {
      int var5 = 1000;

      for(Object var7 : EnumFacing$Plane.HORIZONTAL) {
         if(var7 != var4) {
            BlockPos var8 = var2.offset((EnumFacing)var7);
            IBlockState var9 = var1.getBlockState(var8);
            if(!this.isBlocked(var1, var8, var9) && (var9.getBlock().getMaterial() != this.blockMaterial || ((Integer)var9.getValue(P)).intValue() > 0)) {
               if(!this.isBlocked(var1, var8.down(), var9)) {
                  return var3;
               }

               if(var3 < 4) {
                  int var10 = this.func_176374_a(var1, var8, var3 + 1, ((EnumFacing)var7).getOpposite());
                  if(var10 < var5) {
                     var5 = var10;
                  }
               }
            }
         }
      }

      return var5;
   }

   private Set getPossibleFlowDirections(World var1, BlockPos var2) {
      int var3 = 1000;
      EnumSet var4 = EnumSet.noneOf(EnumFacing.class);

      for(Object var6 : EnumFacing$Plane.HORIZONTAL) {
         BlockPos var7 = var2.offset((EnumFacing)var6);
         IBlockState var8 = var1.getBlockState(var7);
         if(!this.isBlocked(var1, var7, var8) && (var8.getBlock().getMaterial() != this.blockMaterial || ((Integer)var8.getValue(P)).intValue() > 0)) {
            int var9;
            if(this.isBlocked(var1, var7.down(), var1.getBlockState(var7.down()))) {
               var9 = this.func_176374_a(var1, var7, 1, ((EnumFacing)var6).getOpposite());
            } else {
               var9 = 0;
            }

            if(var9 < var3) {
               var4.clear();
            }

            if(var9 <= var3) {
               var4.add((EnumFacing)var6);
               var3 = var9;
            }
         }
      }

      return var4;
   }

   private boolean isBlocked(World var1, BlockPos var2, IBlockState var3) {
      Block var4 = var1.getBlockState(var2).getBlock();
      return var4 instanceof BlockDoor || var4 == Blocks.standing_sign || var4 == Blocks.ladder || var4 == Blocks.reeds || var4.blockMaterial == Material.portal || var4.blockMaterial.blocksMovement();
   }

   protected int a(World var1, BlockPos var2, int var3) {
      int var4 = this.getLevel(var1, var2);
      return var3;
   }

   private boolean canFlowInto(World var1, BlockPos var2, IBlockState var3) {
      Material var4 = var3.getBlock().getMaterial();
      return var4 != this.blockMaterial && var4 != Material.lava && !this.isBlocked(var1, var2, var3);
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      if(!this.checkForMixing(var1, var2, var3)) {
         var1.scheduleUpdate(var2, this, this.tickRate(var1));
      }

   }
}
