package cc.novoline.modules.configurations.property.object;

import cc.novoline.modules.binds.KeyboardKeybind;
import cc.novoline.modules.binds.ModuleKeybind;
import cc.novoline.modules.configurations.property.AbstractProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

public final class KeyBindProperty extends AbstractProperty {
   @Contract(
      pure = true
   )
   public KeyBindProperty(@Nullable ModuleKeybind var1) {
      super(var1);
   }

   public boolean isHeld() {
      ModuleKeybind var2 = (ModuleKeybind)this.value;
      IntProperty.a();
      int var3 = var2.getKey();
      if(var2 instanceof KeyboardKeybind) {
         return Keyboard.isKeyDown(var3);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   private static UnsupportedOperationException a(UnsupportedOperationException var0) {
      return var0;
   }
}
