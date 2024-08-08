package com.example.editme.util.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.network.play.client.CPacketPlayer.PositionRotation;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PlayerUtil {
   private static Minecraft mc = Minecraft.func_71410_x();

   public static boolean CanSeeBlock(BlockPos var0) {
      if (mc.field_71439_g == null) {
         return false;
      } else {
         return mc.field_71441_e.func_147447_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d((double)var0.func_177958_n(), (double)var0.func_177956_o(), (double)var0.func_177952_p()), false, true, false) == null;
      }
   }

   public static void PacketFacePitchAndYaw(float var0, float var1) {
      boolean var2 = mc.field_71439_g.func_70051_ag();
      if (var2 != mc.field_71439_g.field_175171_bO) {
         if (var2) {
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.START_SPRINTING));
         } else {
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.STOP_SPRINTING));
         }

         mc.field_71439_g.field_175171_bO = var2;
      }

      boolean var3 = mc.field_71439_g.func_70093_af();
      if (var3 != mc.field_71439_g.field_175170_bN) {
         if (var3) {
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.START_SNEAKING));
         } else {
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.STOP_SNEAKING));
         }

         mc.field_71439_g.field_175170_bN = var3;
      }

      if (isCurrentViewEntity()) {
         AxisAlignedBB var6 = mc.field_71439_g.func_174813_aQ();
         double var7 = mc.field_71439_g.field_70165_t - mc.field_71439_g.field_175172_bI;
         double var9 = var6.field_72338_b - mc.field_71439_g.field_175166_bJ;
         double var11 = mc.field_71439_g.field_70161_v - mc.field_71439_g.field_175167_bK;
         double var13 = (double)(var1 - mc.field_71439_g.field_175164_bL);
         double var15 = (double)(var0 - mc.field_71439_g.field_175165_bM);
         ++mc.field_71439_g.field_175168_bP;
         boolean var17 = var7 * var7 + var9 * var9 + var11 * var11 > 9.0E-4D || mc.field_71439_g.field_175168_bP >= 20;
         boolean var18 = var13 != 0.0D || var15 != 0.0D;
         if (mc.field_71439_g.func_184218_aH()) {
            mc.field_71439_g.field_71174_a.func_147297_a(new PositionRotation(mc.field_71439_g.field_70159_w, -999.0D, mc.field_71439_g.field_70179_y, var1, var0, mc.field_71439_g.field_70122_E));
            var17 = false;
         } else if (var17 && var18) {
            mc.field_71439_g.field_71174_a.func_147297_a(new PositionRotation(mc.field_71439_g.field_70165_t, var6.field_72338_b, mc.field_71439_g.field_70161_v, var1, var0, mc.field_71439_g.field_70122_E));
         } else if (var17) {
            mc.field_71439_g.field_71174_a.func_147297_a(new Position(mc.field_71439_g.field_70165_t, var6.field_72338_b, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70122_E));
         } else if (var18) {
            mc.field_71439_g.field_71174_a.func_147297_a(new Rotation(var1, var0, mc.field_71439_g.field_70122_E));
         } else if (mc.field_71439_g.field_184841_cd != mc.field_71439_g.field_70122_E) {
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayer(mc.field_71439_g.field_70122_E));
         }

         if (var17) {
            mc.field_71439_g.field_175172_bI = mc.field_71439_g.field_70165_t;
            mc.field_71439_g.field_175166_bJ = var6.field_72338_b;
            mc.field_71439_g.field_175167_bK = mc.field_71439_g.field_70161_v;
            mc.field_71439_g.field_175168_bP = 0;
         }

         if (var18) {
            mc.field_71439_g.field_175164_bL = var1;
            mc.field_71439_g.field_175165_bM = var0;
         }

         mc.field_71439_g.field_184841_cd = mc.field_71439_g.field_70122_E;
         mc.field_71439_g.field_189811_cr = mc.field_71439_g.field_71159_c.field_71474_y.field_189989_R;
      }

   }

   public static int GetItemInHotbar(Item var0) {
      for(int var1 = 0; var1 < 9; ++var1) {
         ItemStack var2 = mc.field_71439_g.field_71071_by.func_70301_a(var1);
         if (var2 != ItemStack.field_190927_a && var2.func_77973_b() == var0) {
            return var1;
         }
      }

      return -1;
   }

   public static BlockPos GetLocalPlayerPosFloored() {
      return new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
   }

   public static boolean IsPlayerInHole() {
      BlockPos var0 = GetLocalPlayerPosFloored();
      IBlockState var1 = mc.field_71441_e.func_180495_p(var0);
      if (var1.func_177230_c() != Blocks.field_150350_a) {
         return false;
      } else if (mc.field_71441_e.func_180495_p(var0.func_177984_a()).func_177230_c() != Blocks.field_150350_a) {
         return false;
      } else if (mc.field_71441_e.func_180495_p(var0.func_177977_b()).func_177230_c() == Blocks.field_150350_a) {
         return false;
      } else {
         BlockPos[] var2 = new BlockPos[]{var0.func_177978_c(), var0.func_177968_d(), var0.func_177974_f(), var0.func_177976_e()};
         int var3 = 0;
         BlockPos[] var4 = var2;
         int var5 = var2.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            BlockPos var7 = var4[var6];
            IBlockState var8 = mc.field_71441_e.func_180495_p(var7);
            if (var8.func_177230_c() != Blocks.field_150350_a && var8.func_185913_b()) {
               ++var3;
            }
         }

         if (var3 < 4) {
            return false;
         } else {
            return true;
         }
      }
   }

   public static boolean isCurrentViewEntity() {
      return mc.func_175606_aa() == mc.field_71439_g;
   }

   public static boolean IsPlayerTrapped() {
      BlockPos var0 = GetLocalPlayerPosFloored();
      BlockPos[] var1 = new BlockPos[]{var0.func_177977_b(), var0.func_177984_a().func_177984_a(), var0.func_177978_c(), var0.func_177968_d(), var0.func_177974_f(), var0.func_177976_e(), var0.func_177978_c().func_177984_a(), var0.func_177968_d().func_177984_a(), var0.func_177974_f().func_177984_a(), var0.func_177976_e().func_177984_a()};
      BlockPos[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         BlockPos var5 = var2[var4];
         IBlockState var6 = mc.field_71441_e.func_180495_p(var5);
         if (var6.func_177230_c() != Blocks.field_150343_Z && mc.field_71441_e.func_180495_p(var5).func_177230_c() != Blocks.field_150357_h) {
            return false;
         }
      }

      return true;
   }

   public static int GetItemSlot(Item var0) {
      if (mc.field_71439_g == null) {
         return 0;
      } else {
         for(int var1 = 0; var1 < mc.field_71439_g.field_71069_bz.func_75138_a().size(); ++var1) {
            if (var1 != 0 && var1 != 5 && var1 != 6 && var1 != 7 && var1 != 8) {
               ItemStack var2 = (ItemStack)mc.field_71439_g.field_71069_bz.func_75138_a().get(var1);
               if (!var2.func_190926_b() && var2.func_77973_b() == var0) {
                  return var1;
               }
            }
         }

         return -1;
      }
   }

   public static enum FacingDirection {
      North,
      South;

      private static final PlayerUtil.FacingDirection[] $VALUES = new PlayerUtil.FacingDirection[]{North, South, East, West};
      West,
      East;
   }
}
