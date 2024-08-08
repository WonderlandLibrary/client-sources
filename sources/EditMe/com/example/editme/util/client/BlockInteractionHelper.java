package com.example.editme.util.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BlockInteractionHelper {
   private static final Minecraft mc;
   public static final List shulkerList;
   public static final List blackList;

   public static float[] getRotationsForPosition(double var0, double var2, double var4) {
      return getRotationsForPosition(var0, var2, var4, mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v);
   }

   public static double[] directionSpeed(double var0) {
      Minecraft var2 = Minecraft.func_71410_x();
      float var3 = var2.field_71439_g.field_71158_b.field_192832_b;
      float var4 = var2.field_71439_g.field_71158_b.field_78902_a;
      float var5 = var2.field_71439_g.field_70126_B + (var2.field_71439_g.field_70177_z - var2.field_71439_g.field_70126_B) * var2.func_184121_ak();
      if (var3 != 0.0F) {
         if (var4 > 0.0F) {
            var5 += (float)(var3 > 0.0F ? -45 : 45);
         } else if (var4 < 0.0F) {
            var5 += (float)(var3 > 0.0F ? 45 : -45);
         }

         var4 = 0.0F;
         if (var3 > 0.0F) {
            var3 = 1.0F;
         } else if (var3 < 0.0F) {
            var3 = -1.0F;
         }
      }

      double var6 = Math.sin(Math.toRadians((double)(var5 + 90.0F)));
      double var8 = Math.cos(Math.toRadians((double)(var5 + 90.0F)));
      double var10 = (double)var3 * var0 * var8 + (double)var4 * var0 * var6;
      double var12 = (double)var3 * var0 * var6 - (double)var4 * var0 * var8;
      return new double[]{var10, var12};
   }

   public static BlockInteractionHelper.PlaceResult place(BlockPos var0, float var1, boolean var2, boolean var3, boolean var4) {
      IBlockState var5 = mc.field_71441_e.func_180495_p(var0);
      boolean var6 = var5.func_185904_a().func_76222_j();
      boolean var7 = var5.func_177230_c() instanceof BlockSlab;
      if (!var6 && !var7) {
         return BlockInteractionHelper.PlaceResult.NotReplaceable;
      } else if (!checkForNeighbours(var0)) {
         return BlockInteractionHelper.PlaceResult.Neighbors;
      } else {
         if (!var7) {
            BlockInteractionHelper.ValidResult var8 = valid(var0);
            if (var8 != BlockInteractionHelper.ValidResult.Ok && !var6) {
               return BlockInteractionHelper.PlaceResult.CantPlace;
            }
         }

         if (var3 && var7 && !var5.func_185917_h()) {
            return BlockInteractionHelper.PlaceResult.CantPlace;
         } else {
            Vec3d var20 = new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v);
            EnumFacing[] var9 = EnumFacing.values();
            int var10 = var9.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               EnumFacing var12 = var9[var11];
               BlockPos var13 = var0.func_177972_a(var12);
               EnumFacing var14 = var12.func_176734_d();
               boolean var15 = mc.field_71441_e.func_180495_p(var13).func_177230_c() == Blocks.field_150355_j;
               if (mc.field_71441_e.func_180495_p(var13).func_177230_c().func_176209_a(mc.field_71441_e.func_180495_p(var13), false)) {
                  Vec3d var16 = (new Vec3d(var13)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(var14.func_176730_m())).func_186678_a(0.5D));
                  if (var20.func_72438_d(var16) <= (double)var1) {
                     Block var17 = mc.field_71441_e.func_180495_p(var13).func_177230_c();
                     boolean var18 = var17.func_180639_a(mc.field_71441_e, var0, mc.field_71441_e.func_180495_p(var0), mc.field_71439_g, EnumHand.MAIN_HAND, var12, 0.0F, 0.0F, 0.0F);
                     if (blackList.contains(var17) || shulkerList.contains(var17) || var18) {
                        mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.START_SNEAKING));
                     }

                     if (var2) {
                        faceVectorPacketInstant(var16);
                     }

                     EnumActionResult var19 = mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, var13, var14, var16, EnumHand.MAIN_HAND);
                     if (var19 != EnumActionResult.FAIL) {
                        if (var4) {
                           mc.field_71439_g.field_71174_a.func_147297_a(new CPacketAnimation(EnumHand.MAIN_HAND));
                        } else {
                           mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                        }

                        if (var18) {
                           mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.STOP_SNEAKING));
                        }

                        return BlockInteractionHelper.PlaceResult.Placed;
                     }
                  }
               }
            }

            return BlockInteractionHelper.PlaceResult.CantPlace;
         }
      }
   }

   public static void placeBlockScaffold(BlockPos var0) {
      Vec3d var1 = new Vec3d(Wrapper.getPlayer().field_70165_t, Wrapper.getPlayer().field_70163_u + (double)Wrapper.getPlayer().func_70047_e(), Wrapper.getPlayer().field_70161_v);
      EnumFacing[] var2 = EnumFacing.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         EnumFacing var5 = var2[var4];
         BlockPos var6 = var0.func_177972_a(var5);
         EnumFacing var7 = var5.func_176734_d();
         if (canBeClicked(var6)) {
            Vec3d var8 = (new Vec3d(var6)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(var7.func_176730_m())).func_186678_a(0.5D));
            if (var1.func_72436_e(var8) <= 18.0625D) {
               faceVectorPacketInstant(var8);
               processRightClickBlock(var6, var7, var8);
               Wrapper.getPlayer().func_184609_a(EnumHand.MAIN_HAND);
               mc.field_71467_ac = 4;
               return;
            }
         }
      }

   }

   public static BlockInteractionHelper.ValidResult valid(BlockPos var0) {
      if (!mc.field_71441_e.func_72855_b(new AxisAlignedBB(var0))) {
         return BlockInteractionHelper.ValidResult.NoEntityCollision;
      } else if (!checkForNeighbours(var0)) {
         return BlockInteractionHelper.ValidResult.NoNeighbors;
      } else {
         IBlockState var1 = mc.field_71441_e.func_180495_p(var0);
         if (var1.func_177230_c() != Blocks.field_150350_a) {
            return BlockInteractionHelper.ValidResult.AlreadyBlockThere;
         } else {
            BlockPos[] var2 = new BlockPos[]{var0.func_177978_c(), var0.func_177968_d(), var0.func_177974_f(), var0.func_177976_e(), var0.func_177984_a(), var0.func_177977_b()};
            BlockPos[] var3 = var2;
            int var4 = var2.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               BlockPos var6 = var3[var5];
               IBlockState var7 = mc.field_71441_e.func_180495_p(var6);
               if (var7.func_177230_c() != Blocks.field_150350_a) {
                  EnumFacing[] var8 = EnumFacing.values();
                  int var9 = var8.length;

                  for(int var10 = 0; var10 < var9; ++var10) {
                     EnumFacing var11 = var8[var10];
                     BlockPos var12 = var0.func_177972_a(var11);
                     boolean var13 = mc.field_71441_e.func_180495_p(var12).func_177230_c() == Blocks.field_150355_j;
                     if (mc.field_71441_e.func_180495_p(var12).func_177230_c().func_176209_a(mc.field_71441_e.func_180495_p(var12), false)) {
                        return BlockInteractionHelper.ValidResult.Ok;
                     }
                  }
               }
            }

            return BlockInteractionHelper.ValidResult.NoNeighbors;
         }
      }
   }

   public static boolean hasNeighbour(BlockPos var0) {
      EnumFacing[] var1 = EnumFacing.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EnumFacing var4 = var1[var3];
         BlockPos var5 = var0.func_177972_a(var4);
         if (!Wrapper.getWorld().func_180495_p(var5).func_185904_a().func_76222_j()) {
            return true;
         }
      }

      return false;
   }

   private static IBlockState getState(BlockPos var0) {
      return Wrapper.getWorld().func_180495_p(var0);
   }

   private static Block getBlock(BlockPos var0) {
      return getState(var0).func_177230_c();
   }

   public static float[] getLegitRotations(Vec3d var0) {
      Vec3d var1 = getEyesPos();
      double var2 = var0.field_72450_a - var1.field_72450_a;
      double var4 = var0.field_72448_b - var1.field_72448_b;
      double var6 = var0.field_72449_c - var1.field_72449_c;
      double var8 = Math.sqrt(var2 * var2 + var6 * var6);
      float var10 = (float)Math.toDegrees(Math.atan2(var6, var2)) - 90.0F;
      float var11 = (float)(-Math.toDegrees(Math.atan2(var4, var8)));
      return new float[]{Wrapper.getPlayer().field_70177_z + MathHelper.func_76142_g(var10 - Wrapper.getPlayer().field_70177_z), Wrapper.getPlayer().field_70125_A + MathHelper.func_76142_g(var11 - Wrapper.getPlayer().field_70125_A)};
   }

   public static EnumFacing getPlaceableSide(BlockPos var0) {
      EnumFacing[] var1 = EnumFacing.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EnumFacing var4 = var1[var3];
         BlockPos var5 = var0.func_177972_a(var4);
         if (mc.field_71441_e.func_180495_p(var5).func_177230_c().func_176209_a(mc.field_71441_e.func_180495_p(var5), false)) {
            IBlockState var6 = mc.field_71441_e.func_180495_p(var5);
            if (!var6.func_185904_a().func_76222_j()) {
               return var4;
            }
         }
      }

      return null;
   }

   public static BlockInteractionHelper.PlaceResult place(BlockPos var0, float var1, boolean var2, boolean var3) {
      return place(var0, var1, var2, var3, false);
   }

   public static boolean checkForNeighbours(BlockPos var0) {
      if (!hasNeighbour(var0)) {
         EnumFacing[] var1 = EnumFacing.values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            EnumFacing var4 = var1[var3];
            BlockPos var5 = var0.func_177972_a(var4);
            if (hasNeighbour(var5)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public static float[] getFacingRotations(int var0, int var1, int var2, EnumFacing var3) {
      return getFacingRotations(var0, var1, var2, var3, 1.0D);
   }

   private static PlayerControllerMP getPlayerController() {
      return Minecraft.func_71410_x().field_71442_b;
   }

   public static float wrapAngleTo180(float var0) {
      for(var0 %= 360.0F; var0 >= 180.0F; var0 -= 360.0F) {
      }

      while(var0 < -180.0F) {
         var0 += 360.0F;
      }

      return var0;
   }

   static {
      blackList = Arrays.asList(Blocks.field_150477_bB, Blocks.field_150486_ae, Blocks.field_150447_bR, Blocks.field_150462_ai, Blocks.field_150467_bQ, Blocks.field_150382_bo, Blocks.field_150438_bZ, Blocks.field_150409_cd, Blocks.field_150367_z, Blocks.field_150415_aT, Blocks.field_150381_bn);
      shulkerList = Arrays.asList(Blocks.field_190977_dl, Blocks.field_190978_dm, Blocks.field_190979_dn, Blocks.field_190980_do, Blocks.field_190981_dp, Blocks.field_190982_dq, Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA);
      mc = Minecraft.func_71410_x();
   }

   public static void faceVectorPacketInstant(Vec3d var0) {
      float[] var1 = getLegitRotations(var0);
      Wrapper.getPlayer().field_71174_a.func_147297_a(new Rotation(var1[0], var1[1], Wrapper.getPlayer().field_70122_E));
   }

   private static void processRightClickBlock(BlockPos var0, EnumFacing var1, Vec3d var2) {
      getPlayerController().func_187099_a(Wrapper.getPlayer(), mc.field_71441_e, var0, var1, var2, EnumHand.MAIN_HAND);
   }

   private static Vec3d getEyesPos() {
      return new Vec3d(Wrapper.getPlayer().field_70165_t, Wrapper.getPlayer().field_70163_u + (double)Wrapper.getPlayer().func_70047_e(), Wrapper.getPlayer().field_70161_v);
   }

   public static float[] getFacingRotations(int var0, int var1, int var2, EnumFacing var3, double var4) {
      return getRotationsForPosition((double)var0 + 0.5D + (double)var3.func_176730_m().func_177958_n() * var4 / 2.0D, (double)var1 + 0.5D + (double)var3.func_176730_m().func_177956_o() * var4 / 2.0D, (double)var2 + 0.5D + (double)var3.func_176730_m().func_177952_p() * var4 / 2.0D);
   }

   public static List getSphere(BlockPos var0, float var1, int var2, boolean var3, boolean var4, int var5) {
      ArrayList var6 = new ArrayList();
      int var7 = var0.func_177958_n();
      int var8 = var0.func_177956_o();
      int var9 = var0.func_177952_p();

      for(int var10 = var7 - (int)var1; (float)var10 <= (float)var7 + var1; ++var10) {
         for(int var11 = var9 - (int)var1; (float)var11 <= (float)var9 + var1; ++var11) {
            for(int var12 = var4 ? var8 - (int)var1 : var8; (float)var12 < (var4 ? (float)var8 + var1 : (float)(var8 + var2)); ++var12) {
               double var13 = (double)((var7 - var10) * (var7 - var10) + (var9 - var11) * (var9 - var11) + (var4 ? (var8 - var12) * (var8 - var12) : 0));
               if (var13 < (double)(var1 * var1) && (!var3 || var13 >= (double)((var1 - 1.0F) * (var1 - 1.0F)))) {
                  var6.add(new BlockPos(var10, var12 + var5, var11));
               }
            }
         }
      }

      return var6;
   }

   public static boolean canBeClicked(BlockPos var0) {
      return getBlock(var0).func_176209_a(getState(var0), false);
   }

   public static boolean IsLiquidOrAir(BlockPos var0) {
      IBlockState var1 = mc.field_71441_e.func_180495_p(var0);
      return var1.func_177230_c() == Blocks.field_150355_j || var1.func_177230_c() == Blocks.field_150353_l || var1.func_177230_c() == Blocks.field_150350_a;
   }

   public static float[] getRotationsForPosition(double var0, double var2, double var4, double var6, double var8, double var10) {
      double var12 = var0 - var6;
      double var14 = var2 - var8;
      double var16 = var4 - var10;
      double var18;
      if (var16 < 0.0D && var12 < 0.0D) {
         var18 = 90.0D + Math.toDegrees(Math.atan(var16 / var12));
      } else if (var16 < 0.0D && var12 > 0.0D) {
         var18 = -90.0D + Math.toDegrees(Math.atan(var16 / var12));
      } else {
         var18 = Math.toDegrees(-Math.atan(var12 / var16));
      }

      double var20 = Math.sqrt(var12 * var12 + var16 * var16);
      double var22 = -Math.toDegrees(Math.atan(var14 / var20));
      var18 = (double)wrapAngleTo180((float)var18);
      var22 = (double)wrapAngleTo180((float)var22);
      var18 = Double.isNaN(var18) ? 0.0D : var18;
      var22 = Double.isNaN(var22) ? 0.0D : var22;
      return new float[]{(float)var18, (float)var22};
   }

   public static enum ValidResult {
      NoNeighbors,
      AlreadyBlockThere,
      Ok;

      private static final BlockInteractionHelper.ValidResult[] $VALUES = new BlockInteractionHelper.ValidResult[]{NoEntityCollision, AlreadyBlockThere, NoNeighbors, Ok};
      NoEntityCollision;
   }

   public static enum PlaceResult {
      Placed,
      CantPlace;

      private static final BlockInteractionHelper.PlaceResult[] $VALUES = new BlockInteractionHelper.PlaceResult[]{NotReplaceable, Neighbors, CantPlace, Placed};
      Neighbors,
      NotReplaceable;
   }
}
