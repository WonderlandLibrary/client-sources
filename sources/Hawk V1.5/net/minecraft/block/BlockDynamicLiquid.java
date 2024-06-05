package net.minecraft.block;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockDynamicLiquid extends BlockLiquid {
   int field_149815_a;
   private static final String __OBFID = "CL_00000234";

   protected int func_176371_a(World var1, BlockPos var2, int var3) {
      int var4 = this.func_176362_e(var1, var2);
      if (var4 < 0) {
         return var3;
      } else {
         if (var4 == 0) {
            ++this.field_149815_a;
         }

         if (var4 >= 8) {
            var4 = 0;
         }

         return var3 >= 0 && var4 >= var3 ? var3 : var4;
      }
   }

   protected BlockDynamicLiquid(Material var1) {
      super(var1);
   }

   private void func_180690_f(World var1, BlockPos var2, IBlockState var3) {
      var1.setBlockState(var2, getStaticLiquidForMaterial(this.blockMaterial).getDefaultState().withProperty(LEVEL, var3.getValue(LEVEL)), 2);
   }

   private void func_176375_a(World var1, BlockPos var2, IBlockState var3, int var4) {
      if (this.func_176373_h(var1, var2, var3)) {
         if (var3.getBlock() != Blocks.air) {
            if (this.blockMaterial == Material.lava) {
               this.func_180688_d(var1, var2);
            } else {
               var3.getBlock().dropBlockAsItem(var1, var2, var3, 0);
            }
         }

         var1.setBlockState(var2, this.getDefaultState().withProperty(LEVEL, var4), 3);
      }

   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      int var5 = (Integer)var3.getValue(LEVEL);
      byte var6 = 1;
      if (this.blockMaterial == Material.lava && !var1.provider.func_177500_n()) {
         var6 = 2;
      }

      int var7 = this.tickRate(var1);
      int var8;
      Iterator var11;
      if (var5 > 0) {
         int var9 = -100;
         this.field_149815_a = 0;

         EnumFacing var10;
         for(var11 = EnumFacing.Plane.HORIZONTAL.iterator(); var11.hasNext(); var9 = this.func_176371_a(var1, var2.offset(var10), var9)) {
            var10 = (EnumFacing)var11.next();
         }

         int var15 = var9 + var6;
         if (var15 >= 8 || var9 < 0) {
            var15 = -1;
         }

         if (this.func_176362_e(var1, var2.offsetUp()) >= 0) {
            var8 = this.func_176362_e(var1, var2.offsetUp());
            if (var8 >= 8) {
               var15 = var8;
            } else {
               var15 = var8 + 8;
            }
         }

         if (this.field_149815_a >= 2 && this.blockMaterial == Material.water) {
            IBlockState var12 = var1.getBlockState(var2.offsetDown());
            if (var12.getBlock().getMaterial().isSolid()) {
               var15 = 0;
            } else if (var12.getBlock().getMaterial() == this.blockMaterial && (Integer)var12.getValue(LEVEL) == 0) {
               var15 = 0;
            }
         }

         if (this.blockMaterial == Material.lava && var5 < 8 && var15 < 8 && var15 > var5 && var4.nextInt(4) != 0) {
            var7 *= 4;
         }

         if (var15 == var5) {
            this.func_180690_f(var1, var2, var3);
         } else {
            var5 = var15;
            if (var15 < 0) {
               var1.setBlockToAir(var2);
            } else {
               var3 = var3.withProperty(LEVEL, var15);
               var1.setBlockState(var2, var3, 2);
               var1.scheduleUpdate(var2, this, var7);
               var1.notifyNeighborsOfStateChange(var2, this);
            }
         }
      } else {
         this.func_180690_f(var1, var2, var3);
      }

      IBlockState var13 = var1.getBlockState(var2.offsetDown());
      if (this.func_176373_h(var1, var2.offsetDown(), var13)) {
         if (this.blockMaterial == Material.lava && var1.getBlockState(var2.offsetDown()).getBlock().getMaterial() == Material.water) {
            var1.setBlockState(var2.offsetDown(), Blocks.stone.getDefaultState());
            this.func_180688_d(var1, var2.offsetDown());
            return;
         }

         if (var5 >= 8) {
            this.func_176375_a(var1, var2.offsetDown(), var13, var5);
         } else {
            this.func_176375_a(var1, var2.offsetDown(), var13, var5 + 8);
         }
      } else if (var5 >= 0 && (var5 == 0 || this.func_176372_g(var1, var2.offsetDown(), var13))) {
         Set var14 = this.func_176376_e(var1, var2);
         var8 = var5 + var6;
         if (var5 >= 8) {
            var8 = 1;
         }

         if (var8 >= 8) {
            return;
         }

         var11 = var14.iterator();

         while(var11.hasNext()) {
            EnumFacing var16 = (EnumFacing)var11.next();
            this.func_176375_a(var1, var2.offset(var16), var1.getBlockState(var2.offset(var16)), var8);
         }
      }

   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      if (!this.func_176365_e(var1, var2, var3)) {
         var1.scheduleUpdate(var2, this, this.tickRate(var1));
      }

   }

   private boolean func_176372_g(World var1, BlockPos var2, IBlockState var3) {
      Block var4 = var1.getBlockState(var2).getBlock();
      return !(var4 instanceof BlockDoor) && var4 != Blocks.standing_sign && var4 != Blocks.ladder && var4 != Blocks.reeds ? (var4.blockMaterial == Material.portal ? true : var4.blockMaterial.blocksMovement()) : true;
   }

   private boolean func_176373_h(World var1, BlockPos var2, IBlockState var3) {
      Material var4 = var3.getBlock().getMaterial();
      return var4 != this.blockMaterial && var4 != Material.lava && !this.func_176372_g(var1, var2, var3);
   }

   private int func_176374_a(World var1, BlockPos var2, int var3, EnumFacing var4) {
      int var5 = 1000;
      Iterator var6 = EnumFacing.Plane.HORIZONTAL.iterator();

      while(true) {
         EnumFacing var7;
         BlockPos var8;
         IBlockState var9;
         do {
            do {
               do {
                  if (!var6.hasNext()) {
                     return var5;
                  }

                  var7 = (EnumFacing)var6.next();
               } while(var7 == var4);

               var8 = var2.offset(var7);
               var9 = var1.getBlockState(var8);
            } while(this.func_176372_g(var1, var8, var9));
         } while(var9.getBlock().getMaterial() == this.blockMaterial && (Integer)var9.getValue(LEVEL) <= 0);

         if (!this.func_176372_g(var1, var8.offsetDown(), var9)) {
            return var3;
         }

         if (var3 < 4) {
            int var10 = this.func_176374_a(var1, var8, var3 + 1, var7.getOpposite());
            if (var10 < var5) {
               var5 = var10;
            }
         }
      }
   }

   private Set func_176376_e(World var1, BlockPos var2) {
      int var3 = 1000;
      EnumSet var4 = EnumSet.noneOf(EnumFacing.class);
      Iterator var5 = EnumFacing.Plane.HORIZONTAL.iterator();

      while(true) {
         EnumFacing var6;
         BlockPos var7;
         IBlockState var8;
         do {
            do {
               if (!var5.hasNext()) {
                  return var4;
               }

               var6 = (EnumFacing)var5.next();
               var7 = var2.offset(var6);
               var8 = var1.getBlockState(var7);
            } while(this.func_176372_g(var1, var7, var8));
         } while(var8.getBlock().getMaterial() == this.blockMaterial && (Integer)var8.getValue(LEVEL) <= 0);

         int var9;
         if (this.func_176372_g(var1, var7.offsetDown(), var1.getBlockState(var7.offsetDown()))) {
            var9 = this.func_176374_a(var1, var7, 1, var6.getOpposite());
         } else {
            var9 = 0;
         }

         if (var9 < var3) {
            var4.clear();
         }

         if (var9 <= var3) {
            var4.add(var6);
            var3 = var9;
         }
      }
   }
}
