package com.example.editme.modules.misc;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.BlockInteractionHelper;
import com.example.editme.util.setting.SettingsManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@Module.Info(
   name = "AutoWither",
   category = Module.Category.MISC
)
public class AutoWither extends Module {
   private Setting delay = this.register(SettingsManager.integerBuilder("Delay").withMinimum(12).withValue((int)20).withMaximum(100).withVisibility(this::lambda$new$0).build());
   private static boolean isSneaking;
   private int buildStage;
   private int headSlot;
   private boolean rotationPlaceableX;
   private int delayStep;
   private Setting placeRange = this.register(SettingsManager.floatBuilder("Range").withMinimum(2.0F).withValue((Number)3.5F).withMaximum(10.0F).build());
   private Setting rotate = this.register(SettingsManager.b("Rotate", true));
   private int bodySlot;
   private boolean rotationPlaceableZ;
   private BlockPos placeTarget;
   private Setting triggerable = this.register(SettingsManager.b("Triggerable", true));

   private static void placeBlock(BlockPos var0, boolean var1) {
      EnumFacing var2 = getPlaceableSide(var0);
      if (var2 != null) {
         BlockPos var3 = var0.func_177972_a(var2);
         EnumFacing var4 = var2.func_176734_d();
         Vec3d var5 = (new Vec3d(var3)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(var4.func_176730_m())).func_186678_a(0.5D));
         Block var6 = mc.field_71441_e.func_180495_p(var3).func_177230_c();
         if (!isSneaking && (BlockInteractionHelper.blackList.contains(var6) || BlockInteractionHelper.shulkerList.contains(var6))) {
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.START_SNEAKING));
            isSneaking = true;
         }

         if (var1) {
            BlockInteractionHelper.faceVectorPacketInstant(var5);
         }

         mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, var3, var4, var5, EnumHand.MAIN_HAND);
         mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
         mc.field_71467_ac = 4;
      }
   }

   private boolean testStructure() {
      return this.testWitherStructure();
   }

   private boolean placingIsBlocked(BlockPos var1) {
      Block var2 = mc.field_71441_e.func_180495_p(var1).func_177230_c();
      if (!(var2 instanceof BlockAir)) {
         return true;
      } else {
         Iterator var3 = mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(var1)).iterator();

         Entity var4;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            var4 = (Entity)var3.next();
         } while(var4 instanceof EntityItem || var4 instanceof EntityXPOrb);

         return true;
      }
   }

   private boolean lambda$new$0(Integer var1) {
      return !(Boolean)this.triggerable.getValue();
   }

   private static EnumFacing getPlaceableSide(BlockPos var0) {
      EnumFacing[] var1 = EnumFacing.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EnumFacing var4 = var1[var3];
         BlockPos var5 = var0.func_177972_a(var4);
         if (mc.field_71441_e.func_180495_p(var5).func_177230_c().func_176209_a(mc.field_71441_e.func_180495_p(var5), false)) {
            IBlockState var6 = mc.field_71441_e.func_180495_p(var5);
            if (!var6.func_185904_a().func_76222_j() && !(var6.func_177230_c() instanceof BlockTallGrass) && !(var6.func_177230_c() instanceof BlockDeadBush)) {
               return var4;
            }
         }
      }

      return null;
   }

   protected void onEnable() {
      if (mc.field_71439_g == null) {
         this.disable();
      } else {
         this.buildStage = 1;
         this.delayStep = 1;
      }
   }

   public List getSphere(BlockPos var1, float var2, int var3, boolean var4, boolean var5, int var6) {
      ArrayList var7 = new ArrayList();
      int var8 = var1.func_177958_n();
      int var9 = var1.func_177956_o();
      int var10 = var1.func_177952_p();

      for(int var11 = var8 - (int)var2; (float)var11 <= (float)var8 + var2; ++var11) {
         for(int var12 = var10 - (int)var2; (float)var12 <= (float)var10 + var2; ++var12) {
            for(int var13 = var5 ? var9 - (int)var2 : var9; (float)var13 < (var5 ? (float)var9 + var2 : (float)(var9 + var3)); ++var13) {
               double var14 = (double)((var8 - var11) * (var8 - var11) + (var10 - var12) * (var10 - var12) + (var5 ? (var9 - var13) * (var9 - var13) : 0));
               if (var14 < (double)(var2 * var2) && (!var4 || var14 >= (double)((var2 - 1.0F) * (var2 - 1.0F)))) {
                  BlockPos var16 = new BlockPos(var11, var13 + var6, var12);
                  var7.add(var16);
               }
            }
         }
      }

      return var7;
   }

   private boolean checkBlocksInHotbar() {
      this.headSlot = -1;
      this.bodySlot = -1;

      for(int var1 = 0; var1 < 9; ++var1) {
         ItemStack var2 = mc.field_71439_g.field_71071_by.func_70301_a(var1);
         if (var2 != ItemStack.field_190927_a) {
            if (var2.func_77973_b() == Items.field_151144_bL && var2.func_77952_i() == 1) {
               if (mc.field_71439_g.field_71071_by.func_70301_a(var1).field_77994_a >= 3) {
                  this.headSlot = var1;
               }
            } else if (var2.func_77973_b() instanceof ItemBlock) {
               Block var3 = ((ItemBlock)var2.func_77973_b()).func_179223_d();
               if (var3 instanceof BlockSoulSand && mc.field_71439_g.field_71071_by.func_70301_a(var1).field_77994_a >= 4) {
                  this.bodySlot = var1;
               }
            }
         }
      }

      return this.bodySlot != -1 && this.headSlot != -1;
   }

   public void onUpdate() {
      if (mc.field_71439_g != null) {
         BlockPos var4;
         if (this.buildStage == 1) {
            isSneaking = false;
            this.rotationPlaceableX = false;
            this.rotationPlaceableZ = false;
            if (!this.checkBlocksInHotbar()) {
               this.disable();
               return;
            }

            List var1 = this.getSphere(mc.field_71439_g.func_180425_c().func_177977_b(), (Float)this.placeRange.getValue(), ((Float)this.placeRange.getValue()).intValue(), false, true, 0);
            boolean var2 = true;
            Iterator var3 = var1.iterator();

            while(var3.hasNext()) {
               var4 = (BlockPos)var3.next();
               this.placeTarget = var4.func_177977_b();
               if (this.testStructure()) {
                  var2 = false;
                  break;
               }
            }

            if (var2) {
               if ((Boolean)this.triggerable.getValue()) {
                  this.disable();
               }

               return;
            }

            mc.field_71439_g.field_71071_by.field_70461_c = this.bodySlot;
            BlockPos[] var9 = AutoWither.BodyParts.bodyBase;
            int var11 = var9.length;

            int var5;
            BlockPos var6;
            for(var5 = 0; var5 < var11; ++var5) {
               var6 = var9[var5];
               placeBlock(this.placeTarget.func_177971_a(var6), (Boolean)this.rotate.getValue());
            }

            if (this.rotationPlaceableX) {
               var9 = AutoWither.BodyParts.ArmsX;
               var11 = var9.length;

               for(var5 = 0; var5 < var11; ++var5) {
                  var6 = var9[var5];
                  placeBlock(this.placeTarget.func_177971_a(var6), (Boolean)this.rotate.getValue());
               }
            } else if (this.rotationPlaceableZ) {
               var9 = AutoWither.BodyParts.ArmsZ;
               var11 = var9.length;

               for(var5 = 0; var5 < var11; ++var5) {
                  var6 = var9[var5];
                  placeBlock(this.placeTarget.func_177971_a(var6), (Boolean)this.rotate.getValue());
               }
            }

            this.buildStage = 2;
         } else if (this.buildStage == 2) {
            mc.field_71439_g.field_71071_by.field_70461_c = this.headSlot;
            BlockPos[] var7;
            int var8;
            int var10;
            if (this.rotationPlaceableX) {
               var7 = AutoWither.BodyParts.headsX;
               var8 = var7.length;

               for(var10 = 0; var10 < var8; ++var10) {
                  var4 = var7[var10];
                  placeBlock(this.placeTarget.func_177971_a(var4), (Boolean)this.rotate.getValue());
               }
            } else if (this.rotationPlaceableZ) {
               var7 = AutoWither.BodyParts.headsZ;
               var8 = var7.length;

               for(var10 = 0; var10 < var8; ++var10) {
                  var4 = var7[var10];
                  placeBlock(this.placeTarget.func_177971_a(var4), (Boolean)this.rotate.getValue());
               }
            }

            if (isSneaking) {
               mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.STOP_SNEAKING));
               isSneaking = false;
            }

            if ((Boolean)this.triggerable.getValue()) {
               this.disable();
            }

            this.buildStage = 3;
         } else if (this.buildStage == 3) {
            if (this.delayStep < (Integer)this.delay.getValue()) {
               ++this.delayStep;
            } else {
               this.delayStep = 1;
               this.buildStage = 1;
            }
         }

      }
   }

   private boolean testWitherStructure() {
      boolean var1 = true;
      this.rotationPlaceableX = true;
      this.rotationPlaceableZ = true;
      boolean var2 = false;
      if (mc.field_71441_e.func_180495_p(this.placeTarget) == null) {
         return false;
      } else {
         Block var3 = mc.field_71441_e.func_180495_p(this.placeTarget).func_177230_c();
         if (var3 instanceof BlockTallGrass || var3 instanceof BlockDeadBush) {
            var2 = true;
         }

         if (getPlaceableSide(this.placeTarget.func_177984_a()) == null) {
            return false;
         } else {
            BlockPos[] var4 = AutoWither.BodyParts.bodyBase;
            int var5 = var4.length;

            int var6;
            BlockPos var7;
            for(var6 = 0; var6 < var5; ++var6) {
               var7 = var4[var6];
               if (this.placingIsBlocked(this.placeTarget.func_177971_a(var7))) {
                  var1 = false;
               }
            }

            var4 = AutoWither.BodyParts.ArmsX;
            var5 = var4.length;

            for(var6 = 0; var6 < var5; ++var6) {
               var7 = var4[var6];
               if (this.placingIsBlocked(this.placeTarget.func_177971_a(var7)) || this.placingIsBlocked(this.placeTarget.func_177971_a(var7.func_177977_b()))) {
                  this.rotationPlaceableX = false;
               }
            }

            var4 = AutoWither.BodyParts.ArmsZ;
            var5 = var4.length;

            for(var6 = 0; var6 < var5; ++var6) {
               var7 = var4[var6];
               if (this.placingIsBlocked(this.placeTarget.func_177971_a(var7)) || this.placingIsBlocked(this.placeTarget.func_177971_a(var7.func_177977_b()))) {
                  this.rotationPlaceableZ = false;
               }
            }

            var4 = AutoWither.BodyParts.headsX;
            var5 = var4.length;

            for(var6 = 0; var6 < var5; ++var6) {
               var7 = var4[var6];
               if (this.placingIsBlocked(this.placeTarget.func_177971_a(var7))) {
                  this.rotationPlaceableX = false;
               }
            }

            var4 = AutoWither.BodyParts.headsZ;
            var5 = var4.length;

            for(var6 = 0; var6 < var5; ++var6) {
               var7 = var4[var6];
               if (this.placingIsBlocked(this.placeTarget.func_177971_a(var7))) {
                  this.rotationPlaceableZ = false;
               }
            }

            return !var2 && var1 && (this.rotationPlaceableX || this.rotationPlaceableZ);
         }
      }
   }

   private static class BodyParts {
      private static final BlockPos[] ArmsZ = new BlockPos[]{new BlockPos(0, 2, -1), new BlockPos(0, 2, 1)};
      private static final BlockPos[] headsX = new BlockPos[]{new BlockPos(0, 3, 0), new BlockPos(-1, 3, 0), new BlockPos(1, 3, 0)};
      private static final BlockPos[] head = new BlockPos[]{new BlockPos(0, 3, 0)};
      private static final BlockPos[] ArmsX = new BlockPos[]{new BlockPos(-1, 2, 0), new BlockPos(1, 2, 0)};
      private static final BlockPos[] bodyBase = new BlockPos[]{new BlockPos(0, 1, 0), new BlockPos(0, 2, 0)};
      private static final BlockPos[] headsZ = new BlockPos[]{new BlockPos(0, 3, 0), new BlockPos(0, 3, -1), new BlockPos(0, 3, 1)};

      static BlockPos[] access$000() {
         return bodyBase;
      }

      static BlockPos[] access$400() {
         return headsZ;
      }

      static BlockPos[] access$100() {
         return ArmsX;
      }

      static BlockPos[] access$300() {
         return headsX;
      }

      static BlockPos[] access$200() {
         return ArmsZ;
      }
   }
}
