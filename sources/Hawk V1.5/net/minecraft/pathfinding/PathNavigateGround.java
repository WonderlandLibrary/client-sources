package net.minecraft.pathfinding;

import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.pathfinder.WalkNodeProcessor;

public class PathNavigateGround extends PathNavigate {
   private static final String __OBFID = "CL_00002246";
   protected WalkNodeProcessor field_179695_a;
   private boolean field_179694_f;

   public void func_179685_e(boolean var1) {
      this.field_179694_f = var1;
   }

   private boolean func_179683_a(int var1, int var2, int var3, int var4, int var5, int var6, Vec3 var7, double var8, double var10) {
      int var12 = var1 - var4 / 2;
      int var13 = var3 - var6 / 2;
      if (!this.func_179692_b(var12, var2, var13, var4, var5, var6, var7, var8, var10)) {
         return false;
      } else {
         for(int var14 = var12; var14 < var12 + var4; ++var14) {
            for(int var15 = var13; var15 < var13 + var6; ++var15) {
               double var16 = (double)var14 + 0.5D - var7.xCoord;
               double var18 = (double)var15 + 0.5D - var7.zCoord;
               if (var16 * var8 + var18 * var10 >= 0.0D) {
                  Block var20 = this.worldObj.getBlockState(new BlockPos(var14, var2 - 1, var15)).getBlock();
                  Material var21 = var20.getMaterial();
                  if (var21 == Material.air) {
                     return false;
                  }

                  if (var21 == Material.water && !this.theEntity.isInWater()) {
                     return false;
                  }

                  if (var21 == Material.lava) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   private boolean func_179692_b(int var1, int var2, int var3, int var4, int var5, int var6, Vec3 var7, double var8, double var10) {
      Iterator var12 = BlockPos.getAllInBox(new BlockPos(var1, var2, var3), new BlockPos(var1 + var4 - 1, var2 + var5 - 1, var3 + var6 - 1)).iterator();

      while(var12.hasNext()) {
         BlockPos var13 = (BlockPos)var12.next();
         double var14 = (double)var13.getX() + 0.5D - var7.xCoord;
         double var16 = (double)var13.getZ() + 0.5D - var7.zCoord;
         if (var14 * var8 + var16 * var10 >= 0.0D) {
            Block var18 = this.worldObj.getBlockState(var13).getBlock();
            if (!var18.isPassable(this.worldObj, var13)) {
               return false;
            }
         }
      }

      return true;
   }

   protected Vec3 getEntityPosition() {
      return new Vec3(this.theEntity.posX, (double)this.func_179687_p(), this.theEntity.posZ);
   }

   protected PathFinder func_179679_a() {
      this.field_179695_a = new WalkNodeProcessor();
      this.field_179695_a.func_176175_a(true);
      return new PathFinder(this.field_179695_a);
   }

   public void func_179691_c(boolean var1) {
      this.field_179695_a.func_176175_a(var1);
   }

   public boolean func_179686_g() {
      return this.field_179695_a.func_176179_b();
   }

   public void func_179690_a(boolean var1) {
      this.field_179695_a.func_176176_c(var1);
   }

   public PathNavigateGround(EntityLiving var1, World var2) {
      super(var1, var2);
   }

   protected boolean isDirectPathBetweenPoints(Vec3 var1, Vec3 var2, int var3, int var4, int var5) {
      int var6 = MathHelper.floor_double(var1.xCoord);
      int var7 = MathHelper.floor_double(var1.zCoord);
      double var8 = var2.xCoord - var1.xCoord;
      double var10 = var2.zCoord - var1.zCoord;
      double var12 = var8 * var8 + var10 * var10;
      if (var12 < 1.0E-8D) {
         return false;
      } else {
         double var14 = 1.0D / Math.sqrt(var12);
         var8 *= var14;
         var10 *= var14;
         var3 += 2;
         var5 += 2;
         if (!this.func_179683_a(var6, (int)var1.yCoord, var7, var3, var4, var5, var1, var8, var10)) {
            return false;
         } else {
            var3 -= 2;
            var5 -= 2;
            double var16 = 1.0D / Math.abs(var8);
            double var18 = 1.0D / Math.abs(var10);
            double var20 = (double)(var6 * 1) - var1.xCoord;
            double var22 = (double)(var7 * 1) - var1.zCoord;
            if (var8 >= 0.0D) {
               ++var20;
            }

            if (var10 >= 0.0D) {
               ++var22;
            }

            var20 /= var8;
            var22 /= var10;
            int var24 = var8 < 0.0D ? -1 : 1;
            int var25 = var10 < 0.0D ? -1 : 1;
            int var26 = MathHelper.floor_double(var2.xCoord);
            int var27 = MathHelper.floor_double(var2.zCoord);
            int var28 = var26 - var6;
            int var29 = var27 - var7;

            do {
               if (var28 * var24 <= 0 && var29 * var25 <= 0) {
                  return true;
               }

               if (var20 < var22) {
                  var20 += var16;
                  var6 += var24;
                  var28 = var26 - var6;
               } else {
                  var22 += var18;
                  var7 += var25;
                  var29 = var27 - var7;
               }
            } while(this.func_179683_a(var6, (int)var1.yCoord, var7, var3, var4, var5, var1, var8, var10));

            return false;
         }
      }
   }

   protected void removeSunnyPath() {
      super.removeSunnyPath();
      if (this.field_179694_f) {
         if (this.worldObj.isAgainstSky(new BlockPos(MathHelper.floor_double(this.theEntity.posX), (int)(this.theEntity.getEntityBoundingBox().minY + 0.5D), MathHelper.floor_double(this.theEntity.posZ)))) {
            return;
         }

         for(int var1 = 0; var1 < this.currentPath.getCurrentPathLength(); ++var1) {
            PathPoint var2 = this.currentPath.getPathPointFromIndex(var1);
            if (this.worldObj.isAgainstSky(new BlockPos(var2.xCoord, var2.yCoord, var2.zCoord))) {
               this.currentPath.setCurrentPathLength(var1 - 1);
               return;
            }
         }
      }

   }

   private int func_179687_p() {
      if (this.theEntity.isInWater() && this.func_179684_h()) {
         int var1 = (int)this.theEntity.getEntityBoundingBox().minY;
         Block var2 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.theEntity.posX), var1, MathHelper.floor_double(this.theEntity.posZ))).getBlock();
         int var3 = 0;

         do {
            if (var2 != Blocks.flowing_water && var2 != Blocks.water) {
               return var1;
            }

            ++var1;
            var2 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.theEntity.posX), var1, MathHelper.floor_double(this.theEntity.posZ))).getBlock();
            ++var3;
         } while(var3 <= 16);

         return (int)this.theEntity.getEntityBoundingBox().minY;
      } else {
         return (int)(this.theEntity.getEntityBoundingBox().minY + 0.5D);
      }
   }

   public void func_179693_d(boolean var1) {
      this.field_179695_a.func_176178_d(var1);
   }

   public void func_179688_b(boolean var1) {
      this.field_179695_a.func_176172_b(var1);
   }

   public boolean func_179684_h() {
      return this.field_179695_a.func_176174_d();
   }

   protected boolean canNavigate() {
      return this.theEntity.onGround || this.func_179684_h() && this.isInLiquid() || this.theEntity.isRiding() && this.theEntity instanceof EntityZombie && this.theEntity.ridingEntity instanceof EntityChicken;
   }

   public boolean func_179689_e() {
      return this.field_179695_a.func_176173_e();
   }
}
