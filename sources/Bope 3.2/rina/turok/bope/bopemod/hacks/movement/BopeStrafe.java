package rina.turok.bope.bopemod.hacks.movement;

import java.util.function.Predicate;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.MobEffects;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.events.BopeEventMove;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeStrafe extends BopeModule {
   BopeSetting speed = this.create("Speed", "StrafeSpeed", 1, 1, 6);
   BopeSetting ground = this.create("Event Ground", "StrafeEventGround", true);
   private Listener<BopeEventMove> listener = new Listener<>(event -> {
      float player_speed = 0.02873F;
      float move_forward = this.mc.player.movementInput.moveForward;
      float move_strafe = this.mc.player.movementInput.moveStrafe;
      float rotation_pit = this.mc.player.rotationPitch;
      float rotation_yaw = this.mc.player.rotationYaw;
      if (this.mc.player != null) {
         if (!this.mc.player.isSneaking() && !this.mc.player.isOnLadder() && !this.mc.player.isInWeb && !this.mc.player.isInLava() && !this.mc.player.isInWater() && !this.mc.player.capabilities.isFlying) {
            if (!this.mc.player.onGround || !this.ground.get_value(true)) {
               int amplifier;
               if (this.mc.player.isPotionActive(MobEffects.SPEED)) {
                  amplifier = this.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
                  player_speed *= 1.0F + 0.2F * (float)(amplifier + 1);
               } else if (this.speed.get_value(1) > 1) {
                  amplifier = this.speed.get_value(1);
                  player_speed *= 1.0F + 0.2F * (float)(amplifier + 1);
               }

               if (move_forward == 0.0F && move_strafe == 0.0F) {
                  event.set_x(0.0D);
                  event.set_z(0.0D);
               } else {
                  if (move_forward != 0.0F) {
                     if (move_strafe > 0.0F) {
                        rotation_yaw += (float)(move_forward > 0.0F ? -45 : 45);
                     } else if (move_strafe < 0.0F) {
                        rotation_yaw += (float)(move_forward > 0.0F ? 45 : -45);
                     }

                     move_forward = 0.0F;
                     if (move_forward > 0.0F) {
                        move_forward = 1.0F;
                     } else if (move_forward < 0.0F) {
                        move_forward = -1.0F;
                     }
                  }

                  event.set_x((double)(move_forward * player_speed) * Math.cos(Math.toRadians((double)(rotation_yaw + 90.0F))) + (double)(move_strafe * player_speed) * Math.sin(Math.toRadians((double)(rotation_yaw + 90.0F))));
                  event.set_z((double)(move_forward * player_speed) * Math.sin(Math.toRadians((double)(rotation_yaw + 90.0F))) - (double)(move_strafe * player_speed) * Math.cos(Math.toRadians((double)(rotation_yaw + 90.0F))));
               }

               event.cancel();
            }
         }
      }
   });

   public BopeStrafe() {
      super(BopeCategory.BOPE_MOVEMENT);
      this.name = "Strafe";
      this.tag = "Strafe";
      this.description = "Strafe to movimentation.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }
}
