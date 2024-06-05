package cc.novoline.utils.pathfinding;

import cc.novoline.utils.pathfinding.AStarCustomPathfinder;
import cc.novoline.utils.pathfinding.Point;
import cc.novoline.utils.pathfinding.Vec3;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public final class PathfinderAI {
   private static int lastPosX;
   private static int lastPosZ;

   private static int getDiffX(Entity var0, Entity var1) {
      return (int)Math.abs(var0.posX - var1.posX);
   }

   private static int getDiffZ(Entity var0, Entity var1) {
      return (int)Math.abs(var0.posZ - var1.posZ);
   }

   private static int getDiffX(BlockPos var0, BlockPos var1) {
      return var0.getX() - var1.getX();
   }

   private static int getDiffZ(BlockPos var0, BlockPos var1) {
      return var0.getZ() - var1.getZ();
   }

   private static int getDiffY(BlockPos var0, BlockPos var1) {
      return var0.getY() - var1.getY();
   }

   public static List getPath(Entity var0, Entity var1) {
      ObjectArrayList var3 = new ObjectArrayList();
      double var4 = 2.0D;
      double var6 = Math.pow(Math.pow(var1.posX - var0.posX, 2.0D) + Math.pow(var1.posY - var0.posY, 2.0D) + Math.pow(var1.posZ - var0.posZ, 2.0D), 0.5D);
      double var8 = var6 / var4;
      double var10 = (var1.posX - var0.posX) / var8;
      double var12 = (var1.posY - var0.posY) / var8;
      Vec3.b();
      double var14 = (var1.posZ - var0.posZ) / var8;
      double var16 = 0.0D;
      double var18 = 0.0D;
      double var20 = 0.0D;
      double var22 = var8--;
      if(var22 <= 0.0D) {
         ;
      }

      Point var24 = new Point((int)(var0.posX + var16 + var10), (int)(var0.posY + var18 + var12), (int)(var0.posZ + var20 + var14));
      var3.add(var24);
      return (List)var3.stream().distinct().collect(Collectors.toCollection(ObjectArrayList::<init>));
   }

   public static List getPathFromPosToPos(BlockPos var0, BlockPos var1) {
      Vec3.b();
      ObjectArrayList var3 = new ObjectArrayList();
      double var4 = 10.0D;
      if(getDiffX(var0, var1) > 0) {
         int var6 = 0;
         if(var6 <= getDiffX(var0, var1) && !isPointNotOkay(new Point(var0.getX() - var6, var0.getY(), var0.getZ()))) {
            if((double)var6 % var4 == 0.0D) {
               var3.add(new Point(var0.getX() - var6, var0.getY(), var0.getZ()));
               if((double)var6 == (double)getDiffX(var0, var1) - ((double)getDiffX(var0, var1) - (double)((int)((double)getDiffX(var0, var1) / var4)) * var4)) {
                  if(getDiffZ(var0, var1) > 0) {
                     int var7 = 0;
                     if(var7 <= getDiffZ(var0, var1) && !isPointNotOkay(new Point(var0.getX() - var6, var0.getY(), var0.getZ() - var7))) {
                        if((double)var7 % var4 == 0.0D) {
                           var3.add(new Point(var0.getX() - var6, var0.getY(), var0.getZ() - var7));
                           if((double)var7 == (double)getDiffZ(var0, var1) - ((double)getDiffZ(var0, var1) - (double)((int)((double)getDiffZ(var0, var1) / var4)) * var4)) {
                              if(getDiffY(var0, var1) > 0) {
                                 int var8 = 0;
                                 if(var8 <= getDiffY(var0, var1)) {
                                    if(isPointNotOkay(new Point(var0.getX() - var6, var0.getY() - var8, var0.getZ() - var7))) {
                                       ;
                                    }

                                    if(var8 % 2 == 0) {
                                       var3.add(new Point(var0.getX() - var6, var0.getY() - var8, var0.getZ() - var7));
                                    }

                                    ++var8;
                                 }
                              }

                              int var20 = 0;
                              if(var20 >= getDiffY(var0, var1)) {
                                 if(isPointNotOkay(new Point(var0.getX() - var6, var0.getY() - var20, var0.getZ() - var7))) {
                                    ;
                                 }

                                 if(var20 % 2 == 0) {
                                    var3.add(new Point(var0.getX() - var6, var0.getY() - var20, var0.getZ() - var7));
                                 }

                                 --var20;
                              }
                           }
                        }

                        ++var7;
                     }
                  }

                  int var13 = 0;
                  if(var13 >= getDiffZ(var0, var1) && !isPointNotOkay(new Point(var0.getX() - var6, var0.getY(), var0.getZ() - var13))) {
                     if((double)var13 % var4 == 0.0D) {
                        var3.add(new Point(var0.getX() - var6, var0.getY(), var0.getZ() - var13));
                        if((double)var13 == (double)getDiffZ(var0, var1) - ((double)getDiffZ(var0, var1) - (double)((int)((double)getDiffZ(var0, var1) / var4)) * var4)) {
                           if(getDiffY(var0, var1) > 0) {
                              int var22 = 0;
                              if(var22 <= getDiffY(var0, var1)) {
                                 if(isPointNotOkay(new Point(var0.getX() - var6, var0.getY() - var22, var0.getZ() - var13))) {
                                    ;
                                 }

                                 if(var22 % 2 == 0) {
                                    var3.add(new Point(var0.getX() - var6, var0.getY() - var22, var0.getZ() - var13));
                                 }

                                 ++var22;
                              }
                           }

                           int var24 = 0;
                           if(var24 >= getDiffY(var0, var1)) {
                              if(isPointNotOkay(new Point(var0.getX() - var6, var0.getY() - var24, var0.getZ() - var13))) {
                                 ;
                              }

                              if(var24 % 2 == 0) {
                                 var3.add(new Point(var0.getX() - var6, var0.getY() - var24, var0.getZ() - var13));
                              }

                              --var24;
                           }
                        }
                     }

                     --var13;
                  }
               }
            }

            ++var6;
         }
      }

      int var10 = 0;
      if(var10 >= getDiffX(var0, var1) && !isPointNotOkay(new Point(var0.getX() - var10, var0.getY(), var0.getZ()))) {
         if((double)var10 % var4 == 0.0D) {
            var3.add(new Point(var0.getX() - var10, var0.getY(), var0.getZ()));
            if((double)var10 == (double)getDiffX(var0, var1) - ((double)getDiffX(var0, var1) - (double)((int)((double)getDiffX(var0, var1) / var4)) * var4)) {
               if(getDiffZ(var0, var1) > 0) {
                  int var15 = 0;
                  if(var15 <= getDiffZ(var0, var1) && !isPointNotOkay(new Point(var0.getX() - var10, var0.getY(), var0.getZ() - var15))) {
                     if((double)var15 % var4 == 0.0D) {
                        var3.add(new Point(var0.getX() - var10, var0.getY(), var0.getZ() - var15));
                        if((double)var15 == (double)getDiffZ(var0, var1) - ((double)getDiffZ(var0, var1) - (double)((int)((double)getDiffZ(var0, var1) / var4)) * var4)) {
                           if(getDiffY(var0, var1) > 0) {
                              int var26 = 0;
                              if(var26 <= getDiffY(var0, var1)) {
                                 if(isPointNotOkay(new Point(var0.getX() - var10, var0.getY() - var26, var0.getZ() - var15))) {
                                    ;
                                 }

                                 if(var26 % 2 == 0) {
                                    var3.add(new Point(var0.getX() - var10, var0.getY() - var26, var0.getZ() - var15));
                                 }

                                 ++var26;
                              }
                           }

                           int var28 = 0;
                           if(var28 >= getDiffY(var0, var1)) {
                              if(isPointNotOkay(new Point(var0.getX() - var10, var0.getY() - var28, var0.getZ() - var15))) {
                                 ;
                              }

                              if(var28 % 2 == 0) {
                                 var3.add(new Point(var0.getX() - var10, var0.getY() - var28, var0.getZ() - var15));
                              }

                              --var28;
                           }
                        }
                     }

                     ++var15;
                  }
               }

               int var17 = 0;
               if(var17 >= getDiffZ(var0, var1) && !isPointNotOkay(new Point(var0.getX() - var10, var0.getY(), var0.getZ() - var17))) {
                  if((double)var17 % var4 == 0.0D) {
                     var3.add(new Point(var0.getX() - var10, var0.getY(), var0.getZ() - var17));
                     if((double)var17 == (double)getDiffZ(var0, var1) - ((double)getDiffZ(var0, var1) - (double)((int)((double)getDiffZ(var0, var1) / var4)) * var4)) {
                        if(getDiffY(var0, var1) > 0) {
                           int var30 = 0;
                           if(var30 <= getDiffY(var0, var1)) {
                              if(isPointNotOkay(new Point(var0.getX() - var10, var0.getY() - var30, var0.getZ() - var17))) {
                                 ;
                              }

                              if(var30 % 2 == 0) {
                                 var3.add(new Point(var0.getX() - var10, var0.getY() - var30, var0.getZ() - var17));
                              }

                              ++var30;
                           }
                        }

                        int var32 = 0;
                        if(var32 >= getDiffY(var0, var1)) {
                           if(isPointNotOkay(new Point(var0.getX() - var10, var0.getY() - var32, var0.getZ() - var17))) {
                              ;
                           }

                           if(var32 % 2 == 0) {
                              var3.add(new Point(var0.getX() - var10, var0.getY() - var32, var0.getZ() - var17));
                           }

                           --var32;
                        }
                     }
                  }

                  --var17;
               }
            }
         }

         --var10;
      }

      return (List)var3.stream().distinct().collect(Collectors.toCollection(ObjectArrayList::<init>));
   }

   private static boolean isPointNotOkay(Point var0) {
      Vec3.b();
      Block var2 = Minecraft.getInstance().world.getBlock(var0.getPosX(), var0.getPosY(), var0.getPosZ());
      return !(var2 instanceof BlockAir) && !(var2 instanceof BlockGrass) && !(var2 instanceof BlockTallGrass);
   }

   public static boolean canPassThrow(BlockPos var0) {
      Vec3.b();
      Block var2 = Minecraft.getInstance().world.getBlockState(new BlockPos(var0.getX(), var0.getY(), var0.getZ())).getBlock();
      return var2.getMaterial() == Material.air || var2.getMaterial() == Material.plants || var2.getMaterial() == Material.vine || var2 == Blocks.ladder || var2 == Blocks.water || var2 == Blocks.flowing_water || var2 == Blocks.wall_sign || var2 == Blocks.standing_sign;
   }

   public static ArrayList computePath(Vec3 var0, Vec3 var1) {
      String var2 = Vec3.b();
      if(!canPassThrow(new BlockPos(var0.mc()))) {
         var0 = var0.addVector(0.0D, 1.0D, 0.0D);
      }

      AStarCustomPathfinder var3 = new AStarCustomPathfinder(var0, var1);
      var3.compute();
      int var4 = 0;
      Object var5 = null;
      Vec3 var6 = null;
      ArrayList var7 = new ArrayList();
      ArrayList var8 = var3.getPath();
      Iterator var9 = var8.iterator();
      if(var9.hasNext()) {
         Vec3 var10 = (Vec3)var9.next();
         if(var4 == 0 || var4 == var8.size() - 1) {
            if(var5 != null) {
               var7.add(((Vec3)var5).addVector(0.5D, 0.0D, 0.5D));
            }

            var7.add(var10.addVector(0.5D, 0.0D, 0.5D));
            var6 = var10;
         }

         boolean var11 = true;
         if(var10.squareDistanceTo(var6) > 25.0D) {
            var11 = false;
         }

         double var12 = Math.min(var6.getX(), var10.getX());
         double var14 = Math.min(var6.getY(), var10.getY());
         double var16 = Math.min(var6.getZ(), var10.getZ());
         double var18 = Math.max(var6.getX(), var10.getX());
         double var20 = Math.max(var6.getY(), var10.getY());
         double var22 = Math.max(var6.getZ(), var10.getZ());
         int var24 = (int)var12;
         if((double)var24 <= var18) {
            int var25 = (int)var14;
            if((double)var25 <= var20) {
               int var26 = (int)var16;
               if((double)var26 <= var22) {
                  if(!AStarCustomPathfinder.checkPositionValidity(var24, var25, var26, false)) {
                     var11 = false;
                  }

                  ++var26;
               }

               ++var25;
            }

            ++var24;
         }

         if(!var11) {
            var7.add(((Vec3)var5).addVector(0.5D, 0.0D, 0.5D));
         }

         ++var4;
      }

      return var7;
   }
}
