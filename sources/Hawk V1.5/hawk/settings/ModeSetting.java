package hawk.settings;

import com.lukflug.panelstudio.settings.EnumSetting;
import hawk.modules.Module;
import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting implements EnumSetting {
   public int index;
   public Module parent;
   public List<String> modes;

   public String getMode() {
      return (String)this.modes.get(this.index);
   }

   public void setMode(String var1) {
      this.index = this.modes.indexOf(var1);
   }

   public void cycle() {
      if (this.index < this.modes.size() - 1) {
         ++this.index;
      } else {
         this.index = 0;
      }

   }

   public void increment() {
      if (this.index < this.modes.size() - 1) {
         ++this.index;
      } else {
         this.index = 0;
      }

   }

   public ModeSetting(String var1, Module var2, String var3, String... var4) {
      this.name = var1;
      this.modes = Arrays.asList(var4);
      this.index = this.modes.indexOf(var3);
      this.parent = var2;
   }

   public boolean is(String var1) {
      return this.index == this.modes.indexOf(var1);
   }

   public String getValueName() {
      return (String)this.modes.get(this.index);
   }
}
