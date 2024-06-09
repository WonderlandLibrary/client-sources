package exhibition.module.impl.movement;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMove;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;

public class DepthStrider extends Module {
   int waitTicks;

   public DepthStrider(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventMove.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMove) {
         EventMove em = (EventMove)event;
         if (mc.thePlayer.isInWater()) {
            ++this.waitTicks;
            double forward;
            double strafe;
            float yaw;
            if (this.waitTicks == 4) {
               forward = (double)mc.thePlayer.movementInput.moveForward;
               strafe = (double)mc.thePlayer.movementInput.moveStrafe;
               yaw = mc.thePlayer.rotationYaw;
               if (forward == 0.0D && strafe == 0.0D) {
                  em.setX(0.0D);
                  em.setZ(0.0D);
               } else {
                  if (forward != 0.0D) {
                     if (strafe > 0.0D) {
                        strafe = 1.0D;
                        yaw += (float)(forward > 0.0D ? -45 : 45);
                     } else if (strafe < 0.0D) {
                        yaw += (float)(forward > 0.0D ? 45 : -45);
                     }

                     strafe = 0.0D;
                     if (forward > 0.0D) {
                        forward = 1.0D;
                     } else if (forward < 0.0D) {
                        forward = -1.0D;
                     }
                  }

                  em.setX(forward * 0.1000000059604645D * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * 0.1000000059604645D * Math.sin(Math.toRadians((double)(yaw + 90.0F))));
                  em.setZ(forward * 0.1000000059604645D * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * 0.1000000059604645D * Math.cos(Math.toRadians((double)(yaw + 90.0F))));
               }
            }

            if (this.waitTicks >= 5) {
               forward = (double)mc.thePlayer.movementInput.moveForward;
               strafe = (double)mc.thePlayer.movementInput.moveStrafe;
               yaw = mc.thePlayer.rotationYaw;
               if (forward == 0.0D && strafe == 0.0D) {
                  em.setX(0.0D);
                  em.setZ(0.0D);
               } else {
                  if (forward != 0.0D) {
                     if (strafe > 0.0D) {
                        strafe = 1.0D;
                        yaw += (float)(forward > 0.0D ? -45 : 45);
                     } else if (strafe < 0.0D) {
                        yaw += (float)(forward > 0.0D ? 45 : -45);
                     }

                     strafe = 0.0D;
                     if (forward > 0.0D) {
                        forward = 1.0D;
                     } else if (forward < 0.0D) {
                        forward = -1.0D;
                     }
                  }

                  em.setX(forward * 0.21000001192092896D * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * 0.21000001192092896D * Math.sin(Math.toRadians((double)(yaw + 90.0F))));
                  em.setZ(forward * 0.21000001192092896D * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * 0.21000001192092896D * Math.cos(Math.toRadians((double)(yaw + 90.0F))));
               }

               this.waitTicks = 0;
            }
         }
      }

   }
}
