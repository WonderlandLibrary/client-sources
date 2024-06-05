package hawk.settings;

import com.lukflug.panelstudio.settings.Toggleable;
import hawk.modules.Module;

public class BooleanSetting extends Setting implements Toggleable {
   public boolean enabled;
   public Module parent;

   public BooleanSetting(String var1, boolean var2, Module var3) {
      this.name = var1;
      this.enabled = var2;
      this.parent = var3;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void toggle() {
      this.enabled = !this.enabled;
   }

   public void setEnabled(boolean var1) {
      this.enabled = var1;
   }

   public boolean isOn() {
      return this.enabled;
   }
}
