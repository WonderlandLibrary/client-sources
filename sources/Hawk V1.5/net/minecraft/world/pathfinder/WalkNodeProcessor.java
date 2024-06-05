package net.minecraft.world.pathfinder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class WalkNodeProcessor extends NodeProcessor {
   private boolean field_176182_j;
   private boolean field_176180_f;
   private boolean field_176181_g;
   private boolean field_176184_i;
   private static final String __OBFID = "CL_00001965";
   private boolean field_176183_h;

   public void func_176176_c(boolean var1) {
      this.field_176183_h = var1;
   }

   public void func_176175_a(boolean var1) {
      this.field_176180_f = var1;
   }

   private int func_176177_a(Entity var1, int var2, int var3, int var4) {
      return func_176170_a(this.field_176169_a, var1, var2, var3, var4, this.field_176168_c, this.field_176165_d, this.field_176166_e, this.field_176183_h, this.field_176181_g, this.field_176180_f);
   }

   public PathPoint func_176161_a(Entity var1) {
      int var2;
      if (this.field_176184_i && var1.isInWater()) {
         var2 = (int)var1.getEntityBoundingBox().minY;

         for(Block var3 = this.field_176169_a.getBlockState(new BlockPos(MathHelper.floor_double(var1.posX), var2, MathHelper.floor_double(var1.posZ))).getBlock(); var3 == Blocks.flowing_water || var3 == Blocks.water; var3 = this.field_176169_a.getBlockState(new BlockPos(MathHelper.floor_double(var1.posX), var2, MathHelper.floor_double(var1.posZ))).getBlock()) {
            ++var2;
         }

         this.field_176183_h = false;
      } else {
         var2 = MathHelper.floor_double(var1.getEntityBoundingBox().minY + 0.5D);
      }

      return this.func_176159_a(MathHelper.floor_double(var1.getEntityBoundingBox().minX), var2, MathHelper.floor_double(var1.getEntityBoundingBox().minZ));
   }

   public boolean func_176174_d() {
      return this.field_176184_i;
   }

   public int func_176164_a(PathPoint[] var1, Entity var2, PathPoint var3, PathPoint var4, float var5) {
      int var6 = 0;
      byte var7 = 0;
      if (this.func_176177_a(var2, var3.xCoord, var3.yCoord + 1, var3.zCoord) == 1) {
         var7 = 1;
      }

      PathPoint var8 = this.func_176171_a(var2, var3.xCoord, var3.yCoord, var3.zCoord + 1, var7);
      PathPoint var9 = this.func_176171_a(var2, var3.xCoord - 1, var3.yCoord, var3.zCoord, var7);
      PathPoint var10 = this.func_176171_a(var2, var3.xCoord + 1, var3.yCoord, var3.zCoord, var7);
      PathPoint var11 = this.func_176171_a(var2, var3.xCoord, var3.yCoord, var3.zCoord - 1, var7);
      if (var8 != null && !var8.visited && var8.distanceTo(var4) < var5) {
         var1[var6++] = var8;
      }

      if (var9 != null && !var9.visited && var9.distanceTo(var4) < var5) {
         var1[var6++] = var9;
      }

      if (var10 != null && !var10.visited && var10.distanceTo(var4) < var5) {
         var1[var6++] = var10;
      }

      if (var11 != null && !var11.visited && var11.distanceTo(var4) < var5) {
         var1[var6++] = var11;
      }

      return var6;
   }

   public boolean func_176179_b() {
      return this.field_176180_f;
   }

   public void func_176163_a() {
      super.func_176163_a();
      this.field_176183_h = this.field_176182_j;
   }

   public PathPoint func_176160_a(Entity var1, double var2, double var4, double var6) {
      return this.func_176159_a(MathHelper.floor_double(var2 - (double)(var1.width / 2.0F)), MathHelper.floor_double(var4), MathHelper.floor_double(var6 - (double)(var1.width / 2.0F)));
   }

   public static int func_176170_a(IBlockAccess var0, Entity var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, boolean var9, boolean var10) {
      boolean var11 = false;
      BlockPos var12 = new BlockPos(var1);

      for(int var13 = var2; var13 < var2 + var5; ++var13) {
         for(int var14 = var3; var14 < var3 + var6; ++var14) {
            for(int var15 = var4; var15 < var4 + var7; ++var15) {
               BlockPos var16 = new BlockPos(var13, var14, var15);
               Block var17 = var0.getBlockState(var16).getBlock();
               if (var17.getMaterial() != Material.air) {
                  if (var17 != Blocks.trapdoor && var17 != Blocks.iron_trapdoor) {
                     if (var17 != Blocks.flowing_water && var17 != Blocks.water) {
                        if (!var10 && var17 instanceof BlockDoor && var17.getMaterial() == Material.wood) {
                           return 0;
                        }
                     } else {
                        if (var8) {
                           return -1;
                        }

                        var11 = true;
                     }
                  } else {
                     var11 = true;
                  }

                  if (var1.worldObj.getBlockState(var16).getBlock() instanceof BlockRailBase) {
                     if (!(var1.worldObj.getBlockState(var12).getBlock() instanceof BlockRailBase) && !(var1.worldObj.getBlockState(var12.offsetDown()).getBlock() instanceof BlockRailBase)) {
                        return -3;
                     }
                  } else if (!var17.isPassable(var0, var16) && (!var9 || !(var17 instanceof BlockDoor) || var17.getMaterial() != Material.wood)) {
                     if (var17 instanceof BlockFence || var17 instanceof BlockFenceGate || var17 instanceof BlockWall) {
                        return -3;
                     }

                     if (var17 == Blocks.trapdoor || var17 == Blocks.iron_trapdoor) {
                        return -4;
                     }

                     Material var18 = var17.getMaterial();
                     if (var18 != Material.lava) {
                        return 0;
                     }

                     if (!var1.func_180799_ab()) {
                        return -2;
                     }
                  }
               }
            }
         }
      }

      return var11 ? 2 : 1;
   }

   public void func_176162_a(IBlockAccess var1, Entity var2) {
      super.func_176162_a(var1, var2);
      this.field_176182_j = this.field_176183_h;
   }

   public void func_176178_d(boolean var1) {
      this.field_176184_i = var1;
   }

   public boolean func_176173_e() {
      return this.field_176183_h;
   }

   public void func_176172_b(boolean var1) {
      this.field_176181_g = var1;
   }

   private PathPoint func_176171_a(Entity var1, int var2, int var3, int var4, int var5) {
      PathPoint var6 = null;
      int var7 = this.func_176177_a(var1, var2, var3, var4);
      if (var7 == 2) {
         return this.func_176159_a(var2, var3, var4);
      } else {
         if (var7 == 1) {
            var6 = this.func_176159_a(var2, var3, var4);
         }

         if (var6 == null && var5 > 0 && var7 != -3 && var7 != -4 && this.func_176177_a(var1, var2, var3 + var5, var4) == 1) {
            var6 = this.func_176159_a(var2, var3 + var5, var4);
            var3 += var5;
         }

         if (var6 != null) {
            int var8 = 0;

            int var9;
            for(var9 = 0; var3 > 0; var6 = this.func_176159_a(var2, var3, var4)) {
               var9 = this.func_176177_a(var1, var2, var3 - 1, var4);
               if (this.field_176183_h && var9 == -1) {
                  return null;
               }

               if (var9 != 1) {
                  break;
               }

               if (var8++ >= var1.getMaxFallHeight()) {
                  return null;
               }

               --var3;
               if (var3 <= 0) {
                  return null;
               }
            }

            if (var9 == -2) {
               return null;
            }
         }

         return var6;
      }
   }
}
