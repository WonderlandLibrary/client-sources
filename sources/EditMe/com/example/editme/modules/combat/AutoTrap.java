package com.example.editme.modules.combat;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.BlockInteractionHelper;
import com.example.editme.util.client.EntityUtil;
import com.example.editme.util.client.Friends;
import com.example.editme.util.setting.SettingsManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;

@Module.Info(
   name = "AutoTrap",
   category = Module.Category.COMBAT,
   description = "Traps your enemies"
)
public class AutoTrap extends Module {
   private int delayStep;
   private Setting blocksPerTick;
   private Setting noGlitchBlocks;
   private int lastHotbarSlot;
   private Setting rotate;
   private EntityPlayer closestTarget;
   private boolean isSneaking;
   private int playerHotbarSlot;
   private boolean firstRun;
   private String lastTargetName;
   private Setting tickDelay;
   private Setting mode;
   private boolean missingObiDisable;
   private int offsetStep;
   private Setting range;

   protected void onDisable() {
      if (mc.field_71439_g != null && mc.field_71439_g.func_110143_aJ() > 0.0F) {
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

   protected void onEnable() {
      if (mc.field_71439_g != null && mc.field_71439_g.func_110143_aJ() > 0.0F) {
         this.firstRun = true;
         this.playerHotbarSlot = mc.field_71439_g.field_71071_by.field_70461_c;
         this.lastHotbarSlot = -1;
      }
   }

   private void findClosestTarget() {
      List var1 = mc.field_71441_e.field_73010_i;
      this.closestTarget = null;
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         EntityPlayer var3 = (EntityPlayer)var2.next();
         if (var3 != mc.field_71439_g && (double)mc.field_71439_g.func_70032_d(var3) <= (Double)this.range.getValue() + 3.0D && EntityUtil.isLiving(var3) && var3.func_110143_aJ() > 0.0F && !Friends.isFriend(var3.func_70005_c_())) {
            if (this.closestTarget == null) {
               this.closestTarget = var3;
            } else if (mc.field_71439_g.func_70032_d(var3) < mc.field_71439_g.func_70032_d(this.closestTarget)) {
               this.closestTarget = var3;
            }
         }
      }

   }

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

   public String getHudInfo() {
      return this.closestTarget != null ? this.closestTarget.func_70005_c_().toUpperCase() : "NO TARGET";
   }

   public AutoTrap() {
      this.mode = this.register(SettingsManager.e("Mode", AutoTrap.Mode.TRAP));
      this.range = this.register(SettingsManager.doubleBuilder("Range").withMinimum(3.5D).withValue((Number)5.0D).withMaximum(8.0D).build());
      this.blocksPerTick = this.register(SettingsManager.integerBuilder("BlocksPerTick").withMinimum(1).withValue((int)4).withMaximum(10).build());
      this.tickDelay = this.register(SettingsManager.integerBuilder("Delay").withMinimum(0).withValue((int)2).withMaximum(10).build());
      this.rotate = this.register(SettingsManager.b("Rotate", false));
      this.noGlitchBlocks = this.register(SettingsManager.b("No Glitch Blocks", true));
      this.playerHotbarSlot = -1;
      this.lastHotbarSlot = -1;
      this.isSneaking = false;
      this.delayStep = 0;
      this.offsetStep = 0;
      this.missingObiDisable = false;
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

   private boolean placeBlockInRange(BlockPos var1, double var2) {
      Block var4 = mc.field_71441_e.func_180495_p(var1).func_177230_c();
      if (!(var4 instanceof BlockAir) && !(var4 instanceof BlockLiquid)) {
         return false;
      } else {
         Iterator var5 = mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(var1)).iterator();

         while(var5.hasNext()) {
            Entity var6 = (Entity)var5.next();
            if (!(var6 instanceof EntityItem) && !(var6 instanceof EntityXPOrb)) {
               return false;
            }
         }

         EnumFacing var11 = getPlaceableSide(var1);
         if (var11 == null) {
            return false;
         } else {
            BlockPos var12 = var1.func_177972_a(var11);
            EnumFacing var7 = var11.func_176734_d();
            if (!BlockInteractionHelper.canBeClicked(var12)) {
               return false;
            } else {
               Vec3d var8 = (new Vec3d(var12)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(var7.func_176730_m())).func_186678_a(0.5D));
               Block var9 = mc.field_71441_e.func_180495_p(var12).func_177230_c();
               if (mc.field_71439_g.func_174791_d().func_72438_d(var8) > var2) {
                  return false;
               } else {
                  int var10 = this.findObiInHotbar();
                  if (var10 == -1) {
                     this.missingObiDisable = true;
                     return false;
                  } else {
                     if (this.lastHotbarSlot != var10) {
                        mc.field_71439_g.field_71071_by.field_70461_c = var10;
                        this.lastHotbarSlot = var10;
                     }

                     if (!this.isSneaking && BlockInteractionHelper.blackList.contains(var9) || BlockInteractionHelper.shulkerList.contains(var9)) {
                        mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.START_SNEAKING));
                        this.isSneaking = true;
                     }

                     if ((Boolean)this.rotate.getValue()) {
                        BlockInteractionHelper.faceVectorPacketInstant(var8);
                     }

                     mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, var12, var7, var8, EnumHand.MAIN_HAND);
                     mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                     mc.field_71467_ac = 4;
                     if ((Boolean)this.noGlitchBlocks.getValue() && !mc.field_71442_b.func_178889_l().equals(GameType.CREATIVE)) {
                        mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerDigging(net.minecraft.network.play.client.CPacketPlayerDigging.Action.START_DESTROY_BLOCK, var12, var7));
                     }

                     return true;
                  }
               }
            }
         }
      }
   }

   public void onUpdate() {
      if (mc.field_71439_g != null && mc.field_71439_g.func_110143_aJ() > 0.0F) {
         if (this.firstRun) {
            if (this.findObiInHotbar() == -1) {
               this.disable();
               return;
            }
         } else {
            if (this.delayStep < (Integer)this.tickDelay.getValue()) {
               ++this.delayStep;
               return;
            }

            this.delayStep = 0;
         }

         this.findClosestTarget();
         if (this.closestTarget != null) {
            if (this.firstRun) {
               this.firstRun = false;
               this.lastTargetName = this.closestTarget.func_70005_c_();
            } else if (!this.lastTargetName.equals(this.closestTarget.func_70005_c_())) {
               this.offsetStep = 0;
               this.lastTargetName = this.closestTarget.func_70005_c_();
            }

            ArrayList var1 = new ArrayList();
            if (((AutoTrap.Mode)this.mode.getValue()).equals(AutoTrap.Mode.TRAP)) {
               Collections.addAll(var1, AutoTrap.Offsets.TRAP);
            }

            if (((AutoTrap.Mode)this.mode.getValue()).equals(AutoTrap.Mode.CITYBOSS)) {
               Collections.addAll(var1, AutoTrap.Offsets.CITYBOSS);
            }

            if (((AutoTrap.Mode)this.mode.getValue()).equals(AutoTrap.Mode.DOUBLEROOF)) {
               Collections.addAll(var1, AutoTrap.Offsets.DOUBLEROOF);
            }

            int var2;
            for(var2 = 0; var2 < (Integer)this.blocksPerTick.getValue(); ++this.offsetStep) {
               if (this.offsetStep >= var1.size()) {
                  this.offsetStep = 0;
                  break;
               }

               BlockPos var3 = new BlockPos((Vec3d)var1.get(this.offsetStep));
               BlockPos var4 = (new BlockPos(this.closestTarget.func_174791_d())).func_177977_b().func_177982_a(var3.field_177962_a, var3.field_177960_b, var3.field_177961_c);
               if (this.placeBlockInRange(var4, (Double)this.range.getValue())) {
                  ++var2;
               }
            }

            if (var2 > 0) {
               if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                  mc.field_71439_g.field_71071_by.field_70461_c = this.playerHotbarSlot;
                  this.lastHotbarSlot = this.playerHotbarSlot;
               }

               if (this.isSneaking) {
                  mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.STOP_SNEAKING));
                  this.isSneaking = false;
               }
            }

            if (this.missingObiDisable) {
               this.missingObiDisable = false;
               this.disable();
            }

         }
      }
   }

   private static class Offsets {
      private static final Vec3d[] DOUBLEROOF = new Vec3d[]{new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 1.0D, -1.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(-1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 2.0D, 1.0D), new Vec3d(-1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 3.0D, -1.0D), new Vec3d(0.0D, 3.0D, 0.0D), new Vec3d(0.0D, 4.0D, 0.0D)};
      private static final Vec3d[] TRAP = new Vec3d[]{new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 1.0D, -1.0D), new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(-1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 2.0D, 1.0D), new Vec3d(-1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 3.0D, -1.0D), new Vec3d(0.0D, 3.0D, 0.0D)};
      private static final Vec3d[] CITYBOSS = new Vec3d[]{new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(0.0D, 1.0D, -1.0D), new Vec3d(0.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 2.0D, 1.0D), new Vec3d(-1.0D, 2.0D, 0.0D), new Vec3d(-1.0D, 2.0D, -1.0D), new Vec3d(1.0D, 2.0D, 1.0D), new Vec3d(1.0D, 2.0D, -1.0D), new Vec3d(-1.0D, 2.0D, 1.0D), new Vec3d(0.0D, 3.0D, -1.0D), new Vec3d(0.0D, 3.0D, 0.0D)};

      static Vec3d[] access$000() {
         return TRAP;
      }

      static Vec3d[] access$100() {
         return CITYBOSS;
      }

      static Vec3d[] access$200() {
         return DOUBLEROOF;
      }
   }

   private static enum Mode {
      CITYBOSS,
      TRAP,
      DOUBLEROOF;

      private static final AutoTrap.Mode[] $VALUES = new AutoTrap.Mode[]{TRAP, CITYBOSS, DOUBLEROOF};
   }
}
