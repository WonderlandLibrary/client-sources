package cc.novoline.modules.binds;

import cc.novoline.modules.binds.KeyboardKeybind;
import cc.novoline.modules.binds.MouseKeybind;
import org.jetbrains.annotations.NotNull;

public final class KeybindFactory {
   @NotNull
   public static MouseKeybind mouse(int var0) {
      return MouseKeybind.of(var0);
   }

   @NotNull
   public static KeyboardKeybind keyboard(int var0) {
      return KeyboardKeybind.of(var0);
   }

   private KeybindFactory() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
