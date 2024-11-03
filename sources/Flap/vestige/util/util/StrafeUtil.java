package vestige.util.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class StrafeUtil {
   private static long lastKeyPressTimeA = 0L;
   private static long lastKeyPressTimeD = 0L;
   private static boolean lastKeyWasA = false;
   private static boolean lastKeyWasD = false;
   private static final long maxConsecutiveDelay = 1000L;

   public static boolean isConsecutiveStrafing() {
      KeyBinding keyBindLeft = Minecraft.getMinecraft().gameSettings.keyBindLeft;
      KeyBinding keyBindRight = Minecraft.getMinecraft().gameSettings.keyBindRight;
      long currentTime = System.currentTimeMillis();
      if (keyBindLeft.isKeyDown()) {
         if (lastKeyWasD && currentTime - lastKeyPressTimeD < 1000L) {
            lastKeyWasA = true;
            lastKeyPressTimeA = currentTime;
            return true;
         }

         lastKeyWasA = true;
         lastKeyWasD = false;
         lastKeyPressTimeA = currentTime;
      }

      if (keyBindRight.isKeyDown()) {
         if (lastKeyWasA && currentTime - lastKeyPressTimeA < 1000L) {
            lastKeyWasD = true;
            lastKeyPressTimeD = currentTime;
            return true;
         }

         lastKeyWasD = true;
         lastKeyWasA = false;
         lastKeyPressTimeD = currentTime;
      }

      return false;
   }
}
