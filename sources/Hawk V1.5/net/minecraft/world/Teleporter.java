package net.minecraft.world;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;

public class Teleporter {
   private static final String __OBFID = "CL_00000153";
   private final List destinationCoordinateKeys = Lists.newArrayList();
   private final WorldServer worldServerInstance;
   private final LongHashMap destinationCoordinateCache = new LongHashMap();
   private final Random random;

   public boolean func_180620_b(Entity var1, float var2) {
      boolean var3 = true;
      double var4 = -1.0D;
      int var6 = MathHelper.floor_double(var1.posX);
      int var7 = MathHelper.floor_double(var1.posZ);
      boolean var8 = true;
      Object var9 = BlockPos.ORIGIN;
      long var10 = ChunkCoordIntPair.chunkXZ2Int(var6, var7);
      if (this.destinationCoordinateCache.containsItem(var10)) {
         Teleporter.PortalPosition var12 = (Teleporter.PortalPosition)this.destinationCoordinateCache.getValueByKey(var10);
         var4 = 0.0D;
         var9 = var12;
         var12.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
         var8 = false;
      } else {
         BlockPos var34 = new BlockPos(var1);

         for(int var13 = -128; var13 <= 128; ++var13) {
            BlockPos var14;
            for(int var15 = -128; var15 <= 128; ++var15) {
               for(BlockPos var16 = var34.add(var13, this.worldServerInstance.getActualHeight() - 1 - var34.getY(), var15); var16.getY() >= 0; var16 = var14) {
                  var14 = var16.offsetDown();
                  if (this.worldServerInstance.getBlockState(var16).getBlock() == Blocks.portal) {
                     while(this.worldServerInstance.getBlockState(var14 = var16.offsetDown()).getBlock() == Blocks.portal) {
                        var16 = var14;
                     }

                     double var17 = var16.distanceSq(var34);
                     if (var4 < 0.0D || var17 < var4) {
                        var4 = var17;
                        var9 = var16;
                     }
                  }
               }
            }
         }
      }

      if (var4 >= 0.0D) {
         if (var8) {
            this.destinationCoordinateCache.add(var10, new Teleporter.PortalPosition(this, (BlockPos)var9, this.worldServerInstance.getTotalWorldTime()));
            this.destinationCoordinateKeys.add(var10);
         }

         double var35 = (double)((BlockPos)var9).getX() + 0.5D;
         double var36 = (double)((BlockPos)var9).getY() + 0.5D;
         double var37 = (double)((BlockPos)var9).getZ() + 0.5D;
         EnumFacing var18 = null;
         if (this.worldServerInstance.getBlockState(((BlockPos)var9).offsetWest()).getBlock() == Blocks.portal) {
            var18 = EnumFacing.NORTH;
         }

         if (this.worldServerInstance.getBlockState(((BlockPos)var9).offsetEast()).getBlock() == Blocks.portal) {
            var18 = EnumFacing.SOUTH;
         }

         if (this.worldServerInstance.getBlockState(((BlockPos)var9).offsetNorth()).getBlock() == Blocks.portal) {
            var18 = EnumFacing.EAST;
         }

         if (this.worldServerInstance.getBlockState(((BlockPos)var9).offsetSouth()).getBlock() == Blocks.portal) {
            var18 = EnumFacing.WEST;
         }

         EnumFacing var19 = EnumFacing.getHorizontal(var1.getTeleportDirection());
         if (var18 != null) {
            EnumFacing var20 = var18.rotateYCCW();
            BlockPos var21 = ((BlockPos)var9).offset(var18);
            boolean var22 = this.func_180265_a(var21);
            boolean var23 = this.func_180265_a(var21.offset(var20));
            if (var23 && var22) {
               var9 = ((BlockPos)var9).offset(var20);
               var18 = var18.getOpposite();
               var20 = var20.getOpposite();
               BlockPos var24 = ((BlockPos)var9).offset(var18);
               var22 = this.func_180265_a(var24);
               var23 = this.func_180265_a(var24.offset(var20));
            }

            float var38 = 0.5F;
            float var25 = 0.5F;
            if (!var23 && var22) {
               var38 = 1.0F;
            } else if (var23 && !var22) {
               var38 = 0.0F;
            } else if (var23) {
               var25 = 0.0F;
            }

            var35 = (double)((BlockPos)var9).getX() + 0.5D;
            var36 = (double)((BlockPos)var9).getY() + 0.5D;
            var37 = (double)((BlockPos)var9).getZ() + 0.5D;
            var35 += (double)((float)var20.getFrontOffsetX() * var38 + (float)var18.getFrontOffsetX() * var25);
            var37 += (double)((float)var20.getFrontOffsetZ() * var38 + (float)var18.getFrontOffsetZ() * var25);
            float var26 = 0.0F;
            float var27 = 0.0F;
            float var28 = 0.0F;
            float var29 = 0.0F;
            if (var18 == var19) {
               var26 = 1.0F;
               var27 = 1.0F;
            } else if (var18 == var19.getOpposite()) {
               var26 = -1.0F;
               var27 = -1.0F;
            } else if (var18 == var19.rotateY()) {
               var28 = 1.0F;
               var29 = -1.0F;
            } else {
               var28 = -1.0F;
               var29 = 1.0F;
            }

            double var30 = var1.motionX;
            double var32 = var1.motionZ;
            var1.motionX = var30 * (double)var26 + var32 * (double)var29;
            var1.motionZ = var30 * (double)var28 + var32 * (double)var27;
            var1.rotationYaw = var2 - (float)(var19.getHorizontalIndex() * 90) + (float)(var18.getHorizontalIndex() * 90);
         } else {
            var1.motionX = var1.motionY = var1.motionZ = 0.0D;
         }

         var1.setLocationAndAngles(var35, var36, var37, var1.rotationYaw, var1.rotationPitch);
         return true;
      } else {
         return false;
      }
   }

