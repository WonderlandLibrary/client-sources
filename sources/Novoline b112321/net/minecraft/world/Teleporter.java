package net.minecraft.world;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern$PatternHelper;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.util.EnumFacing$Axis;
import net.minecraft.util.EnumFacing$AxisDirection;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.Teleporter$PortalPosition;
import net.minecraft.world.WorldServer;

public class Teleporter {
   private final WorldServer worldServerInstance;
   private final Random random;
   private final LongHashMap destinationCoordinateCache = new LongHashMap();
   private final List destinationCoordinateKeys = Lists.newArrayList();

   public Teleporter(WorldServer var1) {
      this.worldServerInstance = var1;
      this.random = new Random(var1.getSeed());
   }

   public void placeInPortal(Entity var1, float var2) {
      if(this.worldServerInstance.provider.getDimensionId() != 1) {
         if(!this.placeInExistingPortal(var1, var2)) {
            this.makePortal(var1);
            this.placeInExistingPortal(var1, var2);
         }
      } else {
         int var3 = MathHelper.floor_double(var1.posX);
         int var4 = MathHelper.floor_double(var1.posY) - 1;
         int var5 = MathHelper.floor_double(var1.posZ);
         byte var6 = 1;
         byte var7 = 0;

         for(int var8 = -2; var8 <= 2; ++var8) {
            for(int var9 = -2; var9 <= 2; ++var9) {
               for(int var10 = -1; var10 < 3; ++var10) {
                  int var11 = var3 + var9 * var6 + var8 * var7;
                  int var12 = var4 + var10;
                  int var13 = var5 + var9 * var7 - var8 * var6;
                  boolean var14 = true;
                  this.worldServerInstance.setBlockState(new BlockPos(var11, var12, var13), Blocks.obsidian.getDefaultState());
               }
            }
         }

         var1.setLocationAndAngles((double)var3, (double)var4, (double)var5, var1.rotationYaw, 0.0F);
         var1.motionX = var1.motionY = var1.motionZ = 0.0D;
      }

   }

   public boolean placeInExistingPortal(Entity var1, float var2) {
      boolean var3 = true;
      double var4 = -1.0D;
      int var6 = MathHelper.floor_double(var1.posX);
      int var7 = MathHelper.floor_double(var1.posZ);
      boolean var8 = true;
      Object var9 = BlockPos.ORIGIN;
      long var10 = ChunkCoordIntPair.chunkXZ2Int(var6, var7);
      if(this.destinationCoordinateCache.containsItem(var10)) {
         Teleporter$PortalPosition var12 = (Teleporter$PortalPosition)this.destinationCoordinateCache.getValueByKey(var10);
         var4 = 0.0D;
         var9 = var12;
         var12.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
         var8 = false;
      } else {
         BlockPos var31 = new BlockPos(var1);

         for(int var13 = -128; var13 <= 128; ++var13) {
            BlockPos var14;
            for(int var15 = -128; var15 <= 128; ++var15) {
               for(BlockPos var16 = var31.a(var13, this.worldServerInstance.getActualHeight() - 1 - var31.getY(), var15); var16.getY() >= 0; var16 = var14) {
                  var14 = var16.down();
                  if(this.worldServerInstance.getBlockState(var16).getBlock() == Blocks.portal) {
                     while(this.worldServerInstance.getBlockState(var14 = var16.down()).getBlock() == Blocks.portal) {
                        var16 = var14;
                     }

                     double var17 = var16.distanceSq(var31);
                     if(var4 < 0.0D || var17 < var4) {
                        var4 = var17;
                        var9 = var16;
                     }
                  }
               }
            }
         }
      }

      if(var4 >= 0.0D) {
         this.destinationCoordinateCache.add(var10, new Teleporter$PortalPosition(this, (BlockPos)var9, this.worldServerInstance.getTotalWorldTime()));
         this.destinationCoordinateKeys.add(Long.valueOf(var10));
         double var32 = (double)((BlockPos)var9).getX() + 0.5D;
         double var33 = (double)((BlockPos)var9).getY() + 0.5D;
         double var35 = (double)((BlockPos)var9).getZ() + 0.5D;
         BlockPattern$PatternHelper var18 = Blocks.portal.func_181089_f(this.worldServerInstance, (BlockPos)var9);
         boolean var19 = var18.getFinger().rotateY().getAxisDirection() == EnumFacing$AxisDirection.NEGATIVE;
         double var20 = var18.getFinger().getAxis() == EnumFacing$Axis.X?(double)var18.func_181117_a().getZ():(double)var18.func_181117_a().getX();
         var33 = (double)(var18.func_181117_a().getY() + 1) - var1.func_181014_aG().yCoord * (double)var18.func_181119_e();
         ++var20;
         if(var18.getFinger().getAxis() == EnumFacing$Axis.X) {
            var35 = var20 + (1.0D - var1.func_181014_aG().xCoord) * (double)var18.func_181118_d() * (double)var18.getFinger().rotateY().getAxisDirection().getOffset();
         } else {
            var32 = var20 + (1.0D - var1.func_181014_aG().xCoord) * (double)var18.func_181118_d() * (double)var18.getFinger().rotateY().getAxisDirection().getOffset();
         }

         float var22 = 0.0F;
         float var23 = 0.0F;
         float var24 = 0.0F;
         float var25 = 0.0F;
         if(var18.getFinger().getOpposite() == var1.func_181012_aH()) {
            var22 = 1.0F;
            var23 = 1.0F;
         } else if(var18.getFinger().getOpposite() == var1.func_181012_aH().getOpposite()) {
            var22 = -1.0F;
            var23 = -1.0F;
         } else if(var18.getFinger().getOpposite() == var1.func_181012_aH().rotateY()) {
            var24 = 1.0F;
            var25 = -1.0F;
         } else {
            var24 = -1.0F;
            var25 = 1.0F;
         }

         double var26 = var1.motionX;
         double var28 = var1.motionZ;
         var1.motionX = var26 * (double)var22 + var28 * (double)var25;
         var1.motionZ = var26 * (double)var24 + var28 * (double)var23;
         var1.rotationYaw = var2 - (float)(var1.func_181012_aH().getOpposite().getHorizontalIndex() * 90) + (float)(var18.getFinger().getHorizontalIndex() * 90);
         var1.setLocationAndAngles(var32, var33, var35, var1.rotationYaw, var1.rotationPitch);
         return true;
      } else {
         return false;
      }
   }

