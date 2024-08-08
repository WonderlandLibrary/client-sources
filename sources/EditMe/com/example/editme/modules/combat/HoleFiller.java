package com.example.editme.modules.combat;

import com.example.editme.events.PacketEvent;
import com.example.editme.events.RenderEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.BlockInteractionHelper;
import com.example.editme.util.client.EntityUtil;
import com.example.editme.util.client.Friends;
import com.example.editme.util.module.ModuleManager;
import com.example.editme.util.render.EditmeTessellator;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

@Module.Info(
   name = "HoleFiller",
   category = Module.Category.COMBAT
)
public class HoleFiller extends Module {
   private static BlockPos PlayerPos;
   private boolean caOn;
   private boolean switchCooldown = false;
   private Setting Green = this.register(SettingsManager.integerBuilder("Green").withMinimum(1).withMaximum(255).withValue((int)255).withVisibility(this::lambda$new$2));
   double d;
   private boolean isAttacking = false;
   private int newSlot;
   private static double yaw;
   private Setting selfProtect = this.register(SettingsManager.b("Self Protect", false));
   private Setting Red = this.register(SettingsManager.integerBuilder("Red").withMinimum(1).withMaximum(255).withValue((int)255).withVisibility(this::lambda$new$1));
   private static boolean togglePitch = false;
   private Setting smart = this.register(SettingsManager.b("Smart", false));
   private static double pitch;
   private Setting range = this.register(SettingsManager.doubleBuilder("Range").withMinimum(0.0D).withMaximum(6.0D).withValue((Number)4.0D));
   private Setting Blue = this.register(SettingsManager.integerBuilder("Blue").withMinimum(1).withMaximum(255).withValue((int)255).withVisibility(this::lambda$new$3));
   private BlockPos render;
   private EntityPlayer closestTarget;
   private Setting rainbow = this.register(SettingsManager.booleanBuilder("Cycle").withValue(false).withVisibility(this::lambda$new$0));
   private Entity renderEnt;
   private Setting doRender = this.register(SettingsManager.b("Render", false));
   @EventHandler
   private Listener packetListener = new Listener(HoleFiller::lambda$new$4, new Predicate[0]);
   private Setting smartRange = this.register(SettingsManager.integerBuilder("Smart Range").withMinimum(0).withMaximum(6).withValue((int)4));
   private long systemTime = -1L;
   private static boolean isSpoofingAngles;

