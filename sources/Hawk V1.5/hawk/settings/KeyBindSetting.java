package hawk.settings;

import com.lukflug.panelstudio.settings.KeybindSetting;
import hawk.modules.Module;
import org.lwjgl.input.Keyboard;

public class KeyBindSetting extends Setting implements KeybindSetting {
   public int code;
   public Module parent;

   public int getKeyCode() {
      return this.code;
   }

   public void setKeyCode(int var1) {
      this.code = var1;
   }

   public String getKeyName() {
      return Keyboard.getKeyName(this.code);
   }

   public KeyBindSetting(int var1, Module var2) {
      this.name = "Keybind";
      this.code = var1;
      this.parent = var2;
   }

   public int getKey() {
      return this.code;
   }

   public void setKey(int var1) {
      this.code = var1;
   }
}
