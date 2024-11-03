package net.augustus.modules.movement;

import java.awt.Color;
import net.augustus.events.EventJump;
import net.augustus.events.EventMove;
import net.augustus.events.EventPostMotion;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.BlockUtil;
import net.augustus.utils.MoveUtil;
import net.lenni0451.eventapi.reflection.EventTarget;

public class Strafe extends Module {
   public final StringValue mode = new StringValue(0, "Mode", this, "Normal", new String[]{"Normal", "Matrix", "Test"});
   public final BooleanValue strafeWhileKB = new BooleanValue(1, "WhileKB", this, false);
   public final BooleanValue onlyOnGround = new BooleanValue(2, "OnlyOnGround", this, false);

   public Strafe() {
      super("Strafe", new Color(50, 168, 82), Categorys.MOVEMENT);
   }

   @EventTarget
   public void onEventPostMotion(EventPostMotion eventPostMotion) {
      if (this.strafeWhileKB.getBoolean() || mc.thePlayer.hurtTime == 0) {
         if (!this.onlyOnGround.getBoolean() || mc.thePlayer.onGround) {
            if (!BlockUtil.isScaffoldToggled()) {
               String var2 = this.mode.getSelected();
               switch(var2) {
                  case "Normal":
                     MoveUtil.strafe();
                     break;
                  case "Matrix":
                     MoveUtil.strafeMatrix();
               }
            }
         }
      }
   }

   @EventTarget
   public void onEventMove(EventMove eventMove) {
      if (this.mode.getSelected().equals("Test")) {
         if (this.strafeWhileKB.getBoolean() || mc.thePlayer.hurtTime == 0) {
            if (!this.onlyOnGround.getBoolean() || mc.thePlayer.onGround) {
               eventMove.setYaw(MoveUtil.getYaw(true));
            }
         }
      }
   }

   @EventTarget
   public void onEventJump(EventJump eventJump) {
      if (this.mode.getSelected().equals("Test")) {
         if (this.strafeWhileKB.getBoolean() || mc.thePlayer.hurtTime == 0) {
            if (!this.onlyOnGround.getBoolean() || mc.thePlayer.onGround) {
               eventJump.setYaw(MoveUtil.getYaw(true));
            }
         }
      }
   }
}