   public void onWorldRender(RenderEvent var1) {
      if (this.render != null && (Boolean)this.doRender.getValue()) {
         float[] var2 = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0F};
         int var3 = Color.HSBtoRGB(var2[0], 1.0F, 1.0F);
         int var4 = var3 >> 16 & 255;
         int var5 = var3 >> 8 & 255;
         int var6 = var3 & 255;
         if ((Boolean)this.rainbow.getValue()) {
            EditmeTessellator.prepare(7);
            EditmeTessellator.drawBox(this.render, var4, var5, var6, 77, 63);
         } else {
            EditmeTessellator.prepare(7);
            EditmeTessellator.drawBox(this.render, (Integer)this.Red.getValue(), (Integer)this.Green.getValue(), (Integer)this.Blue.getValue(), 77, 63);
         }

         EditmeTessellator.release();
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

   private static void setYawAndPitch(float var0, float var1) {
      yaw = (double)var0;
      pitch = (double)var1;
      isSpoofingAngles = true;
   }

   private double getDistanceToBlockPos(BlockPos var1, BlockPos var2) {
      double var3 = (double)(var1.func_177958_n() - var2.func_177958_n());
      double var5 = (double)(var1.func_177956_o() - var2.func_177956_o());
      double var7 = (double)(var1.func_177952_p() - var2.func_177952_p());
      return Math.sqrt(var3 * var3 + var5 * var5 + var7 * var7);
   }

   private boolean lambda$new$0(Boolean var1) {
      return (Boolean)this.doRender.getValue();
   }

   private boolean isInRange(BlockPos var1) {
      NonNullList var2 = NonNullList.func_191196_a();
      var2.addAll((Collection)this.getSphere(getPlayerPos(), ((Double)this.range.getValue()).floatValue(), ((Double)this.range.getValue()).intValue(), false, true, 0).stream().filter(this::IsHole).collect(Collectors.toList()));
      return var2.contains(var1);
   }

   private void lookAtPacket(double var1, double var3, double var5, EntityPlayer var7) {
      double[] var8 = EntityUtil.calculateLookAt(var1, var3, var5, var7);
      setYawAndPitch((float)var8[0], (float)var8[1]);
   }

   private List findCrystalBlocks() {
      NonNullList var1 = NonNullList.func_191196_a();
      if ((Boolean)this.smart.getValue() && this.closestTarget != null) {
         var1.addAll((Collection)this.getSphere(this.getClosestTargetPos(), ((Integer)this.smartRange.getValue()).floatValue(), ((Double)this.range.getValue()).intValue(), false, true, 0).stream().filter(this::IsHole).filter(this::isInRange).collect(Collectors.toList()));
      } else if (!(Boolean)this.smart.getValue()) {
         var1.addAll((Collection)this.getSphere(getPlayerPos(), ((Double)this.range.getValue()).floatValue(), ((Double)this.range.getValue()).intValue(), false, true, 0).stream().filter(this::IsHole).collect(Collectors.toList()));
      }

      return var1;
   }

   public BlockPos getClosestTargetPos() {
      return this.closestTarget != null ? new BlockPos(Math.floor(this.closestTarget.field_70165_t), Math.floor(this.closestTarget.field_70163_u), Math.floor(this.closestTarget.field_70161_v)) : null;
   }

   private boolean IsHole(BlockPos var1) {
      BlockPos var2 = var1.func_177982_a(0, 1, 0);
      BlockPos var3 = var1.func_177982_a(0, 0, 0);
      BlockPos var4 = var1.func_177982_a(0, 0, -1);
      BlockPos var5 = var1.func_177982_a(1, 0, 0);
      BlockPos var6 = var1.func_177982_a(-1, 0, 0);
      BlockPos var7 = var1.func_177982_a(0, 0, 1);
      BlockPos var8 = var1.func_177982_a(0, 2, 0);
      BlockPos var9 = var1.func_177963_a(0.5D, 0.5D, 0.5D);
      BlockPos var10 = var1.func_177982_a(0, -1, 0);
      return mc.field_71441_e.func_180495_p(var2).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_180495_p(var3).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_180495_p(var8).func_177230_c() == Blocks.field_150350_a && (mc.field_71441_e.func_180495_p(var4).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(var4).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e.func_180495_p(var5).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(var5).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e.func_180495_p(var6).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(var6).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e.func_180495_p(var7).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(var7).func_177230_c() == Blocks.field_150357_h) && mc.field_71441_e.func_180495_p(var9).func_177230_c() == Blocks.field_150350_a && (mc.field_71441_e.func_180495_p(var10).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(var10).func_177230_c() == Blocks.field_150357_h);
   }

   public void onUpdate() {
      if (mc.field_71441_e != null) {
         if ((Boolean)this.smart.getValue()) {
            this.findClosestTarget();
         }

         List var1 = this.findCrystalBlocks();
         BlockPos var2 = null;
         double var3 = 0.0D;
         double var5 = 0.0D;
         int var7 = mc.field_71439_g.func_184614_ca().func_77973_b() == Item.func_150898_a(Blocks.field_150343_Z) ? mc.field_71439_g.field_71071_by.field_70461_c : -1;
         int var8;
         if (var7 == -1) {
            for(var8 = 0; var8 < 9; ++var8) {
               if (mc.field_71439_g.field_71071_by.func_70301_a(var8).func_77973_b() == Item.func_150898_a(Blocks.field_150343_Z)) {
                  var7 = var8;
                  break;
               }
            }
         }

         if (var7 != -1) {
            Iterator var10 = var1.iterator();

            while(true) {
               while(true) {
                  BlockPos var9;
                  do {
                     if (!var10.hasNext()) {
                        this.render = var2;
                        if (var2 != null && mc.field_71439_g.field_70122_E) {
                           if (this.caOn) {
                              ModuleManager.disableModule("AutoCrystal");
                           }

                           var8 = mc.field_71439_g.field_71071_by.field_70461_c;
                           if (mc.field_71439_g.field_71071_by.field_70461_c != var7) {
                              mc.field_71439_g.field_71071_by.field_70461_c = var7;
                           }

                           this.lookAtPacket((double)var2.field_177962_a + 0.5D, (double)var2.field_177960_b - 0.5D, (double)var2.field_177961_c + 0.5D, mc.field_71439_g);
                           BlockInteractionHelper.placeBlockScaffold(this.render);
                           mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                           mc.field_71439_g.field_71071_by.field_70461_c = var8;
                           resetRotation();
                           if (this.caOn) {
                              ModuleManager.enableModule("AutoCrystal");
                           }
                        }

                        return;
                     }

                     var9 = (BlockPos)var10.next();
                  } while(!mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(var9)).isEmpty());

                  if ((Boolean)this.smart.getValue() && this.isInRange(var9)) {
                     if (!(Boolean)this.selfProtect.getValue()) {
                        var2 = var9;
                     } else if (mc.field_71439_g.func_70011_f((double)var9.func_177958_n(), (double)var9.func_177956_o(), (double)var9.func_177952_p()) > 1.0D) {
                        var2 = var9;
                     }
                  } else if (!(Boolean)this.smart.getValue()) {
                     var2 = var9;
                  }
               }
            }
         }
      }
   }

   private boolean lambda$new$1(Integer var1) {
      return (Boolean)this.doRender.getValue();
   }

   private boolean lambda$new$2(Integer var1) {
      return (Boolean)this.doRender.getValue();
   }

   private void findClosestTarget() {
      List var1 = mc.field_71441_e.field_73010_i;
      this.closestTarget = null;
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         EntityPlayer var3 = (EntityPlayer)var2.next();
         if (var3 != mc.field_71439_g && !Friends.isFriend(var3.func_70005_c_()) && EntityUtil.isLiving(var3) && var3.func_110143_aJ() > 0.0F) {
            if (this.closestTarget == null) {
               this.closestTarget = var3;
            } else if (mc.field_71439_g.func_70032_d(var3) < mc.field_71439_g.func_70032_d(this.closestTarget)) {
               this.closestTarget = var3;
            }
         }
      }

   }

   public void onDisable() {
      this.closestTarget = null;
      this.render = null;
      resetRotation();
   }

   public static BlockPos getPlayerPos() {
      return new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
   }

   private static void resetRotation() {
      if (isSpoofingAngles) {
         yaw = (double)mc.field_71439_g.field_70177_z;
         pitch = (double)mc.field_71439_g.field_70125_A;
         isSpoofingAngles = false;
      }

   }

   private boolean lambda$new$3(Integer var1) {
      return (Boolean)this.doRender.getValue();
   }

   public void onEnable() {
      if (ModuleManager.isModuleEnabled("AutoCrystal")) {
         this.caOn = true;
      }

   }

   private static void lambda$new$4(PacketEvent.Send var0) {
      Packet var1 = var0.getPacket();
      if (var1 instanceof CPacketPlayer && isSpoofingAngles) {
         ((CPacketPlayer)var1).field_149476_e = (float)yaw;
         ((CPacketPlayer)var1).field_149473_f = (float)pitch;
      }

   }
}
