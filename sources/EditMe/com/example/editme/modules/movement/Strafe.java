package com.example.editme.modules.movement;

import com.example.editme.events.PlayerMoveEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.Objects;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.potion.PotionEffect;

@Module.Info(
   name = "Strafe",
   description = "Strafe",
   category = Module.Category.MOVEMENT
)
public class Strafe extends Module {
   private Setting jumpDetect = this.register(SettingsManager.b("Leaping Effect Detect", true));
   private Setting accelerationTimer = this.register(SettingsManager.b("Acceleration Timer", false));
   private Setting speedDetect = this.register(SettingsManager.b("Speed Effect Detect", true));
   private Setting autoSprint = this.register(SettingsManager.b("Auto Sprint", false));
   private Setting timerSpeed = this.register(SettingsManager.integerBuilder("A-Timer speed").withMinimum(0).withMaximum(10).withValue((int)1).build());
   private double motionSpeed;
   private int currentState = 1;
   @EventHandler
   private Listener packetEventListener = new Listener(this::lambda$new$0, new Predicate[0]);
   private double prevDist;
   private Setting extraYBoost = this.register(SettingsManager.doubleBuilder("Extra Y Boost").withMinimum(0.0D).withMaximum(1.0D).withValue((Number)0.0D).build());
   private Setting multiplier = this.register(SettingsManager.doubleBuilder("Multiplier").withMinimum(0.1D).withMaximum(2.0D).withValue((Number)1.0D).build());

   public void onUpdate() {
      if (mc.field_71439_g != null) {
         this.prevDist = Math.sqrt((mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70169_q) * (mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70169_q) + (mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70166_s) * (mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70166_s));
         if ((Boolean)this.accelerationTimer.getValue()) {
            mc.field_71428_T.field_194149_e = 50.0F / (float)(Integer)this.timerSpeed.getValue();
         } else if (mc.field_71428_T.field_194149_e != 50.0F) {
            mc.field_71428_T.field_194149_e = 50.0F;
         }

         if (!mc.field_71439_g.func_70051_ag() && (Boolean)this.autoSprint.getValue()) {
            mc.field_71439_g.func_70031_b(true);
            mc.field_71439_g.field_71174_a.func_147297_a(new CPacketEntityAction(mc.field_71439_g, Action.START_SPRINTING));
         }

      }
   }

   private void lambda$new$0(PlayerMoveEvent var1) {
      if (mc.field_71439_g != null) {
         switch(this.currentState) {
         case 0:
            ++this.currentState;
            this.prevDist = 0.0D;
            break;
         case 1:
         default:
            if ((mc.field_71441_e.func_184144_a(mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, mc.field_71439_g.field_70181_x, 0.0D)).size() > 0 || mc.field_71439_g.field_70124_G) && this.currentState > 0) {
               this.currentState = mc.field_71439_g.field_191988_bg == 0.0F && mc.field_71439_g.field_70702_br == 0.0F ? 0 : 1;
            }

            this.motionSpeed = this.prevDist - this.prevDist / 159.0D;
            break;
         case 2:
            double var2 = 0.40123128D + (Double)this.extraYBoost.getValue();
            if ((mc.field_71439_g.field_191988_bg != 0.0F || mc.field_71439_g.field_70702_br != 0.0F) && mc.field_71439_g.field_70122_E) {
               if (mc.field_71439_g.func_70644_a(MobEffects.field_76430_j) && (Boolean)this.jumpDetect.getValue()) {
                  var2 += (double)((float)(mc.field_71439_g.func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F);
               }

               var1.setY(mc.field_71439_g.field_70181_x = var2);
               this.motionSpeed *= 2.149D;
            }
            break;
         case 3:
            this.motionSpeed = this.prevDist - 0.76D * (this.prevDist - this.getBaseMotionSpeed());
         }

         this.motionSpeed = Math.max(this.motionSpeed, this.getBaseMotionSpeed());
         double var4 = (double)mc.field_71439_g.field_71158_b.field_192832_b;
         double var6 = (double)mc.field_71439_g.field_71158_b.field_78902_a;
         double var8 = (double)mc.field_71439_g.field_70177_z;
         if (var4 == 0.0D && var6 == 0.0D) {
            var1.setX(0.0D);
            var1.setZ(0.0D);
         }

         if (var4 != 0.0D && var6 != 0.0D) {
            var4 *= Math.sin(0.7853981633974483D);
            var6 *= Math.cos(0.7853981633974483D);
         }

         var1.setX((var4 * this.motionSpeed * -Math.sin(Math.toRadians(var8)) + var6 * this.motionSpeed * Math.cos(Math.toRadians(var8))) * (Double)this.multiplier.getValue() * 0.99D);
         var1.setZ((var4 * this.motionSpeed * Math.cos(Math.toRadians(var8)) - var6 * this.motionSpeed * -Math.sin(Math.toRadians(var8))) * (Double)this.multiplier.getValue() * 0.99D);
         ++this.currentState;
      }
   }

   public void onDisable() {
   }

   private double getBaseMotionSpeed() {
      double var1 = 0.272D;
      if (mc.field_71439_g.func_70644_a(MobEffects.field_76424_c) && (Boolean)this.speedDetect.getValue()) {
         int var3 = ((PotionEffect)Objects.requireNonNull(mc.field_71439_g.func_70660_b(MobEffects.field_76424_c))).func_76458_c();
         var1 *= 1.0D + 0.2D * (double)var3;
      }

      return var1;
   }
}
