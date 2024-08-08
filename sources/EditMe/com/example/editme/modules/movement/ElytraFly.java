package com.example.editme.modules.movement;

import com.example.editme.events.EntityPlayerTravel;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.MathUtil;
import com.example.editme.util.client.Timer;
import com.example.editme.util.setting.SettingsManager;
import java.awt.AWTException;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.util.math.MathHelper;

@Module.Info(
   name = "ElytraFly",
   category = Module.Category.MOVEMENT,
   description = "Fly like an true luftwaffe pilot"
)
public class ElytraFly extends Module {
   private Setting Accelerate;
   private Setting InstantFly;
   private Setting EquipElytra;
   private Setting GlideSpeed;
   public static ElytraFly INSTANCE;
   private Timer AccelerationResetTimer;
   private Timer InstantFlyTimer;
   private Setting DownSpeed;
   public float apMoveForwardFactor;
   private Setting CancelAtHeight;
   private Setting mode;
   private Timer AccelerationTimer;
   public boolean apMoveUp;
   private Timer PacketTimer;
   public float apYawDeg;
   private Setting speed;
   private boolean SendMessage;
   private Setting vAccelerationTimer;
   public boolean apMoveForward;
   private Setting RotationPitch;
   private Setting UpSpeed;
   private Setting CancelInWater;
   public boolean apMoveDown;
   private int ElytraSlot;
   @EventHandler
   private Listener OnTravel;

   public void Accelerate() {
      if (this.AccelerationResetTimer.passed((double)(Integer)this.vAccelerationTimer.getValue())) {
         this.AccelerationResetTimer.reset();
         this.AccelerationTimer.reset();
         this.SendMessage = false;
      }

      float var1 = (Float)this.speed.getValue();
      double[] var2 = MathUtil.directionSpeed((double)var1);
      mc.field_71439_g.field_70181_x = (double)(-((Float)this.GlideSpeed.getValue() / 10000.0F));
      if (mc.field_71439_g.field_71158_b.field_78902_a == 0.0F && mc.field_71439_g.field_71158_b.field_192832_b == 0.0F) {
         mc.field_71439_g.field_70159_w = 0.0D;
         mc.field_71439_g.field_70179_y = 0.0D;
      } else {
         mc.field_71439_g.field_70159_w = var2[0];
         mc.field_71439_g.field_70179_y = var2[1];
      }

      if (mc.field_71474_y.field_74311_E.func_151470_d()) {
         mc.field_71439_g.field_70181_x = (double)(-(Float)this.DownSpeed.getValue());
      }

      mc.field_71439_g.field_184618_aE = 0.0F;
      mc.field_71439_g.field_70721_aZ = 0.0F;
      mc.field_71439_g.field_184619_aG = 0.0F;
   }

