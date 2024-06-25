package cc.slack.utils.render;

import cc.slack.utils.client.mc;

public class FreeLookUtil extends mc {
   public static float cameraYaw;
   public static float cameraPitch;
   public static boolean freelooking;

   public static void overrideMouse(float f3, float f4) {
      cameraYaw += f3 * 0.15F;
      cameraPitch -= f4 * 0.15F;
      cameraPitch = Math.max(-90.0F, Math.min(90.0F, cameraPitch));
   }

   public float getYaw() {
      return freelooking ? cameraYaw : mc.getPlayer().rotationYaw;
   }

   public float getPitch() {
      return freelooking ? cameraPitch : mc.getPlayer().rotationPitch;
   }

   public float getPrevYaw() {
      return freelooking ? cameraYaw : mc.getPlayer().prevRotationYaw;
   }

   public float getPrevPitch() {
      return freelooking ? cameraPitch : mc.getPlayer().prevRotationPitch;
   }

   public static void setFreelooking(boolean setFreelook) {
      freelooking = setFreelook;
   }

   public static void enable() {
      setFreelooking(true);
      cameraYaw = mc.getPlayer().rotationYaw;
      cameraPitch = mc.getPlayer().rotationPitch;
   }

   public static void disable() {
      setFreelooking(false);
      cameraYaw = mc.getPlayer().rotationYaw;
      cameraPitch = mc.getPlayer().rotationPitch;
   }
}