   public boolean makePortal(Entity var1) {
      byte var2 = 16;
      double var3 = -1.0D;
      int var5 = MathHelper.floor_double(var1.posX);
      int var6 = MathHelper.floor_double(var1.posY);
      int var7 = MathHelper.floor_double(var1.posZ);
      int var11 = 0;
      int var12 = this.random.nextInt(4);
      BlockPos$MutableBlockPos var13 = new BlockPos$MutableBlockPos();

      for(int var14 = var5 - var2; var14 <= var5 + var2; ++var14) {
         double var15 = (double)var14 + 0.5D - var1.posX;
         int var17 = var7 - var2;
         if(var17 <= var7 + var2) {
            double var18 = (double)var17 + 0.5D - var1.posZ;
            int var20 = this.worldServerInstance.getActualHeight() - 1;

            while(true) {
               if(this.worldServerInstance.isAirBlock(var13.func_181079_c(var14, var20, var17))) {
                  while(this.worldServerInstance.isAirBlock(var13.func_181079_c(var14, var20 - 1, var17))) {
                     --var20;
                  }

                  label206:
                  for(int var21 = var12; var21 < var12 + 4; ++var21) {
                     int var22 = var21 % 2;
                     int var23 = 1 - var22;
                     if(var21 % 4 >= 2) {
                        var22 = -var22;
                        var23 = -var23;
                     }

                     for(int var24 = 0; var24 < 3; ++var24) {
                        for(int var25 = 0; var25 < 4; ++var25) {
                           for(int var26 = -1; var26 < 4; ++var26) {
                              int var27 = var14 + (var25 - 1) * var22 + var24 * var23;
                              int var28 = var20 + var26;
                              int var29 = var17 + (var25 - 1) * var23 - var24 * var22;
                              var13.func_181079_c(var27, var28, var29);
                              if(!this.worldServerInstance.getBlockState(var13).getBlock().getMaterial().isSolid() || !this.worldServerInstance.isAirBlock(var13)) {
                                 break label206;
                              }
                           }
                        }
                     }

                     double var56 = (double)var20 + 0.5D - var1.posY;
                     double var66 = var15 * var15 + var56 * var56 + var18 * var18;
                     if(var3 < 0.0D || var66 < var3) {
                        var3 = var66;
                        var11 = var21 % 4;
                     }
                  }
               }

               --var20;
            }
         }
      }

      if(var3 < 0.0D) {
         for(int var32 = var5 - var2; var32 <= var5 + var2; ++var32) {
            double var34 = (double)var32 + 0.5D - var1.posX;
            int var36 = var7 - var2;
            if(var36 <= var7 + var2) {
               double var38 = (double)var36 + 0.5D - var1.posZ;
               int var41 = this.worldServerInstance.getActualHeight() - 1;

               while(true) {
                  if(this.worldServerInstance.isAirBlock(var13.func_181079_c(var32, var41, var36))) {
                     while(this.worldServerInstance.isAirBlock(var13.func_181079_c(var32, var41 - 1, var36))) {
                        --var41;
                     }

                     label609:
                     for(int var44 = var12; var44 < var12 + 2; ++var44) {
                        int var48 = var44 % 2;
                        int var52 = 1 - var48;

                        for(int var57 = 0; var57 < 4; ++var57) {
                           for(int var62 = -1; var62 < 4; ++var62) {
                              int var67 = var32 + (var57 - 1) * var48;
                              int var71 = var41 + var62;
                              int var72 = var36 + (var57 - 1) * var52;
                              var13.func_181079_c(var67, var71, var72);
                              if(!this.worldServerInstance.getBlockState(var13).getBlock().getMaterial().isSolid() || !this.worldServerInstance.isAirBlock(var13)) {
                                 break label609;
                              }
                           }
                        }

                        double var58 = (double)var41 + 0.5D - var1.posY;
                        double var68 = var34 * var34 + var58 * var58 + var38 * var38;
                        if(var3 < 0.0D || var68 < var3) {
                           var3 = var68;
                           var11 = var44 % 2;
                        }
                     }
                  }

                  --var41;
               }
            }
         }
      }

      int var33 = var5;
      int var35 = var6;
      int var16 = var7;
      int var37 = var11 % 2;
      int var39 = 1 - var37;
      if(var11 % 4 >= 2) {
         var37 = -var37;
         var39 = -var39;
      }

      if(var3 < 0.0D) {
         int var9 = MathHelper.clamp_int(var6, 70, this.worldServerInstance.getActualHeight() - 10);
         var35 = var9;

         for(int var19 = -1; var19 <= 1; ++var19) {
            for(int var42 = 1; var42 < 3; ++var42) {
               for(int var45 = -1; var45 < 3; ++var45) {
                  int var49 = var33 + (var42 - 1) * var37 + var19 * var39;
                  int var53 = var35 + var45;
                  int var59 = var16 + (var42 - 1) * var39 - var19 * var37;
                  boolean var63 = true;
                  this.worldServerInstance.setBlockState(new BlockPos(var49, var53, var59), Blocks.obsidian.getDefaultState());
               }
            }
         }
      }

      IBlockState var40 = Blocks.portal.getDefaultState().withProperty(BlockPortal.AXIS, EnumFacing$Axis.X);

      for(int var43 = 0; var43 < 4; ++var43) {
         for(int var46 = 0; var46 < 4; ++var46) {
            for(int var50 = -1; var50 < 4; ++var50) {
               int var54 = var33 + (var46 - 1) * var37;
               int var60 = var35 + var50;
               int var64 = var16 + (var46 - 1) * var39;
               boolean var69 = var46 == 3 || var50 == -1 || var50 == 3;
               this.worldServerInstance.setBlockState(new BlockPos(var54, var60, var64), Blocks.obsidian.getDefaultState(), 2);
            }
         }

         for(int var47 = 0; var47 < 4; ++var47) {
            for(int var51 = -1; var51 < 4; ++var51) {
               int var55 = var33 + (var47 - 1) * var37;
               int var61 = var35 + var51;
               int var65 = var16 + (var47 - 1) * var39;
               BlockPos var70 = new BlockPos(var55, var61, var65);
               this.worldServerInstance.notifyNeighborsOfStateChange(var70, this.worldServerInstance.getBlockState(var70).getBlock());
            }
         }
      }

      return true;
   }

   public void removeStalePortalLocations(long var1) {
      if(var1 % 100L == 0L) {
         Iterator var3 = this.destinationCoordinateKeys.iterator();
         long var4 = var1 - 300L;

         while(var3.hasNext()) {
            Long var6 = (Long)var3.next();
            Teleporter$PortalPosition var7 = (Teleporter$PortalPosition)this.destinationCoordinateCache.getValueByKey(var6.longValue());
            if(var7.lastUpdateTime < var4) {
               var3.remove();
               this.destinationCoordinateCache.remove(var6.longValue());
            }
         }
      }

   }
}