   public void HandleImmediateModeElytra(EntityPlayerTravel var1) {
      var1.cancel();
      boolean var2 = mc.field_71474_y.field_74351_w.func_151470_d();
      boolean var3 = mc.field_71474_y.field_74368_y.func_151470_d();
      boolean var4 = mc.field_71474_y.field_74370_x.func_151470_d();
      boolean var5 = mc.field_71474_y.field_74366_z.func_151470_d();
      boolean var6 = mc.field_71474_y.field_74314_A.func_151470_d();
      boolean var7 = mc.field_71474_y.field_74311_E.func_151470_d();
      float var8 = var2 ? 1.0F : (float)(var3 ? -1 : 0);
      float var9 = mc.field_71439_g.field_70177_z;
      if (!var4 || !var2 && !var3) {
         if (var5 && (var2 || var3)) {
            var9 += 40.0F * var8;
         } else if (var4) {
            var9 -= 90.0F;
         } else if (var5) {
            var9 += 90.0F;
         }
      } else {
         var9 -= 40.0F * var8;
      }

      if (var3) {
         var9 -= 180.0F;
      }

      float var10 = (float)Math.toRadians((double)var9);
      double var11 = Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y);
      if (!var6 && !var2 && !var3 && !var4 && !var5) {
         mc.field_71439_g.field_70159_w = 0.0D;
         mc.field_71439_g.field_70181_x = 0.0D;
         mc.field_71439_g.field_70179_y = 0.0D;
      } else if (var6 && var11 > 1.0D) {
         if (mc.field_71439_g.field_70159_w == 0.0D && mc.field_71439_g.field_70179_y == 0.0D) {
            mc.field_71439_g.field_70181_x = (double)(Float)this.UpSpeed.getValue();
         } else {
            double var13 = var11 * 0.008D;
            EntityPlayerSP var10000 = mc.field_71439_g;
            var10000.field_70181_x += var13 * 3.2D;
            var10000 = mc.field_71439_g;
            var10000.field_70159_w -= (double)(-MathHelper.func_76126_a(var10)) * var13 / 1.0D;
            var10000 = mc.field_71439_g;
            var10000.field_70179_y -= (double)MathHelper.func_76134_b(var10) * var13 / 1.0D;
            var10000 = mc.field_71439_g;
            var10000.field_70159_w *= 0.9900000095367432D;
            var10000 = mc.field_71439_g;
            var10000.field_70181_x *= 0.9800000190734863D;
            var10000 = mc.field_71439_g;
            var10000.field_70179_y *= 0.9900000095367432D;
         }
      } else {
         mc.field_71439_g.field_70159_w = (double)(-MathHelper.func_76126_a(var10)) * 1.7999999523162842D;
         mc.field_71439_g.field_70181_x = (double)(-((Float)this.GlideSpeed.getValue() / 10000.0F));
         mc.field_71439_g.field_70179_y = (double)MathHelper.func_76134_b(var10) * 1.7999999523162842D;
      }

      if (var7) {
         mc.field_71439_g.field_70181_x = (double)(-(Float)this.DownSpeed.getValue());
      }