   private boolean func_180265_a(BlockPos var1) {
      return !this.worldServerInstance.isAirBlock(var1) || !this.worldServerInstance.isAirBlock(var1.offsetUp());
   }

   public void removeStalePortalLocations(long var1) {
      if (var1 % 100L == 0L) {
         Iterator var3 = this.destinationCoordinateKeys.iterator();
         long var4 = var1 - 600L;

         while(true) {
            Long var6;
            Teleporter.PortalPosition var7;
            do {
               if (!var3.hasNext()) {
                  return;
               }

               var6 = (Long)var3.next();
               var7 = (Teleporter.PortalPosition)this.destinationCoordinateCache.getValueByKey(var6);
            } while(var7 != null && var7.lastUpdateTime >= var4);

            var3.remove();
            this.destinationCoordinateCache.remove(var6);
         }
      }
   }

   public boolean makePortal(Entity var1) {
      byte var2 = 16;
      double var3 = -1.0D;
      int var5 = MathHelper.floor_double(var1.posX);
      int var6 = MathHelper.floor_double(var1.posY);
      int var7 = MathHelper.floor_double(var1.posZ);
      int var8 = var5;
      int var9 = var6;
      int var10 = var7;
      int var11 = 0;
      int var12 = this.random.nextInt(4);

      int var13;
      double var14;
      int var16;
      double var17;
      int var19;
      int var20;
      int var21;
      int var22;
      int var23;
      int var24;
      int var25;
      int var26;
      int var27;
      double var28;
      double var30;
      int var32;
      for(var13 = var5 - var2; var13 <= var5 + var2; ++var13) {
         var14 = (double)var13 + 0.5D - var1.posX;

         for(var16 = var7 - var2; var16 <= var7 + var2; ++var16) {
            var17 = (double)var16 + 0.5D - var1.posZ;

            label370:
            for(var19 = this.worldServerInstance.getActualHeight() - 1; var19 >= 0; --var19) {
               if (this.worldServerInstance.isAirBlock(new BlockPos(var13, var19, var16))) {
                  while(var19 > 0 && this.worldServerInstance.isAirBlock(new BlockPos(var13, var19 - 1, var16))) {
                     --var19;
                  }

                  for(var20 = var12; var20 < var12 + 4; ++var20) {
                     var21 = var20 % 2;
                     var22 = 1 - var21;
                     if (var20 % 4 >= 2) {
                        var21 = -var21;
                        var22 = -var22;
                     }

                     for(var23 = 0; var23 < 3; ++var23) {
                        for(var24 = 0; var24 < 4; ++var24) {
                           for(var25 = -1; var25 < 4; ++var25) {
                              var26 = var13 + (var24 - 1) * var21 + var23 * var22;
                              var27 = var19 + var25;
                              var32 = var16 + (var24 - 1) * var22 - var23 * var21;
                              if (var25 < 0 && !this.worldServerInstance.getBlockState(new BlockPos(var26, var27, var32)).getBlock().getMaterial().isSolid() || var25 >= 0 && !this.worldServerInstance.isAirBlock(new BlockPos(var26, var27, var32))) {
                                 continue label370;
                              }
                           }
                        }
                     }

                     var28 = (double)var19 + 0.5D - var1.posY;
                     var30 = var14 * var14 + var28 * var28 + var17 * var17;
                     if (var3 < 0.0D || var30 < var3) {
                        var3 = var30;
                        var8 = var13;
                        var9 = var19;
                        var10 = var16;
                        var11 = var20 % 4;
                     }
                  }
               }
            }
         }
      }

      if (var3 < 0.0D) {
         for(var13 = var5 - var2; var13 <= var5 + var2; ++var13) {
            var14 = (double)var13 + 0.5D - var1.posX;

            for(var16 = var7 - var2; var16 <= var7 + var2; ++var16) {
               var17 = (double)var16 + 0.5D - var1.posZ;

               label308:
               for(var19 = this.worldServerInstance.getActualHeight() - 1; var19 >= 0; --var19) {
                  if (this.worldServerInstance.isAirBlock(new BlockPos(var13, var19, var16))) {
                     while(var19 > 0 && this.worldServerInstance.isAirBlock(new BlockPos(var13, var19 - 1, var16))) {
                        --var19;
                     }

                     for(var20 = var12; var20 < var12 + 2; ++var20) {
                        var21 = var20 % 2;
                        var22 = 1 - var21;

                        for(var23 = 0; var23 < 4; ++var23) {
                           for(var24 = -1; var24 < 4; ++var24) {
                              var25 = var13 + (var23 - 1) * var21;
                              var26 = var19 + var24;
                              var27 = var16 + (var23 - 1) * var22;
                              if (var24 < 0 && !this.worldServerInstance.getBlockState(new BlockPos(var25, var26, var27)).getBlock().getMaterial().isSolid() || var24 >= 0 && !this.worldServerInstance.isAirBlock(new BlockPos(var25, var26, var27))) {
                                 continue label308;
                              }
                           }
                        }

                        var28 = (double)var19 + 0.5D - var1.posY;
                        var30 = var14 * var14 + var28 * var28 + var17 * var17;
                        if (var3 < 0.0D || var30 < var3) {
                           var3 = var30;
                           var8 = var13;
                           var9 = var19;
                           var10 = var16;
                           var11 = var20 % 2;
                        }
                     }
                  }
               }
            }
         }
      }

      var32 = var8;
      int var33 = var9;
      var16 = var10;
      int var34 = var11 % 2;
      int var35 = 1 - var34;
      if (var11 % 4 >= 2) {
         var34 = -var34;
         var35 = -var35;
      }

      if (var3 < 0.0D) {
         var9 = MathHelper.clamp_int(var9, 70, this.worldServerInstance.getActualHeight() - 10);
         var33 = var9;

         for(var19 = -1; var19 <= 1; ++var19) {
            for(var20 = 1; var20 < 3; ++var20) {
               for(var21 = -1; var21 < 3; ++var21) {
                  var22 = var32 + (var20 - 1) * var34 + var19 * var35;
                  var23 = var33 + var21;
                  var24 = var16 + (var20 - 1) * var35 - var19 * var34;
                  boolean var36 = var21 < 0;
                  this.worldServerInstance.setBlockState(new BlockPos(var22, var23, var24), var36 ? Blocks.obsidian.getDefaultState() : Blocks.air.getDefaultState());
               }
            }
         }
      }

      IBlockState var38 = Blocks.portal.getDefaultState().withProperty(BlockPortal.field_176550_a, var34 != 0 ? EnumFacing.Axis.X : EnumFacing.Axis.Z);

      for(var20 = 0; var20 < 4; ++var20) {
         for(var21 = 0; var21 < 4; ++var21) {
            for(var22 = -1; var22 < 4; ++var22) {
               var23 = var32 + (var21 - 1) * var34;
               var24 = var33 + var22;
               var25 = var16 + (var21 - 1) * var35;
               boolean var37 = var21 == 0 || var21 == 3 || var22 == -1 || var22 == 3;
               this.worldServerInstance.setBlockState(new BlockPos(var23, var24, var25), var37 ? Blocks.obsidian.getDefaultState() : var38, 2);
            }
         }

         for(var21 = 0; var21 < 4; ++var21) {
            for(var22 = -1; var22 < 4; ++var22) {
               var23 = var32 + (var21 - 1) * var34;
               var24 = var33 + var22;
               var25 = var16 + (var21 - 1) * var35;
               this.worldServerInstance.notifyNeighborsOfStateChange(new BlockPos(var23, var24, var25), this.worldServerInstance.getBlockState(new BlockPos(var23, var24, var25)).getBlock());
            }
         }
      }

      return true;
   }

   public void func_180266_a(Entity var1, float var2) {
      if (this.worldServerInstance.provider.getDimensionId() != 1) {
         if (!this.func_180620_b(var1, var2)) {
            this.makePortal(var1);
            this.func_180620_b(var1, var2);
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
                  boolean var14 = var10 < 0;
                  this.worldServerInstance.setBlockState(new BlockPos(var11, var12, var13), var14 ? Blocks.obsidian.getDefaultState() : Blocks.air.getDefaultState());
               }
            }
         }

         var1.setLocationAndAngles((double)var3, (double)var4, (double)var5, var1.rotationYaw, 0.0F);
         var1.motionX = var1.motionY = var1.motionZ = 0.0D;
      }

   }

   public Teleporter(WorldServer var1) {
      this.worldServerInstance = var1;
      this.random = new Random(var1.getSeed());
   }

   public class PortalPosition extends BlockPos {
      public long lastUpdateTime;
      private static final String __OBFID = "CL_00000154";
      final Teleporter this$0;

      public PortalPosition(Teleporter var1, BlockPos var2, long var3) {
         super(var2.getX(), var2.getY(), var2.getZ());
         this.this$0 = var1;
         this.lastUpdateTime = var3;
      }
   }
}
