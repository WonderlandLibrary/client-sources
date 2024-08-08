package com.example.editme.modules.combat;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.BlockInteractionHelper;
import com.example.editme.util.module.ModuleManager;
import com.example.editme.util.setting.SettingsManager;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@Module.Info(
   name = "Surround",
   category = Module.Category.COMBAT
)
public class Surround extends Module {
   private Setting timeoutTicks;
   private int playerHotbarSlot;
   private Setting autoCenter;
   private Setting smartDisableRange;
   private int delayStep;
   private Setting smartDisable;
   private boolean missingObiDisable;
   private Setting blocksPerTick;
   private Vec3d playerPos;
   private Setting rotate;
   private Setting mode;
   private boolean firstRun;
   private int offsetStep;
   private Setting triggerable;
   private boolean isSneaking;
   private Setting tickDelay;
   private int lastHotbarSlot;
   private int totalTicksRunning;
   private BlockPos initialPos;

   private static EnumFacing getPlaceableSide(BlockPos var0) {
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

   private void centerPlayer(double var1, double var3, double var5) {
      mc.field_71439_g.field_71174_a.func_147297_a(new Position(var1, var3, var5, true));
      mc.field_71439_g.func_70107_b(var1, var3, var5);
   }

   public void onUpdate() {
      if (mc.field_71439_g != null && !ModuleManager.isModuleEnabled("Freecam")) {
         if ((Boolean)this.triggerable.getValue() && this.totalTicksRunning >= (Integer)this.timeoutTicks.getValue()) {
            this.totalTicksRunning = 0;
            this.disable();
         } else if ((Boolean)this.smartDisable.getValue() && mc.field_71439_g.func_70011_f((double)this.initialPos.func_177958_n(), (double)this.initialPos.func_177956_o(), (double)this.initialPos.func_177952_p()) > (Double)this.smartDisableRange.getValue()) {
            this.disable();
         } else {
            if (!this.firstRun) {
               if (this.delayStep < (Integer)this.tickDelay.getValue()) {
                  ++this.delayStep;
                  return;
               }

               this.delayStep = 0;
            }

            if (this.firstRun) {
               this.firstRun = false;
               if (this.findObiInHotbar() == -1) {
                  this.missingObiDisable = true;
               }
            }

            Vec3d[] var1 = new Vec3d[0];
            int var2 = 0;
            if (((Surround.Mode)this.mode.getValue()).equals(Surround.Mode.FULL)) {
               var1 = Surround.Offsets.FULL;
               var2 = Surround.Offsets.FULL.length;
            }

            if (((Surround.Mode)this.mode.getValue()).equals(Surround.Mode.SURROUND)) {
               var1 = Surround.Offsets.SURROUND;
               var2 = Surround.Offsets.SURROUND.length;
            }

            if (((Surround.Mode)this.mode.getValue()).equals(Surround.Mode.CITY)) {
               var1 = Surround.Offsets.CITY;
               var2 = Surround.Offsets.CITY.length;
            }

            if (((Surround.Mode)this.mode.getValue()).equals(Surround.Mode.HITLER)) {
               var1 = Surround.Offsets.HITLER;
               var2 = Surround.Offsets.HITLER.length;
            }

            if (((Surround.Mode)this.mode.getValue()).equals(Surround.Mode.FITMC)) {
               var1 = Surround.Offsets.FITMC;
               var2 = Surround.Offsets.FITMC.length;
            }

            int var3;
            for(var3 = 0; var3 < (Integer)this.blocksPerTick.getValue(); ++this.offsetStep) {
               if (this.offsetStep >= var2) {
                  this.offsetStep = 0;
                  break;
               }

               BlockPos var4 = new BlockPos(var1[this.offsetStep]);
               BlockPos var5 = (new BlockPos(mc.field_71439_g.func_174791_d())).func_177982_a(var4.field_177962_a, var4.field_177960_b, var4.field_177961_c);
               if (this.placeBlock(var5)) {
                  ++var3;
               }
            }

            if (var3 > 0) {
               if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                  mc.field_71439_g.field_71071_by.field_70461_c = this.playerHotbarSlot;
                  this.lastHotbarSlot = this.playerHotbarSlot;
               }

               if (this.isSneaking) {
                  mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.STOP_SNEAKING));
                  this.isSneaking = false;
               }
            }

            ++this.totalTicksRunning;
         }
      }
   }

   private int findObiInHotbar() {
      int var1 = -1;

      for(int var2 = 0; var2 < 9; ++var2) {
         ItemStack var3 = mc.field_71439_g.field_71071_by.func_70301_a(var2);
         if (var3 != ItemStack.field_190927_a && var3.func_77973_b() instanceof ItemBlock) {
            Block var4 = ((ItemBlock)var3.func_77973_b()).func_179223_d();
            if (var4 instanceof BlockObsidian) {
               var1 = var2;
               break;
            }
         }
      }

      return var1;
   }

   protected void onDisable() {
      if (mc.field_71439_g != null) {
         if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            mc.field_71439_g.field_71071_by.field_70461_c = this.playerHotbarSlot;
         }

         if (this.isSneaking) {
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.STOP_SNEAKING));
            this.isSneaking = false;
         }

         this.playerHotbarSlot = -1;
         this.lastHotbarSlot = -1;
         this.missingObiDisable = false;
      }
   }

   private boolean lambda$new$0(Integer var1) {
      return (Boolean)this.triggerable.getValue();
   }

   private boolean placeBlock(BlockPos var1) {
      Block var2 = mc.field_71441_e.func_180495_p(var1).func_177230_c();
      if (!(var2 instanceof BlockAir) && !(var2 instanceof BlockLiquid)) {
         return false;
      } else {
         Iterator var3 = mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(var1)).iterator();

         while(var3.hasNext()) {
            Entity var4 = (Entity)var3.next();
            if (!(var4 instanceof EntityItem) && !(var4 instanceof EntityXPOrb)) {
               return false;
            }
         }

         EnumFacing var9 = getPlaceableSide(var1);
         if (var9 == null) {
            return false;
         } else {
            BlockPos var10 = var1.func_177972_a(var9);
            EnumFacing var5 = var9.func_176734_d();
            if (!BlockInteractionHelper.canBeClicked(var10)) {
               return false;
            } else {
               Vec3d var6 = (new Vec3d(var10)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(var5.func_176730_m())).func_186678_a(0.5D));
               Block var7 = mc.field_71441_e.func_180495_p(var10).func_177230_c();
               int var8 = this.findObiInHotbar();
               if (var8 == -1) {
                  this.missingObiDisable = true;
                  return false;
               } else {
                  if (this.lastHotbarSlot != var8) {
                     mc.field_71439_g.field_71071_by.field_70461_c = var8;
                     this.lastHotbarSlot = var8;
                  }

                  if (!this.isSneaking && BlockInteractionHelper.blackList.contains(var7) || BlockInteractionHelper.shulkerList.contains(var7)) {
                     mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.START_SNEAKING));
                     this.isSneaking = true;
                  }

                  if ((Boolean)this.rotate.getValue()) {
                     BlockInteractionHelper.faceVectorPacketInstant(var6);
                  }

                  mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, var10, var5, var6, EnumHand.MAIN_HAND);
                  mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                  mc.field_71467_ac = 4;
                  if (ModuleManager.isModuleEnabled("NoBreakAnimation")) {
                     ModuleManager.disableModule("NoBreakAnimation");
                  }

                  return true;
               }
            }
         }
      }
   }

   public Surround() {
      this.mode = this.register(SettingsManager.e("Mode", Surround.Mode.FULL));
      this.triggerable = this.register(SettingsManager.b("Triggerable", false));
      this.smartDisable = this.register(SettingsManager.b("Smart Disable", true));
      this.smartDisableRange = this.register(SettingsManager.d("Smart Disable Range", 1.5D));
      this.timeoutTicks = this.register(SettingsManager.integerBuilder("TimeoutTicks").withMinimum(1).withValue((int)40).withMaximum(100).withVisibility(this::lambda$new$0).build());
      this.blocksPerTick = this.register(SettingsManager.integerBuilder("BlocksPerTick").withMinimum(1).withValue((int)4).withMaximum(9).build());
      this.tickDelay = this.register(SettingsManager.integerBuilder("TickDelay").withMinimum(0).withValue((int)0).withMaximum(10).build());
      this.rotate = this.register(SettingsManager.b("Rotate", true));
      this.autoCenter = this.register(SettingsManager.b("AutoCenter", true));
      this.offsetStep = 0;
      this.delayStep = 0;
      this.playerHotbarSlot = -1;
      this.lastHotbarSlot = -1;
      this.isSneaking = false;
      this.totalTicksRunning = 0;
      this.missingObiDisable = false;
   }

   protected void onEnable() {
      if (mc.field_71439_g == null) {
         this.disable();
      } else {
         BlockPos var1 = mc.field_71439_g.func_180425_c();
         this.playerPos = mc.field_71439_g.func_174791_d();
         this.initialPos = mc.field_71439_g.func_180425_c();
         double var2 = (double)var1.func_177956_o();
         double var4 = (double)var1.func_177958_n();
         double var6 = (double)var1.func_177952_p();
         Vec3d var8 = new Vec3d(var4 + 0.5D, var2, var6 + 0.5D);
         Vec3d var9 = new Vec3d(var4 + 0.5D, var2, var6 - 0.5D);
         Vec3d var10 = new Vec3d(var4 - 0.5D, var2, var6 - 0.5D);
         Vec3d var11 = new Vec3d(var4 - 0.5D, var2, var6 + 0.5D);
         if ((Boolean)this.autoCenter.getValue()) {
            if (this.getDst(var8) < this.getDst(var9) && this.getDst(var8) < this.getDst(var10) && this.getDst(var8) < this.getDst(var11)) {
               var4 = (double)var1.func_177958_n() + 0.5D;
               var6 = (double)var1.func_177952_p() + 0.5D;
               this.centerPlayer(var4, var2, var6);
            }

            if (this.getDst(var9) < this.getDst(var8) && this.getDst(var9) < this.getDst(var10) && this.getDst(var9) < this.getDst(var11)) {
               var4 = (double)var1.func_177958_n() + 0.5D;
               var6 = (double)var1.func_177952_p() - 0.5D;
               this.centerPlayer(var4, var2, var6);
            }

            if (this.getDst(var10) < this.getDst(var8) && this.getDst(var10) < this.getDst(var9) && this.getDst(var10) < this.getDst(var11)) {
               var4 = (double)var1.func_177958_n() - 0.5D;
               var6 = (double)var1.func_177952_p() - 0.5D;
               this.centerPlayer(var4, var2, var6);
            }

            if (this.getDst(var11) < this.getDst(var8) && this.getDst(var11) < this.getDst(var9) && this.getDst(var11) < this.getDst(var10)) {
               var4 = (double)var1.func_177958_n() - 0.5D;
               var6 = (double)var1.func_177952_p() + 0.5D;
               this.centerPlayer(var4, var2, var6);
            }
         }

         this.firstRun = true;
         this.playerHotbarSlot = mc.field_71439_g.field_71071_by.field_70461_c;
         this.lastHotbarSlot = -1;
      }
   }

   double getDst(Vec3d var1) {
      return this.playerPos.func_72438_d(var1);
   }

   private static class Offsets {
      private static final Vec3d[] FITMC = new Vec3d[]{new Vec3d(0.0D, 0.0D, 0.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 1.0D), new Vec3d(1.0D, 0.0D, -1.0D), new Vec3d(-1.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, -1.0D), new Vec3d(2.0D, 0.0D, 0.0D), new Vec3d(2.0D, 0.0D, 1.0D), new Vec3d(2.0D, 0.0D, -1.0D), new Vec3d(-2.0D, 0.0D, 0.0D), new Vec3d(-2.0D, 0.0D, 1.0D), new Vec3d(-2.0D, 0.0D, -1.0D), new Vec3d(0.0D, 0.0D, 2.0D), new Vec3d(1.0D, 0.0D, 2.0D), new Vec3d(-1.0D, 0.0D, 2.0D), new Vec3d(0.0D, 0.0D, -2.0D), new Vec3d(-1.0D, 0.0D, -2.0D), new Vec3d(1.0D, 0.0D, -2.0D), new Vec3d(2.0D, 1.0D, -1.0D), new Vec3d(2.0D, 1.0D, 1.0D), new Vec3d(-2.0D, 1.0D, 0.0D), new Vec3d(-2.0D, 1.0D, 1.0D), new Vec3d(-2.0D, 1.0D, -1.0D), new Vec3d(0.0D, 1.0D, 2.0D), new Vec3d(1.0D, 1.0D, 2.0D), new Vec3d(-1.0D, 1.0D, 2.0D), new Vec3d(0.0D, 1.0D, -2.0D), new Vec3d(1.0D, 1.0D, -2.0D), new Vec3d(-1.0D, 1.0D, -2.0D), new Vec3d(2.0D, 2.0D, -1.0D), new Vec3d(2.0D, 2.0D, 1.0D), new Vec3d(-2.0D, 2.0D, 1.0D), new Vec3d(-2.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 2.0D), new Vec3d(-1.0D, 2.0D, 2.0D), new Vec3d(1.0D, 2.0D, -2.0D), new Vec3d(-1.0D, 2.0D, -2.0D), new Vec3d(2.0D, 3.0D, 0.0D), new Vec3d(2.0D, 3.0D, -1.0D), new Vec3d(2.0D, 3.0D, 1.0D), new Vec3d(-2.0D, 3.0D, 0.0D), new Vec3d(-2.0D, 3.0D, 1.0D), new Vec3d(-2.0D, 3.0D, -1.0D), new Vec3d(0.0D, 3.0D, 2.0D), new Vec3d(1.0D, 3.0D, 2.0D), new Vec3d(-1.0D, 3.0D, 2.0D), new Vec3d(0.0D, 3.0D, -2.0D), new Vec3d(1.0D, 3.0D, -2.0D), new Vec3d(-1.0D, 3.0D, -2.0D), new Vec3d(0.0D, 4.0D, 0.0D), new Vec3d(1.0D, 4.0D, 0.0D), new Vec3d(-1.0D, 4.0D, 0.0D), new Vec3d(0.0D, 4.0D, 1.0D), new Vec3d(0.0D, 4.0D, -1.0D), new Vec3d(1.0D, 4.0D, 1.0D), new Vec3d(-1.0D, 4.0D, 1.0D), new Vec3d(-1.0D, 4.0D, -1.0D), new Vec3d(1.0D, 4.0D, -1.0D), new Vec3d(2.0D, 4.0D, 0.0D), new Vec3d(2.0D, 4.0D, 1.0D), new Vec3d(2.0D, 4.0D, -1.0D)};
      private static final Vec3d[] FULL = new Vec3d[]{new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, -1.0D), new Vec3d(0.0D, -1.0D, 0.0D)};
      private static final Vec3d[] HITLER = new Vec3d[]{new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(2.0D, 0.0D, 0.0D), new Vec3d(2.0D, 0.0D, 1.0D), new Vec3d(2.0D, 0.0D, 2.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(0.0D, 0.0D, 2.0D), new Vec3d(-1.0D, 0.0D, 2.0D), new Vec3d(-2.0D, 0.0D, 2.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(-2.0D, 0.0D, 0.0D), new Vec3d(-2.0D, 0.0D, -1.0D), new Vec3d(-2.0D, 0.0D, -2.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(0.0D, 0.0D, -2.0D), new Vec3d(1.0D, 0.0D, -2.0D), new Vec3d(2.0D, 0.0D, -2.0D)};
      private static final Vec3d[] SURROUND = new Vec3d[]{new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, -1.0D)};
      private static final Vec3d[] CITY = new Vec3d[]{new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(2.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 2.0D), new Vec3d(-2.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -2.0D), new Vec3d(1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, -1.0D)};

      static Vec3d[] access$200() {
         return CITY;
      }

      static Vec3d[] access$100() {
         return SURROUND;
      }

      static Vec3d[] access$300() {
         return HITLER;
      }

      static Vec3d[] access$000() {
         return FULL;
      }

      static Vec3d[] access$400() {
         return FITMC;
      }
   }

   private static enum Mode {
      FULL,
      SURROUND,
      HITLER;

      private static final Surround.Mode[] $VALUES = new Surround.Mode[]{SURROUND, CITY, FULL, HITLER, FITMC};
      CITY,
      FITMC;
   }
}