      if (!var6 && var7) {
      }

   }

   public ElytraFly() throws AWTException {
      this.mode = this.register(SettingsManager.e("Mode", ElytraFly.Mode.MANUAL));
      this.speed = this.register(SettingsManager.f("Speed", 2.8F));
      this.DownSpeed = this.register(SettingsManager.f("DownSpeed", 1.0F));
      this.GlideSpeed = this.register(SettingsManager.f("GlideSpeed", 0.0F));
      this.UpSpeed = this.register(SettingsManager.f("UpSpeed", 2.0F));
      this.Accelerate = this.register(SettingsManager.b("Accelerate", true));
      this.vAccelerationTimer = this.register(SettingsManager.i("Timer", 100));
      this.RotationPitch = this.register(SettingsManager.f("RotationPitch", 0.0F));
      this.CancelInWater = this.register(SettingsManager.b("CancelInWater", true));
      this.CancelAtHeight = this.register(SettingsManager.i("CancelAtHeight", 5));
      this.InstantFly = this.register(SettingsManager.b("InstantFly", true));
      this.EquipElytra = this.register(SettingsManager.b("EquipElytra", true));
      this.PacketTimer = new Timer();
      this.AccelerationTimer = new Timer();
      this.AccelerationResetTimer = new Timer();
      this.InstantFlyTimer = new Timer();
      this.SendMessage = false;
      this.ElytraSlot = -1;
      this.OnTravel = new Listener(this::lambda$new$0, new Predicate[0]);
      INSTANCE = this;
   }

   public void onDisable() {
      super.onDisable();
      if (mc.field_71439_g != null) {
         if (this.ElytraSlot != -1) {
            boolean var1 = !mc.field_71439_g.field_71071_by.func_70301_a(this.ElytraSlot).func_190926_b() || mc.field_71439_g.field_71071_by.func_70301_a(this.ElytraSlot).func_77973_b() != Items.field_190931_a;
            mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, 6, 0, ClickType.PICKUP, mc.field_71439_g);
            mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, this.ElytraSlot, 0, ClickType.PICKUP, mc.field_71439_g);
            if (var1) {
               mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, 6, 0, ClickType.PICKUP, mc.field_71439_g);
            }
         }

      }
   }

   public void HandleNormalModeElytra(EntityPlayerTravel var1) {
      double var2 = mc.field_71439_g.field_70163_u;
      if (var2 > (double)(Integer)this.CancelAtHeight.getValue()) {
         boolean var4 = mc.field_71474_y.field_74351_w.func_151470_d() || mc.field_71474_y.field_74370_x.func_151470_d() || mc.field_71474_y.field_74366_z.func_151470_d() || mc.field_71474_y.field_74368_y.func_151470_d();
         boolean var5 = !mc.field_71439_g.func_70090_H() && !mc.field_71439_g.func_180799_ab() && (Boolean)this.CancelInWater.getValue();
         if (!var4) {
            this.AccelerationTimer.resetTimeSkipTo((long)(-(Integer)this.vAccelerationTimer.getValue()));
         } else if (mc.field_71439_g.field_70125_A <= (Float)this.RotationPitch.getValue() && var5) {
            if ((Boolean)this.Accelerate.getValue() && this.AccelerationTimer.passed((double)(Integer)this.vAccelerationTimer.getValue())) {
               this.Accelerate();
               return;
            }

            return;
         }

         var1.cancel();
         this.Accelerate();
      }
   }

   private void lambda$new$0(EntityPlayerTravel var1) {
      if (mc.field_71439_g != null) {
         if (mc.field_71439_g.func_184582_a(EntityEquipmentSlot.CHEST).func_77973_b() == Items.field_185160_cR) {
            if (!mc.field_71439_g.func_184613_cA()) {
               if (!mc.field_71439_g.field_70122_E && (Boolean)this.InstantFly.getValue()) {
                  if (!this.InstantFlyTimer.passed(1000.0D)) {
                     return;
                  }

                  this.InstantFlyTimer.reset();
                  mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.START_FALL_FLYING));
               }

            } else {
               switch((ElytraFly.Mode)this.mode.getValue()) {
               case PACKET:
                  this.HandleNormalModeElytra(var1);
                  break;
               case MANUAL:
                  this.HandleImmediateModeElytra(var1);
               }

            }
         }
      }
   }

   public void onEnable() {
      super.onEnable();
      this.ElytraSlot = -1;
      if ((Boolean)this.EquipElytra.getValue() && mc.field_71439_g != null && mc.field_71439_g.func_184582_a(EntityEquipmentSlot.CHEST).func_77973_b() != Items.field_185160_cR) {
         for(int var1 = 0; var1 < 44; ++var1) {
            ItemStack var2 = mc.field_71439_g.field_71071_by.func_70301_a(var1);
            if (!var2.func_190926_b() && var2.func_77973_b() == Items.field_185160_cR) {
               ItemElytra var3 = (ItemElytra)var2.func_77973_b();
               this.ElytraSlot = var1;
               break;
            }
         }

         if (this.ElytraSlot != -1) {
            boolean var4 = mc.field_71439_g.func_184582_a(EntityEquipmentSlot.CHEST).func_77973_b() != Items.field_190931_a;
            mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, this.ElytraSlot, 0, ClickType.PICKUP, mc.field_71439_g);
            mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, 6, 0, ClickType.PICKUP, mc.field_71439_g);
            if (var4) {
               mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, this.ElytraSlot, 0, ClickType.PICKUP, mc.field_71439_g);
            }
         }
      }

   }

   private static enum Mode {
      MANUAL;

      private static final ElytraFly.Mode[] $VALUES = new ElytraFly.Mode[]{MANUAL, PACKET};
      PACKET;
   }
}
