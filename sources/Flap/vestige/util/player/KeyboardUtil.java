package vestige.util.player;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import vestige.util.IMinecraft;

public class KeyboardUtil implements IMinecraft {
   public static boolean isPressed(KeyBinding key) {
      return Keyboard.isKeyDown(key.getKeyCode());
   }

   public static void resetKeybinding(KeyBinding key) {
      if (mc.currentScreen != null) {
         key.pressed = false;
      } else {
         key.pressed = isPressed(key);
      }

   }

   public static void resetKeybindings(KeyBinding... keys) {
      KeyBinding[] var1 = keys;
      int var2 = keys.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         KeyBinding key = var1[var3];
         resetKeybinding(key);
      }

   }
}
