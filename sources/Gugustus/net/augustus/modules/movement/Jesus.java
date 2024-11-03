package net.augustus.modules.movement;

import java.awt.Color;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.MoveUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.util.MathHelper;

public class Jesus extends Module {
   private final TimeHelper timeHelper = new TimeHelper();
   public StringValue mode = new StringValue(2, "Mode", this, "Solid", new String[]{"Solid", "Speed", "Jump"});
   public DoubleValue speed = new DoubleValue(1, "Speed", this, 1.0, 0.0, 20.0, 2);
   public DoubleValue motion = new DoubleValue(3, "Motion", this, 0.6, 0.01, 2.0, 2);
   public BooleanValue onlyMove = new BooleanValue(4, "OnlyMove", this, false);

   public Jesus() {
      super("Jesus", new Color(39, 103, 146), Categorys.MOVEMENT);
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
      this.setDisplayName(this.getName() + " §8" + this.mode.getSelected());
      String var2 = this.mode.getSelected();
      switch(var2) {
         case "Speed":
            if (mc.thePlayer.isInWater()) {
               if (!mc.gameSettings.keyBindJump.isKeyDown() && !mc.gameSettings.keyBindSneak.isKeyDown()) {
                  mc.thePlayer.motionY = 0.0;
               }

               mc.thePlayer.moveFlying(mc.thePlayer.moveStrafing, mc.thePlayer.moveForward, (float)(this.speed.getValue() / 100.0));
            }
            break;
         case "Jump":
            if (mc.thePlayer.isInWater()) {
               if (this.onlyMove.getBoolean()) {
                  if (MoveUtil.isMoving()) {
                     mc.thePlayer.motionY = this.motion.getValue();
                     if (this.timeHelper.reached(200L) && (mc.thePlayer.isSprinting() || mm.sprint.allSprint && mm.sprint.isToggled())) {
                        float f = mc.thePlayer.rotationYaw * ((float) (Math.PI / 180.0));
                        mc.thePlayer.motionX -= (double)(MathHelper.sin(f) * 0.2F);
                        mc.thePlayer.motionZ += (double)(MathHelper.cos(f) * 0.2F);
                        MoveUtil.strafe();
                     }
                  }
               } else {
                  mc.thePlayer.motionY = this.motion.getValue();
                  if (this.timeHelper.reached(200L) && (mc.thePlayer.isSprinting() || mm.sprint.allSprint && mm.sprint.isToggled())) {
                     float f = mc.thePlayer.rotationYaw * (float) (Math.PI / 180.0);
                     mc.thePlayer.motionX -= (double)(MathHelper.sin(f) * 0.2F);
                     mc.thePlayer.motionZ += (double)(MathHelper.cos(f) * 0.2F);
                     MoveUtil.strafe();
                  }
               }

               this.timeHelper.reset();
            }
      }
   }
}
