package vestige.handler.client;

import org.lwjgl.opengl.Display;
import vestige.Flap;
import vestige.util.IMinecraft;

public class CameraHandler implements IMinecraft {
   private float cameraYaw;
   private float cameraPitch;
   private boolean freelooking;

   public void overrideMouse() {
      if (mc.inGameHasFocus && Display.isActive()) {
         mc.mouseHelper.mouseXYChange();
         float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
         float f2 = f1 * f1 * f1 * 8.0F;
         float f3 = (float)mc.mouseHelper.deltaX * f2;
         float f4 = (float)mc.mouseHelper.deltaY * f2;
         this.cameraYaw += f3 * 0.15F;
         this.cameraPitch -= f4 * 0.15F;
         this.cameraPitch = Math.max(-90.0F, Math.min(90.0F, this.cameraPitch));
      }

   }

   public boolean isFreelooking() {
      if (this.freelooking) {
         return true;
      } else {
         this.cameraYaw = mc.thePlayer.rotationYaw;
         this.cameraPitch = mc.thePlayer.rotationPitch;
         return false;
      }
   }

   public float getYaw() {
      return this.freelooking && !Flap.instance.isDestructed() ? this.cameraYaw : mc.thePlayer.rotationYaw;
   }

   public float getPitch() {
      return this.freelooking && !Flap.instance.isDestructed() ? this.cameraPitch : mc.thePlayer.rotationPitch;
   }

   public float getPrevYaw() {
      return this.freelooking && !Flap.instance.isDestructed() ? this.cameraYaw : mc.thePlayer.prevRotationYaw;
   }

   public float getPrevPitch() {
      return this.freelooking && !Flap.instance.isDestructed() ? this.cameraPitch : mc.thePlayer.rotationPitch;
   }

   public void setFreelooking(boolean freelooking) {
      this.freelooking = freelooking;
   }
}
